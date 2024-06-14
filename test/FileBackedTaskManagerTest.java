import manager.FileBackedTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest {
    private static File file;
    private static FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    public void beforeEach() throws IOException {
        file = File.createTempFile("tests", "csv");
        fileBackedTaskManager = new FileBackedTaskManager(file);
    }

    @Test
    public void shouldSaveAndLoadAnEmptyFile() {
        try {
            String[] lines = Files.readString(file.toPath()).split("\n");
            assertEquals(lines.length, 1);
            assertEquals(lines[0], "");
            fileBackedTaskManager = FileBackedTaskManager.loadFromFile(file);
            assertEquals(lines.length, 1);
            assertEquals(lines[0], "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldSaveMultipleTasks() {
        Task task = new Task("Task", "Description");
        fileBackedTaskManager.addTask(task);
        Epic epic = new Epic("Epic", "Description");
        fileBackedTaskManager.addEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, 2);
        fileBackedTaskManager.addSubtask(subtask);
        try {
            String[] lines = Files.readString(file.toPath()).split("\n");
            assertEquals(lines[0], "id,type,name,description,status,epic");
            assertEquals(lines[1], "1,TASK,Task,NEW,Description");
            assertEquals(lines[2], "2,EPIC,Epic,NEW,Description");
            assertEquals(lines[3], "3,SUBTASK,Subtask,NEW,Description,2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileBackedTaskManager fileBackedTaskManager1 = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fileBackedTaskManager.getAllTasks(), fileBackedTaskManager1.getAllTasks());
        assertEquals(fileBackedTaskManager.getAllEpics(), fileBackedTaskManager1.getAllEpics());
        assertEquals(fileBackedTaskManager.getAllSubtask(), fileBackedTaskManager1.getAllSubtask());

    }

    @Test
    public void shouldDeleteTasksInFile() throws IOException {
        Task task = new Task("Task", "Description");
        fileBackedTaskManager.addTask(task);
        Task task2 = new Task("Task2", "Description");
        fileBackedTaskManager.addTask(task2);

        Epic epic = new Epic("Epic", "Description");
        fileBackedTaskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, 3);
        fileBackedTaskManager.addSubtask(subtask);

        fileBackedTaskManager.deleteTaskByid(task.getid());

        String[] lines = Files.readString(file.toPath()).split("\n");

        assertEquals(4, lines.length);
    }

}