package lin.louis.stream;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BatchSpliterator<T> implements Spliterator<Stream<T>> {

    public static <T> Stream<Stream<T>> batched(Collection<T> stream, int batchSize) {
        return StreamSupport.stream(
                new BatchSpliterator<>(stream, batchSize), false);
    }

    private Queue<T> buffer;

    private final Iterator<T> sourceIterator;

    private final int batchSize;

    private final int size;

    private BatchSpliterator(Collection<T> source, int batchSize) {
        this.buffer = new ArrayDeque<>();
        this.sourceIterator = Objects.requireNonNull(source).iterator();
        this.batchSize = batchSize;
        this.size = calculateSize(source, batchSize);
    }

    @Override
    public boolean tryAdvance(Consumer<? super Stream<T>> action) {
        if (batchSize < 1) {
            return false;
        }

        int count = 0;
        while (sourceIterator.hasNext()) {
            buffer.offer(sourceIterator.next());
            while (buffer.size() > batchSize) {
                buffer.poll();
            }
            if (count < batchSize - 1) {
                count++;
            } else {
                action.accept(toStream(buffer));
                return sourceIterator.hasNext();
            }
        }

        // case when batchSize > source size && not divisible by batchSize
        if (!buffer.isEmpty()) {
            action.accept(toStream(buffer));
            buffer = new ArrayDeque<>();
            return true;
        }

        return false;
    }

    @Override
    public Spliterator<Stream<T>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return size;
    }

    @Override
    public int characteristics() {
        return ORDERED | NONNULL | SIZED;
    }

    private static int calculateSize(Collection<?> source, int batchSize) {
        return source.size() < batchSize
                ? 0 : source.size() - batchSize + 1;
    }

    private Stream<T> toStream(Queue<T> buffer) {
        return Arrays.stream((T[]) buffer.toArray(new Object[0]));
    }
}
