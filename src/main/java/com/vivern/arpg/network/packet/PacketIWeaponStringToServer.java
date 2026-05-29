package com.vivern.arpg.network.packet;

import com.vivern.arpg.items.IWeapon;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIWeaponStringToServer extends Packet {
   public String string;

   public void write(String str) {
      ByteBufUtils.writeUTF8String(this.buf(), str);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.string = ByteBufUtils.readUTF8String(buffer);
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {

   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         ItemStack stack = player.getHeldItemMainhand();
         if (!stack.isEmpty() && stack.getItem() instanceof IWeapon) {
            ((IWeapon)stack.getItem()).receiveClientString(stack, player, this.string);
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
