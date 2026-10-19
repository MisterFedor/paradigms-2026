package expression;

public class UnaryMinus extends UnaryOperation {

    public UnaryMinus(GeneralExpr value) {
        super(value);
    }

    @Override
    protected int operation(int a) {
        return -a;
    }

    @Override
    protected String view(final String elem) {
        return "-(" + elem + ")";
    }

}
