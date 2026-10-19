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

public class ArrayQueue extends AbstractQueue {
    private static final int CAPACITY = 10;
    private Object[] elements = new Object[CAPACITY];
    private int start = 0;
    private int end = 0;
    private int queueLength = elements.length;

    // Pred: true
    // Post: size' = size && immutable(n)
    private void ensureCapacity(int capacity) {
        if (this.queueLength < capacity) {
            Object[] newQueue = new Object[this.queueLength * 2];

            int pos = this.queueLength - this.start;
            System.arraycopy(this.elements, this.start, newQueue, 0, pos);
            System.arraycopy(this.elements, 0, newQueue, pos, this.size - pos);

            this.elements = newQueue;
            this.queueLength = this.elements.length;
            this.start = 0;
            this.end = this.size;
        }
    }


    // Pred: 0 <= i < n
    // Post: returned = (object1 equals elements[i] object) && immutable(n)
    private boolean compareObjects(Object obj1, int i) {
        return (Objects.equals(obj1, this.elements[cycleQueue(this.start, i, this.queueLength)]));

    }

    // Pred: n > 0 && 0 <= pos < n && i >= 0
    // Post: 0 <= returned < n && immutable(n)
    private int cycleQueue(int pos, int i, int queueLength) {
        return (pos + i) % queueLength;
    }

    // Pred: true
    // Post: a[n + 1] = element && n' = n + 1 && immutable(n)
    @Override
    protected void enqueueImpl(Object element) {
        ensureCapacity(this.size + 1);
        this.elements[this.end] = element;
        this.end = cycleQueue(this.end, 1, this.queueLength);
    }

    // Pred: n >= 1
    // Post: returned = a[1] && n' = n - 1 && immutable(n - 1)
    @Override
    protected Object dequeueImpl() {
        Object elem = this.elements[this.start];
        this.elements[this.start] = null;
        this.start = cycleQueue(this.start, 1, this.queueLength);
        return elem;
    }

    // Pred: n >= 1
    // Post: returned = a[1] && immutable(n)
    @Override
    public Object elementImpl() {
        return this.elements[this.start];
    }

    // Pred: true
    // Post: n = 0
    @Override
    public void clearImpl() {
        this.elements = new Object[this.queueLength];
        this.start = 0;
        this.end = 0;
        this.size = 0;
    }

    // Pred: n > 0
    // Post: returned = correct first index of element  or -1, if a does not exist in queue
    // && immutable(n)

    public int indexOf(Object element) {
        return returnIndex(element, 0, this.size, 1);
    }

    // Pred: n > 0
    // Post: returned = correct last index or -1, if a does not exist in queue
    // && immutable(n)
    public int lastIndexOf(Object element) {
        return returnIndex(element, this.size - 1, -1, -1);
    }

    private int returnIndex(Object element, int start, int condition, int step) {
        for (int i = start; i != condition; i += step) {
            if (compareObjects(element, i)) {
                return i;
            }
        }
        return -1;
    }
}
