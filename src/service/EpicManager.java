package service;

import model.Epic;
import model.Subtask;

import java.util.ArrayList;

public class EpicManager extends BasicManager<Epic> {

    // получение списка подзадач для эпика
    public ArrayList<Subtask> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }

    @Override
    public void removeTaskById(int taskId) {
        Epic epic = taskList.getOrDefault(taskId, null);
        if (isNullTask(epic)) {
            return;
        }
        // очистка списка подзадач удаляемого эпика
        epic.removeAllEpicSubtasks();
        taskList.remove(taskId);
    }
}
