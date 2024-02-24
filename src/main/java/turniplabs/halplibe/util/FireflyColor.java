package turniplabs.halplibe.util;

import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.biome.Biome;
import turniplabs.halplibe.helper.FireflyHelper;
import turniplabs.halplibe.helper.ParticleHelper;

public class FireflyColor {
    private final int id;
    private final String particleName;
    private final Biome[] spawnBiomes;
    private final ItemStack itemWhenClickedWithJar;

    public int getId() {
        return this.id;
    }

    public String getParticleName() {
        return this.particleName;
    }

    public Biome[] getSpawnBiomes() {
        return this.spawnBiomes;
    }

    public ItemStack getItemWhenClickedWithJar() {
        return this.itemWhenClickedWithJar;
    }

    /**
     * Specify colors inside the lambda in the {@link ParticleHelper#createParticle(String name, ParticleHelper.ParticleLambda lambda)}
     * method using either:
     * <ul>
     *     <li>{@link ParticleHelper#setFireflyColorMin(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMid(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMax(EntityFireflyFX, float, float, float)}</li>
     * </ul>
     */
    public FireflyColor(int id, String particleName, ItemStack itemWhenClickedWithJar, Biome[] spawnBiomes) {
        for (FireflyColor color : FireflyHelper.registeredColors) {
            if (color.getId() == id) {
                throw new IllegalArgumentException("Id " + id + " is already used by " + color + " when adding " + this);
            }
        }

        this.id = id;
        this.particleName = particleName;
        this.spawnBiomes = spawnBiomes;
        this.itemWhenClickedWithJar = itemWhenClickedWithJar;
    }
}
