package expression;

public class Multiply extends BinOperation {

    public Multiply(GeneralExpr first, GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(int a, int b) {
        return a * b;
    }

    @Override
    public String getOperation() {
        return "*";
    }

}
