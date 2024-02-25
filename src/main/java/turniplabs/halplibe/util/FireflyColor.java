package turniplabs.halplibe.util;

import net.minecraft.client.entity.fx.EntityFireflyFX;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.biome.Biome;
import turniplabs.halplibe.helper.FireflyHelper;
import turniplabs.halplibe.helper.ParticleHelper;

import java.util.*;
import java.util.stream.Collectors;

public class FireflyColor {
    private final int id;
    private final String particleName;
    private final Map<Biome, Float> biomeAndWeights;
    private final ItemStack itemWhenClickedWithJar;

    public int getId() {
        return this.id;
    }

    public String getParticleName() {
        return this.particleName;
    }

    public Map<Biome, Float> getBiomeAndWeights() {
        return this.biomeAndWeights;
    }

    public ItemStack getItemWhenClickedWithJar() {
        return this.itemWhenClickedWithJar.copy();
    }

    /**
     * Specify colors inside the lambda in the {@link ParticleHelper#createParticle(String name, ParticleHelper.ParticleLambda lambda)}
     * method using either:
     * <ul>
     *     <li>{@link ParticleHelper#setFireflyColor(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMin(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMid(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMax(EntityFireflyFX, float, float, float)}</li>
     * </ul>
     */
    public FireflyColor(int id, String particleName, ItemStack itemWhenClickedWithJar, Biome[] biomes) {
        for (FireflyColor color : FireflyHelper.registeredColors) {
            if (color.getId() == id) {
                throw new IllegalArgumentException("Id " + id + " is already used by " + color + " when adding " + this);
            }
        }

        Map<Biome, Float> biomeAndWeights = new HashMap<>();
        for (Biome biome : biomes) {
            biomeAndWeights.put(biome, 1f);
        }

        this.id = id;
        this.particleName = particleName;
        this.biomeAndWeights = biomeAndWeights;
        this.itemWhenClickedWithJar = itemWhenClickedWithJar.copy();
    }

    /**
     * Specify colors inside the lambda in the {@link ParticleHelper#createParticle(String name, ParticleHelper.ParticleLambda lambda)}
     * method using either:
     * <ul>
     *     <li>{@link ParticleHelper#setFireflyColor(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMin(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMid(EntityFireflyFX, float, float, float)}</li>
     *     <li>{@link ParticleHelper#setFireflyColorMax(EntityFireflyFX, float, float, float)}</li>
     * </ul>
     *
     * @param biomeAndWeights format should be biome followed by integer weight.
     */
    public FireflyColor(int id, String particleName, ItemStack itemWhenClickedWithJar, Object[] biomeAndWeights) {
        for (FireflyColor color : FireflyHelper.registeredColors) {
            if (color.getId() == id) {
                throw new IllegalArgumentException("Id " + id + " is already used by " + color + " when adding " + this);
            }
        }

        if (biomeAndWeights.length % 2 != 0) {
            throw new IllegalArgumentException("Mismatched biome and weight arguments!");
        }

        Map<Biome, Float> biomeAndWeights2 = new HashMap<>();
        for (int index = 0; index < biomeAndWeights.length; index += 2) {
            if (biomeAndWeights[index] instanceof Biome && biomeAndWeights[index + 1] instanceof Integer) {
                float weight = (float) (int) biomeAndWeights[index + 1];
                if (weight < 0) {
                    weight = 1f / weight;
                }

                biomeAndWeights2.put((Biome) biomeAndWeights[index], weight);
            } else {
                throw new IllegalArgumentException("Incorrect format for " + this + "! Should be biome followed by integer weight");
            }
        }

        this.id = id;
        this.particleName = particleName;
        this.biomeAndWeights = biomeAndWeights2;
        this.itemWhenClickedWithJar = itemWhenClickedWithJar.copy();
    }
}
