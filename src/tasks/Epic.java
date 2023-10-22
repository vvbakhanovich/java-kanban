package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    // у каждого эпика есть свой список подзадач
    private final List<Long> subtaskList;
    private LocalDateTime endTime;

    private Epic(String taskName, String description) {
        super(taskName, description, Status.NEW);
        subtaskList = new ArrayList<>();
        taskType = TaskTypes.EPIC;
    }

    private Epic(long taskId, String taskName, String description, String startTime, long duration, Status status) {
        super(taskId, taskName, description, startTime, duration, status);
        subtaskList = new ArrayList<>();
        taskType = TaskTypes.EPIC;
    }

    public static Epic create(String taskName, String description) {
        return new Epic(taskName, description);
    }

    public static Epic createFromFileWithStartTime(long taskId,
                                                        String taskName,
                                                        String description,
                                                        String startTime,
                                                        long duration,
                                                        Status status) {
        return new Epic(taskId, taskName, description, startTime, duration, status);
    }

    // возвращает копию списка подзадач
    public List<Long> getSubtaskList() {
        return subtaskList;
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskList, epic.subtaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskList);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", taskType=" + taskType +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", numberOfSubtasks=" + subtaskList.size() +
                ", status=" + status +
                '}';
    }
}
