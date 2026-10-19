package expression;

public class Divide extends BinOperation {

    public Divide(GeneralExpr first, GeneralExpr second) {
        super(first, second);
    }

    @Override
    public String getOperation() {
        return "/";
    }

    @Override
    public int operation(int a, int b) {
        return a / b;
    }

}
