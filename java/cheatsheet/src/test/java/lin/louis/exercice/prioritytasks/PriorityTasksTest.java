package lin.louis.exercice.prioritytasks;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class PriorityTasksTest {
    @Test
    public void testSortTasks() {
        List<PriorityTask.Task> taskList = newArrayList(
                new PriorityTask.Task(4),
                new PriorityTask.Task(2),
                new PriorityTask.Task(10),
                new PriorityTask.Task(5)
        );

        taskList = PriorityTask.orderTasks(taskList);
        assertThat(taskList.get(0).getPriority()).isEqualTo(2);
        assertThat(taskList.get(1).getPriority()).isEqualTo(4);
        assertThat(taskList.get(2).getPriority()).isEqualTo(5);
        assertThat(taskList.get(3).getPriority()).isEqualTo(10);
    }
}
