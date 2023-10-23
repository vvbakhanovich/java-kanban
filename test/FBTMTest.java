import manager.FileBackedTasksManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FBTMTest extends AbstractTaskManagerTest {
    @Override
    @BeforeEach
    void setManager() {
        manager = new FileBackedTasksManager("resources/FBTM_test.csv");
    }

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Override
    @Test
    void addingTaskShouldIncreaseTaskListSize() {
        super.addingTaskShouldIncreaseTaskListSize();
    }

    @Override
    @Test
    void addingNullTaskShouldThrowException() {
        super.addingNullTaskShouldThrowException();
    }

    @Override
    @Test
    void addingEpicShouldIncreaseEpicListSize() {
        super.addingEpicShouldIncreaseEpicListSize();
    }

    @Override
    @Test
    void addingNullEpicShouldThrowException() {
        super.addingNullEpicShouldThrowException();
    }

    @Override
    @Test
    void addingSubtaskShouldIncreaseSubtaskListSize() {
        super.addingSubtaskShouldIncreaseSubtaskListSize();
    }

    @Override
    @Test
    void addingSubtaskWithNoEpicShouldThrowException() {
        super.addingSubtaskWithNoEpicShouldThrowException();
    }

    @Override
    @Test
    void addingNullSubtaskShouldThrowException() {
        super.addingNullSubtaskShouldThrowException();
    }

    @Override
    @Test
    void getShouldReturnTask1() {
        super.getShouldReturnTask1();
    }

    @Override
    @Test
    void getShouldReturnEpic1() {
        super.getShouldReturnEpic1();
    }

    @Override
    @Test
    void getShouldReturnSubtask1() {
        super.getShouldReturnSubtask1();
    }

    @Override
    @Test
    void gettingBasicTaskFromEmptyMapShouldThrowException() {
        super.gettingBasicTaskFromEmptyMapShouldThrowException();
    }

    @Override
    @Test
    void gettingBasicTaskWithWrongIdShouldThrowException() {
        super.gettingBasicTaskWithWrongIdShouldThrowException();
    }

    @Override
    @Test
    void gettingEpicFromEmptyMapShouldThrowException() {
        super.gettingEpicFromEmptyMapShouldThrowException();
    }

    @Override
    @Test
    void gettingEpicWithWrongIdShouldThrowException() {
        super.gettingEpicWithWrongIdShouldThrowException();
    }

    @Override
    @Test
    void gettingSubtaskFromEmptyMapShouldThrowException() {
        super.gettingSubtaskFromEmptyMapShouldThrowException();
    }

    @Override
    @Test
    void gettingSubtaskWithWrongIdShouldThrowException() {
        super.gettingSubtaskWithWrongIdShouldThrowException();
    }

    @Override
    @Test
    void gettingTaskListShouldReturnAllTasks() {
        super.gettingTaskListShouldReturnAllTasks();
    }

    @Override
    @Test
    void gettingEmptyTaskListShouldReturnEmptyList() {
        super.gettingEmptyTaskListShouldReturnEmptyList();
    }

    @Override
    @Test
    void gettingEpicListShouldReturnAllEpic() {
        super.gettingEpicListShouldReturnAllEpic();
    }

    @Override
    @Test
    void gettingEmptyEpicListShouldReturnEmptyList() {
        super.gettingEmptyEpicListShouldReturnEmptyList();
    }

    @Override
    @Test
    void gettingSubtaskListShouldReturnAllTasksInIMTM() {
        super.gettingSubtaskListShouldReturnAllTasksInIMTM();
    }

    @Override
    @Test
    void gettingEmptySubListShouldReturnEmptyList() {
        super.gettingEmptySubListShouldReturnEmptyList();
    }

    @Override
    @Test
    void gettingListOfEpicSubtasksShouldReturnItsSubtasksIdInIMTM() {
        super.gettingListOfEpicSubtasksShouldReturnItsSubtasksIdInIMTM();
    }

    @Override
    void gettingListOfEpicWithNoSubtasksShouldReturnEmptyList() {
        super.gettingListOfEpicWithNoSubtasksShouldReturnEmptyList();
    }

    @Override
    @Test
    void gettingListOfSubtasksOnNullShouldThrowException() {
        super.gettingListOfSubtasksOnNullShouldThrowException();
    }

    @Override
    @Test
    void removingAllTasksShouldMakeTaskListSize0() {
        super.removingAllTasksShouldMakeTaskListSize0();
    }

    @Override
    @Test
    void removingAllEpicsShouldMakeEpicListSize0InIMTM() {
        super.removingAllEpicsShouldMakeEpicListSize0InIMTM();
    }

    @Override
    @Test
    void removingAllSubtasksShouldMakeSubtaskListSize0InIMTM() {
        super.removingAllSubtasksShouldMakeSubtaskListSize0InIMTM();
    }

    @Override
    @Test
    void updatingTaskShouldReplaceOldTaskInIMTM() {
        super.updatingTaskShouldReplaceOldTaskInIMTM();
    }

    @Override
    @Test
    void updatingTaskWithWrongIDShouldThrowExceptionInIMTM() {
        super.updatingTaskWithWrongIDShouldThrowExceptionInIMTM();
    }

    @Override
    @Test
    void updatingEpicShouldReplaceOldEpicInIMTM() {
        super.updatingEpicShouldReplaceOldEpicInIMTM();
    }

    @Override
    @Test
    void updatingEpicWithWrongIDShouldThrowExceptionInIMTM() {
        super.updatingEpicWithWrongIDShouldThrowExceptionInIMTM();
    }

    @Override
    @Test
    void updatingSubtaskShouldReplaceOldSubtaskInIMTM() {
        super.updatingSubtaskShouldReplaceOldSubtaskInIMTM();
    }

    @Override
    @Test
    void updatingSubtaskWithWrongIDShouldThrowExceptionInIMTM() {
        super.updatingSubtaskWithWrongIDShouldThrowExceptionInIMTM();
    }

    @Override
    @Test
    void removingTaskByIdShouldDecreaseTaskListSizeBy1() {
        super.removingTaskByIdShouldDecreaseTaskListSizeBy1();
    }

    @Override
    @Test
    void removingNonExistingTaskShouldThrowException() {
        super.removingNonExistingTaskShouldThrowException();
    }

    @Override
    @Test
    void removingEpicByIdShouldDecreaseEpicListSizeBy1AndDeleteItsSubtasksInIMTM() {
        super.removingEpicByIdShouldDecreaseEpicListSizeBy1AndDeleteItsSubtasksInIMTM();
    }

    @Override
    @Test
    void removingNonExistingEpicShouldThrowException() {
        super.removingNonExistingEpicShouldThrowException();
    }

    @Override
    @Test
    void removingSubtaskByIdShouldDecreaseSubtaskListSizeBy1InIMTM() {
        super.removingSubtaskByIdShouldDecreaseSubtaskListSizeBy1InIMTM();
    }

    @Override
    @Test
    void removingNonExistingSubtaskShouldThrowException() {
        super.removingNonExistingSubtaskShouldThrowException();
    }

    @Override
    @Test
    void gettingHistoryShouldReturnAllTaskAfterGetByIdInIMTM() {
        super.gettingHistoryShouldReturnAllTaskAfterGetByIdInIMTM();
    }

    @Override
    @Test
    void gettingHistoryWithNoTaskShouldReturnEmptyList() {
        super.gettingHistoryWithNoTaskShouldReturnEmptyList();
    }

    @Override
    @Test
    void addingSubtasksWithDoneAndNewStatusesShouldMakeEpicInProgressInIMTM() {
        super.addingSubtasksWithDoneAndNewStatusesShouldMakeEpicInProgressInIMTM();
    }

    @Override
    @Test
    void addingDoneSubtaskShouldMakeEpicDoneInIMTM() {
        super.addingDoneSubtaskShouldMakeEpicDoneInIMTM();
    }

    @Override
    @Test
    void taskShouldAddInStartTimeOrder() {
        super.taskShouldAddInStartTimeOrder();
    }

    @Override
    @Test
    void addingTaskWithCrossingTimeShouldTrowException() {
        super.addingTaskWithCrossingTimeShouldTrowException();
    }

    @Override
    @Test
    void tasksWithNoStartTimeShouldBeInTheEndOfTheList() {
        super.tasksWithNoStartTimeShouldBeInTheEndOfTheList();
    }

    @Override
    @Test
    void EpicStartTimeShouldBeTheSameAsSubtask() {
        super.EpicStartTimeShouldBeTheSameAsSubtask();
    }

    @Override
    @Test
    void EpicStartTimeShouldBeEarliestSubtaskAndEndTimeLatestSubtaskEndTime() {
        super.EpicStartTimeShouldBeEarliestSubtaskAndEndTimeLatestSubtaskEndTime();
    }

    @Override
    @Test
    void removingSubtaskShouldRecalculateTimeOfEpic() {
        super.removingSubtaskShouldRecalculateTimeOfEpic();
    }

    @Override
    @Test
    void removingAllSubtaskFromEpicShouldMakeStarTimeNull() {
        super.removingAllSubtaskFromEpicShouldMakeStarTimeNull();
    }

    @Override
    @Test
    void updatingTaskShouldReplaceOldTimes() {
        super.updatingTaskShouldReplaceOldTimes();
    }

    @Override
    @Test
    void updatingSubtaskShouldRecalculateEpicTimes() {
        super.updatingSubtaskShouldRecalculateEpicTimes();
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListAndEmptyHistoryList() {
        manager.addEpic(epic1);
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("resources/FBTM_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
        assertEquals(0, restoredManager.getHistory().size());
    }

    @Test
    void restoringEmptyTaskListsShouldReturnEmptyList() {
        FileBackedTasksManager emptyManager = new FileBackedTasksManager("resources/empty_test.csv");

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("resources/empty_test.csv");

        assertEquals(0, restoredManager.getBasicTaskList().size());
        assertEquals(0, restoredManager.getEpicList().size());
        assertEquals(0, restoredManager.getSubtaskList().size());
        assertEquals(0, restoredManager.getHistory().size());
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListAndHistoryList() {
        manager.addEpic(epic1);
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.getBasicTaskById(task2.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getBasicTaskById(task1.getTaskId());

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("resources/FBTM_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
    }

    @Test
    void loadingFromFileShouldReturnSameTaskListWithDateAndEmptyHistoryList() {
        manager.addEpic(epic1);
        manager.addBasicTask(taskWithDate1);
        manager.addBasicTask(taskWithDate2);
        manager.addSubtask(subtaskWithDate1);
        manager.addSubtask(subtaskWithDate2);

        FileBackedTasksManager restoredManager = FileBackedTasksManager
                .loadFromFile("resources/FBTM_test.csv");
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
                .loadFromFile("resources/FBTM_test.csv");
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
    }
}