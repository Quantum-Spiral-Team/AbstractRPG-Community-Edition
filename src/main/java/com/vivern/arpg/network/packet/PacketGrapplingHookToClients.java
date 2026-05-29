package com.vivern.arpg.network.packet;

import com.vivern.arpg.items.GraplingHook;
import com.vivern.arpg.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGrapplingHookToClients extends Packet {
   double x = 0.0;
   double y = 0.0;
   double z = 0.0;
   int entityId = 0;
   int itemId = 0;

   public static void send(EntityPlayer player, double x, double y, double z, Item graplingHook) {
      PacketGrapplingHookToClients packet = new PacketGrapplingHookToClients();
      packet.writeArgs(x, y, z, player.getEntityId(), Item.getIdFromItem(graplingHook));
      PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, 64.0);
   }

   public void writeArgs(double x, double y, double z, int identity, int iditem) {
      this.buf().writeDouble(x);
      this.buf().writeDouble(y);
      this.buf().writeDouble(z);
      this.buf().writeInt(identity);
      this.buf().writeInt(iditem);
   }

   @Override
   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readDouble();
      this.y = buffer.readDouble();
      this.z = buffer.readDouble();
      this.entityId = buffer.readInt();
      this.itemId = buffer.readInt();
   }

   @Override
   public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player.world));
   }

   @Override
   public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

   void processMessage(World world) {
      try {
         Entity entity = world.getEntityByID(this.entityId);
         if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            Item item = Item.getItemById(this.itemId);
            if (item instanceof GraplingHook) {
               ((GraplingHook) item).spawnParticle(player.getPositionEyes(0.0F), new Vec3d(this.x, this.y, this.z), world, player);
            }
         }
      } catch (ConcurrentModificationException var5) {
         var5.printStackTrace();
      }
   }
}
