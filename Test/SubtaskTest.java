import Manager.Managers;
import Manager.TaskManager;
import Model.Epic;
import Model.Subtask;
import Model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubtaskTest {
    private TaskManager taskManager;

    @Test
    void shouldAddNewSubtask() {
        taskManager = Managers.getDefaultTask();
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Test addNewSubtask", "Test addNewSubtask description", TaskStatus.NEW, epic.getID());

        final int subtaskId = taskManager.addSubtask(subtask).getID();

        final Subtask savedSubtask = taskManager.getSubTaskByID(subtaskId);

        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final List<Subtask> subtasks = taskManager.getAllSubtask();

        assertNotNull(subtasks, "Задачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void checkThatClassInstancesAreEqualToEachOther() {
        taskManager = Managers.getDefaultTask();
        Epic epic = new Epic("Test addNewSubtask", "Test addNewSubtask description");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", TaskStatus.NEW, epic.getID());
        Subtask subtask2 = new Subtask("Test addNewSubtask", "Test addNewSubtask description", TaskStatus.NEW, epic.getID());
        subtask1.setID(0);
        subtask2.setID(0);
        assertEquals(subtask1, subtask2);
    }
}