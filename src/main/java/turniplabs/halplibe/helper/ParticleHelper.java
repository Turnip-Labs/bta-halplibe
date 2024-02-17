package turniplabs.halplibe.helper;



import net.minecraft.client.entity.fx.EntityFX;

import java.util.HashMap;
import java.util.Map;

public class ParticleHelper {

    public static Map<String, Class<? extends EntityFX>> particles = new HashMap<>();
    @SuppressWarnings("unused") // API function
    public static void createParticle(Class<? extends EntityFX> clazz, String name) {
        particles.put(name, clazz);
    }
}
