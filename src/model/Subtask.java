package model;

public class Subtask extends Task{

    // у каждой подзадачи есть ссылка на эпик, к которому она относится
    private final Epic epic;

    private Subtask(String taskName, String description, Status status, Epic epic) {
        super(taskName, description, status);
        this.epic = epic;
    }

    public static Subtask create(String taskName, String description, Status status, Epic epic) {
        return new Subtask(taskName, description, status, epic);
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicID=" + epic.getTaskId() +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
