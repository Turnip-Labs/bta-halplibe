package turniplabs.halplibe.mixin.recovery;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.block.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import turniplabs.halplibe.HalpLibe;

@Mixin(value = WorldClient.class,remap = false)
public abstract class WorldClientMixin {

    @WrapWithCondition(method = "updateTileEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/entity/TileEntity;tick()V"))
    public boolean disableTickingTileEntities(TileEntity instance) {
        return !HalpLibe.CONFIG.getBoolean("recoveryMode");
    }

}
