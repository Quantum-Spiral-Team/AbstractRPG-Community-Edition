package com.vivern.arpg.network.packet;

import com.vivern.arpg.entity.ISynchronizedEntity;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIESToClients extends Packet {
   double[] args;
   int id = 0;

   public void writeArgs(double x, double y, double z, double a, double b, double c, int id) {
      this.writeArgs(id, x, y, z, a, b, c);
   }

   public void writeArgs(int id, double... args) {
      this.buf().writeInt(id);

      for (double arg : args) {
          this.buf().writeDouble(arg);
      }
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      int count = (buffer.capacity() - 4) / 8;
      this.id = buffer.readInt();
      this.args = new double[Math.max(count, 1)];

      for (int i = 0; i < count; i++) {
         this.args[i] = buffer.readDouble();
      }
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Entity entity = player.world.getEntityByID(this.id);
      if (entity instanceof ISynchronizedEntity) {
         ISynchronizedEntity synchronizedEntity = (ISynchronizedEntity)entity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(synchronizedEntity));
      }
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage(ISynchronizedEntity entity) {
      try {
         entity.onClient(this.args);
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
