import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;
import utility.TasksSaveRestore;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TasksSaveRestoreTest {
    BasicTask task;
    BasicTask taskWithDate;
    Epic epic;
    Subtask subtask;
    Subtask subtaskWithDate;
    HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        task = BasicTask.create("Задача 1", "Описание 1", Status.NEW);
        task.setTaskId(1);
        taskWithDate = BasicTask.createWithStartTime("Задача 1", "Описание 1",
                "12.02.2030 20:00", 12, Status.NEW);
        taskWithDate.setTaskId(1);
        epic = Epic.create("Эпик 1", "Описание эпика 1");
        epic.setTaskId(2);
        subtask = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.DONE, 2);
        subtask.setTaskId(3);
        subtaskWithDate = Subtask.createWithStartTime("Подзадача 1", "Описание подзадачи 1",
                "12.02.2030 20:00", 25, Status.DONE, 2);
        subtaskWithDate.setTaskId(3);
        historyManager = Managers.getDefaultHistory();
    }


    @Test
    void shouldReturnStringFromTask() {

        String result = "1,BASIC_TASK,Задача 1,Описание 1,null,0,NEW";
        assertEquals(result, TasksSaveRestore.taskToString(task).trim());
    }

    @Test
    void shouldReturnStringFromEpic() {

        String result = "2,EPIC,Эпик 1,Описание эпика 1,null,0,NEW";
        assertEquals(result, TasksSaveRestore.taskToString(epic).trim());
    }

    @Test
    void shouldReturnStringFromSubtask() {

        String result = "3,SUBTASK,Подзадача 1,Описание подзадачи 1,null,0,DONE,2";
        assertEquals(result, TasksSaveRestore.taskToString(subtask).trim());
    }

    @Test
    void shouldReturnTaskFromString() {
        String taskInString = "1,BASIC_TASK,Задача 1,Описание 1,null,0,NEW";
        Task restoredTask = TasksSaveRestore.stringToTask(taskInString);
        assertEquals(task, restoredTask);
    }

    @Test
    void shouldReturnEpicFromString() {
        String epicInString = "1,EPIC,Эпик 1,Описание эпика 1,null,0,NEW";
        Task restoredTask = TasksSaveRestore.stringToTask(epicInString);
        assertEquals(epic, restoredTask);
    }

    @Test
    void shouldReturnSubtaskFromString() {
        String subtaskInString = "3,SUBTASK,Подзадача 1,Описание подзадачи 1,null,0,DONE,2";
        Task restoredTask = TasksSaveRestore.stringToTask(subtaskInString);
        assertEquals(subtask, restoredTask);
    }

    @Test
    void shouldReturnListOfIdsInCSV() {
        historyManager.add(task);
        historyManager.add(subtask);
        historyManager.add(epic);
        String result = "1,3,2";

        String historyToCSV = TasksSaveRestore.historyToString(historyManager);
        assertEquals(result, historyToCSV);
    }

    @Test
    void shouldReturnListOfIds() {
        String historyInCSV = "1,3,2";
        assertEquals(List.of(1L, 3L, 2L), TasksSaveRestore.historyFromString(historyInCSV));
    }

    @Test
    void shouldReturnStringFromTaskWithDate() {
        String result = "1,BASIC_TASK,Задача 1,Описание 1,12.02.2030 20:00,12,NEW";
        assertEquals(result, TasksSaveRestore.taskToString(taskWithDate).trim());
    }

    @Test
    void shouldReturnStringFromSubtaskWithDate() {
        String result = "3,SUBTASK,Подзадача 1,Описание подзадачи 1,12.02.2030 20:00,25,DONE,2";
        assertEquals(result, TasksSaveRestore.taskToString(subtaskWithDate).trim());
    }

    @Test
    void shouldReturnTaskWithDateFromString() {
        String taskInString = "1,BASIC_TASK,Задача 1,Описание 1,12.02.2030 20:00,12,NEW";
        Task restoredTask = TasksSaveRestore.stringToTask(taskInString);
        assertEquals(taskWithDate, restoredTask);
    }

    @Test
    void shouldReturnSubtaskWithDateFromString() {
        String taskInString = "3,SUBTASK,Подзадача 1,Описание подзадачи 1,12.02.2030 20:00,25,DONE,2";
        Task restoredTask = TasksSaveRestore.stringToTask(taskInString);
        assertEquals(subtaskWithDate, restoredTask);
    }

}