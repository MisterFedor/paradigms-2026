package queue;

/*
Model:
    a[1], ... , a[n]
    FIFO method

let immutable(i): a'[1] == a[1] && ... && a'[i] == a[i]
let n = a.length

Inv: n >= 0 && 0 <= start < n && 0 <= end < n && 0 <= size <= n

    Pred: true
    Post: a[n + 1] = element && n' = n + 1 && immutable(n)
    enqueue(element)

    Pred: n >= 1
    Post: returned = a[1] && immutable(n)
    element()


    Pred: n >= 1
    Post: returned = a[1] && n' = n - 1 && immutable(n - 1)
    dequeue()


    Pred: true
    Post: returned = n && immutable(n)
    size()


    Pred: true
    Post: returned = (n == 0) && immutable(n)
    isEmpty()

    Pred: true
    Post: returned = (n = 0)
    clear()
 */


import java.util.Objects;

public class ArrayQueueADT {
    private static final int CAPACITY = 10;
    private int size;
    private Object[] elements;
    private int start;
    private int end;
    private int queueLength;

    public ArrayQueueADT() {
        elements = new Object[CAPACITY];
        queueLength = elements.length;
        start = 0;
        end = 0;
        size = 0;
    }

    // Pred: true
    // Post: size' = size && immutable(n)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (queue.queueLength < capacity) {
            Object[] newQueue = new Object[queue.queueLength * 2];

            int pos = queue.queueLength - queue.start;
            System.arraycopy(queue.elements, queue.start, newQueue, 0, pos);
            System.arraycopy(queue.elements, 0, newQueue, pos, queue.size - pos);

            queue.elements = newQueue;
            queue.queueLength = queue.elements.length;
            queue.start = 0;
            queue.end = queue.size;
        }
    }

    // Pred: 0 <= i < n
    // Post: returned = (object1 equals elements[i] object) && immutable(n)
    private static boolean compareObjects(ArrayQueueADT queue, Object obj1, int i) {
        return (Objects.equals(obj1, queue.elements[cycleQueue(queue.start, i, queue.queueLength)]));
    }

    // Pred: n > 0 && 0 <= pos < n && i >= 0
    // Post: 0 <= returned < n && immutable(n)
    private static int cycleQueue(int pos, int i, int queueLength) {
        return (pos + i) % queueLength;
    }

    // Pred: true
    // Post: a[n + 1] = element && n' = n + 1 && immutable(n)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, queue.size + 1);
        queue.elements[queue.end] = element;
        queue.end = cycleQueue(queue.end, 1, queue.queueLength);
        queue.size++;
    }

    // Pred: n >= 1
    // Post: returned = a[1] && n' = n - 1 && immutable(n - 1)
    public static Object dequeue(ArrayQueueADT queue) {
        Object elem = queue.elements[queue.start];
        queue.elements[queue.start] = null;
        queue.start = cycleQueue(queue.start, 1, queue.queueLength);
        queue.size--;
        return elem;
    }

    // Pred: n >= 1
    // Post: returned = a[1] && immutable(n)
    public static Object element(ArrayQueueADT queue) {
        return queue.elements[queue.start];
    }

    // Pred: true
    // Post: returned = n && immutable(n)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: true
    // Post: returned = (n == 0) && immutable(n)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pred: true
    // Post: n = 0
    public static void clear(ArrayQueueADT queue) {
        queue.elements = new Object[queue.queueLength];
        queue.start = 0;
        queue.end = 0;
        queue.size = 0;
    }


    // Pred: element != null && size > 0
    // Post: returned = correct first index of element  or -1, if a does not exist in queue
    // && immutable(n)
    public static int indexOf(ArrayQueueADT queue, Object element) {
        for (int i = 0; i < queue.size; i++) {
            if (compareObjects(queue, element, i)) {
                return i;
            }
        }
        return -1;
    }

    // Pred: a != null && size > 0
    // Post: returned = correct last index or -1, if a does not exist in queue
    // && immutable(n)
    public static int lastIndexOf(ArrayQueueADT queue, Object element) {
        for (int i = queue.size - 1; i >= 0; i--) {
            if (compareObjects(queue, element, i)) {
                return i;
            }
        }
        return -1;
    }

}
