package turniplabs.halplibe.mixin.mixins;

import net.minecraft.core.lang.I18n;
import net.minecraft.core.lang.Language;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.mixin.accessors.LanguageAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

@Mixin(
        value = I18n.class,
        remap = false
)
public abstract class I18nMixin {

    @Shadow private Language currentLanguage;

    @Unique
    private static String[] filesInDir(String directory) {
        List<String> paths = new ArrayList<>();
        if (!directory.endsWith("/")) {
            directory = directory + "/";
        }

        try {
            URI uri = I18n.class.getResource(directory).toURI();
            FileSystem fileSystem = null;
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                try {
                    fileSystem = FileSystems.getFileSystem(uri);
                } catch (Exception var9) {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                }

                myPath = fileSystem.getPath(directory);
            } else {
                myPath = Paths.get(uri);
            }

            Stream<Path> walk = Files.walk(myPath, 1);

            try {
                Iterator<Path> it = walk.iterator();
                it.next();

                while(it.hasNext()) {
                    paths.add(directory + it.next().getFileName().toString());
                }
            } catch (Throwable var10) {
                if (walk != null) {
                    try {
                        walk.close();
                    } catch (Throwable var8) {
                        var10.addSuppressed(var8);
                    }
                }

                throw var10;
            }

            walk.close();

            if (fileSystem != null) {
                fileSystem.close();
            }
        } catch (Exception ignored) {

        }

        return paths.toArray(new String[0]);
    }

    @Shadow
    public static InputStream getResourceAsStream(String path) {
        return null;
    }

    @Inject(
            method = "reload(Ljava/lang/String;Z)V",
            at = @At("TAIL")
    )
    public void addHalplibeModLangFiles(String languageCode, boolean save, CallbackInfo ci){
        Properties entries = ((LanguageAccessor)currentLanguage).getEntries();
        for (ModContainer mod : QuiltLoader.getAllMods()) {
            String[] langs = filesInDir("/lang/"+mod.metadata().id()+"/");
            HalpLibe.LOGGER.debug(mod.metadata().id()+" contains "+langs.length+" language files.");
            HalpLibe.LOGGER.debug(Arrays.toString(langs));
            for (String lang : langs) {
                try (InputStream stream = getResourceAsStream(lang)){//QuiltLauncherBase.getLauncher().getResourceAsStream(lang)) {
                    if (stream != null) {
                        entries.load(stream);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
