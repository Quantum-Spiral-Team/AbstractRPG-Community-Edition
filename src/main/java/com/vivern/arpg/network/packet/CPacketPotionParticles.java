package com.vivern.arpg.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class CPacketPotionParticles extends Packet {

    public CPacketPotionParticles() {
    }

    public CPacketPotionParticles(double x, double y, double z) {
        this.buf().writeDouble(x);
        this.buf().writeDouble(y);
        this.buf().writeDouble(z);
    }

    @Override
    public void client(EntityPlayer player, Packet sp, MessageContext ctx) {
        double x = this.buf().readDouble();
        double y = this.buf().readDouble();
        double z = this.buf().readDouble();
        double doublePi = Math.PI * 2;
        double radius = 0.4;

        for (double i = 0.0; i < doublePi; i += doublePi / 50.0) {
            double newPosX = x + radius * Math.cos(i);
            double newPosZ = z + radius * Math.sin(i);
            double speedX = (newPosX - x) * 0.2;
            double speedZ = (newPosZ - z) * 0.2;
            player.world.spawnParticle(EnumParticleTypes.FLAME, newPosX, y, newPosZ, speedX, 0.0, speedZ, new int[0]);
        }
    }

    @Override
    public void server(EntityPlayerMP player, Packet sp, MessageContext ctx) {}

}
