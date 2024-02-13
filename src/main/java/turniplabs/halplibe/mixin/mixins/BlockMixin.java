package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixin {
    @Shadow public Block dropOverride;
    @Redirect(method = "dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;getBreakResult(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)[Lnet/minecraft/core/item/ItemStack;"))
    private ItemStack[] dropOverrideFix(Block instance, World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity){
        if (dropOverride != null){
            return new ItemStack[]{dropOverride.getDefaultStack()};
        }
        return instance.getBreakResult(world, dropCause, x, y, z, meta, tileEntity);
    }
}
