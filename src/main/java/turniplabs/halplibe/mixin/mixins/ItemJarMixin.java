package turniplabs.halplibe.mixin.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.animal.EntityFireflyCluster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemJar;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.util.FireflyColor;
import turniplabs.halplibe.util.IFireflyColor;

@Debug(export = true)
@Mixin(value = ItemJar.class, remap = false)
abstract public class ItemJarMixin extends ItemPlaceable {
    @Shadow
    public static void fillJar(EntityPlayer player, ItemStack itemToGive) {
    }

    public ItemJarMixin(String name, int id, Block blockToPlace) {
        super(name, id, blockToPlace);
    }

    @Inject(
            method = "onItemRightClick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/core/enums/EnumFireflyColor;BLUE:Lnet/minecraft/core/enums/EnumFireflyColor;"
            ),
            cancellable = true
    )
    private void inject(ItemStack itemstack, World world, EntityPlayer entityplayer, CallbackInfoReturnable<ItemStack> cir, @Local EntityFireflyCluster fireflyCluster) {
        FireflyColor color = ((IFireflyColor) fireflyCluster).halplibe$getColor();
        fillJar(entityplayer, color.getItemWhenClickedWithJar());
        cir.setReturnValue(itemstack);
    }
}
