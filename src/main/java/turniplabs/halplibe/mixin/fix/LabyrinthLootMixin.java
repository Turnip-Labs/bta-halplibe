package turniplabs.halplibe.mixin.fix;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WorldGenLabyrinth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import turniplabs.halplibe.util.LootStack;

import java.util.Random;

import static turniplabs.halplibe.util.LootTables.dungeonLoot;
import static turniplabs.halplibe.util.LootTables.labyrinthLoot;

@Mixin (value = WorldGenLabyrinth.class, remap = false)
public abstract class LabyrinthLootMixin {

    @Accessor("dungeonSize")
    abstract int getDungeonSize();

    @Accessor("treasureGenerated")
    abstract boolean getTreasureGenerated();

    @Accessor("treasureGenerated")
    abstract void setTreasureGenerated(boolean value);

    private ItemStack pickCheckLootItem(Random random) {
        if ( !this.getTreasureGenerated() && this.getDungeonSize() > 7){
            this.setTreasureGenerated(true);
            return new ItemStack(Item.armorQuiverGold);
            }

        ItemStack returnStack;
        LootStack loot = labyrinthLoot.get( random.nextInt( labyrinthLoot.size() ) );

        if ( loot != null ) returnStack = loot.generateLoot();
        else returnStack = null;

        return returnStack;
    }

}
