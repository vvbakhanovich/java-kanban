package tasks;

public class BasicTask extends Task{

    private final TaskTypes taskType = TaskTypes.BASIC_TASK;

    protected BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }

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
