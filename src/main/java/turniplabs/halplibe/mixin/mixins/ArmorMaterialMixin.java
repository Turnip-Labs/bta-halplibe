package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.item.material.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.util.ArmorMaterialMixinInterface;

@Mixin(value = ArmorMaterial.class, remap = false)
abstract public class ArmorMaterialMixin implements ArmorMaterialMixinInterface {
    @Unique
    private String modId = null;

    public String halplibe$getModId() {
        return modId;
    }

    public void halplibe$SetModId(String modId) {
        this.modId = modId;
    }
}
