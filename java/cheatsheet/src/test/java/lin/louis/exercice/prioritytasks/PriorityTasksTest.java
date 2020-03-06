package lin.louis.exercice.prioritytasks;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class PriorityTasksTest {

	@Test
	public void testSortTasks() {
		List<PriorityTask.Task> taskList = PriorityTask.orderTasks(Arrays.asList(
				new PriorityTask.Task(4),
				new PriorityTask.Task(2),
				new PriorityTask.Task(10),
				new PriorityTask.Task(5)
		));
		assertThat(taskList.get(0).getPriority()).isEqualTo(2);
		assertThat(taskList.get(1).getPriority()).isEqualTo(4);
		assertThat(taskList.get(2).getPriority()).isEqualTo(5);
		assertThat(taskList.get(3).getPriority()).isEqualTo(10);
	}
}
