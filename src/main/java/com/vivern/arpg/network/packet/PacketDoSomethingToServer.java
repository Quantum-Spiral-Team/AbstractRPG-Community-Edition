package com.vivern.arpg.network.packet;

import com.vivern.arpg.arpgamemodes.SurvivorGameStyleWatcher;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ConcurrentModificationException;

public class PacketDoSomethingToServer extends Packet {

    double x = 0.0;
    double y = 0.0;
    double z = 0.0;
    double a = 0.0;
    double b = 0.0;
    double c = 0.0;
    int id = 0;

    public void writeArgs(double x, double y, double z, double a, double b, double c, int id) {
        this.buf().writeDouble(x);
        this.buf().writeDouble(y);
        this.buf().writeDouble(z);
        this.buf().writeDouble(a);
        this.buf().writeDouble(b);
        this.buf().writeDouble(c);
        this.buf().writeInt(id);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.a = buffer.readDouble();
        this.b = buffer.readDouble();
        this.c = buffer.readDouble();
        this.id = buffer.readInt();
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
    }

    void processMessage(EntityLivingBase player) {
        try {
            if (this.id == 9 && SurvivorGameStyleWatcher.currentWatcher != null) {
                SurvivorGameStyleWatcher.currentWatcher.onServerPacket(player, this.x, this.y, this.z, this.a, this.b, this.c);
            }
        } catch (ConcurrentModificationException var3) {
            var3.printStackTrace();
        }
    }

}
