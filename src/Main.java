import Manager.InMemoryTaskManager;
import Manager.TaskManager;
import Model.Epic;
import Model.Subtask;
import Model.Task;
import Model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();


        Task task = new Task("Task", "Description");
        Task task1 = new Task("Task1", "Description1");
        Epic epic = new Epic("Epic", "Description");
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, epic.getID());
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic.getID());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getID());
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.getTaskByID(task.getID());
        System.out.println(taskManager.getHistory());

        taskManager.getTaskByID(task1.getID());
        System.out.println(taskManager.getHistory());

        taskManager.getTaskByID(task.getID());
        System.out.println(taskManager.getHistory());

        taskManager.getEpicByID(epic1.getID());
        System.out.println(taskManager.getHistory());


        taskManager.getSubTaskByID(subtask1.getID());
        System.out.println(taskManager.getHistory());


        taskManager.getTaskByID(task1.getID());
        System.out.println(taskManager.getHistory());


        taskManager.getEpicByID(epic.getID());
        System.out.println(taskManager.getHistory());


        taskManager.getSubTaskByID(subtask.getID());
        System.out.println(taskManager.getHistory());


        taskManager.getSubTaskByID(subtask2.getID());
        System.out.println(taskManager.getHistory());


        taskManager.getTaskByID(task.getID());
        System.out.println(taskManager.getHistory());

        taskManager.deleteTaskByID(task.getID());
        taskManager.getEpicSubtask(epic);
        System.out.println(taskManager.getHistory());

        taskManager.deleteEpicByID(epic.getID());
        System.out.println(taskManager.getHistory());
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        System.out.println(taskManager.getHistory());

    }

}

