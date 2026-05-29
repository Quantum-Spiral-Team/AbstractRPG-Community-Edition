package com.vivern.arpg.network.packet.keys;

import com.vivern.arpg.main.ServerKeyTracker;
import com.vivern.arpg.network.packet.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketKeyPressed extends Packet {

    public PacketKeyPressed() {}

    public PacketKeyPressed(byte id,  boolean pressed) {
        this.buf.writeByte(id);
        this.buf.writeBoolean(pressed);
    }

    public PacketKeyPressed(ServerKeyTracker.Keys key,  boolean pressed) {
        this.buf.writeByte(key.getId());
        this.buf.writeBoolean(pressed);
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
        final byte id = sp.buf.readByte();
        final boolean pressed = sp.buf.readBoolean();

        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
            ServerKeyTracker.setKeyPressed(player, id, pressed);
        });
    }
}
