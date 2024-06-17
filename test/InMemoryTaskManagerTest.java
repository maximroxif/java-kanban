import manager.CreateManagers;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = CreateManagers.getDefaultTask();
    }

    @Test
    public void shouldTheTaskUpdateMethod() {
        Task task = new Task("Model.Task", "Description");
        taskManager.addTask(task);

        taskManager.updateTask(new Task(task.getid(), "Model.Task", "Descr", TaskStatus.IN_PROGRESS));
        assertNotEquals(task, taskManager.getTaskByid(task.getid()));
    }

    @Test
    public void shouldTheSubtaskUpdateMethod() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Model.Subtask", "Description", TaskStatus.NEW, epic.getid());
        taskManager.addSubtask(subtask);

        Subtask oldSubtask = subtask;

        Subtask updatedSubTask = new Subtask(subtask.getid(), "Model.Subtask", "Descr", TaskStatus.IN_PROGRESS, epic.getid());

        taskManager.updateSubtask(updatedSubTask);

        assertNotEquals(oldSubtask, updatedSubTask);
        assertEquals(oldSubtask.getid(), updatedSubTask.getid());
    }

    @Test
    public void shouldTheEpicUpdateMethod() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);

        Epic oldEpic = new Epic(epic.getName(), epic.getDescription());

        Epic newEpic = new Epic("Epic1", "Description1");

        taskManager.updateEpic(newEpic);

        assertNotEquals(oldEpic, newEpic);
        assertEquals(oldEpic.getid(), newEpic.getid());
    }

    @Test
    public void shouldGetAllTask() {
        Task task1 = new Task("Task1", "Description1");
        Task task2 = new Task("Task2", "Description2");
        Task task3 = new Task("Task3", "Description3");
        Task task4 = new Task("Task4", "Description4");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);

        List<Task> tasks = taskManager.getAllTasks();

        assertEquals(4, tasks.size());
    }

    @Test
    public void shouldGetAllEpics() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);
        Epic epic2 = new Epic("Epic2", "Description2");
        taskManager.addEpic(epic2);

        assertEquals(3, taskManager.getAllEpics().size());
    }

    @Test
    public void shouldGetAllSubtasks() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Model.Subtask", "Description", TaskStatus.NEW, epic.getid());
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic.getid());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getid());
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        List<Subtask> subtasks = taskManager.getAllSubtask();

        assertEquals(3, subtasks.size());
    }

    @Test
    public void shouldGetEpicSubtask() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("Model.Subtask", "Description", TaskStatus.NEW, epic.getid());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getid());
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask2);

        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask);
        subtasks.add(subtask2);
        assertEquals(taskManager.getEpicSubtask(epic), subtasks);
    }

    @Test
    public void shouldDeleteAllTasks() {
        Task task = new Task("Model.Task", "Description");
        Task task2 = new Task("Task2", "Description2");
        taskManager.addTask(task);
        taskManager.addTask(task2);

        taskManager.deleteAllTasks();

        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void shouldDeleteTaskByid() {
        Task task1 = new Task("Task1", "Description1");
        Task task2 = new Task("Task2", "Description2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertNotNull(taskManager.getTaskByid(task1.getid()));
        taskManager.deleteTaskByid(task1.getid());
        assertNull(taskManager.getTaskByid(task1.getid()));
    }

    @Test
    public void shouldDeleteEpicByid() {
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic1.getid());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic1.getid());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertNotNull(taskManager.getSubTaskByid(subtask1.getid()));
        assertNotNull(taskManager.getSubTaskByid(subtask2.getid()));

        taskManager.deleteEpicByid(epic1.getid());

        assertNull(taskManager.getEpicByid(epic1.getid()));
        assertNull(taskManager.getSubTaskByid(subtask1.getid()));
        assertNull(taskManager.getSubTaskByid(subtask2.getid()));
    }

    @Test
    public void shouldDeleteAllEpics() {
        Epic epic1 = new Epic("Epic1", "Description1");
        Epic epic2 = new Epic("Epic2", "Description2");

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        taskManager.deleteAllEpics();
        assertNull(taskManager.getEpicByid(epic1.getid()));
        assertNull(taskManager.getEpicByid(epic2.getid()));
    }

    @Test
    public void shouldDeleteSubtaskByid() {
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);

        Subtask subtask = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic1.getid());
        taskManager.addSubtask(subtask);

        taskManager.deleteSubtasksByid(subtask.getid());

        assertNull(taskManager.getSubTaskByid(subtask.getid()));
    }

    @Test
    public void shouldDeleteAllSubtasks() {
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic1.getid());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic1.getid());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.deleteAllSubtasks();

        ArrayList<Subtask> subtasks = new ArrayList<>();

        assertEquals(taskManager.getAllSubtask(), subtasks);
    }

}