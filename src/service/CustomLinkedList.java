package service;

import tasks.Task;

import java.util.*;

/**
 * Собственная реализация LinkedList. Позволяет совершать операцию удаления элемента за O(1) за счет хранения Node во
 * внутренней таблице HashMap.
 */
public class CustomLinkedList {
    private Node head;
    private Node tail;
    private final Map<Long, Node> nodes = new HashMap<>();

    /**
     * Добавление элемента типа Task в конец связного списка.
     * @param task задача для сохранения
     */
    public void linkLast(Task task) {
        if (nodes.containsKey(task.getTaskId())) {
            removeNode(nodes.get(task.getTaskId()));
        }
        if (head == null) {
            head = new Node(null, task, null);
            tail = head;
            nodes.put(task.getTaskId(), head);
        } else {
            Node currNode = tail;
            tail = new Node(currNode, task, null);
            tail.prev = currNode;
            currNode.next = tail;
            nodes.put(task.getTaskId(), tail);
        }
    }

    /**
     * Получение списка истории задач, находящихся в CustomLinkedList
     * @return список задач
     */
    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        Node currNode = head;

        while (currNode != null) {
            taskList.add(currNode.getTask());
            currNode = currNode.next;
        }
        return taskList;
    }

    /**
     * Удаление задачи из списка просмотров по id
     * @param id идентификатор задачи, которую требуется удалить
     */
    public boolean remove(Long id) {
        if (nodes.containsKey(id)) {
            Node node = nodes.get(id);
            removeNode(node);
            nodes.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Присваивание новых ссылок при удалении Node
     * @param node Node, которую требуется удалить
     */
    private void removeNode(Node node) {
        Node nodeNext = node.next;
        Node nodePrevious = node.prev;
        if (nodePrevious == null && nodeNext == null) {
            head = null;
            tail = null;
        } else if (nodeNext == null) {
            nodePrevious.next = null;
            tail = nodePrevious;
        } else if (nodePrevious == null) {
            nodeNext.prev = null;
            head = nodeNext;
        } else {
            nodeNext.prev = nodePrevious;
            nodePrevious.next = nodeNext;
        }
    }

    /**
     * Внутренний класс для CustomLinkedList. Содержит ссылку на предыдущий и следующий узел, а также ссылку на задачу,
     * которую он хранит.
     */
    private static class Node {
        private Node prev;
        private final Task task;
        private Node next;

        public Node(Node prev, Task task, Node next) {
            this.next = next;
            this.task = task;
            this.prev = prev;
        }

        public Task getTask() {
            return task;
        }
    }
}