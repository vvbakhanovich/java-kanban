import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import utility.EpicService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class EpicServiceTest {

    private Epic epic;
    private Epic epicWithDate;
    private Map<Long, Subtask> subtaskList;
    private Subtask subtask1;
    private Subtask subtask2;
    private Subtask subtask3;
    private Subtask subtaskWithDate1;
    private Subtask subtaskWithDate2;
    private Subtask subtaskWithDate3;

    @BeforeEach
    void setUp() {
        epic = Epic.create("Epic 1", "Description of epic 1");
        epicWithDate = Epic.create("EpicWithDate 1", "Description of epicWithDate 1");
        epic.setTaskId(1L);
        epicWithDate.setTaskId(2L);
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
        EpicService.removeAllEpicSubtasks(epic, subtaskList);
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

    @Test
    void getStartTimeShouldReturnEarliestTime() {
        LocalDateTime result = LocalDateTime.of(2020, 9, 21, 10, 0);
        EpicService.getEpicTimes(epicWithDate, subtaskList);
        assertEquals(result, epicWithDate.getStartTime());
    }

    @Test
    void getEndTimeShouldReturnLatestTime() {
        LocalDateTime result = LocalDateTime.of(2023, 12, 10, 10, 13);
        EpicService.getEpicTimes(epicWithDate, subtaskList);
        assertEquals(result, epicWithDate.getEndTime());
    }

    @Test
    void getDurationShouldReturnNumberOfMinutesBetweenStartAndEnd() {
        long result = Duration.between(subtaskWithDate2.getStartTime(), subtaskWithDate3.getEndTime()).toMinutes();
        EpicService.getEpicTimes(epicWithDate, subtaskList);
        assertEquals(result, epicWithDate.getDuration());
    }


    private void fillSubtaskMap() {
        subtask1 = Subtask.create("Subtask 1", "Description 1", Status.NEW, 1);
        subtask1.setTaskId(2L);
        subtask2 = Subtask.create("Subtask 2", "Description 3", Status.NEW, 1);
        subtask2.setTaskId(3L);
        subtask3 = Subtask.create("Subtask 3", "Description 3", Status.NEW, 1);
        subtask3.setTaskId(4L);
        subtaskWithDate1 = Subtask.createWithStartTime("Subtask 1", "Description 1",
                "20.09.2023 09:00", 50, Status.NEW, 2);
        subtaskWithDate1.setTaskId(5L);
        subtaskWithDate2 = Subtask.createWithStartTime("Subtask 2", "Description 3",
                "21.09.2020 10:00", 20, Status.NEW, 2);
        subtaskWithDate2.setTaskId(6L);
        subtaskWithDate3 = Subtask.createWithStartTime("Subtask 3", "Description 3",
                "10.12.2023 10:00", 13, Status.NEW, 2);
        subtaskWithDate3.setTaskId(7L);


        subtaskList.put(subtask1.getTaskId(), subtask1);
        subtaskList.put(subtask2.getTaskId(), subtask2);
        subtaskList.put(subtask3.getTaskId(), subtask3);
        subtaskList.put(subtaskWithDate1.getTaskId(), subtaskWithDate1);
        subtaskList.put(subtaskWithDate2.getTaskId(), subtaskWithDate2);
        subtaskList.put(subtaskWithDate3.getTaskId(), subtaskWithDate3);

        EpicService.addEpicSubtask(epic, subtask1.getTaskId());
        EpicService.addEpicSubtask(epic, subtask2.getTaskId());
        EpicService.addEpicSubtask(epic, subtask3.getTaskId());
        EpicService.addEpicSubtask(epicWithDate, subtaskWithDate1.getTaskId());
        EpicService.addEpicSubtask(epicWithDate, subtaskWithDate2.getTaskId());
        EpicService.addEpicSubtask(epicWithDate, subtaskWithDate3.getTaskId());
    }
}