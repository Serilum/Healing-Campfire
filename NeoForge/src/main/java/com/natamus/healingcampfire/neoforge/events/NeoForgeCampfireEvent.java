package com.natamus.healingcampfire.neoforge.events;

import com.natamus.healingcampfire.events.CampfireEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class NeoForgeCampfireEvent {
	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent.Post e) {
		Player player = e.getEntity();
		Level level = player.level();
		if (level.isClientSide) {
			return;
		}

		CampfireEvent.playerTickEvent((ServerLevel)level, (ServerPlayer)player);
	}
}