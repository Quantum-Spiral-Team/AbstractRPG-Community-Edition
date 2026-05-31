package com.vivern.arpg.network.packet;

import com.vivern.arpg.entity.EntityMagicUI;
import com.vivern.arpg.main.IMagicUI;
import com.vivern.arpg.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ConcurrentModificationException;

public class PacketMUIOpenToServer extends Packet {

    public int x = 0;
    public int y = 0;
    public int z = 0;

    public void write(BlockPos pos) {
        this.buf().writeInt(pos.getX());
        this.buf().writeInt(pos.getY());
        this.buf().writeInt(pos.getZ());
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    @SideOnly(Side.CLIENT)
    public static void send(BlockPos pos) {
        PacketMUIOpenToServer packet = new PacketMUIOpenToServer();
        packet.write(pos);
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
            if (player != null) {
                BlockPos pos = new BlockPos(this.x, this.y, this.z);
                IMagicUI mui = EntityMagicUI.getMUIinPos(player.world, pos);
                if (mui != null) {
                    mui.open(player.world, player, pos, null);
                }
            }
        } catch (ConcurrentModificationException var4) {
            var4.printStackTrace();
        }
    }

}
