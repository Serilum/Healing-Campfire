package com.natamus.healingcampfire.forge.events;

import com.natamus.healingcampfire.events.CampfireEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeCampfireEvent {
	@SubscribeEvent
	public void playerTickEvent(PlayerTickEvent e) {
		Player player = e.player;
		Level level = player.getCommandSenderWorld();
		if (level.isClientSide) {
			return;
		}

		CampfireEvent.playerTickEvent((ServerLevel)level, (ServerPlayer)player);
	}
}