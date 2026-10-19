package expression;

public class Abs extends UnaryOperation {
    public Abs(GeneralExpr value) {
        super(value);
    }

    @Override
    protected int operation(int a) {
        if (a >= 0) {
            return a;
        } else {
            return -a;
        }
    }

    @Override
    protected String view(String elem) {
        return "‖" + elem + "‖";
    }
}
