package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ElementalAmmoEarth extends Item {

    public ElementalAmmoEarth() {
        this.setRegistryName("earth_focus_ammo");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("earth_focus_ammo");
    }

}
