package expression.exceptions;

public class DivByZeroException extends EvaluateException {
    public DivByZeroException(String reason) {
        super("Division by zero: " + reason);
    }
}
