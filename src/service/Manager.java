package service;

import model.Epic;
import model.Subtask;
import model.Task;

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

    public void addAnyTask(Task task) {
        int id = generateId();
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            epic.setTaskId(id);
            epicList.put(id, epic);
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            subtask.setTaskId(id);
            subtaskList.put(id, subtask);

            Epic epic = subtask.getEpic();
            epic.getSubtaskList().add(subtask);
            subtask.checkEpicStatus();
        } else {
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
    }

    public void addSubtask(Subtask subtask) {
        int id = generateId();
        subtask.setTaskId(id);
        subtaskList.put(id, subtask);

        Epic epic = subtask.getEpic();
        epic.getSubtaskList().add(subtask);
        subtask.checkEpicStatus();
    }*/

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

    public Task getTaskById(int taskId) {
        Task task = taskList.getOrDefault(taskId, null);
        isNullTask(task);
        return task;
    }

    public Epic getEpicById(int epicId) {
        Epic epic = epicList.getOrDefault(epicId, null);
        isNullTask(epic);
        return epic;
    }

    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtaskList.getOrDefault(subtaskId, null);
        isNullTask(subtask);
        return subtask;
    }

    public void updateAnyTask(int id, Task task) {
        if (task instanceof Epic) {
            Epic currentEpic = epicList.getOrDefault(id, null);
            if (isNullTask(currentEpic)) {
                return;
            }
            epicList.put(id, (Epic) task);
        } else if (task instanceof Subtask) {
            Subtask currentSubtask = subtaskList.getOrDefault(id, null);
            if (isNullTask(currentSubtask)) {
                return;
            }

            Subtask subtask = (Subtask) task;
            Epic epic = subtask.getEpic();
            int index = epic.getSubtaskList().indexOf(currentSubtask);
            subtaskList.put(id, subtask);
            epic.getSubtaskList().set(index, subtask);
            subtask.checkEpicStatus();
        } else {
            Task currentTask = taskList.getOrDefault(taskId, null);
            if (isNullTask(currentTask)) {
                return;
            }
            taskList.put(taskId, task);
        }
    }

    public void updateTask(int taskId, Task task) {
        Task currentTask = taskList.getOrDefault(taskId, null);
        if (isNullTask(currentTask)) {
            return;
        }
        taskList.put(taskId, task);
    }

    public void updateEpic(int epicId, Epic epic) {
        Epic currentEpic = epicList.getOrDefault(epicId, null);
        if (isNullTask(currentEpic)) {
            return;
        }
        epicList.put(epicId, epic);
    }

    public void updateSubtask(int subtaskId, Subtask subtask) {
        Subtask currentSubtask = subtaskList.getOrDefault(subtaskId, null);
        if (isNullTask(currentSubtask)) {
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
