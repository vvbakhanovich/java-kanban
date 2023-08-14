package service;

import model.Epic;
import model.Subtask;

public class SubtaskManager extends BasicManager<Subtask> {
    @Override
    public void addTask(Subtask subtask) {
        int id = generateId();
        subtask.setTaskId(id);
        taskList.put(id, subtask);

        Epic epic = subtask.getEpic();
        epic.getSubtaskList().add(subtask);
        subtask.checkEpicStatus();
    }

    @Override
    public void updateTask(int taskId, Subtask subtask) {
        Subtask currentSubtask = taskList.getOrDefault(taskId, null);
        if (isNullTask(currentSubtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        int index = epic.getSubtaskList().indexOf(currentSubtask);
        taskList.put(taskId, subtask);
        epic.getSubtaskList().set(index, subtask);
        subtask.checkEpicStatus();
    }

    @Override
    public void removeTaskById(int taskId) {
        Subtask subtask = taskList.getOrDefault(taskId, null);
        if (isNullTask(subtask)) {
            return;
        }
        Epic epic = subtask.getEpic();
        taskList.remove(taskId);
        epic.getSubtaskList().remove(subtask);
        subtask.checkEpicStatus();
    }

    // при удалении всех подзадач статус эпика переходит в NEW
    // хотел уточнить нужна ли здесь эта обработка или при удалении подзадач статус эпика меняться не должен
    @Override
    public void removeAllTasks() {
        for (Subtask subtask : taskList.values()) {
            Epic epic = subtask.getEpic();
            epic.getSubtaskList().remove(subtask);
            subtask.checkEpicStatus();
        }
        taskList.clear();
    }
}
