import tasks.Epic;
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
            epicList.put(id, (Epic) task);
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
    public <T extends Task> ArrayList<T> getEpicList() {
        return (ArrayList<T>) new ArrayList<>(epicList.values());
    }


}
