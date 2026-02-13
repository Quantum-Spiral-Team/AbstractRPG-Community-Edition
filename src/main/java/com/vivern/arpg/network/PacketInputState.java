package com.Vivern.Arpg.network;

import com.Vivern.Arpg.arpgfix.KeyBitset;
import com.Vivern.Arpg.arpgfix.PlayerKeyState;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketInputState implements IMessage {

    public static void init(int id) {
        PacketHandler.NETWORK.registerMessage(
                Handler.class,
                PacketInputState.class,
                id,
                Side.SERVER
        );
    }

    private long[] keysDown;
    private int packedActions;
    private boolean mouseLeft;
    private boolean mouseRight;

    public PacketInputState() {}

    public PacketInputState(long[] keysDown, int packedActions, boolean mouseLeft, boolean mouseRight) {
        this.keysDown = keysDown;
        this.packedActions = packedActions;
        this.mouseLeft = mouseLeft;
        this.mouseRight = mouseRight;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // keysDown
        for (int i = 0; i < KeyBitset.LONG_COUNT; i++) {
            buf.writeLong(keysDown[i]);
        }
        // actions + mouse
        buf.writeInt(packedActions);
        buf.writeBoolean(mouseLeft);
        buf.writeBoolean(mouseRight);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        keysDown = new long[KeyBitset.LONG_COUNT];
        for (int i = 0; i < keysDown.length; i++) {
            keysDown[i] = buf.readLong();
        }
        packedActions = buf.readInt();
        mouseLeft = buf.readBoolean();
        mouseRight = buf.readBoolean();
    }

    public static class Handler implements IMessageHandler<PacketInputState, IMessage> {
        @Override
        public IMessage onMessage(PacketInputState msg, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                PlayerKeyState.get(player).updateFromClient(msg.keysDown, msg.packedActions, msg.mouseLeft, msg.mouseRight);
            });
            return null;
        }
    }
}