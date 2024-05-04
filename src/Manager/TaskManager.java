package Manager;

import Model.Epic;
import Model.Subtask;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    void updateTask(Task task);

    ArrayList<Task> getAllTasks();

    Task getTaskByID(int ID);

    void deleteAllTasks();

    void deleteTaskByID(int ID);

    void updateEpic(Epic epic);

    ArrayList<Epic> getAllEpics();

    Epic getEpicByID(int ID);

    void deleteAllEpics();

    void deleteEpicByID(int ID);

    void updateSubtask(Subtask subtask);

    ArrayList<Subtask> getAllSubtask();

    ArrayList<Subtask> getEpicSubtask(Epic epic);

    Subtask getSubTaskByID(int ID);

    void deleteAllSubtasks();

    void deleteSubtasksByID(int ID);

    List<Task> getHistory();
}
