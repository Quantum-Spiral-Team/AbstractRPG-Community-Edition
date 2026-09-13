package com.vivern.arpg.main;

import com.vivern.arpg.recipes.AssemblyTableRecipe;
import com.vivern.arpg.recipes.IndustrialMixerRecipe;
import com.vivern.arpg.recipes.Ingridient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class IntegrationHelper {
    
    private static final Logger LOGGER = LogManager.getLogger(IntegrationHelper.class.getSimpleName());

    public static boolean printDebug = true;
    static Class clazzPulvelizer = null;
    static Method methodPulvelizer1 = null;
    static Method methodPulvelizer2 = null;
    static boolean thermalExpPulvelizer = false;
    static boolean ic2macerator = false;
    static Object ic2objInputGenerator = null;
    static Method ic2forStackMethod = null;
    static Object ic2managerMaceratorObject = null;
    static Method addrecipeIC2MethodMacerator = null;
    static boolean enderioSagmill = false;
    static Constructor constrEndIORecipeInput = null;
    static Constructor constrEndIORecipeOutput = null;
    static Constructor constructorEndIORecipe = null;
    static Class clazzEndIORecipeOutput = null;
    static Object enumBonusTypeCHANCE = null;
    static Object enumBonusTypeNONE = null;
    static Object endIOManagerSagmill = null;
    static Method methodAddRecipe = null;
    static boolean immersiveCrusher = false;
    static Method addRecipeMethodImmersCrusher = null;
    static boolean ic2extractor = false;
    static Object ic2managerExtractorObject = null;
    static Method addrecipeIC2MethodExtr = null;
    static Method methodMagmaCrucible = null;
    static boolean thermalExpMagmaCrucible = false;
    static Method methodTECentrifuge = null;
    static boolean thermalExpCentrifuge = false;
    static Class clazzTESmelter = null;
    static Method methodTESmelter1 = null;
    static Method methodTESmelter2 = null;
    static boolean thermalExpSmelter = false;
    static boolean enderioSmelter = false;
    static Method methodAddRecipeEndIOSmelter = null;
    static Object endIOManagerSmelter = null;
    static Constructor basicManyToOneConstr = null;
    static Constructor constructorEndIORecipe2 = null;
    static Class clazzEndIORecipeInput = null;
    static boolean immersiveAlloyKiln = false;
    static Method addRecipeMethodImmersAlloyKiln = null;
    public static FluidStack emptyfs = new FluidStack(FluidRegistry.WATER, 0);
    static boolean ic2compressor = false;
    static Object ic2managerCompressorObject = null;
    static Method addrecipeIC2MethodCompressor = null;
    static Method methodCompactor = null;
    static boolean thermalExpCompactor = false;
    static Class clazzCompactor = null;
    static Object compactorModeALL = null;

    public static void debugprint1(ItemStack output, String mod) {
        if (printDebug) {
            LOGGER.debug("adding recipe crusher|" + mod + "|" + output.getDisplayName() + "|");
        }
    }

    public static void debugprint2(ItemStack output) {
        if (printDebug) {
            LOGGER.debug("success");
            LOGGER.debug("\n");
        }
    }

    public static void addRecipes() {
        if (printDebug) {
            LOGGER.debug("addCrushersRecipes");
        }

        addCrushersRecipes();
        if (printDebug) {
            LOGGER.debug("addAlloyRecipes");
        }

        addAlloyRecipes();
        if (printDebug) {
            LOGGER.debug("addExtractorRecipes");
        }

        addExtractorRecipes();
        if (printDebug) {
            LOGGER.debug("addMagmaCrucibleRecipes");
        }

        addMagmaCrucibleRecipes();
        if (printDebug) {
            LOGGER.debug("addCentrifugeRecipes");
        }

        addCentrifugeRecipes();
        if (printDebug) {
            LOGGER.debug("addPressRecipes");
        }

        addPressRecipes();
    }

    public static void addCrushersRecipes() {
        addCrushersRecipe(2000, new ItemStack(Items.ICE_GEM), new ItemStack(Items.ICE_DUST, 2), new ItemStack(Items.ICE_DUST), 20, false);
        addCrushersRecipe(1800, new ItemStack(BlocksRegister.CAVE_CRYSTAL), new ItemStack(Items.MAGIC_CRYSTAL_DUST), new ItemStack(Items.MAGIC_CRYSTAL_DUST), 25, false);
        addCrushersRecipe(2200, new ItemStack(BlocksRegister.GLOWING_CAVE_CRYSTAL), new ItemStack(Items.GLOWING_CRYSTAL_DUST), new ItemStack(Items.GLOWING_CRYSTAL_DUST), 25, false);
        addCrushersRecipe(4000, new ItemStack(BlocksRegister.MAGIC_STONE), new ItemStack(Items.MAGIC_CRYSTAL_DUST, 2), new ItemStack(Items.MAGIC_CRYSTAL_DUST), 10, false);
        addCrushersRecipe(1500, new ItemStack(net.minecraft.init.Items.ENCHANTED_BOOK), new ItemStack(Items.MAGIC_POWDER), new ItemStack(net.minecraft.init.Items.LEATHER), 10, false);
        addCrushersRecipe(6000, new ItemStack(Items.ADAMANTIUM_INGOT), new ItemStack(Items.ADAMANTIUM_DUST), null, 0, false);
        addCrushersRecipe(5000, new ItemStack(Items.TOXINIUM_INGOT), new ItemStack(Items.TOXINIUM_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.ARSENIC_INGOT), new ItemStack(Items.ARSENIC_DUST), null, 0, false);
        addCrushersRecipe(3500, new ItemStack(Items.WOLFRAM_INGOT), new ItemStack(Items.WOLFRAM_DUST), null, 0, false);
        addCrushersRecipe(5500, new ItemStack(Items.STORMSTEEL_INGOT), new ItemStack(Items.STORMSTEEL_DUST), null, 0, false);
        addCrushersRecipe(4000, new ItemStack(Items.STORMBRASS_INGOT), new ItemStack(Items.STORMBRASS_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.BRASS_INGOT), new ItemStack(Items.BRASS_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.ZINC_INGOT), new ItemStack(Items.ZINC_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.TITANIUM_INGOT), new ItemStack(Items.TITANIUM_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.MANGANESE_INGOT), new ItemStack(Items.MANGANESE_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.CHROMIUM_INGOT), new ItemStack(Items.CHROMIUM_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.BERYLLIUM_INGOT), new ItemStack(Items.BERYLLIUM_DUST), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(Items.SILVER_INGOT), new ItemStack(Items.SILVER_DUST), null, 0, true);
        addCrushersRecipe(1000, new ItemStack(Items.LITHIUM_INGOT), new ItemStack(Items.LITHIUM_DUST), null, 0, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.ALUMINIUM_ORE), new ItemStack(Items.BAUXITE, 2), new ItemStack(Items.BAUXITE), 30, false);
        addCrushersRecipe(4000, new ItemStack(BlocksRegister.ZINC_ORE), new ItemStack(Items.ZINC_DUST, 2), new ItemStack(Items.SULFUR_DUST), 25, false);
        addCrushersRecipe(4000, new ItemStack(BlocksRegister.ARSENIC_ORE), new ItemStack(Items.ARSENIC_DUST, 2), new ItemStack(Items.SULFUR_DUST), 30, false);
        addCrushersRecipe(2000, new ItemStack(BlocksRegister.SULFUR_CRYSTAL), new ItemStack(Items.SULFUR_DUST), new ItemStack(Items.SULFUR_DUST), 50, false);
        addCrushersRecipe(6000, new ItemStack(Items.COPPER_TRANSFORMER), new ItemStack(Items.COPPER_WIRE, 8), OreDicHelper.getOrNull(OreDicHelper.DUSTIRON, 1), 100, false);
        addCrushersRecipe(2000, new ItemStack(Items.LEAD_BEARING), new ItemStack(Items.BEARING_ALLOY_DUST), new ItemStack(Items.RUBBER), 50, false);
        addCrushersRecipe(1000, new ItemStack(net.minecraft.init.Items.WHEAT), new ItemStack(Items.FLOUR), null, 0, true);
        addCrushersRecipe(9000, new ItemStack(Blocks.HAY_BLOCK), new ItemStack(Items.FLOUR, 9), null, 0, true);
        addCrushersRecipe(15000, new ItemStack(BlocksRegister.ADAMANTIUM_ORE), new ItemStack(Items.ADAMANTIUM_ORE_DUST, 3), new ItemStack(Items.STONE_DUST), 100, false);
        addCrushersRecipe(13000, new ItemStack(BlocksRegister.MITHRIL_ORE), new ItemStack(Items.MITHRIL_ORE_DUST, 3), new ItemStack(Items.STONE_DUST), 100, false);
        addCrushersRecipe(20000, new ItemStack(BlocksRegister.ORESTORM_STEEL), new ItemStack(Items.STORMSTEEL_DUST, 2), new ItemStack(Items.STONE_DUST, 2), 100, false);
        addCrushersRecipe(10000, new ItemStack(BlocksRegister.WOLFRAM_ORE), new ItemStack(Items.WOLFRAM_DUST), new ItemStack(Items.RADIOACTIVE_DUST, 3), 100, false);
        addCrushersRecipe(12000, new ItemStack(BlocksRegister.TOXINIUM_ORE), new ItemStack(Items.TOXINIUM_ORE_DUST, 2), new ItemStack(Items.RADIOACTIVE_DUST, 2), 100, false);
        addCrushersRecipe(2000, new ItemStack(BlocksRegister.ICE_ORE_GLACIER), new ItemStack(Items.ICE_DUST, 5), new ItemStack(net.minecraft.init.Items.SNOWBALL, 2), 50, false);
        addCrushersRecipe(2000, new ItemStack(BlocksRegister.ICE_ORE_SNOW_ICE), new ItemStack(Items.ICE_DUST, 5), new ItemStack(net.minecraft.init.Items.SNOWBALL, 2), 50, false);
        addCrushersRecipe(2000, new ItemStack(BlocksRegister.RADIOACTIVE_STONE), new ItemStack(Items.RADIOACTIVE_DUST, 4), null, 0, false);
        addCrushersRecipe(2000, new ItemStack(BlocksRegister.RADIOACTIVE_COBBLESTONE), new ItemStack(Items.RADIOACTIVE_DUST, 4), null, 0, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.SHELLROCK), new ItemStack(Items.LIMESTONE_DUST, 4), new ItemStack(Items.SEASHELL), 5, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.CHALKROCK), new ItemStack(Items.LIMESTONE_DUST, 4), new ItemStack(net.minecraft.init.Items.DYE, 1, 15), 50, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.STROMATOLITE), new ItemStack(Items.LIMESTONE_DUST, 3), new ItemStack(Items.SALT), 100, false);
        addCrushersRecipe(1000, new ItemStack(Items.CORAL), new ItemStack(Items.LIMESTONE_DUST), null, 0, false);
        addCrushersRecipe(1000, new ItemStack(BlocksRegister.CALCITE), new ItemStack(Items.LIMESTONE_DUST, 4), null, 0, false);
        addCrushersRecipe(14000, new ItemStack(BlocksRegister.METALLIC_CORAL, 6), new ItemStack(Items.AQUATIC_DUST), new ItemStack(Items.LIMESTONE_DUST), 75, true);
        addCrushersRecipe(500, new ItemStack(Items.RUBBLESTONE), new ItemStack(Items.STONE_DUST, 1), null, 0, false);
        addCrushersRecipe(1500, new ItemStack(BlocksRegister.SALT_ORE), new ItemStack(Items.SALT, 3), new ItemStack(Items.STONE_DUST, 1), 100, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.BOMB_RUSTED), new ItemStack(Items.SCRAP_BOMB, 2), new ItemStack(Items.SCRAP_BOMB), 40, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.BOMB_SMALL), new ItemStack(Items.SCRAP_BOMB, 2), new ItemStack(Items.SCRAP_BOMB), 40, false);
        addCrushersRecipe(3000, new ItemStack(BlocksRegister.BOMB_TOXIC), new ItemStack(Items.SCRAP_BOMB, 2), new ItemStack(Items.SCRAP_BOMB), 40, false);
        if (OreDicHelper.doesOreNameExist("dustObsidian")) {
            addCrushersRecipe(8000, new ItemStack(BlocksRegister.DEEP_ROCK), OreDicHelper.get("dustObsidian", 2), new ItemStack(Items.BASALT_DUST, 2), 100, true);
        }
    }

    public static void addAlloyRecipes() {
        addAlloyRecipe(2000, new ItemStack(Items.COPPER_SULFATE, 9), OreDicHelper.get(OreDicHelper.COALCOKE, 1), null, OreDicHelper.get(OreDicHelper.COPPER, 1), OreDicHelper.get("itemSlag", 1), 10);
        if (OreDicHelper.doesOreNameExist("fuelCoke")) {
            addAlloyRecipe(4200, new ItemStack(BlocksRegister.CHROMIUM_ORE), OreDicHelper.get("fuelCoke", 1), null, new ItemStack(Items.CHROMIUM_INGOT), OreDicHelper.get("itemCinnabar", 1), 40);
        }

        if (OreDicHelper.doesOreNameExist("itemCinnabar")) {
            addAlloyRecipe(6000, new ItemStack(BlocksRegister.CHROMIUM_ORE), OreDicHelper.get("itemCinnabar", 1), null, new ItemStack(Items.CHROMIUM_INGOT, 2), OreDicHelper.get("itemSlagRich", 1), 55);
        }

        addAlloyRecipe(500, new ItemStack(net.minecraft.init.Items.SUGAR, 2), OreDicHelper.get("dyeRed", 1), null, new ItemStack(Items.CANDY_CANE), null, 0);
        addAlloyRecipe(500, new ItemStack(net.minecraft.init.Items.APPLE), new ItemStack(net.minecraft.init.Items.STICK), new ItemStack(net.minecraft.init.Items.SUGAR), new ItemStack(Items.CANDY_APPLE), null, 0);
        addAlloyRecipe(2500, OreDicHelper.get("blockGlassHardened", 2), new ItemStack(Items.ARSENIC_NUGGET), null, new ItemStack(Items.CHEMICAL_GLASS, 6), null, 0);
        if (OreDicHelper.doesOreNameExist("fusedQuartz") && OreDicHelper.doesOreNameExist("ingotLead")) {
            addAlloyRecipe(2500, OreDicHelper.get("fusedQuartz", 2), new ItemStack(Items.ARSENIC_NUGGET), OreDicHelper.get("ingotLead", 1), new ItemStack(Items.CHEMICAL_GLASS, 6), null, 0);
        }

        addAlloyRecipe(2500, new ItemStack(Items.FIERY_OIL), new ItemStack(Items.SULFUR_DUST), null, new ItemStack(Items.RUBBER), null, 0);
        addAlloyRecipe(4000, OreDicHelper.get(OreDicHelper.COPPER, 3), new ItemStack(Items.ZINC_INGOT), null, new ItemStack(Items.BRASS_INGOT, 4), null, 0);
        addAlloyRecipe(2000, new ItemStack(Items.ADAMANTIUM_NUGGET, 9), null, null, new ItemStack(Items.ADAMANTIUM_INGOT, 1), null, 0);
        addAlloyRecipe(6000, new ItemStack(BlocksRegister.CHROMIUM_ORE, 2), new ItemStack(net.minecraft.init.Items.COAL), null, new ItemStack(Items.CHROMIUM_INGOT, 1), null, 0);
        addAlloyRecipe(8000, new ItemStack(Blocks.OBSIDIAN, 1), new ItemStack(net.minecraft.init.Items.QUARTZ, 4), new ItemStack(Items.CHROMIUM_INGOT), new ItemStack(BlocksRegister.CHROMIUM_GLASS, 1), null, 0);
        if (OreDicHelper.doesOreNameExist("dustObsidian")) {
            addAlloyRecipe(8000, OreDicHelper.get("dustObsidian", 4), new ItemStack(Items.QUARTZ_DUST, 4), new ItemStack(Items.CHROMIUM_INGOT), new ItemStack(BlocksRegister.CHROMIUM_GLASS, 2), null, 0);
        }

        addAlloyRecipe(3000, new ItemStack(Items.BEARING_ALLOY_DUST), new ItemStack(Items.RUBBER), null, new ItemStack(Items.LEAD_BEARING, 1), null, 0);
        if (Item.getByNameOrId("ic2:crafting") != null) {
            addAlloyRecipe(3000, new ItemStack(Items.BEARING_ALLOY_DUST), new ItemStack(Item.getByNameOrId("ic2:crafting"), 1, 0), null, new ItemStack(Items.LEAD_BEARING, 1), null, 0);
        }

        addAlloyRecipe(8000, new ItemStack(Items.TOXINIUM_DUST), null, null, new ItemStack(Items.TOXINIUM_INGOT, 1), null, 0);
        if (OreDicHelper.doesOreNameExist("fuelCoke")) {
            addAlloyRecipe(5000, new ItemStack(Items.QUARTZ_DUST, 3), OreDicHelper.get("fuelCoke", 1), null, new ItemStack(Items.SILICIUM, 1), null, 0);
        }

        addAlloyRecipe(5000, new ItemStack(Items.QUARTZ_DUST, 3), new ItemStack(net.minecraft.init.Items.DIAMOND, 1), null, new ItemStack(Items.SILICIUM, 1), null, 0);
        addAlloyRecipe(5000, new ItemStack(Items.QUARTZ_DUST, 3), new ItemStack(Items.LITHIUM_DUST, 1), null, new ItemStack(Items.SILICIUM, 1), null, 0);
    }

    public static void addExtractorRecipes() {
        addExtractorRecipe(new ItemStack(BlocksRegister.FIERY_BEAN_LOG, 2), new ItemStack(Items.FIERY_OIL));
        addExtractorRecipe(new ItemStack(BlocksRegister.CONIFER_LOG, 2), new ItemStack(Items.CONIFER_ROSIN));
        addExtractorRecipe(new ItemStack(BlocksRegister.TOXIBERRY_LOG, 2), new ItemStack(Items.TOXIBERRY_JUICE_DRIP));
        addExtractorRecipe(new ItemStack(BlocksRegister.TOXIBERRY_LEAVES, 8), new ItemStack(Items.TOXIBERRY_JUICE_DRIP));
        Item ic2res = Item.getByNameOrId("ic2:misc_resource");
        if (ic2res != null) {
            addExtractorRecipe(new ItemStack(BlocksRegister.SEAGRASS, 10), new ItemStack(ic2res, 1, 6));
            addExtractorRecipe(new ItemStack(BlocksRegister.SEAWEEDBLOCK, 10), new ItemStack(ic2res, 1, 6));
        }

        addExtractorRecipe(new ItemStack(Items.MAGMA_BLOOM_SEED, 16), new ItemStack(Items.LIQUID_FIRE));
        addExtractorRecipe(new ItemStack(BlocksRegister.FIERY_BEAN_SAPLING), new ItemStack(Items.FIERY_OIL, 3));
        addExtractorRecipe(new ItemStack(BlocksRegister.CONIFER_SAPLING), new ItemStack(Items.CONIFER_ROSIN, 3));
    }

    public static void addMagmaCrucibleRecipes() {
        addMagmaCricibleRecipe(4000, new ItemStack(net.minecraft.init.Items.SLIME_BALL), new FluidStack(FluidsRegister.SLIME, 100));
        addMagmaCricibleRecipe(40000, new ItemStack(Blocks.SLIME_BLOCK), new FluidStack(FluidsRegister.SLIME, 1000));
        addMagmaCricibleRecipe(40000, new ItemStack(BlocksRegister.BROWN_SLIME), new FluidStack(FluidsRegister.SLIME, 1000));
        addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.DOLERITE), new FluidStack(FluidRegistry.LAVA, 1000));
        addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.CAVE_ONYX), new FluidStack(FluidRegistry.LAVA, 1000));
        addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.GREEN_ONYX), new FluidStack(FluidRegistry.LAVA, 1000));
        addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.DEEP_ROCK), new FluidStack(FluidRegistry.LAVA, 1000));
        addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.RADIOACTIVE_STONE), new FluidStack(FluidRegistry.LAVA, 1000));
        addMagmaCricibleRecipe(300000, new ItemStack(BlocksRegister.RADIOACTIVE_COBBLESTONE), new FluidStack(FluidRegistry.LAVA, 1000));
        if (FluidRegistry.getFluid("tree_oil") != null) {
            addMagmaCricibleRecipe(2000, new ItemStack(BlocksRegister.PALM_SAPLING), new FluidStack(FluidRegistry.getFluid("tree_oil"), 50));
        }
    }

    public static void addCentrifugeRecipes() {
        addCentrifugeRecipe(4000, new ItemStack(BlocksRegister.FIERY_BEAN_SAPLING), null, new ItemStack(Items.FIERY_OIL, 3), null, null, null, 100, 0, 0, 0);
        addCentrifugeRecipe(4000, new ItemStack(BlocksRegister.CONIFER_SAPLING), null, new ItemStack(Items.CONIFER_ROSIN, 3), null, null, null, 100, 0, 0, 0);
        addCentrifugeRecipe(3000, new ItemStack(Items.CRYOGEN_CELL), new FluidStack(FluidsRegister.CRYON, 100), new ItemStack(Items.EMPTY_CELL), new ItemStack(net.minecraft.init.Items.SNOWBALL), new ItemStack(Items.ICE_DUST), null, 100, 50, 75, 0);
        addCentrifugeRecipe(6000, new ItemStack(Items.MAGMA_BLOOM_SEED, 16), null, new ItemStack(Items.LIQUID_FIRE), null, null, null, 100, 0, 0, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.CONTEM_PLANT_SEEDS, 8), new FluidStack(FluidsRegister.POISON, 250), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(Items.PLANT_FIBER, 2), null, null, 100, 100, 0, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.MUCOPHILLUS_SEEDS, 2), new FluidStack(FluidsRegister.SLIME, 100), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(net.minecraft.init.Items.SLIME_BALL, 2), null, null, 100, 100, 0, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.VISCOSA_SEEDS, 2), new FluidStack(FluidsRegister.TOXIN, 100), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(net.minecraft.init.Items.SLIME_BALL), new ItemStack(Items.PLANT_FIBER), null, 100, 100, 100, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.TOXIBERRY_WEEPING_SEEDS, 3), new FluidStack(FluidsRegister.LUMINESCENT, 100), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(Items.CRYSTALLIZED_POISON), new ItemStack(Items.PLANT_FIBER), null, 100, 100, 100, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.MUCOPHILLUS_BROWN_SEEDS, 5), new FluidStack(FluidsRegister.SLIME, 250), new ItemStack(Items.TOXIBERRY_JUICE_DRIP, 2), new ItemStack(Items.CRYSTALLIZED_POISON), new ItemStack(net.minecraft.init.Items.SLIME_BALL, 3), null, 100, 100, 100, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.TOXIBULB_SEEDS, 1), new FluidStack(FluidsRegister.SULFURICACID, 200), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(Items.PLANT_FIBER), null, null, 100, 100, 0, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.GLOWING_TOXIBERRY, 3), new FluidStack(FluidsRegister.LUMINESCENT, 250), new ItemStack(Items.TOXIBERRY_JUICE_DRIP, 3), new ItemStack(Items.CRYSTALLIZED_POISON), new ItemStack(Items.PLANT_FIBER, 2), null, 100, 100, 100, 0);
        addCentrifugeRecipe(1000, new ItemStack(Items.SMALL_TOXIBERRY, 6), new FluidStack(FluidsRegister.POISON, 100), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(Items.PLANT_FIBER), null, null, 100, 100, 0, 0);
        addCentrifugeRecipe(1000, new ItemStack(BlocksRegister.TOXIBERRY_TREE_SAPLING), null, new ItemStack(Items.TOXIBERRY_STICK), new ItemStack(Items.TOXIBERRY_JUICE_DRIP), new ItemStack(Items.PLANT_FIBER), null, 100, 100, 100, 0);
    }

    public static void addPressRecipes() {
        addPressRecipe(5000, new ItemStack(Items.WOLFRAM_DUST), new ItemStack(Items.WOLFRAM_INGOT), false);
    }

    public static void addCrushersRecipe(int rf, ItemStack input, ItemStack output, ItemStack output2, int chanceOut2, boolean bannedForIC2) {
        if (!thermalExpPulvelizer) {
            try {
                Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.PulverizerManager");
                Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class, int.class);
                Method method2 = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class);
                clazzPulvelizer = clazz;
                methodPulvelizer1 = method;
                methodPulvelizer2 = method2;
                if (clazzPulvelizer != null && methodPulvelizer1 != null && methodPulvelizer2 != null) {
                    thermalExpPulvelizer = true;
                }
            } catch (Exception var22) {
            }
        }

        if (thermalExpPulvelizer) {
            try {
                debugprint1(output, "thermalexpansion");
                if (output2 != null) {
                    methodPulvelizer1.invoke(null, rf, input, output, output2, chanceOut2);
                } else {
                    methodPulvelizer2.invoke(null, rf, input, output);
                }

                debugprint2(output);
            } catch (Exception var21) {
            }
        }

        if (!ic2macerator) {
            try {
                Class clazzInputGenerator = Class.forName("ic2.core.recipe.RecipeInputFactory");
                Method forStackMethod = clazzInputGenerator.getMethod("forStack", ItemStack.class, int.class);
                Object objInputGenerator = clazzInputGenerator.newInstance();
                Class clazzIC2recipes = Class.forName("ic2.api.recipe.Recipes");
                Field managerMacerator = clazzIC2recipes.getField("macerator");
                Object managerMaceratorObject = managerMacerator.get(null);
                Class clazzIC2IRecipeInput = Class.forName("ic2.api.recipe.IRecipeInput");
                addrecipeIC2MethodMacerator = managerMacerator.getType().getMethod("addRecipe", clazzIC2IRecipeInput, NBTTagCompound.class, boolean.class, ItemStack[].class);
                ic2forStackMethod = forStackMethod;
                ic2objInputGenerator = objInputGenerator;
                ic2macerator = true;
                ic2managerMaceratorObject = managerMaceratorObject;
            } catch (Exception var20) {
            }
        }

        if (ic2macerator) {
            try {
                debugprint1(output, "ic2");
                if (!bannedForIC2) {
                    Object recipeIC2Input = ic2forStackMethod.invoke(ic2objInputGenerator, input.copy(), 1);
                    addrecipeIC2MethodMacerator.invoke(ic2managerMaceratorObject, recipeIC2Input, new NBTTagCompound(), true, new ItemStack[]{output.copy()});
                }

                debugprint2(output);
            } catch (Exception var19) {
            }
        }

        if (!enderioSagmill) {
            try {
                Class clazzEndIORecipe = Class.forName("crazypants.enderio.base.recipe.Recipe");
                Class clazzEndIORecipeInput = Class.forName("crazypants.enderio.base.recipe.RecipeInput");
                clazzEndIORecipeOutput = Class.forName("crazypants.enderio.base.recipe.RecipeOutput");
                Class clazzEndIOIRecipeInput = Class.forName("crazypants.enderio.base.recipe.IRecipeInput");
                Class clazzEndIOBonusType = Class.forName("crazypants.enderio.base.recipe.RecipeBonusType");
                Class clazzEndIORecipeLvl = Class.forName("crazypants.enderio.base.recipe.RecipeLevel");
                Constructor constructorEndIORecipeInput = clazzEndIORecipeInput.getConstructor(ItemStack.class);
                Constructor constructorEndIORecipeOutput = clazzEndIORecipeOutput.getConstructor(ItemStack.class, float.class);
                constructorEndIORecipe = clazzEndIORecipe.getConstructor(clazzEndIOIRecipeInput, int.class, clazzEndIOBonusType, Array.newInstance(clazzEndIORecipeOutput, 1).getClass());
                Class clazzEndIOManagerSagmill = Class.forName("crazypants.enderio.base.recipe.sagmill.SagMillRecipeManager");
                Method methodInstance = clazzEndIOManagerSagmill.getMethod("getInstance");
                methodAddRecipe = clazzEndIOManagerSagmill.getMethod("addRecipe", clazzEndIORecipe);
                endIOManagerSagmill = methodInstance.invoke(null);
                constrEndIORecipeInput = constructorEndIORecipeInput;
                constrEndIORecipeOutput = constructorEndIORecipeOutput;
                enumBonusTypeCHANCE = clazzEndIOBonusType.getField("CHANCE_ONLY").get(null);
                enderioSagmill = true;
            } catch (Exception var18) {
            }
        }

        if (enderioSagmill) {
            try {
                debugprint1(output, "enderio");
                Object recipeInput = constrEndIORecipeInput.newInstance(input.copy());
                Object recipeOutput = constrEndIORecipeOutput.newInstance(output.copy(), 1.0F);
                Object arrayOfOutputs = Array.newInstance(clazzEndIORecipeOutput, output2 == null ? 1 : 2);
                Array.set(arrayOfOutputs, 0, recipeOutput);
                if (output2 != null) {
                    Array.set(arrayOfOutputs, 1, constrEndIORecipeOutput.newInstance(output2.copy(), chanceOut2 / 100.0F));
                }

                Object recipe = constructorEndIORecipe.newInstance(recipeInput, rf, enumBonusTypeCHANCE, arrayOfOutputs);
                methodAddRecipe.invoke(endIOManagerSagmill, recipe);
                debugprint2(output);
            } catch (Exception var17) {
            }
        }

        if (!immersiveCrusher) {
            try {
                Class clazzCrusherRecipe = Class.forName("blusunrize.immersiveengineering.api.crafting.CrusherRecipe");
                addRecipeMethodImmersCrusher = clazzCrusherRecipe.getMethod("addRecipe", ItemStack.class, Object.class, int.class);
                immersiveCrusher = true;
            } catch (Exception var16) {
            }
        }

        if (immersiveCrusher) {
            try {
                debugprint1(output, "immersiveengineering");
                addRecipeMethodImmersCrusher.invoke(null, output.copy(), input.copy(), rf);
                debugprint2(output);
            } catch (Exception var15) {
            }
        }
    }

    public static void addAlloyRecipe(int rf, ItemStack input, ItemStack input2, ItemStack input3, ItemStack output, ItemStack output2, int chanceOut2) {
        if (!thermalExpSmelter) {
            try {
                Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.SmelterManager");
                Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class, ItemStack.class, int.class);
                Method method2 = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, ItemStack.class);
                clazzTESmelter = clazz;
                methodTESmelter1 = method;
                methodTESmelter2 = method2;
                if (clazzTESmelter != null && methodTESmelter1 != null && methodTESmelter2 != null) {
                    thermalExpSmelter = true;
                }
            } catch (Exception var21) {
            }
        }

        if (thermalExpSmelter && input3 == null) {
            try {
                debugprint1(output, "thermalexpansion");
                if (output2 != null) {
                    methodTESmelter1.invoke(null, rf, input, input2, output, output2, chanceOut2);
                } else {
                    methodTESmelter2.invoke(null, rf, input, input2, output);
                }

                debugprint2(output);
            } catch (Exception var20) {
            }
        }

        if (!enderioSmelter) {
            try {
                Class clazzEndIORecipe = Class.forName("crazypants.enderio.base.recipe.Recipe");
                Class clazzEndIORecipeMany = Class.forName("crazypants.enderio.base.recipe.IManyToOneRecipe");
                clazzEndIORecipeInput = Class.forName("crazypants.enderio.base.recipe.RecipeInput");
                clazzEndIORecipeOutput = Class.forName("crazypants.enderio.base.recipe.RecipeOutput");
                Class clazzEndIOIRecipeInput = Class.forName("crazypants.enderio.base.recipe.IRecipeInput");
                Class clazzEndIOBonusType = Class.forName("crazypants.enderio.base.recipe.RecipeBonusType");
                Constructor constructorEndIORecipeInput = clazzEndIORecipeInput.getConstructor(ItemStack.class);
                Constructor constructorEndIORecipeOutput = clazzEndIORecipeOutput.getConstructor(ItemStack.class, float.class);
                constructorEndIORecipe2 = clazzEndIORecipe.getConstructor(clazzEndIORecipeOutput, int.class, clazzEndIOBonusType, Array.newInstance(clazzEndIOIRecipeInput, 1).getClass());
                Class clazzEndIOManagerSmelter = Class.forName("crazypants.enderio.base.recipe.alloysmelter.AlloyRecipeManager");
                Method methodInstance = clazzEndIOManagerSmelter.getMethod("getInstance");
                methodAddRecipeEndIOSmelter = clazzEndIOManagerSmelter.getMethod("addRecipe", clazzEndIORecipeMany);
                endIOManagerSmelter = methodInstance.invoke(null);
                constrEndIORecipeInput = constructorEndIORecipeInput;
                constrEndIORecipeOutput = constructorEndIORecipeOutput;
                enumBonusTypeNONE = clazzEndIOBonusType.getField("NONE").get(null);
                Class clazzEndIORecipeManyBase = Class.forName("crazypants.enderio.base.recipe.BasicManyToOneRecipe");
                basicManyToOneConstr = clazzEndIORecipeManyBase.getConstructor(clazzEndIORecipe);
                enderioSmelter = true;
            } catch (Exception var19) {
            }
        }

        if (enderioSmelter) {
            try {
                debugprint1(output, "enderio");
                Object recipeInput = constrEndIORecipeInput.newInstance(input.copy());
                Object recipeOutput = constrEndIORecipeOutput.newInstance(output.copy(), 1.0F);
                Object arrayOfInputs = Array.newInstance(clazzEndIORecipeInput, input2 == null ? 1 : (input3 == null ? 2 : 3));
                Array.set(arrayOfInputs, 0, recipeInput);
                if (input2 != null) {
                    Array.set(arrayOfInputs, 1, constrEndIORecipeInput.newInstance(input2.copy()));
                }

                if (input3 != null) {
                    Array.set(arrayOfInputs, 2, constrEndIORecipeInput.newInstance(input3.copy()));
                }

                Object recipe = constructorEndIORecipe2.newInstance(recipeOutput, rf, enumBonusTypeNONE, arrayOfInputs);
                methodAddRecipeEndIOSmelter.invoke(endIOManagerSmelter, basicManyToOneConstr.newInstance(recipe));
                debugprint2(output);
            } catch (Exception var18) {
            }
        }

        if (!immersiveAlloyKiln) {
            try {
                Class clazzKilnRecipe = Class.forName("blusunrize.immersiveengineering.api.crafting.AlloyRecipe");
                addRecipeMethodImmersAlloyKiln = clazzKilnRecipe.getMethod("addRecipe", ItemStack.class, Object.class, Object.class, int.class);
                immersiveAlloyKiln = true;
            } catch (Exception var17) {
            }
        }

        if (immersiveAlloyKiln && input3 == null) {
            try {
                debugprint1(output, "immersiveengineering");
                addRecipeMethodImmersAlloyKiln.invoke(null, output.copy(), input.copy(), input2.copy(), Math.max(rf / 4, 1));
                debugprint2(output);
            } catch (Exception var16) {
            }
        }

        ItemStack[] inputsarray = input3 == null ? (input2 == null ? new ItemStack[]{input} : new ItemStack[]{input, input2}) : new ItemStack[]{input, input2, input3};
        ItemStack[] outputsarray = output2 != null && chanceOut2 >= 100 ? new ItemStack[]{output, output2} : new ItemStack[]{output};
        NetherMelterRecipesRegister.addRecipe(outputsarray, rf / 1000.0F, inputsarray);
    }

    public static void addExtractorRecipe(ItemStack input, ItemStack output) {
        if (!ic2extractor) {
            try {
                Class clazzInputGenerator = Class.forName("ic2.core.recipe.RecipeInputFactory");
                Method forStackMethod = clazzInputGenerator.getMethod("forStack", ItemStack.class, int.class);
                Object objInputGenerator = clazzInputGenerator.newInstance();
                Class clazzIC2recipes = Class.forName("ic2.api.recipe.Recipes");
                Field managerExtractor = clazzIC2recipes.getField("extractor");
                Object managerMaceratorObject = managerExtractor.get(null);
                Class clazzIC2IRecipeInput = Class.forName("ic2.api.recipe.IRecipeInput");
                addrecipeIC2MethodExtr = managerExtractor.getType().getMethod("addRecipe", clazzIC2IRecipeInput, NBTTagCompound.class, boolean.class, ItemStack[].class);
                ic2forStackMethod = forStackMethod;
                ic2objInputGenerator = objInputGenerator;
                ic2extractor = true;
                ic2managerExtractorObject = managerMaceratorObject;
            } catch (Exception var10) {
            }
        }

        if (ic2extractor) {
            try {
                debugprint1(output, "ic2");
                Object recipeIC2Input = ic2forStackMethod.invoke(ic2objInputGenerator, input.copy(), input.getCount());
                addrecipeIC2MethodExtr.invoke(ic2managerExtractorObject, recipeIC2Input, new NBTTagCompound(), true, new ItemStack[]{output.copy()});
                debugprint2(output);
            } catch (Exception var9) {
            }
        }
    }

    public static void addMagmaCricibleRecipe(int rf, ItemStack input, FluidStack output) {
        if (!thermalExpMagmaCrucible) {
            try {
                Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CrucibleManager");
                Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, FluidStack.class);
                methodMagmaCrucible = method;
                thermalExpMagmaCrucible = true;
            } catch (Exception var6) {
            }
        }

        if (thermalExpMagmaCrucible) {
            try {
                debugprint1(input, "thermalexpansion");
                methodMagmaCrucible.invoke(null, rf, input, output);
                debugprint2(input);
            } catch (Exception var5) {
            }
        }
    }

    public static void addCentrifugeRecipe(int rf, ItemStack input, FluidStack fluid, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4, int chance1, int chance2, int chance3, int chance4) {
        if (output1 != null && output4 == null && (output2 == null || chance2 >= 100) && (output3 == null || chance3 >= 100)) {
            IndustrialMixerRecipe rec = IndustrialMixerRecipesRegister.addRecipe(Items.CENTRIFUGE_MODULE, null, rf, Ingridient.getIngridient(output1), Ingridient.getIngridient(output2), Ingridient.getIngridient(output3), rf / 20, Ingridient.getIngridient(input), Ingridient.EMPTY, Ingridient.EMPTY);
            if (fluid != null) {
                rec.setFluidOutput1(fluid.getUnlocalizedName().replaceFirst("fluid.", ""), fluid.amount);
            }
        }

        if (!thermalExpCentrifuge) {
            try {
                Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CentrifugeManager");
                Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, List.class, List.class, FluidStack.class);
                methodTECentrifuge = method;
                thermalExpCentrifuge = true;
            } catch (Exception var14) {
            }
        }

        if (thermalExpCentrifuge) {
            try {
                debugprint1(input, "thermalexpansion");
                List<ItemStack> listOutputs = new ArrayList<>();
                List<Integer> listChances = new ArrayList<>();
                listOutputs.add(output1);
                listChances.add(chance1);
                if (output2 != null) {
                    listOutputs.add(output2);
                    listChances.add(chance2);
                }

                if (output3 != null) {
                    listOutputs.add(output3);
                    listChances.add(chance3);
                }

                if (output4 != null) {
                    listOutputs.add(output4);
                    listChances.add(chance4);
                }

                methodTECentrifuge.invoke(null, rf, input, listOutputs, listChances, fluid == null ? emptyfs : fluid);
                debugprint2(input);
            } catch (Exception var13) {
            }
        }
    }

    public static void addPressRecipe(int rf, ItemStack input, ItemStack output, boolean bannedForIC2) {
        if (!thermalExpCompactor) {
            try {
                Class clazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CompactorManager");
                Class compactorManagerMODEclazz = Class.forName("cofh.thermalexpansion.util.managers.machine.CompactorManager$Mode");
                Field firstField = compactorManagerMODEclazz.getFields()[0];
                Object enumModeALL = firstField.get(null);
                Method method = clazz.getMethod("addRecipe", int.class, ItemStack.class, ItemStack.class, compactorManagerMODEclazz);
                clazzCompactor = clazz;
                methodCompactor = method;
                if (clazzCompactor != null && methodCompactor != null && enumModeALL != null) {
                    thermalExpCompactor = true;
                    compactorModeALL = enumModeALL;
                }
            } catch (Exception var14) {
            }
        }

        if (!ic2compressor) {
            try {
                Class clazzInputGenerator = Class.forName("ic2.core.recipe.RecipeInputFactory");
                Method forStackMethod = clazzInputGenerator.getMethod("forStack", ItemStack.class, int.class);
                Object objInputGenerator = clazzInputGenerator.newInstance();
                Class clazzIC2recipes = Class.forName("ic2.api.recipe.Recipes");
                Field managerCompressor = clazzIC2recipes.getField("compressor");
                Object managerCompressorObject = managerCompressor.get(null);
                Class clazzIC2IRecipeInput = Class.forName("ic2.api.recipe.IRecipeInput");
                addrecipeIC2MethodCompressor = managerCompressor.getType().getMethod("addRecipe", clazzIC2IRecipeInput, NBTTagCompound.class, boolean.class, ItemStack[].class);
                ic2forStackMethod = forStackMethod;
                ic2objInputGenerator = objInputGenerator;
                ic2compressor = true;
                ic2managerCompressorObject = managerCompressorObject;
            } catch (Exception var13) {
            }
        }

        if (thermalExpCompactor) {
            try {
                methodCompactor.invoke(null, rf, input, output, compactorModeALL);
            } catch (Exception var12) {
            }
        }

        if (ic2compressor) {
            try {
                debugprint1(output, "ic2");
                if (!bannedForIC2) {
                    Object recipeIC2Input = ic2forStackMethod.invoke(ic2objInputGenerator, input.copy(), 1);
                    addrecipeIC2MethodCompressor.invoke(ic2managerCompressorObject, recipeIC2Input, new NBTTagCompound(), true, new ItemStack[]{output.copy()});
                }

                debugprint2(output);
            } catch (Exception var11) {
            }
        }

        ItemStack out2x = output.copy();
        out2x.setCount(out2x.getCount() * 2);
        AssemblyTableRecipesRegister.addRecipe(out2x, rf * 2, ItemStack.EMPTY, 200, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, input, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, new AssemblyTableRecipe.AugmentCost(AssemblyTableRecipesRegister.PRESS, new Ingridient.IngridientItem(input.getItem(), input.getCount(), input.getMetadata(), input.getHasSubtypes())), null, null, null, null);
    }

}
