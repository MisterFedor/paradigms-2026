package expression.exceptions;

import expression.*;

public class CheckedNegate extends UnaryMinus {
    public CheckedNegate(final GeneralExpr value) {
        super(value);
    }

    @Override
    protected int operation(final int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("Unary minus overflow: " + "-" + a);
        }
        return -a;
    }

}
