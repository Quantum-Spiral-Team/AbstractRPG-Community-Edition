package com.vivern.arpg.jei;

import com.vivern.arpg.main.*;
import com.vivern.arpg.recipes.*;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

@JEIPlugin
public class ARPGPlugin implements IModPlugin {

    public static CategoryNetherMelter categoryNetherMelter;
    public static CategoryAssemblyTable categoryAssemblyTable;
    public static CategorySpellForge categorySpellForge;
    public static CategoryIndustrialMixer categoryIndustrialMixer;
    public static CategoryAlchemicLab categoryAlchemicLab;
    public static CategorySieve categorySieve;
    public static IGuiHelper guihelper = null;
    static long prevTime = 0L;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        guihelper = registry.getJeiHelpers().getGuiHelper();
        categoryNetherMelter = new CategoryNetherMelter(guihelper);
        categoryAssemblyTable = new CategoryAssemblyTable(guihelper);
        categorySpellForge = new CategorySpellForge(guihelper);
        categoryIndustrialMixer = new CategoryIndustrialMixer(guihelper);
        categoryAlchemicLab = new CategoryAlchemicLab(guihelper);
        categorySieve = new CategorySieve(guihelper);
        registry.addRecipeCategories(new IRecipeCategory[]{categoryNetherMelter, categoryAssemblyTable, categorySpellForge, categoryIndustrialMixer, categoryAlchemicLab, categorySieve});
    }

    @Override
    public void register(IModRegistry registry) {
        ArrayList<WrapperNetherMelter> listNM = new ArrayList<>();

        for (NetherMelterRecipe recipe : NetherMelterRecipesRegister.allRecipes) {
            WrapperNetherMelter wrapper = new WrapperNetherMelter(recipe);
            listNM.add(wrapper);
        }

        registry.addRecipes(listNM, "arpg:nether_melter");
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.NETHER_MELTER), new String[]{"arpg:nether_melter"});
        ArrayList<WrapperAssemblyTable> listAT = new ArrayList<>();

        for (AssemblyTableRecipe recipe : AssemblyTableRecipesRegister.allRecipes) {
            WrapperAssemblyTable wrapper = new WrapperAssemblyTable(recipe);
            listAT.add(wrapper);
        }

        registry.addRecipes(listAT, "arpg:assembly_table");
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.ASSEMBLY_TABLE), new String[]{"arpg:assembly_table"});
        ArrayList<WrapperSpellForge> listSF = new ArrayList<>();

        for (SpellForgeRecipe recipe : SpellForgeRecipesRegister.allRecipes) {
            WrapperSpellForge wrapper = new WrapperSpellForge(recipe);
            listSF.add(wrapper);
        }

        registry.addRecipes(listSF, "arpg:spell_forge");
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.SPELL_FORGE), new String[]{"arpg:spell_forge"});
        ArrayList<WrapperIndustrialMixer> listIM = new ArrayList<>();

        for (IndustrialMixerRecipe recipe : IndustrialMixerRecipesRegister.allRecipes) {
            WrapperIndustrialMixer wrapper = new WrapperIndustrialMixer(recipe);
            if (wrapper.initFluids()) {
                listIM.add(wrapper);
            }
        }

        registry.addRecipes(listIM, "arpg:industrial_mixer");
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.INDUSTRIAL_MIXER), new String[]{"arpg:industrial_mixer"});
        ArrayList<WrapperAlchemicLab> listAL = new ArrayList<>();

        for (AlchemicLabRecipe recipex : AlchemicLabRecipesRegister.allRecipes) {
            WrapperAlchemicLab wrapper = new WrapperAlchemicLab(recipex);
            if (wrapper.initFluids()) {
                listAL.add(wrapper);
            }
        }

        registry.addRecipes(listAL, "arpg:alchemic_lab");
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.ALCHEMIC_LAB), new String[]{"arpg:alchemic_lab"});
        ArrayList<WrapperSieve> listSI = new ArrayList<>();

        for (SieveRecipe recipexx : SieveRecipesRegister.allRecipes) {
            WrapperSieve wrapper = new WrapperSieve(recipexx);
            listSI.add(wrapper);
        }

        registry.addRecipes(listSI, "arpg:sieve");
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.SIEVE), new String[]{"arpg:sieve"});
        registry.addRecipeCatalyst(new ItemStack(BlocksRegister.ELECTRIC_SIEVE), new String[]{"arpg:sieve"});
    }

    public static void tryFixOredicShow(Minecraft minecraft) {
    }

    public static void tick() {
        tryFixOredicShow(Minecraft.getMinecraft());
    }

}
