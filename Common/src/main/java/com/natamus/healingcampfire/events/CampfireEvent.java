package com.natamus.healingcampfire.events;

import com.natamus.collective.data.BlockEntityData;
import com.natamus.healingcampfire.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CampfireEvent {
	public static void playerTickEvent(ServerLevel level, ServerPlayer player) {
		if (player.tickCount % ConfigHandler.checkForCampfireDelayInTicks != 0) {
			return;
		}

		if (!BlockEntityData.cachedBlockEntities.get(BlockEntityType.CAMPFIRE).containsKey(level)) {
			return;
		}

		BlockPos entityPos = player.blockPosition();
		Vec3i entityVec3i = new Vec3i(entityPos.getX(), entityPos.getY(), entityPos.getZ());

		BlockPos campfirePos = null;
		for (BlockEntity campfireBlockEntity : BlockEntityData.cachedBlockEntities.get(BlockEntityType.CAMPFIRE).get(level)) {
			BlockPos nearbyCampfirePos = campfireBlockEntity.getBlockPos();
			if (!nearbyCampfirePos.closerThan(entityVec3i, ConfigHandler.healingRadius)) {
				continue;
			}

			BlockState campfireState = campfireBlockEntity.getBlockState();
			Block block = campfireState.getBlock();

			if (!ConfigHandler.enableEffectForNormalCampfires) {
				if (block.equals(Blocks.CAMPFIRE)) {
					continue;
				}
			}
			if (!ConfigHandler.enableEffectForSoulCampfires) {
				if (block.equals(Blocks.SOUL_CAMPFIRE)) {
					continue;
				}
			}

			if (ConfigHandler.campfireMustBeLit) {
				Boolean islit = campfireState.getValue(CampfireBlock.LIT);
				if (!islit) {
					continue;
				}
			}
			if (ConfigHandler.campfireMustBeSignalling) {
				Boolean issignalling = campfireState.getValue(CampfireBlock.SIGNAL_FIRE);
				if (!issignalling) {
					continue;
				}
			}

			campfirePos = nearbyCampfirePos.immutable();
			break;
		}

		if (campfirePos == null) {
			return;
		}

		MobEffectInstance effectinstance;
		if (ConfigHandler.hideEffectParticles) {
			effectinstance = new MobEffectInstance(MobEffects.REGENERATION, ConfigHandler.effectDurationSeconds*20, ConfigHandler.regenerationLevel-1, true, false);
		}
		else {
			effectinstance = new MobEffectInstance(MobEffects.REGENERATION, ConfigHandler.effectDurationSeconds*20, ConfigHandler.regenerationLevel-1);
		}

		BlockPos ppos = player.blockPosition();
		double r = ConfigHandler.healingRadius;
		if (ppos.closerThan(campfirePos, r)) {
			boolean addeffect = true;
			MobEffectInstance currentregen = player.getEffect(effectinstance.getEffect());
			if (currentregen != null) {
				int currentduration = currentregen.getDuration();
				if (currentduration > (ConfigHandler.effectDurationSeconds*10)) {
					addeffect = false;
				}
			}

			if (addeffect) {
				player.addEffect(effectinstance);
			}
		}

		if (ConfigHandler.healPassiveMobs) {
			for (Entity entity : level.getEntities(player, new AABB(campfirePos.getX()-r, campfirePos.getY()-r, campfirePos.getZ()-r, campfirePos.getX()+r, campfirePos.getY()+r, campfirePos.getZ()+r))) {
				if (entity instanceof LivingEntity && (!(entity instanceof Player)) && !entity.getType().getCategory().equals(MobCategory.MONSTER)) {
					LivingEntity le = (LivingEntity)entity;
					
					boolean addeffect = true;
					MobEffectInstance currentregen = le.getEffect(effectinstance.getEffect());
					if (currentregen != null) {
						int currentduration = currentregen.getDuration();
						if (currentduration > (ConfigHandler.effectDurationSeconds*10)) {
							addeffect = false;
						}
					}
					
					if (addeffect) {
						le.addEffect(effectinstance);
					}
				}
			}
		}
	}
}