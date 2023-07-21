package turniplabs.halplibe.helper;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import turniplabs.halplibe.mixin.accessors.DimensionAccessor;

import java.util.Arrays;

public class DimensionHelper {

    public static Dimension createDimension(int id, String name, Dimension homeDimension, float worldScale, Block portalBlock, WorldType worldType) {
        //Dimension[] extendedList = Arrays.copyOf(Dimension.dimensionList, Dimension.dimensionList.length + 1);
        //DimensionAccessor.setDimensionList(extendedList);

        Dimension dim = new Dimension(name, homeDimension, worldScale, portalBlock.id).setDefaultWorldType(worldType);
        Dimension.registerDimension(id,dim);
        return dim;
    }
}
