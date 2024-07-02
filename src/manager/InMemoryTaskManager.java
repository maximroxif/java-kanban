package manager;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager = CreateManagers.getDefaultHistory();
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    protected int countid = 1;

    protected int generateid() {
        return countid++;
    }

    @Override
    public Task addTask(Task task) {
        if (intersectsWithAnExistingTask(task)) {
            throw new IntersectsExistingTaskException("Данное время занято " + task.getName() + " " + task.getStartTime());
        }
        task.setid(generateid());
        tasks.put(task.getid(), task);
        updatePrioritizedTasks();
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
        if (intersectsWithAnExistingTask(subtask)) {
            throw new IntersectsExistingTaskException("Данное время занято " + subtask.getName() + " " + subtask.getStartTime());

        }
        Epic epic = epics.get(subtask.getEpicid());
        if (epic == null) {
            return null;
        }

        subtask.setid(generateid());
        subtasks.put(subtask.getid(), subtask);
        epic.addSubTask(subtask);

        if (epic.getStartTime() == null || epic.getStartTime().isAfter(subtask.getStartTime())) {
            epic.setStartTime(subtask.getStartTime());
        }

        if (epic.getEndTime() == null || epic.getEndTime().isBefore(subtask.getEndTime())) {
            epic.setEndTime(subtask.getEndTime());
        }

        epic.setDuration(epic.getDuration().plus(subtask.getDuration()));

        epic.updateStatus();
        updatePrioritizedTasks();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        if (intersectsWithAnExistingTask(task)) {
            throw new IntersectsExistingTaskException("Данное время занято " + task.getName() + " " + task.getStartTime());
        }
        if (tasks.get(task.getid()) != null) {
            tasks.put(task.getid(), task);
            updatePrioritizedTasks();
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
        updatePrioritizedTasks();
    }

    @Override
    public void deleteTaskByid(int id) {
        historyManager.remove(id);
        tasks.remove(id);
        updatePrioritizedTasks();
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic updateEpic = epics.get(epic.getid());
        if (updateEpic == null) {
            return;
        }
        updateEpic.setName(epic.getName());
        updateEpic.setDescription(epic.getDescription());
        updatePrioritizedTasks();
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
        updatePrioritizedTasks();
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
            updatePrioritizedTasks();
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (intersectsWithAnExistingTask(subtask)) {
            throw new IntersectsExistingTaskException("Данное время занято " + subtask.getName() + " " + subtask.getStartTime());
        }
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
            updatePrioritizedTasks();
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
            epic.setStartTime(null);
            epic.setDuration(Duration.ofMinutes(0));
            epic.setEndTime(null);
            updatePrioritizedTasks();
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
        updatePrioritizedTasks();
        // Наколхозил тут конечно) Но что-то не придумал как можно лучше реализовать)
        Set<Task> subtaskTimeList = prioritizedTasks.stream().filter(subtask1 -> subtask1.getTaskType().equals(TaskType.SUBTASK)).collect(Collectors.toSet());
        if (subtaskTimeList.isEmpty()) {
            epic.setStartTime(null);
            epic.setEndTime(null);
            epic.setDuration(Duration.ofMinutes(0));
        } else {
            Set<Task> prioritizedSubasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
            prioritizedSubasks.addAll(subtaskTimeList);
            epic.setStartTime(prioritizedSubasks.stream().findFirst().get().getStartTime());
            Duration duration = Duration.ofMinutes(0);
            for (Task taskss : prioritizedSubasks) {
                duration = duration.plus(taskss.getDuration());
                epic.setDuration(duration);
            }
            epic.setEndTime(epic.getEndTime());
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    protected void updatePrioritizedTasks() {
        prioritizedTasks.clear();
        for (Task task : tasks.values()) {
            if (task.getStartTime() == null) {
                continue;
            }
            prioritizedTasks.add(task);
        }

        for (Subtask subTask : subtasks.values()) {
            if (subTask.getStartTime() == null) {
                continue;
            }
            prioritizedTasks.add(subTask);
        }
    }

    @Override
    public boolean intersectedTasks(Task newTask, Task task) {
        return ((newTask.getStartTime().isBefore(task.getEndTime()))
                && (newTask.getEndTime().isAfter(task.getStartTime())));
    }

    private boolean intersectsWithAnExistingTask(Task newTask) {
        return prioritizedTasks.stream().filter(prioritizedTask -> prioritizedTask.getid() != newTask.getid()).anyMatch(prioritizedTask -> intersectedTasks(newTask, prioritizedTask));
    }
}
