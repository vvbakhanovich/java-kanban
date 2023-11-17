package manager.new_manager;

import tasks.Task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TreeSet;

public final class TaskTimeValidator {
    private TaskTimeValidator(){
    }

    public static boolean isValidStartTime(Task task, TreeSet<Task> sortedTasks) {
        LocalDateTime startTime = task.getStartTime();
        if (startTime == null) {
            return true;
        }
        LocalDateTime endTime = task.getEndTime();

        if (sortedTasks.isEmpty()) {
            return true;
        }

        Task earlierTask = sortedTasks.floor(task);
        Task laterTask = sortedTasks.ceiling(task);

        if (earlierTask == null && laterTask == null) {
            return true;
        }

        if (earlierTask == null) {
            return endTime.isBefore((laterTask).getStartTime());
        } else if (laterTask == null) {
            return startTime.isAfter(earlierTask.getEndTime());
        } else {
            return startTime.isAfter(earlierTask.getEndTime()) &&
                    endTime.isBefore(Objects.requireNonNull(laterTask).getStartTime());
        }
    }
}
