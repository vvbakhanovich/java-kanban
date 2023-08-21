package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

// абстрактный класс, прототип для всех задач
public abstract class BasicManager<T extends Task> {

    // поле идентификатор, присваиваемый при добавлении задачи
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

    // изменил модификатор на protected, так как метод будет вызываться в общем менеджере,
    // но не должен быть виден вне пакета
    protected void removeAllTasks() {
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
        return taskList.getOrDefault(taskId, null);
    }

    public void updateTask(T task) {
        int taskId = task.getTaskId();
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
        return Objects.isNull(task);
    }

}
