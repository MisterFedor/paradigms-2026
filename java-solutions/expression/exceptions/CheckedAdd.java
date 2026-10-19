package expression.exceptions;

import expression.*;

public class CheckedAdd extends Add {
    public CheckedAdd(final GeneralExpr first, final GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(final int a, final int b) {
        if (b > 0 && a > Integer.MAX_VALUE - b) {
            overflowMessage(a, b);
        } else if (b < 0 && a < Integer.MIN_VALUE - b) {
            overflowMessage(a, b);
        }
        return a + b;
    }

    private void overflowMessage(final int a, final int b) {
        throw new OverflowException("Add overflow, check your input: " + a + "+" + b);
    }
}
