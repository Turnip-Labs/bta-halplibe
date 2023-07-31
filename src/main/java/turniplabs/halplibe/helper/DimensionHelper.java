package turniplabs.halplibe.helper;

import net.minecraft.src.Block;
import net.minecraft.src.Dimension;
import net.minecraft.src.WorldType;
import turniplabs.halplibe.mixin.accessors.DimensionAccessor;

import java.util.Arrays;

public class DimensionHelper {

	public static Dimension createDimension(int id, String name, Dimension homeDimension, float worldScale, Block portalBlock, WorldType worldType, int minY, int maxY) {
		Dimension[] extendedList = Arrays.copyOf(Dimension.dimensionList, Dimension.dimensionList.length + 1);
		DimensionAccessor.setDimensionList(extendedList);

		return new Dimension(id, name, homeDimension, worldScale, portalBlock.blockID).setWorldType(worldType).setBounds(minY, maxY);
	}
}
