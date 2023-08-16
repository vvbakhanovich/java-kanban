package model;

import java.util.ArrayList;

public class Subtask extends Task{

    // у каждой подзадачи есть ссылка на эпик, к которому она относится
    private final Epic epic;

    public Subtask(String taskName, String description, Status status, Epic epic) {
        super(taskName, description, status);
        this.epic = epic;
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
