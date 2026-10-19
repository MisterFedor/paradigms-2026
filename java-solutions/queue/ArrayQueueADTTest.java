package queue;

public class ArrayQueueADTTest {
    public static void checkFill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }
    }

    public static void checkOperations(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                            ArrayQueueADT.element(queue) + " " +
                            ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void checkIndexOf(ArrayQueueADT queue) {
        System.out.println(ArrayQueueADT.indexOf(queue, 8));
    }

    public static void checkLastIndexOf(ArrayQueueADT queue) {
        System.out.println(ArrayQueueADT.lastIndexOf(queue, 11));
    }

    public static void checkClear(ArrayQueueADT queue) {
        System.out.println(ArrayQueueADT.size(queue));
        ArrayQueueADT.clear(queue);
        System.out.println(ArrayQueueADT.size(queue));
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
//        ArrayQueueADT queue2 = ArrayQueueADT.init();
        checkFill(queue1);
        checkOperations(queue1);
        checkFill(queue1);
        checkClear(queue1);
        checkOperations(queue1);
        checkFill(queue1);
        checkIndexOf(queue1);
        checkLastIndexOf(queue1);
    }
}
