public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Task", "description", TaskStatus.NEW);
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

        Subtask subtask = new Subtask("subtask", "descriprion", TaskStatus.IN_PROGRESS, epic);
        Subtask subtask1 = new Subtask("subtask", "descriprion", TaskStatus.NEW, epic1);
        Subtask subtask2 = new Subtask("subtask", "descriprion", TaskStatus.DONE, epic2);

        taskManager.addSubtask(subtask);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        System.out.println("Create subtask " + subtask);
        System.out.println("Create subtask " + subtask1);
        System.out.println("Create subtask " + subtask2);
        System.out.println("Epic status " + epic);
        System.out.println("Epic status " + epic2);

        Task taskUpdate = new Task(task1.getID(), "Task1", "description update", TaskStatus.IN_PROGRESS);
        taskManager.updateTask(taskUpdate);
        System.out.println("Task update " + taskUpdate);

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

        taskManager.deleteTaskByID(task.getID());
        System.out.println(taskManager.getAllTasks());

        taskManager.deleteEpicByID(epic1.getID());
        System.out.println(taskManager.getAllEpics());

        taskManager.deleteSubtasksByID(subtask2.getID());
        System.out.println(taskManager.getAllSubtask());

        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());

    }

}

