import manager.CreateManagers;
import manager.TaskManager;
import manager.TaskNotFoundException;
import model.Epic;
import model.Subtask;
import model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {

    @Test
    public void shouldAddNewEpic() throws TaskNotFoundException {
        TaskManager taskManager = CreateManagers.getDefaultTask();
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpicDescription");
        final int epicid = taskManager.addEpic(epic).getid();

        final Epic savedEpic = taskManager.getEpicByid(epicid);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.getFirst(), "Задачи не совпадают.");
    }

    @Test
    public void checkThatClassInstancesAreEqualToEachOther() {
        Epic epic1 = new Epic("Test addNewEpic", "Test addNewEpic description");
        Epic epic2 = new Epic("Test addNewEpic", "Test addNewEpic description");
        epic1.setid(0);
        epic2.setid(0);
        assertEquals(epic1, epic2);
    }

    @Test
    public void checkEpicStatusAllSubtasksNew() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10), epic.getid());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10), epic.getid());
        epic.addSubTask(subtask);
        epic.addSubTask(subtask2);

        assertEquals(TaskStatus.NEW, epic.getTaskStatus());
    }

    @Test
    public void checkEpicStatusAllSubtasksInProgress() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10), epic.getid());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10), epic.getid());
        subtask.setTaskStatus(TaskStatus.IN_PROGRESS);
        subtask2.setTaskStatus(TaskStatus.IN_PROGRESS);
        epic.addSubTask(subtask);
        epic.addSubTask(subtask2);
        epic.updateStatus();
        assertEquals(TaskStatus.IN_PROGRESS, epic.getTaskStatus());
    }

    @Test
    public void checkEpicStatusAllSubtasksDone() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10), epic.getid());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10), epic.getid());
        subtask.setTaskStatus(TaskStatus.DONE);
        subtask2.setTaskStatus(TaskStatus.DONE);
        epic.addSubTask(subtask);
        epic.addSubTask(subtask2);
        epic.updateStatus();
        assertEquals(TaskStatus.DONE, epic.getTaskStatus());
    }

    @Test
    public void checkEpicStatusAllSubtasksStatusDifferent() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10), epic.getid());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10), epic.getid());
        subtask.setTaskStatus(TaskStatus.NEW);
        subtask2.setTaskStatus(TaskStatus.DONE);
        epic.addSubTask(subtask);
        epic.addSubTask(subtask2);
        epic.updateStatus();
        assertEquals(TaskStatus.IN_PROGRESS, epic.getTaskStatus());
    }

}