package turniplabs.halplibe.util.registry.error;

@Deprecated(since = "6.1.0", forRemoval = true)
public class RequestCutShortException extends RuntimeException {
    public RequestCutShortException(String message) {
        super(message);
    }
}
