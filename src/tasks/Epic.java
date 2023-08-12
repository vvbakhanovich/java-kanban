package tasks;

import java.util.ArrayList;

public class Epic extends Task{

    private ArrayList<Subtask> subtaskList;



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
                "subtaskList=" + subtaskList.size() +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
