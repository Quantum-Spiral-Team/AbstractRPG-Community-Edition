package com.vivern.arpg.network.packet;

import com.vivern.arpg.tileentity.ITileEntitySynchronized;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTileEntityToClients extends Packet {
   double[] args;
   int posX;
   int posY;
   int posZ;

   public void writeArgs(int posx, int posy, int posz, double... args) {
      this.buf().writeInt(posx);
      this.buf().writeInt(posy);
      this.buf().writeInt(posz);

      for (double arg : args) {
          this.buf().writeDouble(arg);
      }
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      int count = (buffer.capacity() - 12) / 8;
      this.posX = buffer.readInt();
      this.posY = buffer.readInt();
      this.posZ = buffer.readInt();
      this.args = new double[Math.max(count, 1)];

      for (int i = 0; i < count; i++) {
         this.args[i] = buffer.readDouble();
      }
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      TileEntity tileEntity = player.world.getTileEntity(new BlockPos(this.posX, this.posY, this.posZ));
      if (tileEntity instanceof ITileEntitySynchronized) {
         ITileEntitySynchronized synchronizedTE = (ITileEntitySynchronized)tileEntity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(synchronizedTE));
      }
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage(ITileEntitySynchronized synchronizedTE) {
      try {
         synchronizedTE.onClient(this.args);
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
