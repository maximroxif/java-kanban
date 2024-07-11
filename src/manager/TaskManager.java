package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask) throws TaskNotFoundException;

    void updateTask(Task task);

    List<Task> getAllTasks();

    Task getTaskByid(int id) throws TaskNotFoundException;

    void deleteAllTasks();

    void deleteTaskByid(int id) throws TaskNotFoundException;

    void updateEpic(Epic epic);

    List<Epic> getAllEpics();

    Epic getEpicByid(int id) throws TaskNotFoundException;

    void deleteAllEpics();

    void deleteEpicByid(int id) throws TaskNotFoundException;

    void updateSubtask(Subtask subtask);

    List<Subtask> getAllSubtask();

    List<Subtask> getEpicSubtask(Epic epic);

    Subtask getSubTaskByid(int id) throws TaskNotFoundException;

    void deleteAllSubtasks();

    void deleteSubtasksByid(int id) throws TaskNotFoundException;

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();

    boolean intersectedTasks(Task newTask, Task task);
}
