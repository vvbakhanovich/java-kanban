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

    TaskManager memoryManager;
    TaskManager fileManager;
    BasicTask task1;
    BasicTask task2;
    Epic epic1;
    Epic epic2;
    Subtask subtask1;
    Subtask subtask2;
    Subtask subtask3;


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
        assertEquals(0, memoryManager.getBasicTaskList().size(), "Список задач не пуст в IMTM!");
        memoryManager.addBasicTask(task1);
        assertEquals(1, memoryManager.getBasicTaskList().size(), "Задача не была добавлена в IMTM!");

        assertEquals(0, fileManager.getBasicTaskList().size(), "Список задач не пуст в FBTM!");
        fileManager.addBasicTask(task1);
        assertEquals(1, fileManager.getBasicTaskList().size(), "Задача не была добавлена в FBTM!");
    }

    @Test
    void addingNullTaskShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> memoryManager.addBasicTask(null));
        assertEquals("Попытка добавить пустую задачу.", e1.getMessage());

        NullPointerException e2 = assertThrows(NullPointerException.class,
                () -> fileManager.addBasicTask(null));
        assertEquals("Попытка добавить пустую задачу.", e2.getMessage());
    }

    @Test
    void addingEpicShouldIncreaseEpicListSize() {
        assertEquals(0, memoryManager.getEpicList().size(), "Список эпиков не пуст в IMTM!");
        memoryManager.addEpic(epic1);
        assertEquals(1, memoryManager.getEpicList().size(), "Эпик не был добавлен в IMTM!");

        assertEquals(0, fileManager.getEpicList().size(), "Список эпиков не пуст в FBTM!");
        fileManager.addEpic(epic1);
        assertEquals(1, fileManager.getEpicList().size(), "Эпик не был добавлен в FBTM!");
    }

    @Test
    void addingNullEpicShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> memoryManager.addEpic(null));
        assertEquals("Попытка добавить пустой эпик.", e1.getMessage());

        NullPointerException e2 = assertThrows(NullPointerException.class,
                () -> fileManager.addEpic(null));
        assertEquals("Попытка добавить пустой эпик.", e2.getMessage());
    }

    @Test
    void addingSubtaskShouldIncreaseSubtaskListSize() {
        assertEquals(0, memoryManager.getSubtaskList().size(), "Список подзадач не пуст в IMTM!");
        memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        assertEquals(1, memoryManager.getSubtaskList().size(), "Подзадача не была добавлена в IMTM!");

        assertEquals(0, fileManager.getSubtaskList().size(), "Список подзадач не пуст в FBTM!");
        fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        assertEquals(1, fileManager.getSubtaskList().size(), "Подзадача не была добавлена в FBTM!");
    }

    @Test
    void addingSubtaskWithNoEpicShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.addSubtask(subtask1));
        assertEquals("Эпика с id 1 не существует.", e1.getMessage());

        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.addSubtask(subtask1));
        assertEquals("Эпика с id 1 не существует.", e2.getMessage());
    }

    @Test
    void addingNullSubtaskShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> memoryManager.addSubtask(null));
        assertEquals("Попытка добавить пустую подзадачу", e1.getMessage());

        NullPointerException e2 = assertThrows(NullPointerException.class,
                () -> fileManager.addSubtask(null));
        assertEquals("Попытка добавить пустую подзадачу", e2.getMessage());
    }

    @Test
    void getShouldReturnTask1() {
        long id1 = memoryManager.addBasicTask(task1);
        assertEquals(1, memoryManager.getBasicTaskList().size());
        BasicTask basicTask1 = memoryManager.getBasicTaskById(id1);
        assertEquals("Задача 1", basicTask1.getTaskName());

        long id2 = fileManager.addBasicTask(task1);
        assertEquals(1, fileManager.getBasicTaskList().size());
        BasicTask basicTask2 = fileManager.getBasicTaskById(id2);
        assertEquals("Задача 1", basicTask2.getTaskName());
    }

    @Test
    void getShouldReturnEpic1() {
        long id1 = memoryManager.addEpic(epic1);
        assertEquals(1, memoryManager.getEpicList().size());
        Epic epic = memoryManager.getEpicById(id1);
        assertEquals("Эпик 1", epic.getTaskName());

        long id2 = fileManager.addEpic(epic1);
        assertEquals(1, fileManager.getEpicList().size());
        Epic epicV2 = fileManager.getEpicById(id2);
        assertEquals("Эпик 1", epicV2.getTaskName());
    }

    @Test
    void getShouldReturnSubtask1() {
        memoryManager.addEpic(epic1);
        long id1 = memoryManager.addSubtask(subtask1);
        assertEquals(1, memoryManager.getSubtaskList().size());
        Subtask subtask = memoryManager.getSubtaskById(id1);
        assertEquals("Подзадача 1", subtask.getTaskName());

        fileManager.addEpic(epic1);
        long id2 = fileManager.addSubtask(subtask1);
        assertEquals(1, fileManager.getSubtaskList().size());
        Subtask subtaskV2 = fileManager.getSubtaskById(id2);
        assertEquals("Подзадача 1", subtaskV2.getTaskName());
    }

    @Test
    void gettingBasicTaskFromEmptyMapShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e1.getMessage());

        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> fileManager.getBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e2.getMessage());
    }

    @Test
    void gettingBasicTaskWithWrongIdShouldThrowException() {
        long id1 = memoryManager.addBasicTask(task1);
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getBasicTaskById(id1 + 10));
        assertEquals("Задачи с id " + (id1 + 10) + " не существует.", e1.getMessage());

        long id2 = fileManager.addBasicTask(task1);
        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> fileManager.getBasicTaskById(id2 + 10));
        assertEquals("Задачи с id " + (id2 + 10) + " не существует.", e2.getMessage());
    }

    @Test
    void gettingEpicFromEmptyMapShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e1.getMessage());

        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> fileManager.getEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e2.getMessage());
    }

    @Test
    void gettingEpicWithWrongIdShouldThrowException() {
        long id1 = memoryManager.addEpic(epic1);
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getEpicById(id1 + 10));
        assertEquals("Эпика с id " + (id1 + 10) + " не существует.", e1.getMessage());

        long id2 = fileManager.addEpic(epic1);
        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> fileManager.getEpicById(id2 + 10));
        assertEquals("Эпика с id " + (id2 + 10) + " не существует.", e2.getMessage());
    }

    @Test
    void gettingSubtaskFromEmptyMapShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e1.getMessage());

        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> fileManager.getSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e2.getMessage());
    }

    @Test
    void gettingSubtaskWithWrongIdShouldThrowException() {
        memoryManager.addEpic(epic1);
        long id1 = memoryManager.addSubtask(subtask1);
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> memoryManager.getSubtaskById(id1 + 10));
        assertEquals("Подзадачи с id " + (id1 + 10) + " не существует.", e1.getMessage());

        fileManager.addEpic(epic1);
        long id2 = fileManager.addSubtask(subtask1);
        NoSuchElementException e2 = assertThrows(NoSuchElementException.class,
                () -> fileManager.getSubtaskById(id2 + 10));
        assertEquals("Подзадачи с id " + (id2 + 10) + " не существует.", e2.getMessage());
    }

    @Test
    void gettingTaskListShouldReturnAllTasks() {
        memoryManager.addBasicTask(task1);
        memoryManager.addBasicTask(task2);
        assertIterableEquals(List.of(task1, task2), memoryManager.getBasicTaskList());

        fileManager.addBasicTask(task1);
        fileManager.addBasicTask(task2);
        assertIterableEquals(List.of(task1, task2), fileManager.getBasicTaskList());
    }

    @Test
    void gettingEmptyTaskListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), memoryManager.getBasicTaskList());

        assertIterableEquals(Collections.emptyList(), fileManager.getBasicTaskList());
    }

    @Test
    void gettingEpicListShouldReturnAllEpic() {
        memoryManager.addEpic(epic1);
        memoryManager.addEpic(epic2);
        assertIterableEquals(List.of(epic1, epic2), memoryManager.getEpicList());

        fileManager.addEpic(epic1);
        fileManager.addEpic(epic2);
        assertIterableEquals(List.of(epic1, epic2), fileManager.getEpicList());
    }

    @Test
    void gettingEmptyEpicListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), memoryManager.getEpicList());

        assertIterableEquals(Collections.emptyList(), fileManager.getEpicList());
    }

    @Test
    void gettingSubtaskListShouldReturnAllTasksInIMTM() {
        memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        memoryManager.addSubtask(subtask2);
        memoryManager.addSubtask(subtask3);
        assertIterableEquals(List.of(subtask1, subtask2, subtask3), memoryManager.getSubtaskList());
    }

    @Test
    void gettingSubtaskListShouldReturnAllTasksInFBTM() {
        fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);
        fileManager.addSubtask(subtask3);
        assertIterableEquals(List.of(subtask1, subtask2, subtask3), fileManager.getSubtaskList());
    }

    @Test
    void gettingEmptySubListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), memoryManager.getSubtaskList());

        assertIterableEquals(Collections.emptyList(), fileManager.getSubtaskList());
    }

    @Test
    void gettingListOfEpicSubtasksShouldReturnItsSubtasksIdInIMTM() {
        memoryManager.addEpic(epic1);
        long id1 = memoryManager.addSubtask(subtask1);
        long id2 = memoryManager.addSubtask(subtask2);
        long id3 = memoryManager.addSubtask(subtask3);
        assertEquals(List.of(id1, id2, id3), memoryManager.getEpicSubtaskList(epic1));
    }

    @Test
    void gettingListOfEpicSubtasksShouldReturnItsSubtasksIdInFBTM() {
        fileManager.addEpic(epic1);
        long id1 = fileManager.addSubtask(subtask1);
        long id2 = fileManager.addSubtask(subtask2);
        long id3 = fileManager.addSubtask(subtask3);
        assertEquals(List.of(id1, id2, id3), fileManager.getEpicSubtaskList(epic1));
    }

    @Test
    void gettingListOfEpicWithNoSubtasksShouldReturnEmptyList() {
        memoryManager.addEpic(epic1);
        assertEquals(Collections.emptyList(), memoryManager.getEpicSubtaskList(epic1));

        fileManager.addEpic(epic1);
        assertEquals(Collections.emptyList(), fileManager.getEpicSubtaskList(epic1));
    }

    @Test
    void gettingListOfSubtasksOnNullShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> memoryManager.getEpicSubtaskList(null));
        assertEquals("Попытка найти список подзадач несуществующего эпика", e1.getMessage());

        NullPointerException e2 = assertThrows(NullPointerException.class,
                () -> fileManager.getEpicSubtaskList(null));
        assertEquals("Попытка найти список подзадач несуществующего эпика", e2.getMessage());
    }

    @Test
    void removingAllTasksShouldMakeTaskListSize0() {
        memoryManager.addBasicTask(task1);
        memoryManager.addBasicTask(task2);
        assertEquals(2, memoryManager.getBasicTaskList().size(), "Несоответствие количества задач!");
        memoryManager.removeAllBasicTasks();
        assertEquals(0, memoryManager.getBasicTaskList().size(), "Не все задачи удалены!");

        fileManager.addBasicTask(task1);
        fileManager.addBasicTask(task2);
        assertEquals(2, fileManager.getBasicTaskList().size(), "Несоответствие количества задач!");
        fileManager.removeAllBasicTasks();
        assertEquals(0, fileManager.getBasicTaskList().size(), "Не все задачи удалены!");
    }

    @Test
    void removingAllEpicsShouldMakeEpicListSize0InIMTM() {
        memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        memoryManager.addSubtask(subtask2);
        memoryManager.addEpic(epic2);
        assertEquals(2, memoryManager.getEpicList().size(), "Несоответствие количества эпиков!");
        assertEquals(2, memoryManager.getSubtaskList().size(), "Несоответствие количества подзадач!");
        memoryManager.removeAllEpics();
        assertEquals(0, memoryManager.getEpicList().size(), "Не все эпики удалены!");
        assertEquals(0, memoryManager.getSubtaskList().size(), "Не все подзадачи удалены!");
    }

    @Test
    void removingAllEpicsShouldMakeEpicListSize0InFBTM() {
        fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);
        fileManager.addEpic(epic2);
        assertEquals(2, fileManager.getEpicList().size(), "Несоответствие количества эпиков!");
        assertEquals(2, fileManager.getSubtaskList().size(), "Несоответствие количества подзадач!");
        fileManager.removeAllEpics();
        assertEquals(0, fileManager.getEpicList().size(), "Не все эпики удалены!");
        assertEquals(0, fileManager.getSubtaskList().size(), "Не все подзадачи удалены!");
    }

    @Test
    void removingAllSubtasksShouldMakeSubtaskListSize0InIMTM() {
        memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        memoryManager.addSubtask(subtask2);
        memoryManager.addSubtask(subtask3);
        assertEquals(3, memoryManager.getSubtaskList().size(), "Несоответствие количества подзадач!");
        memoryManager.removeAllEpics();
        assertEquals(0, memoryManager.getSubtaskList().size(), "Не все подзадачи удалены!");
    }

    @Test
    void removingAllSubtasksShouldMakeSubtaskListSize0InFBTM() {
        fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);
        fileManager.addSubtask(subtask3);
        assertEquals(3, fileManager.getSubtaskList().size(), "Несоответствие количества подзадач!");
        fileManager.removeAllEpics();
        assertEquals(0, fileManager.getSubtaskList().size(), "Не все подзадачи удалены!");
    }

    @Test
    void updatingTaskShouldReplaceOldTaskInIMTM() {
        long id = memoryManager.addBasicTask(task1);
        task2.setTaskId(id);
        assertEquals("Задача 1", memoryManager.getBasicTaskById(id).getTaskName(),
                "Задача не была добавлена!");
        memoryManager.updateBasicTask(task2);
        assertEquals("Задача 2", memoryManager.getBasicTaskById(id).getTaskName(),
                "Задача не была обновлена!");
    }

    @Test
    void updatingTaskShouldReplaceOldTaskInFBTM() {
        long id = fileManager.addBasicTask(task1);
        task2.setTaskId(id);
        assertEquals("Задача 1", fileManager.getBasicTaskById(id).getTaskName(),
                "Задача не была добавлена!");
        fileManager.updateBasicTask(task2);
        assertEquals("Задача 2", fileManager.getBasicTaskById(id).getTaskName(),
                "Задача не была обновлена!");
    }

    @Test
    void updatingTaskWithWrongIDShouldThrowExceptionInIMTM() {
        long id = memoryManager.addBasicTask(task1);
        task2.setTaskId(id + 1);
        assertEquals("Задача 1", memoryManager.getBasicTaskById(id).getTaskName(),
                "Задача не была добавлена!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.updateBasicTask(task2));
        assertEquals("Задачи с id 2 не существует.", e.getMessage());
    }

    @Test
    void updatingTaskWithWrongIDShouldThrowExceptionInFBTM() {
        long id = fileManager.addBasicTask(task1);
        task2.setTaskId(id + 1);
        assertEquals("Задача 1", fileManager.getBasicTaskById(id).getTaskName(),
                "Задача не была добавлена!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> fileManager.updateBasicTask(task2));
        assertEquals("Задачи с id 2 не существует.", e.getMessage());
    }

    @Test
    void updatingEpicShouldReplaceOldEpicInIMTM() {
        long id = memoryManager.addEpic(epic1);
        epic2.setTaskId(id);
        assertEquals("Эпик 1", memoryManager.getEpicById(id).getTaskName(),
                "Эпик не был добавлен!");
        memoryManager.updateEpic(epic2);
        assertEquals("Эпик 2", memoryManager.getEpicById(id).getTaskName(),
                "Эпик не был обновлен!");
    }

    @Test
    void updatingEpicShouldReplaceOldEpicInFBTM() {
        long id = fileManager.addEpic(epic1);
        epic2.setTaskId(id);
        assertEquals("Эпик 1", fileManager.getEpicById(id).getTaskName(),
                "Эпик не был добавлен!");
        fileManager.updateEpic(epic2);
        assertEquals("Эпик 2", fileManager.getEpicById(id).getTaskName(),
                "Эпик не был обновлен!");
    }

    @Test
    void updatingEpicWithWrongIDShouldThrowExceptionInIMTM() {
        long id = memoryManager.addEpic(epic1);
        epic2.setTaskId(id + 1);
        assertEquals("Эпик 1", memoryManager.getEpicById(id).getTaskName(),
                "Эпик не был добавлен!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.updateEpic(epic2));
        assertEquals("Эпика с id 2 не существует.", e.getMessage());
    }

    @Test
    void updatingEpicWithWrongIDShouldThrowExceptionInFBTM() {
        long id = fileManager.addEpic(epic1);
        epic2.setTaskId(id + 1);
        assertEquals("Эпик 1", fileManager.getEpicById(id).getTaskName(),
                "Эпик не был добавлен!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> fileManager.updateEpic(epic2));
        assertEquals("Эпика с id 2 не существует.", e.getMessage());
    }

    @Test
    void updatingSubtaskShouldReplaceOldSubtaskInIMTM() {
        memoryManager.addEpic(epic1);
        long id = memoryManager.addSubtask(subtask1);
        subtask2.setTaskId(id);
        assertEquals("Подзадача 1", memoryManager.getSubtaskById(id).getTaskName(),
                "Подзадача не была добавлена!");
        memoryManager.updateSubtask(subtask2);
        assertEquals("Подзадача 2", memoryManager.getSubtaskById(id).getTaskName(),
                "Подзадача не была обновлена!");
    }

    @Test
    void updatingSubtaskShouldReplaceOldSubtaskInFBTM() {
        fileManager.addEpic(epic1);
        long id = fileManager.addSubtask(subtask1);
        subtask2.setTaskId(id);
        assertEquals("Подзадача 1", fileManager.getSubtaskById(id).getTaskName(),
                "Подзадача не была добавлена!");
        fileManager.updateSubtask(subtask2);
        assertEquals("Подзадача 2", fileManager.getSubtaskById(id).getTaskName(),
                "Подзадача не была обновлена!");
    }

    @Test
    void updatingSubtaskWithWrongIDShouldThrowExceptionInIMTM() {
        memoryManager.addEpic(epic1);
        long id = memoryManager.addSubtask(subtask1);
        subtask2.setTaskId(id + 1);
        assertEquals("Подзадача 1", memoryManager.getSubtaskById(id).getTaskName(),
                "Подзадача не была добавлена!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.updateSubtask(subtask2));
        assertEquals("Подзадачи с id 3 не существует.", e.getMessage());
    }

    @Test
    void updatingSubtaskWithWrongIDShouldThrowExceptionInFBTM() {
        fileManager.addEpic(epic1);
        long id = fileManager.addSubtask(subtask1);
        subtask2.setTaskId(id + 1);
        assertEquals("Подзадача 1", fileManager.getSubtaskById(id).getTaskName(),
                "Подзадача не была добавлена!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> fileManager.updateSubtask(subtask2));
        assertEquals("Подзадачи с id 3 не существует.", e.getMessage());
    }


    @Test
    void removingTaskByIdShouldDecreaseTaskListSizeBy1() {
        long id = memoryManager.addBasicTask(task1);
        assertEquals(1, memoryManager.getBasicTaskList().size(), "Задача не была добавлена!");
        memoryManager.removeBasicTaskById(id);
        assertEquals(0, memoryManager.getBasicTaskList().size(), "Задача не была удалена!");

        long id1 = fileManager.addBasicTask(task1);
        assertEquals(1, fileManager.getBasicTaskList().size(), "Задача не была добавлена!");
        fileManager.removeBasicTaskById(id1);
        assertEquals(0, fileManager.getBasicTaskList().size(), "Задача не была удалена!");
    }

    @Test
    void removingNonExistingTaskShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.removeBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e.getMessage());

        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> fileManager.removeBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e1.getMessage());
    }

    @Test
    void removingEpicByIdShouldDecreaseEpicListSizeBy1AndDeleteItsSubtasksInIMTM() {
        long id = memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        memoryManager.addSubtask(subtask2);
        assertEquals(1, memoryManager.getEpicList().size(), "Эпик не был добавлен!");
        assertEquals(2, memoryManager.getSubtaskList().size(), "Подзадачи не были добавлены!");
        memoryManager.removeEpicById(id);
        assertEquals(0, memoryManager.getEpicList().size(), "Эпик не был удален!");
        assertEquals(0, memoryManager.getSubtaskList().size(), "Подзадачи не были удалены!");
    }

    @Test
    void removingEpicByIdShouldDecreaseEpicListSizeBy1AndDeleteItsSubtasksInFBTM() {
        long id = fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);
        assertEquals(1, fileManager.getEpicList().size(), "Эпик не был добавлен!");
        assertEquals(2, fileManager.getSubtaskList().size(), "Подзадачи не были добавлены!");
        fileManager.removeEpicById(id);
        assertEquals(0, fileManager.getEpicList().size(), "Эпик не был удален!");
        assertEquals(0, fileManager.getSubtaskList().size(), "Подзадачи не были удалены!");
    }

    @Test
    void removingNonExistingEpicShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.removeEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e.getMessage());

        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> fileManager.removeEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e1.getMessage());
    }

    @Test
    void removingSubtaskByIdShouldDecreaseSubtaskListSizeBy1InIMTM() {
        memoryManager.addEpic(epic1);
        long id = memoryManager.addSubtask(subtask2);
        assertEquals(1, memoryManager.getSubtaskList().size(), "Подзадача не была добавлена!");
        memoryManager.removeSubtaskById(id);
        assertEquals(0, memoryManager.getSubtaskList().size(), "Подзадача не была удалена!");
    }

    @Test
    void removingSubtaskByIdShouldDecreaseSubtaskListSizeBy1InFBTM() {
        fileManager.addEpic(epic1);
        long id = fileManager.addSubtask(subtask2);
        assertEquals(1, fileManager.getSubtaskList().size(), "Подзадача не была добавлена!");
        fileManager.removeSubtaskById(id);
        assertEquals(0, fileManager.getSubtaskList().size(), "Подзадача не была удалена!");
    }

    @Test
    void removingNonExistingSubtaskShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> memoryManager.removeSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e.getMessage());

        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> fileManager.removeSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e1.getMessage());
    }

    @Test
    void gettingHistoryShouldReturnAllTaskAfterGetByIdInIMTM() {
        long epicId = memoryManager.addEpic(epic1);
        long taskId = memoryManager.addBasicTask(task1);
        long subtaskId = memoryManager.addSubtask(subtask2);
        memoryManager.getSubtaskById(subtaskId);
        memoryManager.getBasicTaskById(taskId);
        memoryManager.getEpicById(epicId);
        assertEquals(List.of(subtask2, task1, epic1), memoryManager.getHistory(),
                "Некорректный список истории просмотров!");
    }

    @Test
    void gettingHistoryShouldReturnAllTaskAfterGetByIdInFBTM() {
        long epicId = fileManager.addEpic(epic1);
        long taskId = fileManager.addBasicTask(task1);
        long subtaskId = fileManager.addSubtask(subtask2);
        fileManager.getSubtaskById(subtaskId);
        fileManager.getBasicTaskById(taskId);
        fileManager.getEpicById(epicId);
        assertEquals(List.of(subtask2, task1, epic1), fileManager.getHistory(),
                "Некорректный список истории просмотров!");
    }

    @Test
    void gettingHistoryWithNoTaskShouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), memoryManager.getHistory(), "Список истории просмотров не пуст!");

        assertEquals(Collections.emptyList(), fileManager.getHistory(), "Список истории просмотров не пуст!");

    }

    @Test
    void addingSubtasksWithDoneAndNewStatusesShouldMakeEpicInProgressInIMTM() {
        long id = memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        memoryManager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, memoryManager.getEpicById(id).getStatus(), "Некорректный статус эпика!");

    }

    @Test
    void addingSubtasksWithDoneAndNewStatusesShouldMakeEpicInProgressInFBTM() {
        long id = fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, fileManager.getEpicById(id).getStatus(), "Некорректный статус эпика!");

    }

    @Test
    void addingDoneSubtaskShouldMakeEpicDoneInIMTM() {
        long id = memoryManager.addEpic(epic1);
        memoryManager.addSubtask(subtask1);
        assertEquals(Status.DONE, memoryManager.getEpicById(id).getStatus(), "Некорректный статус эпика!");
    }

    @Test
    void addingDoneSubtaskShouldMakeEpicDoneInFBTM() {
        long id = fileManager.addEpic(epic1);
        fileManager.addSubtask(subtask1);
        assertEquals(Status.DONE, fileManager.getEpicById(id).getStatus(), "Некорректный статус эпика!");
    }


}
