package com.Vivern.Arpg.network;

import com.Vivern.Arpg.arpgfix.KeyBitset;
import com.Vivern.Arpg.arpgfix.PlayerKeyState;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketKeyState implements IMessage {

    public static void init(int id) {
        PacketHandler.NETWORK.registerMessage(
                PacketKeyState.Handler.class,
                PacketKeyState.class,
//                id,
                id + 1,
                Side.SERVER
        );
    }

    private long[] keys;

    public PacketKeyState() {}

    public PacketKeyState(long[] keys) {
        this.keys = keys;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        for (long l : keys)
            buf.writeLong(l);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        keys = new long[KeyBitset.LONG_COUNT];
        for (int i = 0; i < keys.length; i++)
            keys[i] = buf.readLong();
    }

    public static class Handler implements IMessageHandler<PacketKeyState, IMessage> {
        @Override
        public IMessage onMessage(PacketKeyState msg, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

//            player.getServerWorld().addScheduledTask(() -> PlayerKeyState.get(player).updateKeys(msg.keys));
            return null;
        }
    }
}