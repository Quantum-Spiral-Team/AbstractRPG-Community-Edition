package com.vivern.arpg.items;

import com.vivern.arpg.main.Mana;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GeigerCounter extends Item {

    public GeigerCounter() {
        this.setRegistryName("geiger_counter");
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setTranslationKey("geiger_counter");
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entityIn;
            if (player.getHeldItemMainhand() == stack || player.getHeldItemOffhand() == stack) {
                TextComponentString component = new TextComponentString(TextFormatting.GREEN + "Your radiation: " + Mana.getRad(player));
                SPacketTitle packet = new SPacketTitle(SPacketTitle.Type.ACTIONBAR, component);
                player.connection.sendPacket(packet);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (Minecraft.getMinecraft().player != null) {
            tooltip.add(TextFormatting.GREEN + "Your radiation: " + Mana.getRad(Minecraft.getMinecraft().player));
        }
    }

}
