package com.vivern.arpg.main;

import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.items.AbstractMiningTool;
import com.vivern.arpg.items.ItemCalibrationThing;
import com.vivern.arpg.items.ItemGrenade;
import com.vivern.arpg.items.ItemMagicScroll;
import com.vivern.arpg.items.models.*;
import com.vivern.arpg.renders.*;
import com.vivern.arpg.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import static com.vivern.arpg.main.ItemsRegister.*;

@SideOnly(Side.CLIENT)
public class RenderRegister {

    private static final Logger LOGGER = AbstractRPG.getLogger(RenderRegister.class.getSimpleName());

    @SuppressWarnings("ConstantConditions")
    public static void registerItemsRender() {
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        CreateItemFile.resLocationCreate(BIOFILTERING_MODULE);
        CreateItemFile.resLocationCreate(RICH_SCRAP);
        String inventory = "inventory";

        mesher.register(RICH_SCRAP, 0,
                new ModelResourceLocation(RICH_SCRAP.getRegistryName(), inventory));

        mesher.register(BIOFILTERING_MODULE, 0,
                new ModelResourceLocation(BIOFILTERING_MODULE.getRegistryName(), inventory));

        mesher.register(SWARMETER, 0,
                new ModelResourceLocation(SWARMETER.getRegistryName(), inventory));

        mesher.register(WIZARD_CLOTH, 0,
                new ModelResourceLocation(WIZARD_CLOTH.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_POISON_RAIN, 0,
                new ModelResourceLocation(WEATHER_ROCKET_POISON_RAIN.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_RAIN_FALL, 0,
                new ModelResourceLocation(WEATHER_ROCKET_RAIN_FALL.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_STORM, 0,
                new ModelResourceLocation(WEATHER_ROCKET_STORM.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_RAINS_TORM, 0,
                new ModelResourceLocation(WEATHER_ROCKET_RAINS_TORM.getRegistryName(), inventory));

        mesher.register(TOXINIUM_PICKAXE, 0,
                new ModelResourceLocation(TOXINIUM_PICKAXE.getRegistryName(), inventory));

        mesher.register(TOXINIUM_AXE, 0,
                new ModelResourceLocation(TOXINIUM_AXE.getRegistryName(), inventory));

        mesher.register(TOXINIUM_SHOVEL, 0,
                new ModelResourceLocation(TOXINIUM_SHOVEL.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_PICKAXE, 0,
                new ModelResourceLocation(ADAMANTIUM_PICKAXE.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_AXE, 0,
                new ModelResourceLocation(ADAMANTIUM_AXE.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_SHOVEL, 0,
                new ModelResourceLocation(ADAMANTIUM_SHOVEL.getRegistryName(), inventory));

        mesher.register(AQUATIC_PICKAXE, 0,
                new ModelResourceLocation(AQUATIC_PICKAXE.getRegistryName(), inventory));

        mesher.register(AQUATIC_AXE, 0,
                new ModelResourceLocation(AQUATIC_AXE.getRegistryName(), inventory));

        mesher.register(AQUATIC_SHOVEL, 0,
                new ModelResourceLocation(AQUATIC_SHOVEL.getRegistryName(), inventory));

        mesher.register(STORM_PICKAXE, 0,
                new ModelResourceLocation(STORM_PICKAXE.getRegistryName(), inventory));

        mesher.register(STORM_AXE, 0,
                new ModelResourceLocation(STORM_AXE.getRegistryName(), inventory));

        mesher.register(STORM_SHOVEL, 0,
                new ModelResourceLocation(STORM_SHOVEL.getRegistryName(), inventory));

        mesher.register(DOLERITE_KEY, 0,
                new ModelResourceLocation(DOLERITE_KEY.getRegistryName(), inventory));

        mesher.register(FIBER_BANDAGE, 0,
                new ModelResourceLocation(FIBER_BANDAGE.getRegistryName(), inventory));

        mesher.register(SELECTIVE_LEVITATOR, 0,
                new ModelResourceLocation(SELECTIVE_LEVITATOR.getRegistryName(), inventory));

        mesher.register(STORM_SPAWNER_PIECE, 0,
                new ModelResourceLocation(STORM_SPAWNER_PIECE.getRegistryName(), inventory));

        mesher.register(SACRIFICIAL_DAGGER, 0,
                new ModelResourceLocation(SACRIFICIAL_DAGGER.getRegistryName(), inventory));

        mesher.register(SIREN_KEY, 0,
                new ModelResourceLocation(SIREN_KEY.getRegistryName(), inventory));

        mesher.register(MERMAID_MEDALLION, 0,
                new ModelResourceLocation(MERMAID_MEDALLION.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_CLEAR, 0,
                new ModelResourceLocation(WEATHER_ROCKET_CLEAR.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_SNOWFALL, 0,
                new ModelResourceLocation(WEATHER_ROCKET_SNOWFALL.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET, 0,
                new ModelResourceLocation(WEATHER_ROCKET.getRegistryName(), inventory));

        mesher.register(WEATHER_ROCKET_AURORA, 0,
                new ModelResourceLocation(WEATHER_ROCKET_AURORA.getRegistryName(), inventory));

        mesher.register(PIRATE_SEXTANT, 0,
                new ModelResourceLocation(PIRATE_SEXTANT.getRegistryName(), inventory));

        mesher.register(AQUATIC_CIRCUIT, 0,
                new ModelResourceLocation(AQUATIC_CIRCUIT.getRegistryName(), inventory));

        mesher.register(TIDAL_HEART, 0,
                new ModelResourceLocation(TIDAL_HEART.getRegistryName(), inventory));

        mesher.register(STABILIZATION_CELL, 0,
                new ModelResourceLocation(STABILIZATION_CELL.getRegistryName(), inventory));

        mesher.register(SCRAP_BOMB, 0,
                new ModelResourceLocation(SCRAP_BOMB.getRegistryName(), inventory));

        mesher.register(HEALTHFUL_CAPSULE, 0,
                new ModelResourceLocation(HEALTHFUL_CAPSULE.getRegistryName(), inventory));

        mesher.register(GEIGER_COUNTER, 0,
                new ModelResourceLocation(GEIGER_COUNTER.getRegistryName(), inventory));

        mesher.register(THUNDERBIRD_FEATHER, 0,
                new ModelResourceLocation(THUNDERBIRD_FEATHER.getRegistryName(), inventory));

        mesher.register(PIZZA_SEAFOOD, 0,
                new ModelResourceLocation(PIZZA_SEAFOOD.getRegistryName(), inventory));

        mesher.register(PALE_MEAT_RAW, 0,
                new ModelResourceLocation(PALE_MEAT_RAW.getRegistryName(), inventory));

        mesher.register(PALE_MEAT_SMOKED, 0,
                new ModelResourceLocation(PALE_MEAT_SMOKED.getRegistryName(), inventory));

        mesher.register(FISH_STEAK_RAW, 0,
                new ModelResourceLocation(FISH_STEAK_RAW.getRegistryName(), inventory));

        mesher.register(FISH_STEAK_ROASTED, 0,
                new ModelResourceLocation(FISH_STEAK_ROASTED.getRegistryName(), inventory));

        mesher.register(BLACK_GOO, 0,
                new ModelResourceLocation(BLACK_GOO.getRegistryName(), inventory));

        mesher.register(FIBER_CLOTH, 0,
                new ModelResourceLocation(FIBER_CLOTH.getRegistryName(), inventory));

        mesher.register(ANTI_RAD_PLATING, 0,
                new ModelResourceLocation(ANTI_RAD_PLATING.getRegistryName(), inventory));

        mesher.register(PHASEOLITE, 0,
                new ModelResourceLocation(PHASEOLITE.getRegistryName(), inventory));

        mesher.register(ETHERITE_FUEL_CELL, 0,
                new ModelResourceLocation(ETHERITE_FUEL_CELL.getRegistryName(), inventory));

        mesher.register(VITREOUS_HEART, 0,
                new ModelResourceLocation(VITREOUS_HEART.getRegistryName(), inventory));

        mesher.register(SOLIDIFIED_LIGHTNING, 0,
                new ModelResourceLocation(SOLIDIFIED_LIGHTNING.getRegistryName(), inventory));

        mesher.register(KRAKEN_SKIN, 0,
                new ModelResourceLocation(KRAKEN_SKIN.getRegistryName(), inventory));

        mesher.register(WORSHIPPERS_BAIT, 0,
                new ModelResourceLocation(WORSHIPPERS_BAIT.getRegistryName(), inventory));

        mesher.register(FIN_WINGS, 0,
                new ModelResourceLocation(FIN_WINGS.getRegistryName(), inventory));

        mesher.register(ANCIENT_BATTERY, 0,
                new ModelResourceLocation(ANCIENT_BATTERY.getRegistryName(), inventory));

        mesher.register(AQUATRONIC_BATTERY, 0,
                new ModelResourceLocation(AQUATRONIC_BATTERY.getRegistryName(), inventory));

        mesher.register(ANCIENT_SPAWNER_PIECE, 0,
                new ModelResourceLocation(ANCIENT_SPAWNER_PIECE.getRegistryName(), inventory));

        mesher.register(EYE_OF_BEHOLDER, 0,
                new ModelResourceLocation(EYE_OF_BEHOLDER.getRegistryName(), inventory));

        mesher.register(THUNDERBIRD_WINGS, 0,
                new ModelResourceLocation(THUNDERBIRD_WINGS.getRegistryName(), inventory));

        mesher.register(WINDKEEPER, 0,
                new ModelResourceLocation(WINDKEEPER.getRegistryName(), inventory));

        mesher.register(SHIPWREAKERS_KNOT, 0,
                new ModelResourceLocation(SHIPWREAKERS_KNOT.getRegistryName(), inventory));

        mesher.register(WHITEWIND_BELT, 0,
                new ModelResourceLocation(WHITEWIND_BELT.getRegistryName(), inventory));

        mesher.register(BELT_OF_SHADOWS, 0,
                new ModelResourceLocation(BELT_OF_SHADOWS.getRegistryName(), inventory));

        mesher.register(EREBRIS_SHARD, 0,
                new ModelResourceLocation(EREBRIS_SHARD.getRegistryName(), inventory));

        mesher.register(EREBRIS_FRAGMENT, 0,
                new ModelResourceLocation(EREBRIS_FRAGMENT.getRegistryName(), inventory));

        mesher.register(EREBRIS_CHUNK, 0,
                new ModelResourceLocation(EREBRIS_CHUNK.getRegistryName(), inventory));

        mesher.register(SILICIUM, 0,
                new ModelResourceLocation(SILICIUM.getRegistryName(), inventory));

        mesher.register(SILICIUM_WAFER, 0,
                new ModelResourceLocation(SILICIUM_WAFER.getRegistryName(), inventory));

        mesher.register(PHOTORESISTED_PLATE, 0,
                new ModelResourceLocation(PHOTORESISTED_PLATE.getRegistryName(), inventory));

        mesher.register(LITOGRAPHED_PLATE, 0,
                new ModelResourceLocation(LITOGRAPHED_PLATE.getRegistryName(), inventory));

        mesher.register(GALVANIZED_PLATE, 0,
                new ModelResourceLocation(GALVANIZED_PLATE.getRegistryName(), inventory));

        mesher.register(LEPIDOLITE, 0,
                new ModelResourceLocation(LEPIDOLITE.getRegistryName(), inventory));

        mesher.register(LITHIUM_NUGGET, 0,
                new ModelResourceLocation(LITHIUM_NUGGET.getRegistryName(), inventory));

        mesher.register(LITHIUM_INGOT, 0,
                new ModelResourceLocation(LITHIUM_INGOT.getRegistryName(), inventory));

        mesher.register(LITHIUM_DUST, 0,
                new ModelResourceLocation(LITHIUM_DUST.getRegistryName(), inventory));

        mesher.register(VACUUM_GUN_PELLETS, 0,
                new ModelResourceLocation(VACUUM_GUN_PELLETS.getRegistryName(), inventory));

        mesher.register(LOW_FRICTION_BEARING, 0,
                new ModelResourceLocation(LOW_FRICTION_BEARING.getRegistryName(), inventory));

        mesher.register(CHARM_OF_UNDYING, 0,
                new ModelResourceLocation(CHARM_OF_UNDYING.getRegistryName(), inventory));

        mesher.register(GOTHIC_GEM, 0,
                new ModelResourceLocation(GOTHIC_GEM.getRegistryName(), inventory));

        mesher.register(HYDRAULIC_SHOTGUN_CLIP, 0,
                new ModelResourceLocation(HYDRAULIC_SHOTGUN_CLIP.getRegistryName(), inventory));

        mesher.register(GOTHIC_GEAR, 0,
                new ModelResourceLocation(GOTHIC_GEAR.getRegistryName(), inventory));

        mesher.register(ICE_CIRCLE, 0,
                new ModelResourceLocation(ICE_CIRCLE.getRegistryName(), inventory));

        mesher.register(ICE_CIRCLE_FILLED, 0,
                new ModelResourceLocation(ICE_CIRCLE_FILLED.getRegistryName(), inventory));

        mesher.register(HEALTH_FRUIT, 0,
                new ModelResourceLocation(HEALTH_FRUIT.getRegistryName(), inventory));

        mesher.register(MANA_EXPANSION_POTION, 0,
                new ModelResourceLocation(MANA_EXPANSION_POTION.getRegistryName(), inventory));

        mesher.register(RUBBLESTONE, 0,
                new ModelResourceLocation(RUBBLESTONE.getRegistryName(), inventory));

        mesher.register(SALTPETER, 0,
                new ModelResourceLocation(SALTPETER.getRegistryName(), inventory));

        mesher.register(ASH, 0,
                new ModelResourceLocation(ASH.getRegistryName(), inventory));

        mesher.register(SLIME_LOCATOR, 0,
                new ModelResourceLocation(SLIME_LOCATOR.getRegistryName(), inventory));

        mesher.register(RHINESTONE_PICKAXE, 0,
                new ModelResourceLocation(RHINESTONE_PICKAXE.getRegistryName(), inventory));

        mesher.register(RHINESTONE_AXE, 0,
                new ModelResourceLocation(RHINESTONE_AXE.getRegistryName(), inventory));

        mesher.register(RHINESTONE_SHOVEL, 0,
                new ModelResourceLocation(RHINESTONE_SHOVEL.getRegistryName(), inventory));

        mesher.register(TOPAZ_PICKAXE, 0,
                new ModelResourceLocation(TOPAZ_PICKAXE.getRegistryName(), inventory));

        mesher.register(TOPAZ_AXE, 0,
                new ModelResourceLocation(TOPAZ_AXE.getRegistryName(), inventory));

        mesher.register(TOPAZ_SHOVEL, 0,
                new ModelResourceLocation(TOPAZ_SHOVEL.getRegistryName(), inventory));

        mesher.register(SAPPHIRE_PICKAXE, 0,
                new ModelResourceLocation(SAPPHIRE_PICKAXE.getRegistryName(), inventory));

        mesher.register(SAPPHIRE_AXE, 0,
                new ModelResourceLocation(SAPPHIRE_AXE.getRegistryName(), inventory));

        mesher.register(SAPPHIRE_SHOVEL, 0,
                new ModelResourceLocation(SAPPHIRE_SHOVEL.getRegistryName(), inventory));

        mesher.register(AMETHYST_PICKAXE, 0,
                new ModelResourceLocation(AMETHYST_PICKAXE.getRegistryName(), inventory));

        mesher.register(AMETHYST_AXE, 0,
                new ModelResourceLocation(AMETHYST_AXE.getRegistryName(), inventory));

        mesher.register(AMETHYST_SHOVEL, 0,
                new ModelResourceLocation(AMETHYST_SHOVEL.getRegistryName(), inventory));

        mesher.register(CITRINE_PICKAXE, 0,
                new ModelResourceLocation(CITRINE_PICKAXE.getRegistryName(), inventory));

        mesher.register(CITRINE_AXE, 0,
                new ModelResourceLocation(CITRINE_AXE.getRegistryName(), inventory));

        mesher.register(CITRINE_SHOVEL, 0,
                new ModelResourceLocation(CITRINE_SHOVEL.getRegistryName(), inventory));

        mesher.register(RUBY_PICKAXE, 0,
                new ModelResourceLocation(RUBY_PICKAXE.getRegistryName(), inventory));

        mesher.register(RUBY_AXE, 0,
                new ModelResourceLocation(RUBY_AXE.getRegistryName(), inventory));

        mesher.register(RUBY_SHOVEL, 0,
                new ModelResourceLocation(RUBY_SHOVEL.getRegistryName(), inventory));

        mesher.register(MISSING_DUST, 0,
                new ModelResourceLocation(MISSING_DUST.getRegistryName(), inventory));

        mesher.register(MISSING_INGOT, 0,
                new ModelResourceLocation(MISSING_INGOT.getRegistryName(), inventory));

        mesher.register(MISSING_NUGGET, 0,
                new ModelResourceLocation(MISSING_NUGGET.getRegistryName(), inventory));

        mesher.register(MISSING_MATERIAL, 0,
                new ModelResourceLocation(MISSING_MATERIAL.getRegistryName(), inventory));

        mesher.register(INK, 0,
                new ModelResourceLocation(INK.getRegistryName(), inventory));

        mesher.register(QUARTZ_DUST, 0,
                new ModelResourceLocation(QUARTZ_DUST.getRegistryName(), inventory));

        mesher.register(EMPTY_SYRINGE, 0,
                new ModelResourceLocation(EMPTY_SYRINGE.getRegistryName(), inventory));

        mesher.register(PYROLYSIS_MODULE, 0,
                new ModelResourceLocation(PYROLYSIS_MODULE.getRegistryName(), inventory));

        mesher.register(PURPUR_ALLOY, 0,
                new ModelResourceLocation(PURPUR_ALLOY.getRegistryName(), inventory));

        mesher.register(BLACK_STRAP, 0,
                new ModelResourceLocation(BLACK_STRAP.getRegistryName(), inventory));

        mesher.register(TITANIUM_SLAG, 0,
                new ModelResourceLocation(TITANIUM_SLAG.getRegistryName(), inventory));

        mesher.register(AQUATIC_SPAWNER_PIECE, 0,
                new ModelResourceLocation(AQUATIC_SPAWNER_PIECE.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_ORE_DUST, 0,
                new ModelResourceLocation(ADAMANTIUM_ORE_DUST.getRegistryName(), inventory));

        mesher.register(MITHRIL_ORE_DUST, 0,
                new ModelResourceLocation(MITHRIL_ORE_DUST.getRegistryName(), inventory));

        mesher.register(STONE_DUST, 0,
                new ModelResourceLocation(STONE_DUST.getRegistryName(), inventory));

        mesher.register(LIMESTONE_DUST, 0,
                new ModelResourceLocation(LIMESTONE_DUST.getRegistryName(), inventory));

        mesher.register(BASALT_DUST, 0,
                new ModelResourceLocation(BASALT_DUST.getRegistryName(), inventory));

        mesher.register(URANIUM_INGOT, 0,
                new ModelResourceLocation(URANIUM_INGOT.getRegistryName(), inventory));

        mesher.register(URANIUM_DUST, 0,
                new ModelResourceLocation(URANIUM_DUST.getRegistryName(), inventory));

        mesher.register(URANIUM_NUGGET, 0,
                new ModelResourceLocation(URANIUM_NUGGET.getRegistryName(), inventory));

        mesher.register(TOXINIUM_ORE_DUST, 0,
                new ModelResourceLocation(TOXINIUM_ORE_DUST.getRegistryName(), inventory));

        mesher.register(RADIOACTIVE_DUST, 0,
                new ModelResourceLocation(RADIOACTIVE_DUST.getRegistryName(), inventory));

        mesher.register(NYLON, 0,
                new ModelResourceLocation(NYLON.getRegistryName(), inventory));

        mesher.register(TAR, 0,
                new ModelResourceLocation(TAR.getRegistryName(), inventory));

        mesher.register(PARAFFIN, 0,
                new ModelResourceLocation(PARAFFIN.getRegistryName(), inventory));

        mesher.register(POLYMERIZATION_MODULE, 0,
                new ModelResourceLocation(POLYMERIZATION_MODULE.getRegistryName(), inventory));

        mesher.register(DISTILLATION_MODULE, 0,
                new ModelResourceLocation(DISTILLATION_MODULE.getRegistryName(), inventory));

        mesher.register(RESISTANT_CIRCUIT, 0,
                new ModelResourceLocation(RESISTANT_CIRCUIT.getRegistryName(), inventory));

        mesher.register(GOLD_TRANSFORMER, 0,
                new ModelResourceLocation(GOLD_TRANSFORMER.getRegistryName(), inventory));

        mesher.register(SILVER_TRANSFORMER, 0,
                new ModelResourceLocation(SILVER_TRANSFORMER.getRegistryName(), inventory));

        mesher.register(TOPAZITRON_CRYSTAL, 0,
                new ModelResourceLocation(TOPAZITRON_CRYSTAL.getRegistryName(), inventory));

        mesher.register(BATTERY_TOPAZITRON_CRYSTAL, 0,
                new ModelResourceLocation(BATTERY_TOPAZITRON_CRYSTAL.getRegistryName(), inventory));

        mesher.register(ELECTROMAGNETIC_BEARING, 0,
                new ModelResourceLocation(ELECTROMAGNETIC_BEARING.getRegistryName(), inventory));

        mesher.register(BLUE_ARTHROSTELECHA_ROD, 0,
                new ModelResourceLocation(BLUE_ARTHROSTELECHA_ROD.getRegistryName(), inventory));

        mesher.register(PINK_ARTHROSTELECHA_ROD, 0,
                new ModelResourceLocation(PINK_ARTHROSTELECHA_ROD.getRegistryName(), inventory));

        mesher.register(IMPULSE_THRUSTER, 0,
                new ModelResourceLocation(IMPULSE_THRUSTER.getRegistryName(), inventory));

        mesher.register(STORM_CIRCUIT, 0,
                new ModelResourceLocation(STORM_CIRCUIT.getRegistryName(), inventory));

        mesher.register(STORMBRASS_PLASMATRON, 0,
                new ModelResourceLocation(STORMBRASS_PLASMATRON.getRegistryName(), inventory));

        mesher.register(MITHRIL_INGOT, 0,
                new ModelResourceLocation(MITHRIL_INGOT.getRegistryName(), inventory));

        mesher.register(MITHRIL, 0,
                new ModelResourceLocation(MITHRIL.getRegistryName(), inventory));

        mesher.register(MITHRIL_NUGGET, 0,
                new ModelResourceLocation(MITHRIL_NUGGET.getRegistryName(), inventory));

        mesher.register(AQUATIC_NUGGET, 0,
                new ModelResourceLocation(AQUATIC_NUGGET.getRegistryName(), inventory));

        mesher.register(PEARL, 0,
                new ModelResourceLocation(PEARL.getRegistryName(), inventory));

        mesher.register(BLACK_PEARL, 0,
                new ModelResourceLocation(BLACK_PEARL.getRegistryName(), inventory));

        mesher.register(GLOWING_PEARL, 0,
                new ModelResourceLocation(GLOWING_PEARL.getRegistryName(), inventory));

        mesher.register(AQUATIC_PEARL, 0,
                new ModelResourceLocation(AQUATIC_PEARL.getRegistryName(), inventory));

        mesher.register(AQUATIC_INGOT, 0,
                new ModelResourceLocation(AQUATIC_INGOT.getRegistryName(), inventory));

        mesher.register(AQUATIC_DUST, 0,
                new ModelResourceLocation(AQUATIC_DUST.getRegistryName(), inventory));

        mesher.register(CORAL, 0,
                new ModelResourceLocation(CORAL.getRegistryName(), inventory));

        mesher.register(ARCHELON_SHELL, 0,
                new ModelResourceLocation(ARCHELON_SHELL.getRegistryName(), inventory));

        mesher.register(PLACODERM_SCALES, 0,
                new ModelResourceLocation(PLACODERM_SCALES.getRegistryName(), inventory));

        mesher.register(MESOGLEA, 0,
                new ModelResourceLocation(MESOGLEA.getRegistryName(), inventory));

        mesher.register(TOXEDGE_DOUGH, 0,
                new ModelResourceLocation(TOXEDGE_DOUGH.getRegistryName(), inventory));

        mesher.register(BUTTER, 0,
                new ModelResourceLocation(BUTTER.getRegistryName(), inventory));

        mesher.register(BISCUIT, 0,
                new ModelResourceLocation(BISCUIT.getRegistryName(), inventory));

        mesher.register(SWEET_DOUGH, 0,
                new ModelResourceLocation(SWEET_DOUGH.getRegistryName(), inventory));

        mesher.register(COCOA_POWDER, 0,
                new ModelResourceLocation(COCOA_POWDER.getRegistryName(), inventory));

        mesher.register(CARAMEL, 0,
                new ModelResourceLocation(CARAMEL.getRegistryName(), inventory));

        mesher.register(CHOCOLATE, 0,
                new ModelResourceLocation(CHOCOLATE.getRegistryName(), inventory));

        mesher.register(COCOA_BUTTER, 0,
                new ModelResourceLocation(COCOA_BUTTER.getRegistryName(), inventory));

        mesher.register(VIOLET_PUDDING, 0,
                new ModelResourceLocation(VIOLET_PUDDING.getRegistryName(), inventory));

        mesher.register(GREEN_PUDDING, 0,
                new ModelResourceLocation(GREEN_PUDDING.getRegistryName(), inventory));

        mesher.register(BROWN_PUDDING, 0,
                new ModelResourceLocation(BROWN_PUDDING.getRegistryName(), inventory));

        mesher.register(ORANGE_PUDDING, 0,
                new ModelResourceLocation(ORANGE_PUDDING.getRegistryName(), inventory));

        mesher.register(FLOUR, 0,
                new ModelResourceLocation(FLOUR.getRegistryName(), inventory));

        mesher.register(MEAT_BROTH, 0,
                new ModelResourceLocation(MEAT_BROTH.getRegistryName(), inventory));

        mesher.register(STUFFED_FIERY_BEAN, 0,
                new ModelResourceLocation(STUFFED_FIERY_BEAN.getRegistryName(), inventory));

        mesher.register(BORSCH, 0,
                new ModelResourceLocation(BORSCH.getRegistryName(), inventory));

        mesher.register(FERMENTER_MODULE, 0,
                new ModelResourceLocation(FERMENTER_MODULE.getRegistryName(), inventory));

        mesher.register(CENTRIFUGE_MODULE, 0,
                new ModelResourceLocation(CENTRIFUGE_MODULE.getRegistryName(), inventory));

        mesher.register(CHERRY_TOMATO, 0,
                new ModelResourceLocation(CHERRY_TOMATO.getRegistryName(), inventory));

        mesher.register(MAGIC_JAM, 0,
                new ModelResourceLocation(MAGIC_JAM.getRegistryName(), inventory));

        mesher.register(DOUGH, 0,
                new ModelResourceLocation(DOUGH.getRegistryName(), inventory));

        mesher.register(MOZZARELLA, 0,
                new ModelResourceLocation(MOZZARELLA.getRegistryName(), inventory));

        mesher.register(MUSHROOMS_IN_SAUCE, 0,
                new ModelResourceLocation(MUSHROOMS_IN_SAUCE.getRegistryName(), inventory));

        mesher.register(WHEY_STARTER, 0,
                new ModelResourceLocation(WHEY_STARTER.getRegistryName(), inventory));

        mesher.register(YEAST, 0,
                new ModelResourceLocation(YEAST.getRegistryName(), inventory));

        mesher.register(MOONSHROOM_MEAT, 0,
                new ModelResourceLocation(MOONSHROOM_MEAT.getRegistryName(), inventory));

        mesher.register(SALT_GRAINS, 0,
                new ModelResourceLocation(SALT_GRAINS.getRegistryName(), inventory));

        mesher.register(REDPEPPER, 0,
                new ModelResourceLocation(REDPEPPER.getRegistryName(), inventory));

        mesher.register(HEALTH_BERRY, 0,
                new ModelResourceLocation(HEALTH_BERRY.getRegistryName(), inventory));

        mesher.register(QUANTUM_SLIMEBALL, 0,
                new ModelResourceLocation(QUANTUM_SLIMEBALL.getRegistryName(), inventory));

        mesher.register(MANA_BERRY, 0,
                new ModelResourceLocation(MANA_BERRY.getRegistryName(), inventory));

        mesher.register(ALCHEMICAL_WAX, 0,
                new ModelResourceLocation(ALCHEMICAL_WAX.getRegistryName(), inventory));

        mesher.register(ENIGMATE_TWINS, 0,
                new ModelResourceLocation(ENIGMATE_TWINS.getRegistryName(), inventory));

        mesher.register(BULLET_DIVING, 0,
                new ModelResourceLocation(BULLET_DIVING.getRegistryName(), inventory));

        mesher.register(BULLET_CORAL, 0,
                new ModelResourceLocation(BULLET_CORAL.getRegistryName(), inventory));

        mesher.register(SHELL_ROCKET, 0,
                new ModelResourceLocation(SHELL_ROCKET.getRegistryName(), inventory));

        mesher.register(BULLET_ERRATIC, 0,
                new ModelResourceLocation(BULLET_ERRATIC.getRegistryName(), inventory));

        mesher.register(SURPRISE_ROCKET, 0,
                new ModelResourceLocation(SURPRISE_ROCKET.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_MINIGUN_CLIP, 0,
                new ModelResourceLocation(ADAMANTIUM_MINIGUN_CLIP.getRegistryName(), inventory));

        mesher.register(XMASS_BUNDLE, 0,
                new ModelResourceLocation(XMASS_BUNDLE.getRegistryName(), inventory));

        mesher.register(ELEMENTS_BOOK, 0,
                new ModelResourceLocation(ELEMENTS_BOOK.getRegistryName(), inventory));

        mesher.register(VIAL_EMPTY, 0,
                new ModelResourceLocation(VIAL_EMPTY.getRegistryName(), inventory));

        mesher.register(DASH_IMPULSE_CORSLET, 0,
                new ModelResourceLocation(DASH_IMPULSE_CORSLET.getRegistryName(), inventory));

        mesher.register(DASH_HELLHOUND_BELT, 0,
                new ModelResourceLocation(DASH_HELLHOUND_BELT.getRegistryName(), inventory));

        mesher.register(DASH_BELT_BLACK, 0,
                new ModelResourceLocation(DASH_BELT_BLACK.getRegistryName(), inventory));

        mesher.register(SHADOW_WINGS, 0,
                new ModelResourceLocation(SHADOW_WINGS.getRegistryName(), inventory));

        mesher.register(VIAL_FIRE, 0,
                new ModelResourceLocation(VIAL_FIRE.getRegistryName(), inventory));

        mesher.register(VIAL_EARTH, 0,
                new ModelResourceLocation(VIAL_EARTH.getRegistryName(), inventory));

        mesher.register(VIAL_WATER, 0,
                new ModelResourceLocation(VIAL_WATER.getRegistryName(), inventory));

        mesher.register(VIAL_AIR, 0,
                new ModelResourceLocation(VIAL_AIR.getRegistryName(), inventory));

        mesher.register(VIAL_POISON, 0,
                new ModelResourceLocation(VIAL_POISON.getRegistryName(), inventory));

        mesher.register(VIAL_COLD, 0,
                new ModelResourceLocation(VIAL_COLD.getRegistryName(), inventory));

        mesher.register(VIAL_ELECTRIC, 0,
                new ModelResourceLocation(VIAL_ELECTRIC.getRegistryName(), inventory));

        mesher.register(VIAL_VOID, 0,
                new ModelResourceLocation(VIAL_VOID.getRegistryName(), inventory));

        mesher.register(VIAL_PLEASURE, 0,
                new ModelResourceLocation(VIAL_PLEASURE.getRegistryName(), inventory));

        mesher.register(VIAL_PAIN, 0,
                new ModelResourceLocation(VIAL_PAIN.getRegistryName(), inventory));

        mesher.register(VIAL_DEATH, 0,
                new ModelResourceLocation(VIAL_DEATH.getRegistryName(), inventory));

        mesher.register(VIAL_LIVE, 0,
                new ModelResourceLocation(VIAL_LIVE.getRegistryName(), inventory));

        mesher.register(MAGIC_EXPLORING_KIT, 0,
                new ModelResourceLocation(MAGIC_EXPLORING_KIT.getRegistryName(), inventory));

        mesher.register(MAGIC_RESEARCH_KIT, 0,
                new ModelResourceLocation(MAGIC_RESEARCH_KIT.getRegistryName(), inventory));

        mesher.register(MAGIC_WRITING_KIT, 0,
                new ModelResourceLocation(MAGIC_WRITING_KIT.getRegistryName(), inventory));

        mesher.register(COOLED_RIFLE_CLIP, 0,
                new ModelResourceLocation(COOLED_RIFLE_CLIP.getRegistryName(), inventory));

        mesher.register(BULLET_NIVEOUS, 0,
                new ModelResourceLocation(BULLET_NIVEOUS.getRegistryName(), inventory));

        mesher.register(NIVEOLITE, 0,
                new ModelResourceLocation(NIVEOLITE.getRegistryName(), inventory));

        mesher.register(HELLHOUND_FUR, 0,
                new ModelResourceLocation(HELLHOUND_FUR.getRegistryName(), inventory));

        mesher.register(FROZEN_SPAWNER_PIECE, 0,
                new ModelResourceLocation(FROZEN_SPAWNER_PIECE.getRegistryName(), inventory));

        mesher.register(RUSTED_SPAWNER_PIECE, 0,
                new ModelResourceLocation(RUSTED_SPAWNER_PIECE.getRegistryName(), inventory));

        mesher.register(SPAWNER_PIECE, 0,
                new ModelResourceLocation(SPAWNER_PIECE.getRegistryName(), inventory));

        mesher.register(SLIMY_EGGS, 0,
                new ModelResourceLocation(SLIMY_EGGS.getRegistryName(), inventory));

        mesher.register(BERYLLIUM_GRAINS, 0,
                new ModelResourceLocation(BERYLLIUM_GRAINS.getRegistryName(), inventory));

        mesher.register(ELECTROLYZER_MODULE, 0,
                new ModelResourceLocation(ELECTROLYZER_MODULE.getRegistryName(), inventory));

        mesher.register(ALUMINIUM_INGOT, 0,
                new ModelResourceLocation(ALUMINIUM_INGOT.getRegistryName(), inventory));

        mesher.register(ALUMINIUM_DUST, 0,
                new ModelResourceLocation(ALUMINIUM_DUST.getRegistryName(), inventory));

        mesher.register(ALUMINIUM_NUGGET, 0,
                new ModelResourceLocation(ALUMINIUM_NUGGET.getRegistryName(), inventory));

        mesher.register(BEARING_ALLOY_DUST, 0,
                new ModelResourceLocation(BEARING_ALLOY_DUST.getRegistryName(), inventory));

        mesher.register(COPPER_TRANSFORMER, 0,
                new ModelResourceLocation(COPPER_TRANSFORMER.getRegistryName(), inventory));

        mesher.register(STAMP_MOLD, 0,
                new ModelResourceLocation(STAMP_MOLD.getRegistryName(), inventory));

        mesher.register(STEEL_STAMP, 0,
                new ModelResourceLocation(STEEL_STAMP.getRegistryName(), inventory));

        mesher.register(SILVER_INGOT, 0,
                new ModelResourceLocation(SILVER_INGOT.getRegistryName(), inventory));

        mesher.register(SILVER_DUST, 0,
                new ModelResourceLocation(SILVER_DUST.getRegistryName(), inventory));

        mesher.register(SILVER_NUGGET, 0,
                new ModelResourceLocation(SILVER_NUGGET.getRegistryName(), inventory));

        mesher.register(ARROW_WIND, 0,
                new ModelResourceLocation(ARROW_WIND.getRegistryName(), inventory));

        mesher.register(ARROW_TWIN, 0,
                new ModelResourceLocation(ARROW_TWIN.getRegistryName(), inventory));

        mesher.register(ARROW_MITHRIL, 0,
                new ModelResourceLocation(ARROW_MITHRIL.getRegistryName(), inventory));

        mesher.register(ARROW_BOUNCING, 0,
                new ModelResourceLocation(ARROW_BOUNCING.getRegistryName(), inventory));

        mesher.register(ARROW_SHELL, 0,
                new ModelResourceLocation(ARROW_SHELL.getRegistryName(), inventory));

        mesher.register(ARROW_VOID, 0,
                new ModelResourceLocation(ARROW_VOID.getRegistryName(), inventory));

        mesher.register(ARROW_FISH, 0,
                new ModelResourceLocation(ARROW_FISH.getRegistryName(), inventory));

        mesher.register(ARROW_BENGAL, 0,
                new ModelResourceLocation(ARROW_BENGAL.getRegistryName(), inventory));

        mesher.register(ARROW_MODERN, 0,
                new ModelResourceLocation(ARROW_MODERN.getRegistryName(), inventory));

        mesher.register(ARROW_VICIOUS, 0,
                new ModelResourceLocation(ARROW_VICIOUS.getRegistryName(), inventory));

        mesher.register(ARROW_FIREJET, 0,
                new ModelResourceLocation(ARROW_FIREJET.getRegistryName(), inventory));

        mesher.register(ARROW_FROZEN, 0,
                new ModelResourceLocation(ARROW_FROZEN.getRegistryName(), inventory));

        mesher.register(TIDE_ACTIVATOR_1, 0,
                new ModelResourceLocation(TIDE_ACTIVATOR_1.getRegistryName(), inventory));

        mesher.register(TIDE_ACTIVATOR_2, 0,
                new ModelResourceLocation(TIDE_ACTIVATOR_2.getRegistryName(), inventory));

        mesher.register(TIDE_ACTIVATOR_3, 0,
                new ModelResourceLocation(TIDE_ACTIVATOR_3.getRegistryName(), inventory));

        mesher.register(TIDE_ACTIVATOR_4, 0,
                new ModelResourceLocation(TIDE_ACTIVATOR_4.getRegistryName(), inventory));

        mesher.register(TIDE_ACTIVATOR_5, 0,
                new ModelResourceLocation(TIDE_ACTIVATOR_5.getRegistryName(), inventory));

        mesher.register(ARCANE_ROCKET, 0,
                new ModelResourceLocation(ARCANE_ROCKET.getRegistryName(), inventory));

        mesher.register(BUCKSHOT, 0,
                new ModelResourceLocation(BUCKSHOT.getRegistryName(), inventory));

        mesher.register(NORTHERN_HELM, 0,
                new ModelResourceLocation(NORTHERN_HELM.getRegistryName(), inventory));

        mesher.register(NORTHERN_CHEST, 0,
                new ModelResourceLocation(NORTHERN_CHEST.getRegistryName(), inventory));

        mesher.register(NORTHERN_LEGS, 0,
                new ModelResourceLocation(NORTHERN_LEGS.getRegistryName(), inventory));

        mesher.register(NORTHERN_BOOTS, 0,
                new ModelResourceLocation(NORTHERN_BOOTS.getRegistryName(), inventory));

        mesher.register(CRYSTAL_HELM, 0,
                new ModelResourceLocation(CRYSTAL_HELM.getRegistryName(), inventory));

        mesher.register(CRYSTAL_CHEST, 0,
                new ModelResourceLocation(CRYSTAL_CHEST.getRegistryName(), inventory));

        mesher.register(CRYSTAL_LEGS, 0,
                new ModelResourceLocation(CRYSTAL_LEGS.getRegistryName(), inventory));

        mesher.register(CRYSTAL_BOOTS, 0,
                new ModelResourceLocation(CRYSTAL_BOOTS.getRegistryName(), inventory));

        mesher.register(THUNDERER_HELM, 0,
                new ModelResourceLocation(THUNDERER_HELM.getRegistryName(), inventory));

        mesher.register(THUNDERER_CHEST, 0,
                new ModelResourceLocation(THUNDERER_CHEST.getRegistryName(), inventory));

        mesher.register(THUNDERER_LEGS, 0,
                new ModelResourceLocation(THUNDERER_LEGS.getRegistryName(), inventory));

        mesher.register(THUNDERER_BOOTS, 0,
                new ModelResourceLocation(THUNDERER_BOOTS.getRegistryName(), inventory));

        mesher.register(GOTHIC_SWORD, 0,
                new ModelResourceLocation(GOTHIC_SWORD.getRegistryName(), inventory));

        mesher.register(BURNING_FROST_IGNITER, 0,
                new ModelResourceLocation(BURNING_FROST_IGNITER.getRegistryName(), inventory));

        mesher.register(WINTER_SACRIFICE, 0,
                new ModelResourceLocation(WINTER_SACRIFICE.getRegistryName(), inventory));

        mesher.register(WINTER_SCALE, 0,
                new ModelResourceLocation(WINTER_SCALE.getRegistryName(), inventory));

        mesher.register(HAIL_TEAR, 0,
                new ModelResourceLocation(HAIL_TEAR.getRegistryName(), inventory));

        mesher.register(PLASMA_MINIGUN_CLIP, 0,
                new ModelResourceLocation(PLASMA_MINIGUN_CLIP.getRegistryName(), inventory));

        mesher.register(CRYSTAL_CUTTER_AMMO, 0,
                new ModelResourceLocation(CRYSTAL_CUTTER_AMMO.getRegistryName(), inventory));

        mesher.register(BLOWHOLE_PELLETS, 0,
                new ModelResourceLocation(BLOWHOLE_PELLETS.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_NUGGET, 0,
                new ModelResourceLocation(ADAMANTIUM_NUGGET.getRegistryName(), inventory));

        mesher.register(NAIL_GUN_CLIP, 0,
                new ModelResourceLocation(NAIL_GUN_CLIP.getRegistryName(), inventory));

        mesher.register(NAIL, 0,
                new ModelResourceLocation(NAIL.getRegistryName(), inventory));

        mesher.register(WOLFRAM_WIRE, 0,
                new ModelResourceLocation(WOLFRAM_WIRE.getRegistryName(), inventory));

        mesher.register(GAS_FILTER, 0,
                new ModelResourceLocation(GAS_FILTER.getRegistryName(), inventory));

        mesher.register(LEAD_ACID_BATTERY, 0,
                new ModelResourceLocation(LEAD_ACID_BATTERY.getRegistryName(), inventory));

        mesher.register(LI_ION_BATTERY, 0,
                new ModelResourceLocation(LI_ION_BATTERY.getRegistryName(), inventory));

        mesher.register(LEAD_BEARING, 0,
                new ModelResourceLocation(LEAD_BEARING.getRegistryName(), inventory));

        mesher.register(ARSENIC_BEARING, 0,
                new ModelResourceLocation(ARSENIC_BEARING.getRegistryName(), inventory));

        mesher.register(ELECTRIC_MOTOR, 0,
                new ModelResourceLocation(ELECTRIC_MOTOR.getRegistryName(), inventory));

        mesher.register(LINEAR_MOTOR, 0,
                new ModelResourceLocation(LINEAR_MOTOR.getRegistryName(), inventory));

        mesher.register(EYE_OF_SEER, 0,
                new ModelResourceLocation(EYE_OF_SEER.getRegistryName(), inventory));

        mesher.register(PROCESSOR, 0,
                new ModelResourceLocation(PROCESSOR.getRegistryName(), inventory));

        mesher.register(COPPER_WIRE, 0,
                new ModelResourceLocation(COPPER_WIRE.getRegistryName(), inventory));

        mesher.register(GOLD_WIRE, 0,
                new ModelResourceLocation(GOLD_WIRE.getRegistryName(), inventory));

        mesher.register(SILVER_WIRE, 0,
                new ModelResourceLocation(SILVER_WIRE.getRegistryName(), inventory));

        mesher.register(RUBBER, 0,
                new ModelResourceLocation(RUBBER.getRegistryName(), inventory));

        mesher.register(CIRCUIT, 0,
                new ModelResourceLocation(CIRCUIT.getRegistryName(), inventory));

        mesher.register(ADVANCED_CIRCUIT, 0,
                new ModelResourceLocation(ADVANCED_CIRCUIT.getRegistryName(), inventory));

        mesher.register(TOXIC_CIRCUIT, 0,
                new ModelResourceLocation(TOXIC_CIRCUIT.getRegistryName(), inventory));

        mesher.register(DIMENSION_CIRCUIT, 0,
                new ModelResourceLocation(DIMENSION_CIRCUIT.getRegistryName(), inventory));

        mesher.register(ICICLE_MINIGUN_CLIP, 0,
                new ModelResourceLocation(ICICLE_MINIGUN_CLIP.getRegistryName(), inventory));

        mesher.register(SLIME_PLASTIC, 0,
                new ModelResourceLocation(SLIME_PLASTIC.getRegistryName(), inventory));

        mesher.register(WHITE_SLIMEBALL, 0,
                new ModelResourceLocation(WHITE_SLIMEBALL.getRegistryName(), inventory));

        mesher.register(PLASTIC, 0,
                new ModelResourceLocation(PLASTIC.getRegistryName(), inventory));

        mesher.register(SLIME_CELL, 0,
                new ModelResourceLocation(SLIME_CELL.getRegistryName(), inventory));

        mesher.register(TOXIBERRY_STICK, 0,
                new ModelResourceLocation(TOXIBERRY_STICK.getRegistryName(), inventory));

        mesher.register(SCRAP_METAL, 0,
                new ModelResourceLocation(SCRAP_METAL.getRegistryName(), inventory));

        mesher.register(SULFUR_DUST, 0,
                new ModelResourceLocation(SULFUR_DUST.getRegistryName(), inventory));

        mesher.register(MAGMA_BLOOM_SEED, 0,
                new ModelResourceLocation(MAGMA_BLOOM_SEED.getRegistryName(), inventory));

        mesher.register(PIZZA_CHICKEN, 0,
                new ModelResourceLocation(PIZZA_CHICKEN.getRegistryName(), inventory));

        mesher.register(PIZZA_DIAVOLA, 0,
                new ModelResourceLocation(PIZZA_DIAVOLA.getRegistryName(), inventory));

        mesher.register(PIZZA_CHEESE, 0,
                new ModelResourceLocation(PIZZA_CHEESE.getRegistryName(), inventory));

        mesher.register(PIZZA_TOXIC, 0,
                new ModelResourceLocation(PIZZA_TOXIC.getRegistryName(), inventory));

        mesher.register(PIZZA_GLOWING, 0,
                new ModelResourceLocation(PIZZA_GLOWING.getRegistryName(), inventory));

        mesher.register(SMOKED_SAUSAGE, 0,
                new ModelResourceLocation(SMOKED_SAUSAGE.getRegistryName(), inventory));

        mesher.register(RAW_RIBS, 0,
                new ModelResourceLocation(RAW_RIBS.getRegistryName(), inventory));

        mesher.register(HOT_SPICY_RIBS, 0,
                new ModelResourceLocation(HOT_SPICY_RIBS.getRegistryName(), inventory));

        mesher.register(BAUXITE, 0,
                new ModelResourceLocation(BAUXITE.getRegistryName(), inventory));

        mesher.register(BRASS_INGOT, 0,
                new ModelResourceLocation(BRASS_INGOT.getRegistryName(), inventory));

        mesher.register(BRASS_DUST, 0,
                new ModelResourceLocation(BRASS_DUST.getRegistryName(), inventory));

        mesher.register(BRASS_NUGGET, 0,
                new ModelResourceLocation(BRASS_NUGGET.getRegistryName(), inventory));

        mesher.register(ZINC_INGOT, 0,
                new ModelResourceLocation(ZINC_INGOT.getRegistryName(), inventory));

        mesher.register(ZINC_DUST, 0,
                new ModelResourceLocation(ZINC_DUST.getRegistryName(), inventory));

        mesher.register(ZINC_NUGGET, 0,
                new ModelResourceLocation(ZINC_NUGGET.getRegistryName(), inventory));

        mesher.register(TITANIUM_INGOT, 0,
                new ModelResourceLocation(TITANIUM_INGOT.getRegistryName(), inventory));

        mesher.register(TITANIUM_DUST, 0,
                new ModelResourceLocation(TITANIUM_DUST.getRegistryName(), inventory));

        mesher.register(TITANIUM_NUGGET, 0,
                new ModelResourceLocation(TITANIUM_NUGGET.getRegistryName(), inventory));

        mesher.register(CHEMICAL_GLASS, 0,
                new ModelResourceLocation(CHEMICAL_GLASS.getRegistryName(), inventory));

        mesher.register(TOXINIUM_NUGGET, 0,
                new ModelResourceLocation(TOXINIUM_NUGGET.getRegistryName(), inventory));

        mesher.register(ICE_DUST, 0,
                new ModelResourceLocation(ICE_DUST.getRegistryName(), inventory));

        mesher.register(CONIFER_ROSIN, 0,
                new ModelResourceLocation(CONIFER_ROSIN.getRegistryName(), inventory));

        mesher.register(FIERY_OIL, 0,
                new ModelResourceLocation(FIERY_OIL.getRegistryName(), inventory));

        mesher.register(SALT, 0,
                new ModelResourceLocation(SALT.getRegistryName(), inventory));

        mesher.register(CRYSTALLIZED_POISON, 0,
                new ModelResourceLocation(CRYSTALLIZED_POISON.getRegistryName(), inventory));

        mesher.register(COPPER_SULFATE, 0,
                new ModelResourceLocation(COPPER_SULFATE.getRegistryName(), inventory));

        mesher.register(PLANT_FIBER, 0,
                new ModelResourceLocation(PLANT_FIBER.getRegistryName(), inventory));

        mesher.register(DRIED_PLANT_FIBER, 0,
                new ModelResourceLocation(DRIED_PLANT_FIBER.getRegistryName(), inventory));

        mesher.register(GLOWING_CRYSTAL_DUST, 0,
                new ModelResourceLocation(GLOWING_CRYSTAL_DUST.getRegistryName(), inventory));

        mesher.register(MAGIC_CRYSTAL_DUST, 0,
                new ModelResourceLocation(MAGIC_CRYSTAL_DUST.getRegistryName(), inventory));

        mesher.register(ADVANCED_POLYMER, 0,
                new ModelResourceLocation(ADVANCED_POLYMER.getRegistryName(), inventory));

        mesher.register(TOXIBERRY_JUICE_DRIP, 0,
                new ModelResourceLocation(TOXIBERRY_JUICE_DRIP.getRegistryName(), inventory));

        mesher.register(THUNDER_STONE, 0,
                new ModelResourceLocation(THUNDER_STONE.getRegistryName(), inventory));

        mesher.register(THUNDER_CAPACITOR, 0,
                new ModelResourceLocation(THUNDER_CAPACITOR.getRegistryName(), inventory));

        mesher.register(SKY_CRYSTAL, 0,
                new ModelResourceLocation(SKY_CRYSTAL.getRegistryName(), inventory));

        mesher.register(SKY_CRYSTAL_PIECE, 0,
                new ModelResourceLocation(SKY_CRYSTAL_PIECE.getRegistryName(), inventory));

        mesher.register(WIND_NATURE, 0,
                new ModelResourceLocation(WIND_NATURE.getRegistryName(), inventory));

        mesher.register(SKY_SPHERE, 0,
                new ModelResourceLocation(SKY_SPHERE.getRegistryName(), inventory));

        mesher.register(ARSENIC_INGOT, 0,
                new ModelResourceLocation(ARSENIC_INGOT.getRegistryName(), inventory));

        mesher.register(ARSENIC_DUST, 0,
                new ModelResourceLocation(ARSENIC_DUST.getRegistryName(), inventory));

        mesher.register(ARSENIC_NUGGET, 0,
                new ModelResourceLocation(ARSENIC_NUGGET.getRegistryName(), inventory));

        mesher.register(WOLFRAM_INGOT, 0,
                new ModelResourceLocation(WOLFRAM_INGOT.getRegistryName(), inventory));

        mesher.register(WOLFRAM_DUST, 0,
                new ModelResourceLocation(WOLFRAM_DUST.getRegistryName(), inventory));

        mesher.register(WOLFRAM_NUGGET, 0,
                new ModelResourceLocation(WOLFRAM_NUGGET.getRegistryName(), inventory));

        mesher.register(STORMBRASS_INGOT, 0,
                new ModelResourceLocation(STORMBRASS_INGOT.getRegistryName(), inventory));

        mesher.register(STORMBRASS_DUST, 0,
                new ModelResourceLocation(STORMBRASS_DUST.getRegistryName(), inventory));

        mesher.register(STORMBRASS_NUGGET, 0,
                new ModelResourceLocation(STORMBRASS_NUGGET.getRegistryName(), inventory));

        mesher.register(STORMSTEEL_NUGGET, 0,
                new ModelResourceLocation(STORMSTEEL_NUGGET.getRegistryName(), inventory));

        mesher.register(TOXINIUM_INGOT, 0,
                new ModelResourceLocation(TOXINIUM_INGOT.getRegistryName(), inventory));

        mesher.register(TOXINIUM_DUST, 0,
                new ModelResourceLocation(TOXINIUM_DUST.getRegistryName(), inventory));

        mesher.register(STORMSTEEL_INGOT, 0,
                new ModelResourceLocation(STORMSTEEL_INGOT.getRegistryName(), inventory));

        mesher.register(STORMSTEEL_DUST, 0,
                new ModelResourceLocation(STORMSTEEL_DUST.getRegistryName(), inventory));

        mesher.register(VOID_CRYSTAL, 0,
                new ModelResourceLocation(VOID_CRYSTAL.getRegistryName(), inventory));

        mesher.register(HEROBRINE_CURSE, 0,
                new ModelResourceLocation(HEROBRINE_CURSE.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_DUST, 0,
                new ModelResourceLocation(ADAMANTIUM_DUST.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_INGOT, 0,
                new ModelResourceLocation(ADAMANTIUM_INGOT.getRegistryName(), inventory));

        mesher.register(STORM_SPANNER, 0,
                new ModelResourceLocation(STORM_SPANNER.getRegistryName(), inventory));

        mesher.register(HAZARD_HELM, 0,
                new ModelResourceLocation(HAZARD_HELM.getRegistryName(), inventory));

        mesher.register(HAZARD_CHEST, 0,
                new ModelResourceLocation(HAZARD_CHEST.getRegistryName(), inventory));

        mesher.register(HAZARD_LEGS, 0,
                new ModelResourceLocation(HAZARD_LEGS.getRegistryName(), inventory));

        mesher.register(HAZARD_BOOTS, 0,
                new ModelResourceLocation(HAZARD_BOOTS.getRegistryName(), inventory));

        mesher.register(BULLET_ADAMANTIUM, 0,
                new ModelResourceLocation(BULLET_ADAMANTIUM.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_LONGSWORD, 0,
                new ModelResourceLocation(ADAMANTIUM_LONGSWORD.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_KNIFE, 0,
                new ModelResourceLocation(ADAMANTIUM_KNIFE.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_HELM, 0,
                new ModelResourceLocation(ADAMANTIUM_HELM.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_CHEST, 0,
                new ModelResourceLocation(ADAMANTIUM_CHEST.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_LEGS, 0,
                new ModelResourceLocation(ADAMANTIUM_LEGS.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_BOOTS, 0,
                new ModelResourceLocation(ADAMANTIUM_BOOTS.getRegistryName(), inventory));

        mesher.register(ADAMANTIUM_ROUNDS, 0,
                new ModelResourceLocation(ADAMANTIUM_ROUNDS.getRegistryName(), inventory));

        mesher.register(NORTHERN_INGOT, 0,
                new ModelResourceLocation(NORTHERN_INGOT.getRegistryName(), inventory));

        mesher.register(NORTHERN_SPHERE, 0,
                new ModelResourceLocation(NORTHERN_SPHERE.getRegistryName(), inventory));

        mesher.register(VILE_SUBSTANCE, 0,
                new ModelResourceLocation(VILE_SUBSTANCE.getRegistryName(), inventory));

        mesher.register(EMBRYO, 0,
                new ModelResourceLocation(EMBRYO.getRegistryName(), inventory));

        mesher.register(MUTAGEN, 0,
                new ModelResourceLocation(MUTAGEN.getRegistryName(), inventory));

        mesher.register(CMO, 0,
                new ModelResourceLocation(CMO.getRegistryName(), inventory));

        mesher.register(PROCESSOR_PATTERN, 0,
                new ModelResourceLocation(PROCESSOR_PATTERN.getRegistryName(), inventory));

        mesher.register(ARTHROSLELECHA_BRASS_LOG_WAND, 0,
                new ModelResourceLocation(ARTHROSLELECHA_BRASS_LOG_WAND.getRegistryName(), inventory));

        mesher.register(ARTHROSLELECHA_PINK_LOG_WAND, 0,
                new ModelResourceLocation(ARTHROSLELECHA_PINK_LOG_WAND.getRegistryName(), inventory));

        mesher.register(ENDER_CROWN, 0,
                new ModelResourceLocation(ENDER_CROWN.getRegistryName(), inventory));

        mesher.register(LIVE_HEART, 0,
                new ModelResourceLocation(LIVE_HEART.getRegistryName(), inventory));

        mesher.register(ITEM_TURRET, 0,
                new ModelResourceLocation(ITEM_TURRET.getRegistryName(), inventory));

        mesher.register(WRENCH, 0,
                new ModelResourceLocation(WRENCH.getRegistryName(), inventory));

        mesher.register(VIRULENT_ROD, 0,
                new ModelResourceLocation(VIRULENT_ROD.getRegistryName(), inventory));

        mesher.register(BUNKER_KEYCARD, 0,
                new ModelResourceLocation(BUNKER_KEYCARD.getRegistryName(), inventory));

        mesher.register(ANTI_RAD_PACK, 0,
                new ModelResourceLocation(ANTI_RAD_PACK.getRegistryName(), inventory));

        mesher.register(ANTI_RAD_PILLS, 0,
                new ModelResourceLocation(ANTI_RAD_PILLS.getRegistryName(), inventory));

        mesher.register(ANTI_RAD_INJECTOR, 0,
                new ModelResourceLocation(ANTI_RAD_INJECTOR.getRegistryName(), inventory));

        mesher.register(GAS_MASK, 0,
                new ModelResourceLocation(GAS_MASK.getRegistryName(), inventory));

        mesher.register(ARSENIC_PICKAXE, 0,
                new ModelResourceLocation(ARSENIC_PICKAXE.getRegistryName(), inventory));

        mesher.register(ARSENIC_AXE, 0,
                new ModelResourceLocation(ARSENIC_AXE.getRegistryName(), inventory));

        mesher.register(ARSENIC_SHOVEL, 0,
                new ModelResourceLocation(ARSENIC_SHOVEL.getRegistryName(), inventory));

        mesher.register(RUSTED_KEY, 0,
                new ModelResourceLocation(RUSTED_KEY.getRegistryName(), inventory));

        mesher.register(SNOW_COAT_HELM, 0,
                new ModelResourceLocation(SNOW_COAT_HELM.getRegistryName(), inventory));

        mesher.register(SNOW_COAT_CHEST, 0,
                new ModelResourceLocation(SNOW_COAT_CHEST.getRegistryName(), inventory));

        mesher.register(SNOW_COAT_LEGS, 0,
                new ModelResourceLocation(SNOW_COAT_LEGS.getRegistryName(), inventory));

        mesher.register(SNOW_COAT_BOOTS, 0,
                new ModelResourceLocation(SNOW_COAT_BOOTS.getRegistryName(), inventory));

        mesher.register(CORAL_HELM, 0,
                new ModelResourceLocation(CORAL_HELM.getRegistryName(), inventory));

        mesher.register(CORAL_CHEST, 0,
                new ModelResourceLocation(CORAL_CHEST.getRegistryName(), inventory));

        mesher.register(CORAL_LEGS, 0,
                new ModelResourceLocation(CORAL_LEGS.getRegistryName(), inventory));

        mesher.register(CORAL_BOOTS, 0,
                new ModelResourceLocation(CORAL_BOOTS.getRegistryName(), inventory));

        mesher.register(LICH_HELM, 0,
                new ModelResourceLocation(LICH_HELM.getRegistryName(), inventory));

        mesher.register(LICH_CHEST, 0,
                new ModelResourceLocation(LICH_CHEST.getRegistryName(), inventory));

        mesher.register(LICH_LEGS, 0,
                new ModelResourceLocation(LICH_LEGS.getRegistryName(), inventory));

        mesher.register(LICH_BOOTS, 0,
                new ModelResourceLocation(LICH_BOOTS.getRegistryName(), inventory));

        mesher.register(BONE_HELM, 0,
                new ModelResourceLocation(BONE_HELM.getRegistryName(), inventory));

        mesher.register(BONE_CHEST, 0,
                new ModelResourceLocation(BONE_CHEST.getRegistryName(), inventory));

        mesher.register(BONE_LEGS, 0,
                new ModelResourceLocation(BONE_LEGS.getRegistryName(), inventory));

        mesher.register(BONE_BOOTS, 0,
                new ModelResourceLocation(BONE_BOOTS.getRegistryName(), inventory));

        mesher.register(VOID_ROCKET, 0,
                new ModelResourceLocation(VOID_ROCKET.getRegistryName(), inventory));

        mesher.register(WATERBLAST_ROCKET, 0,
                new ModelResourceLocation(WATERBLAST_ROCKET.getRegistryName(), inventory));

        mesher.register(DEMOLISHING_ROCKET, 0,
                new ModelResourceLocation(DEMOLISHING_ROCKET.getRegistryName(), inventory));

        mesher.register(MINING_ROCKET, 0,
                new ModelResourceLocation(MINING_ROCKET.getRegistryName(), inventory));

        mesher.register(CHEMICAL_ROCKET, 0,
                new ModelResourceLocation(CHEMICAL_ROCKET.getRegistryName(), inventory));

        mesher.register(NAPALM_ROCKET, 0,
                new ModelResourceLocation(NAPALM_ROCKET.getRegistryName(), inventory));

        mesher.register(FROSTFIRE_ROCKET, 0,
                new ModelResourceLocation(FROSTFIRE_ROCKET.getRegistryName(), inventory));

        mesher.register(GLASS_HEART, 0,
                new ModelResourceLocation(GLASS_HEART.getRegistryName(), inventory));

        mesher.register(COMMON_ROCKET, 0,
                new ModelResourceLocation(COMMON_ROCKET.getRegistryName(), inventory));

        mesher.register(SNAP_BALL_AMMO, 0,
                new ModelResourceLocation(SNAP_BALL_AMMO.getRegistryName(), inventory));

        mesher.register(CREATIVE_TEAM_SELECTOR, 0,
                new ModelResourceLocation(CREATIVE_TEAM_SELECTOR.getRegistryName(), inventory));

        mesher.register(FIRE_WHIP, 0,
                new ModelResourceLocation(TIMELESS_SWORD.getRegistryName(), inventory));

        mesher.register(TIMELESS_SWORD, 0,
                new ModelResourceLocation(TIMELESS_SWORD.getRegistryName(), inventory));

        mesher.register(PALM_LOG_WAND, 0,
                new ModelResourceLocation(PALM_LOG_WAND.getRegistryName(), inventory));

        mesher.register(CORAL_RIFLE_CLIP, 0,
                new ModelResourceLocation(CORAL_RIFLE_CLIP.getRegistryName(), inventory));

        mesher.register(MANA_KEEPER, 0,
                new ModelResourceLocation(MANA_KEEPER.getRegistryName(), inventory));

        mesher.register(THORN_KEEPER, 0,
                new ModelResourceLocation(THORN_KEEPER.getRegistryName(), inventory));

        mesher.register(LIGHT_BAND, 0,
                new ModelResourceLocation(LIGHT_BAND.getRegistryName(), inventory));

        mesher.register(SPRINGER_WAISTBAND, 0,
                new ModelResourceLocation(SPRINGER_WAISTBAND.getRegistryName(), inventory));

        mesher.register(SPIRIT_THORN, 0,
                new ModelResourceLocation(SPIRIT_THORN.getRegistryName(), inventory));

        mesher.register(VENOMED_DAGGER, 0,
                new ModelResourceLocation(VENOMED_DAGGER.getRegistryName(), inventory));

        mesher.register(BLEEDING_ROOT, 0,
                new ModelResourceLocation(BLEEDING_ROOT.getRegistryName(), inventory));

        mesher.register(PAINFUL_ROOT, 0,
                new ModelResourceLocation(PAINFUL_ROOT.getRegistryName(), inventory));

        mesher.register(CYBER_AMULET, 0,
                new ModelResourceLocation(CYBER_AMULET.getRegistryName(), inventory));

        mesher.register(PERSISTENCE_PENDENT, 0,
                new ModelResourceLocation(PERSISTENCE_PENDENT.getRegistryName(), inventory));

        mesher.register(BRASS_KNUCKLES, 0,
                new ModelResourceLocation(BRASS_KNUCKLES.getRegistryName(), inventory));

        mesher.register(HELLHOUND_COLLAR, 0,
                new ModelResourceLocation(HELLHOUND_COLLAR.getRegistryName(), inventory));

        mesher.register(GOLDEN_KNUCKLES, 0,
                new ModelResourceLocation(GOLDEN_KNUCKLES.getRegistryName(), inventory));

        mesher.register(LIVE_BLOOD_NECKLACE, 0,
                new ModelResourceLocation(LIVE_BLOOD_NECKLACE.getRegistryName(), inventory));

        mesher.register(ANCIENT_ICE_SHARD, 0,
                new ModelResourceLocation(ANCIENT_ICE_SHARD.getRegistryName(), inventory));

        mesher.register(MANA_RUBBLE, 0,
                new ModelResourceLocation(MANA_RUBBLE.getRegistryName(), inventory));

        mesher.register(ICE_HEART, 0,
                new ModelResourceLocation(ICE_HEART.getRegistryName(), inventory));

        mesher.register(FROST_INGUISHER, 0,
                new ModelResourceLocation(FROST_INGUISHER.getRegistryName(), inventory));

        mesher.register(HOLY_EXTINGUISHER, 0,
                new ModelResourceLocation(HOLY_EXTINGUISHER.getRegistryName(), inventory));

        mesher.register(GHOSTFLAME_TRAP, 0,
                new ModelResourceLocation(GHOSTFLAME_TRAP.getRegistryName(), inventory));

        mesher.register(FLAME_SUPPRESSOR, 0,
                new ModelResourceLocation(FLAME_SUPPRESSOR.getRegistryName(), inventory));

        mesher.register(CONDUCTIVE_BELT, 0,
                new ModelResourceLocation(CONDUCTIVE_BELT.getRegistryName(), inventory));

        mesher.register(LIGHTNING_SOCKS, 0,
                new ModelResourceLocation(LIGHTNING_SOCKS.getRegistryName(), inventory));

        mesher.register(AMMONIA_FLASK, 0,
                new ModelResourceLocation(AMMONIA_FLASK.getRegistryName(), inventory));

        mesher.register(CORROSIVE_FLASK, 0,
                new ModelResourceLocation(CORROSIVE_FLASK.getRegistryName(), inventory));

        mesher.register(CROSS_CHAINLET, 0,
                new ModelResourceLocation(CROSS_CHAINLET.getRegistryName(), inventory));

        mesher.register(DETOXICATOR, 0,
                new ModelResourceLocation(DETOXICATOR.getRegistryName(), inventory));

        mesher.register(HAZARD_GLOVE, 0,
                new ModelResourceLocation(HAZARD_GLOVE.getRegistryName(), inventory));

        mesher.register(MAGIC_CONTACT_LENSES, 0,
                new ModelResourceLocation(MAGIC_CONTACT_LENSES.getRegistryName(), inventory));

        mesher.register(MINERS_GLOVE, 0,
                new ModelResourceLocation(MINERS_GLOVE.getRegistryName(), inventory));

        mesher.register(BODY_WARMER, 0,
                new ModelResourceLocation(BODY_WARMER.getRegistryName(), inventory));

        mesher.register(DEVOURERS_TEETH, 0,
                new ModelResourceLocation(DEVOURERS_TEETH.getRegistryName(), inventory));

        mesher.register(ENDER_LEECH, 0,
                new ModelResourceLocation(ENDER_LEECH.getRegistryName(), inventory));

        mesher.register(GASEOUS_ENERGY_DRINK, 0,
                new ModelResourceLocation(GASEOUS_ENERGY_DRINK.getRegistryName(), inventory));

        mesher.register(RUNNERS_SOCKS, 0,
                new ModelResourceLocation(RUNNERS_SOCKS.getRegistryName(), inventory));

        mesher.register(SLIME_EATER, 0,
                new ModelResourceLocation(SLIME_EATER.getRegistryName(), inventory));

        mesher.register(FIRE_EATER, 0,
                new ModelResourceLocation(FIRE_EATER.getRegistryName(), inventory));

        mesher.register(SLIME_DEVOURER, 0,
                new ModelResourceLocation(SLIME_DEVOURER.getRegistryName(), inventory));

        mesher.register(LAVA_EATER, 0,
                new ModelResourceLocation(LAVA_EATER.getRegistryName(), inventory));

        mesher.register(PERSONAL_EXTINGUISHER, 0,
                new ModelResourceLocation(PERSONAL_EXTINGUISHER.getRegistryName(), inventory));

        mesher.register(ETHER_WORM, 0,
                new ModelResourceLocation(ETHER_WORM.getRegistryName(), inventory));

        mesher.register(ANGEL_WORM, 0,
                new ModelResourceLocation(ANGEL_WORM.getRegistryName(), inventory));

        mesher.register(CANDY_APPLE, 0,
                new ModelResourceLocation(CANDY_APPLE.getRegistryName(), inventory));

        mesher.register(CANDY_CANE, 0,
                new ModelResourceLocation(CANDY_CANE.getRegistryName(), inventory));

        mesher.register(CRIMBERRY_WINE, 0,
                new ModelResourceLocation(CRIMBERRY_WINE.getRegistryName(), inventory));

        mesher.register(GIFT, 0,
                new ModelResourceLocation(GIFT.getRegistryName(), inventory));

        mesher.register(IMPETUS, 0,
                new ModelResourceLocation(AIRBORNE_CIRCLET.getRegistryName(), inventory));

        mesher.register(AIRBORNE_CIRCLET, 0,
                new ModelResourceLocation(AIRBORNE_CIRCLET.getRegistryName(), inventory));

        mesher.register(CHROMIUM_INGOT, 0,
                new ModelResourceLocation(CHROMIUM_INGOT.getRegistryName(), inventory));

        mesher.register(CHROMIUM_DUST, 0,
                new ModelResourceLocation(CHROMIUM_DUST.getRegistryName(), inventory));

        mesher.register(BERYLLIUM_INGOT, 0,
                new ModelResourceLocation(BERYLLIUM_INGOT.getRegistryName(), inventory));

        mesher.register(BERYLLIUM_DUST, 0,
                new ModelResourceLocation(BERYLLIUM_DUST.getRegistryName(), inventory));

        mesher.register(GEOMANTIC_CRYSTAL, 0,
                new ModelResourceLocation(ItemsRegister.GEOMANTIC_CRYSTAL.getRegistryName(), inventory));

        mesher.register(MANGANESE_DUST, 0,
                new ModelResourceLocation(MANGANESE_DUST.getRegistryName(), inventory));

        mesher.register(MANGANESE_INGOT, 0,
                new ModelResourceLocation(MANGANESE_INGOT.getRegistryName(), inventory));

        mesher.register(WEAPON_ENCHANTMENTS_BOX, 0,
                new ModelResourceLocation(WEAPON_ENCHANTMENTS_BOX.getRegistryName(), inventory));

        mesher.register(SIMPLE_ENCHANTMENTS_BOX, 0,
                new ModelResourceLocation(SIMPLE_ENCHANTMENTS_BOX.getRegistryName(), inventory));

        mesher.register(ALL_ENCHANTMENTS_BOX, 0,
                new ModelResourceLocation(ALL_ENCHANTMENTS_BOX.getRegistryName(), inventory));

        mesher.register(EMERALD_EYE, 0,
                new ModelResourceLocation(EMERALD_EYE.getRegistryName(), inventory));

        mesher.register(BULLET_FESTIVAL, 0,
                new ModelResourceLocation(BULLET_FESTIVAL.getRegistryName(), inventory));

        mesher.register(BULLET_EXPLODING, 0,
                new ModelResourceLocation(BULLET_EXPLODING.getRegistryName(), inventory));

        mesher.register(BULLET_CRYSTAL, 0,
                new ModelResourceLocation(BULLET_CRYSTAL.getRegistryName(), inventory));

        mesher.register(BULLET_TOXIC, 0,
                new ModelResourceLocation(BULLET_TOXIC.getRegistryName(), inventory));

        mesher.register(BULLET_POISONOUS, 0,
                new ModelResourceLocation(BULLET_POISONOUS.getRegistryName(), inventory));

        mesher.register(BULLET_THUNDER, 0,
                new ModelResourceLocation(BULLET_THUNDER.getRegistryName(), inventory));

        mesher.register(TOXINIUM_SHOTGUN_CLIP, 0,
                new ModelResourceLocation(TOXINIUM_SHOTGUN_CLIP.getRegistryName(), inventory));

        mesher.register(BULLET_INCENDIARY, 0,
                new ModelResourceLocation(BULLET_INCENDIARY.getRegistryName(), inventory));

        mesher.register(BULLET_SILVER, 0,
                new ModelResourceLocation(BULLET_SILVER.getRegistryName(), inventory));

        mesher.register(BULLET_LEAD, 0,
                new ModelResourceLocation(BULLET_LEAD.getRegistryName(), inventory));

        mesher.register(BULLET_COPPER, 0,
                new ModelResourceLocation(BULLET_COPPER.getRegistryName(), inventory));

        mesher.register(BULLE_TGOLD, 0,
                new ModelResourceLocation(BULLE_TGOLD.getRegistryName(), inventory));

        mesher.register(BULLET_FROZEN, 0,
                new ModelResourceLocation(BULLET_FROZEN.getRegistryName(), inventory));

        mesher.register(SUBMACHINE_CLIP, 0,
                new ModelResourceLocation(SUBMACHINE_CLIP.getRegistryName(), inventory));

        mesher.register(SUBMACHINE, 0,
                new ModelResourceLocation(SUBMACHINE.getRegistryName(), inventory));

        mesher.register(LOCKER, 0,
                new ModelResourceLocation(LOCKER.getRegistryName(), inventory));

        mesher.register(TOXINIUMSHOTGUN, 0,
                new ModelResourceLocation(TOXINIUMSHOTGUN.getRegistryName(), inventory));

        mesher.register(COIN, 0,
                new ModelResourceLocation(COIN.getRegistryName(), inventory));

        mesher.register(TOXINIUM_HELM, 0,
                new ModelResourceLocation(TOXINIUM_HELM.getRegistryName(), inventory));

        mesher.register(TOXINIUM_CHEST, 0,
                new ModelResourceLocation(TOXINIUM_CHEST.getRegistryName(), inventory));

        mesher.register(TOXINIUM_LEGS, 0,
                new ModelResourceLocation(TOXINIUM_LEGS.getRegistryName(), inventory));

        mesher.register(TOXINIUM_BOOTS, 0,
                new ModelResourceLocation(TOXINIUM_BOOTS.getRegistryName(), inventory));

        mesher.register(FORGETPICK_AXE, 0,
                new ModelResourceLocation(FORGETPICK_AXE.getRegistryName(), inventory));

        mesher.register(FORGET_AXE, 0,
                new ModelResourceLocation(FORGET_AXE.getRegistryName(), inventory));

        mesher.register(FORGET_SHOVEL, 0,
                new ModelResourceLocation(FORGET_SHOVEL.getRegistryName(), inventory));

        mesher.register(FIRST, 0,
                new ModelResourceLocation(FIRST.getRegistryName(), inventory));

        mesher.register(ICHOR_SHOWER, 0,
                new ModelResourceLocation(ICHOR_SHOWER.getRegistryName(), inventory));

        mesher.register(SHARK_CANNON, 0,
                new ModelResourceLocation(SHARK_CANNON.getRegistryName(), inventory));

        mesher.register(SHARK_AMMO, 0,
                new ModelResourceLocation(SHARK_AMMO.getRegistryName(), inventory));

        mesher.register(MAGIC_BOOMERANG, 0,
                new ModelResourceLocation(MAGIC_BOOMERANG.getRegistryName(), inventory));

        mesher.register(BUTTERFLY, 0,
                new ModelResourceLocation(BUTTERFLY.getRegistryName(), inventory));

        mesher.register(SUNRISE, 0,
                new ModelResourceLocation(SUNRISE.getRegistryName(), inventory));

        mesher.register(LASER_SNIPER, 0,
                new ModelResourceLocation(LASER_SNIPER.getRegistryName(), inventory));

        mesher.register(ION_BATTERY, 0,
                new ModelResourceLocation(ION_BATTERY.getRegistryName(), inventory));

        mesher.register(LASER_PISTOL, 0,
                new ModelResourceLocation(LASER_PISTOL.getRegistryName(), inventory));

        mesher.register(LASER_RIFLE, 0,
                new ModelResourceLocation(LASER_RIFLE.getRegistryName(), inventory));

        mesher.register(VAMPIRE_KNIFE, 0,
                new ModelResourceLocation(VAMPIRE_KNIFE.getRegistryName(), inventory));

        mesher.register(VAMPIRE_KNIFES, 0,
                new ModelResourceLocation(VAMPIRE_KNIFES.getRegistryName(), inventory));

        mesher.register(FROST_BOLT_STAFF, 0,
                new ModelResourceLocation(FROST_BOLT_STAFF.getRegistryName(), inventory));

        mesher.register(ANTIMATTER_CHARGE, 0,
                new ModelResourceLocation(ANTIMATTER_CHARGE.getRegistryName(), inventory));

        mesher.register(ELEMENTAL_AMMO_FIRE, 0,
                new ModelResourceLocation(ELEMENTAL_AMMO_FIRE.getRegistryName(), inventory));

        mesher.register(ELEMENTAL_AMMO_WATER, 0,
                new ModelResourceLocation(ELEMENTAL_AMMO_WATER.getRegistryName(), inventory));

        mesher.register(ELEMENTAL_AMMO_AIR, 0,
                new ModelResourceLocation(ELEMENTAL_AMMO_AIR.getRegistryName(), inventory));

        mesher.register(ELEMENTAL_AMMO_EARTH, 0,
                new ModelResourceLocation(ELEMENTAL_AMMO_EARTH.getRegistryName(), inventory));

        mesher.register(STINGER_BOLTS, 0,
                new ModelResourceLocation(STINGER_BOLTS.getRegistryName(), inventory));

        mesher.register(FIREWORK_PACK, 0,
                new ModelResourceLocation(FIREWORK_PACK.getRegistryName(), inventory));

        mesher.register(FIREWORK_DRAGON_ROCKET, 0,
                new ModelResourceLocation(FIREWORK_DRAGON_ROCKET.getRegistryName(), inventory));

        mesher.register(QUADROCOPTER_BELT, 0,
                new ModelResourceLocation(QUADROCOPTER_BELT.getRegistryName(), inventory));

        mesher.register(VORTEX_IN_A_BOTTLE, 0,
                new ModelResourceLocation(VORTEX_IN_A_BOTTLE.getRegistryName(), inventory));

        mesher.register(ETHER_SIGN, 0,
                new ModelResourceLocation(ETHER_SIGN.getRegistryName(), inventory));

        mesher.register(PHOENIX_GHOST_CAPE, 0,
                new ModelResourceLocation(PHOENIX_GHOST_CAPE.getRegistryName(), inventory));

        mesher.register(BILEBITER_SPHERE, 0,
                new ModelResourceLocation(BILEBITER_SPHERE.getRegistryName(), inventory));

        mesher.register(SNOWSTORM_STAFF, 0,
                new ModelResourceLocation(FROST_BOLT_STAFF.getRegistryName(), inventory));

        mesher.register(WIZARD_HELM, 0,
                new ModelResourceLocation(WIZARD_HELM.getRegistryName(), inventory));

        mesher.register(WIZARD_CHEST, 0,
                new ModelResourceLocation(WIZARD_CHEST.getRegistryName(), inventory));

        mesher.register(WIZARD_LEGS, 0,
                new ModelResourceLocation(WIZARD_LEGS.getRegistryName(), inventory));

        mesher.register(WIZARD_BOOTS, 0,
                new ModelResourceLocation(WIZARD_BOOTS.getRegistryName(), inventory));

        mesher.register(FIRE_MAGE_HELM, 0,
                new ModelResourceLocation(FIRE_MAGE_HELM.getRegistryName(), inventory));

        mesher.register(FIRE_MAGE_CHEST, 0,
                new ModelResourceLocation(FIRE_MAGE_CHEST.getRegistryName(), inventory));

        mesher.register(FIRE_MAGE_LEGS, 0,
                new ModelResourceLocation(FIRE_MAGE_LEGS.getRegistryName(), inventory));

        mesher.register(FIRE_MAGE_BOOTS, 0,
                new ModelResourceLocation(FIRE_MAGE_BOOTS.getRegistryName(), inventory));

        mesher.register(FIRE_LORD_HELM, 0,
                new ModelResourceLocation(FIRE_LORD_HELM.getRegistryName(), inventory));

        mesher.register(FIRE_LORD_CHEST, 0,
                new ModelResourceLocation(FIRE_LORD_CHEST.getRegistryName(), inventory));

        mesher.register(FIRE_LORD_LEGS, 0,
                new ModelResourceLocation(FIRE_LORD_LEGS.getRegistryName(), inventory));

        mesher.register(FIRE_LORD_BOOTS, 0,
                new ModelResourceLocation(FIRE_LORD_BOOTS.getRegistryName(), inventory));

        mesher.register(GRAPLING_HOOK, 0,
                new ModelResourceLocation(GRAPLING_HOOK.getRegistryName(), inventory));

        mesher.register(JUNGLE_GRAPLING_HOOK, 0,
                new ModelResourceLocation(JUNGLE_GRAPLING_HOOK.getRegistryName(), inventory));

        mesher.register(SEASHELL, 0,
                new ModelResourceLocation(SEASHELL.getRegistryName(), inventory));

        mesher.register(SLIME_GRAPLING_HOOK, 0,
                new ModelResourceLocation(SLIME_GRAPLING_HOOK.getRegistryName(), inventory));

        mesher.register(ENDER_GRAPLING_HOOK, 0,
                new ModelResourceLocation(ENDER_GRAPLING_HOOK.getRegistryName(), inventory));

        mesher.register(FISHING_ROD, 0,
                new ModelResourceLocation(FISHING_ROD.getRegistryName(), inventory));

        mesher.register(ICE_SWORD, 0,
                new ModelResourceLocation(ICE_SWORD.getRegistryName(), inventory));

        mesher.register(NETHER_GRINDER_AMMO, 0,
                new ModelResourceLocation(NETHER_GRINDER_AMMO.getRegistryName(), inventory));

        mesher.register(SNOWFLAKE_SHURIKEN, 0,
                new ModelResourceLocation(SNOWFLAKE_SHURIKEN.getRegistryName(), inventory));

        mesher.register(CHAIN_DAGGER, 0,
                new ModelResourceLocation(CHAIN_DAGGER.getRegistryName(), inventory));

        mesher.register(TOXIC_NUCLEAR_WARHEAD, 0,
                new ModelResourceLocation(TOXIC_NUCLEAR_WARHEAD.getRegistryName(), inventory));

        mesher.register(EMPTY_CELL, 0,
                new ModelResourceLocation(EMPTY_CELL.getRegistryName(), inventory));

        mesher.register(CRYOGEN_CELL, 0,
                new ModelResourceLocation(CRYOGEN_CELL.getRegistryName(), inventory));

        mesher.register(LIGHTNING_HOOK, 0,
                new ModelResourceLocation(LIGHTNING_HOOK.getRegistryName(), inventory));

        mesher.register(RING_OF_PROTECTION, 0,
                new ModelResourceLocation(RING_OF_PROTECTION.getRegistryName(), inventory));

        mesher.register(SPIKE_RING, 0,
                new ModelResourceLocation(SPIKE_RING.getRegistryName(), inventory));

        mesher.register(SPARKLING_NECKLACE, 0,
                new ModelResourceLocation(SPARKLING_NECKLACE.getRegistryName(), inventory));

        mesher.register(EXP, 0,
                new ModelResourceLocation(EMPTY_CELL.getRegistryName(), inventory));

        mesher.register(DEMONIC_IGNITER, 0,
                new ModelResourceLocation(DEMONIC_IGNITER.getRegistryName(), inventory));

        mesher.register(BOUNCING_RING, 0,
                new ModelResourceLocation(BOUNCING_RING.getRegistryName(), inventory));

        mesher.register(SLIME_BOOTS, 0,
                new ModelResourceLocation(SLIME_BOOTS.getRegistryName(), inventory));

        mesher.register(SLIME_HELM, 0,
                new ModelResourceLocation(SLIME_HELM.getRegistryName(), inventory));

        mesher.register(SLIME_CHEST, 0,
                new ModelResourceLocation(SLIME_CHEST.getRegistryName(), inventory));

        mesher.register(SLIME_LEGS, 0,
                new ModelResourceLocation(SLIME_LEGS.getRegistryName(), inventory));

        mesher.register(ICE_BOOTS, 0,
                new ModelResourceLocation(ICE_BOOTS.getRegistryName(), inventory));

        mesher.register(ICE_HELM, 0,
                new ModelResourceLocation(ICE_HELM.getRegistryName(), inventory));

        mesher.register(ICE_CHEST, 0,
                new ModelResourceLocation(ICE_CHEST.getRegistryName(), inventory));

        mesher.register(ICE_LEGS, 0,
                new ModelResourceLocation(ICE_LEGS.getRegistryName(), inventory));

        mesher.register(SOUL_CHARM, 0,
                new ModelResourceLocation(SOUL_CHARM.getRegistryName(), inventory));

        mesher.register(JUNGLE_BOOTS, 0,
                new ModelResourceLocation(JUNGLE_BOOTS.getRegistryName(), inventory));

        mesher.register(JUNGLE_HELM, 0,
                new ModelResourceLocation(JUNGLE_HELM.getRegistryName(), inventory));

        mesher.register(JUNGLE_CHESTPLATE, 0,
                new ModelResourceLocation(JUNGLE_CHESTPLATE.getRegistryName(), inventory));

        mesher.register(JUNGLE_LEGGINS, 0,
                new ModelResourceLocation(JUNGLE_LEGGINS.getRegistryName(), inventory));

        mesher.register(WEB_GRAPLING_HOOK, 0,
                new ModelResourceLocation(WEB_GRAPLING_HOOK.getRegistryName(), inventory));

        mesher.register(ROPE_GRAPLING_HOOK, 0,
                new ModelResourceLocation(ROPE_GRAPLING_HOOK.getRegistryName(), inventory));

        mesher.register(PLASMA_RAILGUN_BOLTS, 0,
                new ModelResourceLocation(PLASMA_RAILGUN_BOLTS.getRegistryName(), inventory));

        mesher.register(AIM_LENS, 0,
                new ModelResourceLocation(AIM_LENS.getRegistryName(), inventory));

        mesher.register(FISH_FEED, 0,
                new ModelResourceLocation(FISH_FEED.getRegistryName(), inventory));

        mesher.register(VICIOUS_EMBLEM, 0,
                new ModelResourceLocation(VICIOUS_EMBLEM.getRegistryName(), inventory));

        mesher.register(ORB_OF_DESTROY, 0,
                new ModelResourceLocation(ORB_OF_DESTROY.getRegistryName(), inventory));

        mesher.register(WOODEN_SKIING, 0,
                new ModelResourceLocation(ORB_OF_DESTROY.getRegistryName(), inventory));

        mesher.register(VIAL_OF_POISON, 0,
                new ModelResourceLocation(VIAL_OF_POISON.getRegistryName(), inventory));

        mesher.register(VAMPIRIC_HEART, 0,
                new ModelResourceLocation(VAMPIRIC_HEART.getRegistryName(), inventory));

        mesher.register(FROZEN_WINGS, 0,
                new ModelResourceLocation(FROZEN_WINGS.getRegistryName(), inventory));

        mesher.register(TOXIC_WINGS, 0,
                new ModelResourceLocation(TOXIC_WINGS.getRegistryName(), inventory));

        mesher.register(GLACIDE_BLADE, 0,
                new ModelResourceLocation(GLACIDE_BLADE.getRegistryName(), inventory));

        mesher.register(INFERNAL_BLADE, 0,
                new ModelResourceLocation(INFERNAL_BLADE.getRegistryName(), inventory));

        mesher.register(CINDER_BOW, 0,
                new ModelResourceLocation(CINDER_BOW.getRegistryName(), inventory));

        mesher.register(INFERNUM_INGOT, 0,
                new ModelResourceLocation(INFERNUM_INGOT.getRegistryName(), inventory));

        mesher.register(MOLTEN_INGOT, 0,
                new ModelResourceLocation(MOLTEN_INGOT.getRegistryName(), inventory));

        mesher.register(INFERNUM_NUGGET, 0,
                new ModelResourceLocation(INFERNUM_NUGGET.getRegistryName(), inventory));

        mesher.register(MOLTEN_NUGGET, 0,
                new ModelResourceLocation(MOLTEN_NUGGET.getRegistryName(), inventory));

        mesher.register(MOLTEN_STRING, 0,
                new ModelResourceLocation(MOLTEN_STRING.getRegistryName(), inventory));

        mesher.register(LIQUID_FIRE, 0,
                new ModelResourceLocation(LIQUID_FIRE.getRegistryName(), inventory));

        mesher.register(DEMONITE, 0,
                new ModelResourceLocation(DEMONITE.getRegistryName(), inventory));

        mesher.register(DEMONITE_SHARD, 0,
                new ModelResourceLocation(DEMONITE_SHARD.getRegistryName(), inventory));

        mesher.register(RUBY, 0,
                new ModelResourceLocation(RUBY.getRegistryName(), inventory));

        mesher.register(SAPPHIRE, 0,
                new ModelResourceLocation(SAPPHIRE.getRegistryName(), inventory));

        mesher.register(CITRINE, 0,
                new ModelResourceLocation(CITRINE.getRegistryName(), inventory));

        mesher.register(AMETHYST, 0,
                new ModelResourceLocation(AMETHYST.getRegistryName(), inventory));

        mesher.register(TOPAZ, 0,
                new ModelResourceLocation(TOPAZ.getRegistryName(), inventory));

        mesher.register(RHINESTONE, 0,
                new ModelResourceLocation(RHINESTONE.getRegistryName(), inventory));

        mesher.register(MAGIC_POWDER, 0,
                new ModelResourceLocation(MAGIC_POWDER.getRegistryName(), inventory));

        mesher.register(ICE_GEM, 0,
                new ModelResourceLocation(ICE_GEM.getRegistryName(), inventory));

        mesher.register(WEATHER_FRAGMENTS, 0,
                new ModelResourceLocation(WEATHER_FRAGMENTS.getRegistryName(), inventory));

        mesher.register(SNOW_CLOTH, 0,
                new ModelResourceLocation(SNOW_CLOTH.getRegistryName(), inventory));

        mesher.register(CONIFER_STICK, 0,
                new ModelResourceLocation(CONIFER_STICK.getRegistryName(), inventory));

        mesher.register(SOUL_STONE, 0,
                new ModelResourceLocation(SOUL_STONE.getRegistryName(), inventory));

        mesher.register(SAPPHIRE_EYE, 0,
                new ModelResourceLocation(SAPPHIRE_EYE.getRegistryName(), inventory));

        mesher.register(INSTANT_ENCHANTMENT_BOOK, 0,
                new ModelResourceLocation(INSTANT_ENCHANTMENT_BOOK.getRegistryName(), inventory));

        mesher.register(THE_LORD_OF_PAIN, 0,
                new ModelResourceLocation(PHOENIX_GHOST_CAPE.getRegistryName(), inventory));

        mesher.register(CRYSTAL_FAN, 0,
                new ModelResourceLocation(RUBY.getRegistryName(), inventory));

        mesher.register(ANTIDOTE, 0,
                new ModelResourceLocation(ANTIDOTE.getRegistryName(), inventory));

        mesher.register(TOXI_COLA, 0,
                new ModelResourceLocation(TOXI_COLA.getRegistryName(), inventory));

        mesher.register(ANTI_POTION, 0,
                new ModelResourceLocation(ANTI_POTION.getRegistryName(), inventory));

        mesher.register(DECEIDUS_JUICE, 0,
                new ModelResourceLocation(DECEIDUS_JUICE.getRegistryName(), inventory));

        mesher.register(TOXEDGE_BREAD, 0,
                new ModelResourceLocation(TOXEDGE_BREAD.getRegistryName(), inventory));

        mesher.register(TOXIBERRY_MOJITO, 0,
                new ModelResourceLocation(TOXIBERRY_MOJITO.getRegistryName(), inventory));

        mesher.register(WASTE_BURGER, 0,
                new ModelResourceLocation(WASTE_BURGER.getRegistryName(), inventory));

        mesher.register(BROWN_SLIME_WAND, 0,
                new ModelResourceLocation(BROWN_SLIME_WAND.getRegistryName(), inventory));

        mesher.register(SLIME_BLOB_WAND, 0,
                new ModelResourceLocation(SLIME_BLOB_WAND.getRegistryName(), inventory));

        mesher.register(BONES_WAND, 0,
                new ModelResourceLocation(BONES_WAND.getRegistryName(), inventory));

        mesher.register(GLOWING_TOXIBERRY, 0,
                new ModelResourceLocation(GLOWING_TOXIBERRY.getRegistryName(), inventory));

        mesher.register(SMALL_TOXIBERRY, 0,
                new ModelResourceLocation(SMALL_TOXIBERRY.getRegistryName(), inventory));

        mesher.register(GOTHIC_PICKAXE, 0,
                new ModelResourceLocation(GOTHIC_PICKAXE.getRegistryName(), inventory));

        mesher.register(GOTHIC_AXE, 0,
                new ModelResourceLocation(GOTHIC_AXE.getRegistryName(), inventory));

        mesher.register(GOTHIC_SHOVEL, 0,
                new ModelResourceLocation(GOTHIC_SHOVEL.getRegistryName(), inventory));

        for (Item item : FOR_RENDER) {
            mesher.register(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), inventory));
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

        for (Item item : ItemGrenade.REGISTRY.values()) {
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

    public static void registerTileEntitySpecialRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFrostPortal.class, new FrostPortalTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedBlockDetector.class, new AdvancedBlockDetectorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileGlowingVein.class, new GlowingVeinTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalSphere.class, new CrystalSphereTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSpellForge.class, new SpellForgeTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSacrificialAltar.class, new SacrificialAltarTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemicLab.class, new AlchemicLabTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChest.class, new ChestsTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRunicMirror.class, new RunicMirrorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileManaBottle.class, new ManaBottleTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileStarLantern.class, new StarLanternTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePresentBox.class, new PresentBoxTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileShimmeringBeastbloom.class, new ShimmeringBeastbloomTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBioCell.class, new BioCellTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDungeonLadder.class, new DungeonLadderTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileToxicPortal.class, new ToxicPortalTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDungeonPortal.class, new DungeonPortalTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileVoidCrystal.class, new VoidCrystalTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileNexusBeacon.class, new TideBeaconTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileResearchTable.class, new ResearchTableTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSplitter.class, new SplitterTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRetort.class, new RetortTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileVial.class, new VialTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBookcase.class, new BookcaseTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileGlossary.class, new GlossaryTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDisenchantmentTable.class, new DisenchantmentTableTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileTritonHearth.class, new TESRModel(new TritonHearthModel(), new ResourceLocation("arpg:textures/triton_hearth_model_tex.png"))
        );
        ClientRegistry.bindTileEntitySpecialRenderer(TileIndustrialMixer.class, new IndustrialMixerTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyTable.class, new AssemblyTableTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyAugment.class, new AssemblyAugmentTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileNexusNiveolite.class, new TESRModel(new TileNexusNiveoliteModel(), new ResourceLocation("arpg:textures/tile_nexus_niveolite_model_tex.png"))
        );
        ClientRegistry.bindTileEntitySpecialRenderer(TileCalibrationBundle.class, new CalibrationBundleTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSoulCatcher.class, new SoulCatcherTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileManaPump.class, new ManaPumpTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new TESRSieve(new SieveModel(), new ResourceLocation("arpg:textures/sieve_model_tex.png")));
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileElectricSieve.class, new TESRSieve(new ElectricSieveModel(), new ResourceLocation("arpg:textures/electric_sieve_model_tex.png"))
        );
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileItemCharger.class, new ItemChargerTESR(new ItemChargerModel(), new ResourceLocation("arpg:textures/item_charger_model_tex.png"))
        );
        ClientRegistry.bindTileEntitySpecialRenderer(TileARPGChest.class, new ARPGChestTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaticaPortal.class, new AquaticaPortalTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileStormledgePortal.class, new StormledgePortalTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMagicGenerator.class, new MagicGeneratorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileEtheriteInvocator.class,
                new TESRModel(
                        new EtheriteInvocatorModel(),
                        new ResourceLocation("arpg:textures/etherite_invocator_model_tex.png"),
                        tile -> ((TileEtheriteInvocator)tile).hasCell ? new float[]{1.0F} : null
                )
        );
        ClientRegistry.bindTileEntitySpecialRenderer(TileTeamBanner.class, new TeamBannerTESR());
    }

    public static void registerFluidsRender() {
        modelLoader(BlocksRegister.FLUID_CRYON, "cryon");
        modelLoader(BlocksRegister.FLUID_POISON, "poison");
        modelLoader(BlocksRegister.FLUID_SLIME, "slime");
        modelLoader(BlocksRegister.FLUID_DARKNESS, "darkness");
        modelLoader(BlocksRegister.FLUID_LARVA_WATER, "larva_water");
        modelLoader(BlocksRegister.FLUID_HYDROTHERMAL, "hydrothermal_solution");
        modelLoader(BlocksRegister.FLUID_BIOGENIC_ACID, "biogenic_acid");
        modelLoader(BlocksRegister.FLUID_TOXIN, "toxin");
        modelLoader(BlocksRegister.FLUID_SULFURIC_ACID, "sulfuric_acid");
        modelLoader(BlocksRegister.FLUID_LUMINESCENT, "luminescent_liquid");
        modelLoader(BlocksRegister.FLUID_SULFURIC_GAS, "sulfuric_gas");
        modelLoader(BlocksRegister.FLUID_MANA_OIL, "mana_oil");
        modelLoader(BlocksRegister.FLUID_PETROLEUM, "petroleum");
        modelLoader(BlocksRegister.FLUID_GASOLINE, "gasoline");
        modelLoader(BlocksRegister.FLUID_FUEL_OIL, "fuel_oil");
        modelLoader(BlocksRegister.FLUID_NITRIC_ACID, "nitric_acid");
        modelLoader(BlocksRegister.FLUID_NATURAL_GAS, "natural_gas");
        modelLoader(BlocksRegister.FLUID_DISSOLVED_TOXINIUM, "dissolved_toxinium");
    }

    private static void modelLoader(Block block, String variant) {
        final ModelResourceLocation flLocation = new ModelResourceLocation("arpg:fluid", variant);
        Item fl = Item.getItemFromBlock(block);
        ModelBakery.registerItemVariants(fl);
        ModelLoader.setCustomMeshDefinition(fl, stack -> flLocation);
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return flLocation;
            }
        });
    }


}
