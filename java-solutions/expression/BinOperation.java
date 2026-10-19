package expression;

import java.util.List;
import java.util.Objects;

public abstract class BinOperation implements GeneralExpr {
    private final GeneralExpr first;
    private final GeneralExpr second;

    public BinOperation(GeneralExpr first, GeneralExpr second) {
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        return 19 * getClass().hashCode() + 17 * first.hashCode()
                + 43 * second.hashCode();
    }

    protected abstract int operation(int a, int b);

    public abstract String getOperation();

    @Override
    public int evaluate(int x) {
        int a = first.evaluate(x);
        int b = second.evaluate(x);
        return operation(a, b);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        int a = first.evaluate(variables);
        int b = second.evaluate(variables);
        return operation(a, b);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + " " + getOperation() + " "
                + second.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(first, ((BinOperation) obj).first)
                && Objects.equals(second, ((BinOperation) obj).second);
    }
}
