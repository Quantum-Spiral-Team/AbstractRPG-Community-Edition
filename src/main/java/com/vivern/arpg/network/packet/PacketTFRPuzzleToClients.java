package com.vivern.arpg.network.packet;

import com.vivern.arpg.container.GUIResearchTable;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTFRPuzzleToClients extends Packet {
   public NBTTagCompound tagCompound;

   public void write(NBTTagCompound tag) {
      ByteBufUtils.writeTag(this.buf(), tag);
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      this.tagCompound = ByteBufUtils.readTag(buf);
      this.buf = buf;
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(this.tagCompound));
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage(NBTTagCompound tagCompound) {
      try {
         GUIResearchTable.setPuzzleFromTag(tagCompound);
      } catch (ConcurrentModificationException | NullPointerException e) {
         e.printStackTrace();
      }
   }
}
