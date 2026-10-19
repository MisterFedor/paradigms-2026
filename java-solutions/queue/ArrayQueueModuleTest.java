package queue;

public class ArrayQueueModuleTest {

    public static void checkFill() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void checkOperations() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                            ArrayQueueModule.element() + " " +
                            ArrayQueueModule.dequeue()
            );
        }
    }

    public static void checkClear() {
        System.out.println(ArrayQueueModule.size());
        ArrayQueueModule.clear();
        System.out.println(ArrayQueueModule.size());
    }

    public static void checkIndexOf(Object element) {
        System.out.println(ArrayQueueModule.indexOf(element));
    }

    public static void checkLastIndexOf(Object element) {
        System.out.println(ArrayQueueModule.lastIndexOf(element));
    }


    public static void main(String[] args) {
        checkFill();
        checkOperations();
        checkFill();
        checkClear();
        checkOperations();
        checkFill();
        checkIndexOf(100);
        checkLastIndexOf(0);
    }
}
