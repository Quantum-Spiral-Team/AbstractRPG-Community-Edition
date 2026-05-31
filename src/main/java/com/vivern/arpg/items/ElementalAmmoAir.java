package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElementalAmmoAir extends Item {

    public ElementalAmmoAir() {
        this.setRegistryName("air_focus_ammo");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("air_focus_ammo");
    }

}
