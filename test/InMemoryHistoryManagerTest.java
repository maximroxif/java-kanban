import manager.CreateManagers;
import manager.TaskManager;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = CreateManagers.getDefaultTask();
    }

    @Test
    public void checkingThatTheAddedTasksRetainThePreviousVersionOfTheTask() {
        Task task = new Task("Model.Task222", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 00), Duration.ofMinutes(0));
        taskManager.addTask(task);
        taskManager.getTaskByid(task.getid());

        List<Task> savedHistory = taskManager.getHistory();
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);
        taskManager.getTaskByid(task.getid());

        assertEquals(savedHistory, taskManager.getHistory());
    }

    @Test
    public void checkingTheImmutabilityOfTheTaskInAllFieldsAndTheManagerDoesNotExceed10() {
        Task task = new Task("Model.Task", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10));
        Task task2 = new Task("Model.Task 2", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10));
        Task task3 = new Task("Model.Task 3", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 32), Duration.ofMinutes(10));
        Task task4 = new Task("Model.Task 4", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 43), Duration.ofMinutes(10));
        Task task5 = new Task("Model.Task 5", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 54), Duration.ofMinutes(10));
        Task task6 = new Task("Model.Task 6", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 13, 5), Duration.ofMinutes(10));
        Task task7 = new Task("Model.Task 7", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 13, 16), Duration.ofMinutes(10));
        Task task8 = new Task("Model.Task 8", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 13, 27), Duration.ofMinutes(10));
        Task task9 = new Task("Model.Task 9", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 13, 38), Duration.ofMinutes(10));
        Task task10 = new Task("Model.Task 10", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 13, 49), Duration.ofMinutes(10));
        taskManager.addTask(task);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addTask(task5);
        taskManager.addTask(task6);
        taskManager.addTask(task7);
        taskManager.addTask(task8);
        taskManager.addTask(task9);
        taskManager.addTask(task10);

        List<Task> savedTasks = new ArrayList<>();
        savedTasks.add(task);
        savedTasks.add(task2);
        savedTasks.add(task3);
        savedTasks.add(task4);
        savedTasks.add(task5);
        savedTasks.add(task6);
        savedTasks.add(task7);
        savedTasks.add(task8);
        savedTasks.add(task9);
        savedTasks.add(task10);

        for (int i = 1; i <= savedTasks.size() + 1; i++) {
            taskManager.getTaskByid(i);
        }
        assertEquals(taskManager.getHistory(), savedTasks);
        taskManager.getTaskByid(2);
        assertNotEquals(taskManager.getHistory(), savedTasks);
    }

}