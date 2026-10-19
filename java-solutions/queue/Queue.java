package queue;

/*
Model:
    a[1], ... , a[n]
    FIFO method

let immutable(i): a'[1] == a[1] && ... && a'[i] == a[i]
let n = a.length

 */

public interface Queue {
    // Pred: true
    // Post: a[n + 1] = element && n' = n + 1 && immutable(n)
    void enqueue(Object element);

    // Pred: n >= 1
    // Post: returned = a[1] && n' = n - 1 && immutable(n - 1)
    Object dequeue();

    // Pred: n >= 1
    // Post: returned = a[1] && immutable(n)
    Object element();

    // Pred: true
    // Post: returned = n && immutable(n)
    int size();

    // Pred: true
    // Post: returned = (n == 0) && immutable(n)
    boolean isEmpty();

    // Pred: true
    // Post: n = 0
    void clear();

    // Pred: element != null && n > 0
    // Post: n times x = dequeue() && if x != element enqueue(x)
    void removeAll(Object element);

    // Pred: element != null && n > 0
    // Post: n times x = dequeue() && if x == element enqueue(x)
    void retainAll(Object element);


}
