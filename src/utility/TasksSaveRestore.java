package utility;

import manager.HistoryManager;
import tasks.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для преобразования задач в строки и обратно.
 */
public final class TasksSaveRestore {

    private TasksSaveRestore() {
    }

    /**
     * Преобразование задачи в строку формата csv
     *
     * @param task задача, которую требуется преобразовать
     * @return строковое представление задачи
     */
    public static String taskToString(Task task) {
        String delimiter = ",";
        TaskTypes type = task.getTaskType();

        switch (type) {
            case BASIC_TASK:
            case EPIC:
                return String.join(delimiter,
                        String.valueOf(task.getTaskId()),
                        task.getTaskType().toString(),
                        task.getTaskName(),
                        task.getDescription(),
                        task.getStatus().toString());
            case SUBTASK:
                Subtask subtask = (Subtask) task;
                return String.join(delimiter,
                        String.valueOf(subtask.getTaskId()),
                        subtask.getTaskType().toString(),
                        subtask.getTaskName(),
                        subtask.getDescription(),
                        subtask.getStatus().toString(),
                        String.valueOf(subtask.getEpicId()));
            default:
                throw new AssertionError("Несуществующий тип задачи: " + type);
        }

    }

    /**
     * Преобразование строки csv файла в задачу.
     *
     * @param value строка, из которой требуется получить задачу
     * @return задача, созданная из csv строки
     */
    public static Task stringToTask(String value) {
        String[] task = value.split(",");
        TaskTypes type = TaskTypes.valueOf(task[1]);

        switch (type) {
            case BASIC_TASK:
                return BasicTask.createFromFile(Long.parseLong(task[0]), task[2], task[3], Status.valueOf(task[4]));
            case EPIC:
                return Epic.createFromFile(Long.parseLong(task[0]), task[2], task[3], Status.valueOf(task[4]));
            case SUBTASK:
                return Subtask.createFromFile(Long.parseLong(task[0]), task[2], task[3], Status.valueOf(task[4]),
                        Long.parseLong(task[5]));
            default:
                return null;
        }
    }

    /**
     * Преобразование истории в строку формата csv. Строка состоит из id просмотренных задач
     *
     * @param historyManager менеджер истории, чьи просмотры необходимо сохранить
     * @return строка, в которой содержатся id просмотренных задач
     */
    public static String historyToString(HistoryManager historyManager) {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (Task task : historyManager.getHistory()) {
            sb.append(separator + task.getTaskId());
            separator = ",";
        }
        return sb.toString();
    }

    /**
     * Преобразование строки формата csv в список id задач.
     *
     * @param value строка, состоящая из id просмотренных задач
     * @return список id задач из истории просмотров
     */
    public static List<Long> historyFromString(String value) {
        List<Long> result = new ArrayList<>();
        String[] historyId = value.split(",");
        for (String taskId : historyId) {
            result.add(Long.parseLong(taskId));
        }
        return result;
    }
}
