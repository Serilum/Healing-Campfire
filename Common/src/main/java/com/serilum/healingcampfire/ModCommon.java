package com.serilum.healingcampfire;

import com.serilum.healingcampfire.config.ConfigHandler;
import com.serilum.healingcampfire.util.Util;
import net.minecraft.world.level.block.entity.BlockEntityTypes;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		Util.cacheCampfireBlockEntityType(BlockEntityTypes.CAMPFIRE);
	}
}
