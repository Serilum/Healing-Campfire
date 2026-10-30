package com.serilum.healingcampfire.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.healingcampfire.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 1, max = 1200) public static int checkForCampfireDelayInTicks = 40;
	@Entry(min = 1, max = 64) public static int healingRadius = 16;
	@Entry(min = 1, max = 600) public static int effectDurationSeconds = 60;
	@Entry(min = 1, max = 50) public static int regenerationLevel = 1;
	@Entry(registry = "mob_effect") public static String campfireEffects = "minecraft:regeneration";
	@Entry public static boolean healPassiveMobs = true;
	@Entry(registry = "entity_type") public static String mobsThatAreNotHealed = "";
	@Entry(min = 0, max = 600) public static int secondsAfterMobDamageBeforeHealing = 0;
	@Entry public static boolean hideEffectParticles = true;
	@Entry public static boolean campfireMustBeLit = true;
	@Entry public static boolean campfireMustBeSignalling = false;
	@Entry public static boolean enableEffectForNormalCampfires = true;
	@Entry public static boolean enableEffectForSoulCampfires = true;
	@Entry(min = 1, max = 64) public static int soulCampfireHealingRadius = 16;
	@Entry(min = 1, max = 600) public static int soulCampfireEffectDurationSeconds = 60;
	@Entry(min = 1, max = 50) public static int soulCampfireEffectLevel = 1;
	@Entry(registry = "mob_effect") public static String soulCampfireEffects = "minecraft:regeneration";
	@Entry public static boolean soulCampfiresHealHostileMobsInstead = false;

	public static void initConfig() {
		configMetaData.put("checkForCampfireDelayInTicks", Arrays.asList(
			"How often in ticks the mod checks for campfires around the player. 1 second = 20 ticks, so by default every 2 seconds."
		));
		configMetaData.put("healingRadius", Arrays.asList(
			"The radius around the campfire in blocks where players receive the campfire's effects."
		));
		configMetaData.put("effectDurationSeconds", Arrays.asList(
			"The duration of the effects which the campfire applies."
		));
		configMetaData.put("regenerationLevel", Arrays.asList(
			"The level of the effects which the campfire applies, by default 1."
		));
		configMetaData.put("campfireEffects", Arrays.asList(
			"The effects which the campfire applies, by default regeneration."
		));
		configMetaData.put("healPassiveMobs", Arrays.asList(
			"When enabled, the campfire heals passive mobs around where the radius is half the width of a bounding box."
		));
		configMetaData.put("mobsThatAreNotHealed", Arrays.asList(
			"Mobs that never receive the campfire's effects."
		));
		configMetaData.put("secondsAfterMobDamageBeforeHealing", Arrays.asList(
			"How many seconds after being hurt by a mob or player the campfire waits before applying its effects again. 0 disables the wait."
		));
		configMetaData.put("hideEffectParticles", Arrays.asList(
			"When enabled, hides the particles from the effects around the campfire."
		));
		configMetaData.put("campfireMustBeLit", Arrays.asList(
			"When enabled, the campfire only has an effect when the block is lit up."
		));
		configMetaData.put("campfireMustBeSignalling", Arrays.asList(
			"When enabled, the campfire only has an effect when the block is signalling."
		));
		configMetaData.put("enableEffectForNormalCampfires", Arrays.asList(
			"When enabled, the mod will work with normal campfires."
		));
		configMetaData.put("enableEffectForSoulCampfires", Arrays.asList(
			"When enabled, the mod will work with soul campfires."
		));
		configMetaData.put("soulCampfireHealingRadius", Arrays.asList(
			"The radius around a soul campfire in blocks where its effects are applied."
		));
		configMetaData.put("soulCampfireEffectDurationSeconds", Arrays.asList(
			"The duration of the effects which a soul campfire applies."
		));
		configMetaData.put("soulCampfireEffectLevel", Arrays.asList(
			"The level of the effects which a soul campfire applies, by default 1."
		));
		configMetaData.put("soulCampfireEffects", Arrays.asList(
			"The effects which a soul campfire applies, by default regeneration."
		));
		configMetaData.put("soulCampfiresHealHostileMobsInstead", Arrays.asList(
			"When enabled, soul campfires apply their effects to hostile mobs instead of players and passive mobs."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}
