package com.serilum.healingcampfire.util;

import com.natamus.collective.data.BlockEntityData;
import com.serilum.healingcampfire.config.ConfigHandler;
import com.serilum.healingcampfire.data.Variables;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Util {
	public static void cacheCampfireBlockEntityType(BlockEntityType<?> blockEntityType) {
		if (Variables.campfireBlockEntityTypes.contains(blockEntityType)) {
			return;
		}

		BlockEntityData.addBlockEntityToCache(blockEntityType, true, false);
		Variables.campfireBlockEntityTypes.add(blockEntityType);
	}

	public static void cacheAllCampfireBlockEntityTypes(MinecraftServer server) {
		Registry<Block> blockRegistry = server.registryAccess().registryOrThrow(Registries.BLOCK);
		Registry<BlockEntityType<?>> blockEntityTypeRegistry = server.registryAccess().registryOrThrow(Registries.BLOCK_ENTITY_TYPE);

		List<BlockState> campfireStates = new ArrayList<>();
		for (Block block : blockRegistry) {
			BlockState defaultState = block.defaultBlockState();
			if (isCampfire(defaultState)) {
				campfireStates.add(defaultState);
			}
		}

		for (BlockEntityType<?> blockEntityType : blockEntityTypeRegistry) {
			for (BlockState campfireState : campfireStates) {
				if (blockEntityType.isValid(campfireState)) {
					cacheCampfireBlockEntityType(blockEntityType);
					break;
				}
			}
		}
	}

	public static List<BlockEntity> getCachedCampfires(Level level) {
		List<BlockEntity> campfires = new ArrayList<>();
		for (BlockEntityType<?> blockEntityType : Variables.campfireBlockEntityTypes) {
			campfires.addAll(BlockEntityData.getCachedBlockEntities(blockEntityType, level));
		}

		return campfires;
	}

	public static boolean isCampfire(BlockState state) {
		return state.getBlock() instanceof CampfireBlock || state.is(BlockTags.CAMPFIRES);
	}

	public static boolean isSoulCampfire(BlockState campfireState) {
		return campfireState.getBlock().getDescriptionId().contains("soul");
	}

	public static boolean isHealingCampfire(BlockState campfireState) {
		if (!isCampfire(campfireState)) {
			return false;
		}

		if (isSoulCampfire(campfireState)) {
			if (!ConfigHandler.enableEffectForSoulCampfires) {
				return false;
			}
		}
		else if (!ConfigHandler.enableEffectForNormalCampfires) {
			return false;
		}

		if (ConfigHandler.campfireMustBeLit && !isLit(campfireState)) {
			return false;
		}

		return !ConfigHandler.campfireMustBeSignalling || isSignalling(campfireState);
	}

	public static boolean isLit(BlockState campfireState) {
		if (!campfireState.hasProperty(CampfireBlock.LIT)) {
			return true;
		}

		return campfireState.getValue(CampfireBlock.LIT);
	}

	public static boolean isSignalling(BlockState campfireState) {
		if (!campfireState.hasProperty(CampfireBlock.SIGNAL_FIRE)) {
			return false;
		}

		return campfireState.getValue(CampfireBlock.SIGNAL_FIRE);
	}

	public static int getHealingRadius(BlockState campfireState) {
		if (isSoulCampfire(campfireState)) {
			return ConfigHandler.soulCampfireHealingRadius;
		}
		return ConfigHandler.healingRadius;
	}

	public static boolean healsHostileMobsInstead(BlockState campfireState) {
		return ConfigHandler.soulCampfiresHealHostileMobsInstead && isSoulCampfire(campfireState);
	}

	public static boolean isMonster(Entity entity) {
		return entity.getType().getCategory().equals(MobCategory.MONSTER);
	}

	public static boolean isTargetingPlayer(LivingEntity livingEntity) {
		if (!(livingEntity instanceof Mob mob)) {
			return false;
		}

		return mob.getTarget() instanceof Player;
	}

	public static boolean isExcludedMob(Entity entity) {
		String entityId = EntityType.getKey(entity.getType()).toString();
		for (String excludedId : ConfigHandler.mobsThatAreNotHealed.split(",")) {
			if (excludedId.strip().equals(entityId)) {
				return true;
			}
		}

		return false;
	}

	public static boolean wasRecentlyHurtByMob(LivingEntity livingEntity) {
		if (ConfigHandler.secondsAfterMobDamageBeforeHealing <= 0) {
			return false;
		}

		int lastHurtByMobTick = livingEntity.getLastHurtByMobTimestamp();
		if (lastHurtByMobTick <= 0) {
			return false;
		}

		return livingEntity.tickCount - lastHurtByMobTick < ConfigHandler.secondsAfterMobDamageBeforeHealing*20;
	}

	public static void applyCampfireEffects(Level level, LivingEntity livingEntity, BlockState campfireState) {
		if (wasRecentlyHurtByMob(livingEntity)) {
			return;
		}

		for (MobEffectInstance campfireEffect : createCampfireEffects(level, campfireState)) {
			if (needsEffect(livingEntity, campfireEffect)) {
				livingEntity.addEffect(campfireEffect);
			}
		}
	}

	public static boolean needsEffect(LivingEntity livingEntity, MobEffectInstance campfireEffect) {
		MobEffectInstance currentEffect = livingEntity.getEffect(campfireEffect.getEffect());
		if (currentEffect == null) {
			return true;
		}

		return currentEffect.getDuration() <= campfireEffect.getDuration()/2;
	}

	public static List<MobEffectInstance> createCampfireEffects(Level level, BlockState campfireState) {
		int durationInTicks = ConfigHandler.effectDurationSeconds*20;
		int amplifier = ConfigHandler.regenerationLevel-1;
		String effectIds = ConfigHandler.campfireEffects;
		if (isSoulCampfire(campfireState)) {
			durationInTicks = ConfigHandler.soulCampfireEffectDurationSeconds*20;
			amplifier = ConfigHandler.soulCampfireEffectLevel-1;
			effectIds = ConfigHandler.soulCampfireEffects;
		}

		List<MobEffectInstance> campfireEffects = new ArrayList<>();
		for (Holder<MobEffect> mobEffect : getMobEffects(level, effectIds)) {
			if (ConfigHandler.hideEffectParticles) {
				campfireEffects.add(new MobEffectInstance(mobEffect, durationInTicks, amplifier, true, false));
			}
			else {
				campfireEffects.add(new MobEffectInstance(mobEffect, durationInTicks, amplifier));
			}
		}

		return campfireEffects;
	}

	public static List<Holder<MobEffect>> getMobEffects(Level level, String effectIds) {
		Registry<MobEffect> mobEffectRegistry = level.registryAccess().registryOrThrow(Registries.MOB_EFFECT);

		List<Holder<MobEffect>> mobEffects = new ArrayList<>();
		for (String rawEffectId : effectIds.split(",")) {
			ResourceLocation effectId = ResourceLocation.tryParse(rawEffectId.strip());
			if (effectId == null) {
				continue;
			}

			Optional<Holder.Reference<MobEffect>> mobEffect = mobEffectRegistry.getHolder(effectId);
			if (mobEffect.isPresent() && !mobEffects.contains(mobEffect.get())) {
				mobEffects.add(mobEffect.get());
			}
		}

		return mobEffects;
	}
}
