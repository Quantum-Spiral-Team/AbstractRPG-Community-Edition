package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElementalAmmoWater extends Item {

    public ElementalAmmoWater() {
        this.setRegistryName("water_focus_ammo");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("water_focus_ammo");
    }

}
