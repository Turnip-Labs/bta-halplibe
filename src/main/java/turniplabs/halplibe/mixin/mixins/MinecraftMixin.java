package turniplabs.halplibe.mixin.mixins;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

@Mixin(
        value = Minecraft.class,
        remap = false
)

public class MinecraftMixin {

    @Inject(method = "startGame", at = @At(value = "INVOKE",target = "Lnet/minecraft/core/data/DataLoader;loadRecipes(Ljava/lang/String;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void recipeEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::onRecipesReady);
        RecipeBuilder.Shapeless(HalpLibe.MOD_ID)
                .addInput(Block.dirt)
                .addInput(Block.cobweb)
                .addInput(Block.algae)
                .create("testShapeless", Block.blockDiamond.getDefaultStack());
        RecipeBuilder.Shaped(HalpLibe.MOD_ID,
                        "X X",
                        " X ",
                        "X X")
                .addInput('X', Block.mobspawner)
                .create("testShaped", Item.doorIron.getDefaultStack());

        RecipeBuilderShaped stairsTemplate = RecipeBuilder.Shaped(HalpLibe.MOD_ID,
                "X  ",
                "XX ",
                "XXX");
        stairsTemplate.addInput('X', Block.dirt).create("stairs1", Block.stairsBrickLimestone.getDefaultStack());
        stairsTemplate.addInput('X', Block.cobweb).create("stairs2", Block.blockDiamond.getDefaultStack());
        stairsTemplate.addInput('X', Block.mesh).create("stairs3", Block.seat.getDefaultStack());
        RecipeBuilder.ModifyTrommel("minecraft", "dirt")
                .addEntry(new WeightedRandomLootObject(Item.diamond.getDefaultStack(), 1, 100), 20)
                .addEntry(new WeightedRandomLootObject(Item.flag.getDefaultStack(), 5), 2)
                .removeEntries(Item.ammoPebble.getDefaultStack());
        RecipeBuilder.ModifyTrommel("minecraft", "clay")
                .setWeights(Item.clay.getDefaultStack(), 200);
        RecipeBuilder.Trommel(HalpLibe.MOD_ID)
                .setInput(Block.cobbleBasalt)
                .addEntry(new WeightedRandomLootObject(Item.olivine.getDefaultStack()), 1)
                .create("cobbled_basalt");
        RecipeBuilder.ModifyWorkbench("minecraft").removeRecipe("block_of_diamond").removeRecipe("block_of_diamond_to_diamond").removeRecipe("cake");
        RecipeBuilder.ModifyBlastFurnace("minecraft").removeRecipe("coal_ores_to_coal");
        RecipeBuilder.ModifyFurnace("minecraft").removeRecipe("porkchop_raw_to_porkchop_cooked");
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    public void beforeGameStartEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("beforeClientStart", ClientStartEntrypoint.class).forEach(ClientStartEntrypoint::beforeClientStart);
        FabricLoader.getInstance().getEntrypoints("beforeGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::beforeGameStart);
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    public void afterGameStartEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("afterGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::afterGameStart);
        FabricLoader.getInstance().getEntrypoints("afterClientStart", ClientStartEntrypoint.class).forEach(ClientStartEntrypoint::afterClientStart);
    }
}
