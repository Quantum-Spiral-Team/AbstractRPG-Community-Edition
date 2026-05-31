package com.vivern.arpg.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileStarLantern extends TileEntity {

    public float red = 1.0F;
    public float green = 1.0F;
    public float blue = 1.0F;
    public long lightSeed = 1L;
    public static Random RANDOM = new Random(31101L);

    public TileStarLantern() {
        this.lightSeed = RANDOM.nextInt();
    }

    public void read(NBTTagCompound compound) {
        if (compound.hasKey("red")) {
            this.red = compound.getFloat("red");
        }

        if (compound.hasKey("green")) {
            this.green = compound.getFloat("green");
        }

        if (compound.hasKey("blue")) {
            this.blue = compound.getFloat("blue");
        }

        super.readFromNBT(compound);
    }

    public NBTTagCompound write(NBTTagCompound compound) {
        compound.setFloat("red", this.red);
        compound.setFloat("green", this.green);
        compound.setFloat("blue", this.blue);
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.write(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.read(compound);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();
        this.write(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        this.read(compound);
        super.handleUpdateTag(compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        NBTTagCompound compound = packet.getNbtCompound();
        this.read(compound);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.write(compound);
        return new SPacketUpdateTileEntity(this.pos, 1, compound);
    }

}
