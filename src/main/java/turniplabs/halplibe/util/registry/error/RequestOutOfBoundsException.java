package turniplabs.halplibe.util.registry.error;

@Deprecated(since = "6.1.0", forRemoval = true)
public class RequestOutOfBoundsException extends RuntimeException {
    public RequestOutOfBoundsException(String message) {
        super(message);
    }
}
