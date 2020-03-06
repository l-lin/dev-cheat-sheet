package lin.louis.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class TimeoutWithExecutorServiceAndFuture {
    @Test(expected = TimeoutException.class)
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
