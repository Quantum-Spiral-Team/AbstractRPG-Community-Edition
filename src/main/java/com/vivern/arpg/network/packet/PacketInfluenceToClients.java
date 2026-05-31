package com.vivern.arpg.network.packet;

import com.vivern.arpg.main.EntityInfluence;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ConcurrentModificationException;

public class PacketInfluenceToClients extends Packet {

    int influenceId = 0;
    int entityId = 0;

    public void writeArgs(int influenceId, int entityId) {
        this.buf().writeInt(influenceId);
        this.buf().writeInt(entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.influenceId = buffer.readInt();
        this.entityId = buffer.readInt();
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
            if (entity != null) {
                EntityInfluence.addEntityInfluence(entity, EntityInfluence.influenceById.get(this.influenceId), 0.0);
            }
        } catch (ConcurrentModificationException var3) {
            var3.printStackTrace();
        }
    }

}
