package turniplabs.halplibe.helper;

import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.client.sound.block.BlockSoundDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import turniplabs.halplibe.mixin.accessors.BlockAccessor;
import turniplabs.halplibe.util.registry.IdSupplier;
import turniplabs.halplibe.util.registry.RunLengthConfig;
import turniplabs.halplibe.util.registry.RunReserves;
import turniplabs.halplibe.util.toml.Toml;

import java.util.function.Consumer;

public class BlockHelper {
    
    public static int highestVanilla;

    private static final RunReserves reserves = new RunReserves(
            BlockHelper::findOpenIds,
            BlockHelper::findLength
    );

    /**
     * Should be called in a runnable scheduled with {@link RegistryHelper#scheduleRegistry(boolean, Runnable)}
     * @param count the amount of needed blocks for the mod
     * @return the first available slot to register in
     */
    public static int findOpenIds(int count) {
        int run = 0;
        for (int i = highestVanilla; i < Block.blocksList.length; i++) {
            if (Block.blocksList[i] == null && !reserves.isReserved(i)) {
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
        for (int i = id; i < Block.blocksList.length; i++) {
            if (Block.blocksList[i] == null && !reserves.isReserved(i)) {
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
     * @param modid     an identifier for the mod, can be anything, but should be something the user can identify
     * @param runs      a toml object representing configured registry runs
     * @param neededIds the number of needed ids
     *                  if this changes after the mod has been configured (i.e. mod updated and now has more blocks) it'll find new, valid runs to put those blocks into
     * @param function  the function to run for registering items
     */
    public static void reserveRuns(String modid, Toml runs, int neededIds, Consumer<IdSupplier> function) {
        RunLengthConfig cfg = new RunLengthConfig(runs, neededIds);
        cfg.register(reserves);
        RegistryHelper.scheduleSmartRegistry(
                () -> {
                    IdSupplier supplier = new IdSupplier(modid, reserves, cfg, neededIds);
                    function.accept(supplier);
                    supplier.validate();
                }
        );
    }
    
    @Deprecated
    public static Block createBlock(String modId, Block block, String texture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] one = TextureHelper.getOrCreateBlockTexture(modId, texture);

        return createBlock(modId, block, one[0], one[1], one[0], one[1], one[0], one[1], one[0], one[1], one[0], one[1], one[0], one[1], stepSound, hardness, resistance, lightValue);
    }

    @Deprecated
    public static Block createBlock(String modId, Block block, String topBottomTexture, String sidesTexture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] topBottom = TextureHelper.getOrCreateBlockTexture(modId, topBottomTexture);
        int[] sides = TextureHelper.getOrCreateBlockTexture(modId, sidesTexture);

        return createBlock(modId, block, topBottom[0], topBottom[1], topBottom[0], topBottom[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], stepSound, hardness, resistance, lightValue);
    }

    @Deprecated
    public static Block createBlock(String modId, Block block, String topTexture, String bottomTexture, String sidesTexture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] top = TextureHelper.getOrCreateBlockTexture(modId, topTexture);
        int[] bottom = TextureHelper.getOrCreateBlockTexture(modId, bottomTexture);
        int[] sides = TextureHelper.getOrCreateBlockTexture(modId, sidesTexture);

        return createBlock(modId, block, top[0], top[1], bottom[0], bottom[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], stepSound, hardness, resistance, lightValue);
    }

    @Deprecated
    public static Block createBlock(String modId, Block block, String topTexture, String bottomTexture, String northTexture, String eastTexture, String southTexture, String westTexture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] top = TextureHelper.getOrCreateBlockTexture(modId, topTexture);
        int[] bottom = TextureHelper.getOrCreateBlockTexture(modId, bottomTexture);
        int[] north = TextureHelper.getOrCreateBlockTexture(modId, northTexture);
        int[] south = TextureHelper.getOrCreateBlockTexture(modId, southTexture);
        int[] east = TextureHelper.getOrCreateBlockTexture(modId, eastTexture);
        int[] west = TextureHelper.getOrCreateBlockTexture(modId, westTexture);

        return createBlock(modId, block, top[0], top[1], bottom[0], bottom[1], north[0], north[1], south[0], south[1], east[0], east[1], west[0], west[1], stepSound, hardness, resistance, lightValue);
    }

    @Deprecated
    public static Block createBlock(String modId, Block block, int topX, int topY, int bottomX, int bottomY, int northX, int northY, int southX, int southY, int eastX, int eastY, int westX, int westY, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        block.withTexCoords(topX, topY, bottomX, bottomY, northX, northY, eastX, eastY, southX, southY, westX, westY);

        return createBlock(modId, block, stepSound, hardness, resistance, lightValue);
    }

    @Deprecated
    public static Block createBlock(String modId, Block block, BlockSound blockSound, float hardness, float resistance, float lightValue) {
        BlockSoundDispatcher.getInstance().addDispatch(block, blockSound);
        ((BlockAccessor) block).callSetHardness(hardness);
        ((BlockAccessor) block).callSetResistance(resistance);
        ((BlockAccessor) block).callSetLightValue(lightValue);

        Item.itemsList[block.id] = new ItemBlock(block);

        return block;
    }
}
