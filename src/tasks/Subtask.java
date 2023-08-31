package tasks;

import java.util.Objects;

public class Subtask extends Task{

    // у каждой подзадачи есть ссылка на эпик, к которому она относится
    private final long epicId;

    private Subtask(String taskName, String description, Status status, long epicId) {
        super(taskName, description, status);
        this.epicId = epicId;
    }

    private Subtask(Subtask subtask, long taskId) {
        super(subtask, taskId);
        this.epicId = subtask.getEpicId();
    }

    public static Subtask create(String taskName, String description, Status status, long epicId) {
        return new Subtask(taskName, description, status, epicId);
    }

    public static Subtask createFromWithId(Subtask subtask, long subtaskId) {
        return new Subtask(subtask, subtaskId);
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
                "epicID=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
