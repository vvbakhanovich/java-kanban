package model;

import java.util.ArrayList;

public class Epic extends Task{

    private final ArrayList<Subtask> subtaskList;

    public Epic(String taskName, String description, Status status) {
        super(taskName, description, status);
        subtaskList = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtaskList() {
        return subtaskList;
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
