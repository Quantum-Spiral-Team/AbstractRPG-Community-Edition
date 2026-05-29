package com.vivern.arpg.network.packet;

import com.vivern.arpg.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class SPacketPotionParticles extends Packet {
   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
      PacketHandler.sendToAllAround(
         new CPacketPotionParticles(player.posX, player.posY, player.posZ),
         player.world,
         player.posX,
         player.posY,
         player.posZ,
         30.0
      );
   }
}
