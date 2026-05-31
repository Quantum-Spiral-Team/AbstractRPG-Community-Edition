package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class AntimatterCharge extends Item {

    public AntimatterCharge() {
        this.setRegistryName("antimatter_charge");
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setTranslationKey("antimatter_charge");
    }

}
