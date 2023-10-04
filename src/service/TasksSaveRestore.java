package service;

import manager.HistoryManager;
import tasks.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TasksSaveRestore {

    public static String taskToString(Task task) {

        if (task instanceof BasicTask) {
            return String.format("%d,%s,%s,%s,%s\n", task.getTaskId(), task.getTaskType(), task.getTaskName(),
                    task.getDescription(), task.getStatus());
        } else if (task instanceof Epic) {
            return String.format("%d,%s,%s,%s,%s\n", task.getTaskId(), task.getTaskType(), task.getTaskName(),
                    task.getDescription(), task.getStatus());
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,%s,%s,%s,%s,%s\n", subtask.getTaskId(), subtask.getTaskType(), subtask.getTaskName(),
                    subtask.getDescription(), subtask.getStatus(), subtask.getEpicId());
        }
        return null;
    }

    public static Task stringToTask(String value) throws NoSuchFieldException{
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
                throw new NoSuchFieldException("Некорректный тип задачи: " + type);
        }
    }

    public static String historyToString(HistoryManager historyManager) {
        StringBuilder sb = new StringBuilder();
        String separator = "";
        for (Task task : historyManager.getHistory()) {
            sb.append(separator + task.getTaskId());
            separator = ",";
        }
        return sb.toString();
    }

    public static List<Long> historyFromString(String value) {
        List<Long> result = new ArrayList<>();
        String[] historyId = value.split(",");
        for(String taskId : historyId) {
            result.add(Long.parseLong(taskId));
        }
        return result;
    }
}
