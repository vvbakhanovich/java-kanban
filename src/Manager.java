import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int taskId;
    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, Subtask> subtaskList;
    private final HashMap<Integer, Epic> epicList;

    public Manager() {
        taskList = new HashMap<>();
        subtaskList = new HashMap<>();
        epicList = new HashMap<>();
        taskId = 0;
    }

    public void addTask(Task task) {
        int id = generateId();
        task.setTaskId(id);

        if (task.getClass() == Task.class) {
            taskList.put(id, task);
        } else if (task.getClass() == Epic.class) {
            Epic epic = (Epic) task;
            ArrayList<Subtask> subtasks = epic.getSubtaskList();

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

                if (statusDone == subtasks.size()) {
                    epic.setStatus(Status.DONE);
                } else if (statusNew == subtasks.size()) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }

            epicList.put(id, epic);
        } else {
            subtaskList.put(id, (Subtask) task);
        }

    }


    public int generateId() {
        return taskId++;
    }

    public ArrayList<Task> getTaskList() {
        return  new ArrayList<>(taskList.values());
    }

    public ArrayList<Epic> getEpicList() {
        return  new ArrayList<>(epicList.values());
    }

    public ArrayList<Subtask> getSubtaskList() {
        return  new ArrayList<>(subtaskList.values());
    }


//    public <T extends Task> ArrayList<T> getEpicList() {
//        return (ArrayList<T>) new ArrayList<>(epicList.values());
//    }


}
