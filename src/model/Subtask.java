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

    // метод для обновления статуса эпика при операциях с подзадачами
    public void checkEpicStatus() {

        ArrayList<Subtask> subtasks = epic.getSubtaskList();
        if (subtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        int statusDone = 0;
        int statusNew = 0;

        for (Subtask subtask : subtasks) {
            switch (subtask.getStatus()) {
                case NEW:
                    statusNew++;
                    break;
                case DONE:
                    statusDone++;
                    break;
            }
        }
        if (statusDone == subtasks.size()) {
            epic.setStatus(Status.DONE);
        } else if (statusNew == subtasks.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
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
