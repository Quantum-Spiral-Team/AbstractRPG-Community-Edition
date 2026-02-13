package com.Vivern.Arpg.network;

import com.Vivern.Arpg.arpgfix.PlayerKeyState;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketWeaponInput_LeftMouse implements IMessage {

    public static void init(int id) {
        PacketHandler.NETWORK.registerMessage(
                PacketWeaponInput_LeftMouse.Handler.class,
                PacketWeaponInput_LeftMouse.class,
                id,
                Side.SERVER
        );
    }

    public boolean pressed;

    public PacketWeaponInput_LeftMouse() {}

    public PacketWeaponInput_LeftMouse(boolean pressed) {
        this.pressed = pressed;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(pressed);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pressed = buf.readBoolean();
    }

    public static class Handler implements IMessageHandler<PacketWeaponInput_LeftMouse, IMessage> {
        @Override
        public IMessage onMessage(PacketWeaponInput_LeftMouse msg, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

//            player.getServerWorld().addScheduledTask(() -> PlayerKeyState.get(player).setMouseLeft(msg.pressed));

            return null;
        }
    }
}