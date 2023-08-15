package turniplabs.halplibe.helper;

import net.minecraft.client.render.block.color.BlockColor;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.client.sound.block.BlockSoundDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import turniplabs.halplibe.mixin.accessors.BlockAccessor;
import turniplabs.halplibe.mixin.accessors.BlockFireAccessor;
import turniplabs.halplibe.mixin.accessors.DispatcherAccessor;

import java.util.Set;
import java.util.TreeSet;

public class BlockBuilder implements Cloneable {
    public static final Set<Integer> infiniburnList = new TreeSet<>();

    private final String MOD_ID;
    private Float hardness = null;
    private Float resistance = null;
    private Integer luminance = null;
    private Integer lightOpacity = null;
    private boolean immovable = false;
    private boolean useInternalLight = false;
    private boolean disabledNNOMC = false;
    private int[] flammability = null;
    private boolean infiniburn = false;
    private Block blockDrop = null;
    private BlockSound blockSound = null;
    private BlockColor blockColor = null;
    private BlockModel blockModel = null;
    private BlockLambda<ItemBlock> customItemBlock = null;
    private Tag<Block>[] tags = null;
    private int[] topTexture = new int[2];
    private int[] bottomTexture = new int[2];
    private int[] northTexture = new int[2];
    private int[] eastTexture = new int[2];
    private int[] southTexture = new int[2];
    private int[] westTexture = new int[2];

    public BlockBuilder(String modId) {
        MOD_ID = modId;
    }

    @Override
    public BlockBuilder clone() {
        try {
            // none of the fields are mutated so this should be fine
            return (BlockBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Sets the block's time to break.
     */
    public BlockBuilder setHardness(float hardness) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.hardness = hardness;
        return blockBuilder;
    }

    /**
     * Sets the block's resistance against explosions.
     */
    public BlockBuilder setResistance(float resistance) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.resistance = resistance;
        return blockBuilder;
    }

    /**
     * Sets the block's light emitting capacity.
     *
     * @param luminance ranges from 0 to 15
     */
    public BlockBuilder setLuminance(int luminance) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.luminance = luminance;
        return blockBuilder;
    }

    /**
     * Sets the block's ability for light to pass through it.
     * Block light and sunlight (once it encounters a non-transparent block) decreases its intensity by 1,
     * so when passing through a block with opacity 1, it will actually decrease by 2.
     *
     * @param lightOpacity ranges from 0 to 15
     */
    public BlockBuilder setLightOpacity(int lightOpacity) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.lightOpacity = lightOpacity;
        return blockBuilder;
    }

    /**
     * Makes a block unable to be moved by pistons.
     */
    public BlockBuilder setImmovable() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.immovable = true;
        return blockBuilder;
    }

    /**
     * Makes a block unable to be broken.
     */
    public BlockBuilder setUnbreakable() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.hardness = -1.0f;
        return blockBuilder;
    }

    /**
     * Makes a block drop a different block than itself upon breaking.
     */
    public BlockBuilder setBlockDrop(Block droppedBlock) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockDrop = droppedBlock;
        return blockBuilder;
    }

    /**
     * Makes a block's interior faces get light from the block's position.
     * Used for things like slabs, stairs, layers and various other non-full
     * blocks that allow light to pass through them.
     */
    public BlockBuilder setUseInternalLight() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.useInternalLight = true;
        return blockBuilder;
    }

    /**
     * Sets the block's flammability.
     *
     * @param chanceToCatchFire how likely it is for the block to catch fire
     *                          non-destructively
     * @param chanceToDegrade   how likely it is for the block to burn itself
     *                          to ash and disappear
     */
    public BlockBuilder setFlammability(int chanceToCatchFire, int chanceToDegrade) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.flammability = new int[]{chanceToCatchFire, chanceToDegrade};
        return blockBuilder;
    }

    public BlockBuilder setInfiniburn() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.infiniburn = true;
        return blockBuilder;
    }

    public BlockBuilder setBlockSound(BlockSound blockSound) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockSound = blockSound;
        return blockBuilder;
    }

    public BlockBuilder setBlockColor(BlockColor color) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockColor = color;
        return blockBuilder;
    }

    public BlockBuilder setBlockModel(BlockModel model) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockModel = model;
        return blockBuilder;
    }

    public BlockBuilder setDisabledNeighborNotifyOnMetadataChange() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.disabledNNOMC = true;
        return blockBuilder;
    }

    public BlockBuilder setItemBlock(BlockLambda<ItemBlock> customItemBlock) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.customItemBlock = customItemBlock;
        return blockBuilder;
    }

    @SafeVarargs
    public final BlockBuilder setTags(Tag<Block>... tags) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.tags = tags;
        return blockBuilder;
    }

    public BlockBuilder setTextures(String texture) {
        int[] one = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.topTexture = one;
        blockBuilder.bottomTexture = one;
        blockBuilder.northTexture = one;
        blockBuilder.eastTexture = one;
        blockBuilder.southTexture = one;
        blockBuilder.westTexture = one;

        return blockBuilder;
    }

    public BlockBuilder setTextures(int x, int y) {
        int[] one = new int[]{x, y};

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.topTexture = one;
        blockBuilder.bottomTexture = one;
        blockBuilder.northTexture = one;
        blockBuilder.eastTexture = one;
        blockBuilder.southTexture = one;
        blockBuilder.westTexture = one;

        return blockBuilder;
    }

    public BlockBuilder setSides(String texture) {
        int[] sides = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.northTexture = sides;
        blockBuilder.eastTexture = sides;
        blockBuilder.southTexture = sides;
        blockBuilder.westTexture = sides;

        return blockBuilder;
    }

    public BlockBuilder setSides(int x, int y) {
        int[] sides = new int[]{x, y};

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.northTexture = sides;
        blockBuilder.eastTexture = sides;
        blockBuilder.southTexture = sides;
        blockBuilder.westTexture = sides;

        return blockBuilder;
    }

    public BlockBuilder setTopBottomTexture(String texture) {
        int[] topBottom = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.topTexture = topBottom;
        blockBuilder.bottomTexture = topBottom;

        return blockBuilder;
    }

    public BlockBuilder setTopBottomTexture(int x, int y) {
        int[] topBottom = new int[]{x, y};

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.topTexture = topBottom;
        blockBuilder.bottomTexture = topBottom;

        return blockBuilder;
    }

    public BlockBuilder setTopTexture(String texture) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.topTexture = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);
        return blockBuilder;
    }

    public BlockBuilder setTopTexture(int x, int y) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.topTexture = new int[]{x, y};
        return blockBuilder;
    }

    public BlockBuilder setBottomTexture(String texture) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.bottomTexture = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);
        return blockBuilder;
    }

    public BlockBuilder setBottomTexture(int x, int y) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.bottomTexture = new int[]{x, y};
        return blockBuilder;
    }

    public BlockBuilder setNorthTexture(String texture) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.northTexture = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);
        return blockBuilder;
    }

    public BlockBuilder setNorthTexture(int x, int y) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.northTexture = new int[]{x, y};
        return blockBuilder;
    }

    public BlockBuilder setEastTexture(String texture) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.eastTexture = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);
        return blockBuilder;
    }

    public BlockBuilder setEastTexture(int x, int y) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.eastTexture = new int[]{x, y};
        return blockBuilder;
    }

    public BlockBuilder setSouthTexture(String texture) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.southTexture = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);
        return blockBuilder;
    }

    public BlockBuilder setSouthTexture(int x, int y) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.southTexture = new int[]{x, y};
        return blockBuilder;
    }

    public BlockBuilder setWestTexture(String texture) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.westTexture = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);
        return blockBuilder;
    }

    public BlockBuilder setWestTexture(int x, int y) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.westTexture = new int[]{x, y};
        return blockBuilder;
    }

    public Block build(Block block) {
        block.withTexCoords(
                topTexture[0], topTexture[1],
                bottomTexture[0], bottomTexture[1],
                northTexture[0], northTexture[1],
                eastTexture[0], eastTexture[1],
                southTexture[0], southTexture[1],
                westTexture[0], westTexture[1]
        );

        if (hardness != null) {
            ((BlockAccessor) block).callSetHardness(hardness);
        }

        if (resistance != null) {
            ((BlockAccessor) block).callSetResistance(resistance);
        }

        if (luminance != null) {
            ((BlockAccessor) block).callSetLightValue(luminance);
        }

        if (lightOpacity != null) {
            ((BlockAccessor) block).callSetLightOpacity(lightOpacity);
        }

        ((BlockAccessor) block).callSetIsLitInteriorSurface(useInternalLight);

        if (immovable) {
            ((BlockAccessor) block).callSetImmovable();
        }

        if (flammability != null) {
            ((BlockFireAccessor) Block.fire).callSetBurnRate(block.id, flammability[0], flammability[1]);
        }

        if (infiniburn) {
            infiniburnList.add(block.id);
        }

        if (disabledNNOMC) {
            ((BlockAccessor) block).callWithDisabledNeighborNotifyOnMetadataChange();
        }

        if (blockDrop != null) {
            ((BlockAccessor) block).callSetDropOverride(blockDrop);
        }

        if (blockSound != null) {
            ((DispatcherAccessor) BlockSoundDispatcher.getInstance()).callAddDispatch(block, blockSound);
        }

        if (blockColor != null) {
            BlockColorDispatcher.getInstance().addDispatch(block, blockColor);
        }

        if (blockModel != null) {
            BlockModelDispatcher.getInstance().addDispatch(block, blockModel);
        }

        if (customItemBlock != null) {
            Item.itemsList[block.id] = customItemBlock.run(block);
        } else {
            Item.itemsList[block.id] = new ItemBlock(block);
        }

        if (tags != null) {
            block.withTags(tags);
        }

        block.setKey(MOD_ID + "." + block.getKey().substring(5));

        return block;
    }

    private interface BlockLambda<T> {
        T run(Block block);
    }
}
