package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.mobs.HostileProjectiles;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class Hail extends WorldEvent {

    public final Random rand = new Random();

    public Hail(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
        super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted % 23 == 0) {
            this.updateAllLoaded(this.rand, 1.0E-4F, 14);
        }
    }

    @Override
    public void updateLoadedXZ(World world, int posX, int posZ) {
        if (world.isAirBlock(new BlockPos(posX, 230, posZ))) {
            this.spawnHailblast(world, posX, 230.0, posZ);
        }
    }

    public void spawnHailblast(World world, double x, double y, double z) {
        HostileProjectiles.Hailblast entity = new HostileProjectiles.Hailblast(world);
        entity.setPosition(x, y, z);
        double val2 = 1.0;
        entity.wind = new Vec3d(Weather.getWindX(x, z) * val2, Weather.getWindY(x, z) * val2, Weather.getWindZ(x, z) * val2);
        entity.motionX = this.rand.nextGaussian() / 7.0 + entity.wind.x;
        entity.motionY = this.rand.nextFloat() * 0.6 - 1.8 + entity.wind.y;
        entity.motionZ = this.rand.nextGaussian() / 7.0 + entity.wind.z;
        entity.damage = 20.0F;
        entity.spawnFragmentChance = 0.02F;
        entity.damageRadiusAdd = 1.5F;
        world.spawnEntity(entity);
    }

    @Override
    public boolean canOverlapWith(WorldEvent other) {
        return false;
    }

    @Override
    public int getCooldown() {
        return 3000 + this.worldProvider.getWorld().rand.nextInt(6000);
    }

}