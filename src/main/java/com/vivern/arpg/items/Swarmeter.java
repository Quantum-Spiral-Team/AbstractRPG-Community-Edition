package com.vivern.arpg.items;

import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.MobSpawn;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class Swarmeter extends Item {

    public Swarmeter() {
        this.setRegistryName("swarmeter");
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setTranslationKey("swarmeter");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (!world.isRemote && player instanceof EntityPlayerMP) {
            int st = Mana.getSwarmTicks(player);
            String message = "Swarm points: " + Mana.getSwarmPoints(player) + " | ticks: " + st + " | SWM: " + Mana.getSwarmsWithoutMiniboss(player);
            MobSpawn spawn = MobSpawn.spawnByDimension.get(player.dimension);
            if (spawn != null && spawn.enableSwarms) {
                message = message + " | ticks%: " + st % spawn.getSwarmFrequency();
            }

            player.sendMessage(new TextComponentString(message));
        }

        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Provides information about swarms. For debug or better understanding");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
