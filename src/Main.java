import Manager.InMemoryTaskManager;
import Manager.TaskManager;
import Model.Epic;
import Model.Subtask;
import Model.Task;
import Model.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task("Model.Task", "description", TaskStatus.NEW);
        taskManager.addTask(task);
        Task task1 = new Task("Task1", "description1", TaskStatus.NEW);
        taskManager.addTask(task1);
        Task task2 = new Task("Task2", "description2", TaskStatus.NEW);
        taskManager.addTask(task2);

        System.out.println("Create task " + task);
        System.out.println("Create task " + task1);
        System.out.println("Create task " + task2);

        Epic epic = new Epic("epic", "description");
        Epic epic1 = new Epic("epic1", "description");
        Epic epic2 = new Epic("epic2", "description");

        taskManager.addEpic(epic);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        System.out.println("Create epic " + epic);
        System.out.println("Create epic " + epic1);
        System.out.println("Create epic " + epic2);

        Subtask subtask = new Subtask("subtask", "descriprion", TaskStatus.IN_PROGRESS, epic.getID());
        Subtask subtask1 = new Subtask("subtask", "descriprion", TaskStatus.NEW, epic1.getID());
        Subtask subtask2 = new Subtask("subtask", "descriprion", TaskStatus.DONE, epic2.getID());

        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);



        System.out.println("Create subtask " + subtask);
        System.out.println("Create subtask " + subtask1);
        System.out.println("Create subtask " + subtask2);
        System.out.println("Model.Epic status " + epic);
        System.out.println("Model.Epic status " + epic2);

        Task taskUpdate = new Task(task1.getID(), "Task1", "description update", TaskStatus.IN_PROGRESS);
        taskManager.updateTask(taskUpdate);
        System.out.println("Model.Task update " + taskUpdate);

        Epic epicUpdate = taskManager.getEpicByID(epic.getID());
        epicUpdate.setName("EpicUpdate");
        epicUpdate.setDescription("DescriptionUpdate");
        taskManager.updateEpic(epicUpdate);
        System.out.println(epicUpdate);

        Subtask subtaskUpdate = taskManager.getSubTaskByID(subtask2.getID());
        subtaskUpdate.setName("subtaskUpdate");
        subtaskUpdate.setDescription("descriptionUpdate");
        subtaskUpdate.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtaskUpdate);
        System.out.println(subtaskUpdate);

        printAllTasks(taskManager);
        taskManager.deleteAllEpics();
        taskManager.deleteAllTasks();
        printAllTasks(taskManager);

    }
    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : epic.getSubtasks()) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Subtask subtask : manager.getAllSubtask()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}

