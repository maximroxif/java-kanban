import manager.CreateManagers;
import manager.FileBackedTaskManager;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = CreateManagers.getDefaultTask();


        Task task = new Task("Task", "Description");
        Task task1 = new Task("Task1", "Description1");
        Epic epic = new Epic("Epic", "Description");
        Epic epic1 = new Epic("Epic1", "Description1");
        taskManager.addTask(task);
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, epic.getid());
        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic.getid());
        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getid());
        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        Subtask subtask3 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic1.getid());
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        task1 = new Task("Update task", "Update descr ");
        taskManager.updateTask(task1);

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

//        taskManager.deleteEpicByid(epic.getid());
        System.out.println(taskManager.getHistory());
//        taskManager.deleteAllTasks();
//        taskManager.deleteAllSubtasks();
//        taskManager.deleteAllEpics();
        System.out.println(taskManager.getHistory());
        System.out.println();
        System.out.println();

        File dir = new File("task.csv");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(dir);
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager = fileBackedTaskManager.loadFromFile(dir);
//        System.out.println(fileBackedTaskManager.getAllEpics());
        System.out.println();
//        System.out.println(fileBackedTaskManager.getHistory());
//        taskManager1.addTask()/
        fileBackedTaskManager.addTask(new Task("Task33", "Description33"));
        Epic epic2333 = new Epic("epic", "description");
        fileBackedTaskManager.addEpic(epic2333);
        fileBackedTaskManager.addSubtask(new Subtask("name", "descr", TaskStatus.NEW, 3));
        fileBackedTaskManager = fileBackedTaskManager.loadFromFile(dir);
        fileBackedTaskManager.addTask(new Task("sss", "ddd"));
        System.out.println(fileBackedTaskManager.getEpicSubtask(epic2333));
    }

}

