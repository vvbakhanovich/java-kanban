package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BasicManager<T extends Task> {

    protected static int taskId = 0;
    HashMap<Integer, T> taskList;

    public BasicManager() {
        taskList = new HashMap<>();
    }

    public void addTask(T task) {
        int id = generateId();
        task.setTaskId(id);
        taskList.put(id, task);
    }

    public void removeAllTasks() {
        taskList.clear();
    }

    public void removeTaskById(int taskId) {
        T task = taskList.getOrDefault(taskId, null);
        if (isNullTask(task)) {
            return;
        }
        taskList.remove(taskId);
    }

    public T getTaskById(int taskId) {
        T task = taskList.getOrDefault(taskId, null);
        isNullTask(task);
        return task;
    }

    public void updateTask(int taskId, T task) {
        T currentTask = taskList.getOrDefault(taskId, null);
        if (isNullTask(currentTask)) {
            return;
        }
        taskList.put(taskId, task);
    }

    public ArrayList<T> getTaskList() {
        return new ArrayList<>(taskList.values());
    }



    protected int generateId() {
        return taskId++;
    }

    protected boolean isNullTask(T task) {
        if (task == null) {
            System.out.println("Задачи с таким идентификатором не существует");
            return true;
        }
        return false;
    }

}
