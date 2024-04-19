import java.util.ArrayList;
import java.util.HashMap;

class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    int countID = 0;

    public int generateID() {
        return countID++;
    }

    void addTask(Task task) {
        task.setID(generateID());
        tasks.put(task.getID(), task);
    }

    void addEpic(Epic epic) {
        epic.setID(generateID());
        epics.put(epic.getID(), epic);
    }

    void addSubtask(Subtask subtask) {
        subtask.setID(generateID());
        subtasks.put(subtask.getID(), subtask);
        Epic epic = epics.get(subtask.getEpic().getID());
        epic.addSubTask(subtask);
        epic.updateStatus();
    }

    void updateTask(Task task) {
        tasks.put(task.getID(), task);
    }

    ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    void getTaskByID(int ID) {
        tasks.get(ID);
    }

    void deleteAllTasks() {
        tasks.clear();
    }

    void deleteTaskByID(int ID) {
        tasks.remove(ID);
    }

    void updateEpic(Epic epic) {
        Epic updateEpic = epics.get(epic.getID());
        if (updateEpic == null) {
            return;
        }
        updateEpic.setName(epic.getName());
        updateEpic.setDescription(epic.getDescription());
    }

    ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    Epic getEpicByID(int ID) {
        epics.get(ID);
        return epics.get(ID);
    }

    void deleteAllEpics() {
        epics.clear();
    }

    void deleteEpicByID(int ID) {
        epics.remove(ID);
    }

    void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getID(), subtask);
        Epic epic = epics.get(subtask.getEpic().getID());
        ArrayList<Subtask> subtaskForEpic = epic.getSubtasks();
        for (int i = 0; i < subtaskForEpic.size(); i++) {
            if (subtaskForEpic.equals(subtask)) {
                subtaskForEpic.set(i, subtask);
                break;
            }
        }
    }

    ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    Subtask getSubTaskByID(int ID) {
        return subtasks.get(ID);

    }

    void deleteAllSubtasks() {
        subtasks.clear();
    }

    void deleteSubtasksByID(int ID) {
        subtasks.remove(ID);
    }
}
