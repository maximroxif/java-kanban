import manager.FileBackedTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
//        TaskManager taskManager = CreateManagers.getDefaultTask();
//
//
//        Task task = new Task("Task", "Description");
//        Task task1 = new Task("Task1", "Description1");
//        Epic epic = new Epic("Epic", "Description");
//        Epic epic1 = new Epic("Epic1", "Description1");
//        taskManager.addTask(task);
//        taskManager.addEpic(epic);
//
//        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, epic.getid());
//        Subtask subtask1 = new Subtask("Subtask1", "Description1", TaskStatus.NEW, epic.getid());
//        Subtask subtask2 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic.getid());
//        taskManager.addTask(task1);
//        taskManager.addEpic(epic1);
//        Subtask subtask3 = new Subtask("Subtask2", "Description2", TaskStatus.NEW, epic1.getid());
//        taskManager.addSubtask(subtask);
//        taskManager.addSubtask(subtask1);
//        taskManager.addSubtask(subtask2);
//        taskManager.addSubtask(subtask3);
//        task1 = new Task("Update task", "Update descr ");
//        taskManager.updateTask(task1);
//
//        taskManager.getTaskByid(task.getid());
//        System.out.println(taskManager.getHistory());
//
//        taskManager.getTaskByid(task1.getid());
//        System.out.println(taskManager.getHistory());
//
//        taskManager.getTaskByid(task.getid());
//        System.out.println(taskManager.getHistory());
//
//        taskManager.getEpicByid(epic1.getid());
//        System.out.println(taskManager.getHistory());
//
//
//        taskManager.getSubTaskByid(subtask1.getid());
//        System.out.println(taskManager.getHistory());
//
//
//        taskManager.getTaskByid(task1.getid());
//        System.out.println(taskManager.getHistory());
//
//
//        taskManager.getEpicByid(epic.getid());
//        System.out.println(taskManager.getHistory());
//
//
//        taskManager.getSubTaskByid(subtask.getid());
//        System.out.println(taskManager.getHistory());
//
//
//        taskManager.getSubTaskByid(subtask2.getid());
//        System.out.println(taskManager.getHistory());
//
//
//        taskManager.getTaskByid(task.getid());
//        System.out.println(taskManager.getHistory());
//
//        taskManager.deleteTaskByid(task.getid());
//        taskManager.getEpicSubtask(epic);
//        System.out.println(taskManager.getHistory());
//
////        taskManager.deleteEpicByid(epic.getid());
//        System.out.println(taskManager.getHistory());
////        taskManager.deleteAllTasks();
////        taskManager.deleteAllSubtasks();
////        taskManager.deleteAllEpics();
//        System.out.println(taskManager.getHistory());
//        System.out.println();
//        System.out.println();

        File dir = new File("task.csv");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(dir);
        Task task = new Task("name", "descr", LocalDateTime.of(2024, Month.JUNE, 30, 18, 00), Duration.ofMinutes(10));
        fileBackedTaskManager.addTask(task);
//        fileBackedTaskManager = fileBackedTaskManager.loadFromFile(dir);
//        System.out.println(fileBackedTaskManager.getAllEpics());
        System.out.println();
//        System.out.println(fileBackedTaskManager.getHistory());
//        taskManager1.addTask()/
        Task task1 = new Task("name1", "descr1", LocalDateTime.of(2024, Month.JUNE, 30, 17, 10), Duration.ofMinutes(15));
        Task task2 = new Task("name2", "descr1", LocalDateTime.of(2024, Month.JUNE, 30, 17, 10), Duration.ofMinutes(15));

        fileBackedTaskManager.addTask(task1);
//        fileBackedTaskManager.addTask(task2);
        Epic epic2333 = new Epic("epic", "description");
        Epic epic233 = new Epic("epic1", "description1");
        fileBackedTaskManager.addEpic(epic2333);
        fileBackedTaskManager.addEpic(epic233);
        Subtask subtask5 = new Subtask("name", "desxr", LocalDateTime.of(2024, Month.JUNE, 30, 17, 43), Duration.ofMinutes(10), epic2333.getid());
        Subtask subtask6 = new Subtask("name2", "desxr1", LocalDateTime.of(2024, Month.JUNE, 30, 18, 43), Duration.ofMinutes(10), epic2333.getid());
        Subtask subtask7 = new Subtask("name23", "desxr13", LocalDateTime.of(2024, Month.JUNE, 30, 15, 43), Duration.ofMinutes(10), epic2333.getid());
//        fileBackedTaskManager.addSubtask(new Subtask("name", "descr", TaskStatus.NEW, 3));
        fileBackedTaskManager.addSubtask(subtask5);
        fileBackedTaskManager.addSubtask(subtask6);
        fileBackedTaskManager.addSubtask(subtask7);
        fileBackedTaskManager.deleteSubtasksByid(7);
//        fileBackedTaskManager.deleteSubtasksByid(6);
        fileBackedTaskManager.deleteSubtasksByid(5);
//        fileBackedTaskManager.deleteAllSubtasks();
        fileBackedTaskManager = fileBackedTaskManager.loadFromFile(dir);
//        fileBackedTaskManager.addTask(new Task("sss", "ddd"));
        System.out.println(fileBackedTaskManager.getPrioritizedTasks());
        System.out.println(epic2333.getEndTime());
//        System.out.println(fileBackedTaskManager.getEpicSubtask(epic2333));

    }

}

