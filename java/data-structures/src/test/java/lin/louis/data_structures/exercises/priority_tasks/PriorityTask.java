package lin.louis.data_structures.exercises.priority_tasks;

import java.util.Comparator;
import java.util.List;


public class PriorityTask {

	public static List<Task> orderTasks(List<Task> unsortedTaskList) {
		unsortedTaskList.sort(Comparator.comparingInt(Task::getPriority));
		return unsortedTaskList;
	}

	private PriorityTask() {}

	public static class Task {

		private final int priority;

		public Task(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}
}
