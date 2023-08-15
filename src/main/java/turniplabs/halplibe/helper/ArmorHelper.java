package turniplabs.halplibe.helper;

import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.apache.commons.lang3.ArrayUtils;
import turniplabs.halplibe.mixin.accessors.RenderPlayerAccessor;

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
        String[] armorFilenamePrefix = RenderPlayerAccessor.getArmorFilenamePrefix();
        armorFilenamePrefix = ArrayUtils.add(armorFilenamePrefix, textureName);
        RenderPlayerAccessor.setArmorFilenamePrefix(armorFilenamePrefix);

        ArmorMaterial armorMaterial = new ArmorMaterial(textureName, armorFilenamePrefix.length - 1, durability);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.COMBAT, combat);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.BLAST, blast);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.FIRE, fire);
        ArmorMaterial.setProtectionValuePercent(armorMaterial, DamageType.FALL, fall);

        return armorMaterial;
    }
}
