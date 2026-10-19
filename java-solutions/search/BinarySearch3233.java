package search;

public class BinarySearch3233 {

    // Pred: n = args.length && n > 0 &&
    // forall int i: 0 <= i < args.length => exists int x_i == Integer.parseInt(args[i]) &&
    // exists int k: 0 <= k < n &&
    // forall int i: k <= i < (n - 1) => args[i] < args[i + 1] &&
    // forall int j: 0 <= j < (k - 1) => args[j] < args[j + 1] &&
    // (k can be == 0 => forall int i 0 <= i < n - 1: args[i] < args[i +1])
    // || (k > 0 => args[n - 1] < args[0])
    public static void main(String[] args) {
        // args.length > 0
        final int[] a = new int[args.length];
        // a.length = N = args.length

        // Pred: forall int i: 0 <= i < N => args[i] can be parsed like int
        // Inv: 0 <= i <= N &&
        // forall int j: 0 <= j < i => a[j] == Integer.parseInt(args[j])
        for (int i = 0; i < args.length; i++) {
            a[i] = (Integer.parseInt(args[i]));
        }
        // Post: forall int j: 0 <= j < i => a[j] == Integer.parseInt(args[j]) && a[i] = int(args[i + 1])
        System.out.println(BinSearchIterative(a));
        //System.out.println(BinSearchRecursive(a, 0, a.length - 1));
    }
    // Post: n = args.length && forall int j: 0 <= j < n => a[j] == Integer.parseInt(args[j]) &&
    // exists int k: 0 <= k < n &&
    // forall int i: k <= i < (n - 1) => a[i] < a[i + 1] &&
    // forall int j: 0 <= j < (k - 1) => a[j] < a[j + 1] &&
    // (k can be == 0 => forall int i: a[i] <= a[i +1]) || (k > 0 => a[n - 1] < a[0]) &&
    // printed BinSearchIterative [ BinSearchRecursive ] answer


    // Pred: n = a.length && n > 0 &&
    // exists int k: 0 <= k < n &&
    // forall int i: k <= i < (n - 1) => a[i] < a[i + 1] &&
    // forall int j: 0 <= j < (k - 1) => a[j] < a[j + 1] &&
    // (k can be == 0 => forall int i: a[i] < a[i +1]) || (k > 0 => a[n - 1] < a[0]) &&
    // => k - minimum element index
    private static int BinSearchIterative(int[] a) {
        int left = 0;
        int right = a.length - 1;

        // Pred: (right - left) > 1
        // Inv: 0 <= left < right < n, index k in [left,right]
        while (right - left > 1) {
            // left < middle < right
            int middle = (left + right) / 2;
            if (a[middle] > a[right]) {
                // k in [middle + 1 ... right]
                left = middle + 1;
            } else {
                // k in [left...middle]
                right = middle;
            }
        }
        // Post: 0 <= left < right < n
        // right - left <= 1 => (right == left) || (right == left + 1)
        return (a[left] < a[right]) ? left : right;
    }
    // Post: returned = r, 0 <= r < n &&
    // forall int i: 0 <= i < n => a[r] <= a[i]


    // Pred: n = a.length && n > 0 && 0 <= left <= right < n &&
    // exists int k: 0 <= k < n &&
    // forall int i: k <= i < (n - 1) => a[i] < a[i + 1] &&
    // forall int j: 0 <= j < (k - 1) => a[j] < a[j + 1] &&
    // k in [left..right]
    private static int BinSearchRecursive(int[] a, int left, int right) {
        if (right == left) {
            // [left..right].length == 1 && k == left == right
            return left;
            // returned k
        }
        int middle = (left + right) / 2;
        // left <= middle < right

        if (a[right] < a[middle]) {
            // k in [middle + 1 ... right]
            return BinSearchRecursive(a, middle + 1, right);
        } else {
            // k in [left ... middle]
            return BinSearchRecursive(a, left, middle);
        }
    }
    // Post: returned = r, 0 <= r < n &&
    // forall int i: 0 <= i < n => a[r] <= a[i]
}


