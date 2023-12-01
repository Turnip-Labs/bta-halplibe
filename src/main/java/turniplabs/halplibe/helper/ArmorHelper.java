package turniplabs.halplibe.helper;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;

public class ArmorHelper {

    /**
     * Damage parameters can be more than 100%. Armor durability will be a bit different from in-game.
     *
     * @param textureName name of the armor texture file
     * @param durability  durability of your armor
     * @param combat      combat damage reduction in percent
     * @param blast       blast damage reduction in percent
     * @param fire        fire damage reduction in percent
     * @param fall        fall damage reduction in percent
     * @return the new ArmorMaterial.
     */
    public static ArmorMaterial createArmorMaterial(String textureName, int durability, float combat, float blast, float fire, float fall) {
        ArmorMaterial armorMaterial = new ArmorMaterial(textureName, ArmorMaterial.getArmorMaterials().size()-1, durability)
        .withProtectionPercentage(DamageType.COMBAT, combat)
        .withProtectionPercentage(DamageType.BLAST, blast)
        .withProtectionPercentage(DamageType.FIRE, fire)
        .withProtectionPercentage(DamageType.FALL, fall);
        ArmorMaterial.register(armorMaterial);

        return armorMaterial;
    }
}
