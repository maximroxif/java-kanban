import manager.FileBackedTaskManager;
import manager.TaskNotFoundException;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    private static File file;

    @BeforeEach
    public void beforeEach() throws IOException {
        file = File.createTempFile("tests", "csv");
        this.taskManager = new FileBackedTaskManager(file);
    }

    @Test
    public void shouldSaveAndLoadAnEmptyFile() {
        try {
            String[] lines = Files.readString(file.toPath()).split("\n");
            assertEquals(lines.length, 1);
            assertEquals(lines[0], "");
            taskManager = FileBackedTaskManager.loadFromFile(file);
            assertEquals(lines.length, 1);
            assertEquals(lines[0], "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldSaveMultipleTasks() throws TaskNotFoundException {
        Task task = new Task("Task", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10));
        taskManager.addTask(task);
        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10), 2);
        taskManager.addSubtask(subtask);
        try {
            String[] lines = Files.readString(file.toPath()).split("\n");
            assertEquals(lines[0], "id,type,name,description,status,epic,startTime,duration ");
            assertEquals(lines[1], "1,TASK,Task,Description,NEW,,30.06.24 12:10,10");
            assertEquals(lines[2], "2,EPIC,Epic,Description,NEW,,30.06.24 12:21,10");
            assertEquals(lines[3], "3,SUBTASK,Subtask,Description,NEW,2,30.06.24 12:21,10");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileBackedTaskManager fileBackedTaskManager1 = FileBackedTaskManager.loadFromFile(file);
        assertEquals(taskManager.getAllTasks(), fileBackedTaskManager1.getAllTasks());
        assertEquals(taskManager.getAllEpics(), fileBackedTaskManager1.getAllEpics());
        assertEquals(taskManager.getAllSubtask(), fileBackedTaskManager1.getAllSubtask());

    }

    @Test
    public void shouldDeleteTasksInFile() throws IOException, TaskNotFoundException {
        Task task = new Task("Task", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 10), Duration.ofMinutes(10));
        taskManager.addTask(task);
        Task task2 = new Task("Task2", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 21), Duration.ofMinutes(10));
        taskManager.addTask(task2);

        Epic epic = new Epic("Epic", "Description");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description", LocalDateTime.of(2024, Month.JUNE, 30, 12, 32), Duration.ofMinutes(10), 3);
        taskManager.addSubtask(subtask);

        taskManager.deleteTaskByid(task.getid());

        String[] lines = Files.readString(file.toPath()).split("\n");

        assertEquals(4, lines.length);
    }

}