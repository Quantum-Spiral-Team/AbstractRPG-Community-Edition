package com.vivern.arpg.proxy;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.entity.*;
import com.vivern.arpg.hooks.ARPGHooks;
import com.vivern.arpg.hooks.coloredlightning.ColoredLightning;
import com.vivern.arpg.items.CryonedBlock;
import com.vivern.arpg.items.models.*;
import com.vivern.arpg.main.*;
import com.vivern.arpg.mobs.*;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.renders.*;
import com.vivern.arpg.renders.mobrender.*;
import com.vivern.arpg.shader.ShaderMain;
import com.vivern.arpg.weather.Weather;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"deprecation", "ConstantConditions", "unchecked"})
@EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    //   public static AnimationTimer animationTimer = new AnimationTimer(); UNUSED
    public static List<ResourceLocation> sand = new ArrayList<>();
    public static List<ResourceLocation> coloredAcidTex = new ArrayList<>();
    public static List<ResourceLocation> fireDetex = new ArrayList<>();
    public static QuadrocopterBeltModel quadrocopterBeltModel;

    private static final Logger LOGGER = AbstractRPG.getLogger(ClientProxy.class.getSimpleName());

    @Override
    public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException {
        super.preInit(event);
        Keys.register();
        RenderRegister.registerFluidsRender();
        RenderingRegistry.registerEntityRenderingHandler(EntityFlyApple.class, new SnowballRender(Items.APPLE));
        RenderingRegistry.registerEntityRenderingHandler(AppleEffect.class, new RenderSplash("arpg:textures/splash_apple.png", 1.0F, 10, 1.0F, 0.0F, 0.0F, 0.0F, false, -1, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityIchor.class, new SnowballRender(null));
        RenderingRegistry.registerEntityRenderingHandler(IchorEffect.class, new RenderSplash("arpg:textures/ichor.png", 0.5F, 4, 0.5F, 0.0F, 0.0F, 0.0F, false, -1, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(SharkRocket.class, new RenderRocketFactory("arpg:textures/shark_rocket.png", SharkRocketModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityBoomerangMagic.class, new RenderBoomerangFactory(ItemsRegister.MAGIC_BOOMERANG));
        RenderingRegistry.registerEntityRenderingHandler(GUNParticle.class, new GUNPFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, new RenderSplash("arpg:textures/butterflyswing.png", 0.7F, 3, 0.7F, 0.7F, 0.0F, 0.0F, false, -1, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntitySunrise.class, new SpearRenderFactory(ItemsRegister.SUNRISE, 2.5F, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityLaserParticle.class, new LaserFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityStreamLaserP.class, new StreamLaserFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityVampireKnife.class, new RenderRocketFactory("arpg:textures/vampire_knifes.png", VampireKnifes.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(BloodDrop.class, new RenderSplash("arpg:textures/blooddrop.png", 0.1F, 1, 0.1F, 0.0F, 0.0F, 0.0F, true, -1, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityCubicParticle.class, new CubikFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityFrostBolt.class, new BlockEntityFactory(new ResourceLocation("arpg:textures/ice_cube.png"), 0.02F, 0.02F, 0.02F, 99, 1.0F, 1.0F, 1.0F, true, 1.0F, 1.0F, 1.0F, 0.08F, true));
        RenderingRegistry.registerEntityRenderingHandler(GunPEmitter.class, new GUNPFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityLaserDisk.class, new JSONFactory());
        RenderingRegistry.registerEntityRenderingHandler(ElementProjectile.class, new RenderRocketFactory("arpg:textures/el_projectile_tex.png", ElProjectileModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(StingerBoltEntity.class, new RenderRocketFactory("arpg:textures/stinger_bolt_tex.png", StingerBoltModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityStingerShard.class, new BlockEntityFactory(new ResourceLocation("arpg:textures/stinger_shard_tex.png"), 0.01F, 0.01F, 0.01F, 99, 1.0F, 1.0F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.28F, true));
        RenderingRegistry.registerEntityRenderingHandler(FireworkEntity.class, new FireworkRenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(DragonFireworkEntity.class, new RenderRocketFactory("arpg:textures/dragon_firework_tex.png", DragonFireworkModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(BetweenParticle.class, new BetweenRenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(FreezingParticle.class, new FreezingFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySand.class, new RenderSplash("arpg:textures/minisand1.png", 0.02F, 1, 0.02F, 0.0F, 0.0F, 0.0F, false, -1, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(TrailParticle.class, new GUNPFactory());
        RenderingRegistry.registerEntityRenderingHandler(BilebiterShoot.class, new RenderSplash("arpg:textures/bilebiter_shoot3.png", 0.13F, 1, 0.13F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(BilebiterHomingShoot.class, new RenderRocketFactory("arpg:textures/bilebiter_homing_tex.png", BilebiterHomingModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlacedItem.class, new PlacedItemFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricAcidRadiationPotion.class, new SnowballRender(Items.GLASS_BOTTLE));
        RenderingRegistry.registerEntityRenderingHandler(StaffFireballEntity.class, new RenderSplash("arpg:textures/fireball.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(SnowstormEntity.class, new RenderSplash("arpg:textures/cold.png", 0.3F, 1, 0.3F, 0.0F, 0.0F, 0.0F, true, 150, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricBolt.class, new RenderSplash("arpg:textures/electric_bolt.png", 0.3F, 1, 0.3F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntitySlimeBullet.class, new RenderRocketFactory("arpg:textures/slimebullet.png", CubikModel.class, 0.3F, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityFiremageSetBonus.class, new RenderSplash("arpg:textures/fireball.png", 0.15F, 1, 0.15F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(GraplingHookParticle.class, new GHFactory());
        RenderingRegistry.registerEntityRenderingHandler(EnderSeerProjectile.class, new RenderSplash("arpg:textures/ender_seer_proj.png", 0.15F, 1, 0.15F, 0.0F, 0.0F, 0.0F, false, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(FishingHook.class, new RenderSplash("arpg:textures/floater1.png", 0.05F, 1, 0.15F, 0.0F, 0.0F, 0.0F, false, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(NetherGrinderBullet.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModCubic(new ResourceLocation("arpg:textures/nether_grinder_bullet_tex.png"), 0.002F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 1, 0.0F)).add(new RenderModule.RModCeratargetTail(new ResourceLocation("arpg:textures/bullet_volumetric_tail.png"), 1.5F, 0.5F, 3.0F, 0.012F, 0.006F, -1).setMulticolored()));
        RenderingRegistry.registerEntityRenderingHandler(CannonSnowball.class, new SnowballRender(Items.SNOWBALL));
        RenderingRegistry.registerEntityRenderingHandler(EntitySnowflakeShuriken.class, new RenderBoomerangFactory(ItemsRegister.SNOWFLAKE_SHURIKEN));
        RenderingRegistry.registerEntityRenderingHandler(GraveLurkerProjectile.class, new RenderSplash("arpg:textures/fireball2.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityThrowedReaper.class, new RenderBoomerangFactory(ItemsRegister.REAPER));
        RenderingRegistry.registerEntityRenderingHandler(EntitySwordGhost.class, new RenderSplash("arpg:textures/sword_ghost.png", 0.17F, 1, 0.17F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(ToxicNuke.class, new RenderRocketFactory("arpg:textures/toxic_nuke_model_tex.png", ToxicNukeModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(ToxicNukeShard.class, new RenderSplash("arpg:textures/acid_splash3.png", 0.3F, 1, 0.3F, 0.0F, 0.0F, 0.0F, true, 200, DestFactor.SRC_COLOR));
        RenderingRegistry.registerEntityRenderingHandler(CryoGunEntity.class, new RenderRocketFactory("arpg:textures/cryo_gun_entity_model_tex.png", CryoGunEntityModel.class, 1.4F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityMinigunIcicle.class, new RenderRocketFactory("arpg:textures/minigun_icicle_model_tex.png", MinigunIcicleModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntitySummon.class, new RenderSplash("arpg:textures/entity_summon.png", 0.18F, 1, 0.18F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(PlasmaRailgunShoot.class, new BlockEntityFactory(new ResourceLocation("arpg:textures/plasma_railgun_bullet_tex.png"), 0.003F, 0.003F, 0.01F, 240, 1.0F, 1.0F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.08F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityHeadShooter.class, new BlockEntityFactory(new ResourceLocation("arpg:textures/nether_grinder_bullet_tex.png"), 0.0035F, 0.0035F, 0.0035F, 220, 1.0F, 1.0F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.08F, false));
        RenderingRegistry.registerEntityRenderingHandler(PlasmaPistolShoot.class, new RenderSplash("arpg:textures/plasma_pistol_shoot.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityBogFlower.class, new RenderSplash("arpg:textures/blob.png", 0.08F, 1, 0.08F, 0.0F, 0.0F, 0.0F, true, 150, DestFactor.SRC_COLOR));
        RenderingRegistry.registerEntityRenderingHandler(PistolFishStrike.class, new RenderRocketFactory("arpg:textures/pistol_fish_strike_model_tex.png", PistolFishStrikeModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(BubbleFishShoot.class, new RenderSplash("arpg:textures/blob.png", 0.06F, 1, 0.06F, 0.0F, 0.0F, 0.0F, true, -1, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(LavaDropperShoot.class, new RenderSplash("arpg:textures/lava_dropper_shoot.png", 0.3F, 12, 0.3F, 0.0F, 0.0F, 0.0F, false, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(PlasmaAcceleratorShoot.class, new RenderSplash("arpg:textures/plasma_accelerator_shoot.png", 0.25F, 1, 0.25F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(VacuumGunShoot.class, new BlockEntityFactory(new ResourceLocation("arpg:textures/plasma_railgun_bullet_tex.png"), 0.005F, 0.005F, 0.005F, 200, 0.8F, 0.6F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.08F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityShard.class, new ShardRenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(GemStaffShoot.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.21F, 1, 0.21F, 0.0F, 0.0F, 0.0F, true, 200, DestFactor.ONE, true));
        RenderingRegistry.registerEntityRenderingHandler(SweepParticle.class, new SweepParticleFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowFirejet.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_firejet.png")));
        RenderingRegistry.registerEntityRenderingHandler(CustomMobProjectile.class, new CustomProjectileRender.CustomProjectileFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityAcidFire.class, new RenderSplash("arpg:textures/acid_orb.png", 0.05F, 1, 0.05F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(ParticleGore.class, new RenderParticleGore.ParticleGoreFactory());
        RenderingRegistry.registerEntityRenderingHandler(NailGunShoot.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/nail_gun_shoot.png"), 0.06625F, -1));
        RenderingRegistry.registerEntityRenderingHandler(CrystalStarShoot.class, new RenderRocketFactory("arpg:textures/crystal_star_shoot_model_tex.png", CrystalStarShootModel.class, 3.0F, false, 0.0027F, false));
        RenderingRegistry.registerEntityRenderingHandler(LordOfPainSpike.class, new RenderSpecial.RenderSpecialFactory(1, new ResourceLocation("arpg:textures/lord_of_pain_spike_model_tex.png")));
        RenderingRegistry.registerEntityRenderingHandler(CrystalFanShoot.class, new RenderSpecial.RenderSpecialFactory(2, new ResourceLocation("arpg:textures/crystal_fan_shoot.png")));
        RenderingRegistry.registerEntityRenderingHandler(CrystalFanBonus.class, new RenderSplash("arpg:textures/crystal_bonus.png", 0.36F, 14, 0.36F, 0.0F, 0.0F, 0.0F, true, 100, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityCoin.class, new RenderCoin.RenderCoinFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityGeomanticCrystal.class, new RenderGeomanticCrystal.GeomanticCrystalFactory());
        RenderingRegistry.registerEntityRenderingHandler(ThornkeeperShoot.class, new RenderRocketFactory("arpg:textures/thorn_keeper_shoot.png", ThornkeeperShootModel.class, 0.7F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityThistleThorn.class, new RenderRocketFactory("arpg:textures/entity_thistle_thorn_model_tex.png", EntityThistleThornModel.class, 1.1F, false));
        RenderingRegistry.registerEntityRenderingHandler(SpikedBurst.class, new RenderRocketFactory("arpg:textures/spiked_burst_model_tex.png", SpikedBurstModel.class, 1.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityRestlessSkull.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.13F, 1, 0.13F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicRocket.class, new RenderSplash("arpg:textures/magic_rocket.png", 0.22F, 1, 0.22F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE, true));
        RenderingRegistry.registerEntityRenderingHandler(StingingCellEntity.class, new RenderStingingCell.StingingCellRenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySeaEffloresce.class, new RenderSeaEffloresce.SeaEffloresceFactory());
        RenderingRegistry.registerEntityRenderingHandler(CoralRifleBullet.class, new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/coral_bullet_arrow_tex.png"), 0.03F, 150, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityTimelessSword.class, new RenderSpecial.RenderSpecialFactory(3, new ResourceLocation("arpg:textures/entity_timeless_sword.png")));
        RenderingRegistry.registerEntityRenderingHandler(WhipParticle.class, new RenderWhip.RenderWhipFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityFrostfireExplosive.class, new RenderSpecial.RenderSpecialFactory(5, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(EntityAcidBomb.class, new RenderSpecial.RenderSpecialFactory(6, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(EntityAcidBomb.AcidBombBlob.class, new BlockEntityFactory(new ResourceLocation("arpg:textures/slimebullet.png"), 0.02F, 0.02F, 0.02F, 200, 1.0F, 0.9F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.14F, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityMagneticField.class, new RenderSpecial.RenderSpecialFactory(7, new ResourceLocation("arpg:textures/forcefield.png")));
        RenderingRegistry.registerEntityRenderingHandler(HadronBlasterShoot.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.14F, 1, 0.14F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE, true).setColor(1.0F, 0.1F, 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(HadronBlasterShoot.HadronBlasterBonus.class, new RenderSplash("arpg:textures/stone3_tr.png", 0.3F, 17, 0.3F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE, true));
        RenderingRegistry.registerEntityRenderingHandler(AnimatedGParticle.class, new AnimatedGunPRender.AnimGUNPFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntitySnapball.class, new RenderSpecial.RenderSpecialFactory(8, new ResourceLocation("arpg:textures/snap_ball.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityLaunchedRocket.class, new RenderRocketFactory("arpg:textures/rocket.png", RocketModel.class, 1.3F, false, 0.0F, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityMiniNuke.class, new RenderSpecial.RenderSpecialFactory(9, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(NexusCap.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.0F, 1, 0.0F, 0.0F, 0.0F, 0.0F, false, 200, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(EntityChainMace.class, new RenderSpecial.RenderSpecialFactory(10, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderSpecial.RenderSpecialFactory(11, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(AdvancedFallingBlock.class, new RenderAdvFallingBlock.RenderAdvFallBlockFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityLiveHeart.class, new RenderLiveHeart.RenderLiveHeartFactory());
        RenderingRegistry.registerEntityRenderingHandler(BulletParticle.class, new RenderBulletParticle.BulletParticleFactory());
        RenderingRegistry.registerEntityRenderingHandler(BlowholeShoot.class, new RenderSpecial.RenderSpecialFactory(12, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(EntityCrystalCutter.class, new RenderSpecial.RenderSpecialFactory(13, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(CeratargetShoot.class, new RenderSpecial.RenderSpecialFactory(14, new ResourceLocation("arpg:textures/ceratarget_shoot.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowFrozen.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_frozen.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowVicious.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_vicious.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowModern.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_modern.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowBengal.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_bengal.png"), 0.05625F, 200));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowFish.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_fish.png")).setTilt(false).setHorizontalShake(true));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowVoid.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_void.png"), 0.058F, 160).setBlend(true));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowShell.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_shell.png")));
        RenderingRegistry.registerEntityRenderingHandler(ShellShard.class, new RenderRocketFactory("arpg:textures/shell_shard_model_tex.png", ShellShardModel.class, 1.5F, false));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowBouncing.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_bouncing.png")).setBlend(true));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowMithril.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_mithril.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowTwin.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_twin.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntityArrowWind.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_wind.png")).setBlend(true));
        RenderingRegistry.registerEntityRenderingHandler(AzureOreShoot.class, new RenderSplash("arpg:textures/azure_ore_shoot.png", 0.17F, 4, 0.17F, 0.0F, 0.0F, 0.0F, true, 230, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(ModelledPartickle.class, new ModelledPartickleRender.ModelledPartickleFactory());
        RenderingRegistry.registerEntityRenderingHandler(ViolenceShoot.class, new RenderSplash("arpg:textures/fireball2.png", 0.16F, 1, 0.16F, 0.0F, 0.0F, 0.0F, true, 230, DestFactor.ONE));
        RenderingRegistry.registerEntityRenderingHandler(WhispersShoot.class, new RenderSpecial.RenderSpecialFactory(17, new ResourceLocation("arpg:textures/whispers_shoot.png")));
        RenderingRegistry.registerEntityRenderingHandler(EntitySpellForgeCatalyst.class, new RenderSpecial.RenderSpecialFactory(19, TextureMap.LOCATION_BLOCKS_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(ParticleTentacle.class, new ParticleTentacleRender.ParticleTentacleFactory());
        RenderingRegistry.registerEntityRenderingHandler(XmassRocket.class, new RenderRocketFactory("arpg:textures/xmass_rocket_model_tex.png", XmassRocketModel.class, 1.0F, false, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(XmassBall.class, new RenderRocketFactory("arpg:textures/xmass_rocket_model_tex.png", XmassBallModel.class, 2.0F, false, 0.0F, false));
        RenderingRegistry.registerEntityRenderingHandler(CoralPolyp.class, new RenderSpecial.RenderSpecialFactory(20, new ResourceLocation("arpg:textures/coral_polyp_body.png")));
        RenderingRegistry.registerEntityRenderingHandler(EnigmateTwinsShoot.class, new RenderSplash("arpg:textures/enigmate_shoot.png", 0.05F, 4, 0.05F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE_MINUS_SRC_ALPHA));
        RenderingRegistry.registerEntityRenderingHandler(CryonedBlock.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModRenderByBoundingBox(new ResourceLocation("arpg:textures/cryoned_block.png"), 140, 1.0F, 1.0F, 1.0F, 1.0F, true, false).setBlendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_DST_COLOR)));
        RenderingRegistry.registerEntityRenderingHandler(CryoDestroyerSpray.class, new RenderModular.RenderModularFactory().setDelayedRender(2).add(new RenderModule.RModTail(new ResourceLocation("arpg:textures/hail_trace.png"), 0.1F, 2.5F, false, 0.25F, 170, 0.5F, 0.5F, 0.5F, 1.0F, true, 0).setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)));
        RenderingRegistry.registerEntityRenderingHandler(WandColdShoot.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModTail(new ResourceLocation("arpg:textures/cold_tail.png"), 0.15F, 3.2F, true, 0.125F, 200, 1.0F, 1.0F, 1.0F, 1.0F, true, 0).setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)).add(new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/simple_magic_shoot.png"), 0.18F, 200, 0.4F, 0.7F, 1.0F, 1.0F, true, 0.0F).setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)));
        RenderingRegistry.registerEntityRenderingHandler(WandColdWave.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModCutter(new ResourceLocation("arpg:textures/wand_of_cold_wave.png"), 0.0F, 3.0F, 0.13F, 200, 1.0F, 1.0F, 1.0F, 1.0F, true, 0.0F, WandColdWave.ticksForMaxWidth, 28, 7).setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)));
        RenderingRegistry.registerEntityRenderingHandler(SurvivorLootSpawner.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModCubic(new ResourceLocation("arpg:textures/survivor_loot_spawner.png"), 0.16F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 1, 0.0F)).add(new RenderModule.RModCeratargetTail(new ResourceLocation("arpg:textures/bullet_volumetric_tail.png"), 120.0F, 0.5F, 20.0F, 0.48000002F, 0.24000001F, -1)));
        CrystalStarShootModel crystalStarShootModel = new CrystalStarShootModel();
        RenderingRegistry.registerEntityRenderingHandler(CrystalStarPoweredShoot.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 1)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 15)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 13)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 88)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 959)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 113)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 4)).add(new RenderModule.RModModel(new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"), crystalStarShootModel, 0.15F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 2, 0.0F, 7)));
        RenderingRegistry.registerEntityRenderingHandler(PlasmaRifleBall.class, new RenderModular.RenderModularFactory().add(new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/plasma_rifle_ball.png"), 0.17F, 240, 1.0F, 1.0F, 1.0F, 1.0F, true, 0.0F).setAnimation(34, 1, 0, false, true).setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE).setMulticolored()));
        RenderingRegistry.registerEntityRenderingHandler(EntityWeatherRocket.class, new RenderModular.RenderModularFactory().add(new RenderModule.RModModel(null, new WeatherRocketModel(), 0.0625F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 3, 0.0F)));
        RenderingRegistry.registerEntityRenderingHandler(EntityHangingAllSides.class, new RenderHangingAllSides.RenderHangingAllSidesFactory());
        RenderingRegistry.registerEntityRenderingHandler(BigLightningStrike.class, new RenderBigLightningStrike.BigLightningStrikeFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityBossLoot.class, new RenderBossLoot.BossLootFactory());
        RenderingRegistry.registerEntityRenderingHandler(SummonedBlaze.class, RenderSummonedBlaze.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(Gnater.class, RenderGnater.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(WhiteSlime.class, RenderWhiteSlime.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(Troglodyte.class, RenderTroglodyte.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(Moonshroom.class, RenderMoonshroom.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(Blubber.class, RenderBlubber.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityPart.class, new RenderSpecial.RenderSpecialFactory(-1, null));
        ToxicomaniaMobsPack.initRender();
        NPCMobsPack.initRender();
        EverfrostMobsPack.initRender();
        StormledgeMobsPack.initRender();
        AquaticaMobsPack.initRender();
        OtherMobsPack.initRender();
        DungeonMobsPack.initRender();
        HostileProjectiles.registerRender();
        InitMobRenders.init();
        ColoredLightning.preInitLight();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        RenderRegister.registerItemsRender();
        RenderRegister.registerTileEntitySpecialRenderers();
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand1.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand2.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand3.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand4.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand5.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand6.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand7.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand8.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand9.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand10.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand11.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand12.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand13.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand14.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand15.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand16.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand17.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand18.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand19.png"));
        sand.add(new ResourceLocation("arpg:textures/de_sand/sand19.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid1.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid2.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid3.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid4.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid5.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid6.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid7.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid8.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid9.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid10.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid11.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid12.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid13.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid14.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid15.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid16.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid17.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid18.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid19.png"));
        coloredAcidTex.add(new ResourceLocation("arpg:textures/de_acid/acid19.png"));

        for (int f = 1; f < 20; f++) {
            fireDetex.add(new ResourceLocation("arpg:textures/de_fire/fire" + f + ".png"));
        }

        Weather.registerWeatherRender(Weather.RENDERNETHERSMOKE);
        Freezing.initIceLayers();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ShaderMain.register();
        RenderFluidHelper.init();
        DeathEffects.initMainTextures();
        InvasionInfo.initInfos();

        try {
            for (Field field : BlockModelRenderer.class.getDeclaredFields()) {
                if (field.getType() == BlockColors.class) {
                    field.setAccessible(true);
                    ARPGHooks.blockColors = (BlockColors) field.get(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer());
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException var6) {
            LOGGER.fatal(var6);
        }

        LOGGER.debug("arpg | Applying reflection to GlStateManager.activeTextureUnit");

        for (Field field : GlStateManager.class.getDeclaredFields()) {
            if (field.getName().equals("activeTextureUnit") && !field.isAccessible()) {
                field.setAccessible(true);
            }
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        BlocksRegister.registerStateMappers();
        BlocksRegister.registerBlocksRender();
    }

    @Override
    public void playWingsSound(EntityPlayer player, @Nullable MovingSound sound) {
        if (player instanceof EntityPlayerSP && sound != null) {
            Minecraft.getMinecraft().getSoundHandler().playSound(sound);
        }
    }

}
