package com.serilum.healingcampfire;

import com.natamus.collective.data.BlockEntityData;
import com.serilum.healingcampfire.config.ConfigHandler;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		BlockEntityData.addCampfiresToCache();
	}
}
