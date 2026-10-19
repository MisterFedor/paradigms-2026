package expression.exceptions;

import expression.*;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(final GeneralExpr first, final GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(final int a, final int b) {
        if (b > 0 && a < Integer.MIN_VALUE + b) {
            overflowMessage(a, b);
        } else if (b < 0 && a > Integer.MAX_VALUE + b) {
            overflowMessage(a, b);
        }
        return a - b;
    }

    private void overflowMessage(int a, int b) {
        throw new OverflowException("Subtract overflow, check your input: " + a + "-" + b);
    }
}
