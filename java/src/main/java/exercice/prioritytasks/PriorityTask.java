package exercice.prioritytasks;

import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author llin
 * @created 14/05/14.
 */
public class PriorityTask {
    public static List<Task> orderTasks(List<Task> unsortedTaskList) {
        Collections.sort(unsortedTaskList, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getPriority() > task2.getPriority()) {
                    return 1;
                }
                if (task1.getPriority() < task2.getPriority()) {
                    return -1;
                }
                return 0;
            }
        });

        return unsortedTaskList;
    }

    @Data
    public static class Task {
        private int priority;

        public Task(int priority) {
            this.priority = priority;
        }
    }
}
