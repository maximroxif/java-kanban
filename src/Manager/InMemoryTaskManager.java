package Manager;

import Model.Epic;
import Model.Subtask;
import Model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int countID = 0;

    private int generateID() {
        return countID++;
    }

    @Override
    public Task addTask(Task task) {
        task.setID(generateID());
        tasks.put(task.getID(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setID(generateID());
        epics.put(epic.getID(), epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicID());
        if (epic == null) {
            return null;
        }

        subtask.setID(generateID());
        subtasks.put(subtask.getID(), subtask);
        epic.addSubTask(subtask);
        epic.updateStatus();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.get(task.getID()) != null) {
            tasks.put(task.getID(), task);
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<>(tasks.values());
        for (Task task : tasksList) {
            if (task != null) {
                historyManager.add(task);
            }
        }
        return tasksList;
    }

    @Override
    public Task getTaskByID(int ID) {
        historyManager.add(tasks.get(ID));
        return tasks.get(ID);
    }

    @Override
    public void deleteAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<>(tasks.values());
        for (Task task : tasksList) {
            historyManager.remove(task.getID());
        }
        tasks.clear();
    }

    @Override
    public void deleteTaskByID(int ID) {
        historyManager.remove(ID);
        tasks.remove(ID);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic updateEpic = epics.get(epic.getID());
        if (updateEpic == null) {
            return;
        }
        updateEpic.setName(epic.getName());
        updateEpic.setDescription(epic.getDescription());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>(epics.values());
        for (Epic epic : epicsList) {
            historyManager.add(epic);
        }
        return epicsList;
    }

    @Override
    public Epic getEpicByID(int ID) {
        historyManager.add(epics.get(ID));
        return epics.get(ID);
    }

    @Override
    public void deleteAllEpics() {
        ArrayList<Subtask> subtasksList = new ArrayList<>(subtasks.values());
        ArrayList<Epic> epicsList = new ArrayList<>(epics.values());
        for (Subtask subtask : subtasksList) {
            historyManager.remove(subtask.getID());
        }
        for (Epic epic : epicsList) {
            historyManager.remove(epic.getID());
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteEpicByID(int ID) {
        Epic epic = epics.get(ID);
        if (epic != null) {
            ArrayList<Subtask> epicsSubtasks = epic.getSubtasks();
            for (Subtask subtask : epicsSubtasks) {
                subtasks.remove(subtask.getID());
                historyManager.remove(subtask.getID());
            }
            epic.getSubtasks().clear();
            historyManager.remove(ID);
            epics.remove(ID);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.get(subtask.getID()) == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicID());
        if (epic == null) {
            return;
        }
        if (subtask.getEpicID() == subtasks.get(subtask.getID()).getEpicID()) {
            ArrayList<Subtask> subtaskForEpic = epic.getSubtasks();
            for (int i = 0; i < subtaskForEpic.size(); i++) {
                if (subtaskForEpic.get(i).getID() == subtask.getID()) {
                    subtaskForEpic.set(i, subtask);
                    epic.addSubTask(subtask);
                    epic.updateStatus();
                    break;
                }
            }
            subtasks.put(subtask.getID(), subtask);
        }
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
        ArrayList<Subtask> subtasksList = new ArrayList<>(subtasks.values());
        for (Subtask subtask : subtasksList) {
            historyManager.add(subtask);
        }
        return subtasksList;
    }

    @Override
    public ArrayList<Subtask> getEpicSubtask(Epic epic) {
        ArrayList<Subtask> subtasksList = new ArrayList<>(epic.getSubtasks());
        for (Subtask subtask : subtasksList) {
            historyManager.add(subtask);
        }
        return subtasksList;
    }

    @Override
    public Subtask getSubTaskByID(int ID) {
        historyManager.add(subtasks.get(ID));
        return subtasks.get(ID);
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            ArrayList<Subtask> epicsSubtaks = epic.getSubtasks();
            for (Subtask epicsSubtak : epicsSubtaks) {
                historyManager.remove(epicsSubtak.getID());
            }
            epicsSubtaks.clear();
            epic.updateStatus();
        }
    }

    @Override
    public void deleteSubtasksByID(int ID) {
        Subtask subtask = subtasks.remove(ID);
        Epic epic = epics.get(subtask.getEpicID());
        if (epic == null) {
            return;
        }
        ArrayList<Subtask> epicsSubtasks = epic.getSubtasks();
        for (int i = 0; i < epicsSubtasks.size(); i++) {
            if (epicsSubtasks.get(i).getID() == ID) {
                historyManager.remove(ID);
                epicsSubtasks.remove(i);
                break;
            }
        }
        epic.updateStatus();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
