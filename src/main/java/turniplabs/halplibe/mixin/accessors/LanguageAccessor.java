package turniplabs.halplibe.mixin.accessors;

import net.minecraft.core.lang.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Properties;

@Mixin(value = Language.class)
public interface LanguageAccessor {
    @Accessor
    Properties getEntries();
}
