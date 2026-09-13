package com.vivern.arpg.main;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OreDicHelper {

    public static String COPPER = "ingotCopper";
    public static String LEAD = "ingotLead";
    public static String STEEL = "ingotSteel";
    public static String BRONZE = "ingotBronze";
    public static String TIN = "ingotTin";
    public static String ALUMINIUM = "ingotAluminum";
    public static String ELECTRUM = "ingotElectrum";
    public static String TITANIUM = "ingotTitanium";
    public static String BRASS = "ingotBrass";
    public static String INVAR = "ingotInvar";
    public static String SILVER = "ingotSilver";
    public static String NICKEL = "ingotNickel";
    public static String PLATINUM = "ingotPlatinum";
    public static String CHROMIUM = "ingotChromium";
    public static String BERYLLIUM = "ingotBeryllium";
    public static String MANGANESE = "ingotManganese";
    public static String ZINC = "ingotZinc";
    public static String IRIDIUM = "ingotIridium";
    public static String LITHIUM = "ingotLithium";
    public static String SILICIUM = "ingotSilicium";
    public static String RUBY = "gemRuby";
    public static String SAPPHIRE = "gemSapphire";
    public static String CITRINE = "gemCitrine";
    public static String AMETHYST = "gemAmethyst";
    public static String TOPAZ = "gemTopaz";
    public static String RHINESTONE = "gemRhinestone";
    public static String CERTUSQUARTZ = "crystalCertusQuartz";
    public static String FLUIXCRYSTAL = "crystalFluix";
    public static String DUSTSULFUR = "dustSulfur";
    public static String DUSTCHARCOAL = "dustCharcoal";
    public static String DUSTCOAL = "dustCoal";
    public static String DUSTQUARTZ = "dustQuartz";
    public static String DUSTNETHERQUARTZ = "dustNetherQuartz";
    public static String DUSTCERTUSQUARTZ = "dustCertusQuartz";
    public static String DUSTLAZULI = "dustLapis";
    public static String DUSTOBSIDIAN = "dustObsidian";
    public static String DUSTSILICONDIOXIDE = "dustSiliconDioxide";
    public static String DUSTDIAMOND = "dustDiamond";
    public static String DUSTSALTPETER = "dustSaltpeter";
    public static String DUSTSALT = "dustSalt";
    public static String DUSTFLOUR = "dustFlour";
    public static String DUSTSTONE = "dustStone";
    public static String DUSTCOPPER = "dustCopper";
    public static String DUSTLEAD = "dustLead";
    public static String DUSTSTEEL = "dustSteel";
    public static String DUSTBRONZE = "dustBronze";
    public static String DUSTTIN = "dustTin";
    public static String DUSTALUMINIUM = "dustAluminum";
    public static String DUSTELECTRUM = "dustElectrum";
    public static String DUSTTITANIUM = "dustTitanium";
    public static String DUSTBRASS = "dustBrass";
    public static String DUSTSILVER = "dustSilver";
    public static String DUSTIRON = "dustIron";
    public static String DUSTGOLD = "dustGold";
    public static String DUSTNICKEL = "dustNickel";
    public static String DUSTPLATINUM = "dustPlatinum";
    public static String DUSTCHROMIUM = "dustChromium";
    public static String DUSTBERYLLIUM = "dustBeryllium";
    public static String DUSTMANGANESE = "dustManganese";
    public static String DUSTZINC = "dustZinc";
    public static String DUSTASH = "dustAsh";
    public static String DUSTLITHIUM = "dustLithium";
    public static String NUGGETBRASS = "nuggetBrass";
    public static String NUGGETTITANIUM = "nuggetTitanium";
    public static String NUGGETZINC = "nuggetZinc";
    public static String NUGGETSILVER = "nuggetSilver";
    public static String NUGGETALUMINIUM = "nuggetAluminum";
    public static String NUGGETLITHIUM = "nuggetLithium";
    public static String QUICKSILVER = "quicksilver";
    public static String RUBBER = "itemRubber";
    public static String RUBBER2 = "materialRubber";
    public static String SILICON = "itemSilicon";
    public static String COALCOKE = "fuelCoke";
    public static String RICHSLAG = "itemSlagRich";
    public static String YEAST = "yeast";
    public static String BEETROOT = "cropBeetroot";
    public static String FLINT = "itemFlint";
    public static String COAL = "itemCoal";
    public static String CHARCOAL = "itemCharcoal";
    public static String BLAZEROD = "rodBlaze";
    public static String GHASTTEAR = "itemGhastTear";
    public static String ENDEREYE = "pearlEnderEye";
    public static String BLAZEPOWDER = "itemBlazePowder";
    public static String CLAY = "itemClay";
    public static String LEATHER = "itemLeather";
    public static String PLANKS = "plankWood";
    public static String LOG = "logWood";
    public static String CIRCUIT = "circuitBasic";
    public static String CIRCUITADVANCED = "circuitAdvanced";
    public static String PLASTIC = "materialPlastic";
    public static String PLASTICADVANCED = "materialAdvancedPlastic";
    public static String POLYMERADVANCED = "materialAdvancedPolymer";
    public static String GLASSHARDENED = "blockGlassHardened";
    public static String BLOCKSILVER = "blockSilver";
    public static String ORESILVER = "oreSilver";

    public static List<String> getOreNames(ItemStack stack) {
        List<String> list = new ArrayList<>();
        if (!stack.isEmpty()) {
            int[] ids = OreDictionary.getOreIDs(stack);

            for (int id : ids) {
                list.add(OreDictionary.getOreName(id));
            }
        }

        return list;
    }

    public static boolean hasOreName(ItemStack stack) {
        return !stack.isEmpty() && OreDictionary.getOreIDs(stack).length > 0;
    }

    public static boolean itemStringOredigMatches(ItemStack st1, String name) {
        List<String> names = getOreNames(st1);
        if (!names.isEmpty()) {
            for (String n : names) {
                if (n.equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean matches(ItemStack st1, ItemStack st2) {
        List<String> names1 = getOreNames(st1);
        if (!names1.isEmpty()) {
            for (String n : names1) {
                if (itemStringOredigMatches(st2, n)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static ItemStack get(String oredigName, int stackCount) {
        for (ItemStack ore : OreDictionary.getOres(oredigName)) {
            if (!ore.isEmpty()) {
                return new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
            }
        }

        return ItemStack.EMPTY;
    }

    public static ItemStack getButThisModInPriority(String oredigName, int stackCount) {
        NonNullList<ItemStack> ores = OreDictionary.getOres(oredigName);
        ItemStack returned = ItemStack.EMPTY;

        for (ItemStack ore : ores) {
            if (!ore.isEmpty()) {
                if (returned.isEmpty()) {
                    returned = new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
                } else {
                    ResourceLocation registryName = ore.getItem().getRegistryName();
                    String modId = registryName == null ? null : registryName.getNamespace();
                    if ("arpg".equals(modId)) {
                        returned = new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
                        break;
                    }
                }
            }
        }

        return returned;
    }

    public static ItemStack getForJei(String oredigName, int stackCount) {
        NonNullList<ItemStack> ores = OreDictionary.getOres(oredigName);
        int amount = ores.size();
        int current = (int) (Minecraft.getSystemTime() / 1000L % amount);
        ItemStack ore = ores.get(current);
        return !ore.isEmpty() ? new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound()) : ItemStack.EMPTY;
    }

    @Nullable
    public static ItemStack getOrNull(String oredigName, int stackCount) {
        NonNullList<ItemStack> ores = OreDictionary.getOres(oredigName);
        if (!ores.isEmpty()) {
            for (ItemStack ore : ores) {
                if (!ore.isEmpty()) {
                    return new ItemStack(ore.getItem(), stackCount, ore.getMetadata(), ore.getTagCompound());
                }
            }
        }

        return null;
    }

    public static IBlockState getBlock(String oredigName) {
        for (ItemStack ore : OreDictionary.getOres(oredigName)) {
            if (!ore.isEmpty() && ore.getItem() instanceof ItemBlock) {
                ItemBlock itembl = (ItemBlock) ore.getItem();
                int i = itembl.getMetadata(ore.getMetadata());
                return itembl.getBlock().getStateFromMeta(i);
            }
        }

        return Blocks.AIR.getDefaultState();
    }

    public static void init() {
        OreDictionary.registerOre(RUBY, Items.RUBY);
        OreDictionary.registerOre(SAPPHIRE, Items.SAPPHIRE);
        OreDictionary.registerOre(CITRINE, Items.CITRINE);
        OreDictionary.registerOre(AMETHYST, Items.AMETHYST);
        OreDictionary.registerOre(TOPAZ, Items.TOPAZ);
        OreDictionary.registerOre(RHINESTONE, Items.RHINESTONE);
        OreDictionary.registerOre(MANGANESE, Items.MANGANESE_INGOT);
        OreDictionary.registerOre(DUSTMANGANESE, Items.MANGANESE_DUST);
        OreDictionary.registerOre(BERYLLIUM, Items.BERYLLIUM_INGOT);
        OreDictionary.registerOre(DUSTBERYLLIUM, Items.BERYLLIUM_DUST);
        OreDictionary.registerOre(CHROMIUM, Items.CHROMIUM_INGOT);
        OreDictionary.registerOre(DUSTCHROMIUM, Items.CHROMIUM_DUST);
        OreDictionary.registerOre(DUSTSULFUR, Items.SULFUR_DUST);
        OreDictionary.registerOre(DUSTASH, Items.ASH);
        OreDictionary.registerOre(DUSTSALTPETER, Items.SALTPETER);
        OreDictionary.registerOre(DUSTSTONE, Items.STONE_DUST);
        OreDictionary.registerOre(BRASS, Items.BRASS_INGOT);
        OreDictionary.registerOre(DUSTBRASS, Items.BRASS_DUST);
        OreDictionary.registerOre(NUGGETBRASS, Items.BRASS_NUGGET);
        OreDictionary.registerOre(TITANIUM, Items.TITANIUM_INGOT);
        OreDictionary.registerOre(DUSTTITANIUM, Items.TITANIUM_DUST);
        OreDictionary.registerOre(NUGGETTITANIUM, Items.TITANIUM_NUGGET);
        OreDictionary.registerOre(SILVER, Items.SILVER_INGOT);
        OreDictionary.registerOre(DUSTSILVER, Items.SILVER_DUST);
        OreDictionary.registerOre(NUGGETSILVER, Items.SILVER_NUGGET);
        OreDictionary.registerOre(ALUMINIUM, Items.ALUMINIUM_INGOT);
        OreDictionary.registerOre(DUSTALUMINIUM, Items.ALUMINIUM_DUST);
        OreDictionary.registerOre(NUGGETALUMINIUM, Items.ALUMINIUM_NUGGET);
        OreDictionary.registerOre(ZINC, Items.ZINC_INGOT);
        OreDictionary.registerOre(DUSTZINC, Items.ZINC_DUST);
        OreDictionary.registerOre(NUGGETZINC, Items.ZINC_NUGGET);
        OreDictionary.registerOre(PLANKS, BlocksRegister.FIERY_BEAN_PLANKS);
        OreDictionary.registerOre(PLANKS, BlocksRegister.PALM_PLANKS);
        OreDictionary.registerOre(PLANKS, BlocksRegister.ROTTEN_PLANKS);
        OreDictionary.registerOre(LOG, BlocksRegister.FIERY_BEAN_LOG);
        OreDictionary.registerOre(LOG, BlocksRegister.PALM_LOG);
        OreDictionary.registerOre(CIRCUIT, Items.CIRCUIT);
        OreDictionary.registerOre(CIRCUITADVANCED, Items.ADVANCED_CIRCUIT);
        OreDictionary.registerOre(RUBBER, Items.RUBBER);
        OreDictionary.registerOre(RUBBER2, Items.RUBBER);
        OreDictionary.registerOre(PLASTIC, Items.SLIME_PLASTIC);
        OreDictionary.registerOre(PLASTIC, Items.PLASTIC);
        OreDictionary.registerOre(PLASTICADVANCED, Items.PLASTIC);
        OreDictionary.registerOre(POLYMERADVANCED, Items.ADVANCED_POLYMER);
        OreDictionary.registerOre(DUSTSALT, Items.SALT);
        OreDictionary.registerOre(YEAST, Items.YEAST);
        OreDictionary.registerOre(DUSTFLOUR, Items.FLOUR);
        OreDictionary.registerOre(DUSTQUARTZ, Items.QUARTZ_DUST);
        OreDictionary.registerOre(GLASSHARDENED, BlocksRegister.CHROMIUM_GLASS);
        OreDictionary.registerOre(BLOCKSILVER, BlocksRegister.SILVER_BLOCK);
        OreDictionary.registerOre(ORESILVER, BlocksRegister.SILVER_ORE);
        OreDictionary.registerOre(LITHIUM, Items.LITHIUM_INGOT);
        OreDictionary.registerOre(DUSTLITHIUM, Items.LITHIUM_DUST);
        OreDictionary.registerOre(NUGGETLITHIUM, Items.LITHIUM_NUGGET);
        OreDictionary.registerOre(SILICIUM, Items.SILICIUM);
        OreDictionary.registerOre("itemSilicon", Items.SILICIUM);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.FLINT, FLINT);
        regiserOreIfNameNoAdded(new ItemStack(net.minecraft.init.Items.COAL, 1, 0), COAL);
        regiserOreIfNameNoAdded(new ItemStack(net.minecraft.init.Items.COAL, 1, 1), CHARCOAL);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.BLAZE_ROD, BLAZEROD);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.GHAST_TEAR, GHASTTEAR);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.ENDER_EYE, ENDEREYE);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.BLAZE_POWDER, BLAZEPOWDER);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.BEETROOT, BEETROOT);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.CLAY_BALL, CLAY);
        regiserOreIfNameNoAdded(net.minecraft.init.Items.LEATHER, LEATHER);
        regiserOreIfNameNoAdded(new ItemStack(Blocks.IRON_BARS), "barsIron");
    }

    public static void regiserOreIfNameNoAdded(Item item, String name) {
        regiserOreIfNameNoAdded(new ItemStack(item), name);
    }

    public static void regiserOreIfNameNoAdded(ItemStack itemStack, String name) {
        if (!itemStringOredigMatches(itemStack, name)) {
            OreDictionary.registerOre(name, itemStack);
        }
    }

    public static ItemStack getMissingItemStack(String nameOrOredicName, int amount) {
        if (nameOrOredicName.contains("dust")) {
            return new ItemStack(Items.MISSING_DUST, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
        } else if (nameOrOredicName.contains("ingot")) {
            return new ItemStack(Items.MISSING_INGOT, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
        } else {
            return nameOrOredicName.contains("nugget") ? new ItemStack(Items.MISSING_NUGGET, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName) : new ItemStack(Items.MISSING_MATERIAL, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
        }
    }

    public static boolean isMissing(Item item) {
        return item == Items.MISSING_MATERIAL || item == Items.MISSING_INGOT || item == Items.MISSING_DUST || item == Items.MISSING_NUGGET;
    }

    public static boolean doesOreNameExist(String name) {
        for (ItemStack ore : OreDictionary.getOres(name)) {
            if (!ore.isEmpty()) {
                return true;
            }
        }

        return false;
    }

}
