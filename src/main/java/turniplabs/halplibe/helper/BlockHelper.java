package turniplabs.halplibe.helper;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.StepSound;
import turniplabs.halplibe.mixin.accessors.BlockInterface;

public class BlockHelper {

    public static Block createBlock(Block block, String name, int x, int y, StepSound stepSound, float hardness, float resistance, float lightValue) {
        return createBlock(block, name, x, y, x, y, x, y, x, y, x, y, x, y, stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(Block block, String name, int topBottomX, int topBottomY, int sidesX, int sidesY, StepSound stepSound, float hardness, float resistance, float lightValue) {
        return createBlock(block, name, topBottomX, topBottomY, topBottomX, topBottomY, sidesX, sidesY, sidesX, sidesY, sidesX, sidesY, sidesX, sidesY, stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(Block block, String name, int topX, int topY, int bottomX, int bottomY, int sidesX, int sidesY, StepSound stepSound, float hardness, float resistance, float lightValue) {
        return createBlock(block, name, topX, topY, bottomX, bottomY, sidesX, sidesY, sidesX, sidesY, sidesX, sidesY, sidesX, sidesY, stepSound, hardness, resistance, lightValue);
    }

    public static Block createBlock(Block block, String name, int topX, int topY, int bottomX, int bottomY, int northX, int northY, int eastX, int eastY, int southX, int southY, int westX, int westY, StepSound stepSound, float hardness, float resistance, float lightValue) {
        block.setTexCoords(topX, topY, bottomX, bottomY, northX, northY, eastX, eastY, southX, southY, westX, westY);
        block.setBlockName(name);

        ((BlockInterface) block).callSetHardness(hardness);
        ((BlockInterface) block).callSetResistance(resistance);
        ((BlockInterface) block).callSetStepSound(stepSound);
        ((BlockInterface) block).callSetLightValue(lightValue);

        Item.itemsList[block.blockID] = new ItemBlock(block.blockID - Block.blocksList.length);

        return block;
    }
}
