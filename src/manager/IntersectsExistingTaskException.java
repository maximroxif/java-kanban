package manager;

public class IntersectsExistingTaskException extends RuntimeException {
    public IntersectsExistingTaskException(String message) {
        super(message);
    }
}
