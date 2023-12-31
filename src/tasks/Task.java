package tasks;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;

import static utility.Constants.FORMATTER;

public abstract class Task implements Comparable<Task>{
    protected String taskName;
    protected long taskId;
    protected String description;
    protected Status status;
    protected TaskTypes taskType;
    protected LocalDateTime startTime;
    protected long duration;

    protected Task(String taskName, String description, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    protected Task(String taskName, String description, LocalDateTime startTime, long duration, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    protected Task(long taskId, String taskName, String description, Status status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    protected Task(long taskId, String taskName, String description, LocalDateTime startTime, long duration, Status status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
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
        return taskId == task.taskId && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskId, description, status, taskType, startTime, duration);
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

    @Override
    public int compareTo(Task o) {
        if (o == null) return 1;

        return Comparator
                .comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Task::getTaskId)
                .compare(this, o);
    }
}
