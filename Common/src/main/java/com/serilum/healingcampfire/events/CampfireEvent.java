package com.serilum.healingcampfire.events;

import com.natamus.collective.data.BlockEntityData;
import com.serilum.healingcampfire.config.ConfigHandler;
import com.serilum.healingcampfire.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CampfireEvent {
	public static void playerTickEvent(ServerLevel level, ServerPlayer player) {
		if (player.tickCount % ConfigHandler.checkForCampfireDelayInTicks != 0) {
			return;
		}

		List<BlockEntity> campfires = BlockEntityData.getCachedCampfires(level);
		if (campfires.isEmpty()) {
			return;
		}

		BlockPos playerPos = player.blockPosition();

		BlockPos campfirePos = null;
		BlockState campfireState = null;
		for (BlockEntity campfireBlockEntity : campfires) {
			BlockState nearbyCampfireState = campfireBlockEntity.getBlockState();
			if (!Util.isHealingCampfire(nearbyCampfireState)) {
				continue;
			}

			BlockPos nearbyCampfirePos = campfireBlockEntity.getBlockPos();
			if (!nearbyCampfirePos.closerThan(playerPos, Util.getHealingRadius(nearbyCampfireState))) {
				continue;
			}

			campfirePos = nearbyCampfirePos.immutable();
			campfireState = nearbyCampfireState;
			break;
		}

		if (campfirePos == null) {
			return;
		}

		boolean healsHostileMobs = Util.healsHostileMobsInstead(campfireState);
		if (!healsHostileMobs) {
			Util.applyCampfireEffects(level, player, campfireState);

			if (!ConfigHandler.healPassiveMobs) {
				return;
			}
		}

		double r = Util.getHealingRadius(campfireState);
		for (Entity entity : level.getEntities(player, new AABB(campfirePos.getX()-r, campfirePos.getY()-r, campfirePos.getZ()-r, campfirePos.getX()+r, campfirePos.getY()+r, campfirePos.getZ()+r))) {
			if (!(entity instanceof LivingEntity livingEntity) || entity instanceof Player) {
				continue;
			}

			if (Util.isMonster(entity) != healsHostileMobs || Util.isExcludedMob(entity)) {
				continue;
			}

			if (!healsHostileMobs && Util.isTargetingPlayer(livingEntity)) {
				continue;
			}

			Util.applyCampfireEffects(level, livingEntity, campfireState);
		}
	}
}
