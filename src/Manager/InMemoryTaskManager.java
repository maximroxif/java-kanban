package Manager;

import Model.Epic;
import Model.Subtask;
import Model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = CreateManagers.getDefaultHistory();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int countid = 0;

    private int generateid() {
        return countid++;
    }

    @Override
    public Task addTask(Task task) {
        task.setid(generateid());
        tasks.put(task.getid(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setid(generateid());
        epics.put(epic.getid(), epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicid());
        if (epic == null) {
            return null;
        }

        subtask.setid(generateid());
        subtasks.put(subtask.getid(), subtask);
        epic.addSubTask(subtask);
        epic.updateStatus();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.get(task.getid()) != null) {
            tasks.put(task.getid(), task);
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
    public Task getTaskByid(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void deleteAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<>(tasks.values());
        for (Task task : tasksList) {
            historyManager.remove(task.getid());
        }
        tasks.clear();
    }

    @Override
    public void deleteTaskByid(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic updateEpic = epics.get(epic.getid());
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
    public Epic getEpicByid(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void deleteAllEpics() {
        ArrayList<Subtask> subtasksList = new ArrayList<>(subtasks.values());
        ArrayList<Epic> epicsList = new ArrayList<>(epics.values());
        for (Subtask subtask : subtasksList) {
            historyManager.remove(subtask.getid());
        }
        for (Epic epic : epicsList) {
            historyManager.remove(epic.getid());
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteEpicByid(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            ArrayList<Subtask> epicsSubtasks = epic.getSubtasks();
            for (Subtask subtask : epicsSubtasks) {
                subtasks.remove(subtask.getid());
                historyManager.remove(subtask.getid());
            }
            epic.getSubtasks().clear();
            historyManager.remove(id);
            epics.remove(id);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.get(subtask.getid()) == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicid());
        if (epic == null) {
            return;
        }
        if (subtask.getEpicid() == subtasks.get(subtask.getid()).getEpicid()) {
            ArrayList<Subtask> subtaskForEpic = epic.getSubtasks();
            for (int i = 0; i < subtaskForEpic.size(); i++) {
                if (subtaskForEpic.get(i).getid() == subtask.getid()) {
                    subtaskForEpic.set(i, subtask);
                    epic.addSubTask(subtask);
                    epic.updateStatus();
                    break;
                }
            }
            subtasks.put(subtask.getid(), subtask);
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
    public Subtask getSubTaskByid(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            ArrayList<Subtask> epicsSubtaks = epic.getSubtasks();
            for (Subtask epicsSubtak : epicsSubtaks) {
                historyManager.remove(epicsSubtak.getid());
            }
            epicsSubtaks.clear();
            epic.updateStatus();
        }
    }

    @Override
    public void deleteSubtasksByid(int id) {
        Subtask subtask = subtasks.remove(id);
        Epic epic = epics.get(subtask.getEpicid());
        if (epic == null) {
            return;
        }
        ArrayList<Subtask> epicsSubtasks = epic.getSubtasks();
        for (int i = 0; i < epicsSubtasks.size(); i++) {
            if (epicsSubtasks.get(i).getid() == id) {
                historyManager.remove(id);
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
