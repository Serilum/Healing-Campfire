package com.natamus.healingcampfire;

import com.natamus.collective.data.BlockEntityData;
import com.natamus.healingcampfire.config.ConfigHandler;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		BlockEntityData.addBlockEntityToCache(BlockEntityTypes.CAMPFIRE, true, false);
	}
}