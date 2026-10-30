package com.serilum.healingcampfire.util;

import com.natamus.collective.functions.CampfireFunctions;
import com.serilum.healingcampfire.config.ConfigHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class Util {
	public static boolean isHealingCampfire(BlockState campfireState) {
		if (!CampfireFunctions.isCampfire(campfireState)) {
			return false;
		}

		if (CampfireFunctions.isSoulCampfire(campfireState)) {
			if (!ConfigHandler.enableEffectForSoulCampfires) {
				return false;
			}
		}
		else if (!ConfigHandler.enableEffectForNormalCampfires) {
			return false;
		}

		if (ConfigHandler.campfireMustBeLit && !CampfireFunctions.isLit(campfireState)) {
			return false;
		}

		return !ConfigHandler.campfireMustBeSignalling || CampfireFunctions.isSignalling(campfireState);
	}

	public static int getHealingRadius(BlockState campfireState) {
		if (CampfireFunctions.isSoulCampfire(campfireState)) {
			return ConfigHandler.soulCampfireHealingRadius;
		}
		return ConfigHandler.healingRadius;
	}

	public static boolean healsHostileMobsInstead(BlockState campfireState) {
		return ConfigHandler.soulCampfiresHealHostileMobsInstead && CampfireFunctions.isSoulCampfire(campfireState);
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
		if (CampfireFunctions.isSoulCampfire(campfireState)) {
			durationInTicks = ConfigHandler.soulCampfireEffectDurationSeconds*20;
			amplifier = ConfigHandler.soulCampfireEffectLevel-1;
			effectIds = ConfigHandler.soulCampfireEffects;
		}

		List<MobEffectInstance> campfireEffects = new ArrayList<>();
		for (MobEffect mobEffect : getMobEffects(level, effectIds)) {
			if (ConfigHandler.hideEffectParticles) {
				campfireEffects.add(new MobEffectInstance(mobEffect, durationInTicks, amplifier, true, false));
			}
			else {
				campfireEffects.add(new MobEffectInstance(mobEffect, durationInTicks, amplifier));
			}
		}

		return campfireEffects;
	}

	public static List<MobEffect> getMobEffects(Level level, String effectIds) {
		Registry<MobEffect> mobEffectRegistry = level.registryAccess().registryOrThrow(Registries.MOB_EFFECT);

		List<MobEffect> mobEffects = new ArrayList<>();
		for (String rawEffectId : effectIds.split(",")) {
			ResourceLocation effectId = ResourceLocation.tryParse(rawEffectId.strip());
			if (effectId == null || !mobEffectRegistry.containsKey(effectId)) {
				continue;
			}

			MobEffect mobEffect = mobEffectRegistry.get(effectId);
			if (!mobEffects.contains(mobEffect)) {
				mobEffects.add(mobEffect);
			}
		}

		return mobEffects;
	}
}
