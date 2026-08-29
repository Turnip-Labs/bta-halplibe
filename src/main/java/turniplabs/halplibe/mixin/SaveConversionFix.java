package turniplabs.halplibe.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.ProgressListener;
import net.minecraft.core.world.save.conversion.SaveConverterMCRegionBase;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import turniplabs.halplibe.util.HalpLibeUtils;

import java.io.File;
import java.util.ArrayList;

@Mixin(value = SaveConverterMCRegionBase.class, remap = false)
public class SaveConversionFix {
    /**
     * <p>The original BTA code uses a counter to resolve the dimension.
     * This counter would count from 0 to the {@link Dimension#getDimensionList()} length.
     * Not only was this missing dimensions with an {@link Dimension#id} greater than 2,
     * but it would also corrupt worlds if a custom dimension was initialized before the BTA dimensions.
     * </p>
     * </br>
     * This mixin fixes this by resolving the dimension ID through the directory name instead of using the counter.</br>
     */
    @WrapOperation(
            method = "convertSave",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/world/save/conversion/SaveConverterMCRegionBase;convertDimensionRegions(Lnet/minecraft/core/world/Dimension;Ljava/util/ArrayList;IILnet/minecraft/core/world/ProgressListener;)I"
            )
    )
    private int fixConversionBug(
            SaveConverterMCRegionBase instance,
            @NotNull Dimension dimension,
            @NotNull ArrayList<File> files,
            int numberOfConversion, int totalConversions,
            @NotNull ProgressListener progressListener,
            Operation<Integer> original,
            @Local(name = "dimensionDir")File dir

    ) {
        String name = dir.getName();
        int id;
        try {
            id = Integer.parseInt(name);
        } catch (NumberFormatException e) {
            HalpLibeUtils.LOGGER.error("Skipping dimension conversion. Directory '{}' does is not a valid numeric dimension id.", name, e);
            return 0;
        }
        Dimension correctDim = Dimension.getDimensionList().get(id);
        if(correctDim == null){
            HalpLibeUtils.LOGGER.error("Skipping dimension conversion. No dimension is registered with id {}. (directory '{}').", id, name);
            return 0;
        }
        return original.call(instance, Dimension.getDimensionList().get(id), files, numberOfConversion, totalConversions, progressListener);
    }
}
