package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import utility.EpicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EpicServiceTest {

    private static Epic epic;
    private static Map<Long, Subtask> subtaskList;
    private static Subtask subtask1;
    private static Subtask subtask2;
    private static Subtask subtask3;

    @BeforeEach
    void setUp() {
        epic = Epic.create("Epic 1", "Description of epic 1");
        epic.setTaskId(1L);
        subtaskList = new HashMap<>();
        fillSubtaskMap();
    }

    @Test
    void subtaskListSizeShouldBe3AfterBeforeEach() {
        assertEquals(3, epic.getSubtaskList().size());
        assertIterableEquals(List.of(2L, 3L, 4L), epic.getSubtaskList());
    }

    @Test
    void afterRemove1SubtaskListSizeShouldBe2() {
        EpicService.removeEpicSubtask(epic, 3L);
        assertEquals(2, epic.getSubtaskList().size());
        assertIterableEquals(List.of(2L, 4L), epic.getSubtaskList());
    }

    @Test
    void removeAllEpicSubtasksShouldMakeSubtaskListSizeZero() {
        assertEquals(3, epic.getSubtaskList().size());
        EpicService.removeAllEpicSubtasks(epic);
        assertEquals(0, epic.getSubtaskList().size());
    }

    @Test
    void ifAllSubtasksNewEpicShouldBeNew() {
        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    void ifAllSubtasksDoneEpicShouldBeDone() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);

        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void ifAllSubtasksInProgressEpicShouldBeInProgress() {
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.IN_PROGRESS);

        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void ifSomeSubtasksNewAndInProgressEpicShouldBeInProgress() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.NEW);

        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void ifSomeSubtasksInProgressAndDoneEpicShouldBeInProgress() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.DONE);

        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void ifSomeSubtasksNewAndDoneEpicShouldBeInProgress() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        subtask3.setStatus(Status.DONE);

        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void ifSomeSubtasksMixStatusAndDoneEpicShouldBeInProgress() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.DONE);

        EpicService.checkEpicStatus(epic, subtaskList);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    private void fillSubtaskMap() {
        subtask1 = Subtask.create("Subtask 1", "Description 1", Status.NEW, 1);
        subtask1.setTaskId(2L);
        subtask2 = Subtask.create("Subtask 2", "Description 3", Status.NEW, 1);
        subtask2.setTaskId(3L);
        subtask3 = Subtask.create("Subtask 3", "Description 3", Status.NEW, 1);
        subtask3.setTaskId(4L);

        subtaskList.put(subtask1.getTaskId(), subtask1);
        subtaskList.put(subtask2.getTaskId(), subtask2);
        subtaskList.put(subtask3.getTaskId(), subtask3);

        EpicService.addEpicSubtask(epic, subtask1.getTaskId());
        EpicService.addEpicSubtask(epic, subtask2.getTaskId());
        EpicService.addEpicSubtask(epic, subtask3.getTaskId());
    }
}