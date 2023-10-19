package tests;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    static TaskManager memoryManager;
    static TaskManager fileManager;
    static BasicTask task1;
    static BasicTask task2;
    static Epic epic1;
    static Epic epic2;
    static Subtask subtask1;
    static Subtask subtask2;
    static Subtask subtask3;


    @BeforeEach
    void setUp() {
        memoryManager = Managers.getDefault();
        fileManager = Managers.getFileManager();
        task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.DONE);
        epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        subtask1 = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.DONE, 1);
        epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, 1);
        subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.DONE, 1);
    }

    @Test
    void addingTaskShouldIncreaseTaskListSize() {
        assertEquals(0, memoryManager.getBasicTaskList().size());
        memoryManager.addBasicTask(task1);
        assertEquals(1, memoryManager.getBasicTaskList().size());
    }

    @Test
    void addingNullTaskShouldThrowException() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> memoryManager.addBasicTask(null));
        assertEquals("Попытка добавить пустую задачу.", e.getMessage());
    }

    @Test
    void addingEpicShouldIncreaseEpicListSize() {
        assertEquals(0, memoryManager.getEpicList().size());
        memoryManager.addEpic(epic1);
        assertEquals(1, memoryManager.getEpicList().size());
    }

    @Test
    void addingNullEpicShouldThrowException() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> memoryManager.addEpic(null));
        assertEquals("Попытка добавить пустой эпик.", e.getMessage());
    }

    @Test
    void addingSubtaskShouldIncreaseSubtaskListSize() {
        assertEquals(0, memoryManager.getSubtaskList().size());
        memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        assertEquals(1, memoryManager.getSubtaskList().size());
    }

    @Test
    void addingSubtaskWithNoEpicShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.addSubtask(subtask1));
        assertEquals("Эпика с id 1 не существует.", e.getMessage());
    }

    @Test
    void addingNullSubtaskShouldThrowException() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> memoryManager.addSubtask(null));
        assertEquals("Попытка добавить пустую подзадачу", e.getMessage());
    }

    @Test
    void getShouldReturnTask1() {
        long id = memoryManager.addBasicTask(task1);
        assertEquals(1, memoryManager.getBasicTaskList().size());
        BasicTask basicTask = memoryManager.getBasicTaskById(id);
        assertEquals("Задача 1", basicTask.getTaskName());
    }

    @Test
    void getShouldReturnEpic1() {
        long id = memoryManager.addEpic(epic1);
        assertEquals(1, memoryManager.getEpicList().size());
        Epic epic = memoryManager.getEpicById(id);
        assertEquals("Эпик 1", epic.getTaskName());
    }

    @Test
    void getShouldReturnSubtask1() {
        memoryManager.addEpic(epic1);
        long id = memoryManager.addSubtask(subtask1);
        assertEquals(1, memoryManager.getSubtaskList().size());
        Subtask subtask = memoryManager.getSubtaskById(id);
        assertEquals("Подзадача 1", subtask.getTaskName());
    }

    @Test
    void gettingBasicTaskFromEmptyMapShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e.getMessage());
    }

    @Test
    void gettingBasicTaskWithWrongIdShouldThrowException() {
        long id = memoryManager.addBasicTask(task1);
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getBasicTaskById(id + 10));
        assertEquals("Задачи с id " + (id + 10) + " не существует.", e.getMessage());
    }

    @Test
    void gettingEpicFromEmptyMapShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e.getMessage());
    }

    @Test
    void gettingEpicWithWrongIdShouldThrowException() {
        long id = memoryManager.addEpic(epic1);
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getEpicById(id + 10));
        assertEquals("Эпика с id " + (id + 10) + " не существует.", e.getMessage());
    }

    @Test
    void gettingSubtaskFromEmptyMapShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e.getMessage());
    }

    @Test
    void gettingSubtaskWithWrongIdShouldThrowException() {
        memoryManager.addEpic(epic1);
        long id = memoryManager.addSubtask(subtask1);
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getSubtaskById(id + 10));
        assertEquals("Подзадачи с id " + (id + 10) + " не существует.", e.getMessage());
    }

    @Test
    void gettingTaskListShouldReturnAllTasks() {
        memoryManager.addBasicTask(task1);
        memoryManager.addBasicTask(task2);
        assertIterableEquals(List.of(task1, task2), memoryManager.getBasicTaskList());
    }

    @Test
    void gettingEmptyTaskListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), memoryManager.getBasicTaskList());
    }

    @Test
    void gettingEpicListShouldReturnAllEpic() {
        memoryManager.addEpic(epic1);
        memoryManager.addEpic(epic2);
        assertIterableEquals(List.of(epic1, epic2), memoryManager.getEpicList());
    }

    @Test
    void gettingEmptyEpicListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), memoryManager.getEpicList());
    }

    @Test
    void gettingSubtaskListShouldReturnAllTasks() {
        memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        memoryManager.addSubtask(subtask2);
        memoryManager.addSubtask(subtask3);
        assertIterableEquals(List.of(subtask1, subtask2, subtask3), memoryManager.getSubtaskList());
    }

    @Test
    void gettingEmptySubListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), memoryManager.getSubtaskList());
    }

    @Test
    void gettingListOfEpicSubtasksShouldReturnItsSubtasksId() {
        memoryManager.addEpic(epic1);
        long id1 = memoryManager.addSubtask(subtask1);
        long id2 = memoryManager.addSubtask(subtask2);
        long id3 = memoryManager.addSubtask(subtask3);
        assertEquals(List.of(id1, id2, id3), memoryManager.getEpicSubtaskList(epic1));
    }

    @Test
    void gettingListOfEpicWithNoSubtasksShouldReturnEmptyList() {
        memoryManager.addEpic(epic1);
        assertEquals(Collections.emptyList(), memoryManager.getEpicSubtaskList(epic1));
    }

    @Test
    void gettingListOfSubtasksOnNullShouldThrowException() {
        NullPointerException e = assertThrows(NullPointerException.class,
                () -> memoryManager.getEpicSubtaskList(null));
        assertEquals("Попытка найти список подзадач несуществующего эпика", e.getMessage());
    }





}
