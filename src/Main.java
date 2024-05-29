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

        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, epic.getid());
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic.getid());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getid());
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.getTaskByid(task.getid());
        System.out.println(taskManager.getHistory());

        taskManager.getTaskByid(task1.getid());
        System.out.println(taskManager.getHistory());

        taskManager.getTaskByid(task.getid());
        System.out.println(taskManager.getHistory());

        taskManager.getEpicByid(epic1.getid());
        System.out.println(taskManager.getHistory());


        taskManager.getSubTaskByid(subtask1.getid());
        System.out.println(taskManager.getHistory());


        taskManager.getTaskByid(task1.getid());
        System.out.println(taskManager.getHistory());


        taskManager.getEpicByid(epic.getid());
        System.out.println(taskManager.getHistory());


        taskManager.getSubTaskByid(subtask.getid());
        System.out.println(taskManager.getHistory());


        taskManager.getSubTaskByid(subtask2.getid());
        System.out.println(taskManager.getHistory());


        taskManager.getTaskByid(task.getid());
        System.out.println(taskManager.getHistory());

        taskManager.deleteTaskByid(task.getid());
        taskManager.getEpicSubtask(epic);
        System.out.println(taskManager.getHistory());

        taskManager.deleteEpicByid(epic.getid());
        System.out.println(taskManager.getHistory());
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        System.out.println(taskManager.getHistory());

    }

}

