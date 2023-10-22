package tests;

import manager.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class FileBackedTasksManagerTest {

    FileBackedTasksManager manager;
    BasicTask task1;
    BasicTask task2;
    Epic epic1;
    Epic epic2;
    Subtask subtask1;
    Subtask subtask2;
    Subtask subtask3;
    BasicTask taskWithDate1;
    BasicTask taskWithDate2;
    BasicTask taskWithDate3;
    Subtask subtaskWithDate1;
    Subtask subtaskWithDate2;
    Subtask subtaskWithDate3;


    @BeforeEach
    void setUp() {
        manager = new FileBackedTasksManager("src/resources/recovery_test.csv");
        task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.DONE);
        epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        subtask1 = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.DONE, 3);
        epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, 3);
        subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.DONE, 1);
        taskWithDate1 = BasicTask.createWithStartTime("Задача со временем 1",
                "Описание задачи со временем 1", "22.10.2023 09:09", 23, Status.IN_PROGRESS);
        taskWithDate2 = BasicTask.createWithStartTime("Задача со временем 2",
                "Описание задачи со временем 2", "22.10.2023 08:00", 15, Status.NEW);
        taskWithDate3 = BasicTask.createWithStartTime("Задача со временем 2",
                "Описание задачи со временем 2", "22.10.2023 09:00", 15, Status.NEW);
        subtaskWithDate1 = Subtask.createWithStartTime("Subtask 1", "Description 1",
                "20.09.2023 09:00", 50, Status.NEW, 3);
        subtaskWithDate2 = Subtask.createWithStartTime("Subtask 2", "Description 3",
                "21.09.2020 10:00", 20, Status.NEW, 3);
        subtaskWithDate3 = Subtask.createWithStartTime("Subtask 3", "Description 3",
                "10.12.2023 10:00", 13, Status.NEW, 3);
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListAndEmptyHistoryList() {
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("src/resources/recovery_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
        assertEquals(0, restoredManager.getHistory().size());
    }

    @Test
    void restoringEmptyTaskListsShouldReturnEmptyList() {
        FileBackedTasksManager emptyManager = new FileBackedTasksManager("src/resources/empty_test.csv");

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("src/resources/empty_test.csv");

        assertEquals(0, restoredManager.getBasicTaskList().size());
        assertEquals(0, restoredManager.getEpicList().size());
        assertEquals(0, restoredManager.getSubtaskList().size());
        assertEquals(0, restoredManager.getHistory().size());
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListAndHistoryList() {
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.addEpic(epic1);
        manager.getBasicTaskById(task2.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getBasicTaskById(task1.getTaskId());

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("src/resources/recovery_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListWithDateAndEmptyHistoryList() {
        manager.addBasicTask(taskWithDate1);
        manager.addBasicTask(taskWithDate2);
        manager.addEpic(epic1);
        manager.addSubtask(subtaskWithDate1);
        manager.addSubtask(subtaskWithDate2);

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("src/resources/recovery_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
        assertEquals(0, restoredManager.getHistory().size());
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListWithDateAndHistoryList() {
        manager.addBasicTask(taskWithDate1);
        manager.addBasicTask(taskWithDate2);
        manager.addEpic(epic1);
        manager.getBasicTaskById(taskWithDate2.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getBasicTaskById(taskWithDate1.getTaskId());

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("src/resources/recovery_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
    }

}