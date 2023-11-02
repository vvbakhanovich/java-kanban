import http.KVServer;
import manager.HttpTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class HttpTaskManagerTest extends AbstractTaskManagerTest {

    private KVServer server;

    @Override
    @BeforeEach
    void setManager() {
        try {
            server = new KVServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.start();
        manager = new HttpTaskManager("http://localhost:8078/");
    }

    @AfterEach
    void stop() {
        server.stop();
    }

    @Test
    void loadingShouldReturnSameTaskListAndEmptyHistoryList() {
        manager.addEpic(epic1);
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        HttpTaskManager restoredManager = new HttpTaskManager("http://localhost:8078/", true);
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
        assertEquals(0, restoredManager.getHistory().size());
    }

    @Test
    void loadingShouldReturnSameTaskListAndHistoryList() {
        manager.addEpic(epic1);
        manager.addBasicTask(task1);
        manager.addBasicTask(task2);
        manager.getBasicTaskById(task2.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getBasicTaskById(task1.getTaskId());

        HttpTaskManager restoredManager = new HttpTaskManager("http://localhost:8078/", true);
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
    }

    @Test
    void loadingShouldReturnSameTaskListWithDateAndEmptyHistoryList() {
        manager.addEpic(epic1);
        manager.addBasicTask(taskWithDate1);
        manager.addBasicTask(taskWithDate2);
        manager.addSubtask(subtaskWithDate1);
        manager.addSubtask(subtaskWithDate2);

        HttpTaskManager restoredManager = new HttpTaskManager("http://localhost:8078/", true);
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

        HttpTaskManager restoredManager = new HttpTaskManager("http://localhost:8078/", true);
        assertIterableEquals(manager.getBasicTaskList(), restoredManager.getBasicTaskList());
        assertIterableEquals(manager.getEpicList(), restoredManager.getEpicList());
        assertIterableEquals(manager.getSubtaskList(), restoredManager.getSubtaskList());
        assertIterableEquals(manager.getHistory(), restoredManager.getHistory());
    }

}