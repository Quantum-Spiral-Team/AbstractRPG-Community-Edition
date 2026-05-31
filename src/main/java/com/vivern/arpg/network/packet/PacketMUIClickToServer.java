package com.vivern.arpg.network.packet;

import com.vivern.arpg.entity.EntityMagicUI;
import com.vivern.arpg.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ConcurrentModificationException;

public class PacketMUIClickToServer extends Packet {

    public int id = 0;

    public void writeInt(int id) {
        this.buf().writeInt(id);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.id = buffer.readInt();
    }

    @SideOnly(Side.CLIENT)
    public static void send(int id) {
        PacketMUIClickToServer packet = new PacketMUIClickToServer();
        packet.writeInt(id);
        PacketHandler.NETWORK.sendToServer(packet);
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
            if (en instanceof EntityMagicUI && en.getDistanceSq(player) <= 64.0) {
                ((EntityMagicUI) en).onPressTick(player);
            }
        } catch (ConcurrentModificationException var3) {
            var3.printStackTrace();
        }
    }

}
