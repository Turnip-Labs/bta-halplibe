package turniplabs.halplibe.util;

import net.minecraft.src.ItemStack;

import java.util.Random;

public final class LootStack {

    public ItemStack itemStack;
    // the actual item

    public int rarity;
    // how rare it is, 99 equals a one hundred in one chance of finding it etc.

    public int maxQuantity;
    // do NOT put anything under 1.

    public int metadataRange;
    // the range of damage values a given item can spawn with.
    // NOTE: the resulting value is added to the original damage value, not overriding it.


    public LootStack(ItemStack itemStack, int rarity, int maxQuantity, int metadataRange) {
        this.itemStack = itemStack;
        this.rarity = rarity;
        this.maxQuantity = maxQuantity;
        this.metadataRange = metadataRange;
    }

    public LootStack(ItemStack itemStack){
        this(itemStack, 0, 1,0);
    }

    public LootStack(ItemStack itemStack,int rarity){
        this(itemStack, rarity, 1,0);
    }

    public LootStack(ItemStack itemStack,int rarity, int maxQuantity){
        this(itemStack, rarity, maxQuantity,0);
    }

    private final Random random = new Random();
    public ItemStack generateLoot() {
        ItemStack outStack;

        if ( this.rarity != 0) {

            if (random.nextInt(rarity) == 0) {
                outStack = new ItemStack(this.itemStack.getItem());

                if (this.maxQuantity > 1)
                    outStack.stackSize = 1 + random.nextInt(this.maxQuantity);

                if (this.metadataRange > 0)
                    outStack.setMetadata( random.nextInt(this.metadataRange) );

                } else outStack = null;

        } else {

            // if rarity equals to zero.
            outStack = this.itemStack;

            if (this.maxQuantity > 1)
                outStack.stackSize = 1 + random.nextInt(this.maxQuantity);

            if (this.metadataRange > 0)
                outStack.setMetadata( random.nextInt(this.metadataRange) );

            }

        return  outStack;
    }

}
