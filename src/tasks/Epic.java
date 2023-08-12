package tasks;

import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Subtask> subtaskList;



    public Epic(String taskName, String description, Status status) {
        super(taskName, description, status);
        subtaskList = new ArrayList<>();
    }
}
