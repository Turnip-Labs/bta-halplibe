package turniplabs.halplibe.helper;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityDispatcher;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.factories.EntityFactory;
import net.minecraft.core.util.collection.NamespaceID;
import org.jspecify.annotations.NonNull;

public final class EntityHelper {

    @SuppressWarnings("unused")
    public <T extends Entity> @NonNull EntityFactory<T> createEntity(@NonNull Class<T> entityClass, @NonNull NamespaceID namespaceID, @NonNull EntityFactory<T> factory) {
       return EntityDispatcher.getInstance().addMapping(entityClass, namespaceID, factory);
    }

    @SuppressWarnings("unused")
    public static void addMapping(@NonNull Class<? extends TileEntity> entityClass, @NonNull NamespaceID id) {
        TileEntityDispatcher.addMapping(entityClass, id);
    }
}
