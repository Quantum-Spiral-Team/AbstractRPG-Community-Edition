package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.arpgfix.ItemKeyPressInfo;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public abstract class ItemKeyPressInfo_Armor extends ItemArmor implements ItemKeyPressInfo {
    public ItemKeyPressInfo_Armor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
    }
}