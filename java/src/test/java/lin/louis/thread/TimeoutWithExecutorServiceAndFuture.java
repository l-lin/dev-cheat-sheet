package lin.louis.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
 * @author Oodrive
 * @author llin
 * @created 10/08/15 11:06
 */
public class TimeoutWithExecutorServiceAndFuture {
    @Test
    public void timeoutUsingExecutorServiceAndFuture() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> task = executorService.submit(() -> {
            System.out.println("Before sleeping...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.println("Some error: " + e.getMessage());
            }
            System.out.println("After sleeping...");
        });
        task.get(2, TimeUnit.SECONDS);
    }
}
