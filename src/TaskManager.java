import java.util.ArrayList;
import java.util.HashMap;

class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    private int countID = 0;

    private int generateID() {
        return countID++;
    }

    Task addTask(Task task) {
        task.setID(generateID());
        tasks.put(task.getID(), task);
        return tasks.get(task.getID());
    }

    public Epic addEpic(Epic epic) {
        epic.setID(generateID());
        epics.put(epic.getID(), epic);
        return epics.get(epic.getID());
    }

    public Subtask addSubtask(Subtask subtask) {
        subtask.setID(generateID());
        subtasks.put(subtask.getID(), subtask);
        Epic epic = epics.get(subtask.getEpicID());
        if (epic == null) {
            return null;
        }
        epic.addSubTask(subtask);
        epic.updateStatus();
        return subtasks.get(subtask.getID());
    }

    public void updateTask(Task task) {
        tasks.put(task.getID(), task);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskByID(int ID) {
        return tasks.get(ID);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteTaskByID(int ID) {
        tasks.remove(ID);
    }

    public void updateEpic(Epic epic) {
        Epic updateEpic = epics.get(epic.getID());
        if (updateEpic == null) {
            return;
        }
        updateEpic.setName(epic.getName());
        updateEpic.setDescription(epic.getDescription());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicByID(int ID) {
        return epics.get(ID);
    }

    public void deleteAllEpics() {
        deleteAllSubtasks();
        epics.clear();
    }

    public void deleteEpicByID(int ID) {
        Epic epic = epics.get(ID);
        ArrayList<Subtask> epicsSubtasks = epic.getSubtasks();
        ArrayList<Integer> subtasksID = new ArrayList<>();
        for (Subtask subtask : epicsSubtasks) {
            subtasksID.add(subtask.getID());
        }
        for (Integer subtask : subtasksID) {
            subtasks.remove(subtask);
        }
        epic.getSubtasks().clear();
        epics.remove(ID);
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.get(subtask.getID()) == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicID());
        if (epic == null) {
            return;
        }
        ArrayList<Subtask> subtaskForEpic = epic.getSubtasks();
        for (int i = 0; i < subtaskForEpic.size(); i++) {
            if(subtaskForEpic.get(i).getID() == subtask.getID()){
                subtaskForEpic.set(i, subtask);
                epic.addSubTask(subtask);
                epic.updateStatus();
            }
        }
    }

    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getEpicSubtask(Epic epic) {
        return new ArrayList<>(epic.getSubtasks());
    }

    public Subtask getSubTaskByID(int ID) {
        return subtasks.get(ID);
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            ArrayList<Subtask> epicsSubtaks = epic.getSubtasks();
            epicsSubtaks.clear();
            epic.updateStatus();
        }
    }

    public void deleteSubtasksByID(int ID) {
        Subtask subtask = subtasks.remove(ID);
        Epic epic = epics.get(subtask.getEpicID());
        if(epic == null){
            return;
        }
        ArrayList<Subtask> epicsSubtasks = epic.getSubtasks();
        for (int i = 0; i < epicsSubtasks.size(); i++) {
            if(epicsSubtasks.get(i).getID() == ID){
                epicsSubtasks.remove(i);
                break;
            }
        }
        epic.updateStatus();
    }
}
