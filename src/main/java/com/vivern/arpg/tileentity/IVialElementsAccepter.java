package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.recipes.EnergyCost;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IVialElementsAccepter {

    float acceptVialElements(ItemStack var1, ShardType var2, float var3);

    float getElementCount(ShardType var1);

    @Nullable
    default EnergyCost provideVialElements(ItemStack vial_or_spellRod, float amount, boolean dontDecreaseIfLow) {
        return null;
    }

}
