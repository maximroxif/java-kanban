import Manager.Managers;
import Manager.TaskManager;
import Model.Task;
import Model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeAll() {
        taskManager = Managers.getDefaultTask();
    }

    @Test
    public void checkingThatTheAddedTasksRetainThePreviousVersionOfTheTask() {
        Task task = new Task("Model.Task222", "Description", TaskStatus.NEW);
        taskManager.addTask(task);
        taskManager.getTaskByID(task.getID());

        List<Task> savedHistory = taskManager.getHistory();
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);
        taskManager.getTaskByID(task.getID());

        assertEquals(savedHistory, taskManager.getHistory());
    }

    @Test
    public void checkingTheImmutabilityOfTheTaskInAllFieldsAndTheManagerDoesNotExceed10() {
        Task task = new Task("Model.Task", "Description");
        Task task2 = new Task("Model.Task 2", "Description");
        Task task3 = new Task("Model.Task 3", "Description");
        Task task4 = new Task("Model.Task 4", "Description");
        Task task5 = new Task("Model.Task 5", "Description");
        Task task6 = new Task("Model.Task 6", "Description");
        Task task7 = new Task("Model.Task 7", "Description");
        Task task8 = new Task("Model.Task 8", "Description");
        Task task9 = new Task("Model.Task 9", "Description");
        Task task10 = new Task("Model.Task 10", "Description");
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

        for (int i = 0; i <= savedTasks.size(); i++) {
            taskManager.getTaskByID(i);
        }
        assertEquals(taskManager.getHistory(), savedTasks);
        taskManager.getTaskByID(2);
        assertNotEquals(taskManager.getHistory(), savedTasks);
    }

}