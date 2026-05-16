package turniplabs.halplibe.helper;

import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.sound.BlockSound;
import org.apache.commons.lang3.ArrayUtils;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryRegistry;
import turniplabs.halplibe.mixin.accessors.BlocksAccessor;
import turniplabs.halplibe.util.registry.IdSupplier;
import turniplabs.halplibe.util.registry.RunLengthConfig;
import turniplabs.halplibe.util.registry.RunReserves;
import turniplabs.halplibe.util.toml.Toml;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class BlockBuilder implements Cloneable {
    private final @NonNull String modId;

    private @Nullable Float hardness = null;
    private @Nullable Float resistance = null;
    private @Nullable Integer luminance = null;
    private @Nullable Integer lightOpacity = null;
    private @Nullable Float slipperiness = null;
    private boolean immovable = false;
    private boolean useInternalLight = false;
    private boolean visualUpdateOnMetadata = false;
    private @Nullable Boolean ticking = null;
    private boolean infiniburn = false;
    private int @Nullable [] flammability = null;
    private @Nullable BlockSound blockSound = null;
    private @Nullable BlockLambda<ItemBlock<?>> customBlockItem = null;
    private @Nullable Tag<Block<?>>[] tags = null;
    private @Nullable Supplier<TileEntity> entitySupplier = null;
    private boolean disableStats = false;
    private @Nullable Float particleGravity = null;
    private @Nullable MaterialColor overrideColor = null;
    private @Nullable Supplier<IItemConvertible> statParent = null;
    private @Nullable CreativeInventoryPlacement creativeInventoryPlacement = null;

    public BlockBuilder(@NonNull String modId) {
        this.modId = modId;
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
     * Sets the block to be a TileEntity Block which creates the provided tile entities on placement
     *
     * @param tileEntitySupplier supplier of TileEntity instances for the block to create when placed
     * @return @return Copy of {@link ItemBuilder}
     */
    @SuppressWarnings("unused")
    public @NonNull BlockBuilder setTileEntity(@Nullable Supplier<TileEntity> tileEntitySupplier) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.entitySupplier = tileEntitySupplier;
        return blockBuilder;
    }

    /**
     * Sets how long it takes to break the block.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setHardness(float hardness) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.hardness = hardness;
        return blockBuilder;
    }

    /**
     * Sets the block's resistance against explosions.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setResistance(float resistance) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.resistance = resistance;
        return blockBuilder;
    }

    /**
     * Sets the block's light emitting capacity.
     *
     * @param luminance ranges from 0 to 15
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setLuminance(int luminance) {
        BlockBuilder blockBuilder = clone();
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
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setLightOpacity(int lightOpacity) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.lightOpacity = lightOpacity;
        return blockBuilder;
    }

    /**
     * Sets the block's slipperiness, 0.6 is default, 0.98 is ice.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setSlipperiness(float slipperiness) {
        BlockBuilder blockBuilder = clone();
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
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setFlammability(int chanceToCatchFire, int chanceToDegrade) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.flammability = new int[]{chanceToCatchFire, chanceToDegrade};
        return blockBuilder;
    }

    /**
     * Makes a block unable to be moved by pistons.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setImmovable() {
        BlockBuilder blockBuilder = clone();
        blockBuilder.immovable = true;
        return blockBuilder;
    }

    /**
     * Makes a block unable to be broken.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setUnbreakable() {
        BlockBuilder blockBuilder = clone();
        blockBuilder.hardness = -1.0f;
        return blockBuilder;
    }

    /**
     * Makes fire burn indefinitely on top of the block.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setInfiniburn() {
        BlockBuilder blockBuilder = clone();
        blockBuilder.infiniburn = true;
        return blockBuilder;
    }

    /**
     * Makes a block's interior faces get light from the block's position.<br>
     * Used for things like slabs, stairs, layers and various other non-full
     * blocks that allow light to pass through them.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setUseInternalLight() {
        BlockBuilder blockBuilder = clone();
        blockBuilder.useInternalLight = true;
        return blockBuilder;
    }

    /**
     * Makes the block receive a tick update when the game loads the chunk the block is in.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setTicking(boolean ticking) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.ticking = ticking;
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
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setBlockSound(BlockSound blockSound) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.blockSound = blockSound;
        return blockBuilder;
    }

    /**
     * Sets the block's item used to place the block.<br>
     * Example code:
     * <pre>{@code
     *     public static final Block customSlab = new BlockBuilder(MOD_ID)
     *          .setBlockItem(BlockItemSlab::new)
     *          .build(new BlockSlab(Block.dirt, 4003));
     * }</pre>
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setBlockItem(BlockLambda<ItemBlock<?>> customBlockItem) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.customBlockItem = customBlockItem;
        return blockBuilder;
    }

    /**
     * Overrides all previous tags with the ones provided
     */
    @SafeVarargs
    @SuppressWarnings({"unused"})
    public final @NonNull BlockBuilder setTags(Tag<Block<?>>... tags) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.tags = tags;
        return blockBuilder;
    }

    /**
     * Adds provided tags to previously specified tags
     */
    @SafeVarargs
    @SuppressWarnings({"unused"})
    public final @NonNull BlockBuilder addTags(Tag<Block<?>>... tags) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.tags = ArrayUtils.addAll(this.tags, tags);
        return blockBuilder;
    }

    /**
     * Disables stats for a block.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setDisableStats() {
        BlockBuilder blockBuilder = clone();
        blockBuilder.disableStats = true;
        return blockBuilder;
    }

    /**
     * Sets the gravity applied to block particles.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setParticleGravity(float particleGravity) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.particleGravity = particleGravity;
        return blockBuilder;
    }

    /**
     * Makes the block receive a visual update when the metadata of that block changes.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setVisualUpdateOnMetadata() {
        BlockBuilder blockBuilder = this.clone();
        blockBuilder.visualUpdateOnMetadata = true;
        return blockBuilder;
    }

    /**
     * Overrides the block color used for the map.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setOverrideColor(MaterialColor overrideColor) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.overrideColor = overrideColor;
        return blockBuilder;
    }

    /**
     * Sets the parent item used for statistics.
     */
    @SuppressWarnings({"unused"})
    public @NonNull BlockBuilder setStatParent(Supplier<IItemConvertible> statParent) {
        BlockBuilder blockBuilder = clone();
        blockBuilder.statParent = statParent;
        return blockBuilder;
    }

    /**
     * Adds block to the creative inventory based on the placement construct
     */
    public BlockBuilder setCreativeInventoryPlacement(@Nullable CreativeInventoryPlacement creativeInventoryPlacement) {
        this.creativeInventoryPlacement = creativeInventoryPlacement;
        return this;
    }

    /**
     * Generates a block with the specified configuration
     * @param translationKey Dot separated identifier to use for translation (eg `cracked.polished.blackstone.bricks`)
     * @param name Underscore separated name (eg `waxed_lightly_weathered_cut_copper_stairs`)
     * @param numericId Numeric id of the block must be in the range [0, 16383]
     * @param blockLogicSupplier {@link BlockLogic} that will be assigned to the Block on creation
     * @return Returns the {@link Block} after registration and configuration
     */
    public <T extends BlockLogic> @NonNull Block<T> build(String translationKey, String name, int numericId, BlockLogicSupplier<T> blockLogicSupplier) {
        Block<T> block = Blocks.register(
                String.format("%s.%s", modId, translationKey),
                String.format("%s:block/%s", modId, name),
                numericId,
                blockLogicSupplier
        );

        if (hardness != null) block.withHardness(hardness);
        if (resistance != null) block.withBlastResistance(resistance);
        if (luminance != null) block.withLightEmission(luminance);
        if (lightOpacity != null) block.withLightBlock(lightOpacity);
        if (slipperiness != null) block.friction = slipperiness;
        if (particleGravity != null) block.blockParticleGravity = particleGravity;
        block.withLitInteriorSurface(useInternalLight);
        if (immovable) block.withImmovableFlagSet();
        if (infiniburn) block.withTags(BlockTags.INFINITE_BURN);
        if (ticking != null) block.setTicking(ticking);
        if (visualUpdateOnMetadata) block.withDisabledNeighborNotifyOnMetadataChange();
        if (overrideColor != null) block.withOverrideColor(overrideColor);
        if (blockSound != null) block.withSound(blockSound);
        if (entitySupplier != null) block.withEntity(entitySupplier);
        if (tags != null) block.withTags(tags);
        if (customBlockItem != null) block.setBlockItem(() -> customBlockItem.run(block));
        if (flammability != null) BlockLogicFire.setFlammable(block, flammability[0], flammability[1]);
        if (disableStats) block.withDisabledStats();
        if (statParent != null) block.setStatParent(statParent);

        if (creativeInventoryPlacement != null) {
            CreativeInventoryRegistry.INSTANCE.register(block, creativeInventoryPlacement);
        }

        if (BlocksAccessor.hasInit()) {
            block.init();
            block.getLogic().initializeBlock();
            BlocksAccessor.cacheBlock(block);
        }

        return block;
    }

    /**
     * Convenience method: Pass name with underscores, ex. log_apple
     * automatically converts to log.apple for translation key.
     */
    @SuppressWarnings({"unused"})
    public <T extends BlockLogic> @NonNull Block<T> build(String identifier, int numericId, BlockLogicSupplier<T> logicSupplier) {
        String translationKey;
        String name;
        if (identifier.contains(".")) {
            translationKey = identifier;
            name = identifier.replace(".", "_");
        } else {
            translationKey = identifier.replace("_", ".");
            name = identifier;
        }
        return build(translationKey, name, numericId, logicSupplier);
    }

    @FunctionalInterface
    public interface BlockLambda<T> {
        T run(Block<?> block);
    }

    public static class Registry {
        public static int highestVanilla;

        private static final RunReserves reserves = new RunReserves(
                Registry::findOpenIds,
                Registry::findLength
        );

        /**
         * Should be called in a runnable scheduled with {@link IdSupplierHelper#scheduleRegistry(boolean, Runnable)}
         *
         * @param count the amount of needed blocks for the mod
         * @return the first available slot to register in
         */
        public static int findOpenIds(int count) {
            int run = 0;
            for (int i = highestVanilla; i < Blocks.blocksList.length; i++) {
                if (Blocks.blocksList[i] == null && !reserves.isReserved(i)) {
                    if (run >= count)
                        return (i - run);
                    run++;
                } else {
                    run = 0;
                }
            }
            return -1;
        }

        public static int findLength(int id, int terminate) {
            int run = 0;
            for (int i = id; i < Blocks.blocksList.length; i++) {
                if (Blocks.blocksList[i] == null && !reserves.isReserved(i)) {
                    run++;
                    if (run >= terminate) return terminate;
                } else {
                    return run;
                }
            }
            return run;
        }

        /**
         * Allows halplibe to automatically figure out where to insert the runs
         *
         * @param modId     an identifier for the mod, can be anything, but should be something the user can identify
         * @param runs      a toml object representing configured registry runs
         * @param neededIds the number of needed ids
         *                  if this changes after the mod has been configured (i.e. mod updated and now has more blocks) it'll find new, valid runs to put those blocks into
         * @param function  the function to run for registering items
         */
        public static void reserveRuns(String modId, Toml runs, int neededIds, Consumer<IdSupplier> function) {
            RunLengthConfig cfg = new RunLengthConfig(runs, neededIds);
            cfg.register(reserves);
            IdSupplierHelper.scheduleSmartRegistry(
                    () -> {
                        IdSupplier supplier = new IdSupplier(modId, reserves, cfg, neededIds);
                        function.accept(supplier);
                        supplier.validate();
                    }
            );
        }
    }
}
