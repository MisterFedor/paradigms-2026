package expression.exceptions;

public class OverflowException extends EvaluateException {
    public OverflowException(String reason) {
        super("Overflow: " + reason);
    }
}
