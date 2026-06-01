package com.vivern.arpg.network.packet;

import com.vivern.arpg.entity.AdvancedFallingBlock;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketFallingBlockToClients extends Packet {

    public String block = "";
    public int entityId = 0;
    public int blockMeta = 0;

    public void write(String block, int entityId, int blockMeta) {
        ByteBufUtils.writeUTF8String(this.buf(), block);
        this.buf().writeInt(entityId);
        this.buf().writeInt(blockMeta);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.block = ByteBufUtils.readUTF8String(buf);
        this.entityId = buf.readInt();
        this.blockMeta = buf.readInt();
        this.setBuf(buf);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
        if (!this.block.isEmpty()) {
            Entity entity = player.world.getEntityByID(this.entityId);
            if (entity instanceof AdvancedFallingBlock) {
                AdvancedFallingBlock fallingBlock = (AdvancedFallingBlock) entity;
                fallingBlock.fallTile = Block.getBlockFromName(this.block).getStateFromMeta(this.blockMeta);
            }
        }
    }

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

}
