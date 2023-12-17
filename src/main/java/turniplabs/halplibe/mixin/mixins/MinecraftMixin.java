package turniplabs.halplibe.mixin.mixins;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

@Mixin(
        value = Minecraft.class,
        remap = false
)

public class MinecraftMixin {

    @Inject(method = "startGame", at = @At(value = "INVOKE",target = "Lnet/minecraft/core/data/DataLoader;loadRecipes(Ljava/lang/String;)V",shift = At.Shift.BEFORE))
    public void recipeEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::onRecipesReady);
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    public void beforeGameStartEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("beforeGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::beforeGameStart);
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    public void afterGameStartEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("afterGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::afterGameStart);
    }
}
