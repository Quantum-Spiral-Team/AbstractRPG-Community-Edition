package com.vivern.arpg.network.packet;

import com.vivern.arpg.entity.EntityCoin;
import com.vivern.arpg.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCoinToServer extends Packet {
   public int entityId = 0;
   public boolean isMessage;

   public void writeInts(int entityid) {
      this.buf().writeInt(entityid);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.entityId = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityLivingBase player) {
      try {
         Entity en = player.world.getEntityByID(this.entityId);
         if (en instanceof EntityCoin && player instanceof EntityPlayerMP) {
            EntityCoin coin = (EntityCoin)en;
            PacketCoinToClient packet = new PacketCoinToClient();
            packet.write(coin.store, this.entityId);
            PacketHandler.sendTo(packet, (EntityPlayerMP)player);
         }
      } catch (ConcurrentModificationException var5) {
         var5.printStackTrace();
      }
   }
}
