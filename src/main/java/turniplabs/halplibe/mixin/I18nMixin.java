package turniplabs.halplibe.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.lang.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.mixin.accessors.LanguageAccessor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(value = I18n.class, remap = false)
public abstract class I18nMixin {

    @Shadow
    private Language currentLanguage;

    @Unique
    private void loadLangFile(Path path, boolean subFolder) {
        String langCode = subFolder ? path.getParent().getFileName().toString() : path.getFileName().toString();
        Language currentLanguage = this.currentLanguage;
        Language defaultLanguage = Language.Default.INSTANCE;

        if (Files.isRegularFile(path) && path.toString().endsWith(".lang")) {
            try (InputStreamReader reader = new InputStreamReader(path.toUri().toURL().openStream(), StandardCharsets.UTF_8)) {
                if (langCode.contains(defaultLanguage.getId())) {
                    //noinspection DataFlowIssue (Suppressing IntelliJ ClassCastException warning)
                    ((LanguageAccessor) defaultLanguage).getEntries().load(reader);
                } else if (langCode.contains(currentLanguage.getId())) {
                    ((LanguageAccessor) currentLanguage).getEntries().load(reader);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Inject(
            method = "reload(Ljava/lang/String;Z)V",
            at = @At("TAIL")
    )
    public void addHalplibeModLangFiles(String languageCode, boolean save, CallbackInfo ci) {
        Language defaultLanguage = Language.Default.INSTANCE;
        String defaultLangId = defaultLanguage.getId();
        String currentLangId = currentLanguage.getId();
        HalpLibe.LOGGER.debug("Current lang: " + currentLangId);

        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            String modId = mod.getMetadata().getId();
            Optional<Path> optionalPath = mod.findPath("lang/" + modId); // Get the path to lang/modid/ folder
            if (!optionalPath.isPresent()) continue;

            // Get all files inside folder
            try (Stream<Path> stream = Files.list(optionalPath.get())) {
                List<Path> list = stream.collect(Collectors.toList());

                for (Path p : list) {
                    String fileName = p.getFileName().toString();
                    // Skip if it doesn't match any lang ID
                    if(!(fileName.contains(currentLangId) || fileName.contains(defaultLangId))) continue;

                    if (Files.isDirectory(p)) { // Read subfolder lang files
                        try (Stream<Path> subStream = Files.list(p)) {
                            subStream.forEach(subP -> loadLangFile(subP, true)); // Try to read lang files in subdirectory
                        } catch (IOException e) {
                            HalpLibe.LOGGER.error("Failed to read .lang files of [{}] in folder {}!", modId, p.getFileName());
                        }
                    }else /* Read root lang file */ {
                        loadLangFile(p, false);
                    }
                }
            } catch (IOException e) {
                HalpLibe.LOGGER.error("Failed to read .lang files of [{}]!", modId);
            }
        }
    }
}
