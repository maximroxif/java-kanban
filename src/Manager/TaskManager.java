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

    Task getTaskByid(int id);

    void deleteAllTasks();

    void deleteTaskByid(int id);

    void updateEpic(Epic epic);

    List<Epic> getAllEpics();

    Epic getEpicByid(int id);

    void deleteAllEpics();

    void deleteEpicByid(int id);

    void updateSubtask(Subtask subtask);

    List<Subtask> getAllSubtask();

    List<Subtask> getEpicSubtask(Epic epic);

    Subtask getSubTaskByid(int id);

    void deleteAllSubtasks();

    void deleteSubtasksByid(int id);

    List<Task> getHistory();
}
