package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeAllEpics() {
        epicList.clear();
        subtaskList.clear();
    }

    public void removeAllSubtasks() {
        subtaskList.clear();

        for (Epic epic : epicList.values()) {
            epic.removeAllEpicSubtasks();
            epic.checkEpicStatus();
        }
    }

    public Task getTaskById(int taskId) {
        return taskList.getOrDefault(taskId, null);
    }

    public Epic getEpicById(int epicId) {
        return epicList.getOrDefault(epicId, null);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtaskList.getOrDefault(subtaskId, null);
    }

    public void addTask(Task task) {
        int id = generateId();

        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            epic.setTaskId(id);
            epicList.put(id, epic);
        } else if (task instanceof Subtask){
            Subtask subtask = (Subtask) task;
            subtask.setTaskId(id);
            subtaskList.put(id, subtask);
            Epic epic = subtask.getEpic();
            epic.addEpicSubtask(subtask);
            epic.checkEpicStatus();
        } else {
            task.setTaskId(id);
            taskList.put(id, task);
        }
    }

    public void updateTask(Task task) {
        int taskId = task.getTaskId();
        if (task instanceof Epic) {
            Epic epic = (Epic) task;
            Epic currentEpic = epicList.getOrDefault(taskId, null);
            if (isNullTask(currentEpic)) {
                return;
            }
            epicList.put(taskId, epic);
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            Subtask currentSubtask = subtaskList.getOrDefault(taskId, null);
            if (isNullTask(currentSubtask)) {
                return;
            }
            Epic epic = subtask.getEpic();
            int index = epic.getEpicSubtaskId(currentSubtask);
            taskList.put(taskId, subtask);
            epic.updateEpicSubtask(index, subtask);
            epic.checkEpicStatus();
        } else {
            Task currentTask = taskList.getOrDefault(taskId, null);
            if (isNullTask(currentTask)) {
                return;
            }
            taskList.put(taskId, task);
        }

    }

    public void removeTaskById(int taskId) {
        Task task = taskList.getOrDefault(taskId, null);
        if (isNullTask(task)) {
            return;
        }
        taskList.remove(taskId);
    }

    public void removeEpicById(int taskId) {
        Epic epic = epicList.getOrDefault(taskId, null);
        if (isNullTask(epic)) {
            return;
        }
        // очистка списка подзадач удаляемого эпика
        epic.removeAllEpicSubtasks();
        taskList.remove(taskId);
    }

    public void removeSubtaskById(int subtaskId) {
        Subtask subtask = subtaskList.getOrDefault(taskId, null);
        if (isNullTask(subtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        taskList.remove(taskId);
        epic.removeEpicSubtask(subtask);
        epic.checkEpicStatus();
    }

    private int generateId() {
        return taskId++;
    }

    private boolean isNullTask(Task task) {
        return Objects.isNull(task);
    }


}
