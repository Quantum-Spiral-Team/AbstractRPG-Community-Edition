package com.vivern.arpg.main;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

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
      int current = (int)(Minecraft.getSystemTime() / 1000L % amount);
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
            ItemBlock itembl = (ItemBlock)ore.getItem();
            int i = itembl.getMetadata(ore.getMetadata());
            return itembl.getBlock().getStateFromMeta(i);
         }
      }

      return Blocks.AIR.getDefaultState();
   }

   public static void init() {
      OreDictionary.registerOre(RUBY, ItemsRegister.RUBY);
      OreDictionary.registerOre(SAPPHIRE, ItemsRegister.SAPPHIRE);
      OreDictionary.registerOre(CITRINE, ItemsRegister.CITRINE);
      OreDictionary.registerOre(AMETHYST, ItemsRegister.AMETHYST);
      OreDictionary.registerOre(TOPAZ, ItemsRegister.TOPAZ);
      OreDictionary.registerOre(RHINESTONE, ItemsRegister.RHINESTONE);
      OreDictionary.registerOre(MANGANESE, ItemsRegister.MANGANESE_INGOT);
      OreDictionary.registerOre(DUSTMANGANESE, ItemsRegister.MANGANESE_DUST);
      OreDictionary.registerOre(BERYLLIUM, ItemsRegister.BERYLLIUM_INGOT);
      OreDictionary.registerOre(DUSTBERYLLIUM, ItemsRegister.BERYLLIUM_DUST);
      OreDictionary.registerOre(CHROMIUM, ItemsRegister.CHROMIUM_INGOT);
      OreDictionary.registerOre(DUSTCHROMIUM, ItemsRegister.CHROMIUM_DUST);
      OreDictionary.registerOre(DUSTSULFUR, ItemsRegister.SULFUR_DUST);
      OreDictionary.registerOre(DUSTASH, ItemsRegister.ASH);
      OreDictionary.registerOre(DUSTSALTPETER, ItemsRegister.SALTPETER);
      OreDictionary.registerOre(DUSTSTONE, ItemsRegister.STONE_DUST);
      OreDictionary.registerOre(BRASS, ItemsRegister.BRASS_INGOT);
      OreDictionary.registerOre(DUSTBRASS, ItemsRegister.BRASS_DUST);
      OreDictionary.registerOre(NUGGETBRASS, ItemsRegister.BRASS_NUGGET);
      OreDictionary.registerOre(TITANIUM, ItemsRegister.TITANIUM_INGOT);
      OreDictionary.registerOre(DUSTTITANIUM, ItemsRegister.TITANIUM_DUST);
      OreDictionary.registerOre(NUGGETTITANIUM, ItemsRegister.TITANIUM_NUGGET);
      OreDictionary.registerOre(SILVER, ItemsRegister.SILVER_INGOT);
      OreDictionary.registerOre(DUSTSILVER, ItemsRegister.SILVER_DUST);
      OreDictionary.registerOre(NUGGETSILVER, ItemsRegister.SILVER_NUGGET);
      OreDictionary.registerOre(ALUMINIUM, ItemsRegister.ALUMINIUM_INGOT);
      OreDictionary.registerOre(DUSTALUMINIUM, ItemsRegister.ALUMINIUM_DUST);
      OreDictionary.registerOre(NUGGETALUMINIUM, ItemsRegister.ALUMINIUM_NUGGET);
      OreDictionary.registerOre(ZINC, ItemsRegister.ZINC_INGOT);
      OreDictionary.registerOre(DUSTZINC, ItemsRegister.ZINC_DUST);
      OreDictionary.registerOre(NUGGETZINC, ItemsRegister.ZINC_NUGGET);
      OreDictionary.registerOre(PLANKS, BlocksRegister.FIERY_BEAN_PLANKS);
      OreDictionary.registerOre(PLANKS, BlocksRegister.PALM_PLANKS);
      OreDictionary.registerOre(PLANKS, BlocksRegister.ROTTEN_PLANKS);
      OreDictionary.registerOre(LOG, BlocksRegister.FIERY_BEAN_LOG);
      OreDictionary.registerOre(LOG, BlocksRegister.PALM_LOG);
      OreDictionary.registerOre(CIRCUIT, ItemsRegister.CIRCUIT);
      OreDictionary.registerOre(CIRCUITADVANCED, ItemsRegister.ADVANCED_CIRCUIT);
      OreDictionary.registerOre(RUBBER, ItemsRegister.RUBBER);
      OreDictionary.registerOre(RUBBER2, ItemsRegister.RUBBER);
      OreDictionary.registerOre(PLASTIC, ItemsRegister.SLIME_PLASTIC);
      OreDictionary.registerOre(PLASTIC, ItemsRegister.PLASTIC);
      OreDictionary.registerOre(PLASTICADVANCED, ItemsRegister.PLASTIC);
      OreDictionary.registerOre(POLYMERADVANCED, ItemsRegister.ADVANCED_POLYMER);
      OreDictionary.registerOre(DUSTSALT, ItemsRegister.SALT);
      OreDictionary.registerOre(YEAST, ItemsRegister.YEAST);
      OreDictionary.registerOre(DUSTFLOUR, ItemsRegister.FLOUR);
      OreDictionary.registerOre(DUSTQUARTZ, ItemsRegister.QUARTZ_DUST);
      OreDictionary.registerOre(GLASSHARDENED, BlocksRegister.CHROMIUM_GLASS);
      OreDictionary.registerOre(BLOCKSILVER, BlocksRegister.SILVER_BLOCK);
      OreDictionary.registerOre(ORESILVER, BlocksRegister.SILVER_ORE);
      OreDictionary.registerOre(LITHIUM, ItemsRegister.LITHIUM_INGOT);
      OreDictionary.registerOre(DUSTLITHIUM, ItemsRegister.LITHIUM_DUST);
      OreDictionary.registerOre(NUGGETLITHIUM, ItemsRegister.LITHIUM_NUGGET);
      OreDictionary.registerOre(SILICIUM, ItemsRegister.SILICIUM);
      OreDictionary.registerOre("itemSilicon", ItemsRegister.SILICIUM);
      regiserOreIfNameNoAdded(Items.FLINT, FLINT);
      regiserOreIfNameNoAdded(new ItemStack(Items.COAL, 1, 0), COAL);
      regiserOreIfNameNoAdded(new ItemStack(Items.COAL, 1, 1), CHARCOAL);
      regiserOreIfNameNoAdded(Items.BLAZE_ROD, BLAZEROD);
      regiserOreIfNameNoAdded(Items.GHAST_TEAR, GHASTTEAR);
      regiserOreIfNameNoAdded(Items.ENDER_EYE, ENDEREYE);
      regiserOreIfNameNoAdded(Items.BLAZE_POWDER, BLAZEPOWDER);
      regiserOreIfNameNoAdded(Items.BEETROOT, BEETROOT);
      regiserOreIfNameNoAdded(Items.CLAY_BALL, CLAY);
      regiserOreIfNameNoAdded(Items.LEATHER, LEATHER);
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
         return new ItemStack(ItemsRegister.MISSING_DUST, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
      } else if (nameOrOredicName.contains("ingot")) {
         return new ItemStack(ItemsRegister.MISSING_INGOT, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
      } else {
         return nameOrOredicName.contains("nugget")
            ? new ItemStack(ItemsRegister.MISSING_NUGGET, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName)
            : new ItemStack(ItemsRegister.MISSING_MATERIAL, amount).setStackDisplayName("Missing mod item: " + nameOrOredicName);
      }
   }

   public static boolean isMissing(Item item) {
      return item == ItemsRegister.MISSING_MATERIAL
         || item == ItemsRegister.MISSING_INGOT
         || item == ItemsRegister.MISSING_DUST
         || item == ItemsRegister.MISSING_NUGGET;
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
