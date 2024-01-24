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
import net.minecraft.core.util.helper.Side;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import turniplabs.halplibe.mixin.accessors.BlockFireAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BlockBuilder implements Cloneable {
    public static final Set<Integer> infiniburnList = new TreeSet<>();
    static {
        infiniburnList.add(Block.netherrack.id);
    }

    private final String MOD_ID;
    private Float hardness = null;
    private Float resistance = null;
    private Integer luminance = null;
    private Integer lightOpacity = null;
    private Float slipperiness = null;
    private boolean immovable = false;
    private boolean useInternalLight = false;
    private boolean visualUpdateOnMetadata = false;
    private Boolean tickOnLoad = null;
    private boolean infiniburn = false;
    private int[] flammability = null;
    private Block blockDrop = null;
    private BlockSound blockSound = null;
    private BlockColor blockColor = null;
    private BlockModel blockModel = null;
    private BlockLambda<ItemBlock> customItemBlock = null;
    private Tag<Block>[] tags = null;
    private int[] topTexture = null;
    private int[] bottomTexture = null;
    private int[] northTexture = null;
    private int[] eastTexture = null;
    private int[] southTexture = null;
    private int[] westTexture = null;

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
     * Sets how long it takes to break the block.
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
     * Sets the block's ability for light to pass through it.<br>
     * Block light and sunlight (once it encounters a non-transparent block) decreases
     * its intensity by 1 every block travelled.<br>
     * Therefore, when passing through a block with opacity 1, it will actually decrease by 2.
     *
     * @param lightOpacity ranges from 0 to 15
     */
    public BlockBuilder setLightOpacity(int lightOpacity) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.lightOpacity = lightOpacity;
        return blockBuilder;
    }

    /**
     * Sets the block's slipperiness, 0.6 is default, 0.98 is ice.
     */
    public BlockBuilder setSlipperiness(float slipperiness) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.slipperiness = slipperiness;
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
     * Makes fire burn indefinitely on top of the block.
     */
    public BlockBuilder setInfiniburn() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.infiniburn = true;
        return blockBuilder;
    }

    /**
     * Makes a block's interior faces get light from the block's position.<br>
     * Used for things like slabs, stairs, layers and various other non-full
     * blocks that allow light to pass through them.
     */
    public BlockBuilder setUseInternalLight() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.useInternalLight = true;
        return blockBuilder;
    }


    @Deprecated
    public BlockBuilder setDisabledNeighborNotifyOnMetadataChange() {
        return setVisualUpdateOnMetadata();
    }

    /**
     * Makes the block receive a visual update when the metadata of that block changes.
     */
    public BlockBuilder setVisualUpdateOnMetadata() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.visualUpdateOnMetadata = true;
        return blockBuilder;
    }

    /**
     * Makes the block receive a tick update when the game loads the chunk the block is in.
     */
    public BlockBuilder setTickOnLoad() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.tickOnLoad = true;
        return blockBuilder;
    }
    /**
     * Makes the block receive a tick update when the game loads the chunk the block is in.
     */
    public BlockBuilder setTicking(boolean ticking) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.tickOnLoad = ticking;
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
     * Sets the block's sound when walking over and breaking it.<br>
     * Example code:
     * <pre>{@code
     *     public static final Block exampleBlock = new BlockBuilder(MOD_ID)
     *          .setBlockSound(BlockSounds.WOOD)
     *          .build(new Block("example.block", 4000, Material.wood));
     * }</pre>
     */
    public BlockBuilder setBlockSound(BlockSound blockSound) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockSound = blockSound;
        return blockBuilder;
    }

    /**
     * Makes the block's textures be colorized according to the provided BlockColor.<br>
     * Example code:
     * <pre>{@code
     *     public static final Block customGrassBlock = new BlockBuilder(MOD_ID)
     *          .setBlockColor(new BlockColorGrass())
     *          .build(new BlockGrass("custom.grass.block", 4001, Material.grass));
     * }</pre>
     */
    public BlockBuilder setBlockColor(BlockColor blockColor) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockColor = blockColor;
        return blockBuilder;
    }

    /**
     * Sets the block's visible model.<br>
     * Example code:
     * <pre>{@code
     *     public static final Block customFlower = new BlockBuilder(MOD_ID)
     *          .setBlockModel(new BlockModelRenderBlocks(1))
     *          .build(new BlockFlower("custom.flower", 4002);
     * }</pre>
     */
    public BlockBuilder setBlockModel(BlockModel blockModel) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.blockModel = blockModel;
        return blockBuilder;
    }

    /**
     * Sets the block's item used to place the block.<br>
     * Example code:
     * <pre>{@code
     *     public static final Block customSlab = new BlockBuilder(MOD_ID)
     *          .setItemBlock(ItemBlockSlab::new)
     *          .build(new BlockSlab(Block.dirt, 4003));
     * }</pre>
     */
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

    @SafeVarargs
    public final BlockBuilder addTags(Tag<Block>... tags) {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.tags = ArrayUtils.addAll(this.tags, tags);
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

    @Deprecated
    public BlockBuilder setSides(String texture) {
        return setSideTextures(texture);
    }

    public BlockBuilder setSideTextures(String texture) {
        int[] sides = TextureHelper.getOrCreateBlockTexture(MOD_ID, texture);

        BlockBuilder blockBuilder = this.clone();
        blockBuilder.northTexture = sides;
        blockBuilder.eastTexture = sides;
        blockBuilder.southTexture = sides;
        blockBuilder.westTexture = sides;

        return blockBuilder;
    }

    @Deprecated
    public BlockBuilder setSides(int x, int y) {
        return setSideTextures(x, y);
    }

    public BlockBuilder setSideTextures(int x, int y) {
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
        if (topTexture != null) {
            block.atlasIndices[Side.TOP.getId()] = Block.texCoordToIndex(topTexture[0], topTexture[1]);
        }

        if (topTexture != null) {
            block.atlasIndices[Side.BOTTOM.getId()] = Block.texCoordToIndex(bottomTexture[0], bottomTexture[1]);
        }

        if (topTexture != null) {
            block.atlasIndices[Side.NORTH.getId()] = Block.texCoordToIndex(northTexture[0], northTexture[1]);
        }

        if (topTexture != null) {
            block.atlasIndices[Side.EAST.getId()] = Block.texCoordToIndex(eastTexture[0], eastTexture[1]);
        }

        if (topTexture != null) {
            block.atlasIndices[Side.SOUTH.getId()] = Block.texCoordToIndex(southTexture[0], southTexture[1]);
        }

        if (topTexture != null) {
            block.atlasIndices[Side.WEST.getId()] = Block.texCoordToIndex(westTexture[0], westTexture[1]);
        }

        if (hardness != null) {
            block.withHardness(hardness);
        }

        if (resistance != null) {
            block.withBlastResistance(resistance);
        }

        if (luminance != null) {
            block.withLightEmission(luminance);
        }

        if (lightOpacity != null) {
            block.withLightBlock(lightOpacity);
        }

        if (slipperiness != null) {
            block.movementScale = slipperiness;
        }

        block.withLitInteriorSurface(useInternalLight);

        if (immovable) {
            block.withImmovableFlagSet();
        }

        if (flammability != null) {
            ((BlockFireAccessor) Block.fire).callSetBurnRate(block.id, flammability[0], flammability[1]);
        }

        if (infiniburn) {
            infiniburnList.add(block.id);
        }

        if (visualUpdateOnMetadata) {
            block.withDisabledNeighborNotifyOnMetadataChange();
        }

        if (tickOnLoad != null){
            block.setTicking(tickOnLoad);
        }

        if (blockDrop != null) {
            block.setDropOverride(blockDrop);
        }

        if (blockSound != null) {
            BlockSoundDispatcher.getInstance().addDispatch(block, blockSound);
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

        List<String> tokens = Arrays.stream(block.getKey().split("\\."))
                .filter(token -> !token.equals(MOD_ID))
                .collect(Collectors.toList());

        List<String> newTokens = new ArrayList<>();
        newTokens.add(MOD_ID);
        newTokens.addAll(tokens.subList(1, tokens.size()));

        block.setKey(StringUtils.join(newTokens, '.'));

        return block;
    }
    
    @FunctionalInterface
    public interface BlockLambda<T> {
        T run(Block block);
    }
}
