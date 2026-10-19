package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    @Override
    protected void enqueueImpl(Object element) {
        Node nextNode = new Node(element);
        if (isEmpty()) {
            head = nextNode;
            tail = head;
        } else {
            tail.next = nextNode;
            tail = nextNode;
        }
    }

    @Override
    protected Object dequeueImpl() {
        Object element = head.value;
        head = head.next;
        return element;
    }

    @Override
    protected Object elementImpl() {
        return head.value;
    }

    @Override
    protected void clearImpl() {
        head = tail = null;
        size = 0;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value) {
            this.value = value;
        }
    }


}
