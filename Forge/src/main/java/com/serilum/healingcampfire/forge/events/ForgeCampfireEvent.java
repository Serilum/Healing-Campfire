package com.serilum.healingcampfire.forge.events;

import com.serilum.healingcampfire.events.CampfireEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeCampfireEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeCampfireEvent.class);
	}

	@SubscribeEvent
	public static void onServerAboutToStart(ServerAboutToStartEvent e) {
		CampfireEvent.onServerStarting(e.getServer());
	}

	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent.Post e) {
		Player player = e.player();
		Level level = player.level();
		if (level.isClientSide()) {
			return;
		}

		CampfireEvent.playerTickEvent((ServerLevel)level, (ServerPlayer)player);
	}
}
