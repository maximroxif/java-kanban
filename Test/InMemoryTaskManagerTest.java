import Manager.Managers;
import Manager.TaskManager;
import Model.Epic;
import Model.Subtask;
import Model.Task;
import Model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefaultTask();
    }

    @Test
    public void shouldTheTaskUpdateMethod() {
        Task task = new Task("Model.Task", "Description");
        taskManager.addTask(task);

        taskManager.updateTask(new Task(task.getID(), "Model.Task", "Descr", TaskStatus.IN_PROGRESS));
        assertNotEquals(task, taskManager.getTaskByID(task.getID()));
    }

    @Test
    public void shouldTheSubtaskUpdateMethod() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Model.Subtask", "Description", TaskStatus.NEW, epic.getID());
        taskManager.addSubtask(subtask);

        Subtask oldSubtask = subtask;

        Subtask updatedSubTask = new Subtask(subtask.getID(), "Model.Subtask", "Descr", TaskStatus.IN_PROGRESS, epic.getID());

        taskManager.updateSubtask(updatedSubTask);

        assertNotEquals(oldSubtask, updatedSubTask);
        assertEquals(oldSubtask.getID(), updatedSubTask.getID());
    }

    @Test
    public void shouldTheEpicUpdateMethod() {
        Epic epic = new Epic("Model.Epic", "Description");
        taskManager.addEpic(epic);

        Epic oldEpic = new Epic(epic.getName(), epic.getDescription());

        Epic newEpic = new Epic("Epic1", "Description1");

        taskManager.updateEpic(newEpic);

        assertNotEquals(oldEpic, newEpic);
        assertEquals(oldEpic.getID(), newEpic.getID());
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

        Subtask subtask = new Subtask("Model.Subtask", "Description", TaskStatus.NEW, epic.getID());
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic.getID());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getID());
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
        Subtask subtask = new Subtask("Model.Subtask", "Description", TaskStatus.NEW, epic.getID());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getID());
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
    public void shouldDeleteTaskById() {
        Task task1 = new Task("Task1", "Description1");
        Task task2 = new Task("Task2", "Description2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertNotNull(taskManager.getTaskByID(task1.getID()));
        taskManager.deleteTaskByID(task1.getID());
        assertNull(taskManager.getTaskByID(task1.getID()));
    }

    @Test
    public void shouldDeleteEpicById() {
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic1.getID());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic1.getID());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertNotNull(taskManager.getSubTaskByID(subtask1.getID()));
        assertNotNull(taskManager.getSubTaskByID(subtask2.getID()));

        taskManager.deleteEpicByID(0);

        assertNull(taskManager.getEpicByID(epic1.getID()));
        assertNull(taskManager.getSubTaskByID(subtask1.getID()));
        assertNull(taskManager.getSubTaskByID(subtask2.getID()));
    }

    @Test
    public void shouldDeleteAllEpics() {
        Epic epic1 = new Epic("Epic1", "Description1");
        Epic epic2 = new Epic("Epic2", "Description2");

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        taskManager.deleteAllEpics();
        assertNull(taskManager.getEpicByID(epic1.getID()));
        assertNull(taskManager.getEpicByID(epic2.getID()));
    }

    @Test
    public void shouldDeleteSubtaskById() {
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);

        Subtask subtask = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic1.getID());
        taskManager.addSubtask(subtask);

        taskManager.deleteSubtasksByID(subtask.getID());

        assertNull(taskManager.getSubTaskByID(subtask.getID()));
    }

    @Test
    public void shouldDeleteAllSubtasks() {
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic1.getID());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic1.getID());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.deleteAllSubtasks();

        ArrayList<Subtask> subtasks = new ArrayList<>();

        assertEquals(taskManager.getAllSubtask(), subtasks);
    }

}