package turniplabs.halplibe.util;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.Random;

public final class LootStack {

    public Item item;
    // the actual item

    public int rarity;
    // how rare it is, 99 equals a one hundred in one chance of finding it etc.

    public int maxQuantity;
    public int minQuantity;
    public int maxMetadata;
    public int minMetadata;

    // the range of damage values a given item can spawn with.


    public LootStack(Item item, int rarity, int maxQuantity, int minQuantity, int maxMetadata, int minMetadata) {
        this.item = item;
        this.rarity = rarity;
        this.maxQuantity = maxQuantity;
        this.minQuantity = minQuantity;
        this.maxMetadata = maxMetadata;
        this.minMetadata = minMetadata;
    }

    public LootStack(Block block, int rarity, int maxQuantity, int minQuantity, int maxMetadata, int minMetadata) {
        this.item = new ItemStack(block).getItem();
        this.rarity = rarity;
        this.maxQuantity = maxQuantity;
        this.minQuantity = minQuantity;
        this.maxMetadata = maxMetadata;
        this.minMetadata = minMetadata;
    }

    public LootStack(Item item){
        this(item, 0, 1, 1,0, 0);
    }

    public LootStack(Item item,int rarity){
        this(item, rarity, 1, 1,0, 0);
    }

    public LootStack(Item item,int rarity, int maxQuantity){
        this(item, rarity, maxQuantity, 1, 0, 0);
    }

    public LootStack(Block block, int rarity, int maxQuantity){
        this( block, rarity, maxQuantity, 1, 0, 0);
    }

    public LootStack(Item item,int rarity, int maxQuantity, int minQuantity){
        this(item, rarity, maxQuantity, minQuantity, 0, 0);
    }

    private final Random random = new Random();
    public ItemStack generateLoot() {
        int quantity = 1;
        int metadata;

        if ( this.rarity != 0) {

            if (random.nextInt(rarity) == 0) {

                if (this.maxQuantity - minQuantity > 1)
                    quantity = random.nextInt(this.maxQuantity - this.minQuantity) + this.minQuantity;

                if (this.maxMetadata - minMetadata > 0)
                    metadata = random.nextInt( this.maxMetadata - this.minMetadata) + this.minMetadata;
                    else metadata = this.minMetadata;

                } else return null;

        } else {

            // if rarity equals to zero.
            if (this.maxQuantity - minQuantity > 1)
                quantity = random.nextInt(this.maxQuantity - this.minQuantity) + this.minQuantity;

            if (this.maxMetadata - minMetadata > 0)
                metadata = random.nextInt( this.maxMetadata - this.minMetadata) + this.minMetadata;
                else metadata = this.minMetadata;

        }

        return  new ItemStack(this.item, quantity, metadata);
    }

}
