package turniplabs.halplibe.helper;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;

public final class ArmorHelper {

    /**
     * Creates a new ArmorMaterial with custom protection values.
     *
     * @param modId       Your mod ID
     * @param name        Name of the armor material (used for texture files, e.g. "sapphire")
     * @param durability  Base durability of the armor pieces
     * @param combat      Protection against combat/melee damage (in percent, 0-100+)
     * @param blast       Protection against explosions
     * @param fire        Protection against fire/lava
     * @param fall        Protection against fall damage
     * @param drown       Protection against drowning (optional, defaults to 0%)
     * @param generic     Protection against generic/other damage (optional, defaults to 0%)
     */
    @SuppressWarnings("unused")
    public static ArmorMaterial createArmorMaterial(String modId, String name, int durability, float combat, float blast, float fire, float fall, float drown, float generic) {
        ArmorMaterial armorMaterial = new ArmorMaterial(NamespaceID.fromPool(modId, name), durability)
                .withProtectionPercentage(DamageType.COMBAT, combat)
                .withProtectionPercentage(DamageType.BLAST, blast)
                .withProtectionPercentage(DamageType.FIRE, fire)
                .withProtectionPercentage(DamageType.FALL, fall)
                .withProtectionPercentage(DamageType.DROWN, drown)
                .withProtectionPercentage(DamageType.GENERIC, generic);

        return ArmorMaterial.register(armorMaterial);
    }

    @SuppressWarnings("unused")
    public static ArmorMaterial createArmorMaterial(String modId, String name, int durability, float combat, float blast, float fire, float fall) {
        return createArmorMaterial(modId, name, durability, combat, blast, fire, fall, 0.0f, 0.0f);
    }
}
