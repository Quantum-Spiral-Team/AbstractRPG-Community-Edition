package com.vivern.arpg.network.packet;

import com.vivern.arpg.weather.render.WorldEventsClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWorldEventToClients extends Packet {

    byte index;
    byte state;
    int dimension;

    public void writeArgs(byte index, byte state, int dimension) {
        this.buf().writeByte(index);
        this.buf().writeByte(state);
        this.buf().writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.index = buffer.readByte();
        this.state = buffer.readByte();
        this.dimension = buffer.readInt();
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(
                () -> WorldEventsClientHandler.onSyncPacket(this.index, this.state, this.dimension));
    }

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

}
