package com.vivern.arpg.network.packet;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.potions.AdvancedPotion;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class PacketAdvPotionToClients extends Packet {

    NBTTagCompound stackNBT;
    int index = 0;
    int duration = 0;
    int amplifier = 0;
    int entityId = 0;

    public void writeArgs(ItemStack itemstack, int index, int duration, int amplifier, int entityId) {
        this.buf().writeInt(index);
        this.buf().writeInt(duration);
        this.buf().writeInt(amplifier);
        this.buf().writeInt(entityId);
        ByteBufUtils.writeTag(this.buf(), itemstack.getTagCompound());
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.index = buffer.readInt();
        this.duration = buffer.readInt();
        this.amplifier = buffer.readInt();
        this.entityId = buffer.readInt();
        this.stackNBT = ByteBufUtils.readTag(buffer);
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
    }

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

    void processMessage(World world) {
        try {
            Entity entity = world.getEntityByID(this.entityId);
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) entity;
                if (this.duration == 0) {
                    living.removeActivePotionEffect(AdvancedPotion.potionByIndex.get(this.index));
                } else {
                    AdvancedPotion potion = AdvancedPotion.potionByIndex.get(this.index);
                    living.removeActivePotionEffect(potion);
                    PotionEffect effect = new PotionEffect(potion, this.duration, this.amplifier);
                    ItemStack stack = new ItemStack(ItemsRegister.EXP);
                    stack.setTagCompound(this.stackNBT);
                    List<ItemStack> stacks = new ArrayList<>();
                    stacks.add(stack);
                    effect.setCurativeItems(stacks);
                    living.addPotionEffect(effect);
                }
            }
        } catch (ConcurrentModificationException var8) {
            var8.printStackTrace();
        }
    }

}
