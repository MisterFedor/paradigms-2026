package queue;

public class ArrayQueueMyTest {
    public static void checkFill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void checkIndexOf(ArrayQueue queue) {
        System.out.println(queue.indexOf(7));
    }

    public static void checkLastIndexOf(ArrayQueue queue) {
        System.out.println(queue.lastIndexOf(3));
    }

    public static void checkOperations(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                            queue.element() + " " +
                            queue.dequeue()
            );
        }
    }

    public static void checkClear(ArrayQueue queue) {
        System.out.println(queue.size());
        queue.clear();
        System.out.println(queue.size());
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        checkFill(queue);
        checkOperations(queue);
        checkFill(queue);
        checkClear(queue);
        checkOperations(queue);
        checkFill(queue);
        checkIndexOf(queue);
        checkLastIndexOf(queue);
    }
}
