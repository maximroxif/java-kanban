import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {

    @Test
    void shouldAddNewTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10));
        final int taskid = taskManager.addTask(task).getid();

        final Task savedTask = taskManager.getTaskByid(taskid);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void checkThatClassInstancesAreEqualToEachOther() {
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10));
        Task task2 = new Task("Test addNewTask", "Test addNewTask description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10));
        task1.setid(0);
        task2.setid(0);
        assertEquals(task1, task2);
    }

}