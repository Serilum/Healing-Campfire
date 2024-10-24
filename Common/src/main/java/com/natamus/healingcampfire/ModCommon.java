package com.natamus.healingcampfire;

import com.natamus.collective.data.BlockEntityData;
import com.natamus.healingcampfire.config.ConfigHandler;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		BlockEntityData.addBlockEntityToCache(BlockEntityType.CAMPFIRE);
	}
}