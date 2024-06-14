import manager.CreateManagers;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubtaskTest {
    private TaskManager taskManager;

    @Test
    void shouldAddNewSubtask() {
        taskManager = CreateManagers.getDefaultTask();
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description", TaskStatus.NEW, epic.getid());

        final int subtaskid = taskManager.addSubtask(subtask).getid();

        final Subtask savedSubtask = taskManager.getSubTaskByid(subtaskid);

        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtask();

        assertNotNull(subtasks, "Задачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void checkThatClassInstancesAreEqualToEachOther() {
        taskManager = CreateManagers.getDefaultTask();
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", TaskStatus.NEW, epic.getid());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", TaskStatus.NEW, epic.getid());
        subtask1.setid(0);
        subtask2.setid(0);
        assertEquals(subtask1, subtask2);
    }
}