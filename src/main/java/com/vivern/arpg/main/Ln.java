package com.vivern.arpg.main;

import com.vivern.arpg.items.IWeapon;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;

import static com.vivern.arpg.AbstractRPG.LOGGER;

@SuppressWarnings("deprecation")
public class Ln {
   public static String translate(String input) {
      return I18n.translateToLocal(input);
   }

   public static String localizedName(Item input) {
      return I18n.translateToLocal(input.getTranslationKey());
   }

   public static void printDescriptionsSample() {
      LOGGER.info("\n");

      for (Item item : Item.REGISTRY) {
         if (item instanceof IWeapon) {
            String name = item.getRegistryName().getPath();
            LOGGER.info("description.{}=", name);
            LOGGER.info("\n");
            LOGGER.info("descspecial.{}=", name);
            LOGGER.info("\n");
         }
      }

      LOGGER.info("\n");
   }

   public static boolean canTranslate(String input) {
      return I18n.canTranslate(input);
   }
}
