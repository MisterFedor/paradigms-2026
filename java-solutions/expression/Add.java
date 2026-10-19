package expression;

public class Add extends BinOperation {

    public Add(GeneralExpr first, GeneralExpr second) {
        super(first, second);
    }

    @Override
    public int operation(int a, int b) {
        return a + b;
    }

    @Override
    public String getOperation() {
        return "+";
    }

}
