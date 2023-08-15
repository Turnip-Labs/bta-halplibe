package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFire;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import turniplabs.halplibe.helper.BlockBuilder;

@Mixin(value = BlockFire.class, remap = false)
public abstract class BlockFireMixin {
    @Redirect(
            method = "updateTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/world/World;getBlockId(III)I",
                    ordinal = 0
            )
    )
    private int checkInfiniburn(World world, int x, int y, int z) {
        int id = world.getBlockId(x, y, z);
        return BlockBuilder.infiniburnList.contains(id) ? Block.netherrack.id : id;
    }
}
