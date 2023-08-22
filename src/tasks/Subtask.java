package tasks;

public class Subtask extends Task{

    // у каждой подзадачи есть ссылка на эпик, к которому она относится
    private final long epicId;

    private Subtask(String taskName, String description, Status status, long epicId) {
        super(taskName, description, status);
        this.epicId = epicId;
    }

    public static Subtask create(String taskName, String description, Status status, long epicId) {
        return new Subtask(taskName, description, status, epicId);
    }

    public long getEpicId() {
        return epicId;
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
