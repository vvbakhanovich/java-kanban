package tasks;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {

    // у каждой подзадачи есть ссылка на эпик, к которому она относится
    private final long epicId;

    private Subtask(String taskName, String description, Status status, long epicId) {
        super(taskName, description, status);
        this.epicId = epicId;
        taskType = TaskTypes.SUBTASK;
    }

    private Subtask(String taskName, String description, String startTime, long duration, Status status, long epicId) {
        super(taskName, description, startTime, duration, status);
        this.epicId = epicId;
        taskType = TaskTypes.SUBTASK;
    }

    private Subtask(String taskName, String description, LocalDateTime startTime, long duration, Status status, long epicId) {
        super(taskName, description, startTime, duration, status);
        this.epicId = epicId;
        taskType = TaskTypes.SUBTASK;
    }


    private Subtask(long taskId, String taskName, String description, String startTime, long duration, Status status,
                    long epicId) {
        super(taskId, taskName, description, startTime, duration, status);
        this.epicId = epicId;
        taskType = TaskTypes.SUBTASK;
    }

    private Subtask(long taskId, String taskName, String description, LocalDateTime startTime, long duration, Status status,
                    long epicId) {
        super(taskId, taskName, description, startTime, duration, status);
        this.epicId = epicId;
        taskType = TaskTypes.SUBTASK;
    }


    public static Subtask create(String taskName, String description, Status status, long epicId) {
        return new Subtask(taskName, description, status, epicId);
    }

    public static Subtask createWithStartTime(String taskName, String description, String startTime, long duration,
                                              Status status, long epicId) {
        return new Subtask(taskName, description, startTime, duration, status, epicId);
    }

    public static Subtask createWithStartTime2(String taskName, String description, LocalDateTime startTime, long duration,
                                              Status status, long epicId) {
        return new Subtask(taskName, description, startTime, duration, status, epicId);
    }

    public static Subtask createFromFileWithStartTime(long taskId, String taskName, String description,
                                                      String startTime, long duration, Status status, long epicId) {
        return new Subtask(taskId, taskName, description, startTime, duration, status, epicId);
    }

    public static Subtask createFromFileWithStartTime2(long taskId, String taskName, String description,
                                                      LocalDateTime startTime, long duration, Status status, long epicId) {
        return new Subtask(taskId, taskName, description, startTime, duration, status, epicId);
    }

    public long getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", epicId=" + epicId +
                ", status=" + status +
                '}';
    }
}
