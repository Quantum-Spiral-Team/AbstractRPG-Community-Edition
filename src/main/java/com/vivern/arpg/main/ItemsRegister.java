package com.vivern.arpg.main;

import baubles.api.BaubleType;
import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.items.*;
import com.vivern.arpg.items.armor.Armors;
import com.vivern.arpg.items.armor.BoneHelm;
import com.vivern.arpg.items.armor.JungleBoots;
import com.vivern.arpg.items.armor.JungleChestplate;
import com.vivern.arpg.items.armor.JungleHelm;
import com.vivern.arpg.items.armor.JungleLeggins;
import com.vivern.arpg.items.armor.LichHelm;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.TEISRGuns;
import com.vivern.arpg.renders.TEISROther;
import com.vivern.arpg.tileentity.ChestLock;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
@EventBusSubscriber
public class ItemsRegister {

   private static final Logger LOGGER = AbstractRPG.getLogger("ItemsRegister");

   public static final Set<Item> FOR_RENDER = new HashSet<>();
   public static final ArmorMaterial NULL_MATERIAL = EnumHelper.addArmorMaterial(
      "arpg:null_mt", "arpg:null_tx", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F
   );

   public static final Item FIRST = new ItemFirst();
   public static final Item ICHOR_SHOWER = new IchorShower();
   public static final Item SHARK_CANNON = new SharkCannon();
   public static final Item SHARK_AMMO = new SharkAmmo();
   public static final Item MAGIC_BOOMERANG = new MagicBoomerang();
   public static final Item BUTTERFLY = new Butterfly();
   public static final Item SUNRISE = new Sunrise();
   public static final Item LASER_SNIPER = new LaserSniper();
   public static final Item ION_BATTERY = new ItemAccumulator("ion_battery", ItemAccumulator.REDSTONE_ION_CAPACITY, ItemAccumulator.REDSTONE_ION_THROUGHPUT);
   public static final Item LASER_PISTOL = new LaserPistol();
   public static final Item LASER_RIFLE = new LaserRifle();
   public static final Item VAMPIRE_KNIFE = new VampireKnife();
   public static final Item VAMPIRE_KNIFES = new VampireKnifes();
   public static final Item FROST_BOLT_STAFF = new FrostBoltStaff();
   public static final Item ANNIHILATION_GUN = new AnnihilationGun();
   public static final Item ANTIMATTER_CHARGE = new AntimatterCharge();
   public static final Item ELEMENTAL_AMMO_FIRE = new ElementalAmmoFire();
   public static final Item ELEMENTAL_AMMO_WATER = new ElementalAmmoWater();
   public static final Item ELEMENTAL_AMMO_AIR = new ElementalAmmoAir();
   public static final Item ELEMENTAL_AMMO_EARTH = new ElementalAmmoEarth();
   public static final Item ELEMENT_FOCUS = new ElementFocus();
   public static final Item STINGER = new Stinger();
   public static final Item STINGER_BOLTS = new StingerBolts();
   public static final Item FIREWORK_LAUNCHER = new FireworkLauncher();
   public static final Item FIREWORK_PACK = new FireworkPack();
   public static final Item FIREWORK_DRAGON_ROCKET = new FireworkDragonRocket();
   public static final Item QUADROCOPTER_BELT = new QuadrocopterBelt();
   public static final Item VORTEX_IN_A_BOTTLE = new VortexInABottle();
   public static final Item ETHER_SIGN = new EtherSign();
   public static final Item PHOENIX_GHOST_CAPE = new PhoenixGhostCape();
   public static final Item SCEPTER_OF_SANDS = new ScepterOfSands();
   public static final Item BILEBITER = new Bilebiter();
   public static final Item BILEBITER_SPHERE = new ItemAmmo(CreativeTabs.COMBAT, "bilebiter_sphere", 64, 1);
   public static final Item ELECTRIC_ACID_RADIATION_POTION = new ElectricAcidRadiationPotion();
   public static final Item FIREBALL_STAFF = new FireballStaff();
   public static final Item SNOWSTORM_STAFF = new SnowstormStaff();
   public static final Item ELECTROSTATIC = new ElectricStaff();
   public static final Item SLIME_SHOTGUN = new SlimeShotgun();
   public static final Item WIZARD_HELM = Armors.wizardSET.HELMET;
   public static final Item WIZARD_CHEST = Armors.wizardSET.CHESTPLATE;
   public static final Item WIZARD_LEGS = Armors.wizardSET.LEGGINS;
   public static final Item WIZARD_BOOTS = Armors.wizardSET.BOOTS;
   public static final Item FIRE_MAGE_HELM = Armors.firemageSET.HELMET;
   public static final Item FIRE_MAGE_CHEST = Armors.firemageSET.CHESTPLATE;
   public static final Item FIRE_MAGE_LEGS = Armors.firemageSET.LEGGINS;
   public static final Item FIRE_MAGE_BOOTS = Armors.firemageSET.BOOTS;
   public static final Item FIRE_LORD_HELM = Armors.firelordSET.HELMET;
   public static final Item FIRE_LORD_CHEST = Armors.firelordSET.CHESTPLATE;
   public static final Item FIRE_LORD_LEGS = Armors.firelordSET.LEGGINS;
   public static final Item FIRE_LORD_BOOTS = Armors.firelordSET.BOOTS;
   public static final Item WAND_OF_BLAZES = new WandOfBlazes();
   public static final Item GRAPLING_HOOK = new GraplingHook().setRegistryName("grapling_hook").setTranslationKey("grapling_hook");
   public static final Item JUNGLE_GRAPLING_HOOK = new JungleGraplingHook();
   public static final Item SEASHELL = new ItemShell();
   public static final Item SLIME_GRAPLING_HOOK = new SlimeGraplingHook();
   public static final Item ENDER_GRAPLING_HOOK = new EnderGraplingHook();
   public static final Item FISHING_ROD = new ItemFishingRod().setRegistryName("fishing_rod").setTranslationKey("fishing_rod");
   public static final Item MOLTEN_GREAT_AXE = new MoltenGreatAxe();
   public static final Item ICE_SWORD = new IceSword();
   public static final Item NETHER_GRINDER = new NetherGrinder();
   public static final Item NETHER_GRINDER_AMMO = new ItemAmmoClip("nether_grinder_ammo", CreativeTabs.COMBAT, 64, 35);
   public static final Item SNOWBALL_CANNON = new SnowballCannon();
   public static final Item SNOWFLAKE_SHURIKEN = new SnowflakeShuriken();
   public static final Item CURSED_BLADE = new CursedBlade();
   public static final Item GRAVE_LURKER = new GraveLurker();
   public static final Item REAPER = new Reaper();
   public static final Item GHOST_SWORD = new GhostSword();
   public static final Item STAFF_OF_CORPSE = new StaffOfCorpse();
   public static final Item CHAIN_DAGGER = new ChainDagger();
   public static final Item DEAD_RAMPART = new DeadRampart();
   public static final Item TOXIC_NUCLEAR_CANNON = new ToxicNuclearCannon();
   public static final Item TOXIC_NUCLEAR_WARHEAD = new ItemAmmo(CreativeTabs.COMBAT, "toxic_nuclear_warhead", 64, 1);
   public static final Item CRYOGUN = new CryoGun();
   public static final Item CRYOGEN_CELL = new ItemAmmo(CreativeTabs.COMBAT, "cryogen_cell", 64, 1);
   public static final Item EMPTY_CELL = new ItemAmmo(CreativeTabs.MATERIALS, "empty_cell", 64, 1);
   public static final Item CHARGER = new Charger();
   public static final Item LIGHTNING_HOOK = new LightningHook();
   public static final Item ICICLE_MINIGUN = new IcicleMinigun();
   public static final Item STATIC_LANCE = new StaticLance();
   public static final Item RING_OF_PROTECTION = new RingOfProtection();
   public static final Item SPIKE_RING = new SpikeRing();
   public static final Item SPARKLING_NECKLACE = new SparklingNecklace();
   public static final Item EXP = new ExpItem();
   public static final Item DEMONIC_IGNITER = new DemonicIgniter();
   public static final Item BOUNCING_RING = new BouncingRing();
   public static final Item SLIME_HELM = Armors.slimeSET.HELMET;
   public static final Item SLIME_CHEST = Armors.slimeSET.CHESTPLATE;
   public static final Item SLIME_LEGS = Armors.slimeSET.LEGGINS;
   public static final Item SLIME_BOOTS = Armors.slimeSET.BOOTS;
   public static final Item ICE_HELM = Armors.iceSET.HELMET;
   public static final Item ICE_CHEST = Armors.iceSET.CHESTPLATE;
   public static final Item ICE_LEGS = Armors.iceSET.LEGGINS;
   public static final Item ICE_BOOTS = Armors.iceSET.BOOTS;
   public static final Item SOUL_CHARM = new SoulCharm();
   public static final Item CONIFER_ROD = new ConiferRod();
   public static final Item JUNGLE_HELM = new JungleHelm(); // TODO: перенести в Armors
   public static final Item JUNGLE_CHESTPLATE = new JungleChestplate(); // TODO: перенести в Armors
   public static final Item JUNGLE_LEGGINS = new JungleLeggins(); // TODO: перенести в Armors
   public static final Item JUNGLE_BOOTS = new JungleBoots(); // TODO: перенести в Armors
   public static final Item WEB_GRAPLING_HOOK = new WebGraplingHook();
   public static final Item ROPE_GRAPLING_HOOK = new RopeGraplingHook();
   public static final Item PLASMA_RAILGUN = new PlasmaRailgun();
   public static final Item PLASMA_RAILGUN_BOLTS = new ItemAmmo(CreativeTabs.COMBAT, "plasma_railgun_bolts", 64, 1);
   public static final Item PLASMA_RIFLE = new PlasmaRifle();
   public static final Item HOLOGRAPHIC_SHIELD = new HolographicShield();
   public static final Item HEAD_SHOOTER = new HeadShooter();
   public static final Item PLASMA_PISTOL = new PlasmaPistol();
   public static final Item AIM_LENS = new AimLens();
   public static final Item BOG_FLOWER = new BogFlower();
   public static final Item FISH_FEED = new ItemAmmo(CreativeTabs.COMBAT, "fish_feed", 64, 1);
   public static final Item PISTOL_FISH = new PistolFish();
   public static final Item BUBBLEFISH = new BubbleFish();
   public static final Item VICIOUS_EMBLEM = new ViciousEmblem();
   public static final Item ORB_OF_DESTROY = new OrbOfDestroy();
   public static final Item WOODEN_SKIING = new WoodenSkiing();
   public static final Item VIAL_OF_POISON = new VialOfPoison();
   public static final Item VAMPIRIC_HEART = new VampiricHeart();
   public static final Item LAVA_DROPPER = new LavaDropper();
   public static final Item PLASMA_ACCELERATOR = new PlasmaAccelerator();
   public static final Item VACUUM_GUN = new VacuumGun();
   public static final Item GEM_STAFF = new GemStaff();
   public static final Item WAND_OF_COLD = new WandOfCold();
   public static final Item ICE_BEAM = new IceBeam();
   public static final Item FROZEN_WINGS = new FrozenWings();
   public static final Item TOXIC_WINGS = new ToxicWings();
   public static final Item FAIRY_WINGS = new FairyWings();
   public static final Item ICE_FLOWER_SEEDS = BlocksRegister.ICE_FLOWER.seed;
   public static final Item CRIMBERRY_SEEDS = BlocksRegister.CRIMBERRY.seed;
   public static final Item WINTER_WILLOW_SEEDS = BlocksRegister.WINTER_WILLOW.seed;
   public static final Item GLACIDE_BLADE = new GlacideBlade();
   public static final Item INFERNAL_BLADE = new InfernalBlade();
   public static final Item CINDER_BOW = new CinderBow();
   public static final Item SKULL_CRASHER = new SkullCrasher();
   public static final Item SPELL_HAMMER = new SpellHammer();
   public static final Item MOLTEN_INGOT = new ItemItem("molten_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item INFERNUM_INGOT = new ItemItem("infernum_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MOLTEN_NUGGET = new ItemItem("molten_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item INFERNUM_NUGGET = new ItemItem("infernum_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MOLTEN_STRING = new ItemItem("molten_string", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LIQUID_FIRE = new ItemItem("liquid_fire", CreativeTabs.MATERIALS, 0, 64).setFuel(200);
   public static final Item DEMONITE = new ItemItem("demonite", CreativeTabs.MATERIALS, 0, 64);
   public static final Item DEMONITE_SHARD = new ItemItem("demonite_shard", CreativeTabs.MATERIALS, 0, 64);
   public static final Item RUBY = new ItemItem("ruby", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static final Item SAPPHIRE = new ItemItem("sapphire", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static final Item CITRINE = new ItemItem("citrine", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static final Item AMETHYST = new ItemItem("amethyst", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static final Item TOPAZ = new ItemItem("topaz", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static final Item RHINESTONE = new ItemItem("rhinestone", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static final Item MAGIC_POWDER = new ItemItem("magic_powder", CreativeTabs.MATERIALS, 0, 64).setEnchantGlow();
   public static final Item ICE_GEM = new ItemItem("ice_gem", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WEATHER_FRAGMENTS = new ItemItem("weather_fragments", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SNOW_CLOTH = new ItemItem("snow_cloth", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CONIFER_STICK = new ItemItem("conifer_stick", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SOUL_STONE = new SoulStone();
   public static final Item SAPPHIRE_EYE = new SapphireEye();
   public static final Item INSTANT_ENCHANTMENT_BOOK = new InstantEnchantmentBook();
   public static final Item ACID_FIRE = new AcidFire();
   public static final Item GLOW_BLADE = new GlowBlade();
   public static final Item NAIL_GUN = new NailGun();
   public static final Item CRYSTAL_STAR = new CrystalStar();
   public static final Item THE_LORD_OF_PAIN = new TheLordOfPain();
   public static final Item VOLTRIDENT = new Voltrident();
   public static final Item CRYSTAL_FAN = new CrystalFan();
   public static final Item TOXIBERRY_ARCANO_SEEDS = BlocksRegister.TOXIBERRY_ARCANO.seed;
   public static final Item TOXIBERRY_VIBRANT_SEEDS = BlocksRegister.TOXIBERRY_VIBRANT.seed;
   public static final Item MOSS_PLANT_SEEDS = BlocksRegister.MOSS_PLANT.seed;
   public static final Item CONTEM_PLANT_SEEDS = BlocksRegister.CONTEM_PLANT.seed;
   public static final Item MUCOPHILLUS_SEEDS = BlocksRegister.MUCOPHILLUS.seed;
   public static final Item MUCOPHILLUS_BROWN_SEEDS = BlocksRegister.MUCOPHILLUS_BROWN.seed;
   public static final Item VISCOSA_SEEDS = BlocksRegister.VISCOSA.seed;
   public static final Item TOXIBERRY_WEEPING_SEEDS = BlocksRegister.TOXIBERRY_WEEPING.seed;
   public static final Item TOXINIA_SEEDS = BlocksRegister.TOXINIA.seed;
   public static final Item ARELIA_SEEDS = BlocksRegister.ARELIA.seed;
   public static final Item DECEIDUS_SEEDS = BlocksRegister.DECEIDUS.seed;
   public static final Item JUNKWEED_SEEDS = BlocksRegister.JUNKWEED.seed;
   public static final Item TOXEDGE_SEEDS = BlocksRegister.TOXEDGE.seed;
   public static final Item TOXIBULB_SEEDS = BlocksRegister.TOXIBULB.seed;
   public static final Item ANTIDOTE = new Antidote();
   public static final Item TOXI_COLA = new ToxiCola();
   public static final Item ANTI_POTION = new Antipotion();
   public static final Item WASTE_BURGER = new ItemEatable(
      "wasteburger",
      0,
      64,
      9,
      1.0F,
      false,
      32,
      new PotionEffect[]{new PotionEffect(MobEffects.POISON, 500), new PotionEffect(MobEffects.STRENGTH, 500)},
      new float[]{0.9F, 1.0F},
      false,
      50
   );
   public static final Item TOXIBERRY_MOJITO = new ItemEatable(
         "toxiberry_mojito",
         0,
         64,
         1,
         0.0F,
         false,
         25,
         new PotionEffect[]{new PotionEffect(PotionEffects.TOXIN, 50), new PotionEffect(MobEffects.SPEED, 850)},
         new float[]{0.8F, 1.0F},
         true,
         40
      )
      .setStackToReturn(new ItemStack(Items.GLASS_BOTTLE))
      .setAlwaysEdible();
   public static final Item TOXEDGE_BREAD = new ItemEatable(
      "toxedge_bread", 0, 64, 3, 0.5F, false, 32, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 50)}, new float[]{0.1F}, false, 20
   );
   public static final Item DECEIDUS_JUICE = new ItemEatable(
         "deceidus_juice",
         0,
         64,
         2,
         0.0F,
         false,
         20,
         new PotionEffect[]{
            new PotionEffect(PotionEffects.TOXIN, 50), new PotionEffect(PotionEffects.MAGIC_POWER, 550), new PotionEffect(MobEffects.SPEED, 250, 2)
         },
         new float[]{0.25F, 1.0F, 0.97F},
         true,
         30
      )
      .setAlwaysEdible();
   public static final Item BROWN_SLIME_WAND = new ToolWand(
         "brown_slime_wand", CreativeTabs.TOOLS, 1000, new ItemStack(Blocks.SLIME_BLOCK), true, "arpg:brown_slime"
      )
      .setReplaceLogic("minecraft:slime", "arpg:brown_slime");
   public static final Item SLIME_BLOB_WAND = new ToolWand(
      "slime_blob_wand", CreativeTabs.TOOLS, 1000, new ItemStack(Items.SLIME_BALL), true, "arpg:slime_blob"
   );
   public static final Item BONES_WAND = new ToolWand("bones_wand", CreativeTabs.TOOLS, 500, new ItemStack(Items.BONE, 2), true, "arpg:bones_pile");
   public static final Item GLOWING_TOXIBERRY = new ItemEatable(
         "glowing_toxiberry",
         0,
         64,
         3,
         0.2F,
         false,
         20,
         new PotionEffect[]{new PotionEffect(PotionEffects.TOXIN, 700), new PotionEffect(MobEffects.GLOWING, 700)},
         new float[]{0.99F, 0.99F},
         false,
         15
      )
      .setAlwaysEdible();
   public static final Item SMALL_TOXIBERRY = new ItemEatable(
         "small_toxiberry",
         0,
         64,
         1,
         0.0F,
         false,
         10,
         new PotionEffect[]{new PotionEffect(PotionEffects.TOXIN, 100), new PotionEffect(MobEffects.POISON, 100)},
         new float[]{0.99F, 0.99F},
         false,
         25
      )
      .setAlwaysEdible();
   public static final Item TOXINIUM_HELM = Armors.toxiniumSET.HELMET;
   public static final Item TOXINIUM_CHEST = Armors.toxiniumSET.CHESTPLATE;
   public static final Item TOXINIUM_LEGS = Armors.toxiniumSET.LEGGINS;
   public static final Item TOXINIUM_BOOTS = Armors.toxiniumSET.BOOTS;
   public static final Item COIN = new Coin();
   public static final Item TOXINIUMSHOTGUN = new ToxiniumShotgun();
   public static final Item LOCKER = new Locker();
   public static final Item SUBMACHINE = new Submachine();
   public static final Item SUBMACHINE_CLIP = new ItemAmmoClip("submachine_clip", CreativeTabs.COMBAT, 64, 40);
   public static final Item BULLET_FROZEN = new ItemBullet.BulletFrozen();
   public static final Item BULLET_COPPER = new ItemBullet("bullet_copper", "copper", 64, 0.0F, 0.0F, 0.9F, 0.5F, 0.3F);
   public static final Item BULLET_LEAD = new ItemBullet("bullet_lead", "lead", 64, 1.0F, 0.2F, 0.7F, 0.7F, 0.8F);
   public static final Item BULLET_SILVER = new ItemBullet.BulletSilver();
   public static final Item BULLE_TGOLD = new ItemBullet("bullet_gold", "gold", 64, 2.5F, 0.4F, 0.9F, 0.7F, 0.2F);
   public static final Item BULLET_INCENDIARY = new ItemBullet.BulletIncendiary();
   public static final Item TOXINIUM_SHOTGUN_CLIP = new ItemAmmoClip("toxinium_shotgun_clip", CreativeTabs.COMBAT, 64, 32);
   public static final Item BULLET_THUNDER = new ItemBullet.BulletThunder();
   public static final Item BULLET_POISONOUS = new ItemBullet.BulletPoisonous();
   public static final Item BULLET_TOXIC = new ItemBullet.BulletToxic();
   public static final Item BULLET_CRYSTAL = new ItemBullet.BulletCrystal();
   public static final Item BULLET_EXPLODING = new ItemBullet.BulletExploding();
   public static final Item BULLET_FESTIVAL = new ItemBullet.BulletFestival();
   public static final Item BULLET_NIVEOUS = new ItemBullet.BulletNiveous();
   public static final Item BULLET_ERRATIC = new ItemBullet.BulletErratic();
   public static final Item BULLET_CORAL = new ItemBullet.BulletCoral();
   public static final Item BULLET_DIVING = new ItemBullet.BulletDiving();
   public static final Item EMERALD_EYE = new EmeraldEye();
   public static final Item WEAPON_ENCHANTMENTS_BOX = new ItemLootCase("weapon_enchantments_box", CreativeTabs.MISC, 16, ItemLootCase.EMPTY_LIST);
   public static final Item SIMPLE_ENCHANTMENTS_BOX = new ItemLootCase("simple_enchantments_box", CreativeTabs.MISC, 16, ItemLootCase.EMPTY_LIST);
   public static final Item ALL_ENCHANTMENTS_BOX = new ItemLootCase("all_enchantments_box", CreativeTabs.MISC, 16, ItemLootCase.EMPTY_LIST);
   public static final Item GEOMANTIC_CRYSTAL = new ItemGeomanticCrystal();
   public static final Item MANGANESE_INGOT = new ItemItem("manganese_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MANGANESE_DUST = new ItemItem("manganese_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BERYLLIUM_INGOT = new ItemItem("beryllium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BERYLLIUM_DUST = new ItemItem("beryllium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CHROMIUM_INGOT = new ItemItem("chromium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CHROMIUM_DUST = new ItemItem("chromium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AIRBORNE_CIRCLET = new AirborneCirclet();
   public static final Item IMPETUS = new Impetus();
   public static final Item GIFT = new ItemItem("gift", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CANDY_APPLE = new ItemEatable("candy_apple", 0, 64, 5, 0.3F, false, 32, null, null, false, 0);
   public static final Item CANDY_CANE = new ItemEatable("candy_cane", 0, 64, 3, 0.1F, false, 13, null, null, false, 0);
   public static final Item CRIMBERRY_WINE = new ItemEatable(
         "crimberry_wine", 0, 64, 2, 0.2F, false, 32, new PotionEffect[]{new PotionEffect(MobEffects.RESISTANCE, 400)}, new float[]{1.0F}, true, 0
      )
      .setStackToReturn(new ItemStack(Items.GLASS_BOTTLE))
      .setAlwaysEdible();
   public static final Item BODY_WARMER = new BaubleAntipotion(
      "body_warmer", CreativeTabs.COMBAT, PotionEffects.FREEZING, BaubleType.TRINKET, "Immunity to Freezing"
   );
   public static final Item DEVOURERS_TEETH = new BaubleAntipotion(
      "devourers_teeth", CreativeTabs.COMBAT, MobEffects.HUNGER, BaubleType.HEAD, "Immunity to Hunger potion effect"
   );
   public static final Item ENDER_LEECH = new BaubleAntipotion(
      "ender_leech", CreativeTabs.COMBAT, PotionEffects.ENDER_POISON, BaubleType.TRINKET, "Immunity to Ender Poison"
   );
   public static final Item GASEOUS_ENERGY_DRINK = new BaubleAntipotion(
      "gaseous_energy_drink", CreativeTabs.COMBAT, MobEffects.WEAKNESS, BaubleType.TRINKET, "Immunity to Weakness"
   );
   public static final Item MAGIC_CONTACT_LENSES = new BaubleAntipotion(
      "magic_contact_lenses", CreativeTabs.COMBAT, MobEffects.BLINDNESS, BaubleType.HEAD, "Immunity to Blidness"
   );
   public static final Item MINERS_GLOVE = new BaubleAntipotion(
      "miners_glove", CreativeTabs.COMBAT, MobEffects.MINING_FATIGUE, BaubleType.RING, "Immunity to Mining Fatigue"
   );
   public static final Item RUNNERS_SOCKS = new BaubleAntipotion(
      "runners_socks", CreativeTabs.COMBAT, MobEffects.SLOWNESS, BaubleType.TRINKET, "Immunity to Slowness"
   );
   public static final Item SLIME_EATER = new BaubleAntipotion(
         "slime_eater", CreativeTabs.COMBAT, PotionEffects.SLIME, BaubleType.TRINKET, "Immunity to Slime potion effect"
      )
      .setRender(1);
   public static final Item FIRE_EATER = new BaubleAntipotion(
         "fire_eater", CreativeTabs.COMBAT, PotionEffects.FIERYOIL, BaubleType.TRINKET, "Immunity to Fiery Oil"
      )
      .setRender(1);
   public static final Item SLIME_DEVOURER = new BaubleAntipotion(
      "slime_devourer",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.SLIME, MobEffects.HUNGER},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Slime and Hunger potions"}
   );
   public static final Item LAVA_EATER = new BaubleAntipotion(
      "lava_eater",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.FREEZING, PotionEffects.FIERYOIL},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Freezing and Fiery Oil"}
   );
   public static final Item PERSONAL_EXTINGUISHER = new BaubleAntipotion(
         "personal_extinguisher", CreativeTabs.COMBAT, null, BaubleType.TRINKET, true, new String[]{"Automatically removes fire from you"}
      )
      .setRender(1);
   public static final Item ETHER_WORM = new BaubleAntipotion.BaubleAntipotionFallless(
      "ether_worm",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.ENDER_POISON},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Ender Poison and fall damage"}
   );
   public static final Item ANGEL_WORM = new BaubleAntipotion.BaubleAntipotionFallless(
         "angel_worm",
         CreativeTabs.COMBAT,
         new Potion[]{PotionEffects.ENDER_POISON, PotionEffects.SLIME, MobEffects.HUNGER, PotionEffects.FREEZING, PotionEffects.FIERYOIL},
         BaubleType.TRINKET,
         false,
         new String[]{"Immunity to Slime, Hunger, Freezing, Fiery Oil,", "Ender Poison and fall damage"}
      )
      .setRender(1);
   public static final Item CROSS_CHAINLET = new BaubleAntipotion(
         "cross_chainlet", CreativeTabs.COMBAT, PotionEffects.DEMONIC_BURN, BaubleType.AMULET, "Immunity to Demonic Burn"
      )
      .setRender(1);
   public static final Item DETOXICATOR = new BaubleAntipotion(
         "detoxicator", CreativeTabs.COMBAT, MobEffects.POISON, BaubleType.RING, "Immunity to Poison"
      )
      .setRender(3);
   public static final Item HAZARD_GLOVE = new BaubleAntipotion(
      "hazard_glove",
      CreativeTabs.COMBAT,
      new Potion[]{MobEffects.MINING_FATIGUE, MobEffects.POISON},
      BaubleType.RING,
      false,
      new String[]{"Immunity to Poison and Mining Fatigue"}
   );
   public static final Item AMMONIA_FLASK = new BaubleAntipotion(
         "ammonia_flask", CreativeTabs.COMBAT, MobEffects.NAUSEA, BaubleType.TRINKET, "Immunity to Nausea"
      )
      .setRender(1);
   public static final Item CORROSIVE_FLASK = new BaubleAntipotion(
         "corrosive_flask",
         CreativeTabs.COMBAT,
         new Potion[]{MobEffects.NAUSEA, MobEffects.WEAKNESS},
         BaubleType.TRINKET,
         false,
         new String[]{"Immunity to Weakness and Nausea"}
      )
      .setRender(1);
   public static final Item CONDUCTIVE_BELT = new BaubleAntipotion(
      "conductive_belt", CreativeTabs.COMBAT, PotionEffects.SHOCK, BaubleType.BELT, "Immunity to Shock"
   );
   public static final Item LIGHTNING_SOCKS = new BaubleAntipotion(
      "lightning_socks",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.SHOCK, MobEffects.SLOWNESS},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Shock and Slowness"}
   );
   public static final Item FROST_INGUISHER = new BaubleAntipotion( // B: ну и что это за сухофрукт? Как я это понимать должен? И как мне это нормально записать?
         "personal_frostinguisher",
         CreativeTabs.COMBAT,
         PotionEffects.FROSTBURN,
         BaubleType.TRINKET,
         "Immunity to Burning Frost"
      ) //TODO переименовать по-человечески
      .setRender(1);
   public static final Item HOLY_EXTINGUISHER = new BaubleAntipotion( // B: обожаю Виверна за такие названия <3
         "holy_extinguisher", //TODO переименовать
         CreativeTabs.COMBAT,
         new Potion[]{PotionEffects.DEMONIC_BURN},
         BaubleType.TRINKET,
         false,
         new String[]{"Immunity to Demonic Burn"}
      )
      .setRender(1);
   public static final Item GHOSTFLAME_TRAP = new BaubleAntipotion(
           "ghostflame_trap",
           CreativeTabs.COMBAT,
           MobEffects.MINING_FATIGUE,
           BaubleType.TRINKET,
           "Immunity to Ghostflame"
      )
      .setRender(1);
   public static final Item FLAME_SUPPRESSOR = new BaubleAntipotion(
         "flame_suppressor",
         CreativeTabs.COMBAT,
         new Potion[]{PotionEffects.DEMONIC_BURN, PotionEffects.FROSTBURN},
         BaubleType.TRINKET,
         true,
         new String[]{"Immunity to Fire, Burning Frost and Demonic Burn"}
      )
      .setRender(1);
   public static final Item ANCIENT_ICE_SHARD = new BaublesPack.BaubleAttributed(
         "ancient_ice_shard", CreativeTabs.COMBAT, PropertiesRegistry.MANASPEED_MAX, 0.2, 0, BaubleType.AMULET, "+0.2 Mana Regeneration"
      )
      .setRender(1);
   public static final Item MANA_RUBBLE = new BaublesPack.BaubleAttributed(
      "mana_rubble", CreativeTabs.COMBAT, PropertiesRegistry.MANA_MAX, 5.0, 0, BaubleType.TRINKET, "+5 Maximum Mana"
   );
   public static final Item ICE_HEART = new BaublesPack.IceHeart();
   public static final Item LIVE_BLOOD_NECKLACE = new BaublesPack.LiveBloodNecklace();
   public static final Item BRASS_KNUCKLES = new BaublesPack.BaubleAttributed(
         "brass_knuckles", CreativeTabs.COMBAT, SharedMonsterAttributes.ATTACK_DAMAGE, 2.0, 0, BaubleType.RING, "+2 Melee Attack Damage"
      )
      .setRender(5);
   public static final Item HELLHOUND_COLLAR = new BaublesPack.HellhoundCollar();
   public static final Item GOLDEN_KNUCKLES = new BaublesPack.GoldenKnuckles();
   public static final Item PERSISTENCE_PENDENT = new BaublesPack.PersistencePendent();
   public static final Item CYBER_AMULET = new BaublesPack.CyberAmulet();
   public static final Item PAINFUL_ROOT = new BaublesPack.PainfulRoot();
   public static final Item BLEEDING_ROOT = new BaublesPack.BleedingRoot();
   public static final Item VENOMED_DAGGER = new BaublesPack.VenomedDagger();
   public static final Item SPIRIT_THORN = new BaublesPack.SpiritThorn();
   public static final Item SPRINGER_WAISTBAND = new BaublesPack.SpringerWaistband();
   public static final Item LIGHT_BAND = new BaublesPack.LightBand();
   public static final Item THORN_KEEPER = new BaublesPack.ThornKeeper();
   public static final Item MANA_KEEPER = new BaublesPack.ManaKeeper();
   public static final Item THISTLE_THORN = new ThistleThorn();
   public static final Item RESTLESS_SKULL = new RestlessSkull();
   public static final Item MAGIC_ROCKET = new MagicRocket();
   public static final Item STINGING_CELL = new StingingCell();
   public static final Item SEA_EFFLORESCE = new SeaEffloresce();
   public static final Item CORAL_RIFLE_CLIP = new ItemAmmoClip("coral_rifle_clip", CreativeTabs.COMBAT, 64, CoralRifle.maxammo);
   public static final Item CORAL_RIFLE = new CoralRifle();
   public static final Item PALM_LOG_WAND = new ToolWand("palm_log_wand", CreativeTabs.TOOLS, 1000, "arpg:palm_log", true, "arpg:palm_log")
      .setReplaceLogic("arpg:palm_log", "arpg:palm_log")
      .setPlaceMeta(2)
      .setToReplaceMeta(2);
   public static final Item TIMELESS_SWORD = new TimelessSword();
   public static final Item FIRE_WHIP = new FireWhip();
   public static final Item CREATIVE_TEAM_SELECTOR = new CreativeTeamSelector();
   public static final Item HADRON_BLASTER = new HadronBlaster();
   public static final Item SNAP_BALL = new SnapBall();
   public static final Item SNAP_BALL_AMMO = new ItemItem("snap_ball_ammo", CreativeTabs.COMBAT, 0, 64);
   public static final Item ROCKET_LAUNCHER = new RocketLauncher();
   public static final Item COMMON_ROCKET = new ItemRocket.CommonRocket();
   public static final Item GLASS_HEART = new BaublesPack.GlassHeart();
   public static final Item FROSTFIRE_ROCKET = new ItemRocket.FrostfireRocket();
   public static final Item CHEMICAL_ROCKET = new ItemRocket.ChemicalRocket();
   public static final Item NAPALM_ROCKET = new ItemRocket.NapalmRocket();
   public static final Item DEMOLISHING_ROCKET = new ItemRocket.DemolishingRocket();
   public static final Item MINING_ROCKET = new ItemRocket.MiningRocket();
   public static final Item VOID_ROCKET = new ItemRocket.VoidRocket();
   public static final Item WATERBLAST_ROCKET = new ItemRocket.WaterblastRocket();
   public static final Item ARCANE_ROCKET = new ItemRocket.ArcaneRocket();
   public static final Item SURPRISE_ROCKET = new ItemRocket.SurpriseRocket();
   public static final Item SHELL_ROCKET = new ItemRocket.ShellRocket();
   public static final Item BONE_HELM = new BoneHelm();
   public static final Item BONE_CHEST = new BoneHelm.BoneChestplate();
   public static final Item BONE_LEGS = new BoneHelm.BoneLeggins();
   public static final Item BONE_BOOTS = new BoneHelm.BoneBoots();
   public static final Item LICH_HELM = new LichHelm();
   public static final Item LICH_CHEST = new LichHelm.LichChestplate();
   public static final Item LICH_LEGS = new LichHelm.LichLeggins();
   public static final Item LICH_BOOTS = new LichHelm.LichBoots();
   public static final Item CORAL_HELM = Armors.coralSET.HELMET;
   public static final Item CORAL_CHEST = Armors.coralSET.CHESTPLATE;
   public static final Item CORAL_LEGS = Armors.coralSET.LEGGINS;
   public static final Item CORAL_BOOTS = Armors.coralSET.BOOTS;
   public static final Item SNOW_COAT_HELM = Armors.snowCoatSET.HELMET;
   public static final Item SNOW_COAT_CHEST = Armors.snowCoatSET.CHESTPLATE;
   public static final Item SNOW_COAT_LEGS = Armors.snowCoatSET.LEGGINS;
   public static final Item SNOW_COAT_BOOTS = Armors.snowCoatSET.BOOTS;
   public static final Item RUSTED_KEY = new ItemKey("rusted_key", 64, ChestLock.RUSTED_KEY, true);
   public static final Item GAS_MASK = new BaublesPack.GasMask();
   public static final Item ANTI_RAD_PILLS = new ItemEatable("anti_rad_pills", 0, 64, 0, 0.0F, false, 14, null, null, false, -250).setAlwaysEdible();
   public static final Item ANTI_RAD_INJECTOR = new AntiRadInjector();
   public static final Item ANTI_RAD_PACK = new BaublesPack.AntiRadPack();
   public static final Item BUNKER_KEYCARD = new ItemItem("bunker_keycard", CreativeTabs.TOOLS, 0, 1);
   public static final Item VIRULENT_ROD = new VirulentRod();
   public static final Item WRENCH = new Wrench();
   public static final Item ITEM_TURRET = new ItemTurret();
   public static final Item CHAIN_MACE = new ChainMace("chain_mace");
   public static final Item DIAMOND_CHAIN_MACE = new ChainMace.DiamondChainMace();
   public static final Item MOLTEN_CHAIN_MACE = new ChainMace.MoltenChainMace();
   public static final Item ICEBREAKER = new ChainMace.Icebreaker();
   public static final Item FRAG_GRENADE = new ItemGrenade("frag_grenade", 32, (byte)1, 30, 18.0F, 2.0F, new ResourceLocation("arpg:textures/frag_grenade.png"));
   public static final Item BOMB = new ItemGrenade.Bomb("bomb", 24, (byte)2, 40, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_bomb.png"));
   public static final Item CRYO_GRENADE = new ItemGrenade.Cryogrenade(
      "cryo_grenade", 16, (byte)3, 30, 5.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_cryo.png")
   );
   public static final Item HELL_GRENADE = new ItemGrenade.HellGrenade(
      "hell_grenade", 20, (byte)4, 30, 11.0F, 0.8F, new ResourceLocation("arpg:textures/grenade_hell.png")
   );
   public static final Item MOLOTOV_COCKTAIL = new ItemGrenade.MolotovCocktail(
      "molotov_cocktail", 32, (byte)5, 100, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_molotov.png")
   );
   public static final Item OIL_BOTTLE = new ItemGrenade.OilBottle(
           "oil_bottle", 48, (byte)6, 100, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_oil.png")
   );
   public static final Item SNOW_GRENADE = new ItemGrenade.SnowGrenade(
      "snow_grenade", 24, (byte)7, 30, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_snow.png")
   );
   public static final Item GAS_GRENADE = new ItemGrenade.GasGrenade(
      "gas_grenade", 20, (byte)8, 80, 30.0F, 1.0F, new ResourceLocation("arpg:textures/grenade_gas.png")
   );
   public static final Item SEA_GRENADE = new ItemGrenade.SeaGrenade(
      "sea_grenade", 18, (byte)9, 30, 15.0F, 1.5F, new ResourceLocation("arpg:textures/grenade_sea.png")
   );
   public static final Item WATCHING_GRENADE = new ItemGrenade.WatchingGrenade(
      "watching_grenade", 14, (byte)10, 35, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_watching.png")
   );
   public static final Item GRAVITY_GRENADE = new ItemGrenade.GravityGrenade(
      "gravity_grenade", 16, (byte)11, 40, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_gravity.png")
   );
   public static final Item LIVE_HEART = new ItemItem("live_heart", CreativeTabs.FOOD, 0, 64);
   public static final Item ENDER_CROWN = new BaublesPack.EnderCrown();
   public static final Item ARTHROSLELECHA_BRASS_LOG_WAND = new ToolWand(
         "arthroslelecha_brass_log_wand", CreativeTabs.TOOLS, 1000, "arpg:arthrostelecha_log_brass", true, "arpg:arthrostelecha_log_brass"
      )
      .setReplaceLogic("arpg:arthrostelecha_log_brass", "arpg:arthrostelecha_log_brass")
      .setPlaceMeta(2)
      .setToReplaceMeta(2);
   public static final Item ARTHROSLELECHA_PINK_LOG_WAND = new ToolWand(
         "arthroslelecha_pink_log_wand", CreativeTabs.TOOLS, 1000, "arpg:arthrostelecha_log_pink", true, "arpg:arthrostelecha_log_pink"
      )
      .setReplaceLogic("arpg:arthrostelecha_log_pink", "arpg:arthrostelecha_log_pink")
      .setPlaceMeta(2)
      .setToReplaceMeta(2);
   public static final Item PROCESSOR_PATTERN = new ItemItem("processor_pattern", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CMO = new ItemItem("cmo", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MUTAGEN = new ItemItem("mutagen", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EMBRYO = new ItemItem("embryo", CreativeTabs.MATERIALS, 0, 64);
   public static final Item VILE_SUBSTANCE = new ItemItem("vile_substance", CreativeTabs.MATERIALS, 0, 64);
   public static final Item NORTHERN_INGOT = new ItemItem("northern_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item NORTHERN_SPHERE = new ItemItem("northern_sphere", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ADAMANTIUM_ROUNDS = new AdamantiumRounds();
   public static final Item ADAMANTIUM_REVOLVER = new AdamantiumRevolver();
   public static final Item ADAMANTIUM_BATTLE_AXE = new AdamantiumBattleAxe();
   public static final Item ADAMANTIUM_HELM = Armors.adamantiumSET.HELMET;
   public static final Item ADAMANTIUM_CHEST = Armors.adamantiumSET.CHESTPLATE;
   public static final Item ADAMANTIUM_LEGS = Armors.adamantiumSET.LEGGINS;
   public static final Item ADAMANTIUM_BOOTS = Armors.adamantiumSET.BOOTS;
   public static final Item ADAMANTIUM_LONGSWORD = new AdamantiumLongsword();
   public static final Item ADAMANTIUM_KNIFE = new AdamantiumKnife();
   public static final Item BULLET_ADAMANTIUM = new ItemBullet("bullet_adamantium", "adamantium", 64, 3.0F, 0.3F, 0.662F, 0.192F, 0.274F);
   public static final Item HAZARD_HELM = Armors.hazardSET.HELMET;
   public static final Item HAZARD_CHEST = Armors.hazardSET.CHESTPLATE;
   public static final Item HAZARD_LEGS = Armors.hazardSET.LEGGINS;
   public static final Item HAZARD_BOOTS = Armors.hazardSET.BOOTS;
   public static final Item STORM_SPANNER = new StormSpanner();
   public static final Item ADAMANTIUM_INGOT = new ItemItem("adamantium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ADAMANTIUM_DUST = new ItemItem("adamantium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item HEROBRINE_CURSE = new BaublesPack.HerobrineCurse();
   public static final Item VOID_CRYSTAL = new ItemNoGravivy("void_crystal", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TOXINIUM_INGOT = new ItemItem("toxinium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TOXINIUM_DUST = new ItemItem("toxinium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TOXINIUM_NUGGET = new ItemItem("toxinium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMSTEEL_INGOT = new ItemItem("stormsteel_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMSTEEL_DUST = new ItemItem("stormsteel_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMSTEEL_NUGGET = new ItemItem("stormsteel_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMBRASS_INGOT = new ItemItem("stormbrass_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMBRASS_DUST = new ItemItem("stormbrass_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMBRASS_NUGGET = new ItemItem("stormbrass_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ARSENIC_INGOT = new ItemItem("arsenic_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ARSENIC_DUST = new ItemItem("arsenic_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ARSENIC_NUGGET = new ItemItem("arsenic_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WOLFRAM_INGOT = new ItemItem("wolfram_ingot", CreativeTabs.MATERIALS, 0, 64); //TODO wolfram -> tungsten
   public static final Item WOLFRAM_DUST = new ItemItem("wolfram_dust", CreativeTabs.MATERIALS, 0, 64); //TODO wolfram -> tungsten
   public static final Item WOLFRAM_NUGGET = new ItemItem("wolfram_nugget", CreativeTabs.MATERIALS, 0, 64); //TODO wolfram -> tungsten
   public static final Item SKY_CRYSTAL = new ItemItem("sky_crystal", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SKY_CRYSTAL_PIECE = new ItemItem("sky_crystal_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WIND_NATURE = new ItemNoGravivy("wind_nature", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SKY_SPHERE = new ItemItem("sky_sphere", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ENDER_PROTECTOR = new EnderProtector();
   public static final Item DRAGON_TAIL = new DragonTail();
   public static final Item DRAGON_SHELL = new DragonShell();
   public static final Item WINTER_BREATH = new WinterBreath();
   public static final Item TOXINIUM_SHIELD = new ToxiniumShield();
   public static final Item CARAPACE = new Carapace();
   public static final Item ROTTEN_SHIELD = new RottenShield();
   public static final Item HELLMARK = new Hellmark();
   public static final Item TOXIBERRY_JUICE_DRIP = new ItemItem("toxiberry_juice_drip", CreativeTabs.MATERIALS, 0, 64);
   public static final Item THUNDER_STONE = new ItemItem("thunder_stone", CreativeTabs.MATERIALS, 0, 64);
   public static final Item THUNDER_CAPACITOR = new ItemItem("thunder_capacitor", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ADVANCED_POLYMER = new ItemItem("advanced_polymer", CreativeTabs.MATERIALS, 0, 64).setFuel(20);
   public static final Item GLOWING_CRYSTAL_DUST = new ItemItem("glowing_crystal_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MAGIC_CRYSTAL_DUST = new ItemItem("magic_crystal_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item COPPER_SULFATE = new ItemItem("copper_sulfate", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PLANT_FIBER = new ItemItem("plant_fiber", CreativeTabs.MATERIALS, 0, 64);
   public static final Item DRIED_PLANT_FIBER = new ItemItem("dried_plant_fiber", CreativeTabs.MATERIALS, 0, 64).setFuel(10);
   public static final Item CRYSTALLIZED_POISON = new ItemItem("crystallized_poison", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SALT = new ItemItem("salt", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SALT_GRAINS = new ItemItem("salt_grains", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CONIFER_ROSIN = new ItemItem("conifer_rosin", CreativeTabs.MATERIALS, 0, 64);
   public static final Item FIERY_OIL = new ItemItem("fiery_oil", CreativeTabs.MATERIALS, 0, 64).setFuel(70);
   public static final Item ICE_DUST = new ItemItem("ice_dust", CreativeTabs.MATERIALS, 0, 64).setEnchantGlow();
   public static final Item CHEMICAL_GLASS = new ItemItem("chemical_glass", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BRASS_INGOT = new ItemItem("brass_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BRASS_DUST = new ItemItem("brass_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BRASS_NUGGET = new ItemItem("brass_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ZINC_INGOT = new ItemItem("zinc_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ZINC_DUST = new ItemItem("zinc_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ZINC_NUGGET = new ItemItem("zinc_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TITANIUM_INGOT = new ItemItem("titanium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TITANIUM_DUST = new ItemItem("titanium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TITANIUM_NUGGET = new ItemItem("titanium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BAUXITE = new ItemItem("bauxite", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PIZZA_CHICKEN = new ItemEatable("pizza_chicken", 0, 64, 8, 1.0F, false, 38, null, null, false, 0);
   public static final Item PIZZA_DIAVOLA = new ItemEatable( //ай к чёрту, оставим как есть, будет пасхалкой (читается как пицца дьявола)
      "pizza_diavola", 0, 64, 9, 1.0F, false, 38, new PotionEffect[]{new PotionEffect(PotionEffects.BERSERK, 666)}, new float[]{1.0F}, false, 0
   );
   public static final Item PIZZA_CHEESE = new ItemEatable("pizza_cheeze",/*TODO cheeze->cheese*/ 0, 64, 7, 1.0F, false, 36, null, null, false, 0);
   public static final Item PIZZA_TOXIC = new ItemEatable(
      "pizza_toxedge" /*TODO toxedge -> toxic*/, 0, 64, 10, 1.0F, false, 38, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 50)}, new float[]{0.5F}, false, 60
   );
   public static final Item PIZZA_GLOWING = new ItemEatable(
      "pizza_glowing",
      0,
      64,
      9,
      1.0F,
      false,
      32,
      new PotionEffect[]{
         new PotionEffect(MobEffects.GLOWING, 150), new PotionEffect(PotionEffects.MAGIC_POWER, 250), new PotionEffect(PotionEffects.RAINBOW, 140)
      },
      new float[]{0.9F, 1.0F, 0.5F},
      false,
      60
   );
   public static final Item PIZZA_SEAFOOD = new ItemEatable("pizza_seafood", 0, 64, 12, 1.0F, false, 32, null, null, false, 0);
   public static final Item SMOKED_SAUSAGE = new ItemEatable("smoked_sausage", 0, 64, 7, 0.8F, true, 26, null, null, false, 0);
   public static final Item RAW_RIBS = new ItemEatable("raw_ribs", 0, 64, 3, 0.3F, true, 32, null, null, false, 0);
   public static final Item HOT_SPICY_RIBS = new ItemEatable(
      "hot_spicy_ribs", 0, 64, 8, 0.8F, true, 32, new PotionEffect[]{new PotionEffect(PotionEffects.BERSERK, 400)}, new float[]{1.0F}, false, 0
   );
   public static final Item MAGMA_BLOOM_SEED = new MagmaBloomSeed();
   public static final Item SULFUR_DUST = new ItemItem("sulfur_dust", CreativeTabs.MATERIALS, 0, 64); //TODO sulfur_dust -> just sulfur
   public static final Item TOXIBERRY_STICK = new ItemItem("toxiberry_stick", CreativeTabs.MATERIALS, 0, 64).setFuel(10);
   public static final Item SCRAP_METAL = new ItemItem("scrap_metal", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SLIME_CELL = new ItemItem("slime_cell", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SLIME_PLASTIC = new ItemItem("slime_plastic", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WHITE_SLIMEBALL = new ItemItem("white_slimeball", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PLASTIC = new ItemItem("plastic", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ICICLE_MINIGUN_CLIP = new ItemAmmoClip("icicle_minigun_clip", CreativeTabs.COMBAT, 64, IcicleMinigun.maxammo, true);
   public static final Item CIRCUIT = new ItemItem("circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ADVANCED_CIRCUIT = new ItemItem("advanced_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item DIMENSION_CIRCUIT = new ItemItem("dimension_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TOXIC_CIRCUIT = new ItemItem("toxic_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item COPPER_WIRE = new ItemItem("copper_wire", CreativeTabs.MATERIALS, 0, 64);
   public static final Item GOLD_WIRE = new ItemItem("gold_wire", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SILVER_WIRE = new ItemItem("silver_wire", CreativeTabs.MATERIALS, 0, 64);
   public static final Item RUBBER = new ItemItem("rubber", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PROCESSOR = new ItemItem("processor", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EYE_OF_SEER = new ItemItem("eye_of_seer", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ELECTRIC_MOTOR = new ItemItem("electric_motor", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LINEAR_MOTOR = new ItemItem("linear_motor", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LEAD_BEARING = new ItemItem("lead_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ARSENIC_BEARING = new ItemItem("arsenic_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LEAD_ACID_BATTERY = new ItemAccumulator("lead_acid_battery", ItemAccumulator.LEAD_ACID_CAPACITY, ItemAccumulator.LEAD_ACID_THROUGHPUT);
   public static final Item LI_ION_BATTERY = new ItemAccumulator("li_ion_battery", ItemAccumulator.LI_ION_CAPACITY, ItemAccumulator.LI_ION_THROUGHPUT);
   public static final Item GAS_FILTER = new ItemItem("gas_filter", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WOLFRAM_WIRE = new ItemItem("wolfram_wire", CreativeTabs.MATERIALS, 0, 64);
   public static final Item NAIL = new ItemItem("nail", CreativeTabs.MATERIALS, 0, 64);
   public static final Item NAIL_GUN_CLIP = new ItemAmmoClip("nail_gun_clip", CreativeTabs.COMBAT, 64, NailGun.maxammo, true);
   public static final Item ADAMANTIUM_NUGGET = new ItemItem("adamantium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BLOWHOLE = new Blowhole();
   public static final Item BLOWHOLE_PELLETS = new ItemItem("blowhole_pellets", CreativeTabs.COMBAT, 0, 64);
   public static final Item CRYSTAL_CUTTER = new CrystalCutter();
   public static final Item CRYSTAL_CUTTER_AMMO = new ItemItem("crystal_cutter_ammo", CreativeTabs.COMBAT, 0, 64);
   public static final Item PLASMA_MINIGUN = new PlasmaMinigun();
   public static final Item PLASMA_MINIGUN_CLIP = new ItemAmmoClip("plasma_minigun_clip", CreativeTabs.COMBAT, 64, PlasmaMinigun.maxammo);
   public static final Item CERATARGET = new Ceratarget();
   public static final Item HAIL_TEAR = new HailTear();
   public static final Item WINTER_SACRIFICE = new WinterSacrifice();
   public static final Item WINTER_SCALE = new ItemItem("winter_scale", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BURNING_FROST_IGNITER = new BurningFrostIgniter();
   public static final Item GOTHIC_SWORD = new GothicSword();
   public static final Item CRYSTAL_HELM = Armors.crystalSET.HELMET;
   public static final Item CRYSTAL_CHEST = Armors.crystalSET.CHESTPLATE;
   public static final Item CRYSTAL_LEGS = Armors.crystalSET.LEGGINS;
   public static final Item CRYSTAL_BOOTS = Armors.crystalSET.BOOTS;
   public static final Item THUNDERER_HELM = Armors.thundererSET.HELMET;
   public static final Item THUNDERER_CHEST = Armors.thundererSET.CHESTPLATE;
   public static final Item THUNDERER_LEGS = Armors.thundererSET.LEGGINS;
   public static final Item THUNDERER_BOOTS = Armors.thundererSET.BOOTS;
   public static final Item NORTHERN_HELM = Armors.northernSET.HELMET;
   public static final Item NORTHERN_CHEST = Armors.northernSET.CHESTPLATE;
   public static final Item NORTHERN_LEGS = Armors.northernSET.LEGGINS;
   public static final Item NORTHERN_BOOTS = Armors.northernSET.BOOTS;
   public static final Item GRENADE_LAUNCHER = new GrenadeLauncher();
   public static final Item HOLY_SHOTGUN = new HolyShotgun();
   public static final Item BUCKSHOT = new Buckshot();
   public static final Item ECHINUS = new ChainMace.Echinus();
   public static final Item TIDE_ACTIVATOR_1 = new ItemItem("tide_activator_1", CreativeTabs.TOOLS, 0, 1);
   public static final Item TIDE_ACTIVATOR_2 = new ItemItem("tide_activator_2", CreativeTabs.TOOLS, 0, 1);
   public static final Item TIDE_ACTIVATOR_3 = new ItemItem("tide_activator_3", CreativeTabs.TOOLS, 0, 1);
   public static final Item TIDE_ACTIVATOR_4 = new ItemItem("tide_activator_4", CreativeTabs.TOOLS, 0, 1);
   public static final Item TIDE_ACTIVATOR_5 = new ItemItem("tide_activator_5", CreativeTabs.TOOLS, 0, 1);
   public static final Item ARROW_FROZEN = new Arrows.ArrowFrozen();
   public static final Item ARROW_FIREJET = new Arrows.ArrowFirejet();
   public static final Item ARROW_VICIOUS = new Arrows.ArrowVicious();
   public static final Item ARROW_MODERN = new Arrows.ArrowModern();
   public static final Item ARROW_BENGAL = new Arrows.ArrowBengal();
   public static final Item ARROW_FISH = new Arrows.ArrowFish();
   public static final Item ARROW_VOID = new Arrows.ArrowVoid();
   public static final Item ARROW_SHELL = new Arrows.ArrowShell();
   public static final Item ARROW_BOUNCING = new Arrows.ArrowBouncing();
   public static final Item ARROW_MITHRIL = new Arrows.ArrowMithril();
   public static final Item ARROW_TWIN = new Arrows.ArrowTwin();
   public static final Item ARROW_WIND = new Arrows.ArrowWind();
   public static final Item MITHRIL_BOW = new MithrilBow();
   public static final Item COMPOUND_BOW = new CompoundBow();
   public static final Item AZURE_ORE_STAFF = new AzureOreStaff();
   public static final Item SILVER_INGOT = new ItemItem("silver_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SILVER_DUST = new ItemItem("silver_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SILVER_NUGGET = new ItemItem("silver_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item COPPER_TRANSFORMER = new ItemItem("copper_transformer", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STAMP_MOLD = new ItemItem("stamp_mold", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STEEL_STAMP = new ItemItem("steel_stamp", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BEARING_ALLOY_DUST = new ItemItem("bearing_alloy_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ALUMINIUM_INGOT = new ItemItem("aluminium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ALUMINIUM_DUST = new ItemItem("aluminium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ALUMINIUM_NUGGET = new ItemItem("aluminium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ELECTROLYZER_MODULE = new ItemItem("electrolyzer_module", CreativeTabs.MATERIALS, 256, 64);
   public static final Item BIOFILTERING_MODULE = new ItemItem("biofiltering_module", CreativeTabs.MATERIALS, 512, 64);
   public static final Item BERYLLIUM_GRAINS = new ItemItem("beryllium_grains", CreativeTabs.MATERIALS, 0, 64);
   public static final Item INSTANCER = new Instancer("instancer", 1.5F, 1.3F, 2000);
   public static final Item SLIMY_EGGS = new ItemMobEgg("slimy_eggs", 64, new ResourceLocation("arpg:white_slime"));
   public static final Item SPAWNER_PIECE = new ItemItem("spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item FROZEN_SPAWNER_PIECE = new ItemItem("frozen_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item RUSTED_SPAWNER_PIECE = new ItemItem("rusted_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ENDER_INSTANCER = new EnderInstancer();
   public static final Item WINTER_INSTANCER = new WinterInstancer();
   public static final Item MILITARY_INSTANCER = new MilitaryInstancer();
   public static final Item AQUATIC_INSTANCER = new AquaticInstancer();
   public static final Item VIOLENCE = new Violence();
   public static final Item STAFF_OF_WITHERDRY = new StaffOfWitherdry();
   public static final Item HELLHOUND_FUR = new ItemItem("hellhound_fur", CreativeTabs.MATERIALS, 0, 64);
   public static final Item NIVEOLITE = new ItemItem("niveolite", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WHISPERS_BLADE = new WhispersBlade();
   public static final Item PUMP_SHOTGUN = new PumpShotgun();
   public static final Item GOTHIC_BOW = new GothicBow();
   public static final Item COOLED_RIFLE_CLIP = new ItemAmmoClip("cooled_rifle_clip", CreativeTabs.COMBAT, 64, 28);
   public static final Item COOLED_RIFLE = new CooledRifle();
   public static final Item AQUATIC_BOW = new AquaticBow();
   public static final Item MAGIC_EXPLORING_KIT = new ItemItem("magic_exploring_kit", CreativeTabs.TOOLS, 0, 1);
   public static final Item MAGIC_RESEARCH_KIT = new ItemItem("magic_research_kit", CreativeTabs.TOOLS, 0, 1);
   public static final Item MAGIC_WRITING_KIT = new ItemItem("magic_writing_kit", CreativeTabs.TOOLS, 0, 1);
   public static final Item BEAKER = new Beaker();
   public static final Item SPELL_PLIERS = new SpellPliers();
   public static final Item VIAL_FIRE = new ItemVial(1);
   public static final Item VIAL_EARTH = new ItemVial(2);
   public static final Item VIAL_WATER = new ItemVial(3);
   public static final Item VIAL_AIR = new ItemVial(4);
   public static final Item VIAL_POISON = new ItemVial(5);
   public static final Item VIAL_COLD = new ItemVial(6);
   public static final Item VIAL_ELECTRIC = new ItemVial(7);
   public static final Item VIAL_VOID = new ItemVial(8);
   public static final Item VIAL_PLEASURE = new ItemVial(9);
   public static final Item VIAL_PAIN = new ItemVial(10);
   public static final Item VIAL_DEATH = new ItemVial(11);
   public static final Item VIAL_LIVE = new ItemVial(12);
   public static final Item[] VIALS = new Item[]{
           VIAL_FIRE, VIAL_EARTH, VIAL_WATER, VIAL_AIR, VIAL_POISON, VIAL_COLD, VIAL_ELECTRIC, VIAL_VOID, VIAL_PLEASURE, VIAL_PAIN, VIAL_DEATH, VIAL_LIVE
   };
   public static final Item VIAL = new ItemVial(0);
   public static final Item VIAL_EMPTY = new ItemVial(-1);
   public static final Item SPELL_ROLL = new ItemSpellRoll();
   public static final Item SHADOW_WINGS = new ShadowWings();
   public static final Item DASH_BELT_BLACK = new DashBelt.DashBeltBlack();
   public static final Item DASH_HELLHOUND_BELT = new DashBelt.DashHellhoundBelt();
   public static final Item DASH_IMPULSE_CORSLET = new DashBelt.DashImpulseCorslet();
   public static final Item ELEMENTS_BOOK = new ElementsBook();
   public static final Item XMASS_LAUNCHER = new XmassLauncher();
   public static final Item XMASS_BUNDLE = new ItemItem("xmass_bundle", CreativeTabs.COMBAT, 0, 64);
   public static final Item ADAMANTIUM_MINIGUN = new AdamantiumMinigun();
   public static final Item ADAMANTIUM_MINIGUN_CLIP = new ItemAmmoClip("adamantium_minigun_clip", CreativeTabs.COMBAT, 64, AdamantiumMinigun.maxammo);
   public static final Item BUZDYGAN = new Buzdygan();
   public static final Item WHIP = new Whip("whip", 450);
   public static final Item MAULER = new Mauler();
   public static final Item SNAKEWHIP = new Snakewhip();
   public static final Item ENIGMATE_TWINS = new BaublesPack.EnigmateTwins();
   public static final Item ALCHEMICAL_WAX = new ItemItem("alchemical_wax", CreativeTabs.MATERIALS, 0, 64).setFuel(40);
   public static final Item MANA_BERRY = new ItemEatable(
         "mana_berry", 0, 64, 0, 0.0F, false, 32, new PotionEffect[]{new PotionEffect(MobEffects.NAUSEA, 150)}, new float[]{1.0F}, false, 0
      )
      .setMana(3.0F);
   public static final Item HEALTH_BERRY = new ItemEatable(
      "health_berry",
      0,
      64,
      1,
      0.0F,
      false,
      32,
      new PotionEffect[]{
         new PotionEffect(MobEffects.INSTANT_HEALTH, 1),
         new PotionEffect(PotionEffects.HEALTH_DEGRADATION, 640),
         new PotionEffect(PotionEffects.HEALTH_DEGRADATION, 640, 1)
      },
      new float[]{1.0F, 0.9F, 0.2F},
      false,
      0
   );
   public static final Item QUANTUM_SLIMEBALL = new ItemItem("quantum_slimeball", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CANISTER = new Canister();
   public static final Item REDPEPPER = new ItemEatable("red_pepper", 0, 64, 4, 0.2F, false, 28, null, null, false, 0).setBurn(3);
   public static final Item MOONSHROOM_MEAT = new ItemEatable("moonshroom_meat", 0, 64, 2, 0.1F, false, 38, null, null, false, 0);
   public static final Item YEAST = new ItemItem("yeast", CreativeTabs.FOOD, 0, 64);
   public static final Item WHEY_STARTER = new ItemItem("whey_starter", CreativeTabs.FOOD, 0, 64);
   public static final Item MUSHROOMS_IN_SAUCE = new ItemEatable("mushrooms_in_sauce", 0, 64, 12, 0.9F, false, 34, null, null, false, 0)
      .setStackToReturn(new ItemStack(Items.BOWL));
   public static final Item MOZZARELLA = new ItemEatable("mozzarella", 0, 64, 2, 0.2F, false, 32, null, null, false, 0);
   public static final Item DOUGH = new ItemItem("dough", CreativeTabs.FOOD, 0, 64);
   public static final Item SWEET_DOUGH = new ItemItem("sweet_dough", CreativeTabs.FOOD, 0, 64);
   public static final Item TOXEDGE_DOUGH = new ItemItem("toxedge_dough", CreativeTabs.FOOD, 0, 64);
   public static final Item MAGIC_JAM = new ItemEatable(
         "magic_jam", 0, 64, 6, 0.7F, false, 28, new PotionEffect[]{new PotionEffect(PotionEffects.MANA_REGENERATION, 400)}, new float[]{1.0F}, false, 4
      )
      .setMana(6.0F)
      .setAlwaysEdible();
   public static final Item CHERRY_TOMATO = new ItemEatable("cherry_tomato", 0, 64, 2, 0.4F, false, 32, null, null, false, 0);
   public static final Item FERMENTER_MODULE = new ItemItem("fermenter_module", CreativeTabs.MATERIALS, 256, 64);
   public static final Item CENTRIFUGE_MODULE = new ItemItem("centrifuge_module", CreativeTabs.MATERIALS, 256, 64);
   public static final Item BORSCH = new ItemEatable("borsch", 0, 64, 9, 0.75F, false, 36, null, null, true, 0).setStackToReturn(new ItemStack(Items.BOWL));
   public static final Item STUFFED_FIERY_BEAN = new ItemEatable("stuffed_fiery_bean", 0, 64, 9, 0.6F, true, 32, null, null, false, 0);
   public static final Item MEAT_BROTH = new ItemEatable("meat_broth", 0, 64, 3, 0.2F, true, 12, null, null, true, 0)
      .setStackToReturn(new ItemStack(Items.BOWL));
   public static final Item FLOUR = new ItemItem("flour", CreativeTabs.MATERIALS, 0, 64);
   public static final Item VIOLET_PUDDING = new ItemEatable(
         "violet_pudding", 0, 64, 6, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(PotionEffects.ENDER_POISON, 25)}, new float[]{1.0F}, false, 0
      )
      .setMana(5.0F)
      .setAlwaysEdible();
   public static final Item GREEN_PUDDING = new ItemEatable(
         "green_pudding", 0, 64, 6, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 60)}, new float[]{1.0F}, false, 15
      )
      .setAlwaysEdible();
   public static final Item BROWN_PUDDING = new ItemEatable(
         "brown_pudding", 0, 64, 5, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 100, 1)}, new float[]{1.0F}, false, 0
      )
      .setAlwaysEdible();
   public static final Item ORANGE_PUDDING = new ItemEatable(
         "orange_pudding", 0, 64, 5, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(MobEffects.FIRE_RESISTANCE, 200)}, new float[]{1.0F}, false, 0
      )
      .setAlwaysEdible();
   public static final Item COCOA_BUTTER = new ItemItem("cocoa_butter", CreativeTabs.MATERIALS, 0, 64);
   public static final Item COCOA_POWDER = new ItemItem("cocoa_powder", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BUTTER = new ItemItem("butter", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CHOCOLATE = new ItemEatable("chocolate", 0, 64, 4, 0.2F, false, 26, null, null, false, 0);
   public static final Item CARAMEL = new ItemEatable("caramel", 0, 64, 2, 0.0F, false, 24, null, null, false, 0);
   public static final Item BISCUIT = new ItemEatable("biscuit", 0, 64, 2, 0.3F, false, 14, null, null, false, 0);
   public static final Item PEARL = new ItemItem("pearl", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BLACK_PEARL = new ItemItem("black_pearl", CreativeTabs.MATERIALS, 0, 64);
   public static final Item GLOWING_PEARL = new ItemItem("glowing_pearl", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AQUATIC_PEARL = new ItemItem("aquatic_pearl", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AQUATIC_INGOT = new ItemItem("aquatic_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AQUATIC_DUST = new ItemItem("aquatic_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AQUATIC_NUGGET = new ItemItem("aquatic_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CORAL = new ItemItem("coral", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ARCHELON_SHELL = new ItemItem("archelon_shell", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PLACODERM_SCALES = new ItemItem("placoderm_scales", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MESOGLEA = new ItemItem("mesoglea", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MITHRIL_INGOT = new ItemItem("mithril_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MITHRIL = new ItemItem("mithril_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MITHRIL_NUGGET = new ItemItem("mithril_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item IMPULSE_THRUSTER = new ItemItem("impulse_thruster", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORM_CIRCUIT = new ItemItem("storm_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STORMBRASS_PLASMATRON = new ItemItem("stormbrass_plasmatron", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ELECTROMAGNETIC_BEARING = new ItemItem("electromagnetic_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BLUE_ARTHROSTELECHA_ROD = new ItemItem("blue_arthrostelecha_rod", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PINK_ARTHROSTELECHA_ROD = new ItemItem("pink_arthrostelecha_rod", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TOPAZITRON_CRYSTAL = new ItemItem("topazitron_crystal", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BATTERY_TOPAZITRON_CRYSTAL = new ItemAccumulator(
      "battery_topazitron_crystal", ItemAccumulator.TOPAZITRON_CAPACITY, ItemAccumulator.TOPAZITRON_THROUGHPUT
   );
   public static final Item GOLD_TRANSFORMER = new ItemItem("gold_transformer", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SILVER_TRANSFORMER = new ItemItem("silver_transformer", CreativeTabs.MATERIALS, 0, 64);
   public static final Item RESISTANT_CIRCUIT = new ItemItem("resistant_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item POLYMERIZATION_MODULE = new ItemItem("polymerization_module", CreativeTabs.MATERIALS, 1000, 64);
   public static final Item DISTILLATION_MODULE = new ItemItem("distillation_module", CreativeTabs.MATERIALS, 1000, 64);
   public static final Item PYROLYSIS_MODULE = new ItemItem("pyrolysis_module", CreativeTabs.MATERIALS, 500, 64);
   public static final Item PARAFFIN = new ItemItem("paraffin", CreativeTabs.MATERIALS, 0, 64).setFuel(40);
   public static final Item TAR = new ItemItem("tar", CreativeTabs.MATERIALS, 0, 64).setFuel(100);
   public static final Item NYLON = new ItemItem("nylon", CreativeTabs.MATERIALS, 0, 64).setFuel(30);
   public static final Item RADIOACTIVE_DUST = new ItemItem("radioactive_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TOXINIUM_ORE_DUST = new ItemItem("toxinium_ore_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item URANIUM_INGOT = new ItemItem("uranium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item URANIUM_DUST = new ItemItem("uranium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item URANIUM_NUGGET = new ItemItem("uranium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STONE_DUST = new ItemItem("stone_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LIMESTONE_DUST = new ItemItem("limestone_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BASALT_DUST = new ItemItem("basalt_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ADAMANTIUM_ORE_DUST = new ItemItem("adamantium_ore_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MITHRIL_ORE_DUST = new ItemItem("mithril_ore_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AQUATIC_SPAWNER_PIECE = new ItemItem("aquatic_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TITANIUM_SLAG = new ItemItem("titanium_slag", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BLACK_STRAP = new ItemItem("black_strap", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PURPUR_ALLOY = new ItemItem("purpur_alloy", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EMPTY_SYRINGE = new ItemItem("empty_syringe", CreativeTabs.MATERIALS, 0, 64);
   public static final Item QUARTZ_DUST = new ItemItem("quartz_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PHASEOLITE = new ItemItem("phaseolite", CreativeTabs.MATERIALS, 0, 64);
   public static final Item INK = new InkBottle("ink_bottle", 256);
   public static final Item MISSING_DUST = new ItemItem("missing_dust", null, 0, 64); // Удалить
   public static final Item MISSING_INGOT = new ItemItem("missing_ingot", null, 0, 64); // Удалить
   public static final Item MISSING_NUGGET = new ItemItem("missing_nugget", null, 0, 64); // Удалить
   public static final Item MISSING_MATERIAL = new ItemItem("missing_material", null, 0, 64); // Удалить
   public static final Item SLIME_LOCATOR = new SlimeLocator();
   public static final Item ASH = new ItemItem("ash", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SALTPETER = new ItemItem("saltpeter", CreativeTabs.MATERIALS, 0, 64);
   public static final Item RUBBLESTONE = new ItemItem("stone_rubble", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SPELL_ROD = new SpellRod();
   public static final Item HEALTH_FRUIT = new HealthFruit();
   public static final Item MANA_EXPANSION_POTION = new ManaExpansionPotion();
   public static final Item ICE_CIRCLE = new ItemItem("ice_circle", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ICE_CIRCLE_FILLED = new ItemItem("ice_circle_filled", CreativeTabs.MATERIALS, 0, 64);
   public static final Item CRYO_DESTROYER = new CryoDestroyer();
   public static final Item GOTHIC_GEAR = new ItemItem("gothic_gear", CreativeTabs.MATERIALS, 0, 64);
   public static final Item GOTHIC_GEM = new ItemItem("gothic_gem", CreativeTabs.MATERIALS, 0, 64);
   public static final Item HYDRAULIC_SHOTGUN = new HydraulicShotgun();
   public static final Item HYDRAULIC_SHOTGUN_CLIP = new ItemBuckshotClip("hydraulic_shotgun_clip", CreativeTabs.COMBAT, 64, 10);
   public static final Item ICE_COMPASS = new IceCompass();
   public static final Item CHARM_OF_UNDYING = new BaublesPack.CharmOfUndying();
   public static final Item LOW_FRICTION_BEARING = new ItemItem("low_friction_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static final Item VACUUM_GUN_PELLETS = new ItemItem("vacuum_gun_pellets", CreativeTabs.COMBAT, 0, 64);
   public static final Item LITHIUM_INGOT = new ItemItem("lithium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LITHIUM_DUST = new ItemItem("lithium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LITHIUM_NUGGET = new ItemItem("lithium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static final Item LEPIDOLITE = new ItemItem("lepidolite", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SILICIUM = new ItemItem("silicium", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SILICIUM_WAFER = new ItemItem("silicium_wafer", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PHOTORESISTED_PLATE = new ItemItem("photoresisted_plate", CreativeTabs.MATERIALS, 0, 1);
   public static final Item LITOGRAPHED_PLATE = new ItemItem("litographed_plate", CreativeTabs.MATERIALS, 0, 64);
   public static final Item GALVANIZED_PLATE = new ItemItem("galvanized_plate", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EREBRIS_SHARD = new ItemItem("erebris_shard", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EREBRIS_FRAGMENT = new ItemItem("erebris_fragment", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EREBRIS_CHUNK = new ItemItem("erebris_chunk", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BELT_OF_SHADOWS = new DashBelt.BeltOfShadows();
   public static final Item WHITEWIND_BELT = new DashBelt.WhitewindBelt();
   public static final Item SHIPWREAKERS_KNOT = new DashBelt.ShipwreakersKnot();
   public static final Item WINDKEEPER = new DashBelt.Windkeeper();
   public static final Item ANCIENT_BATTERY = new ItemAccumulator("ancient_battery", ItemAccumulator.ANCIENT_CAPACITY, ItemAccumulator.ANCIENT_THROUGHPUT);
   public static final Item AQUATRONIC_BATTERY = new ItemAccumulator("aquatronic_battery", ItemAccumulator.AQUATRONIC_CAPACITY, ItemAccumulator.AQUATRONIC_THROUGHPUT);
   public static final Item ANCIENT_SPAWNER_PIECE = new ItemItem("ancient_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item EYE_OF_BEHOLDER = new ItemItem("eye_of_beholder", CreativeTabs.MATERIALS, 0, 64);
   public static final Item THUNDERBIRD_WINGS = new ThunderbirdWings();
   public static final Item FIN_WINGS = new FinWings();
   public static final Item WORSHIPPERS_BAIT = new WorshippersBait();
   public static final Item KRAKEN_SKIN = new ItemItem("kraken_skin", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SOLIDIFIED_LIGHTNING = new ItemNoGravivy("solidified_lightning", CreativeTabs.MATERIALS, 0, 64);
   public static final Item ETHERITE_FUEL_CELL = new ItemItem("etherite_fuel_cell", CreativeTabs.MATERIALS, 0, 16);
   public static final Item VITREOUS_HEART = new ItemItem("vitreous_heart", CreativeTabs.MATERIALS, 0, 64);
   public static final Item FIBER_CLOTH = new ItemItem("fiber_cloth", CreativeTabs.MATERIALS, 0, 64).setFuel(90);
   public static final Item ANTI_RAD_PLATING = new ItemItem("anti_rad_plating", CreativeTabs.MATERIALS, 0, 64);
   public static final Item BLACK_GOO = new ItemItem("black_goo", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PALE_MEAT_RAW = new ItemEatable(
      "pale_meat_raw", 0, 64, 2, 0.0F, true, 32, new PotionEffect[]{new PotionEffect(PotionEffects.WAVING, 220)}, new float[]{0.5F}, false, 0
   );
   public static final Item PALE_MEAT_SMOKED = new ItemEatable("pale_meat_smoked", 0, 64, 6, 0.5F, true, 32, null, null, false, 0);
   public static final Item FISH_STEAK_RAW = new ItemEatable("fish_steak_raw", 0, 64, 7, 0.9F, true, 32, null, null, false, 0);
   public static final Item FISH_STEAK_ROASTED = new ItemEatable("fish_steak_roasted", 0, 64, 10, 1.0F, true, 32, null, null, false, 0);
   public static final Item THUNDERBIRD_FEATHER = new ItemItem("thunderbird_feather", CreativeTabs.MATERIALS, 0, 64);
   public static final Item GEIGER_COUNTER = new GeigerCounter();
   public static final Item HEALTHFUL_CAPSULE = new HealthfulCapsule();
   public static final Item SCRAP_BOMB = new ItemItem("scrap_bomb", CreativeTabs.MATERIALS, 0, 64);
   public static final Item STABILIZATION_CELL = new ItemItem("stabilization_cell", CreativeTabs.MATERIALS, 0, 64);
   public static final Item AQUATIC_CIRCUIT = new ItemItem("aquatic_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static final Item TIDAL_HEART = new ItemItem("tidal_heart", CreativeTabs.MATERIALS, 0, 64);
   public static final Item PIRATE_SEXTANT = new ItemItem("pirate_sextant", CreativeTabs.MATERIALS, 0, 64);
   public static final Item MERMAID_MEDALLION = new BaublesPack.MermaidMedallion();
   public static final Item SIREN_KEY = new ItemKey("siren_key", 1, ChestLock.SIREN, false);
   public static final Item SACRIFICIAL_DAGGER = new SacrificialDagger();
   public static final Item STORM_SPAWNER_PIECE = new ItemItem("storm_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SELECTIVE_LEVITATOR = new BaublesPack.SelectiveLevitator();
   public static final Item FIBER_BANDAGE = new FiberBandage();
   public static final Item DOLERITE_KEY = new ItemKey("dolerite_key", 64, ChestLock.DOLERITE, true);
   public static final Item WIZARD_CLOTH = new ItemItem("wizard_cloth", CreativeTabs.MATERIALS, 0, 64);
   public static final Item SWARMETER = new Swarmeter();
   public static final Item RICH_SCRAP = new ItemItem("rich_scrap", CreativeTabs.MATERIALS, 0, 64);
   public static final Item WEATHER_ROCKET_CLEAR = new ItemWeatherRocket("weather_rocket_clear", null, true, 0.8F, 0.8F, 0.5F);
   public static final Item WEATHER_ROCKET_SNOWFALL = new ItemWeatherRocket("weather_rocket_snowfall", "Snowfall", true, 0.95F, 0.95F, 1.0F);
   public static final Item WEATHER_ROCKET = new ItemWeatherRocket("weather_rocket_hail", "Hail", true, 0.5F, 0.75F, 1.0F);
   public static final Item WEATHER_ROCKET_AURORA = new ItemWeatherRocket("weather_rocket_aurora", "Aurora", true, 0.1F, 1.0F, 0.57F);
   public static final Item WEATHER_ROCKET_POISON_RAIN = new ItemWeatherRocket("weather_rocket_poison_rain", "PoisonRain", true, 0.1F, 1.0F, 0.1F);
   public static final Item WEATHER_ROCKET_RAIN_FALL = new ItemWeatherRocket("weather_rocket_rainfall", "Rainfall", true, 0.3F, 0.8F, 1.0F);
   public static final Item WEATHER_ROCKET_STORM = new ItemWeatherRocket("weather_rocket_storm", "Storm", true, 0.92F, 0.4F, 1.0F);
   public static final Item WEATHER_ROCKET_RAINS_TORM = new ItemWeatherRocket("weather_rocket_rainstorm", "Rainstorm", true, 0.45F, 0.75F, 1.0F);
   public static final Item CALIBRATION_CRYSTAL_ATTRACT_BIG = new ItemCalibrationThing("calibration_crystal_attract_big", 1.0F, 0.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.attracttex);
   public static final Item CALIBRATION_CRYSTAL_ATTRACT_MEDIUM = new ItemCalibrationThing("calibration_crystal_attract_medium", 0.5F, 0.0F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.attracttex);
   public static final Item CALIBRATION_CRYSTAL_ATTRACT_SMALL = new ItemCalibrationThing("calibration_crystal_attract_small", 0.1F, 0.0F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.attracttex);
   public static final Item CALIBRATION_CRYSTAL_DETRACT_BIG = new ItemCalibrationThing("calibration_crystal_detract_big", -1.0F, 0.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.detracttex);
   public static final Item CALIBRATION_CRYSTAL_DETRACT_MEDIUM = new ItemCalibrationThing("calibration_crystal_detract_medium", -0.5F, 0.0F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.detracttex);
   public static final Item CALIBRATION_CRYSTAL_DETRACT_SMALL = new ItemCalibrationThing("calibration_crystal_detract_small", -0.1F, 0.0F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.detracttex);
   public static final Item CALIBRATION_CRYSTAL_FOCUS_BIG = new ItemCalibrationThing("calibration_crystal_focus_big", 0.0F, -1.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.focustex);
   public static final Item CALIBRATION_CRYSTAL_FOCUS_MEDIUM = new ItemCalibrationThing("calibration_crystal_focus_medium", 0.0F, -0.5F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.focustex);
   public static final Item CALIBRATION_CRYSTAL_FOCUS_SMALL = new ItemCalibrationThing("calibration_crystal_focus_small", 0.0F, -0.1F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.focustex);
   public static final Item CALIBRATION_CRYSTAL_RANGE_BIG = new ItemCalibrationThing("calibration_crystal_range_big", 0.0F, 1.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.rangetex);
   public static final Item CALIBRATION_CRYSTAL_RANGE_MEDIUM = new ItemCalibrationThing("calibration_crystal_range_medium", 0.0F, 0.5F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.rangetex);
   public static final Item CALIBRATION_CRYSTAL_RANGE_SMALL = new ItemCalibrationThing("calibration_crystal_range_small", 0.0F, 0.1F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.rangetex);
   public static final Item CALIBRATION_CRYSTAL_SPEED_BIG = new ItemCalibrationThing("calibration_crystal_speed_big", 0.0F, 0.0F, 1.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.speedtex);
   public static final Item CALIBRATION_CRYSTAL_SPEED_MEDIUM = new ItemCalibrationThing("calibration_crystal_speed_medium", 0.0F, 0.0F, 0.5F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.speedtex);
   public static final Item CALIBRATION_CRYSTAL_SPEED_SMALL = new ItemCalibrationThing("calibration_crystal_speed_small", 0.0F, 0.0F, 0.1F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.speedtex);
   public static final Item CALIBRATION_CRYSTAL_SLOW_BIG = new ItemCalibrationThing("calibration_crystal_slow_big", 0.0F, 0.0F, -1.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.slowtex);
   public static final Item CALIBRATION_CRYSTAL_SLOW_MEDIUM = new ItemCalibrationThing("calibration_crystal_slow_medium", 0.0F, 0.0F, -0.5F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.slowtex);
   public static final Item CALIBRATION_CRYSTAL_SLOW_SMALL = new ItemCalibrationThing("calibration_crystal_slow_small", 0.0F, 0.0F, -0.1F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.slowtex);
   public static final Item CANDLE = new ItemCalibrationThing("magic_candle", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candletex, 19, 1);
   public static final Item MAGIC_CANDLE_BLACK = new ItemCalibrationThing("magic_candle_black", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_black, 19, 1);
   public static final Item MAGIC_CANDLE_RED = new ItemCalibrationThing("magic_candle_red", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_red, 19, 1);
   public static final Item MAGIC_CANDLE_GREEN = new ItemCalibrationThing("magic_candle_green", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_green, 19, 1);
   public static final Item MAGIC_CANDLE_BROWN = new ItemCalibrationThing("magic_candle_brown", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_brown, 19, 1);
   public static final Item MAGIC_CANDLE_BLUE = new ItemCalibrationThing("magic_candle_blue", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_blue, 19, 1);
   public static final Item MAGIC_CANDLE_PURPLE = new ItemCalibrationThing("magic_candle_purple", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_purple, 19, 1);
   public static final Item MAGIC_CANDLE_CYAN = new ItemCalibrationThing("magic_candle_cyan", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_cyan, 19, 1);
   public static final Item MAGIC_CANDLE_LIGHTGRAY = new ItemCalibrationThing("magic_candle_lightgray", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_lightgray, 19, 1);
   public static final Item MAGIC_CANDLE_GRAY = new ItemCalibrationThing("magic_candle_gray", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_gray, 19, 1);
   public static final Item MAGIC_CANDLE_PINK = new ItemCalibrationThing("magic_candle_pink", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_pink, 19, 1);
   public static final Item MAGIC_CANDLE_LIME = new ItemCalibrationThing("magic_candle_lime", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_lime, 19, 1);
   public static final Item MAGIC_CANDLE_YELLOW = new ItemCalibrationThing("magic_candle_yellow", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_yellow, 19, 1);
   public static final Item MAGIC_CANDLE_LIGHTBLUE = new ItemCalibrationThing("magic_candle_lightblue", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_lightblue, 19, 1);
   public static final Item MAGIC_CANDLE_MAGENTA = new ItemCalibrationThing("magic_candle_magenta", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_magenta, 19, 1);
   public static final Item MAGIC_CANDLE_ORANGE = new ItemCalibrationThing("magic_candle_orange", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_orange, 19, 1);
   public static final Item MAGIC_CANDLE_WHITE = new ItemCalibrationThing("magic_candle_white", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_white, 19, 1);
   public static final Item RUBY_PICKAXE = new ItemBasicTool("ruby_pickaxe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("pickaxe", 2, 7.5F)
      .setItemToRepair(RUBY)
      .setToolEnchantability(23);
   public static final Item RUBY_AXE = new ItemBasicTool("ruby_axe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("axe", 2, 7.5F)
      .setItemToRepair(RUBY)
      .setToolEnchantability(23);
   public static final Item RUBY_SHOVEL = new ItemBasicTool("ruby_shovel", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("shovel", 2, 7.5F)
      .setItemToRepair(RUBY)
      .setToolEnchantability(23);
   public static final Item SAPPHIRE_PICKAXE = new ItemBasicTool("sapphire_pickaxe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("pickaxe", 2, 7.5F)
      .setItemToRepair(SAPPHIRE)
      .setToolEnchantability(23);
   public static final Item SAPPHIRE_AXE = new ItemBasicTool("sapphire_axe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("axe", 2, 7.5F)
      .setItemToRepair(SAPPHIRE)
      .setToolEnchantability(23);
   public static final Item SAPPHIRE_SHOVEL = new ItemBasicTool("sapphire_shovel", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("shovel", 2, 7.5F)
      .setItemToRepair(SAPPHIRE)
      .setToolEnchantability(23);
   public static final Item CITRINE_PICKAXE = new ItemBasicTool("citrine_pickaxe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("pickaxe", 2, 7.5F)
      .setItemToRepair(CITRINE)
      .setToolEnchantability(23);
   public static final Item CITRINE_AXE = new ItemBasicTool("citrine_axe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("axe", 2, 7.5F)
      .setItemToRepair(CITRINE)
      .setToolEnchantability(23);
   public static final Item CITRINE_SHOVEL = new ItemBasicTool("citrine_shovel", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("shovel", 2, 7.5F)
      .setItemToRepair(CITRINE)
      .setToolEnchantability(23);
   public static final Item AMETHYST_PICKAXE = new ItemBasicTool("amethyst_pickaxe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("pickaxe", 1, 7.0F)
      .setItemToRepair(AMETHYST)
      .setToolEnchantability(23);
   public static final Item AMETHYST_AXE = new ItemBasicTool("amethyst_axe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("axe", 1, 7.0F)
      .setItemToRepair(AMETHYST)
      .setToolEnchantability(23);
   public static final Item AMETHYST_SHOVEL = new ItemBasicTool("amethyst_shovel", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("shovel", 1, 7.0F)
      .setItemToRepair(AMETHYST)
      .setToolEnchantability(23);
   public static final Item TOPAZ_PICKAXE = new ItemBasicTool("topaz_pickaxe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("pickaxe", 1, 7.0F)
      .setItemToRepair(TOPAZ)
      .setToolEnchantability(23);
   public static final Item TOPAZ_AXE = new ItemBasicTool("topaz_axe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("axe", 1, 7.0F)
      .setItemToRepair(TOPAZ)
      .setToolEnchantability(23);
   public static final Item TOPAZ_SHOVEL = new ItemBasicTool("topaz_shovel", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("shovel", 1, 7.0F)
      .setItemToRepair(TOPAZ)
      .setToolEnchantability(23);
   public static final Item RHINESTONE_PICKAXE = new ItemBasicTool("rhinestone_pickaxe", CreativeTabs.TOOLS, 140, 1, true)
      .setHarvestLvl("pickaxe", 1, 6.5F)
      .setItemToRepair(RHINESTONE)
      .setToolEnchantability(23);
   public static final Item RHINESTONE_AXE = new ItemBasicTool("rhinestone_axe", CreativeTabs.TOOLS, 140, 1, true)
      .setHarvestLvl("axe", 1, 6.5F)
      .setItemToRepair(RHINESTONE)
      .setToolEnchantability(23);
   public static final Item RHINESTONE_SHOVEL = new ItemBasicTool("rhinestone_shovel", CreativeTabs.TOOLS, 140, 1, true)
      .setHarvestLvl("shovel", 1, 6.5F)
      .setItemToRepair(RHINESTONE)
      .setToolEnchantability(23);
   public static final Item FORGETPICK_AXE = new ItemBasicTool("forget_pickaxe", CreativeTabs.TOOLS, 1800, 1, true)
      .setHarvestLvl("pickaxe", 4, 20.0F)
      .setItemToRepair(INFERNUM_INGOT)
      .setToolEnchantability(15);
   public static final Item FORGET_AXE = new ItemBasicTool("forget_axe", CreativeTabs.TOOLS, 1300, 1, true)
      .setHarvestLvl("axe", 4, 20.0F)
      .setItemToRepair(INFERNUM_INGOT)
      .setToolEnchantability(15);
   public static final Item FORGET_SHOVEL = new ItemBasicTool("forget_shovel", CreativeTabs.TOOLS, 1200, 1, true)
      .setHarvestLvl("shovel", 4, 20.0F)
      .setItemToRepair(INFERNUM_INGOT)
      .setToolEnchantability(15);
   public static final Item GOTHIC_PICKAXE = new ItemBasicTool("gothic_pickaxe", CreativeTabs.TOOLS, 1500, 1, true)
      .setHarvestLvl("pickaxe", 5, 30.0F)
      .setToolEnchantability(17);
   public static final Item GOTHIC_AXE = new ItemBasicTool("gothic_axe", CreativeTabs.TOOLS, 1500, 1, true)
      .setHarvestLvl("axe", 5, 30.0F)
      .setToolEnchantability(17);
   public static final Item GOTHIC_SHOVEL = new ItemBasicTool("gothic_shovel", CreativeTabs.TOOLS, 1500, 1, true)
      .setHarvestLvl("shovel", 5, 30.0F)
      .setToolEnchantability(17);
   public static final Item ARSENIC_PICKAXE = new ItemBasicTool("arsenic_pickaxe", CreativeTabs.TOOLS, 2050, 1, true)
      .setHarvestLvl("pickaxe", 8, 40.0F)
      .setItemToRepair(ARSENIC_INGOT)
      .setToolEnchantability(8);
   public static final Item ARSENIC_AXE = new ItemBasicTool("arsenic_axe", CreativeTabs.TOOLS, 2100, 1, true)
      .setHarvestLvl("axe", 8, 40.0F)
      .setItemToRepair(ARSENIC_INGOT)
      .setToolEnchantability(8);
   public static final Item ARSENIC_SHOVEL = new ItemBasicTool("arsenic_shovel", CreativeTabs.TOOLS, 2000, 1, true)
      .setHarvestLvl("shovel", 8, 40.0F)
      .setItemToRepair(ARSENIC_INGOT)
      .setToolEnchantability(8);
   public static final Item TOXINIUM_PICKAXE = new ItemBasicTool("toxinium_pickaxe", CreativeTabs.TOOLS, 4000, 1, true)
      .setHarvestLvl("pickaxe", 9, 47.0F)
      .setItemToRepair(TOXINIUM_INGOT)
      .setToolEnchantability(10);
   public static final Item TOXINIUM_AXE = new ItemBasicTool("toxinium_axe", CreativeTabs.TOOLS, 4000, 1, true)
      .setHarvestLvl("axe", 9, 47.0F)
      .setItemToRepair(TOXINIUM_INGOT)
      .setToolEnchantability(10);
   public static final Item TOXINIUM_SHOVEL = new ItemBasicTool("toxinium_shovel", CreativeTabs.TOOLS, 4000, 1, true)
      .setHarvestLvl("shovel", 9, 47.0F)
      .setItemToRepair(TOXINIUM_INGOT)
      .setToolEnchantability(10);
   public static final Item ADAMANTIUM_PICKAXE = new ItemBasicTool("adamantium_pickaxe", CreativeTabs.TOOLS, 5000, 1, true)
      .setHarvestLvl("pickaxe", 10, 55.0F)
      .setItemToRepair(ADAMANTIUM_INGOT)
      .setToolEnchantability(12);
   public static final Item ADAMANTIUM_AXE = new ItemBasicTool("adamantium_axe", CreativeTabs.TOOLS, 5000, 1, true)
      .setHarvestLvl("axe", 10, 55.0F)
      .setItemToRepair(ADAMANTIUM_INGOT)
      .setToolEnchantability(12);
   public static final Item ADAMANTIUM_SHOVEL = new ItemBasicTool("adamantium_shovel", CreativeTabs.TOOLS, 5000, 1, true)
      .setHarvestLvl("shovel", 10, 55.0F)
      .setItemToRepair(ADAMANTIUM_INGOT)
      .setToolEnchantability(12);
   public static final Item AQUATIC_PICKAXE = new ItemBasicTool("aquatic_pickaxe", CreativeTabs.TOOLS, 6000, 1, true)
      .setHarvestLvl("pickaxe", 11, 67.0F)
      .setItemToRepair(AQUATIC_INGOT)
      .setToolEnchantability(20);
   public static final Item AQUATIC_AXE = new ItemBasicTool("aquatic_axe", CreativeTabs.TOOLS, 6000, 1, true)
      .setHarvestLvl("axe", 11, 67.0F)
      .setItemToRepair(AQUATIC_INGOT)
      .setToolEnchantability(20);
   public static final Item AQUATIC_SHOVEL = new ItemBasicTool("aquatic_shovel", CreativeTabs.TOOLS, 6000, 1, true)
      .setHarvestLvl("shovel", 11, 67.0F)
      .setItemToRepair(AQUATIC_INGOT)
      .setToolEnchantability(20);
   public static final Item STORM_PICKAXE = new ItemBasicTool("storm_pickaxe", CreativeTabs.TOOLS, 7000, 1, true)
      .setHarvestLvl("pickaxe", 12, 85.0F)
      .setItemToRepair(PHASEOLITE)
      .setToolEnchantability(20);
   public static final Item STORM_AXE = new ItemBasicTool("storm_axe", CreativeTabs.TOOLS, 7000, 1, true)
      .setHarvestLvl("axe", 12, 85.0F)
      .setItemToRepair(PHASEOLITE)
      .setToolEnchantability(20);
   public static final Item STORM_SHOVEL = new ItemBasicTool("storm_shovel", CreativeTabs.TOOLS, 7000, 1, true)
      .setHarvestLvl("shovel", 12, 85.0F)
      .setItemToRepair(PHASEOLITE)
      .setToolEnchantability(20);
   public static final MiningTools DIAMOND_MINING_TOOLS = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_diamond.png"),
      "tools_diamond",
      3,
      0.15F,
      "diamond_drill",
      "diamond_chainsaw",
      ItemAccumulator.LEAD_ACID_CAPACITY,
      ItemAccumulator.LEAD_ACID_THROUGHPUT,
      200,
      2048,
      1000,
      new MiningTools.LaserDigger("laser_digger", 5120, ItemAccumulator.LEAD_ACID_CAPACITY * 2, ItemAccumulator.LEAD_ACID_THROUGHPUT, 180),
      new Vec3d(1.0, 0.0, 1.0)
   );
   public static final Item DIAMOND_ELECTRIC_DRILL = DIAMOND_MINING_TOOLS.electricDrill;
   public static final Item DIAMOND_FUEL_DRILL = DIAMOND_MINING_TOOLS.fuelDrill;
   public static final Item DIAMOND_ELECTRIC_CHAINSAW = DIAMOND_MINING_TOOLS.electricChainsaw;
   public static final Item DIAMOND_FUEL_CHAINSAW = DIAMOND_MINING_TOOLS.fuelChainsaw;
   public static final Item LASER_MINING_DIGGER = DIAMOND_MINING_TOOLS.miningLaser;
   public static final MiningTools TOXINIUM_MINING_TOOLS = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_toxinium.png"),
      "tools_toxinium",
      9,
      1.3F,
      "toxinium_drill",
      "toxinium_chainsaw",
      ItemAccumulator.LI_ION_CAPACITY * 2,
      ItemAccumulator.LI_ION_THROUGHPUT,
      270,
      5000,
      4000,
      new MiningTools.NuclearMiningRay("nuclear_mining_ray", 12500, ItemAccumulator.LI_ION_CAPACITY * 3, ItemAccumulator.LI_ION_THROUGHPUT, 210),
      new Vec3d(0.0, 1.0, 0.0)
   );
   public static final Item TOXINIUM_ELECTRIC_DRILL = TOXINIUM_MINING_TOOLS.electricDrill;
   public static final Item TOXINIUM_FUEL_DRILL = TOXINIUM_MINING_TOOLS.fuelDrill;
   public static final Item TOXINIUM_ELECTRIC_CHAINSAW = TOXINIUM_MINING_TOOLS.electricChainsaw;
   public static final Item TOXINIUM_FUEL_CHAINSAW = TOXINIUM_MINING_TOOLS.fuelChainsaw;
   public static final Item NUCLEAR_MINING_RAY = TOXINIUM_MINING_TOOLS.miningLaser;
   public static final MiningTools ADAMANTIUM_MINING_TOOLS = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_adamantium.png"),
      "tools_adamantium",
      10,
      1.4F,
      "adamantium_drill",
      "adamantium_chainsaw",
      ItemAccumulator.ANCIENT_CAPACITY,
      ItemAccumulator.ANCIENT_THROUGHPUT,
      340,
      6000,
      6000,
      new MiningTools.EyelightProspector("eyelight_prospector", 15000, ItemAccumulator.ANCIENT_CAPACITY * 2, ItemAccumulator.ANCIENT_THROUGHPUT, 240),
      new Vec3d(0.78, 0.87, 0.93)
   );
   public static final Item ADAMANTIUM_ELECTRIC_DRILL = ADAMANTIUM_MINING_TOOLS.electricDrill;
   public static final Item ADAMANTIUM_FUEL_DRILL = ADAMANTIUM_MINING_TOOLS.fuelDrill;
   public static final Item ADAMANTIUM_ELECTRIC_CHAINSAW = ADAMANTIUM_MINING_TOOLS.electricChainsaw;
   public static final Item ADAMANTIUM_FUEL_CHAINSAW = ADAMANTIUM_MINING_TOOLS.fuelChainsaw;
   public static final Item EYE_LIGHT_PROSPECTOR = ADAMANTIUM_MINING_TOOLS.miningLaser;
   public static final MiningTools AQUATIC_MINING_TOOLS = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_aquatic.png"),
      "tools_aquatic",
      11,
      1.6F,
      "aquatic_drill",
      "aquatic_chainsaw",
      ItemAccumulator.AQUATRONIC_CAPACITY,
      ItemAccumulator.AQUATRONIC_THROUGHPUT,
      400,
      7000,
      8000,
      new MiningTools.CorrosiveWaterblaster("corrosive_waterblaster", 15000, 8000, 1),
      new Vec3d(0.2, 0.73, 1.0)
   );
   public static final Item AQUATIC_ELECTRIC_DRILL = AQUATIC_MINING_TOOLS.electricDrill;
   public static final Item AQUATIC_FUEL_DRILL = AQUATIC_MINING_TOOLS.fuelDrill;
   public static final Item AQUATIC_ELECTRIC_CHAINSAW = AQUATIC_MINING_TOOLS.electricChainsaw;
   public static final Item AQUATIC_FUEL_CHAINSAW = AQUATIC_MINING_TOOLS.fuelChainsaw;
   public static final Item CORROSIVE_WATER_BLASTER = AQUATIC_MINING_TOOLS.miningLaser;
   public static final MiningTools STORM_MINING_TOOLS = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_storm.png"),
      "tools_storm",
      12,
      1.9F,
      "storm_drill",
      "storm_chainsaw",
      ItemAccumulator.TOPAZITRON_CAPACITY,
      ItemAccumulator.TOPAZITRON_THROUGHPUT,
      450,
      8000,
      10000,
      new MiningTools.PlasmaCutter("plasma_cutter", 15000, ItemAccumulator.TOPAZITRON_CAPACITY * 2, ItemAccumulator.TOPAZITRON_THROUGHPUT, 320),
      new Vec3d(0.69, 0.58, 0.9)
   );
   public static final Item STORM_ELECTRIC_DRILL = STORM_MINING_TOOLS.electricDrill;
   public static final Item STORM_FUEL_DRILL = STORM_MINING_TOOLS.fuelDrill;
   public static final Item STORM_ELECTRIC_CHAINSAW = STORM_MINING_TOOLS.electricChainsaw;
   public static final Item STORM_FUEL_CHAINSAW = STORM_MINING_TOOLS.fuelChainsaw;
   public static final Item PLASMA_CUTTER = STORM_MINING_TOOLS.miningLaser;

   @SideOnly(Side.CLIENT)
   public static void registerItemsRender() {
      ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      CreateItemFile.ResLocationCreate(BIOFILTERING_MODULE);
      CreateItemFile.ResLocationCreate(RICH_SCRAP);
      String inventory = "inventory";
      mesher.register(RICH_SCRAP, 0, new ModelResourceLocation(RICH_SCRAP.getRegistryName(), inventory));
      mesher.register(BIOFILTERING_MODULE, 0, new ModelResourceLocation(BIOFILTERING_MODULE.getRegistryName(), inventory));
      mesher.register(SWARMETER, 0, new ModelResourceLocation(SWARMETER.getRegistryName(), inventory));
      mesher.register(WIZARD_CLOTH, 0, new ModelResourceLocation(WIZARD_CLOTH.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_POISON_RAIN, 0, new ModelResourceLocation(WEATHER_ROCKET_POISON_RAIN.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_RAIN_FALL, 0, new ModelResourceLocation(WEATHER_ROCKET_RAIN_FALL.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_STORM, 0, new ModelResourceLocation(WEATHER_ROCKET_STORM.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_RAINS_TORM, 0, new ModelResourceLocation(WEATHER_ROCKET_RAINS_TORM.getRegistryName(), inventory));
      mesher.register(TOXINIUM_PICKAXE, 0, new ModelResourceLocation(TOXINIUM_PICKAXE.getRegistryName(), inventory));
      mesher.register(TOXINIUM_AXE, 0, new ModelResourceLocation(TOXINIUM_AXE.getRegistryName(), inventory));
      mesher.register(TOXINIUM_SHOVEL, 0, new ModelResourceLocation(TOXINIUM_SHOVEL.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_PICKAXE, 0, new ModelResourceLocation(ADAMANTIUM_PICKAXE.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_AXE, 0, new ModelResourceLocation(ADAMANTIUM_AXE.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_SHOVEL, 0, new ModelResourceLocation(ADAMANTIUM_SHOVEL.getRegistryName(), inventory));
      mesher.register(AQUATIC_PICKAXE, 0, new ModelResourceLocation(AQUATIC_PICKAXE.getRegistryName(), inventory));
      mesher.register(AQUATIC_AXE, 0, new ModelResourceLocation(AQUATIC_AXE.getRegistryName(), inventory));
      mesher.register(AQUATIC_SHOVEL, 0, new ModelResourceLocation(AQUATIC_SHOVEL.getRegistryName(), inventory));
      mesher.register(STORM_PICKAXE, 0, new ModelResourceLocation(STORM_PICKAXE.getRegistryName(), inventory));
      mesher.register(STORM_AXE, 0, new ModelResourceLocation(STORM_AXE.getRegistryName(), inventory));
      mesher.register(STORM_SHOVEL, 0, new ModelResourceLocation(STORM_SHOVEL.getRegistryName(), inventory));
      mesher.register(DOLERITE_KEY, 0, new ModelResourceLocation(DOLERITE_KEY.getRegistryName(), inventory));
      mesher.register(FIBER_BANDAGE, 0, new ModelResourceLocation(FIBER_BANDAGE.getRegistryName(), inventory));
      mesher.register(SELECTIVE_LEVITATOR, 0, new ModelResourceLocation(SELECTIVE_LEVITATOR.getRegistryName(), inventory));
      mesher.register(STORM_SPAWNER_PIECE, 0, new ModelResourceLocation(STORM_SPAWNER_PIECE.getRegistryName(), inventory));
      mesher.register(SACRIFICIAL_DAGGER, 0, new ModelResourceLocation(SACRIFICIAL_DAGGER.getRegistryName(), inventory));
      mesher.register(SIREN_KEY, 0, new ModelResourceLocation(SIREN_KEY.getRegistryName(), inventory));
      mesher.register(MERMAID_MEDALLION, 0, new ModelResourceLocation(MERMAID_MEDALLION.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_CLEAR, 0, new ModelResourceLocation(WEATHER_ROCKET_CLEAR.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_SNOWFALL, 0, new ModelResourceLocation(WEATHER_ROCKET_SNOWFALL.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET, 0, new ModelResourceLocation(WEATHER_ROCKET.getRegistryName(), inventory));
      mesher.register(WEATHER_ROCKET_AURORA, 0, new ModelResourceLocation(WEATHER_ROCKET_AURORA.getRegistryName(), inventory));
      mesher.register(PIRATE_SEXTANT, 0, new ModelResourceLocation(PIRATE_SEXTANT.getRegistryName(), inventory));
      mesher.register(AQUATIC_CIRCUIT, 0, new ModelResourceLocation(AQUATIC_CIRCUIT.getRegistryName(), inventory));
      mesher.register(TIDAL_HEART, 0, new ModelResourceLocation(TIDAL_HEART.getRegistryName(), inventory));
      mesher.register(STABILIZATION_CELL, 0, new ModelResourceLocation(STABILIZATION_CELL.getRegistryName(), inventory));
      mesher.register(SCRAP_BOMB, 0, new ModelResourceLocation(SCRAP_BOMB.getRegistryName(), inventory));
      mesher.register(HEALTHFUL_CAPSULE, 0, new ModelResourceLocation(HEALTHFUL_CAPSULE.getRegistryName(), inventory));
      mesher.register(GEIGER_COUNTER, 0, new ModelResourceLocation(GEIGER_COUNTER.getRegistryName(), inventory));
      mesher.register(THUNDERBIRD_FEATHER, 0, new ModelResourceLocation(THUNDERBIRD_FEATHER.getRegistryName(), inventory));
      mesher.register(PIZZA_SEAFOOD, 0, new ModelResourceLocation(PIZZA_SEAFOOD.getRegistryName(), inventory));
      mesher.register(PALE_MEAT_RAW, 0, new ModelResourceLocation(PALE_MEAT_RAW.getRegistryName(), inventory));
      mesher.register(PALE_MEAT_SMOKED, 0, new ModelResourceLocation(PALE_MEAT_SMOKED.getRegistryName(), inventory));
      mesher.register(FISH_STEAK_RAW, 0, new ModelResourceLocation(FISH_STEAK_RAW.getRegistryName(), inventory));
      mesher.register(FISH_STEAK_ROASTED, 0, new ModelResourceLocation(FISH_STEAK_ROASTED.getRegistryName(), inventory));
      mesher.register(BLACK_GOO, 0, new ModelResourceLocation(BLACK_GOO.getRegistryName(), inventory));
      mesher.register(FIBER_CLOTH, 0, new ModelResourceLocation(FIBER_CLOTH.getRegistryName(), inventory));
      mesher.register(ANTI_RAD_PLATING, 0, new ModelResourceLocation(ANTI_RAD_PLATING.getRegistryName(), inventory));
      mesher.register(PHASEOLITE, 0, new ModelResourceLocation(PHASEOLITE.getRegistryName(), inventory));
      mesher.register(ETHERITE_FUEL_CELL, 0, new ModelResourceLocation(ETHERITE_FUEL_CELL.getRegistryName(), inventory));
      mesher.register(VITREOUS_HEART, 0, new ModelResourceLocation(VITREOUS_HEART.getRegistryName(), inventory));
      mesher.register(SOLIDIFIED_LIGHTNING, 0, new ModelResourceLocation(SOLIDIFIED_LIGHTNING.getRegistryName(), inventory));
      mesher.register(KRAKEN_SKIN, 0, new ModelResourceLocation(KRAKEN_SKIN.getRegistryName(), inventory));
      mesher.register(WORSHIPPERS_BAIT, 0, new ModelResourceLocation(WORSHIPPERS_BAIT.getRegistryName(), inventory));
      mesher.register(FIN_WINGS, 0, new ModelResourceLocation(FIN_WINGS.getRegistryName(), inventory));
      mesher.register(ANCIENT_BATTERY, 0, new ModelResourceLocation(ANCIENT_BATTERY.getRegistryName(), inventory));
      mesher.register(AQUATRONIC_BATTERY, 0, new ModelResourceLocation(AQUATRONIC_BATTERY.getRegistryName(), inventory));
      mesher.register(ANCIENT_SPAWNER_PIECE, 0, new ModelResourceLocation(ANCIENT_SPAWNER_PIECE.getRegistryName(), inventory));
      mesher.register(EYE_OF_BEHOLDER, 0, new ModelResourceLocation(EYE_OF_BEHOLDER.getRegistryName(), inventory));
      mesher.register(THUNDERBIRD_WINGS, 0, new ModelResourceLocation(THUNDERBIRD_WINGS.getRegistryName(), inventory));
      mesher.register(WINDKEEPER, 0, new ModelResourceLocation(WINDKEEPER.getRegistryName(), inventory));
      mesher.register(SHIPWREAKERS_KNOT, 0, new ModelResourceLocation(SHIPWREAKERS_KNOT.getRegistryName(), inventory));
      mesher.register(WHITEWIND_BELT, 0, new ModelResourceLocation(WHITEWIND_BELT.getRegistryName(), inventory));
      mesher.register(BELT_OF_SHADOWS, 0, new ModelResourceLocation(BELT_OF_SHADOWS.getRegistryName(), inventory));
      mesher.register(EREBRIS_SHARD, 0, new ModelResourceLocation(EREBRIS_SHARD.getRegistryName(), inventory));
      mesher.register(EREBRIS_FRAGMENT, 0, new ModelResourceLocation(EREBRIS_FRAGMENT.getRegistryName(), inventory));
      mesher.register(EREBRIS_CHUNK, 0, new ModelResourceLocation(EREBRIS_CHUNK.getRegistryName(), inventory));
      mesher.register(SILICIUM, 0, new ModelResourceLocation(SILICIUM.getRegistryName(), inventory));
      mesher.register(SILICIUM_WAFER, 0, new ModelResourceLocation(SILICIUM_WAFER.getRegistryName(), inventory));
      mesher.register(PHOTORESISTED_PLATE, 0, new ModelResourceLocation(PHOTORESISTED_PLATE.getRegistryName(), inventory));
      mesher.register(LITOGRAPHED_PLATE, 0, new ModelResourceLocation(LITOGRAPHED_PLATE.getRegistryName(), inventory));
      mesher.register(GALVANIZED_PLATE, 0, new ModelResourceLocation(GALVANIZED_PLATE.getRegistryName(), inventory));
      mesher.register(LEPIDOLITE, 0, new ModelResourceLocation(LEPIDOLITE.getRegistryName(), inventory));
      mesher.register(LITHIUM_NUGGET, 0, new ModelResourceLocation(LITHIUM_NUGGET.getRegistryName(), inventory));
      mesher.register(LITHIUM_INGOT, 0, new ModelResourceLocation(LITHIUM_INGOT.getRegistryName(), inventory));
      mesher.register(LITHIUM_DUST, 0, new ModelResourceLocation(LITHIUM_DUST.getRegistryName(), inventory));
      mesher.register(VACUUM_GUN_PELLETS, 0, new ModelResourceLocation(VACUUM_GUN_PELLETS.getRegistryName(), inventory));
      mesher.register(LOW_FRICTION_BEARING, 0, new ModelResourceLocation(LOW_FRICTION_BEARING.getRegistryName(), inventory));
      mesher.register(CHARM_OF_UNDYING, 0, new ModelResourceLocation(CHARM_OF_UNDYING.getRegistryName(), inventory));
      mesher.register(GOTHIC_GEM, 0, new ModelResourceLocation(GOTHIC_GEM.getRegistryName(), inventory));
      mesher.register(HYDRAULIC_SHOTGUN_CLIP, 0, new ModelResourceLocation(HYDRAULIC_SHOTGUN_CLIP.getRegistryName(), inventory));
      mesher.register(GOTHIC_GEAR, 0, new ModelResourceLocation(GOTHIC_GEAR.getRegistryName(), inventory));
      mesher.register(ICE_CIRCLE, 0, new ModelResourceLocation(ICE_CIRCLE.getRegistryName(), inventory));
      mesher.register(ICE_CIRCLE_FILLED, 0, new ModelResourceLocation(ICE_CIRCLE_FILLED.getRegistryName(), inventory));
      mesher.register(HEALTH_FRUIT, 0, new ModelResourceLocation(HEALTH_FRUIT.getRegistryName(), inventory));
      mesher.register(MANA_EXPANSION_POTION, 0, new ModelResourceLocation(MANA_EXPANSION_POTION.getRegistryName(), inventory));
      mesher.register(RUBBLESTONE, 0, new ModelResourceLocation(RUBBLESTONE.getRegistryName(), inventory));
      mesher.register(SALTPETER, 0, new ModelResourceLocation(SALTPETER.getRegistryName(), inventory));
      mesher.register(ASH, 0, new ModelResourceLocation(ASH.getRegistryName(), inventory));
      mesher.register(SLIME_LOCATOR, 0, new ModelResourceLocation(SLIME_LOCATOR.getRegistryName(), inventory));
      mesher.register(RHINESTONE_PICKAXE, 0, new ModelResourceLocation(RHINESTONE_PICKAXE.getRegistryName(), inventory));
      mesher.register(RHINESTONE_AXE, 0, new ModelResourceLocation(RHINESTONE_AXE.getRegistryName(), inventory));
      mesher.register(RHINESTONE_SHOVEL, 0, new ModelResourceLocation(RHINESTONE_SHOVEL.getRegistryName(), inventory));
      mesher.register(TOPAZ_PICKAXE, 0, new ModelResourceLocation(TOPAZ_PICKAXE.getRegistryName(), inventory));
      mesher.register(TOPAZ_AXE, 0, new ModelResourceLocation(TOPAZ_AXE.getRegistryName(), inventory));
      mesher.register(TOPAZ_SHOVEL, 0, new ModelResourceLocation(TOPAZ_SHOVEL.getRegistryName(), inventory));
      mesher.register(SAPPHIRE_PICKAXE, 0, new ModelResourceLocation(SAPPHIRE_PICKAXE.getRegistryName(), inventory));
      mesher.register(SAPPHIRE_AXE, 0, new ModelResourceLocation(SAPPHIRE_AXE.getRegistryName(), inventory));
      mesher.register(SAPPHIRE_SHOVEL, 0, new ModelResourceLocation(SAPPHIRE_SHOVEL.getRegistryName(), inventory));
      mesher.register(AMETHYST_PICKAXE, 0, new ModelResourceLocation(AMETHYST_PICKAXE.getRegistryName(), inventory));
      mesher.register(AMETHYST_AXE, 0, new ModelResourceLocation(AMETHYST_AXE.getRegistryName(), inventory));
      mesher.register(AMETHYST_SHOVEL, 0, new ModelResourceLocation(AMETHYST_SHOVEL.getRegistryName(), inventory));
      mesher.register(CITRINE_PICKAXE, 0, new ModelResourceLocation(CITRINE_PICKAXE.getRegistryName(), inventory));
      mesher.register(CITRINE_AXE, 0, new ModelResourceLocation(CITRINE_AXE.getRegistryName(), inventory));
      mesher.register(CITRINE_SHOVEL, 0, new ModelResourceLocation(CITRINE_SHOVEL.getRegistryName(), inventory));
      mesher.register(RUBY_PICKAXE, 0, new ModelResourceLocation(RUBY_PICKAXE.getRegistryName(), inventory));
      mesher.register(RUBY_AXE, 0, new ModelResourceLocation(RUBY_AXE.getRegistryName(), inventory));
      mesher.register(RUBY_SHOVEL, 0, new ModelResourceLocation(RUBY_SHOVEL.getRegistryName(), inventory));
      mesher.register(MISSING_DUST, 0, new ModelResourceLocation(MISSING_DUST.getRegistryName(), inventory));
      mesher.register(MISSING_INGOT, 0, new ModelResourceLocation(MISSING_INGOT.getRegistryName(), inventory));
      mesher.register(MISSING_NUGGET, 0, new ModelResourceLocation(MISSING_NUGGET.getRegistryName(), inventory));
      mesher.register(MISSING_MATERIAL, 0, new ModelResourceLocation(MISSING_MATERIAL.getRegistryName(), inventory));
      mesher.register(INK, 0, new ModelResourceLocation(INK.getRegistryName(), inventory));
      mesher.register(QUARTZ_DUST, 0, new ModelResourceLocation(QUARTZ_DUST.getRegistryName(), inventory));
      mesher.register(EMPTY_SYRINGE, 0, new ModelResourceLocation(EMPTY_SYRINGE.getRegistryName(), inventory));
      mesher.register(PYROLYSIS_MODULE, 0, new ModelResourceLocation(PYROLYSIS_MODULE.getRegistryName(), inventory));
      mesher.register(PURPUR_ALLOY, 0, new ModelResourceLocation(PURPUR_ALLOY.getRegistryName(), inventory));
      mesher.register(BLACK_STRAP, 0, new ModelResourceLocation(BLACK_STRAP.getRegistryName(), inventory));
      mesher.register(TITANIUM_SLAG, 0, new ModelResourceLocation(TITANIUM_SLAG.getRegistryName(), inventory));
      mesher.register(AQUATIC_SPAWNER_PIECE, 0, new ModelResourceLocation(AQUATIC_SPAWNER_PIECE.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_ORE_DUST, 0, new ModelResourceLocation(ADAMANTIUM_ORE_DUST.getRegistryName(), inventory));
      mesher.register(MITHRIL_ORE_DUST, 0, new ModelResourceLocation(MITHRIL_ORE_DUST.getRegistryName(), inventory));
      mesher.register(STONE_DUST, 0, new ModelResourceLocation(STONE_DUST.getRegistryName(), inventory));
      mesher.register(LIMESTONE_DUST, 0, new ModelResourceLocation(LIMESTONE_DUST.getRegistryName(), inventory));
      mesher.register(BASALT_DUST, 0, new ModelResourceLocation(BASALT_DUST.getRegistryName(), inventory));
      mesher.register(URANIUM_INGOT, 0, new ModelResourceLocation(URANIUM_INGOT.getRegistryName(), inventory));
      mesher.register(URANIUM_DUST, 0, new ModelResourceLocation(URANIUM_DUST.getRegistryName(), inventory));
      mesher.register(URANIUM_NUGGET, 0, new ModelResourceLocation(URANIUM_NUGGET.getRegistryName(), inventory));
      mesher.register(TOXINIUM_ORE_DUST, 0, new ModelResourceLocation(TOXINIUM_ORE_DUST.getRegistryName(), inventory));
      mesher.register(RADIOACTIVE_DUST, 0, new ModelResourceLocation(RADIOACTIVE_DUST.getRegistryName(), inventory));
      mesher.register(NYLON, 0, new ModelResourceLocation(NYLON.getRegistryName(), inventory));
      mesher.register(TAR, 0, new ModelResourceLocation(TAR.getRegistryName(), inventory));
      mesher.register(PARAFFIN, 0, new ModelResourceLocation(PARAFFIN.getRegistryName(), inventory));
      mesher.register(POLYMERIZATION_MODULE, 0, new ModelResourceLocation(POLYMERIZATION_MODULE.getRegistryName(), inventory));
      mesher.register(DISTILLATION_MODULE, 0, new ModelResourceLocation(DISTILLATION_MODULE.getRegistryName(), inventory));
      mesher.register(RESISTANT_CIRCUIT, 0, new ModelResourceLocation(RESISTANT_CIRCUIT.getRegistryName(), inventory));
      mesher.register(GOLD_TRANSFORMER, 0, new ModelResourceLocation(GOLD_TRANSFORMER.getRegistryName(), inventory));
      mesher.register(SILVER_TRANSFORMER, 0, new ModelResourceLocation(SILVER_TRANSFORMER.getRegistryName(), inventory));
      mesher.register(TOPAZITRON_CRYSTAL, 0, new ModelResourceLocation(TOPAZITRON_CRYSTAL.getRegistryName(), inventory));
      mesher.register(BATTERY_TOPAZITRON_CRYSTAL, 0, new ModelResourceLocation(BATTERY_TOPAZITRON_CRYSTAL.getRegistryName(), inventory));
      mesher.register(ELECTROMAGNETIC_BEARING, 0, new ModelResourceLocation(ELECTROMAGNETIC_BEARING.getRegistryName(), inventory));
      mesher.register(BLUE_ARTHROSTELECHA_ROD, 0, new ModelResourceLocation(BLUE_ARTHROSTELECHA_ROD.getRegistryName(), inventory));
      mesher.register(PINK_ARTHROSTELECHA_ROD, 0, new ModelResourceLocation(PINK_ARTHROSTELECHA_ROD.getRegistryName(), inventory));
      mesher.register(IMPULSE_THRUSTER, 0, new ModelResourceLocation(IMPULSE_THRUSTER.getRegistryName(), inventory));
      mesher.register(STORM_CIRCUIT, 0, new ModelResourceLocation(STORM_CIRCUIT.getRegistryName(), inventory));
      mesher.register(STORMBRASS_PLASMATRON, 0, new ModelResourceLocation(STORMBRASS_PLASMATRON.getRegistryName(), inventory));
      mesher.register(MITHRIL_INGOT, 0, new ModelResourceLocation(MITHRIL_INGOT.getRegistryName(), inventory));
      mesher.register(MITHRIL, 0, new ModelResourceLocation(MITHRIL.getRegistryName(), inventory));
      mesher.register(MITHRIL_NUGGET, 0, new ModelResourceLocation(MITHRIL_NUGGET.getRegistryName(), inventory));
      mesher.register(AQUATIC_NUGGET, 0, new ModelResourceLocation(AQUATIC_NUGGET.getRegistryName(), inventory));
      mesher.register(PEARL, 0, new ModelResourceLocation(PEARL.getRegistryName(), inventory));
      mesher.register(BLACK_PEARL, 0, new ModelResourceLocation(BLACK_PEARL.getRegistryName(), inventory));
      mesher.register(GLOWING_PEARL, 0, new ModelResourceLocation(GLOWING_PEARL.getRegistryName(), inventory));
      mesher.register(AQUATIC_PEARL, 0, new ModelResourceLocation(AQUATIC_PEARL.getRegistryName(), inventory));
      mesher.register(AQUATIC_INGOT, 0, new ModelResourceLocation(AQUATIC_INGOT.getRegistryName(), inventory));
      mesher.register(AQUATIC_DUST, 0, new ModelResourceLocation(AQUATIC_DUST.getRegistryName(), inventory));
      mesher.register(CORAL, 0, new ModelResourceLocation(CORAL.getRegistryName(), inventory));
      mesher.register(ARCHELON_SHELL, 0, new ModelResourceLocation(ARCHELON_SHELL.getRegistryName(), inventory));
      mesher.register(PLACODERM_SCALES, 0, new ModelResourceLocation(PLACODERM_SCALES.getRegistryName(), inventory));
      mesher.register(MESOGLEA, 0, new ModelResourceLocation(MESOGLEA.getRegistryName(), inventory));
      mesher.register(TOXEDGE_DOUGH, 0, new ModelResourceLocation(TOXEDGE_DOUGH.getRegistryName(), inventory));
      mesher.register(BUTTER, 0, new ModelResourceLocation(BUTTER.getRegistryName(), inventory));
      mesher.register(BISCUIT, 0, new ModelResourceLocation(BISCUIT.getRegistryName(), inventory));
      mesher.register(SWEET_DOUGH, 0, new ModelResourceLocation(SWEET_DOUGH.getRegistryName(), inventory));
      mesher.register(COCOA_POWDER, 0, new ModelResourceLocation(COCOA_POWDER.getRegistryName(), inventory));
      mesher.register(CARAMEL, 0, new ModelResourceLocation(CARAMEL.getRegistryName(), inventory));
      mesher.register(CHOCOLATE, 0, new ModelResourceLocation(CHOCOLATE.getRegistryName(), inventory));
      mesher.register(COCOA_BUTTER, 0, new ModelResourceLocation(COCOA_BUTTER.getRegistryName(), inventory));
      mesher.register(VIOLET_PUDDING, 0, new ModelResourceLocation(VIOLET_PUDDING.getRegistryName(), inventory));
      mesher.register(GREEN_PUDDING, 0, new ModelResourceLocation(GREEN_PUDDING.getRegistryName(), inventory));
      mesher.register(BROWN_PUDDING, 0, new ModelResourceLocation(BROWN_PUDDING.getRegistryName(), inventory));
      mesher.register(ORANGE_PUDDING, 0, new ModelResourceLocation(ORANGE_PUDDING.getRegistryName(), inventory));
      mesher.register(FLOUR, 0, new ModelResourceLocation(FLOUR.getRegistryName(), inventory));
      mesher.register(MEAT_BROTH, 0, new ModelResourceLocation(MEAT_BROTH.getRegistryName(), inventory));
      mesher.register(STUFFED_FIERY_BEAN, 0, new ModelResourceLocation(STUFFED_FIERY_BEAN.getRegistryName(), inventory));
      mesher.register(BORSCH, 0, new ModelResourceLocation(BORSCH.getRegistryName(), inventory));
      mesher.register(FERMENTER_MODULE, 0, new ModelResourceLocation(FERMENTER_MODULE.getRegistryName(), inventory));
      mesher.register(CENTRIFUGE_MODULE, 0, new ModelResourceLocation(CENTRIFUGE_MODULE.getRegistryName(), inventory));
      mesher.register(CHERRY_TOMATO, 0, new ModelResourceLocation(CHERRY_TOMATO.getRegistryName(), inventory));
      mesher.register(MAGIC_JAM, 0, new ModelResourceLocation(MAGIC_JAM.getRegistryName(), inventory));
      mesher.register(DOUGH, 0, new ModelResourceLocation(DOUGH.getRegistryName(), inventory));
      mesher.register(MOZZARELLA, 0, new ModelResourceLocation(MOZZARELLA.getRegistryName(), inventory));
      mesher.register(MUSHROOMS_IN_SAUCE, 0, new ModelResourceLocation(MUSHROOMS_IN_SAUCE.getRegistryName(), inventory));
      mesher.register(WHEY_STARTER, 0, new ModelResourceLocation(WHEY_STARTER.getRegistryName(), inventory));
      mesher.register(YEAST, 0, new ModelResourceLocation(YEAST.getRegistryName(), inventory));
      mesher.register(MOONSHROOM_MEAT, 0, new ModelResourceLocation(MOONSHROOM_MEAT.getRegistryName(), inventory));
      mesher.register(SALT_GRAINS, 0, new ModelResourceLocation(SALT_GRAINS.getRegistryName(), inventory));
      mesher.register(REDPEPPER, 0, new ModelResourceLocation(REDPEPPER.getRegistryName(), inventory));
      mesher.register(HEALTH_BERRY, 0, new ModelResourceLocation(HEALTH_BERRY.getRegistryName(), inventory));
      mesher.register(QUANTUM_SLIMEBALL, 0, new ModelResourceLocation(QUANTUM_SLIMEBALL.getRegistryName(), inventory));
      mesher.register(MANA_BERRY, 0, new ModelResourceLocation(MANA_BERRY.getRegistryName(), inventory));
      mesher.register(ALCHEMICAL_WAX, 0, new ModelResourceLocation(ALCHEMICAL_WAX.getRegistryName(), inventory));
      mesher.register(ENIGMATE_TWINS, 0, new ModelResourceLocation(ENIGMATE_TWINS.getRegistryName(), inventory));
      mesher.register(BULLET_DIVING, 0, new ModelResourceLocation(BULLET_DIVING.getRegistryName(), inventory));
      mesher.register(BULLET_CORAL, 0, new ModelResourceLocation(BULLET_CORAL.getRegistryName(), inventory));
      mesher.register(SHELL_ROCKET, 0, new ModelResourceLocation(SHELL_ROCKET.getRegistryName(), inventory));
      mesher.register(BULLET_ERRATIC, 0, new ModelResourceLocation(BULLET_ERRATIC.getRegistryName(), inventory));
      mesher.register(SURPRISE_ROCKET, 0, new ModelResourceLocation(SURPRISE_ROCKET.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_MINIGUN_CLIP, 0, new ModelResourceLocation(ADAMANTIUM_MINIGUN_CLIP.getRegistryName(), inventory));
      mesher.register(XMASS_BUNDLE, 0, new ModelResourceLocation(XMASS_BUNDLE.getRegistryName(), inventory));
      mesher.register(ELEMENTS_BOOK, 0, new ModelResourceLocation(ELEMENTS_BOOK.getRegistryName(), inventory));
      mesher.register(VIAL_EMPTY, 0, new ModelResourceLocation(VIAL_EMPTY.getRegistryName(), inventory));
      mesher.register(DASH_IMPULSE_CORSLET, 0, new ModelResourceLocation(DASH_IMPULSE_CORSLET.getRegistryName(), inventory));
      mesher.register(DASH_HELLHOUND_BELT, 0, new ModelResourceLocation(DASH_HELLHOUND_BELT.getRegistryName(), inventory));
      mesher.register(DASH_BELT_BLACK, 0, new ModelResourceLocation(DASH_BELT_BLACK.getRegistryName(), inventory));
      mesher.register(SHADOW_WINGS, 0, new ModelResourceLocation(SHADOW_WINGS.getRegistryName(), inventory));
      mesher.register(VIAL_FIRE, 0, new ModelResourceLocation(VIAL_FIRE.getRegistryName(), inventory));
      mesher.register(VIAL_EARTH, 0, new ModelResourceLocation(VIAL_EARTH.getRegistryName(), inventory));
      mesher.register(VIAL_WATER, 0, new ModelResourceLocation(VIAL_WATER.getRegistryName(), inventory));
      mesher.register(VIAL_AIR, 0, new ModelResourceLocation(VIAL_AIR.getRegistryName(), inventory));
      mesher.register(VIAL_POISON, 0, new ModelResourceLocation(VIAL_POISON.getRegistryName(), inventory));
      mesher.register(VIAL_COLD, 0, new ModelResourceLocation(VIAL_COLD.getRegistryName(), inventory));
      mesher.register(VIAL_ELECTRIC, 0, new ModelResourceLocation(VIAL_ELECTRIC.getRegistryName(), inventory));
      mesher.register(VIAL_VOID, 0, new ModelResourceLocation(VIAL_VOID.getRegistryName(), inventory));
      mesher.register(VIAL_PLEASURE, 0, new ModelResourceLocation(VIAL_PLEASURE.getRegistryName(), inventory));
      mesher.register(VIAL_PAIN, 0, new ModelResourceLocation(VIAL_PAIN.getRegistryName(), inventory));
      mesher.register(VIAL_DEATH, 0, new ModelResourceLocation(VIAL_DEATH.getRegistryName(), inventory));
      mesher.register(VIAL_LIVE, 0, new ModelResourceLocation(VIAL_LIVE.getRegistryName(), inventory));
      mesher.register(MAGIC_EXPLORING_KIT, 0, new ModelResourceLocation(MAGIC_EXPLORING_KIT.getRegistryName(), inventory));
      mesher.register(MAGIC_RESEARCH_KIT, 0, new ModelResourceLocation(MAGIC_RESEARCH_KIT.getRegistryName(), inventory));
      mesher.register(MAGIC_WRITING_KIT, 0, new ModelResourceLocation(MAGIC_WRITING_KIT.getRegistryName(), inventory));
      mesher.register(COOLED_RIFLE_CLIP, 0, new ModelResourceLocation(COOLED_RIFLE_CLIP.getRegistryName(), inventory));
      mesher.register(BULLET_NIVEOUS, 0, new ModelResourceLocation(BULLET_NIVEOUS.getRegistryName(), inventory));
      mesher.register(NIVEOLITE, 0, new ModelResourceLocation(NIVEOLITE.getRegistryName(), inventory));
      mesher.register(HELLHOUND_FUR, 0, new ModelResourceLocation(HELLHOUND_FUR.getRegistryName(), inventory));
      mesher.register(FROZEN_SPAWNER_PIECE, 0, new ModelResourceLocation(FROZEN_SPAWNER_PIECE.getRegistryName(), inventory));
      mesher.register(RUSTED_SPAWNER_PIECE, 0, new ModelResourceLocation(RUSTED_SPAWNER_PIECE.getRegistryName(), inventory));
      mesher.register(SPAWNER_PIECE, 0, new ModelResourceLocation(SPAWNER_PIECE.getRegistryName(), inventory));
      mesher.register(SLIMY_EGGS, 0, new ModelResourceLocation(SLIMY_EGGS.getRegistryName(), inventory));
      mesher.register(BERYLLIUM_GRAINS, 0, new ModelResourceLocation(BERYLLIUM_GRAINS.getRegistryName(), inventory));
      mesher.register(ELECTROLYZER_MODULE, 0, new ModelResourceLocation(ELECTROLYZER_MODULE.getRegistryName(), inventory));
      mesher.register(ALUMINIUM_INGOT, 0, new ModelResourceLocation(ALUMINIUM_INGOT.getRegistryName(), inventory));
      mesher.register(ALUMINIUM_DUST, 0, new ModelResourceLocation(ALUMINIUM_DUST.getRegistryName(), inventory));
      mesher.register(ALUMINIUM_NUGGET, 0, new ModelResourceLocation(ALUMINIUM_NUGGET.getRegistryName(), inventory));
      mesher.register(BEARING_ALLOY_DUST, 0, new ModelResourceLocation(BEARING_ALLOY_DUST.getRegistryName(), inventory));
      mesher.register(COPPER_TRANSFORMER, 0, new ModelResourceLocation(COPPER_TRANSFORMER.getRegistryName(), inventory));
      mesher.register(STAMP_MOLD, 0, new ModelResourceLocation(STAMP_MOLD.getRegistryName(), inventory));
      mesher.register(STEEL_STAMP, 0, new ModelResourceLocation(STEEL_STAMP.getRegistryName(), inventory));
      mesher.register(SILVER_INGOT, 0, new ModelResourceLocation(SILVER_INGOT.getRegistryName(), inventory));
      mesher.register(SILVER_DUST, 0, new ModelResourceLocation(SILVER_DUST.getRegistryName(), inventory));
      mesher.register(SILVER_NUGGET, 0, new ModelResourceLocation(SILVER_NUGGET.getRegistryName(), inventory));
      mesher.register(ARROW_WIND, 0, new ModelResourceLocation(ARROW_WIND.getRegistryName(), inventory));
      mesher.register(ARROW_TWIN, 0, new ModelResourceLocation(ARROW_TWIN.getRegistryName(), inventory));
      mesher.register(ARROW_MITHRIL, 0, new ModelResourceLocation(ARROW_MITHRIL.getRegistryName(), inventory));
      mesher.register(ARROW_BOUNCING, 0, new ModelResourceLocation(ARROW_BOUNCING.getRegistryName(), inventory));
      mesher.register(ARROW_SHELL, 0, new ModelResourceLocation(ARROW_SHELL.getRegistryName(), inventory));
      mesher.register(ARROW_VOID, 0, new ModelResourceLocation(ARROW_VOID.getRegistryName(), inventory));
      mesher.register(ARROW_FISH, 0, new ModelResourceLocation(ARROW_FISH.getRegistryName(), inventory));
      mesher.register(ARROW_BENGAL, 0, new ModelResourceLocation(ARROW_BENGAL.getRegistryName(), inventory));
      mesher.register(ARROW_MODERN, 0, new ModelResourceLocation(ARROW_MODERN.getRegistryName(), inventory));
      mesher.register(ARROW_VICIOUS, 0, new ModelResourceLocation(ARROW_VICIOUS.getRegistryName(), inventory));
      mesher.register(ARROW_FIREJET, 0, new ModelResourceLocation(ARROW_FIREJET.getRegistryName(), inventory));
      mesher.register(ARROW_FROZEN, 0, new ModelResourceLocation(ARROW_FROZEN.getRegistryName(), inventory));
      mesher.register(TIDE_ACTIVATOR_1, 0, new ModelResourceLocation(TIDE_ACTIVATOR_1.getRegistryName(), inventory));
      mesher.register(TIDE_ACTIVATOR_2, 0, new ModelResourceLocation(TIDE_ACTIVATOR_2.getRegistryName(), inventory));
      mesher.register(TIDE_ACTIVATOR_3, 0, new ModelResourceLocation(TIDE_ACTIVATOR_3.getRegistryName(), inventory));
      mesher.register(TIDE_ACTIVATOR_4, 0, new ModelResourceLocation(TIDE_ACTIVATOR_4.getRegistryName(), inventory));
      mesher.register(TIDE_ACTIVATOR_5, 0, new ModelResourceLocation(TIDE_ACTIVATOR_5.getRegistryName(), inventory));
      mesher.register(ARCANE_ROCKET, 0, new ModelResourceLocation(ARCANE_ROCKET.getRegistryName(), inventory));
      mesher.register(BUCKSHOT, 0, new ModelResourceLocation(BUCKSHOT.getRegistryName(), inventory));
      mesher.register(NORTHERN_HELM, 0, new ModelResourceLocation(NORTHERN_HELM.getRegistryName(), inventory));
      mesher.register(NORTHERN_CHEST, 0, new ModelResourceLocation(NORTHERN_CHEST.getRegistryName(), inventory));
      mesher.register(NORTHERN_LEGS, 0, new ModelResourceLocation(NORTHERN_LEGS.getRegistryName(), inventory));
      mesher.register(NORTHERN_BOOTS, 0, new ModelResourceLocation(NORTHERN_BOOTS.getRegistryName(), inventory));
      mesher.register(CRYSTAL_HELM, 0, new ModelResourceLocation(CRYSTAL_HELM.getRegistryName(), inventory));
      mesher.register(CRYSTAL_CHEST, 0, new ModelResourceLocation(CRYSTAL_CHEST.getRegistryName(), inventory));
      mesher.register(CRYSTAL_LEGS, 0, new ModelResourceLocation(CRYSTAL_LEGS.getRegistryName(), inventory));
      mesher.register(CRYSTAL_BOOTS, 0, new ModelResourceLocation(CRYSTAL_BOOTS.getRegistryName(), inventory));
      mesher.register(THUNDERER_HELM, 0, new ModelResourceLocation(THUNDERER_HELM.getRegistryName(), inventory));
      mesher.register(THUNDERER_CHEST, 0, new ModelResourceLocation(THUNDERER_CHEST.getRegistryName(), inventory));
      mesher.register(THUNDERER_LEGS, 0, new ModelResourceLocation(THUNDERER_LEGS.getRegistryName(), inventory));
      mesher.register(THUNDERER_BOOTS, 0, new ModelResourceLocation(THUNDERER_BOOTS.getRegistryName(), inventory));
      mesher.register(GOTHIC_SWORD, 0, new ModelResourceLocation(GOTHIC_SWORD.getRegistryName(), inventory));
      mesher.register(BURNING_FROST_IGNITER, 0, new ModelResourceLocation(BURNING_FROST_IGNITER.getRegistryName(), inventory));
      mesher.register(WINTER_SACRIFICE, 0, new ModelResourceLocation(WINTER_SACRIFICE.getRegistryName(), inventory));
      mesher.register(WINTER_SCALE, 0, new ModelResourceLocation(WINTER_SCALE.getRegistryName(), inventory));
      mesher.register(HAIL_TEAR, 0, new ModelResourceLocation(HAIL_TEAR.getRegistryName(), inventory));
      mesher.register(PLASMA_MINIGUN_CLIP, 0, new ModelResourceLocation(PLASMA_MINIGUN_CLIP.getRegistryName(), inventory));
      mesher.register(CRYSTAL_CUTTER_AMMO, 0, new ModelResourceLocation(CRYSTAL_CUTTER_AMMO.getRegistryName(), inventory));
      mesher.register(BLOWHOLE_PELLETS, 0, new ModelResourceLocation(BLOWHOLE_PELLETS.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_NUGGET, 0, new ModelResourceLocation(ADAMANTIUM_NUGGET.getRegistryName(), inventory));
      mesher.register(NAIL_GUN_CLIP, 0, new ModelResourceLocation(NAIL_GUN_CLIP.getRegistryName(), inventory));
      mesher.register(NAIL, 0, new ModelResourceLocation(NAIL.getRegistryName(), inventory));
      mesher.register(WOLFRAM_WIRE, 0, new ModelResourceLocation(WOLFRAM_WIRE.getRegistryName(), inventory));
      mesher.register(GAS_FILTER, 0, new ModelResourceLocation(GAS_FILTER.getRegistryName(), inventory));
      mesher.register(LEAD_ACID_BATTERY, 0, new ModelResourceLocation(LEAD_ACID_BATTERY.getRegistryName(), inventory));
      mesher.register(LI_ION_BATTERY, 0, new ModelResourceLocation(LI_ION_BATTERY.getRegistryName(), inventory));
      mesher.register(LEAD_BEARING, 0, new ModelResourceLocation(LEAD_BEARING.getRegistryName(), inventory));
      mesher.register(ARSENIC_BEARING, 0, new ModelResourceLocation(ARSENIC_BEARING.getRegistryName(), inventory));
      mesher.register(ELECTRIC_MOTOR, 0, new ModelResourceLocation(ELECTRIC_MOTOR.getRegistryName(), inventory));
      mesher.register(LINEAR_MOTOR, 0, new ModelResourceLocation(LINEAR_MOTOR.getRegistryName(), inventory));
      mesher.register(EYE_OF_SEER, 0, new ModelResourceLocation(EYE_OF_SEER.getRegistryName(), inventory));
      mesher.register(PROCESSOR, 0, new ModelResourceLocation(PROCESSOR.getRegistryName(), inventory));
      mesher.register(COPPER_WIRE, 0, new ModelResourceLocation(COPPER_WIRE.getRegistryName(), inventory));
      mesher.register(GOLD_WIRE, 0, new ModelResourceLocation(GOLD_WIRE.getRegistryName(), inventory));
      mesher.register(SILVER_WIRE, 0, new ModelResourceLocation(SILVER_WIRE.getRegistryName(), inventory));
      mesher.register(RUBBER, 0, new ModelResourceLocation(RUBBER.getRegistryName(), inventory));
      mesher.register(CIRCUIT, 0, new ModelResourceLocation(CIRCUIT.getRegistryName(), inventory));
      mesher.register(ADVANCED_CIRCUIT, 0, new ModelResourceLocation(ADVANCED_CIRCUIT.getRegistryName(), inventory));
      mesher.register(TOXIC_CIRCUIT, 0, new ModelResourceLocation(TOXIC_CIRCUIT.getRegistryName(), inventory));
      mesher.register(DIMENSION_CIRCUIT, 0, new ModelResourceLocation(DIMENSION_CIRCUIT.getRegistryName(), inventory));
      mesher.register(ICICLE_MINIGUN_CLIP, 0, new ModelResourceLocation(ICICLE_MINIGUN_CLIP.getRegistryName(), inventory));
      mesher.register(SLIME_PLASTIC, 0, new ModelResourceLocation(SLIME_PLASTIC.getRegistryName(), inventory));
      mesher.register(WHITE_SLIMEBALL, 0, new ModelResourceLocation(WHITE_SLIMEBALL.getRegistryName(), inventory));
      mesher.register(PLASTIC, 0, new ModelResourceLocation(PLASTIC.getRegistryName(), inventory));
      mesher.register(SLIME_CELL, 0, new ModelResourceLocation(SLIME_CELL.getRegistryName(), inventory));
      mesher.register(TOXIBERRY_STICK, 0, new ModelResourceLocation(TOXIBERRY_STICK.getRegistryName(), inventory));
      mesher.register(SCRAP_METAL, 0, new ModelResourceLocation(SCRAP_METAL.getRegistryName(), inventory));
      mesher.register(SULFUR_DUST, 0, new ModelResourceLocation(SULFUR_DUST.getRegistryName(), inventory));
      mesher.register(MAGMA_BLOOM_SEED, 0, new ModelResourceLocation(MAGMA_BLOOM_SEED.getRegistryName(), inventory));
      mesher.register(PIZZA_CHICKEN, 0, new ModelResourceLocation(PIZZA_CHICKEN.getRegistryName(), inventory));
      mesher.register(PIZZA_DIAVOLA, 0, new ModelResourceLocation(PIZZA_DIAVOLA.getRegistryName(), inventory));
      mesher.register(PIZZA_CHEESE, 0, new ModelResourceLocation(PIZZA_CHEESE.getRegistryName(), inventory));
      mesher.register(PIZZA_TOXIC, 0, new ModelResourceLocation(PIZZA_TOXIC.getRegistryName(), inventory));
      mesher.register(PIZZA_GLOWING, 0, new ModelResourceLocation(PIZZA_GLOWING.getRegistryName(), inventory));
      mesher.register(SMOKED_SAUSAGE, 0, new ModelResourceLocation(SMOKED_SAUSAGE.getRegistryName(), inventory));
      mesher.register(RAW_RIBS, 0, new ModelResourceLocation(RAW_RIBS.getRegistryName(), inventory));
      mesher.register(HOT_SPICY_RIBS, 0, new ModelResourceLocation(HOT_SPICY_RIBS.getRegistryName(), inventory));
      mesher.register(BAUXITE, 0, new ModelResourceLocation(BAUXITE.getRegistryName(), inventory));
      mesher.register(BRASS_INGOT, 0, new ModelResourceLocation(BRASS_INGOT.getRegistryName(), inventory));
      mesher.register(BRASS_DUST, 0, new ModelResourceLocation(BRASS_DUST.getRegistryName(), inventory));
      mesher.register(BRASS_NUGGET, 0, new ModelResourceLocation(BRASS_NUGGET.getRegistryName(), inventory));
      mesher.register(ZINC_INGOT, 0, new ModelResourceLocation(ZINC_INGOT.getRegistryName(), inventory));
      mesher.register(ZINC_DUST, 0, new ModelResourceLocation(ZINC_DUST.getRegistryName(), inventory));
      mesher.register(ZINC_NUGGET, 0, new ModelResourceLocation(ZINC_NUGGET.getRegistryName(), inventory));
      mesher.register(TITANIUM_INGOT, 0, new ModelResourceLocation(TITANIUM_INGOT.getRegistryName(), inventory));
      mesher.register(TITANIUM_DUST, 0, new ModelResourceLocation(TITANIUM_DUST.getRegistryName(), inventory));
      mesher.register(TITANIUM_NUGGET, 0, new ModelResourceLocation(TITANIUM_NUGGET.getRegistryName(), inventory));
      mesher.register(CHEMICAL_GLASS, 0, new ModelResourceLocation(CHEMICAL_GLASS.getRegistryName(), inventory));
      mesher.register(TOXINIUM_NUGGET, 0, new ModelResourceLocation(TOXINIUM_NUGGET.getRegistryName(), inventory));
      mesher.register(ICE_DUST, 0, new ModelResourceLocation(ICE_DUST.getRegistryName(), inventory));
      mesher.register(CONIFER_ROSIN, 0, new ModelResourceLocation(CONIFER_ROSIN.getRegistryName(), inventory));
      mesher.register(FIERY_OIL, 0, new ModelResourceLocation(FIERY_OIL.getRegistryName(), inventory));
      mesher.register(SALT, 0, new ModelResourceLocation(SALT.getRegistryName(), inventory));
      mesher.register(CRYSTALLIZED_POISON, 0, new ModelResourceLocation(CRYSTALLIZED_POISON.getRegistryName(), inventory));
      mesher.register(COPPER_SULFATE, 0, new ModelResourceLocation(COPPER_SULFATE.getRegistryName(), inventory));
      mesher.register(PLANT_FIBER, 0, new ModelResourceLocation(PLANT_FIBER.getRegistryName(), inventory));
      mesher.register(DRIED_PLANT_FIBER, 0, new ModelResourceLocation(DRIED_PLANT_FIBER.getRegistryName(), inventory));
      mesher.register(GLOWING_CRYSTAL_DUST, 0, new ModelResourceLocation(GLOWING_CRYSTAL_DUST.getRegistryName(), inventory));
      mesher.register(MAGIC_CRYSTAL_DUST, 0, new ModelResourceLocation(MAGIC_CRYSTAL_DUST.getRegistryName(), inventory));
      mesher.register(ADVANCED_POLYMER, 0, new ModelResourceLocation(ADVANCED_POLYMER.getRegistryName(), inventory));
      mesher.register(TOXIBERRY_JUICE_DRIP, 0, new ModelResourceLocation(TOXIBERRY_JUICE_DRIP.getRegistryName(), inventory));
      mesher.register(THUNDER_STONE, 0, new ModelResourceLocation(THUNDER_STONE.getRegistryName(), inventory));
      mesher.register(THUNDER_CAPACITOR, 0, new ModelResourceLocation(THUNDER_CAPACITOR.getRegistryName(), inventory));
      mesher.register(SKY_CRYSTAL, 0, new ModelResourceLocation(SKY_CRYSTAL.getRegistryName(), inventory));
      mesher.register(SKY_CRYSTAL_PIECE, 0, new ModelResourceLocation(SKY_CRYSTAL_PIECE.getRegistryName(), inventory));
      mesher.register(WIND_NATURE, 0, new ModelResourceLocation(WIND_NATURE.getRegistryName(), inventory));
      mesher.register(SKY_SPHERE, 0, new ModelResourceLocation(SKY_SPHERE.getRegistryName(), inventory));
      mesher.register(ARSENIC_INGOT, 0, new ModelResourceLocation(ARSENIC_INGOT.getRegistryName(), inventory));
      mesher.register(ARSENIC_DUST, 0, new ModelResourceLocation(ARSENIC_DUST.getRegistryName(), inventory));
      mesher.register(ARSENIC_NUGGET, 0, new ModelResourceLocation(ARSENIC_NUGGET.getRegistryName(), inventory));
      mesher.register(WOLFRAM_INGOT, 0, new ModelResourceLocation(WOLFRAM_INGOT.getRegistryName(), inventory));
      mesher.register(WOLFRAM_DUST, 0, new ModelResourceLocation(WOLFRAM_DUST.getRegistryName(), inventory));
      mesher.register(WOLFRAM_NUGGET, 0, new ModelResourceLocation(WOLFRAM_NUGGET.getRegistryName(), inventory));
      mesher.register(STORMBRASS_INGOT, 0, new ModelResourceLocation(STORMBRASS_INGOT.getRegistryName(), inventory));
      mesher.register(STORMBRASS_DUST, 0, new ModelResourceLocation(STORMBRASS_DUST.getRegistryName(), inventory));
      mesher.register(STORMBRASS_NUGGET, 0, new ModelResourceLocation(STORMBRASS_NUGGET.getRegistryName(), inventory));
      mesher.register(STORMSTEEL_NUGGET, 0, new ModelResourceLocation(STORMSTEEL_NUGGET.getRegistryName(), inventory));
      mesher.register(TOXINIUM_INGOT, 0, new ModelResourceLocation(TOXINIUM_INGOT.getRegistryName(), inventory));
      mesher.register(TOXINIUM_DUST, 0, new ModelResourceLocation(TOXINIUM_DUST.getRegistryName(), inventory));
      mesher.register(STORMSTEEL_INGOT, 0, new ModelResourceLocation(STORMSTEEL_INGOT.getRegistryName(), inventory));
      mesher.register(STORMSTEEL_DUST, 0, new ModelResourceLocation(STORMSTEEL_DUST.getRegistryName(), inventory));
      mesher.register(VOID_CRYSTAL, 0, new ModelResourceLocation(VOID_CRYSTAL.getRegistryName(), inventory));
      mesher.register(HEROBRINE_CURSE, 0, new ModelResourceLocation(HEROBRINE_CURSE.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_DUST, 0, new ModelResourceLocation(ADAMANTIUM_DUST.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_INGOT, 0, new ModelResourceLocation(ADAMANTIUM_INGOT.getRegistryName(), inventory));
      mesher.register(STORM_SPANNER, 0, new ModelResourceLocation(STORM_SPANNER.getRegistryName(), inventory));
      mesher.register(HAZARD_HELM, 0, new ModelResourceLocation(HAZARD_HELM.getRegistryName(), inventory));
      mesher.register(HAZARD_CHEST, 0, new ModelResourceLocation(HAZARD_CHEST.getRegistryName(), inventory));
      mesher.register(HAZARD_LEGS, 0, new ModelResourceLocation(HAZARD_LEGS.getRegistryName(), inventory));
      mesher.register(HAZARD_BOOTS, 0, new ModelResourceLocation(HAZARD_BOOTS.getRegistryName(), inventory));
      mesher.register(BULLET_ADAMANTIUM, 0, new ModelResourceLocation(BULLET_ADAMANTIUM.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_LONGSWORD, 0, new ModelResourceLocation(ADAMANTIUM_LONGSWORD.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_KNIFE, 0, new ModelResourceLocation(ADAMANTIUM_KNIFE.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_HELM, 0, new ModelResourceLocation(ADAMANTIUM_HELM.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_CHEST, 0, new ModelResourceLocation(ADAMANTIUM_CHEST.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_LEGS, 0, new ModelResourceLocation(ADAMANTIUM_LEGS.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_BOOTS, 0, new ModelResourceLocation(ADAMANTIUM_BOOTS.getRegistryName(), inventory));
      mesher.register(ADAMANTIUM_ROUNDS, 0, new ModelResourceLocation(ADAMANTIUM_ROUNDS.getRegistryName(), inventory));
      mesher.register(NORTHERN_INGOT, 0, new ModelResourceLocation(NORTHERN_INGOT.getRegistryName(), inventory));
      mesher.register(NORTHERN_SPHERE, 0, new ModelResourceLocation(NORTHERN_SPHERE.getRegistryName(), inventory));
      mesher.register(VILE_SUBSTANCE, 0, new ModelResourceLocation(VILE_SUBSTANCE.getRegistryName(), inventory));
      mesher.register(EMBRYO, 0, new ModelResourceLocation(EMBRYO.getRegistryName(), inventory));
      mesher.register(MUTAGEN, 0, new ModelResourceLocation(MUTAGEN.getRegistryName(), inventory));
      mesher.register(CMO, 0, new ModelResourceLocation(CMO.getRegistryName(), inventory));
      mesher.register(PROCESSOR_PATTERN, 0, new ModelResourceLocation(PROCESSOR_PATTERN.getRegistryName(), inventory));
      mesher.register(ARTHROSLELECHA_BRASS_LOG_WAND, 0, new ModelResourceLocation(ARTHROSLELECHA_BRASS_LOG_WAND.getRegistryName(), inventory));
      mesher.register(ARTHROSLELECHA_PINK_LOG_WAND, 0, new ModelResourceLocation(ARTHROSLELECHA_PINK_LOG_WAND.getRegistryName(), inventory));
      mesher.register(ENDER_CROWN, 0, new ModelResourceLocation(ENDER_CROWN.getRegistryName(), inventory));
      mesher.register(LIVE_HEART, 0, new ModelResourceLocation(LIVE_HEART.getRegistryName(), inventory));
      mesher.register(ITEM_TURRET, 0, new ModelResourceLocation(ITEM_TURRET.getRegistryName(), inventory));
      mesher.register(WRENCH, 0, new ModelResourceLocation(WRENCH.getRegistryName(), inventory));
      mesher.register(VIRULENT_ROD, 0, new ModelResourceLocation(VIRULENT_ROD.getRegistryName(), inventory));
      mesher.register(BUNKER_KEYCARD, 0, new ModelResourceLocation(BUNKER_KEYCARD.getRegistryName(), inventory));
      mesher.register(ANTI_RAD_PACK, 0, new ModelResourceLocation(ANTI_RAD_PACK.getRegistryName(), inventory));
      mesher.register(ANTI_RAD_PILLS, 0, new ModelResourceLocation(ANTI_RAD_PILLS.getRegistryName(), inventory));
      mesher.register(ANTI_RAD_INJECTOR, 0, new ModelResourceLocation(ANTI_RAD_INJECTOR.getRegistryName(), inventory));
      mesher.register(GAS_MASK, 0, new ModelResourceLocation(GAS_MASK.getRegistryName(), inventory));
      mesher.register(ARSENIC_PICKAXE, 0, new ModelResourceLocation(ARSENIC_PICKAXE.getRegistryName(), inventory));
      mesher.register(ARSENIC_AXE, 0, new ModelResourceLocation(ARSENIC_AXE.getRegistryName(), inventory));
      mesher.register(ARSENIC_SHOVEL, 0, new ModelResourceLocation(ARSENIC_SHOVEL.getRegistryName(), inventory));
      mesher.register(RUSTED_KEY, 0, new ModelResourceLocation(RUSTED_KEY.getRegistryName(), inventory));
      mesher.register(SNOW_COAT_HELM, 0, new ModelResourceLocation(SNOW_COAT_HELM.getRegistryName(), inventory));
      mesher.register(SNOW_COAT_CHEST, 0, new ModelResourceLocation(SNOW_COAT_CHEST.getRegistryName(), inventory));
      mesher.register(SNOW_COAT_LEGS, 0, new ModelResourceLocation(SNOW_COAT_LEGS.getRegistryName(), inventory));
      mesher.register(SNOW_COAT_BOOTS, 0, new ModelResourceLocation(SNOW_COAT_BOOTS.getRegistryName(), inventory));
      mesher.register(CORAL_HELM, 0, new ModelResourceLocation(CORAL_HELM.getRegistryName(), inventory));
      mesher.register(CORAL_CHEST, 0, new ModelResourceLocation(CORAL_CHEST.getRegistryName(), inventory));
      mesher.register(CORAL_LEGS, 0, new ModelResourceLocation(CORAL_LEGS.getRegistryName(), inventory));
      mesher.register(CORAL_BOOTS, 0, new ModelResourceLocation(CORAL_BOOTS.getRegistryName(), inventory));
      mesher.register(LICH_HELM, 0, new ModelResourceLocation(LICH_HELM.getRegistryName(), inventory));
      mesher.register(LICH_CHEST, 0, new ModelResourceLocation(LICH_CHEST.getRegistryName(), inventory));
      mesher.register(LICH_LEGS, 0, new ModelResourceLocation(LICH_LEGS.getRegistryName(), inventory));
      mesher.register(LICH_BOOTS, 0, new ModelResourceLocation(LICH_BOOTS.getRegistryName(), inventory));
      mesher.register(BONE_HELM, 0, new ModelResourceLocation(BONE_HELM.getRegistryName(), inventory));
      mesher.register(BONE_CHEST, 0, new ModelResourceLocation(BONE_CHEST.getRegistryName(), inventory));
      mesher.register(BONE_LEGS, 0, new ModelResourceLocation(BONE_LEGS.getRegistryName(), inventory));
      mesher.register(BONE_BOOTS, 0, new ModelResourceLocation(BONE_BOOTS.getRegistryName(), inventory));
      mesher.register(VOID_ROCKET, 0, new ModelResourceLocation(VOID_ROCKET.getRegistryName(), inventory));
      mesher.register(WATERBLAST_ROCKET, 0, new ModelResourceLocation(WATERBLAST_ROCKET.getRegistryName(), inventory));
      mesher.register(DEMOLISHING_ROCKET, 0, new ModelResourceLocation(DEMOLISHING_ROCKET.getRegistryName(), inventory));
      mesher.register(MINING_ROCKET, 0, new ModelResourceLocation(MINING_ROCKET.getRegistryName(), inventory));
      mesher.register(CHEMICAL_ROCKET, 0, new ModelResourceLocation(CHEMICAL_ROCKET.getRegistryName(), inventory));
      mesher.register(NAPALM_ROCKET, 0, new ModelResourceLocation(NAPALM_ROCKET.getRegistryName(), inventory));
      mesher.register(FROSTFIRE_ROCKET, 0, new ModelResourceLocation(FROSTFIRE_ROCKET.getRegistryName(), inventory));
      mesher.register(GLASS_HEART, 0, new ModelResourceLocation(GLASS_HEART.getRegistryName(), inventory));
      mesher.register(COMMON_ROCKET, 0, new ModelResourceLocation(COMMON_ROCKET.getRegistryName(), inventory));
      mesher.register(SNAP_BALL_AMMO, 0, new ModelResourceLocation(SNAP_BALL_AMMO.getRegistryName(), inventory));
      mesher.register(CREATIVE_TEAM_SELECTOR, 0, new ModelResourceLocation(CREATIVE_TEAM_SELECTOR.getRegistryName(), inventory));
      mesher.register(FIRE_WHIP, 0, new ModelResourceLocation(TIMELESS_SWORD.getRegistryName(), inventory));
      mesher.register(TIMELESS_SWORD, 0, new ModelResourceLocation(TIMELESS_SWORD.getRegistryName(), inventory));
      mesher.register(PALM_LOG_WAND, 0, new ModelResourceLocation(PALM_LOG_WAND.getRegistryName(), inventory));
      mesher.register(CORAL_RIFLE_CLIP, 0, new ModelResourceLocation(CORAL_RIFLE_CLIP.getRegistryName(), inventory));
      mesher.register(MANA_KEEPER, 0, new ModelResourceLocation(MANA_KEEPER.getRegistryName(), inventory));
      mesher.register(THORN_KEEPER, 0, new ModelResourceLocation(THORN_KEEPER.getRegistryName(), inventory));
      mesher.register(LIGHT_BAND, 0, new ModelResourceLocation(LIGHT_BAND.getRegistryName(), inventory));
      mesher.register(SPRINGER_WAISTBAND, 0, new ModelResourceLocation(SPRINGER_WAISTBAND.getRegistryName(), inventory));
      mesher.register(SPIRIT_THORN, 0, new ModelResourceLocation(SPIRIT_THORN.getRegistryName(), inventory));
      mesher.register(VENOMED_DAGGER, 0, new ModelResourceLocation(VENOMED_DAGGER.getRegistryName(), inventory));
      mesher.register(BLEEDING_ROOT, 0, new ModelResourceLocation(BLEEDING_ROOT.getRegistryName(), inventory));
      mesher.register(PAINFUL_ROOT, 0, new ModelResourceLocation(PAINFUL_ROOT.getRegistryName(), inventory));
      mesher.register(CYBER_AMULET, 0, new ModelResourceLocation(CYBER_AMULET.getRegistryName(), inventory));
      mesher.register(PERSISTENCE_PENDENT, 0, new ModelResourceLocation(PERSISTENCE_PENDENT.getRegistryName(), inventory));
      mesher.register(BRASS_KNUCKLES, 0, new ModelResourceLocation(BRASS_KNUCKLES.getRegistryName(), inventory));
      mesher.register(HELLHOUND_COLLAR, 0, new ModelResourceLocation(HELLHOUND_COLLAR.getRegistryName(), inventory));
      mesher.register(GOLDEN_KNUCKLES, 0, new ModelResourceLocation(GOLDEN_KNUCKLES.getRegistryName(), inventory));
      mesher.register(LIVE_BLOOD_NECKLACE, 0, new ModelResourceLocation(LIVE_BLOOD_NECKLACE.getRegistryName(), inventory));
      mesher.register(ANCIENT_ICE_SHARD, 0, new ModelResourceLocation(ANCIENT_ICE_SHARD.getRegistryName(), inventory));
      mesher.register(MANA_RUBBLE, 0, new ModelResourceLocation(MANA_RUBBLE.getRegistryName(), inventory));
      mesher.register(ICE_HEART, 0, new ModelResourceLocation(ICE_HEART.getRegistryName(), inventory));
      mesher.register(FROST_INGUISHER, 0, new ModelResourceLocation(FROST_INGUISHER.getRegistryName(), inventory));
      mesher.register(HOLY_EXTINGUISHER, 0, new ModelResourceLocation(HOLY_EXTINGUISHER.getRegistryName(), inventory));
      mesher.register(GHOSTFLAME_TRAP, 0, new ModelResourceLocation(GHOSTFLAME_TRAP.getRegistryName(), inventory));
      mesher.register(FLAME_SUPPRESSOR, 0, new ModelResourceLocation(FLAME_SUPPRESSOR.getRegistryName(), inventory));
      mesher.register(CONDUCTIVE_BELT, 0, new ModelResourceLocation(CONDUCTIVE_BELT.getRegistryName(), inventory));
      mesher.register(LIGHTNING_SOCKS, 0, new ModelResourceLocation(LIGHTNING_SOCKS.getRegistryName(), inventory));
      mesher.register(AMMONIA_FLASK, 0, new ModelResourceLocation(AMMONIA_FLASK.getRegistryName(), inventory));
      mesher.register(CORROSIVE_FLASK, 0, new ModelResourceLocation(CORROSIVE_FLASK.getRegistryName(), inventory));
      mesher.register(CROSS_CHAINLET, 0, new ModelResourceLocation(CROSS_CHAINLET.getRegistryName(), inventory));
      mesher.register(DETOXICATOR, 0, new ModelResourceLocation(DETOXICATOR.getRegistryName(), inventory));
      mesher.register(HAZARD_GLOVE, 0, new ModelResourceLocation(HAZARD_GLOVE.getRegistryName(), inventory));
      mesher.register(MAGIC_CONTACT_LENSES, 0, new ModelResourceLocation(MAGIC_CONTACT_LENSES.getRegistryName(), inventory));
      mesher.register(MINERS_GLOVE, 0, new ModelResourceLocation(MINERS_GLOVE.getRegistryName(), inventory));
      mesher.register(BODY_WARMER, 0, new ModelResourceLocation(BODY_WARMER.getRegistryName(), inventory));
      mesher.register(DEVOURERS_TEETH, 0, new ModelResourceLocation(DEVOURERS_TEETH.getRegistryName(), inventory));
      mesher.register(ENDER_LEECH, 0, new ModelResourceLocation(ENDER_LEECH.getRegistryName(), inventory));
      mesher.register(GASEOUS_ENERGY_DRINK, 0, new ModelResourceLocation(GASEOUS_ENERGY_DRINK.getRegistryName(), inventory));
      mesher.register(RUNNERS_SOCKS, 0, new ModelResourceLocation(RUNNERS_SOCKS.getRegistryName(), inventory));
      mesher.register(SLIME_EATER, 0, new ModelResourceLocation(SLIME_EATER.getRegistryName(), inventory));
      mesher.register(FIRE_EATER, 0, new ModelResourceLocation(FIRE_EATER.getRegistryName(), inventory));
      mesher.register(SLIME_DEVOURER, 0, new ModelResourceLocation(SLIME_DEVOURER.getRegistryName(), inventory));
      mesher.register(LAVA_EATER, 0, new ModelResourceLocation(LAVA_EATER.getRegistryName(), inventory));
      mesher.register(PERSONAL_EXTINGUISHER, 0, new ModelResourceLocation(PERSONAL_EXTINGUISHER.getRegistryName(), inventory));
      mesher.register(ETHER_WORM, 0, new ModelResourceLocation(ETHER_WORM.getRegistryName(), inventory));
      mesher.register(ANGEL_WORM, 0, new ModelResourceLocation(ANGEL_WORM.getRegistryName(), inventory));
      mesher.register(CANDY_APPLE, 0, new ModelResourceLocation(CANDY_APPLE.getRegistryName(), inventory));
      mesher.register(CANDY_CANE, 0, new ModelResourceLocation(CANDY_CANE.getRegistryName(), inventory));
      mesher.register(CRIMBERRY_WINE, 0, new ModelResourceLocation(CRIMBERRY_WINE.getRegistryName(), inventory));
      mesher.register(GIFT, 0, new ModelResourceLocation(GIFT.getRegistryName(), inventory));
      mesher.register(IMPETUS, 0, new ModelResourceLocation(AIRBORNE_CIRCLET.getRegistryName(), inventory));
      mesher.register(AIRBORNE_CIRCLET, 0, new ModelResourceLocation(AIRBORNE_CIRCLET.getRegistryName(), inventory));
      mesher.register(CHROMIUM_INGOT, 0, new ModelResourceLocation(CHROMIUM_INGOT.getRegistryName(), inventory));
      mesher.register(CHROMIUM_DUST, 0, new ModelResourceLocation(CHROMIUM_DUST.getRegistryName(), inventory));
      mesher.register(BERYLLIUM_INGOT, 0, new ModelResourceLocation(BERYLLIUM_INGOT.getRegistryName(), inventory));
      mesher.register(BERYLLIUM_DUST, 0, new ModelResourceLocation(BERYLLIUM_DUST.getRegistryName(), inventory));
      mesher.register(MANGANESE_DUST, 0, new ModelResourceLocation(MANGANESE_DUST.getRegistryName(), inventory));
      mesher.register(MANGANESE_INGOT, 0, new ModelResourceLocation(MANGANESE_INGOT.getRegistryName(), inventory));
      mesher.register(WEAPON_ENCHANTMENTS_BOX, 0, new ModelResourceLocation(WEAPON_ENCHANTMENTS_BOX.getRegistryName(), inventory));
      mesher.register(SIMPLE_ENCHANTMENTS_BOX, 0, new ModelResourceLocation(SIMPLE_ENCHANTMENTS_BOX.getRegistryName(), inventory));
      mesher.register(ALL_ENCHANTMENTS_BOX, 0, new ModelResourceLocation(ALL_ENCHANTMENTS_BOX.getRegistryName(), inventory));
      mesher.register(EMERALD_EYE, 0, new ModelResourceLocation(EMERALD_EYE.getRegistryName(), inventory));
      mesher.register(BULLET_FESTIVAL, 0, new ModelResourceLocation(BULLET_FESTIVAL.getRegistryName(), inventory));
      mesher.register(BULLET_EXPLODING, 0, new ModelResourceLocation(BULLET_EXPLODING.getRegistryName(), inventory));
      mesher.register(BULLET_CRYSTAL, 0, new ModelResourceLocation(BULLET_CRYSTAL.getRegistryName(), inventory));
      mesher.register(BULLET_TOXIC, 0, new ModelResourceLocation(BULLET_TOXIC.getRegistryName(), inventory));
      mesher.register(BULLET_POISONOUS, 0, new ModelResourceLocation(BULLET_POISONOUS.getRegistryName(), inventory));
      mesher.register(BULLET_THUNDER, 0, new ModelResourceLocation(BULLET_THUNDER.getRegistryName(), inventory));
      mesher.register(TOXINIUM_SHOTGUN_CLIP, 0, new ModelResourceLocation(TOXINIUM_SHOTGUN_CLIP.getRegistryName(), inventory));
      mesher.register(BULLET_INCENDIARY, 0, new ModelResourceLocation(BULLET_INCENDIARY.getRegistryName(), inventory));
      mesher.register(BULLET_SILVER, 0, new ModelResourceLocation(BULLET_SILVER.getRegistryName(), inventory));
      mesher.register(BULLET_LEAD, 0, new ModelResourceLocation(BULLET_LEAD.getRegistryName(), inventory));
      mesher.register(BULLET_COPPER, 0, new ModelResourceLocation(BULLET_COPPER.getRegistryName(), inventory));
      mesher.register(BULLE_TGOLD, 0, new ModelResourceLocation(BULLE_TGOLD.getRegistryName(), inventory));
      mesher.register(BULLET_FROZEN, 0, new ModelResourceLocation(BULLET_FROZEN.getRegistryName(), inventory));
      mesher.register(SUBMACHINE_CLIP, 0, new ModelResourceLocation(SUBMACHINE_CLIP.getRegistryName(), inventory));
      mesher.register(SUBMACHINE, 0, new ModelResourceLocation(SUBMACHINE.getRegistryName(), inventory));
      mesher.register(LOCKER, 0, new ModelResourceLocation(LOCKER.getRegistryName(), inventory));
      mesher.register(TOXINIUMSHOTGUN, 0, new ModelResourceLocation(TOXINIUMSHOTGUN.getRegistryName(), inventory));
      mesher.register(COIN, 0, new ModelResourceLocation(COIN.getRegistryName(), inventory));
      mesher.register(TOXINIUM_HELM, 0, new ModelResourceLocation(TOXINIUM_HELM.getRegistryName(), inventory));
      mesher.register(TOXINIUM_CHEST, 0, new ModelResourceLocation(TOXINIUM_CHEST.getRegistryName(), inventory));
      mesher.register(TOXINIUM_LEGS, 0, new ModelResourceLocation(TOXINIUM_LEGS.getRegistryName(), inventory));
      mesher.register(TOXINIUM_BOOTS, 0, new ModelResourceLocation(TOXINIUM_BOOTS.getRegistryName(), inventory));
      mesher.register(FORGETPICK_AXE, 0, new ModelResourceLocation(FORGETPICK_AXE.getRegistryName(), inventory));
      mesher.register(FORGET_AXE, 0, new ModelResourceLocation(FORGET_AXE.getRegistryName(), inventory));
      mesher.register(FORGET_SHOVEL, 0, new ModelResourceLocation(FORGET_SHOVEL.getRegistryName(), inventory));
      mesher.register(FIRST, 0, new ModelResourceLocation(FIRST.getRegistryName(), inventory));
      mesher.register(ICHOR_SHOWER, 0, new ModelResourceLocation(ICHOR_SHOWER.getRegistryName(), inventory));
      mesher.register(SHARK_CANNON, 0, new ModelResourceLocation(SHARK_CANNON.getRegistryName(), inventory));
      mesher.register(SHARK_AMMO, 0, new ModelResourceLocation(SHARK_AMMO.getRegistryName(), inventory));
      mesher.register(MAGIC_BOOMERANG, 0, new ModelResourceLocation(MAGIC_BOOMERANG.getRegistryName(), inventory));
      mesher.register(BUTTERFLY, 0, new ModelResourceLocation(BUTTERFLY.getRegistryName(), inventory));
      mesher.register(SUNRISE, 0, new ModelResourceLocation(SUNRISE.getRegistryName(), inventory));
      mesher.register(LASER_SNIPER, 0, new ModelResourceLocation(LASER_SNIPER.getRegistryName(), inventory));
      mesher.register(ION_BATTERY, 0, new ModelResourceLocation(ION_BATTERY.getRegistryName(), inventory));
      mesher.register(LASER_PISTOL, 0, new ModelResourceLocation(LASER_PISTOL.getRegistryName(), inventory));
      mesher.register(LASER_RIFLE, 0, new ModelResourceLocation(LASER_RIFLE.getRegistryName(), inventory));
      mesher.register(VAMPIRE_KNIFE, 0, new ModelResourceLocation(VAMPIRE_KNIFE.getRegistryName(), inventory));
      mesher.register(VAMPIRE_KNIFES, 0, new ModelResourceLocation(VAMPIRE_KNIFES.getRegistryName(), inventory));
      mesher.register(FROST_BOLT_STAFF, 0, new ModelResourceLocation(FROST_BOLT_STAFF.getRegistryName(), inventory));
      mesher.register(ANTIMATTER_CHARGE, 0, new ModelResourceLocation(ANTIMATTER_CHARGE.getRegistryName(), inventory));
      mesher.register(ELEMENTAL_AMMO_FIRE, 0, new ModelResourceLocation(ELEMENTAL_AMMO_FIRE.getRegistryName(), inventory));
      mesher.register(ELEMENTAL_AMMO_WATER, 0, new ModelResourceLocation(ELEMENTAL_AMMO_WATER.getRegistryName(), inventory));
      mesher.register(ELEMENTAL_AMMO_AIR, 0, new ModelResourceLocation(ELEMENTAL_AMMO_AIR.getRegistryName(), inventory));
      mesher.register(ELEMENTAL_AMMO_EARTH, 0, new ModelResourceLocation(ELEMENTAL_AMMO_EARTH.getRegistryName(), inventory));
      mesher.register(STINGER_BOLTS, 0, new ModelResourceLocation(STINGER_BOLTS.getRegistryName(), inventory));
      mesher.register(FIREWORK_PACK, 0, new ModelResourceLocation(FIREWORK_PACK.getRegistryName(), inventory));
      mesher.register(FIREWORK_DRAGON_ROCKET, 0, new ModelResourceLocation(FIREWORK_DRAGON_ROCKET.getRegistryName(), inventory));
      mesher.register(QUADROCOPTER_BELT, 0, new ModelResourceLocation(QUADROCOPTER_BELT.getRegistryName(), inventory));
      mesher.register(VORTEX_IN_A_BOTTLE, 0, new ModelResourceLocation(VORTEX_IN_A_BOTTLE.getRegistryName(), inventory));
      mesher.register(ETHER_SIGN, 0, new ModelResourceLocation(ETHER_SIGN.getRegistryName(), inventory));
      mesher.register(PHOENIX_GHOST_CAPE, 0, new ModelResourceLocation(PHOENIX_GHOST_CAPE.getRegistryName(), inventory));
      mesher.register(BILEBITER_SPHERE, 0, new ModelResourceLocation(BILEBITER_SPHERE.getRegistryName(), inventory));
      mesher.register(SNOWSTORM_STAFF, 0, new ModelResourceLocation(FROST_BOLT_STAFF.getRegistryName(), inventory));
      mesher.register(WIZARD_HELM, 0, new ModelResourceLocation(WIZARD_HELM.getRegistryName(), inventory));
      mesher.register(WIZARD_CHEST, 0, new ModelResourceLocation(WIZARD_CHEST.getRegistryName(), inventory));
      mesher.register(WIZARD_LEGS, 0, new ModelResourceLocation(WIZARD_LEGS.getRegistryName(), inventory));
      mesher.register(WIZARD_BOOTS, 0, new ModelResourceLocation(WIZARD_BOOTS.getRegistryName(), inventory));
      mesher.register(FIRE_MAGE_HELM, 0, new ModelResourceLocation(FIRE_MAGE_HELM.getRegistryName(), inventory));
      mesher.register(FIRE_MAGE_CHEST, 0, new ModelResourceLocation(FIRE_MAGE_CHEST.getRegistryName(), inventory));
      mesher.register(FIRE_MAGE_LEGS, 0, new ModelResourceLocation(FIRE_MAGE_LEGS.getRegistryName(), inventory));
      mesher.register(FIRE_MAGE_BOOTS, 0, new ModelResourceLocation(FIRE_MAGE_BOOTS.getRegistryName(), inventory));
      mesher.register(FIRE_LORD_HELM, 0, new ModelResourceLocation(FIRE_LORD_HELM.getRegistryName(), inventory));
      mesher.register(FIRE_LORD_CHEST, 0, new ModelResourceLocation(FIRE_LORD_CHEST.getRegistryName(), inventory));
      mesher.register(FIRE_LORD_LEGS, 0, new ModelResourceLocation(FIRE_LORD_LEGS.getRegistryName(), inventory));
      mesher.register(FIRE_LORD_BOOTS, 0, new ModelResourceLocation(FIRE_LORD_BOOTS.getRegistryName(), inventory));
      mesher.register(GRAPLING_HOOK, 0, new ModelResourceLocation(GRAPLING_HOOK.getRegistryName(), inventory));
      mesher.register(JUNGLE_GRAPLING_HOOK, 0, new ModelResourceLocation(JUNGLE_GRAPLING_HOOK.getRegistryName(), inventory));
      mesher.register(SEASHELL, 0, new ModelResourceLocation(SEASHELL.getRegistryName(), inventory));
      mesher.register(SLIME_GRAPLING_HOOK, 0, new ModelResourceLocation(SLIME_GRAPLING_HOOK.getRegistryName(), inventory));
      mesher.register(ENDER_GRAPLING_HOOK, 0, new ModelResourceLocation(ENDER_GRAPLING_HOOK.getRegistryName(), inventory));
      mesher.register(FISHING_ROD, 0, new ModelResourceLocation(FISHING_ROD.getRegistryName(), inventory));
      mesher.register(ICE_SWORD, 0, new ModelResourceLocation(ICE_SWORD.getRegistryName(), inventory));
      mesher.register(NETHER_GRINDER_AMMO, 0, new ModelResourceLocation(NETHER_GRINDER_AMMO.getRegistryName(), inventory));
      mesher.register(SNOWFLAKE_SHURIKEN, 0, new ModelResourceLocation(SNOWFLAKE_SHURIKEN.getRegistryName(), inventory));
      mesher.register(CHAIN_DAGGER, 0, new ModelResourceLocation(CHAIN_DAGGER.getRegistryName(), inventory));
      mesher.register(TOXIC_NUCLEAR_WARHEAD, 0, new ModelResourceLocation(TOXIC_NUCLEAR_WARHEAD.getRegistryName(), inventory));
      mesher.register(EMPTY_CELL, 0, new ModelResourceLocation(EMPTY_CELL.getRegistryName(), inventory));
      mesher.register(CRYOGEN_CELL, 0, new ModelResourceLocation(CRYOGEN_CELL.getRegistryName(), inventory));
      mesher.register(LIGHTNING_HOOK, 0, new ModelResourceLocation(LIGHTNING_HOOK.getRegistryName(), inventory));
      mesher.register(RING_OF_PROTECTION, 0, new ModelResourceLocation(RING_OF_PROTECTION.getRegistryName(), inventory));
      mesher.register(SPIKE_RING, 0, new ModelResourceLocation(SPIKE_RING.getRegistryName(), inventory));
      mesher.register(SPARKLING_NECKLACE, 0, new ModelResourceLocation(SPARKLING_NECKLACE.getRegistryName(), inventory));
      mesher.register(EXP, 0, new ModelResourceLocation(EMPTY_CELL.getRegistryName(), inventory));
      mesher.register(DEMONIC_IGNITER, 0, new ModelResourceLocation(DEMONIC_IGNITER.getRegistryName(), inventory));
      mesher.register(BOUNCING_RING, 0, new ModelResourceLocation(BOUNCING_RING.getRegistryName(), inventory));
      mesher.register(SLIME_BOOTS, 0, new ModelResourceLocation(SLIME_BOOTS.getRegistryName(), inventory));
      mesher.register(SLIME_HELM, 0, new ModelResourceLocation(SLIME_HELM.getRegistryName(), inventory));
      mesher.register(SLIME_CHEST, 0, new ModelResourceLocation(SLIME_CHEST.getRegistryName(), inventory));
      mesher.register(SLIME_LEGS, 0, new ModelResourceLocation(SLIME_LEGS.getRegistryName(), inventory));
      mesher.register(ICE_BOOTS, 0, new ModelResourceLocation(ICE_BOOTS.getRegistryName(), inventory));
      mesher.register(ICE_HELM, 0, new ModelResourceLocation(ICE_HELM.getRegistryName(), inventory));
      mesher.register(ICE_CHEST, 0, new ModelResourceLocation(ICE_CHEST.getRegistryName(), inventory));
      mesher.register(ICE_LEGS, 0, new ModelResourceLocation(ICE_LEGS.getRegistryName(), inventory));
      mesher.register(SOUL_CHARM, 0, new ModelResourceLocation(SOUL_CHARM.getRegistryName(), inventory));
      mesher.register(JUNGLE_BOOTS, 0, new ModelResourceLocation(JUNGLE_BOOTS.getRegistryName(), inventory));
      mesher.register(JUNGLE_HELM, 0, new ModelResourceLocation(JUNGLE_HELM.getRegistryName(), inventory));
      mesher.register(JUNGLE_CHESTPLATE, 0, new ModelResourceLocation(JUNGLE_CHESTPLATE.getRegistryName(), inventory));
      mesher.register(JUNGLE_LEGGINS, 0, new ModelResourceLocation(JUNGLE_LEGGINS.getRegistryName(), inventory));
      mesher.register(WEB_GRAPLING_HOOK, 0, new ModelResourceLocation(WEB_GRAPLING_HOOK.getRegistryName(), inventory));
      mesher.register(ROPE_GRAPLING_HOOK, 0, new ModelResourceLocation(ROPE_GRAPLING_HOOK.getRegistryName(), inventory));
      mesher.register(PLASMA_RAILGUN_BOLTS, 0, new ModelResourceLocation(PLASMA_RAILGUN_BOLTS.getRegistryName(), inventory));
      mesher.register(AIM_LENS, 0, new ModelResourceLocation(AIM_LENS.getRegistryName(), inventory));
      mesher.register(FISH_FEED, 0, new ModelResourceLocation(FISH_FEED.getRegistryName(), inventory));
      mesher.register(VICIOUS_EMBLEM, 0, new ModelResourceLocation(VICIOUS_EMBLEM.getRegistryName(), inventory));
      mesher.register(ORB_OF_DESTROY, 0, new ModelResourceLocation(ORB_OF_DESTROY.getRegistryName(), inventory));
      mesher.register(WOODEN_SKIING, 0, new ModelResourceLocation(ORB_OF_DESTROY.getRegistryName(), inventory));
      mesher.register(VIAL_OF_POISON, 0, new ModelResourceLocation(VIAL_OF_POISON.getRegistryName(), inventory));
      mesher.register(VAMPIRIC_HEART, 0, new ModelResourceLocation(VAMPIRIC_HEART.getRegistryName(), inventory));
      mesher.register(FROZEN_WINGS, 0, new ModelResourceLocation(FROZEN_WINGS.getRegistryName(), inventory));
      mesher.register(TOXIC_WINGS, 0, new ModelResourceLocation(TOXIC_WINGS.getRegistryName(), inventory));
      mesher.register(GLACIDE_BLADE, 0, new ModelResourceLocation(GLACIDE_BLADE.getRegistryName(), inventory));
      mesher.register(INFERNAL_BLADE, 0, new ModelResourceLocation(INFERNAL_BLADE.getRegistryName(), inventory));
      mesher.register(CINDER_BOW, 0, new ModelResourceLocation(CINDER_BOW.getRegistryName(), inventory));
      mesher.register(INFERNUM_INGOT, 0, new ModelResourceLocation(INFERNUM_INGOT.getRegistryName(), inventory));
      mesher.register(MOLTEN_INGOT, 0, new ModelResourceLocation(MOLTEN_INGOT.getRegistryName(), inventory));
      mesher.register(INFERNUM_NUGGET, 0, new ModelResourceLocation(INFERNUM_NUGGET.getRegistryName(), inventory));
      mesher.register(MOLTEN_NUGGET, 0, new ModelResourceLocation(MOLTEN_NUGGET.getRegistryName(), inventory));
      mesher.register(MOLTEN_STRING, 0, new ModelResourceLocation(MOLTEN_STRING.getRegistryName(), inventory));
      mesher.register(LIQUID_FIRE, 0, new ModelResourceLocation(LIQUID_FIRE.getRegistryName(), inventory));
      mesher.register(DEMONITE, 0, new ModelResourceLocation(DEMONITE.getRegistryName(), inventory));
      mesher.register(DEMONITE_SHARD, 0, new ModelResourceLocation(DEMONITE_SHARD.getRegistryName(), inventory));
      mesher.register(RUBY, 0, new ModelResourceLocation(RUBY.getRegistryName(), inventory));
      mesher.register(SAPPHIRE, 0, new ModelResourceLocation(SAPPHIRE.getRegistryName(), inventory));
      mesher.register(CITRINE, 0, new ModelResourceLocation(CITRINE.getRegistryName(), inventory));
      mesher.register(AMETHYST, 0, new ModelResourceLocation(AMETHYST.getRegistryName(), inventory));
      mesher.register(TOPAZ, 0, new ModelResourceLocation(TOPAZ.getRegistryName(), inventory));
      mesher.register(RHINESTONE, 0, new ModelResourceLocation(RHINESTONE.getRegistryName(), inventory));
      mesher.register(MAGIC_POWDER, 0, new ModelResourceLocation(MAGIC_POWDER.getRegistryName(), inventory));
      mesher.register(ICE_GEM, 0, new ModelResourceLocation(ICE_GEM.getRegistryName(), inventory));
      mesher.register(WEATHER_FRAGMENTS, 0, new ModelResourceLocation(WEATHER_FRAGMENTS.getRegistryName(), inventory));
      mesher.register(SNOW_CLOTH, 0, new ModelResourceLocation(SNOW_CLOTH.getRegistryName(), inventory));
      mesher.register(CONIFER_STICK, 0, new ModelResourceLocation(CONIFER_STICK.getRegistryName(), inventory));
      mesher.register(SOUL_STONE, 0, new ModelResourceLocation(SOUL_STONE.getRegistryName(), inventory));
      mesher.register(SAPPHIRE_EYE, 0, new ModelResourceLocation(SAPPHIRE_EYE.getRegistryName(), inventory));
      mesher.register(INSTANT_ENCHANTMENT_BOOK, 0, new ModelResourceLocation(INSTANT_ENCHANTMENT_BOOK.getRegistryName(), inventory));
      mesher.register(THE_LORD_OF_PAIN, 0, new ModelResourceLocation(PHOENIX_GHOST_CAPE.getRegistryName(), inventory));
      mesher.register(CRYSTAL_FAN, 0, new ModelResourceLocation(RUBY.getRegistryName(), inventory));
      mesher.register(ANTIDOTE, 0, new ModelResourceLocation(ANTIDOTE.getRegistryName(), inventory));
      mesher.register(TOXI_COLA, 0, new ModelResourceLocation(TOXI_COLA.getRegistryName(), inventory));
      mesher.register(ANTI_POTION, 0, new ModelResourceLocation(ANTI_POTION.getRegistryName(), inventory));
      mesher.register(DECEIDUS_JUICE, 0, new ModelResourceLocation(DECEIDUS_JUICE.getRegistryName(), inventory));
      mesher.register(TOXEDGE_BREAD, 0, new ModelResourceLocation(TOXEDGE_BREAD.getRegistryName(), inventory));
      mesher.register(TOXIBERRY_MOJITO, 0, new ModelResourceLocation(TOXIBERRY_MOJITO.getRegistryName(), inventory));
      mesher.register(WASTE_BURGER, 0, new ModelResourceLocation(WASTE_BURGER.getRegistryName(), inventory));
      mesher.register(BROWN_SLIME_WAND, 0, new ModelResourceLocation(BROWN_SLIME_WAND.getRegistryName(), inventory));
      mesher.register(SLIME_BLOB_WAND, 0, new ModelResourceLocation(SLIME_BLOB_WAND.getRegistryName(), inventory));
      mesher.register(BONES_WAND, 0, new ModelResourceLocation(BONES_WAND.getRegistryName(), inventory));
      mesher.register(GLOWING_TOXIBERRY, 0, new ModelResourceLocation(GLOWING_TOXIBERRY.getRegistryName(), inventory));
      mesher.register(SMALL_TOXIBERRY, 0, new ModelResourceLocation(SMALL_TOXIBERRY.getRegistryName(), inventory));
      mesher.register(GOTHIC_PICKAXE, 0, new ModelResourceLocation(GOTHIC_PICKAXE.getRegistryName(), inventory));
      mesher.register(GOTHIC_AXE, 0, new ModelResourceLocation(GOTHIC_AXE.getRegistryName(), inventory));
      mesher.register(GOTHIC_SHOVEL, 0, new ModelResourceLocation(GOTHIC_SHOVEL.getRegistryName(), inventory));

      for (Item item : FOR_RENDER) {
         mesher.register(item, 0, new ModelResourceLocation(item.getRegistryName(), inventory));
      }
      
      mesher.register(ANNIHILATION_GUN, stack ->
              new ModelResourceLocation(ItemsRegister.ANNIHILATION_GUN.getRegistryName(), inventory));
      ANNIHILATION_GUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ELEMENT_FOCUS, stack ->
              new ModelResourceLocation(ItemsRegister.ELEMENT_FOCUS.getRegistryName(), inventory));
      ELEMENT_FOCUS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(STINGER, stack ->
              new ModelResourceLocation(ItemsRegister.STINGER.getRegistryName(), inventory));
      STINGER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(FIREWORK_LAUNCHER, stack ->
              new ModelResourceLocation(ItemsRegister.FIREWORK_LAUNCHER.getRegistryName(), inventory));
      FIREWORK_LAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SCEPTER_OF_SANDS, stack ->
              new ModelResourceLocation(ItemsRegister.SCEPTER_OF_SANDS.getRegistryName(), inventory));
      SCEPTER_OF_SANDS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(BILEBITER, stack ->
              new ModelResourceLocation(ItemsRegister.BILEBITER.getRegistryName(), inventory));
      BILEBITER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(FIREBALL_STAFF, stack ->
              new ModelResourceLocation(ItemsRegister.FIREBALL_STAFF.getRegistryName(), inventory));
      FIREBALL_STAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ELECTROSTATIC, stack ->
              new ModelResourceLocation(ItemsRegister.ELECTROSTATIC.getRegistryName(), inventory));
      ELECTROSTATIC.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SLIME_SHOTGUN, stack ->
              new ModelResourceLocation(ItemsRegister.SLIME_SHOTGUN.getRegistryName(), inventory));
      SLIME_SHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(MOLTEN_GREAT_AXE, stack ->
              new ModelResourceLocation(ItemsRegister.MOLTEN_GREAT_AXE.getRegistryName(), inventory));
      MOLTEN_GREAT_AXE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(NETHER_GRINDER, stack ->
              new ModelResourceLocation(ItemsRegister.NETHER_GRINDER.getRegistryName(), inventory));
      NETHER_GRINDER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SNOWBALL_CANNON, stack ->
              new ModelResourceLocation(ItemsRegister.SNOWBALL_CANNON.getRegistryName(), inventory));
      SNOWBALL_CANNON.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CURSED_BLADE, stack ->
              new ModelResourceLocation(ItemsRegister.CURSED_BLADE.getRegistryName(), inventory));
      CURSED_BLADE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(GRAVE_LURKER, stack ->
              new ModelResourceLocation(ItemsRegister.GRAVE_LURKER.getRegistryName(), inventory));
      GRAVE_LURKER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(REAPER, stack ->
              new ModelResourceLocation(ItemsRegister.REAPER.getRegistryName(), inventory));
      REAPER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(GHOST_SWORD, stack ->
              new ModelResourceLocation(ItemsRegister.GHOST_SWORD.getRegistryName(), inventory));
      GHOST_SWORD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(STAFF_OF_CORPSE, stack ->
              new ModelResourceLocation(ItemsRegister.STAFF_OF_CORPSE.getRegistryName(), inventory));
      STAFF_OF_CORPSE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(TOXIC_NUCLEAR_CANNON, stack ->
              new ModelResourceLocation(ItemsRegister.TOXIC_NUCLEAR_CANNON.getRegistryName(), inventory));
      TOXIC_NUCLEAR_CANNON.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CRYOGUN, stack ->
              new ModelResourceLocation(ItemsRegister.CRYOGUN.getRegistryName(), inventory));
      CRYOGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CHARGER, stack ->
              new ModelResourceLocation(ItemsRegister.CHARGER.getRegistryName(), inventory));
      CHARGER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ICICLE_MINIGUN, stack ->
              new ModelResourceLocation(ItemsRegister.ICICLE_MINIGUN.getRegistryName(), inventory));
      ICICLE_MINIGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(STATIC_LANCE, stack ->
              new ModelResourceLocation(ItemsRegister.STATIC_LANCE.getRegistryName(), inventory));
      STATIC_LANCE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(WAND_OF_BLAZES, stack ->
              new ModelResourceLocation(ItemsRegister.WAND_OF_BLAZES.getRegistryName(), inventory));
      WAND_OF_BLAZES.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CONIFER_ROD, stack ->
              new ModelResourceLocation(ItemsRegister.CONIFER_ROD.getRegistryName(), inventory));
      CONIFER_ROD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PLASMA_RAILGUN, stack ->
              new ModelResourceLocation(ItemsRegister.PLASMA_RAILGUN.getRegistryName(), inventory));
      PLASMA_RAILGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PLASMA_RIFLE, stack ->
              new ModelResourceLocation(ItemsRegister.PLASMA_RIFLE.getRegistryName(), inventory));
      PLASMA_RIFLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(HOLOGRAPHIC_SHIELD, stack ->
              new ModelResourceLocation(ItemsRegister.HOLOGRAPHIC_SHIELD.getRegistryName(), inventory));
      HOLOGRAPHIC_SHIELD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(HEAD_SHOOTER, stack ->
              new ModelResourceLocation(ItemsRegister.HEAD_SHOOTER.getRegistryName(), inventory));
      HEAD_SHOOTER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PLASMA_PISTOL, stack ->
              new ModelResourceLocation(ItemsRegister.PLASMA_PISTOL.getRegistryName(), inventory));
      PLASMA_PISTOL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(BOG_FLOWER, stack ->
              new ModelResourceLocation(ItemsRegister.BOG_FLOWER.getRegistryName(), inventory));
      BOG_FLOWER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PISTOL_FISH, stack ->
              new ModelResourceLocation(ItemsRegister.PISTOL_FISH.getRegistryName(), inventory));
      PISTOL_FISH.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(BUBBLEFISH, stack ->
              new ModelResourceLocation(ItemsRegister.BUBBLEFISH.getRegistryName(), inventory));
      BUBBLEFISH.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(LAVA_DROPPER, stack ->
              new ModelResourceLocation(ItemsRegister.LAVA_DROPPER.getRegistryName(), inventory));
      LAVA_DROPPER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PLASMA_ACCELERATOR, stack ->
              new ModelResourceLocation(ItemsRegister.PLASMA_ACCELERATOR.getRegistryName(), inventory));
      PLASMA_ACCELERATOR.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(VACUUM_GUN, stack ->
              new ModelResourceLocation(ItemsRegister.VACUUM_GUN.getRegistryName(), inventory));
      VACUUM_GUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(GEM_STAFF, stack ->
              new ModelResourceLocation(ItemsRegister.GEM_STAFF.getRegistryName(), inventory));
      GEM_STAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(WAND_OF_COLD, stack ->
              new ModelResourceLocation(ItemsRegister.WAND_OF_COLD.getRegistryName(), inventory));
      WAND_OF_COLD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ICE_BEAM, stack ->
              new ModelResourceLocation(ItemsRegister.ICE_BEAM.getRegistryName(), inventory));
      ICE_BEAM.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SKULL_CRASHER, stack ->
              new ModelResourceLocation(ItemsRegister.SKULL_CRASHER.getRegistryName(), inventory));
      SKULL_CRASHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SPELL_HAMMER, stack ->
              new ModelResourceLocation(ItemsRegister.SPELL_HAMMER.getRegistryName(), inventory));
      SPELL_HAMMER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ACID_FIRE, stack ->
              new ModelResourceLocation(ItemsRegister.ACID_FIRE.getRegistryName(), inventory));
      ACID_FIRE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(GLOW_BLADE, stack ->
              new ModelResourceLocation(ItemsRegister.GLOW_BLADE.getRegistryName(), inventory));
      GLOW_BLADE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(NAIL_GUN, stack ->
              new ModelResourceLocation(ItemsRegister.NAIL_GUN.getRegistryName(), inventory));
      NAIL_GUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CRYSTAL_STAR, stack ->
              new ModelResourceLocation(ItemsRegister.CRYSTAL_STAR.getRegistryName(), inventory));
      CRYSTAL_STAR.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(TOXINIUMSHOTGUN, stack ->
              new ModelResourceLocation(ItemsRegister.TOXINIUMSHOTGUN.getRegistryName(), inventory));
      TOXINIUMSHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SUBMACHINE, stack ->
              new ModelResourceLocation(ItemsRegister.SUBMACHINE.getRegistryName(), inventory));
      SUBMACHINE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(THISTLE_THORN, stack ->
              new ModelResourceLocation(ItemsRegister.THISTLE_THORN.getRegistryName(), inventory));
      THISTLE_THORN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(RESTLESS_SKULL, stack ->
              new ModelResourceLocation(ItemsRegister.RESTLESS_SKULL.getRegistryName(), inventory));
      RESTLESS_SKULL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(MAGIC_ROCKET, stack ->
              new ModelResourceLocation(ItemsRegister.MAGIC_ROCKET.getRegistryName(), inventory));
      MAGIC_ROCKET.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(STINGING_CELL, stack ->
              new ModelResourceLocation(ItemsRegister.STINGING_CELL.getRegistryName(), inventory));
      STINGING_CELL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SEA_EFFLORESCE, stack ->
              new ModelResourceLocation(ItemsRegister.SEA_EFFLORESCE.getRegistryName(), inventory));
      SEA_EFFLORESCE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CORAL_RIFLE, stack ->
              new ModelResourceLocation(ItemsRegister.CORAL_RIFLE.getRegistryName(), inventory));
      CORAL_RIFLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(HADRON_BLASTER, stack ->
              new ModelResourceLocation(ItemsRegister.HADRON_BLASTER.getRegistryName(), inventory));
      HADRON_BLASTER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SNAP_BALL, stack ->
              new ModelResourceLocation(ItemsRegister.SNAP_BALL.getRegistryName(), inventory));
      SNAP_BALL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ROCKET_LAUNCHER, stack ->
              new ModelResourceLocation(ItemsRegister.ROCKET_LAUNCHER.getRegistryName(), inventory));
      ROCKET_LAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CHAIN_MACE, stack ->
              new ModelResourceLocation(ItemsRegister.CHAIN_MACE.getRegistryName(), inventory));
      CHAIN_MACE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(DIAMOND_CHAIN_MACE, stack ->
              new ModelResourceLocation(ItemsRegister.DIAMOND_CHAIN_MACE.getRegistryName(), inventory));
      DIAMOND_CHAIN_MACE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(MOLTEN_CHAIN_MACE, stack ->
              new ModelResourceLocation(ItemsRegister.MOLTEN_CHAIN_MACE.getRegistryName(), inventory));
      MOLTEN_CHAIN_MACE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ICEBREAKER, stack ->
              new ModelResourceLocation(ItemsRegister.ICEBREAKER.getRegistryName(), inventory));
      ICEBREAKER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ADAMANTIUM_REVOLVER, stack ->
              new ModelResourceLocation(ItemsRegister.ADAMANTIUM_REVOLVER.getRegistryName(), inventory));
      ADAMANTIUM_REVOLVER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ADAMANTIUM_BATTLE_AXE, stack ->
              new ModelResourceLocation(ItemsRegister.ADAMANTIUM_BATTLE_AXE.getRegistryName(), inventory));
      ADAMANTIUM_BATTLE_AXE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ENDER_PROTECTOR, stack ->
              new ModelResourceLocation(ItemsRegister.ENDER_PROTECTOR.getRegistryName(), inventory));
      ENDER_PROTECTOR.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(DRAGON_TAIL, stack ->
              new ModelResourceLocation(ItemsRegister.DRAGON_TAIL.getRegistryName(), inventory));
      DRAGON_TAIL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(DRAGON_SHELL, stack ->
              new ModelResourceLocation(ItemsRegister.DRAGON_SHELL.getRegistryName(), inventory));
      DRAGON_SHELL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(WINTER_BREATH, stack ->
              new ModelResourceLocation(ItemsRegister.WINTER_BREATH.getRegistryName(), inventory));
      WINTER_BREATH.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(TOXINIUM_SHIELD, stack ->
              new ModelResourceLocation(ItemsRegister.TOXINIUM_SHIELD.getRegistryName(), inventory));
      TOXINIUM_SHIELD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CARAPACE, stack ->
              new ModelResourceLocation(ItemsRegister.CARAPACE.getRegistryName(), inventory));
      CARAPACE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ROTTEN_SHIELD, stack ->
              new ModelResourceLocation(ItemsRegister.ROTTEN_SHIELD.getRegistryName(), inventory));
      ROTTEN_SHIELD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(HELLMARK, stack ->
              new ModelResourceLocation(ItemsRegister.HELLMARK.getRegistryName(), inventory));
      HELLMARK.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(BLOWHOLE, stack ->
              new ModelResourceLocation(ItemsRegister.BLOWHOLE.getRegistryName(), inventory));
      BLOWHOLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CRYSTAL_CUTTER, stack ->
              new ModelResourceLocation(ItemsRegister.CRYSTAL_CUTTER.getRegistryName(), inventory));
      CRYSTAL_CUTTER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PLASMA_MINIGUN, stack ->
              new ModelResourceLocation(ItemsRegister.PLASMA_MINIGUN.getRegistryName(), inventory));
      PLASMA_MINIGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CERATARGET, stack ->
              new ModelResourceLocation(ItemsRegister.CERATARGET.getRegistryName(), inventory));
      CERATARGET.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(GRENADE_LAUNCHER, stack ->
              new ModelResourceLocation(ItemsRegister.GRENADE_LAUNCHER.getRegistryName(), inventory));
      GRENADE_LAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(HOLY_SHOTGUN, stack ->
              new ModelResourceLocation(ItemsRegister.HOLY_SHOTGUN.getRegistryName(), inventory));
      HOLY_SHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ECHINUS, stack ->
              new ModelResourceLocation(ItemsRegister.ECHINUS.getRegistryName(), inventory));
      ECHINUS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(MITHRIL_BOW, stack ->
              new ModelResourceLocation(ItemsRegister.MITHRIL_BOW.getRegistryName(), inventory));
      MITHRIL_BOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(COMPOUND_BOW, stack ->
              new ModelResourceLocation(ItemsRegister.COMPOUND_BOW.getRegistryName(), inventory));
      COMPOUND_BOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(AZURE_ORE_STAFF, stack ->
              new ModelResourceLocation(ItemsRegister.AZURE_ORE_STAFF.getRegistryName(), inventory));
      AZURE_ORE_STAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(INSTANCER, stack ->
              new ModelResourceLocation(ItemsRegister.INSTANCER.getRegistryName(), inventory));
      INSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ENDER_INSTANCER, stack ->
              new ModelResourceLocation(ItemsRegister.ENDER_INSTANCER.getRegistryName(), inventory));
      ENDER_INSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(WINTER_INSTANCER, stack ->
              new ModelResourceLocation(ItemsRegister.WINTER_INSTANCER.getRegistryName(), inventory));
      WINTER_INSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(MILITARY_INSTANCER, stack ->
              new ModelResourceLocation(ItemsRegister.MILITARY_INSTANCER.getRegistryName(), inventory));
      MILITARY_INSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(AQUATIC_INSTANCER, stack ->
              new ModelResourceLocation(ItemsRegister.AQUATIC_INSTANCER.getRegistryName(), inventory));
      AQUATIC_INSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(VIOLENCE, stack ->
              new ModelResourceLocation(ItemsRegister.VIOLENCE.getRegistryName(), inventory));
      VIOLENCE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(STAFF_OF_WITHERDRY, stack ->
              new ModelResourceLocation(ItemsRegister.STAFF_OF_WITHERDRY.getRegistryName(), inventory));
      STAFF_OF_WITHERDRY.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(WHISPERS_BLADE, stack ->
              new ModelResourceLocation(ItemsRegister.WHISPERS_BLADE.getRegistryName(), inventory));
      WHISPERS_BLADE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(VOLTRIDENT, stack ->
              new ModelResourceLocation(ItemsRegister.VOLTRIDENT.getRegistryName(), inventory));
      VOLTRIDENT.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(PUMP_SHOTGUN, stack ->
              new ModelResourceLocation(ItemsRegister.PUMP_SHOTGUN.getRegistryName(), inventory));
      PUMP_SHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(GOTHIC_BOW, stack ->
              new ModelResourceLocation(ItemsRegister.GOTHIC_BOW.getRegistryName(), inventory));
      GOTHIC_BOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CINDER_BOW, stack ->
              new ModelResourceLocation(ItemsRegister.CINDER_BOW.getRegistryName(), inventory));
      CINDER_BOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(COOLED_RIFLE, stack ->
              new ModelResourceLocation(ItemsRegister.COOLED_RIFLE.getRegistryName(), inventory));
      COOLED_RIFLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(AQUATIC_BOW, stack ->
              new ModelResourceLocation(ItemsRegister.AQUATIC_BOW.getRegistryName(), inventory));
      AQUATIC_BOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(BEAKER, stack ->
              new ModelResourceLocation(ItemsRegister.BEAKER.getRegistryName(), inventory));
      BEAKER.setTileEntityItemStackRenderer(TEISROther.instance);
      mesher.register(SPELL_PLIERS, stack ->
              new ModelResourceLocation(ItemsRegister.SPELL_PLIERS.getRegistryName(), inventory));
      SPELL_PLIERS.setTileEntityItemStackRenderer(TEISROther.instance);
      mesher.register(VIAL, stack ->
              new ModelResourceLocation(ItemsRegister.VIAL.getRegistryName(), inventory));
      VIAL.setTileEntityItemStackRenderer(TEISROther.instance);
      mesher.register(SPELL_ROLL, stack ->
              new ModelResourceLocation(ItemsRegister.SPELL_ROLL.getRegistryName(), inventory));
      SPELL_ROLL.setTileEntityItemStackRenderer(TEISROther.instance);
      mesher.register(XMASS_LAUNCHER, stack ->
              new ModelResourceLocation(ItemsRegister.XMASS_LAUNCHER.getRegistryName(), inventory));
      XMASS_LAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ADAMANTIUM_MINIGUN, stack ->
              new ModelResourceLocation(ItemsRegister.ADAMANTIUM_MINIGUN.getRegistryName(), inventory));
      ADAMANTIUM_MINIGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(BUZDYGAN, stack ->
              new ModelResourceLocation(ItemsRegister.BUZDYGAN.getRegistryName(), inventory));
      BUZDYGAN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(WHIP, stack ->
              new ModelResourceLocation(ItemsRegister.WHIP.getRegistryName(), inventory));
      WHIP.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(MAULER, stack ->
              new ModelResourceLocation(ItemsRegister.MAULER.getRegistryName(), inventory));
      MAULER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SNAKEWHIP, stack ->
              new ModelResourceLocation(ItemsRegister.SNAKEWHIP.getRegistryName(), inventory));
      SNAKEWHIP.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(SPELL_ROD, stack ->
              new ModelResourceLocation(ItemsRegister.SPELL_ROD.getRegistryName(), inventory));
      SPELL_ROD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CRYO_DESTROYER, stack ->
              new ModelResourceLocation(ItemsRegister.CRYO_DESTROYER.getRegistryName(), inventory));
      CRYO_DESTROYER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(HYDRAULIC_SHOTGUN, stack ->
              new ModelResourceLocation(ItemsRegister.HYDRAULIC_SHOTGUN.getRegistryName(), inventory));
      HYDRAULIC_SHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(ICE_COMPASS, stack ->
              new ModelResourceLocation(ItemsRegister.ICE_COMPASS.getRegistryName(), inventory));
      ICE_COMPASS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      mesher.register(CANISTER, stack ->
              new ModelResourceLocation(ItemsRegister.CANISTER.getRegistryName(), inventory));
      CANISTER.setTileEntityItemStackRenderer(TEISROther.instance);

      for (final Item item : ItemMagicScroll.magicScrolls.values()) {
         mesher.register(item, stack ->
              new ModelResourceLocation(item.getRegistryName(), inventory));
         item.setTileEntityItemStackRenderer(TEISRGuns.instance);
      }

      for (Item item : ItemGrenade.registry.values()) {
         mesher.register(item, stack ->
              new ModelResourceLocation(ItemsRegister.FRAG_GRENADE.getRegistryName(), inventory));
         item.setTileEntityItemStackRenderer(TEISRGuns.instance);
      }

      try {
         Field[] fields = ItemsRegister.class.getFields();

         for (Field field : fields) {
            if (field.getType() == Item.class) {
               final Item item = (Item)field.get(null);
               if (item instanceof ItemCalibrationThing || item instanceof AbstractMiningTool) {
                  mesher.register(item, stack ->
              new ModelResourceLocation(item.getRegistryName(), inventory));
                  item.setTileEntityItemStackRenderer(TEISROther.instance);
               }
            }
         }
      } catch (Exception e) {
         LOGGER.fatal(e);
      }
   }

   @SubscribeEvent
   public static void registerItems(RegistryEvent.Register<Item> event) throws IllegalAccessException {
      Field[] fields = ItemsRegister.class.getFields();
      List<Item> items = new ArrayList<>();

      for (Field field : fields) {
         if (field.getType() == Item.class) {
            Item item = (Item)field.get(new ItemsRegister());
            items.add(item);
         }
      }

      ItemMagicScroll.createScrolls();
      items.addAll(ItemMagicScroll.magicScrolls.values());

      event.getRegistry().registerAll(items.toArray(new Item[0]));

      BlocksRegister.setupAfterItems();
      ItemBullet.init();
      ItemRocket.init();
   }

}