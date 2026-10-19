package expression.exceptions;

import expression.*;

public class CheckedDivide extends Divide {
    public CheckedDivide(final GeneralExpr first, final GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(final int a, final int b) {
        if (b == 0) {
            throw new DivByZeroException("Division by zero, check your input: " + a + "/" + b);
        } else if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Divide overflow, check your input: " + a + "/" + b);
        }
        return a / b;
    }
}
