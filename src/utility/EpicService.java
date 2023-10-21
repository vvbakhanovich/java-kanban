package utility;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.util.List;
import java.util.Map;

/**
 * Данный класс отвечает за операции со связанными с эпиком подзадачами.
 */

public class EpicService {

    /**
     * При добавлении подзадачи указывается эпик, к которому она относится. Данный метод добавляет идентификатор
     * подзадачи в список идентификаторов для конкретного эпика.
     * @param epic эпик, для которого требуется добавить подзадачу
     * @param subtaskId идентификатор добавляемой подзадачи
     */
    public static void addEpicSubtask(Epic epic, Long subtaskId) {
        List<Long> subtaskList = epic.getSubtaskList();
        subtaskList.add(subtaskId);
    }

    /**
     * При удалении подзадачи требуется также удалить ее и из списка подзадач ее эпика.
     * @param epic эпик, у которого требуется удалить подзадачу
     * @param subtaskId идентификатор подзадачи для удаления
     */
    public static void removeEpicSubtask(Epic epic, Long subtaskId) {
        epic.getSubtaskList().remove(subtaskId);
    }

    /**
     * Удаление списка подзадач эпика
     * @param epic эпик, у которого необходимо очистить список подзадач
     */
    public static void removeAllEpicSubtasks(Epic epic, Map<Long, Subtask> subtasks) {
        for (Long id : epic.getSubtaskList()) {
            subtasks.remove(id);
        }
        epic.getSubtaskList().clear();
    }

    /**
     * Метод для проверки статуса эпика после операций с подзадачами. В случае добавления/изменения/удаления подзадачи
     * статус эпика должен быть перecчитан по следующей логике: если список подзадач пуст или все подзадачи имеют
     * статус NEW, то статус эпика также должен быть NEW. Если все подзадачи эпика имеют статус DONE, эпик также должен
     * иметь статус DONE. В противном случае статус эпика должен быть IN_PROGRESS.
     * @param epic эпик, для которого необходимо произвести проверку статуса
     * @param subtasks мапа, хранящая в себе полный список подзадач, из которой по id будет получен статус подзадачи
     */
    public static void checkEpicStatus(Epic epic, Map<Long, Subtask> subtasks) {
        List<Long> subtaskList = epic.getSubtaskList();

        if (subtaskList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        int statusDone = 0;
        int statusNew = 0;

        for (Long subtaskId : subtaskList) {
            Subtask subtask = subtasks.get(subtaskId);
            switch (subtask.getStatus()) {
                case IN_PROGRESS:
                    epic.setStatus(Status.IN_PROGRESS);
                    return;
                case DONE:
                    statusDone++;
                    break;
                case NEW:
                    statusNew++;
            }
        }

        if (statusDone == subtaskList.size()) {
            epic.setStatus(Status.DONE);
        } else if (statusNew == subtaskList.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
