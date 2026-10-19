package search;

public class BinarySearch {
    // Pred: n = args.length && n > 0 &&
    // exists int x_0 : x_0 == Integer.parseInt(args[0]) &&
    // forall int i: 1 <= i < args.length => exists int x_i == Integer.parseInt(args[i]) &&
    // forall int i: 1 <= i < args.length - 1 => Integer.parseInt(args[i]) >= Integer.parseInt(args[i+1])
    public static void main(String[] args) {
        // Pred: Integer.parseInt(args[0]) = X
        // args.length > 0 && exists int x_0 == X
        final int x = Integer.parseInt(args[0]);
        // Post: x == X && args.length > 0
        // Pred: args.length > 0
        final int[] a = new int[args.length - 1];
        // Post: a.length == args.length - 1

        // Pred: n = args.length && n > 0 && a.length == n - 1 && forall int i: (1 <= i < n) =>
        // (exists int x_i: x_i == Integer.parseInt(args[i])
        // Inv: 1 <= i <= n && a.length == n - 1 &&
        // forall int j: (0 <= j < i - 1) => a[j] == Integer.parseInt(args[j + 1])
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = (Integer.parseInt(args[i]));
        }
        // Post: forall int i: (0 <= i < n - 1) => a[i] == Integer.parseInt(args[i +1]))
        System.out.println(binSearchIterative(a, x));
        //System.out.println(BinSearchRecursive(a, x, -1, a.length));
    }
    // Post: forall int i: (0 <= i < n - 1) => a[i] == Integer.parseInt(args[i +1]))
    // && printed BinSearchIterative(a, x) or BinSearchRecursive(a, x, -1, a.length) answer


    // Pred: n = a.length && n >= 0 && forall int i: 0 <= i < a.length - 1 => a[i] >= a[i + 1]
    private static int binSearchIterative(int[] a, int x) {

        int left = -1;
        int right = a.length;

        // Inv: n = a.length &&
        // forall int i: 0 <= i <= left: a[i] > x
        // forall int i: right <= i < n : a[i] <= x
        // -1 <= left < right <= n
        while (right - left > 1) {
            int middle = (right + left) / 2;
            // Pred: 0 <= middle < a.length
            if (a[middle] > x) {
                left = middle;
            } else {
                right = middle;
            }
        }

        return right;
        // Post: returned right = r
        // 0 <= r <= n
        // && forall int i: 0 <= i < r => a[i] > x
        // && forall int i: r <= i < n => a[i] <= x
    }
    // Post: returned = r.
    // 0 <= r <= n
    // && forall int i: 0 <= i < r => a[i] > x
    // && forall in i: r <= i < n => a[i] <= x

    // Pred: n = a.length && n >= 0 && forall int i: 0 <= i < a.length - 1 => a[i] >= a[i + 1]
    // forall int i: 0 <= i <= left: a[i] > x
    // forall int i: right <= i < n : a[i] <= x
    // -1 <= left < right <= n
    private static int binSearchRecursive(int[] a, int x, int left, int right) {
        if (right - left == 1) {
            return right;
        }
        // Pred: left < middle < right &&
        // left >= -1 && right <= n => 0 <= middle < n
        int middle = (left + right) / 2;
        if (a[middle] > x) {
            // forall i <= middle: a[i] >= a[middle] > x
            return binSearchRecursive(a, x, middle, right);
        } else {
            // forall i >= middle: a[i] <= a[middle] <= x
            return binSearchRecursive(a, x, left, middle);
        }
    }
    // Post: returned = r.
    // 0 <= r <= n
    // && forall int i: 0 <= i < r => a[i] > x
    // && forall int i: r <= i < n => a[i] <= x
}


