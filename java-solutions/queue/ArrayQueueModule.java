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

public class ArrayQueueModule {
    private static final int CAPACITY = 10;
    private static int size = 0;
    private static Object[] elements = new Object[CAPACITY];
    private static int start = 0;
    private static int end = 0;
    private static int queueLength = elements.length;


    // Pred: true
    // Post: size' = size && immutable(n)
    private static void ensureCapacity(int capacity) {
        if (queueLength < capacity) {
            Object[] newQueue = new Object[queueLength * 2];
            for (int i = 0; i < size; i++) {
                newQueue[i] = elements[(start + i) % queueLength];
            }
            elements = newQueue;
            queueLength = elements.length;
            start = 0;
            end = size;
        }
    }

    // Pred: 0 <= i < n
    // Post: returned = (object1 equals elements[i] object) && immutable(n)
    private static boolean compareObjects(Object obj1, int i) {
        return (Objects.equals(obj1, elements[cycleQueue(start, i, queueLength)]));

    }

    // Pred: n > 0 && 0 <= pos < n && i >= 0
    // Post: 0 <= returned < n && immutable(n)
    private static int cycleQueue(int pos, int i, int queueLength) {
        return (pos + i) % queueLength;
    }

    // Pred: true
    // Post: a[n + 1] = element && n' = n + 1 && immutable(n)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        elements[end] = element;
        end = cycleQueue(end, 1, queueLength);
        size++;
    }

    // Pred: n >= 1
    // Post: returned = a[1] && n' = n - 1 && immutable(n - 1)
    public static Object dequeue() {
        Object elem = elements[start];
        elements[start] = null;
        start = cycleQueue(start, 1, queueLength);
        size--;
        return elem;
    }

    // Pred: n >= 1
    // Post: returned = a[1] && immutable(n)
    public static Object element() {
        return elements[start];
    }

    // Pred: true
    // Post: returned = n && immutable(n)
    public static int size() {
        return size;
    }

    // Pred: true
    // Post: returned = (n == 0) && immutable(n)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: n = 0
    public static void clear() {
        elements = new Object[queueLength];
        start = 0;
        end = 0;
        size = 0;
    }

    // Pred: element != null && size > 0
    // Post: returned = correct first index of element  or -1, if a does not exist in queue
    // && immutable(n)
    public static int indexOf(Object element) {
        for (int i = 0; i < size; i++) {
            if (compareObjects(element, i)) {
                return i;
            }
        }
        return -1;
    }

    // Pred: a != null && size > 0
    // Post: returned = correct last index or -1, if a does not exist in queue
    // && immutable(n)
    public static int lastIndexOf(Object element) {
        for (int i = size - 1; i >= 0; i--) {
            if (compareObjects(element, i)) {
                return i;
            }
        }
        return -1;
    }

}
