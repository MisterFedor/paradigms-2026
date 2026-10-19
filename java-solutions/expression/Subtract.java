package expression;

import java.util.List;

public class Subtract extends BinOperation {

    public Subtract(GeneralExpr first, GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(int a, int b) {
        return a - b;
    }

    @Override
    public String getOperation() {
        return "-";
    }

}
