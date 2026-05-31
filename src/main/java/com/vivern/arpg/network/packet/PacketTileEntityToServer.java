package com.vivern.arpg.network.packet;

import com.vivern.arpg.tileentity.ITileEntitySynchronized;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ConcurrentModificationException;

public class PacketTileEntityToServer extends Packet {

    double[] args;
    int posX = 0;
    int posY = 0;
    int posZ = 0;

    public void writeArgs(int x, int y, int z, double... args) {
        this.buf().writeInt(x);
        this.buf().writeInt(y);
        this.buf().writeInt(z);
        for (double arg : args) {
            this.buf().writeDouble(arg);
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        int count = (buffer.capacity() - 12) / 8;
        this.posX = buffer.readInt();
        this.posY = buffer.readInt();
        this.posZ = buffer.readInt();
        this.args = new double[Math.max(count, 0)];
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                this.args[i] = buffer.readDouble();
            }
        }
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {}

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> this.processMessage(player));
    }

    void processMessage(EntityLivingBase player) {
        try {
            TileEntity tile = player.world.getTileEntity(new BlockPos(this.posX, this.posY, this.posZ));
            if (tile instanceof ITileEntitySynchronized) {
                ((ITileEntitySynchronized) tile).onServer(player, this.args);
            }
        } catch (ConcurrentModificationException var3) {
            var3.printStackTrace();
        }
    }

}
