package utility;

import manager.HistoryManager;
import tasks.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static utility.Constants.FORMATTER;

/**
 * Вспомогательный класс для преобразования задач в строки и обратно.
 */
public final class TasksSaveRestore {
    private static final DateTimeFormatter formatter = FORMATTER;

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
                        printStartTime(task.getStartTime()),
                        String.valueOf(task.getDuration()),
                        task.getStatus().toString()) +
                        "\n";
            case SUBTASK:
                Subtask subtask = (Subtask) task;
                return String.join(delimiter,
                        String.valueOf(subtask.getTaskId()),
                        subtask.getTaskType().toString(),
                        subtask.getTaskName(),
                        subtask.getDescription(),
                        printStartTime(task.getStartTime()),
                        String.valueOf(task.getDuration()),
                        subtask.getStatus().toString(),
                        String.valueOf(subtask.getEpicId())) +
                        "\n";
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
                return BasicTask.createFromFileWithStartTime(Long.parseLong(task[0]), task[2], task[3],
                        task[4], Long.parseLong(task[5]), Status.valueOf(task[6]));
            case EPIC:
                return Epic.createFromFileWithStartTime(Long.parseLong(task[0]), task[2], task[3],
                        task[4], Long.parseLong(task[5]), Status.valueOf(task[6]));
            case SUBTASK:
                return Subtask.createFromFileWithStartTime(Long.parseLong(task[0]), task[2], task[3],
                        task[4], Long.parseLong(task[5]), Status.valueOf(task[6]), Long.parseLong(task[7]));
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
        String delimiter = "";
        for (Task task : historyManager.getHistory()) {
            sb.append(delimiter).append(task.getTaskId());
            delimiter = ",";
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
        if (value != null) {
            String[] historyId = value.split(",");
            for (String taskId : historyId) {
                result.add(Long.parseLong(taskId));
            }
        }
        return result;
    }

    private static String printStartTime(LocalDateTime startTime) {
        if(startTime != null) {
            return startTime.format(formatter);
        }
        return "null";
    }
}
