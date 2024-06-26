package lin.louis.data_structures.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TimeoutWithExecutorServiceAndFuture {
    @Test
    void timeoutUsingExecutorServiceAndFuture() {
		Assertions.assertThrows(TimeoutException.class, () -> {
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
			task.get(1, TimeUnit.SECONDS);
		});
	}
}
