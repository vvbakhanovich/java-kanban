package service;

import model.Epic;
import model.Subtask;

public class SubtaskManager extends BasicManager<Subtask> {

    //
    @Override
    public void addTask(Subtask subtask) {
        int id = generateId();
        subtask.setTaskId(id);
        taskList.put(id, subtask);

        Epic epic = subtask.getEpic();
        epic.addEpicSubtask(subtask);
        epic.checkEpicStatus();
    }

    @Override
    public void updateTask(Subtask subtask) {
        int taskId = subtask.getTaskId();
        Subtask currentSubtask = taskList.getOrDefault(taskId, null);
        if (isNullTask(currentSubtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        int index = epic.getEpicSubtaskId(currentSubtask);
        taskList.put(taskId, subtask);
        epic.updateEpicSubtask(index, subtask);
        epic.checkEpicStatus();
    }

    @Override
    public void removeTaskById(int taskId) {
        Subtask subtask = taskList.getOrDefault(taskId, null);
        if (isNullTask(subtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        taskList.remove(taskId);
        epic.removeEpicSubtask(subtask);
        epic.checkEpicStatus();
    }

}
