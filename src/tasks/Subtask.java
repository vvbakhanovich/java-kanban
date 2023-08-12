package tasks;

import java.util.ArrayList;

public class Subtask extends Task{
    private final Epic epic;

    public Subtask(String taskName, String description, Status status, Epic epic) {
        super(taskName, description, status);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void checkEpicStatus() {

        ArrayList<Subtask> subtasks = epic.getSubtaskList();
        if (subtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
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

}
