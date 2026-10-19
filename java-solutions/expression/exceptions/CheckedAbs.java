package expression.exceptions;

import expression.*;


public class CheckedAbs extends Abs {

    public CheckedAbs(final GeneralExpr value) {
        super(value);
    }

    @Override
    protected int operation(final int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("Abs overflow, check your input: " + a);
        } else if (a >= 0) {
            return a;
        } else {
            return -a;
        }
    }
}
