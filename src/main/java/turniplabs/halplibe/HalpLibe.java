package turniplabs.halplibe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.launch.knot.Knot;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.mixin.accessors.DispatcherAccessor;

public class HalpLibe implements ModInitializer {
	public static final String MOD_ID = "halplibe";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static String addModId(String modId, String name) {
		return modId + "." + name;
	}

	static {
		//this is here to possibly fix some class loading issues but might not work anyway, delete if it causes even more problems
		try {
			Class.forName("net.minecraft.core.block.Block");
			Class.forName("net.minecraft.core.item.Item");
		} catch (ClassNotFoundException ignored) {}
	}

	@Override
	public void onInitialize() {
		LOGGER.info("HalpLibe initialized.");
	}
}
