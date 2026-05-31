package com.vivern.arpg.items;

import com.vivern.arpg.main.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class ItemBlockHasSubtypes extends ItemBlock {

    public ItemBlockHasSubtypes(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.addPropertyOverride(new ResourceLocation("rtype"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return NBTHelper.GetNBTint(stack, "type");
            }
        });
    }

    @Override
    public int getMaxDamage() {
        return 0;
    }

}
