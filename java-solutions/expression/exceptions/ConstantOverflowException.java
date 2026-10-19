package expression.exceptions;

public class ConstantOverflowException extends RuntimeException {
    public ConstantOverflowException(String message) {
        super(message);
    }
}
