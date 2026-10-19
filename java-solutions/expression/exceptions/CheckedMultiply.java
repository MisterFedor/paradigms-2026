package expression.exceptions;

import expression.*;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(final GeneralExpr first, final GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(final int a, final int b) {
        if ((a == Integer.MIN_VALUE) && b == -1) {
            overflowMessage(a, b);
        } else if ((b == Integer.MIN_VALUE) && a == -1) {
            overflowMessage(a, b);
        } else if (a > 0) {
            if (b > 0) {
                if (a > Integer.MAX_VALUE / b) {
                    overflowMessage(a, b);
                }

            } else if (b < 0) {
                if (b < Integer.MIN_VALUE / a) {
                    overflowMessage(a, b);
                }
            }

        } else if (a < 0) {
            if (b > 0) {
                if (a < Integer.MIN_VALUE / b) {
                    overflowMessage(a, b);
                }
            } else if (b < 0) {
                if (a < Integer.MAX_VALUE / b) {
                    overflowMessage(a, b);
                }
            }
        }
        return a * b;
    }

    private void overflowMessage(int a, int b) {
        throw new OverflowException("Multiply overflow, check your input: " + a + "*" + b);
    }
}

