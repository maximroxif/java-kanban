package Manager;

import Model.Epic;
import Model.Subtask;
import Model.Task;

import java.util.List;

public interface TaskManager {
    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    void updateTask(Task task);

    List<Task> getAllTasks();

    Task getTaskByID(int ID);

    void deleteAllTasks();

    void deleteTaskByID(int ID);

    void updateEpic(Epic epic);

    List<Epic> getAllEpics();

    Epic getEpicByID(int ID);

    void deleteAllEpics();

    void deleteEpicByID(int ID);

    void updateSubtask(Subtask subtask);

    List<Subtask> getAllSubtask();

    List<Subtask> getEpicSubtask(Epic epic);

    Subtask getSubTaskByID(int ID);

    void deleteAllSubtasks();

    void deleteSubtasksByID(int ID);

    List<Task> getHistory();
}
