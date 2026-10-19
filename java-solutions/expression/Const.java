package expression;

import java.util.List;

public class Const implements GeneralExpr {

    private final int c;

    public Const(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return Integer.toString(c);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.c == ((Const) obj).c;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(c);
    }

    @Override
    public int evaluate(int x) {
        return c;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return c;
    }
}
