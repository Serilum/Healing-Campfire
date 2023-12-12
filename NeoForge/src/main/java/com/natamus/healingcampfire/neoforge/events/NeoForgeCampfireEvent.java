package com.natamus.healingcampfire.neoforge.events;

import com.natamus.healingcampfire.events.CampfireEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeCampfireEvent {
	@SubscribeEvent
	public static void playerTickEvent(PlayerTickEvent e) {
		Player player = e.player;
		Level level = player.level();
		if (level.isClientSide) {
			return;
		}

		CampfireEvent.playerTickEvent((ServerLevel)level, (ServerPlayer)player);
	}
}