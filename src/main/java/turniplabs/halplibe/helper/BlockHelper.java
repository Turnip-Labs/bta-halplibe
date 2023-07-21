package turniplabs.halplibe.helper;

import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.client.sound.block.BlockSoundDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.block.ItemBlock;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.mixin.accessors.BlockAccessor;
import turniplabs.halplibe.mixin.accessors.DispatcherAccessor;

public class BlockHelper {

    public static Block createBlock(String modId, Block block, String translationKey, String texture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] one = TextureHelper.registerBlockTexture(modId, texture);

        return createBlock(modId, block, translationKey, one[0], one[1], one[0], one[1], one[0], one[1], one[0], one[1], one[0], one[1], one[0], one[1], stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(String modId, Block block, String translationKey, String topBottomTexture, String sidesTexture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] topBottom = TextureHelper.registerBlockTexture(modId, topBottomTexture);
        int[] sides = TextureHelper.registerBlockTexture(modId, sidesTexture);

        return createBlock(modId, block, translationKey, topBottom[0], topBottom[1], topBottom[0], topBottom[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(String modId, Block block, String translationKey, String topTexture, String bottomTexture, String sidesTexture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] top = TextureHelper.registerBlockTexture(modId, topTexture);
        int[] bottom = TextureHelper.registerBlockTexture(modId, bottomTexture);
        int[] sides = TextureHelper.registerBlockTexture(modId, sidesTexture);

        return createBlock(modId, block, translationKey, top[0], top[1], bottom[0], bottom[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], sides[0], sides[1], stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(String modId, Block block, String translationKey, String topTexture, String bottomTexture, String northTexture, String eastTexture, String southTexture, String westTexture, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        int[] top = TextureHelper.registerBlockTexture(modId, topTexture);
        int[] bottom = TextureHelper.registerBlockTexture(modId, bottomTexture);
        int[] north = TextureHelper.registerBlockTexture(modId, northTexture);
        int[] south = TextureHelper.registerBlockTexture(modId, southTexture);
        int[] east = TextureHelper.registerBlockTexture(modId, eastTexture);
        int[] west = TextureHelper.registerBlockTexture(modId, westTexture);

        return createBlock(modId, block, translationKey, top[0], top[1], bottom[0], bottom[1], north[0], north[1], south[0], south[1], east[0], east[1], west[0], west[1], stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(String modId, Block block, String translationKey, int topX, int topY, int bottomX, int bottomY, int northX, int northY, int southX, int southY, int eastX, int eastY, int westX, int westY, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        block.withTexCoords(topX, topY, bottomX, bottomY, northX, northY, eastX, eastY, southX, southY, westX, westY);

        return createBlock(modId, block, translationKey, stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(String modId, Block block, String translationKey, BlockSound stepSound, float hardness, float resistance, float lightValue) {
        block.setKey(HalpLibe.addModId(modId, translationKey));

        ((DispatcherAccessor)BlockSoundDispatcher.getInstance()).callAddDispatch(block,stepSound);
        //((BlockAccessor) block).callSetStepSound(stepSound);
        ((BlockAccessor) block).callSetHardness(hardness);
        ((BlockAccessor) block).callSetResistance(resistance);
        ((BlockAccessor) block).callSetLightValue(lightValue);

        Item.itemsList[block.id] = new ItemBlock(block);

        return block;
    }
}
