package com.vivern.arpg.network.packet;

import com.vivern.arpg.main.ServerKeyTracker;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketKeysState extends Packet {
    private short mask;

    public PacketKeysState() {}

    public PacketKeysState(short mask) {
        this.buf().writeShort(mask);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.mask = buf.readShort();
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
        final short mask = ((PacketKeysState) sp).mask;

        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
            ServerKeyTracker.updatePlayerKeys(player, mask)
        );
    }

}
