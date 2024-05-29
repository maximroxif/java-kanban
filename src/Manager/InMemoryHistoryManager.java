package Manager;

import Model.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {


    private static final Map<Integer, Node<Task>> history = new LinkedHashMap<>();
    private Node<Task> head = null;
    private Node<Task> tail = null;
    private int size = 0;

    private void linkLast(Task task) {
        if (head == null) {
            Node<Task> oldHead = head;
            Node<Task> newNode = new Node<>(null, task, oldHead);
            head = newNode;
            if (oldHead == null)
                tail = newNode;
            else
                oldHead.prev = newNode;
            size++;
            history.put(task.getID(), newNode);
        } else {
            Node<Task> oldTail = tail;
            Node<Task> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            size++;
            history.put(task.getID(), newNode);
        }
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> hist = new ArrayList<>();
        Node<Task> node = head;
        for (int i = 0; i < size; i++) {
            Task nodes = node.item;
            hist.add(nodes);
            node = node.next;
        }
        return hist;
    }

    private void removeNode(Node<Task> node) {
        final Task element = node.item;
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
        if (size < 0)
            size = 0;
    }

    @Override
    public void remove(int id) {
        if (history.get(id) == null) {
            return;
        } else {
            removeNode(history.get(id));
            history.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.containsKey(task.getID())) {
                remove(task.getID());
            }
            linkLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
