package com.vivern.arpg.main;

import com.vivern.arpg.items.armor.SnowcoatHelm;
import com.vivern.arpg.recipes.MoltenGreataxeOil;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

@EventBusSubscriber(
   modid = "arpg"
)
public class RecipesRegister {
   @SubscribeEvent
   public static void registerRecipes(Register<IRecipe> event) {
      event.getRegistry().register(new MoltenGreataxeOil().setRegistryName(MoltenGreataxeOil.name));
      event.getRegistry().register(new SnowcoatHelm.SnowcoatDye().setRegistryName(SnowcoatHelm.SnowcoatDye.name));
   }

   public static IRecipe getShapelessRecipe(ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Ingredient... params) {
      NonNullList<Ingredient> lst = NonNullList.create();

      Collections.addAll(lst, params);

      return new ShapelessRecipes(group == null ? "" : group.toString(), output, lst).setRegistryName(name);
   }
}
