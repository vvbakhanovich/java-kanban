package manager;

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
//        if (task == null) {
//            System.out.println("Отсутствует задача");
//            return;
//        }
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            int id = generateId();
            epic.setTaskId(id);
            epicList.put(id, epic);
        } else {
            int id = generateId();
            task.setTaskId(id);
            taskList.put(id, task);
        }
    }

/*    public void addTask(Task task) {
        if (task == null) {
            System.out.println("Отсутствует задача");
            return;
        }
        int id = generateId();
        task.setTaskId(id);
        taskList.put(id, task);
    }

    public void addEpic(Epic epic) {
        if (epic == null) {
            System.out.println("Отсутствует задача");
            return;
        }

        int id = generateId();
        epic.setTaskId(id);
        epicList.put(id, epic);
    }*/

    public void addSubtask(Subtask subtask) {
//        if (subtask == null) {
//            System.out.println("Отсутствует задача");
//            return;
//        }

        int id = generateId();
        subtask.setTaskId(id);
        subtaskList.put(id, subtask);

        Epic epic = subtask.getEpic();
        epic.getSubtaskList().add(subtask);
        subtask.checkEpicStatus();
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeAllEpics() {
        epicList.clear();
    }

    public void removeAllSubtasks() {
        subtaskList.clear();
    }

    public void removeTaskById(int taskId) {
        Task task = taskList.getOrDefault(taskId, null);
        if (isNullTask(task)) {
            return;
        }
        taskList.remove(taskId);
    }

    public void removeEpicById(int epicId) {
        Epic epic = epicList.getOrDefault(epicId, null);
        if (isNullTask(epic)) {
            return;
        }
        epicList.remove(epicId);
    }

    public void removeSubtaskById(int subtaskId) {
        Subtask subtask = subtaskList.getOrDefault(subtaskId, null);
        if (isNullTask(subtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        subtaskList.remove(subtaskId);
        epic.getSubtaskList().remove(subtask);
        subtask.checkEpicStatus();
    }

    public Task getTaskById(int id) {
        Task task = taskList.getOrDefault(id, null);
        isNullTask(task);
        return task;
    }

    public Epic getEpicById(int id) {
        Epic epic = epicList.getOrDefault(id, null);
        isNullTask(epic);
        return epic;
    }

    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtaskList.getOrDefault(id, null);
        isNullTask(subtask);
        return subtask;
    }

    public void updateTask(int taskId, Task task) {
        Task currentTask = taskList.getOrDefault(taskId, null);
        if (isNullTask(task)) {
            return;
        }
        taskList.put(taskId, task);
    }

    public void updateEpic(int epicId, Epic epic) {
        Epic currentEpic = epicList.getOrDefault(epicId, null);
        if (isNullTask(epic)) {
            return;
        }
        epicList.put(epicId, epic);
    }

    public void updateSubtask(int subtaskId, Subtask subtask) {
        Subtask currentSubtask = subtaskList.getOrDefault(subtaskId, null);
        if (isNullTask(subtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        int index = epic.getSubtaskList().indexOf(currentSubtask);
        subtaskList.put(subtaskId, subtask);
        epic.getSubtaskList().set(index, subtask);
        subtask.checkEpicStatus();
    }

    private int generateId() {
        return taskId++;
    }

    private boolean isNullTask(Task task) {
        if (task == null) {
            System.out.println("Задачи с таким идентификатором не существует");
            return true;
        }
        return false;
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    public ArrayList<Subtask> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }
}
