package com.natamus.healingcampfire;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.healingcampfire.events.CampfireEvent;
import com.natamus.healingcampfire.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CollectivePlayerEvents.PLAYER_TICK.register((ServerLevel world, ServerPlayer player) -> {
			CampfireEvent.playerTickEvent(world, player);
		});
	}

	private static void setGlobalConstants() {

	}
}
