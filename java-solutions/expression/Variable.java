package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements GeneralExpr {

    private final String var;
    private final int varIndex;

    public Variable(String var) {
        this.var = var;
        this.varIndex = 0;
    }

    public Variable(int x) {
        this.var = "$" + x;
        this.varIndex = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.var, ((Variable) obj).var);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(var);
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(varIndex);
    }
}
