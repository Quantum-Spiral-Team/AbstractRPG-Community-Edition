package com.vivern.arpg.network.packet;

import com.vivern.arpg.renders.ManaBar;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSmallSomethingToClients extends Packet {
   double x = 0.0;
   int id = 0;

   public void writeArgs(int id, double x) {
      this.buf().writeDouble(x);
      this.buf().writeInt(id);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readDouble();
      this.id = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(this::processMessage);
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage() {
      try {
         if (this.id == 1) {
            ManaBar.energy_bars_enable = true;
         }

         if (this.id == 2) {
            ManaBar.leadershipUsed = (int)this.x;
         }
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
