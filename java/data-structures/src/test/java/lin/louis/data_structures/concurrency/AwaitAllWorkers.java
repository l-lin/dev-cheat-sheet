package lin.louis.data_structures.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;


class AwaitAllWorkers {

	@Test
	void waitUntilAllWorkersAreFinished() throws InterruptedException {
		// GIVEN
		var output = Collections.synchronizedList(new ArrayList<>());
		var countDownLatch = new CountDownLatch(3);
		var workers = Stream
				.generate(() -> new Thread(() -> {
					output.add("Counted down");
					countDownLatch.countDown();
				}))
				.limit(3)
				.collect(Collectors.toList());

		// WHEN
		workers.forEach(Thread::start);
		countDownLatch.await(2, TimeUnit.SECONDS);
		output.add("Finished");

		// THEN
		assertEquals(4, output.size());
		for (int i = 0; i < output.size() - 2; i++) {
			assertEquals("Counted down", output.get(i));
		}
		assertEquals("Finished", output.get(output.size() - 1));
	}
}
