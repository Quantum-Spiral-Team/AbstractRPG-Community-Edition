package com.vivern.arpg.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemItem extends Item {

    public int burntime = -1;
    public boolean beacon = false;
    public boolean ench = false;

    public ItemItem(String name, CreativeTabs tab, int maxdamage, int maxstacksize) {
        this.setRegistryName(name);
        this.setCreativeTab(tab);
        this.setTranslationKey(name);
        this.setMaxDamage(maxdamage);
        this.setMaxStackSize(maxstacksize);
    }

    public ItemItem setFuel(int time) {
        this.burntime = time;
        return this;
    }

    public ItemItem setBeacon() {
        this.beacon = true;
        return this;
    }

    public ItemItem setEnchantGlow() {
        this.ench = true;
        return this;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return this.burntime * 20;
    }

    @Override
    public boolean isBeaconPayment(ItemStack stack) {
        return this.beacon ? this.beacon : super.isBeaconPayment(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return this.ench || super.hasEffect(stack);
    }

}
