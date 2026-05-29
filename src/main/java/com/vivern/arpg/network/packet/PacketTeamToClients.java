package com.vivern.arpg.network.packet;

import com.vivern.arpg.mobs.AbstractMob;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTeamToClients extends Packet {
   String team;
   int id = 0;

   public void writeArgs(String team, int id) {
      this.buf().writeInt(id);
      ByteBufUtils.writeUTF8String(this.buf(), team);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.id = buffer.readInt();
      this.team = ByteBufUtils.readUTF8String(buffer);
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      Entity entity = player.world.getEntityByID(this.id);
      if (entity instanceof AbstractMob) {
         AbstractMob abstractMob = (AbstractMob) entity;
         FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(abstractMob));
      }
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage(AbstractMob abstractMob) {
      try {
         abstractMob.team = this.team;
      } catch (ConcurrentModificationException var3) {
         var3.printStackTrace();
      }
   }
}
