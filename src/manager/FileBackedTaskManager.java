package manager;

import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }


    void save() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("id,type,name,description,status,epic\n");
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
            return task.getid() + "," + task.getTaskType() + "," + task.getName() + "," + task.getTaskStatus()
                    + "," + task.getDescription() + "," + Subtask.getEpicID() + "\n";
        }
        return task.getid() + "," + task.getTaskType() + "," + task.getName() + "," + task.getTaskStatus()
                + "," + task.getDescription() + "\n";
    }

    private static Task fromString(String value) {
        String[] line = value.split(",");
        int id = Integer.parseInt(line[0]);
        TaskType type = TaskType.valueOf(line[1]);
        String name = line[2];
        TaskStatus status = TaskStatus.valueOf(line[3]);
        String description = line[4];
        if (type.equals(TaskType.TASK)) {
            return new Task(id, name, description, status);
        }
        if (type.equals(TaskType.EPIC)) {
            return new Epic(id, name, description, status);
        }
        if (type.equals(TaskType.SUBTASK)) {
            return new Subtask(id, name, description, status, Integer.parseInt(line[5]));
        }
        return null;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            reader.readLine();
            while (reader.ready()) {
                String line = reader.readLine();
                String[] lines = line.split(",");
                TaskType type = TaskType.valueOf(lines[1]);
                if (type.equals(TaskType.TASK)) {
                    Task task = fromString(line);
                    fileBackedTaskManager.addTask(task);
                } else if (type.equals(TaskType.EPIC)) {
                    Epic epic = (Epic) fromString(line);
                    fileBackedTaskManager.addEpic(epic);
                } else {
                    Subtask subtask = (Subtask) fromString(line);
                    fileBackedTaskManager.addSubtask(subtask);
                }

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
    public Subtask addSubtask(Subtask subtask) {
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
    public void deleteTaskByid(int id) {
        super.deleteTaskByid(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteEpicByid(int id) {
        super.deleteEpicByid(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteSubtasksByid(int id) {
        super.deleteSubtasksByid(id);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }


}
