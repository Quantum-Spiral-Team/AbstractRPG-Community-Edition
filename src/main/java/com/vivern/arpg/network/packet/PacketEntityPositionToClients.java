package com.vivern.arpg.network.packet;

import com.vivern.arpg.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ConcurrentModificationException;

public class PacketEntityPositionToClients extends Packet {

    int entityId = 0;
    double posX = 0.0;
    double posY = 0.0;
    double posZ = 0.0;

    public static void send(Entity entity, double posX, double posY, double posZ, double distance) {
        PacketEntityPositionToClients packet = new PacketEntityPositionToClients();
        packet.writeArgs(entity.getEntityId(), posX, posY, posZ);
        PacketHandler.sendToAllAround(packet, entity.world, entity.posX, entity.posY, entity.posZ, distance);
    }

    public void writeArgs(int entityId, double posX, double posY, double posZ) {
        this.buf().writeInt(entityId);
        this.buf().writeDouble(posX);
        this.buf().writeDouble(posY);
        this.buf().writeDouble(posZ);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.posX = buffer.readDouble();
        this.posY = buffer.readDouble();
        this.posZ = buffer.readDouble();
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
    }

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

    void processMessage(EntityPlayer player) {
        try {
            Entity entity = player.world.getEntityByID(this.entityId);
            if (entity != null) {
                entity.setPosition(this.posX, this.posY, this.posZ);
            }
        } catch (ConcurrentModificationException var3) {
            var3.printStackTrace();
        }
    }

}
