package com.natamus.healingcampfire;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.healingcampfire.neoforge.config.IntegrateNeoForgeConfig;
import com.natamus.healingcampfire.neoforge.events.NeoForgeCampfireEvent;
import com.natamus.healingcampfire.util.Reference;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(Reference.MOD_ID)
public class ModNeoForge {
	
	public ModNeoForge(IEventBus modEventBus) {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		modEventBus.addListener(this::loadComplete);

		setGlobalConstants();
		ModCommon.init();

		IntegrateNeoForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		NeoForge.EVENT_BUS.register(NeoForgeCampfireEvent.class);
	}

	private static void setGlobalConstants() {

	}
}