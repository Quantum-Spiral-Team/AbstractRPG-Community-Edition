package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElementalAmmoFire extends Item {
   public ElementalAmmoFire() {
      this.setRegistryName("fire_focus_ammo");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("fire_focus_ammo");
   }
}
