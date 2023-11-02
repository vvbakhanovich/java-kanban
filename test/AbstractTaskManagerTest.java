import exceptions.InvalidTimeException;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTaskManagerTest {

    TaskManager manager;
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
    void setManager() {
        manager = new InMemoryTaskManager();
    }
    @BeforeEach
    void setUp() {

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
        taskWithDate1 = BasicTask.createWithStartTime("Задача со временем 1",
                "Описание задачи со временем 1",
                LocalDateTime.of(2023, 10, 22, 9,9), 23, Status.IN_PROGRESS);
        taskWithDate2 = BasicTask.createWithStartTime("Задача со временем 2",
                "Описание задачи со временем 2",
                LocalDateTime.of(2023, 10, 22, 8,0), 15, Status.NEW);
        taskWithDate3 = BasicTask.createWithStartTime("Задача со временем 2",
                "Описание задачи со временем 2",
                LocalDateTime.of(2023, 10, 22, 9,0), 15, Status.NEW);
        subtaskWithDate1 = Subtask.createWithStartTime("Subtask 1", "Description 1",
                LocalDateTime.of(2023, 9, 20, 9,0), 50, Status.NEW, 1);
        subtaskWithDate2 = Subtask.createWithStartTime("Subtask 2", "Description 3",
                LocalDateTime.of(2020, 9, 21, 10,9), 20, Status.NEW, 1);
        subtaskWithDate3 = Subtask.createWithStartTime("Subtask 3", "Description 3",
                LocalDateTime.of(2023, 12, 10, 10,0), 13, Status.NEW, 1);
    }

    @Test
    void addingTaskShouldIncreaseTaskListSize() {
        assertEquals(0, manager.getBasicTaskList().size(), "Список задач не пуст в IMTM!");
        manager.addBasicTask(task1);
        assertEquals(1, manager.getBasicTaskList().size(), "Задача не была добавлена в IMTM!");
    }

    @Test
    void addingNullTaskShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> manager.addBasicTask(null));
        assertEquals("Попытка добавить пустую задачу.", e1.getMessage());
    }

    @Test
    void addingEpicShouldIncreaseEpicListSize() {
        assertEquals(0, manager.getEpicList().size(), "Список эпиков не пуст в IMTM!");
        manager.addEpic(epic1);
        assertEquals(1, manager.getEpicList().size(), "Эпик не был добавлен в IMTM!");
    }

    @Test
    void addingNullEpicShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> manager.addEpic(null));
        assertEquals("Попытка добавить пустой эпик.", e1.getMessage());
    }

    @Test
    void addingSubtaskShouldIncreaseSubtaskListSize() {
        assertEquals(0, manager.getSubtaskList().size(), "Список подзадач не пуст в IMTM!");
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        assertEquals(1, manager.getSubtaskList().size(), "Подзадача не была добавлена в IMTM!");
    }

    @Test
    void addingSubtaskWithNoEpicShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.addSubtask(subtask1));
        assertEquals("Эпика с id 1 не существует.", e1.getMessage());
    }

    @Test
    void addingNullSubtaskShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> manager.addSubtask(null));
        assertEquals("Попытка добавить пустую подзадачу", e1.getMessage());
    }

    @Test
    void getShouldReturnTask1() {
        long id1 = manager.addBasicTask(task1);
        assertEquals(1, manager.getBasicTaskList().size());
        BasicTask basicTask1 = manager.getBasicTaskById(id1);
        assertEquals("Задача 1", basicTask1.getTaskName());
    }

    @Test
    void getShouldReturnEpic1() {
        long id1 = manager.addEpic(epic1);
        assertEquals(1, manager.getEpicList().size());
        Epic epic = manager.getEpicById(id1);
        assertEquals("Эпик 1", epic.getTaskName());
    }

    @Test
    void getShouldReturnSubtask1() {
        manager.addEpic(epic1);
        long id1 = manager.addSubtask(subtask1);
        assertEquals(1, manager.getSubtaskList().size());
        Subtask subtask = manager.getSubtaskById(id1);
        assertEquals("Подзадача 1", subtask.getTaskName());
    }

    @Test
    void gettingBasicTaskFromEmptyMapShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.getBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e1.getMessage());
    }

    @Test
    void gettingBasicTaskWithWrongIdShouldThrowException() {
        long id1 = manager.addBasicTask(task1);
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.getBasicTaskById(id1 + 10));
        assertEquals("Задачи с id " + (id1 + 10) + " не существует.", e1.getMessage());
    }

    @Test
    void gettingEpicFromEmptyMapShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.getEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e1.getMessage());
    }

    @Test
    void gettingEpicWithWrongIdShouldThrowException() {
        long id1 = manager.addEpic(epic1);
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.getEpicById(id1 + 10));
        assertEquals("Эпика с id " + (id1 + 10) + " не существует.", e1.getMessage());
    }

    @Test
    void gettingSubtaskFromEmptyMapShouldThrowException() {
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.getSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e1.getMessage());
    }

    @Test
    void gettingSubtaskWithWrongIdShouldThrowException() {
        manager.addEpic(epic1);
        long id1 = manager.addSubtask(subtask1);
        NoSuchElementException e1 = assertThrows(NoSuchElementException.class,
                () -> manager.getSubtaskById(id1 + 10));
        assertEquals("Подзадачи с id " + (id1 + 10) + " не существует.", e1.getMessage());
    }

    @Test
    void gettingTaskListShouldReturnAllTasks() {
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        assertIterableEquals(List.of(task1, task2), manager.getBasicTaskList());
    }

    @Test
    void gettingEmptyTaskListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), manager.getBasicTaskList());
    }

    @Test
    void gettingEpicListShouldReturnAllEpic() {
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        assertIterableEquals(List.of(epic1, epic2), manager.getEpicList());
    }

    @Test
    void gettingEmptyEpicListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), manager.getEpicList());
    }

    @Test
    void gettingSubtaskListShouldReturnAllTasksInIMTM() {
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        assertIterableEquals(List.of(subtask1, subtask2, subtask3), manager.getSubtaskList());
    }

    @Test
    void gettingEmptySubListShouldReturnEmptyList() {
        assertIterableEquals(Collections.emptyList(), manager.getSubtaskList());
    }

    @Test
    void gettingListOfEpicSubtasksShouldReturnItsSubtasksIdInIMTM() {
        manager.addEpic(epic1);
        long id1 = manager.addSubtask(subtask1);
        long id2 = manager.addSubtask(subtask2);
        long id3 = manager.addSubtask(subtask3);
        assertEquals(List.of(id1, id2, id3), manager.getEpicSubtaskList(epic1.getTaskId()));
    }

    @Test
    void gettingListOfEpicWithNoSubtasksShouldReturnEmptyList() {
        manager.addEpic(epic1);
        assertEquals(Collections.emptyList(), manager.getEpicSubtaskList(epic1.getTaskId()));
    }

    @Test
    void gettingListOfSubtasksOnNullShouldThrowException() {
        NullPointerException e1 = assertThrows(NullPointerException.class,
                () -> manager.getEpicSubtaskList(0));
        assertEquals("Попытка найти список подзадач несуществующего эпика", e1.getMessage());
    }

    @Test
    void removingAllTasksShouldMakeTaskListSize0() {
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        assertEquals(2, manager.getBasicTaskList().size(), "Несоответствие количества задач!");
        manager.removeAllBasicTasks();
        assertEquals(0, manager.getBasicTaskList().size(), "Не все задачи удалены!");
    }

    @Test
    void removingAllEpicsShouldMakeEpicListSize0InIMTM() {
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addEpic(epic2);
        assertEquals(2, manager.getEpicList().size(), "Несоответствие количества эпиков!");
        assertEquals(2, manager.getSubtaskList().size(), "Несоответствие количества подзадач!");
        manager.removeAllEpics();
        assertEquals(0, manager.getEpicList().size(), "Не все эпики удалены!");
        assertEquals(0, manager.getSubtaskList().size(), "Не все подзадачи удалены!");
    }

    @Test
    void removingAllSubtasksShouldMakeSubtaskListSize0InIMTM() {
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        assertEquals(3, manager.getSubtaskList().size(), "Несоответствие количества подзадач!");
        manager.removeAllEpics();
        assertEquals(0, manager.getSubtaskList().size(), "Не все подзадачи удалены!");
    }

    @Test
    void updatingTaskShouldReplaceOldTaskInIMTM() {
        long id = manager.addBasicTask(task1);
        task2.setTaskId(id);
        assertEquals("Задача 1", manager.getBasicTaskById(id).getTaskName(),
                "Задача не была добавлена!");
        manager.updateBasicTask(task2);
        assertEquals("Задача 2", manager.getBasicTaskById(id).getTaskName(),
                "Задача не была обновлена!");
    }

    @Test
    void updatingTaskWithWrongIDShouldThrowExceptionInIMTM() {
        long id = manager.addBasicTask(task1);
        task2.setTaskId(id + 1);
        assertEquals("Задача 1", manager.getBasicTaskById(id).getTaskName(),
                "Задача не была добавлена!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> manager.updateBasicTask(task2));
        assertEquals("Задачи с id 2 не существует.", e.getMessage());
    }

    @Test
    void updatingEpicShouldReplaceOldEpicInIMTM() {
        long id = manager.addEpic(epic1);
        epic2.setTaskId(id);
        assertEquals("Эпик 1", manager.getEpicById(id).getTaskName(),
                "Эпик не был добавлен!");
        manager.updateEpic(epic2);
        assertEquals("Эпик 2", manager.getEpicById(id).getTaskName(),
                "Эпик не был обновлен!");
    }

    @Test
    void updatingEpicWithWrongIDShouldThrowExceptionInIMTM() {
        long id = manager.addEpic(epic1);
        epic2.setTaskId(id + 1);
        assertEquals("Эпик 1", manager.getEpicById(id).getTaskName(),
                "Эпик не был добавлен!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> manager.updateEpic(epic2));
        assertEquals("Эпика с id 2 не существует.", e.getMessage());
    }

    @Test
    void updatingSubtaskShouldReplaceOldSubtaskInIMTM() {
        manager.addEpic(epic1);
        long id = manager.addSubtask(subtask1);
        subtask2.setTaskId(id);
        assertEquals("Подзадача 1", manager.getSubtaskById(id).getTaskName(),
                "Подзадача не была добавлена!");
        manager.updateSubtask(subtask2);
        assertEquals("Подзадача 2", manager.getSubtaskById(id).getTaskName(),
                "Подзадача не была обновлена!");
    }

    @Test
    void updatingSubtaskWithWrongIDShouldThrowExceptionInIMTM() {
        manager.addEpic(epic1);
        long id = manager.addSubtask(subtask1);
        subtask2.setTaskId(id + 1);
        assertEquals("Подзадача 1", manager.getSubtaskById(id).getTaskName(),
                "Подзадача не была добавлена!");
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> manager.updateSubtask(subtask2));
        assertEquals("Подзадачи с id 3 не существует.", e.getMessage());
    }

    @Test
    void removingTaskByIdShouldDecreaseTaskListSizeBy1() {
        long id = manager.addBasicTask(task1);
        assertEquals(1, manager.getBasicTaskList().size(), "Задача не была добавлена!");
        manager.removeBasicTaskById(id);
        assertEquals(0, manager.getBasicTaskList().size(), "Задача не была удалена!");
    }

    @Test
    void removingNonExistingTaskShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> manager.removeBasicTaskById(1));
        assertEquals("Задачи с id 1 не существует.", e.getMessage());
    }

    @Test
    void removingEpicByIdShouldDecreaseEpicListSizeBy1AndDeleteItsSubtasksInIMTM() {
        long id = manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(1, manager.getEpicList().size(), "Эпик не был добавлен!");
        assertEquals(2, manager.getSubtaskList().size(), "Подзадачи не были добавлены!");
        manager.removeEpicById(id);
        assertEquals(0, manager.getEpicList().size(), "Эпик не был удален!");
        assertEquals(0, manager.getSubtaskList().size(), "Подзадачи не были удалены!");
    }

    @Test
    void removingNonExistingEpicShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> manager.removeEpicById(1));
        assertEquals("Эпика с id 1 не существует.", e.getMessage());
    }

    @Test
    void removingSubtaskByIdShouldDecreaseSubtaskListSizeBy1InIMTM() {
        manager.addEpic(epic1);
        long id = manager.addSubtask(subtask2);
        assertEquals(1, manager.getSubtaskList().size(), "Подзадача не была добавлена!");
        manager.removeSubtaskById(id);
        assertEquals(0, manager.getSubtaskList().size(), "Подзадача не была удалена!");
    }

    @Test
    void removingNonExistingSubtaskShouldThrowException() {
        NoSuchElementException e = assertThrows(NoSuchElementException.class,
                () -> manager.removeSubtaskById(1));
        assertEquals("Подзадачи с id 1 не существует.", e.getMessage());
    }

    @Test
    void gettingHistoryShouldReturnAllTaskAfterGetByIdInIMTM() {
        long epicId = manager.addEpic(epic1);
        long taskId = manager.addBasicTask(task1);
        long subtaskId = manager.addSubtask(subtask2);
        manager.getSubtaskById(subtaskId);
        manager.getBasicTaskById(taskId);
        manager.getEpicById(epicId);
        assertEquals(List.of(subtask2, task1, epic1), manager.getHistory(),
                "Некорректный список истории просмотров!");
    }

    @Test
    void gettingHistoryWithNoTaskShouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), manager.getHistory(), "Список истории просмотров не пуст!");
    }

    @Test
    void addingSubtasksWithDoneAndNewStatusesShouldMakeEpicInProgressInIMTM() {
        long id = manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, manager.getEpicById(id).getStatus(), "Некорректный статус эпика!");
    }

    @Test
    void addingDoneSubtaskShouldMakeEpicDoneInIMTM() {
        long id = manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        assertEquals(Status.DONE, manager.getEpicById(id).getStatus(), "Некорректный статус эпика!");
    }

    @Test
    void taskShouldAddInStartTimeOrder() {
        manager.addBasicTask(taskWithDate1);
        assertEquals(1, manager.getBasicTaskList().size());
        manager.addBasicTask(taskWithDate2);
        assertEquals(2, manager.getBasicTaskList().size());
        assertIterableEquals(List.of(taskWithDate2, taskWithDate1), manager.getPrioritizedTasks());
    }

    @Test
    void addingTaskWithCrossingTimeShouldTrowException() {
        manager.addBasicTask(taskWithDate1);
        assertEquals(1, manager.getBasicTaskList().size());
        InvalidTimeException e = assertThrows(InvalidTimeException.class,
                () -> manager.addBasicTask(taskWithDate3));
        assertEquals("Задачи не должны пересекаться по времени!", e.getMessage());
    }

    @Test
    void tasksWithNoStartTimeShouldBeInTheEndOfTheList() {
        manager.addBasicTask(taskWithDate1);
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.addBasicTask(taskWithDate2);
        assertEquals(4, manager.getBasicTaskList().size());
        assertIterableEquals(List.of(taskWithDate2, taskWithDate1, task1, task2), manager.getPrioritizedTasks());
    }

    @Test
    void EpicStartTimeShouldBeTheSameAsSubtask() {
        manager.addEpic(epic1);
        manager.addSubtask(subtaskWithDate1);
        assertEquals(epic1.getStartTime(), subtaskWithDate1.getStartTime());
        assertEquals(epic1.getEndTime(), subtaskWithDate1.getEndTime());
        assertEquals(epic1.getDuration(), subtaskWithDate1.getDuration());
    }

    @Test
    void EpicStartTimeShouldBeEarliestSubtaskAndEndTimeLatestSubtaskEndTime() {
        manager.addEpic(epic1);
        manager.addSubtask(subtaskWithDate1);
        manager.addSubtask(subtaskWithDate2);
        assertEquals(epic1.getStartTime(), subtaskWithDate2.getStartTime());
        assertEquals(epic1.getEndTime(), subtaskWithDate1.getEndTime());
    }

    @Test
    void removingSubtaskShouldRecalculateTimeOfEpic() {
        manager.addEpic(epic1);
        manager.addSubtask(subtaskWithDate1);
        long id = manager.addSubtask(subtaskWithDate2);
        assertEquals(epic1.getStartTime(), subtaskWithDate2.getStartTime());
        assertEquals(epic1.getEndTime(), subtaskWithDate1.getEndTime());
        manager.removeSubtaskById(id);
        assertEquals(epic1.getStartTime(), subtaskWithDate1.getStartTime());
        assertEquals(epic1.getEndTime(), subtaskWithDate1.getEndTime());
    }

    @Test
    void removingAllSubtaskFromEpicShouldMakeStarTimeNull() {
        manager.addEpic(epic1);
        long id1 = manager.addSubtask(subtaskWithDate1);
        long id2 = manager.addSubtask(subtaskWithDate2);
        assertEquals(epic1.getStartTime(), subtaskWithDate2.getStartTime());
        assertEquals(epic1.getEndTime(), subtaskWithDate1.getEndTime());
        manager.removeSubtaskById(id1);
        manager.removeSubtaskById(id2);
        assertNull(epic1.getStartTime());
        assertNull(epic1.getEndTime());
        assertEquals(0, epic1.getDuration());
    }

    @Test
    void updatingTaskShouldReplaceOldTimes() {
        long id = manager.addBasicTask(taskWithDate1);
        manager.addBasicTask(taskWithDate2);
        taskWithDate3.setTaskId(id);
        manager.updateBasicTask(taskWithDate3);
        assertIterableEquals(List.of(taskWithDate2, taskWithDate3), manager.getPrioritizedTasks());
    }

    @Test
    void updatingSubtaskShouldRecalculateEpicTimes() {
        manager.addEpic(epic1);
        long id = manager.addSubtask(subtaskWithDate1);
        manager.addSubtask(subtaskWithDate2);
        subtaskWithDate3.setTaskId(id);
        manager.updateSubtask(subtaskWithDate3);
        assertEquals("Subtask 3", manager.getSubtaskById(id).getTaskName());
        assertEquals(epic1.getStartTime(), subtaskWithDate2.getStartTime());
        assertEquals(epic1.getEndTime(), subtaskWithDate3.getEndTime());
    }
}

