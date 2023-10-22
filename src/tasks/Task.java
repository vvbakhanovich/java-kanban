package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class Task {
    protected String taskName;
    protected long taskId;
    protected String description;
    protected Status status;
    protected TaskTypes taskType;
    protected LocalDateTime startTime;
    protected long duration;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    Pattern dateTimePattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}");

    protected Task(String taskName, String description, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    protected Task(String taskName, String description, String startTime, long duration, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        if(dateTimePattern.matcher(startTime).matches()) {
            this.startTime = LocalDateTime.parse(startTime, FORMATTER);
        }
        this.duration = duration;
    }

    protected Task(long taskId, String taskName, String description, Status status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    protected Task(long taskId, String taskName, String description, String startTime, long duration, Status status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        if(dateTimePattern.matcher(startTime).matches()) {
            this.startTime = LocalDateTime.parse(startTime, FORMATTER);
        }
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public TaskTypes getTaskType() {
        return taskType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName=" + taskName +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", status=" + status +
                '}';
    }
}
