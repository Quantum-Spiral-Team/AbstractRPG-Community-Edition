package com.vivern.arpg.network.packet;

import com.vivern.arpg.tileentity.IManaBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketManaBufferToClients extends Packet {
   float mana;
   int posX = 0;
   int posY = 0;
   int posZ = 0;

   public void writeArgs(int posx, int posy, int posz, float mana) {
      this.buf().writeInt(posx);
      this.buf().writeInt(posy);
      this.buf().writeInt(posz);
      this.buf().writeFloat(mana);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.posX = buffer.readInt();
      this.posY = buffer.readInt();
      this.posZ = buffer.readInt();
      this.mana = buffer.readFloat();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      BlockPos poss = new BlockPos(this.posX, this.posY, this.posZ);
      TileEntity tileEntity = player.world.getTileEntity(poss);
      if (tileEntity instanceof IManaBuffer) {
         IManaBuffer manaBuffer = (IManaBuffer) tileEntity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(manaBuffer, this.mana));
      }
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage(IManaBuffer manaBuffer, float mana) {
      manaBuffer.getManaBuffer().setMana(mana);
   }
}
