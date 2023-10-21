package tasks;

public class BasicTask extends Task {

    private final TaskTypes taskType = TaskTypes.BASIC_TASK;

    private BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    private BasicTask(String taskName, String description, String startTime, long duration, Status status) {
        super(taskName, description, startTime, duration, status);
    }

    private BasicTask(long taskId, String taskName, String description, Status status) {
        super(taskId, taskName, description, status);
    }

    private BasicTask(long taskId, String taskName, String description, String startTime, long duration, Status status) {
        super(taskId, taskName, description, startTime, duration, status);
    }

    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }

    public static BasicTask createWithStartTime(String taskName,
                                                String description,
                                                String startTime,
                                                long duration,
                                                Status status) {
        return new BasicTask(taskName, description, startTime, duration, status);
    }


    public static BasicTask createFromFile(long taskId, String taskName, String description, Status status) {
        return new BasicTask(taskId, taskName, description, status);
    }

    public static BasicTask createFromFileWithStartTime(long taskId,
                                                        String taskName,
                                                        String description,
                                                        String startTime,
                                                        long duration,
                                                        Status status) {
        return new BasicTask(taskId, taskName, description, startTime, duration, status);
    }

    @Override
    public TaskTypes getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return "BasicTask{" +
                "taskId='" + taskId + '\'' +
                ", taskId=" + taskName +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", status=" + status +
                '}';
    }
}
