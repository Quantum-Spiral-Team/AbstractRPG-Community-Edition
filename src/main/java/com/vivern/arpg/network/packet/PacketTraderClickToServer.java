package com.vivern.arpg.network.packet;

import com.vivern.arpg.mobs.NPCMobsPack;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTraderClickToServer extends Packet {
   public int id = 0;
   public int mx = 0;
   public int my = 0;
   public int key = 0;
   public boolean isMessage;
   public int page = 0;

   public void writeInts(int id, int mouseX, int mouseY, int mouseButton, int page) {
      this.buf().writeInt(id);
      this.buf().writeInt(mouseX);
      this.buf().writeInt(mouseY);
      this.buf().writeInt(mouseButton);
      this.buf().writeInt(page);
      this.isMessage = true;
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.id = buffer.readInt();
      this.mx = buffer.readInt();
      this.my = buffer.readInt();
      this.key = buffer.readInt();
      this.page = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
   }

   void processMessage(EntityPlayerMP player) {
      try {
         Entity en = player.world.getEntityByID(this.id);
         if (en instanceof NPCMobsPack.NpcTrader) {
            NPCMobsPack.NpcTrader trader = (NPCMobsPack.NpcTrader)en;
            trader.guiclick(player, this.mx, this.my, this.key, this.page);
         }
      } catch (ConcurrentModificationException var4) {
         var4.printStackTrace();
      }
   }
}
