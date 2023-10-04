package tasks;

public class BasicTask extends Task {

    private final TaskTypes taskType = TaskTypes.BASIC_TASK;

    protected BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    protected BasicTask(long taskId, String taskName, String description, Status status) {
        super(taskId, taskName, description, status);
    }

    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }

    public static BasicTask createFromFile(long taskId, String taskName, String description, Status status) {
        return new BasicTask(taskId, taskName, description, status);
    }

    @Override
    public TaskTypes getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return "{" +
                "taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
