package turniplabs.halplibe.mixin.accessors;

import net.minecraft.client.util.dispatch.Dispatcher;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(
        value = Dispatcher.class,
        remap = false
)
@Debug(
        export = true
)
public interface DispatcherAccessor {

    @Invoker("addDispatch")
    <T, U> void callAddDispatch(T item, U dispatchable);
}
