package model;

import java.util.ArrayList;

public class Epic extends Task {

    // у каждого эпика есть свой список подзадач
    private final ArrayList<Subtask> subtaskList;

    public Epic(String taskName, String description) {
        super(taskName, description, Status.NEW);
        subtaskList = new ArrayList<>();
    }

    // возвращает копию списка подзадач
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList);
    }

    public void addEpicSubtask(Subtask subtask) {
        subtaskList.add(subtask);
    }

    public int getEpicSubtaskId(Subtask subtask) {
        return subtaskList.indexOf(subtask);
    }

    public void updateEpicSubtask(int subtaskId, Subtask subtask) {
        subtaskList.set(subtaskId, subtask);
    }

    public void removeEpicSubtask(Subtask subtask) {
        subtaskList.remove(subtask);
    }

    public void removeAllEpicSubtasks() {
        subtaskList.clear();
    }

    // перенесен метод из класса Subtask
    // метод для обновления статуса эпика при операциях с подзадачами
    public void checkEpicStatus() {

        if (subtaskList.isEmpty()) {
            status = Status.NEW;
            return;
        }

        int statusDone = 0;
        int statusNew = 0;

        for (Subtask subtask : subtaskList) {
            switch (subtask.getStatus()) {
                case IN_PROGRESS:
                    status = Status.IN_PROGRESS;
                    return;
                case DONE:
                    statusDone++;
                    break;
                case NEW:
                    statusNew++;
            }
        }

        if (statusDone == subtaskList.size()) {
            status = Status.DONE;
        } else if (statusNew == subtaskList.size()) {
            status = Status.NEW;
        } else {
            status = Status.IN_PROGRESS;
        }
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
