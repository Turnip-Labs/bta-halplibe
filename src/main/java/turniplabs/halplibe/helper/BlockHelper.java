package turniplabs.halplibe.helper;

import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.client.sound.block.BlockSoundDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import turniplabs.halplibe.mixin.accessors.BlockAccessor;
import turniplabs.halplibe.mixin.accessors.DispatcherAccessor;

@Deprecated
public class BlockHelper {

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
        ((DispatcherAccessor) BlockSoundDispatcher.getInstance()).callAddDispatch(block, blockSound);
        ((BlockAccessor) block).callSetHardness(hardness);
        ((BlockAccessor) block).callSetResistance(resistance);
        ((BlockAccessor) block).callSetLightValue(lightValue);

        Item.itemsList[block.id] = new ItemBlock(block);

        return block;
    }
}
