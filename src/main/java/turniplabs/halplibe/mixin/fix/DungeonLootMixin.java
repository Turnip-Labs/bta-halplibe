package turniplabs.halplibe.mixin.fix;

import net.minecraft.src.ItemStack;
import net.minecraft.src.WorldGenDungeon;
import org.spongepowered.asm.mixin.Mixin;
import turniplabs.halplibe.util.LootStack;

import java.util.Random;

import static turniplabs.halplibe.util.LootTables.dungeonLoot;

@Mixin(value = WorldGenDungeon.class, remap = false)
public class DungeonLootMixin {

    private ItemStack pickCheckLootItem(Random random) {
        ItemStack returnStack;
        LootStack loot = dungeonLoot.get( random.nextInt( dungeonLoot.size() ) );

        if ( loot != null ) returnStack = loot.generateLoot();
            else returnStack = null;

        return returnStack;
    }

}
