import Manager.Managers;
import Manager.TaskManager;
import Model.Task;
import Model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {

    @Test
    void shouldAddNewTask() {
        TaskManager taskManager = Managers.getDefaultTask();
        Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        final int taskId = taskManager.addTask(task).getID();

        final Task savedTask = taskManager.getTaskByID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void checkThatClassInstancesAreEqualToEachOther() {
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        Task task2 = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
        task1.setID(0);
        task2.setID(0);
        assertEquals(task1, task2);
    }

}