<h2>Healing Campfire</h2>
<p><a href="https://github.com/Serilum/Healing-Campfire"><img src="https://serilum.com/assets/data/logo/healing-campfire.png"></a></p><h2>Download</h2>
<p>You can download Healing Campfire on CurseForge and Modrinth:</p><p>&nbsp;&nbsp;CurseForge: &nbsp;&nbsp;<a href="https://curseforge.com/minecraft/mc-mods/healing-campfire">https://curseforge.com/minecraft/mc-mods/healing-campfire</a><br>&nbsp;&nbsp;Modrinth: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://modrinth.com/mod/healing-campfire">https://modrinth.com/mod/healing-campfire</a></p>
<h2>Issue Tracker</h2>
<p>To keep a better overview of all mods, the issue tracker is located in a separate repository.<br>&nbsp;&nbsp;For issues, ideas, suggestions or anything else, please follow this link:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;-> <a href="https://github.com/ricksouth/serilum-mc-mods/issues">Issue Tracker</a></p>
<h2>Pull Requests</h2>
<p>Because of the way mod loader files are bundled into one jar, some extra information is needed to do a PR.<br>&nbsp;&nbsp;A wiki page entry about it is available here:</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;-> <a href="https://github.com/ricksouth/serilum-mc-mods/wiki/Pull-Request-Information">Pull Request Information</a></p>
<h2>Mod Description</h2>
<p><a href="https://serilum.com/" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/a1.jpg" alt="" width="838" height="400"></a><br><br><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/header.png"><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:75125" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_20.png"></a><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:73407" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_19.png"></a><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:73250" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_18.png"></a><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:73242" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_17.png"></a><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:70886" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_16.png"></a><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:68722" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_15.png"></a><a href="https://legacy.curseforge.com/minecraft/mc-mods/healing-campfire/files/all?filter-status=1&filter-game-version=1738749986:64806" rel="nofollow"><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/description/Versions/1_14.png"></a><br><br><strong><span style="font-size:24px">Requires the library mod&nbsp;<a style="font-size:24px" href="https://www.curseforge.com/minecraft/mc-mods/collective" rel="nofollow">Collective</a>.</span></strong><strong>&nbsp;<br><br> &nbsp; &nbsp;This mod is part of <span style="color:#008000"><a style="color:#008000" href="https://curseforge.com/minecraft/modpacks/the-vanilla-experience" rel="nofollow">The Vanilla Experience</a></span>.</strong><br><span style="font-size:18px">Healing Campfire creates an area around the campfire where players and/or passive mobs receive the regeneration effect in a configurable radius. Modded passive entities will be healed as well as long as they are <em><span style="text-decoration:underline">not</span> </em>classified as&nbsp;"<em>EntityClassification.Monster</em>". When the entity has half of the remaining regeneration effect left and is near the campfire, the effect duration will refresh.<br><br></span><strong><span style="color:#008000;font-size:16px">The <span style="color:#36f;font-size:16px">Soul Campfire</span> in the 1.16 version is compatible! This is configurable.</span></strong><br>You may also be interested in <span style="color:#f90"><strong><a style="color:#f90" href="https://www.curseforge.com/minecraft/mc-mods/no-hostiles-around-campfire" rel="nofollow">No Hostiles Around Campfire</a></strong></span>.<br><br><br><strong><span style="font-size:20px">Configurable:</span> <span style="color:#008000;font-size:14px"><a style="color:#008000" href="https://github.com/ricksouth/serilum-mc-mods/wiki/how-to-configure-mods" rel="nofollow">(&nbsp;how do I configure?&nbsp;)</a></span><br></strong><strong>checkForCampfireDelayInTicks </strong>(default = 40, min 1, max 1200): How often in ticks the mod checks for campfires around the player. 1 second = 20 ticks, so by default every 2 seconds.<br><strong>healingRadius&nbsp;</strong>(default = 16, min 1, max 64): The radius around the campfire in blocks where players receive the regeneration effect.<strong><br>effectDurationSeconds </strong>(default = 60, min 1, max 600): The duration of the regeneration effect which the campfire applies.<br><strong>regenerationLevel </strong>(default = 1, min 1, max 50): The level of regeneration which the campfire applies, by default 1.<br><br><strong>healPassiveMobs&nbsp;</strong>(default = true): When enabled, the campfire heals passive mobs around where the radius is half the width of a bounding box.<br><strong>hideEffectParticles&nbsp;</strong>(default = true): When enabled, hides the particles from the regeneration effect around the campfire.<br><br><strong>campfireMustBeLit</strong>&nbsp;(default = true): When enabled, the campfire only has an effect when the block is lit up.<br><strong>campfireMustBeSignalling</strong>&nbsp;(default = false): When enabled, the campfire only has an effect when the block is signalling.<br><br><span style="font-size:14px"><em>1.16+_only config options:</em></span><br><strong>enableEffectForNormalCampfires</strong>&nbsp;(default = true): When enabled, the mod will work with normal campfires.<br><strong>enableEffectForSoulCampfires</strong>&nbsp;(default = true): When enabled, the mod will work with soul campfires.<br><br><br><span style="font-size:18px"><strong>A GIF showing the effect:</strong></span></p>
<div class="spoiler">
<p><img src="https://github.com/ricksouth/serilum-mc-mods/raw/master/cdn/healing-campfire/a.gif"></p>
</div>
<p><br>------------------<br><br><span style="font-size:24px"><strong>You may freely use this mod in any modpack, as long as the download remains hosted within the CurseForge or Modrinth ecosystem.</strong></span><br><br><span style="font-size:18px"><a style="font-size:18px;color:#008000" href="https://serilum.com/" rel="nofollow">Serilum.com</a> contains an overview and more information on all mods available.</span><br><br><span style="font-size:14px">Comments are disabled as I'm unable to keep track of all the separate pages on each mod.</span><span style="font-size:14px"><br>For issues, ideas, suggestions or anything else there is the&nbsp;<a style="font-size:14px;color:#008000" href="https://github.com/ricksouth/serilum-mc-mods/" rel="nofollow">Github repo</a>. Thanks!</span><span style="font-size:6px"><br><br></span><a href="https://ricksouth.com/donate" rel="nofollow"><img src="https://raw.githubusercontent.com/ricksouth/serilum-mc-mods/master/description/Shields/donation_rounded.svg" alt="" width="306" height="50"></a></p>