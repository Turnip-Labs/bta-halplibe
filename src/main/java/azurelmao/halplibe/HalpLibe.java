package azurelmao.halplibe;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HalpLibe implements ModInitializer {
    public static final String MOD_ID = "halplibe";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return MOD_ID + "." + name;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("HalpLibe initialized.");
    }
}
