package turniplabs.halplibe.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Global;
import net.minecraft.core.lang.I18n;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.event.defs.ServerEvents;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

@Environment(EnvType.SERVER)
@Mixin(value = MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow
    private static MinecraftServer instance;

    @Inject(method = "startServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/data/DataLoader;loadRecipesFromFile(Ljava/lang/String;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void recipeEntrypoint(CallbackInfoReturnable<Boolean> cir) {
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::initNamespaces);
        CommonEvents.RECIPES_NAMESPACE_INIT.emit(Runnable::run);
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::onRecipesReady);
        CommonEvents.RECIPES_READY.emit(Runnable::run);
    }

    @Inject(method = "startServer", at = @At("HEAD"))
    public void beforeGameStartEntrypoint(CallbackInfoReturnable<Boolean> cir) {
        instance = (MinecraftServer) (Object) this;
        Global.isServer = true;
        NetworkHandler.internalNetworkHandlerSetup();
        FabricLoader.getInstance().getEntrypoints("beforeGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::beforeGameStart);
        ServerEvents.BEFORE_SERVER_START.emit(Runnable::run);
        CommonEvents.BEFORE_GAME_START.emit(Runnable::run);
    }

        @Inject(method = "startServer", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V", ordinal = 1, shift = At.Shift.BEFORE))
    public void afterGameStartEntrypoint(CallbackInfoReturnable<Boolean> cir) {
        CreativeInventoryRegistry.INSTANCE.bakeAll();

        FabricLoader.getInstance().getEntrypoints("afterGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::afterGameStart);
        ServerEvents.AFTER_SERVER_START.emit(Runnable::run);
        CommonEvents.AFTER_GAME_START.emit(Runnable::run);
    }

    @Inject(method = "startServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;init()V"))
    public void printRecovery(CallbackInfoReturnable<Boolean> cir) {
        //before game start is too early and after game start is too late so thats why this is here
        if (HalpLibe.CONFIG.getBoolean("recoveryMode")) {
            HalpLibe.LOGGER.warn(I18n.getInstance().translateKey("halplibe.recoveryMode"));
            HalpLibe.LOGGER.warn(I18n.getInstance().translateKey("halplibe.recoveryMode.text"));
            HalpLibe.LOGGER.warn(I18n.getInstance().translateKey("halplibe.recoveryMode.text2"));
            HalpLibe.LOGGER.warn(I18n.getInstance().translateKey("halplibe.recoveryMode.action1"));
        }
    }

    @Inject(method = "startServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/Dimension;init()V", shift = At.Shift.AFTER))
    public void dimensionRegistry(CallbackInfoReturnable<Boolean> cir) {
        CommonEvents.DIMENSION_REGISTRY.emit(Runnable::run);
    }

    /*
     * @author sunsetsatellite
     * @reason begone log4j (this fixes logging not existing on a modded server at the cost of no gui)

    @Overwrite
    public static void main(String[] args) {
        StatList.init();

        try {
            MinecraftServer minecraftserver = new MinecraftServer();
            (new ThreadServerApplication("Server thread", minecraftserver)).start();
        } catch (Exception e) {
            logger.error("Failed to start the minecraft server", e);
        }

    }*/
}