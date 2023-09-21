package turniplabs.halplibe.util;

import net.minecraft.core.block.Block;
import net.minecraft.core.item.block.ItemBlock;

public class SpecialItemBlock extends ItemBlock {
	public SpecialItemBlock(int id, Block block) {
		super(block);
		
		this.id = id;
		if (itemsList[this.id] != null) {
			System.out.println("CONFLICT @ " + this.id);
		}
		
		itemsList[this.id] = this;
		if (this.id > highestItemId) {
			highestItemId = this.id;
		}
	}
}
