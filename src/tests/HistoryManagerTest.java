package tests;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    static HistoryManager historyManager;
    static BasicTask task1;
    static BasicTask task2;
    static BasicTask task3;
    static Epic epic1;
    static Subtask subtask1;

    @BeforeAll
    static void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        task1.setTaskId(1);
        task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.DONE);
        task2.setTaskId(2);
        task3 = BasicTask.create("Задача 3", "Описание задачи 3", Status.DONE);
        task3.setTaskId(2);
        epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        epic1.setTaskId(3);
        subtask1 = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.DONE, 3);
        subtask1.setTaskId(4);
    }

    @Test
    void addingTaskShouldIncreaseListBy1() {
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void addingNullTaskShouldThrowException() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> historyManager.add(null));
        assertEquals("Невозможно добавить пустую задачу!", e.getMessage());
    }

    @Test
    void getHistoryShouldReturnAllTasksInList() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        assertIterableEquals(List.of(task1, task2, epic1), historyManager.getHistory());
    }

    @Test
    void addingSameTaskShouldReplaceOldTask() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        assertIterableEquals(List.of(task1, task2, epic1), historyManager.getHistory());
        historyManager.add(task3);
        assertIterableEquals(List.of(task1, epic1, task3), historyManager.getHistory());
    }

    @Test
    void gettingHistoryWithNoTasksShouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), historyManager.getHistory());
    }

    @Test
    void removingElementShouldDecreaseListSize() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        assertEquals(3, historyManager.getHistory().size());
        assertTrue(historyManager.remove(task2.getTaskId()));
        assertEquals(2, historyManager.getHistory().size());
        assertIterableEquals(List.of(task1, epic1), historyManager.getHistory());
    }

    @Test
    void removingFirstElementShouldDecreaseListSize() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        assertEquals(3, historyManager.getHistory().size());
        assertTrue(historyManager.remove(task1.getTaskId()));
        assertEquals(2, historyManager.getHistory().size());
        assertIterableEquals(List.of(task2, epic1), historyManager.getHistory());
    }

    @Test
    void removingLastElementShouldDecreaseListSize() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        assertEquals(3, historyManager.getHistory().size());
        assertTrue(historyManager.remove(epic1.getTaskId()));
        assertEquals(2, historyManager.getHistory().size());
        assertIterableEquals(List.of(task1, task2), historyManager.getHistory());
    }

    @Test
    void removingOnlyElementShouldDecreaseListSize() {
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size());
        assertTrue(historyManager.remove(task1.getTaskId()));
        assertEquals(0, historyManager.getHistory().size());
        assertIterableEquals(Collections.emptyList(), historyManager.getHistory());
    }
}