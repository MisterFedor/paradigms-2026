package expression;

import java.util.List;

public abstract class UnaryOperation implements GeneralExpr {
    protected final GeneralExpr value;

    public UnaryOperation(GeneralExpr value) {
        this.value = value;
    }

    protected abstract int operation(int a);

    protected abstract String view(String elem);

    @Override
    public String toString() {
        return view(value.toString());
    }

    @Override
    public int evaluate(final int x) {
        return operation(value.evaluate(x));
    }

    @Override
    public int evaluate(final List<Integer> variables) {
        return operation(value.evaluate(variables));
    }
}
