package expression.exceptions;

import expression.GeneralExpr;
import expression.UnaryOperation;

public class Cbrt extends UnaryOperation {
    public Cbrt(GeneralExpr value) {
        super(value);
    }

    @Override
    protected int operation(final int a) {
        return (int) Math.cbrt(a);
    }

    @Override
    protected String view(final String elem) {
        return "∛(" + elem + ")";
    }
}
