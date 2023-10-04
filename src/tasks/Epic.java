package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private final TaskTypes taskType = TaskTypes.EPIC;

    // у каждого эпика есть свой список подзадач
    private final List<Long> subtaskList;

    private Epic(String taskName, String description) {
        super(taskName, description, Status.NEW);
        subtaskList = new ArrayList<>();
    }

    private Epic(long taskId, String taskName, String description, Status status) {
        super(taskId, taskName, description, status);
        subtaskList = new ArrayList<>();
    }

    public static Epic create(String taskName, String description) {
        return new Epic(taskName, description);
    }

    public static Epic createFromFile(long taskId, String taskName, String description, Status status) {
        return new Epic(taskId, taskName, description, status);
    }

    // возвращает копию списка подзадач
    public List<Long> getSubtaskList() {
        return subtaskList;
    }

    @Override
    public TaskTypes getTaskType() {
        return taskType;
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
                "numberOfSubtasks=" + subtaskList.size() +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
