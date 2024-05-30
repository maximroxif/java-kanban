package manager;

import model.Task;

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
            Node<Task> newNode = new Node<>(null, task, null);
            head = newNode;
            tail = newNode;
            history.put(task.getid(), newNode);
        } else {
            final Node<Task> oldTail = tail;
            Node<Task> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            history.put(task.getid(), newNode);
        }
        size++;
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
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (prev == null) {
            head = next;
            if(head != null)
                head.prev = null;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
            if (tail != null)
                tail.next = null;
        } else {
            next.prev = prev;
        }
        size--;
        if (size < 0)
            size = 0;
    }

    @Override
    public void remove(int id) {
        if (history.get(id) != null) {
            removeNode(history.get(id));
            history.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.containsKey(task.getid())) {
                remove(task.getid());
            }
            linkLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
