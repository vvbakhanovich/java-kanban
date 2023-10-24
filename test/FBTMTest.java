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
