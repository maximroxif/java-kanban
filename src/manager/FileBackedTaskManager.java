package manager;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import model.TaskType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");


    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,description,status,epic,startTime,duration \n");
            if (!tasks.isEmpty()) {
                for (Task task : tasks.values()) {
                    fileWriter.write(toString(task));
                }
            }
            if (!epics.isEmpty()) {
                for (Epic epic : epics.values()) {
                    fileWriter.write(toString(epic));
                }
            }
            if (!subtasks.isEmpty()) {
                for (Subtask subtask : subtasks.values()) {
                    fileWriter.write(toString(subtask));
                }
            }
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка записи");
        }
    }

    private String toString(Task task) {
        if (task.getTaskType().equals(TaskType.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            return task.getid() + "," + task.getTaskType() + "," + task.getName() + "," + task.getDescription()
                    + "," + task.getTaskStatus() + "," + subtask.getEpicId() + "," + task.getStartTime().format(DATE_TIME_FORMATTER)
                    + "," + task.getDuration().toMinutes() + "\n";
        }
        return task.getid() + "," + task.getTaskType() + "," + task.getName() + "," + task.getDescription()
                + "," + task.getTaskStatus() + "," + ","
                + (task.getStartTime() != null ? task.getStartTime().format(DATE_TIME_FORMATTER) : "")
                + "," + task.getDuration().toMinutes() + "\n";
    }


    private static Task fromString(String value) {
        String[] line = value.split(",");
        int id = Integer.parseInt(line[0]);
        TaskType type = TaskType.valueOf(line[1]);
        String name = line[2];
        String description = line[3];
        TaskStatus status = TaskStatus.valueOf(line[4]);
        LocalDateTime startTime = null;
        if (!line[6].isBlank()) {
            startTime = LocalDateTime.parse(line[6], DATE_TIME_FORMATTER);
        }

        Duration duration = Duration.ofMinutes(Integer.parseInt(line[7]));

        if (type.equals(TaskType.TASK)) {
            Task task = new Task(name, description, startTime, duration);
            task.setid(id);
            return task;
        }
        if (type.equals(TaskType.EPIC)) {
            Epic epic = new Epic(name, description);
            epic.setid(id);
            epic.setTaskStatus(status);
            return epic;
        }
        if (type.equals(TaskType.SUBTASK)) {
            int epicid = Integer.parseInt(line[5]);
            Subtask subtask = new Subtask(name, description, startTime, duration, epicid);
            subtask.setid(id);
            return subtask;
        }
        return null;
    }

    @Override
    protected void updatePrioritizedTasks() {
        super.updatePrioritizedTasks();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            reader.readLine();
            while (reader.ready()) {
                String line = reader.readLine();
                String[] lines = line.split(",");
                TaskType type = TaskType.valueOf(lines[1]);
                int id = Integer.parseInt(lines[0]);
                int count = 1;
                if (type.equals(TaskType.TASK)) {
                    Task task = fromString(line);
                    fileBackedTaskManager.tasks.put(task.getid(), task);
                    fileBackedTaskManager.updatePrioritizedTasks();
                    if (id > count) {
                        count = id;
                    }
                } else if (type.equals(TaskType.EPIC)) {
                    Epic epic = (Epic) fromString(line);
                    fileBackedTaskManager.epics.put(epic.getid(), epic);
                    fileBackedTaskManager.updatePrioritizedTasks();
                    if (id > count) {
                        count = id;
                    }
                } else {
                    Subtask subtask = (Subtask) fromString(line);
                    fileBackedTaskManager.subtasks.put(subtask.getid(), subtask);
                    Epic epic = fileBackedTaskManager.epics.get(subtask.getEpicid());
                    epic.addSubTask(subtask);
                    fileBackedTaskManager.updatePrioritizedTasks();
                    if (id > count) {
                        count = id;
                    }
                }
                fileBackedTaskManager.countid = ++count;
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка файла");
        }
        return fileBackedTaskManager;
    }

    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) throws TaskNotFoundException {
        super.addSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskByid(int id) throws TaskNotFoundException {
        super.deleteTaskByid(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteEpicByid(int id) throws TaskNotFoundException {
        super.deleteEpicByid(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteSubtasksByid(int id) throws TaskNotFoundException {
        super.deleteSubtasksByid(id);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }


}
