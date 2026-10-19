package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;
    protected boolean removeAll;
    protected boolean retainAll;

    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object dequeue() {
        Object element = dequeueImpl();
        size--;
        return element;
    }

    protected abstract Object dequeueImpl();

    public Object element() {
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public void removeAll(Object element) {
        removeAll = true;
        checkAllElements(element);
        removeAll = false;
    }

    public void retainAll(Object element) {
        retainAll = true;
        checkAllElements(element);
        retainAll = false;
    }

    private void checkAllElements(Object element) {
        int queueLength = size;
        for (int i = 0; i < queueLength; i++) {
            Object objForCompare = dequeue();
            if (removeAll) {
                if (!Objects.equals(element, objForCompare)) {
                    enqueue(objForCompare);
                }
            } else if (retainAll) {
                if (Objects.equals(element, objForCompare)) {
                    enqueue(objForCompare);
                }
            }
        }
    }
}
