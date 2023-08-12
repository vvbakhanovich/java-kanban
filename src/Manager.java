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

    public void addTaskOrEpic(Task task) {
        if (task == null) {
            System.out.println("Отсутствует задача");
            return;
        }
        if (task.getClass() == Task.class) {
            int id = generateId();
            task.setTaskId(id);
            taskList.put(id, task);
        } else if (task.getClass() == Epic.class) {
            Epic epic = (Epic) task;
            int id = generateId();
            epic.setTaskId(id);
            epicList.put(id, epic);
        }
    }

//    public void addTask(Task task) {
//        if (task == null) {
//            System.out.println("Отсутствует задача");
//            return;
//        }
//        int id = generateId();
//        task.setTaskId(id);
//        taskList.put(id, task);
//    }
//
//    public void addEpic(Epic epic) {
//        if (epic == null) {
//            System.out.println("Отсутствует задача");
//            return;
//        }
//
//        int id = generateId();
//        epic.setTaskId(id);
//        epicList.put(id, epic);
//    }

    public void addSubtask(Subtask subtask) {
        if (subtask == null) {
            System.out.println("Отсутствует задача");
            return;
        }

        int id = generateId();
        subtask.setTaskId(id);
        subtaskList.put(id, subtask);

        Epic epic = subtask.getEpic();
        epic.getSubtaskList().add(subtask);
        subtask.checkEpicStatus();
    }

    public void removeTasks() {
        taskList.clear();
    }

    public void removeEpics() {
        epicList.clear();
    }

    public void removeSubtasks() {
        subtaskList.clear();
    }

    public Task getTaskById(int id) {
        Task task = taskList.getOrDefault(id, null);

        if (task == null) {
            System.out.println("Задачи с таким идентификатором не существует");
        }
        return task;
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

    public ArrayList<Subtask> getEpicSubtaskList(Epic epic) {
        return  new ArrayList<>(epic.getSubtaskList());
    }



//    public <T extends Task> ArrayList<T> getEpicList() {
//        return (ArrayList<T>) new ArrayList<>(epicList.values());
//    }


}
