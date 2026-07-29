package turniplabs.halplibe.util;

import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NullMarked
public final class HalpLibeUtils {
    // IMPORTANT: This class should NEVER refer to game classes. The "halplibe" string deliberately does
    // not use MOD_ID from the HalpLibe class.
    public static final Logger LOGGER = LoggerFactory.getLogger("halplibe");

    private HalpLibeUtils() {}
}
