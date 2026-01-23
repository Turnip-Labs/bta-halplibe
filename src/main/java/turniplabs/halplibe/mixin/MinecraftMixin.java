package turniplabs.halplibe.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.popup.PopupBuilder;
import net.minecraft.client.gui.popup.PopupComponent;
import net.minecraft.client.gui.popup.PopupScreen;
import net.minecraft.core.enums.EnumOS;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.network.NetworkHandler;
import turniplabs.halplibe.mixin.accessors.ItemsAccessor;
import turniplabs.halplibe.util.*;

@Mixin(
        value = Minecraft.class,
        remap = false
)

public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public Screen currentScreen;

    @Shadow
    public abstract void displayScreen(Screen screen);

    @Inject(method = "startGame", at = @At(value = "INVOKE",target = "Lnet/minecraft/core/data/DataLoader;loadRecipesFromFile(Ljava/lang/String;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void recipeEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::initNamespaces);
        FabricLoader.getInstance().getEntrypoints("recipesReady", RecipeEntrypoint.class).forEach(RecipeEntrypoint::onRecipesReady);
    }

    @Inject(method = "startGame", at = @At("HEAD"))
    public void beforeGameStartEntrypoint(CallbackInfo ci){
        FabricLoader.getInstance().getEntrypoints("beforeClientStart", ClientStartEntrypoint.class).forEach(ClientStartEntrypoint::beforeClientStart);
        FabricLoader.getInstance().getEntrypoints("beforeGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::beforeGameStart);
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    public void afterGameStartEntrypoint(CallbackInfo ci){
        NetworkHandler.internalNetworkHandlerSetup();
        FabricLoader.getInstance().getEntrypoints("afterGameStart", GameStartEntrypoint.class).forEach(GameStartEntrypoint::afterGameStart);
        FabricLoader.getInstance().getEntrypoints("afterClientStart", ClientStartEntrypoint.class).forEach(ClientStartEntrypoint::afterClientStart);
        if(HalpLibe.CONFIG.getBoolean("recoveryMode")) {
            PopupScreen popup = new PopupBuilder(this.currentScreen, 246)
                    .withLabelLiteral(I18n.getInstance().translateKey("halplibe.recoveryMode"))
                    .withMessageBox("message",128,
                            I18n.getInstance().translateKey("halplibe.recoveryMode.text")+"\n\n"
                                    + I18n.getInstance().translateKey("halplibe.recoveryMode.text2")+"\n"
                                    + I18n.getInstance().translateKey("halplibe.recoveryMode.action1")+"\n"
                                    + I18n.getInstance().translateKey("halplibe.recoveryMode.action2")+"\n\n"
                                    + I18n.getInstance().translateKey("halplibe.recoveryMode.text3"),44)
                    .closeOnClickOut(0)
                    .build();
            displayScreen(popup);
        }
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Blocks;init()V", shift = At.Shift.AFTER))
    public void afterBlockInitEntrypoint(CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("afterBlockInit", BlockInitEntrypoint.class).forEach(BlockInitEntrypoint::afterBlockInit);
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/Items;init()V", shift = At.Shift.AFTER))
    public void afterItemInitEntrypoint(CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("afterItemInit", ItemInitEntrypoint.class).forEach(ItemInitEntrypoint::afterItemInit);
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/achievement/stat/StatList;init()V"))
    public void initStats(CallbackInfo ci) {
        ItemsAccessor.invokeInitStats();
    }

    @Inject(method = "printWrongJavaVersionInfo", at = @At("HEAD"), cancellable = true)
    private void printWrongJavaVersionInfo(CallbackInfo ci) {
        if (Minecraft.getOs() == EnumOS.linux){
            System.out.println("If the game crashes with a message similar to \n\"Inconsistency detected by ld.so: dl-lookup.c: 111: check_match: Assertion `version->filename == NULL || ! _dl_name_match_p (version->filename, map)' failed!\", \nEither use Java 8 or 17 from Eclipse Adoptium!");
        }
        ci.cancel();
    }
}
