package com.vivern.arpg.entity;

import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class SurvivorLootSpawner extends EntityThrowable {

    ResourceLocation flame = new ResourceLocation("arpg", "textures/flame_big.png");
    public List<ItemStack> LOOT;
    public List<NPCMobsPack.Trade> TRADES;

    public SurvivorLootSpawner(World world) {
        super(world);
    }

    public SurvivorLootSpawner(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    public SurvivorLootSpawner(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > 1000) {
            this.setDead();
        }

        if (this.world.isRemote) {
            GUNParticle fire2 = new GUNParticle(this.flame, 1.13F + (float) this.rand.nextGaussian() / 15.0F, -0.009F, 10 + this.rand.nextInt(5), 240, this.world, this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F, 1.0F, 0.8F + (float) this.rand.nextGaussian() / 5.0F, 1.0F, true, this.rand.nextInt(100) - 50);
            fire2.alphaTickAdding = -0.1F;
            fire2.alphaGlowing = true;
            fire2.scaleTickAdding = -0.05F;
            this.world.spawnEntity(fire2);
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.004F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 5) {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 2.0F, 0.95F + this.rand.nextFloat() / 10.0F, false);

            for (int ss = 0; ss < 27; ss++) {
                GUNParticle fire = new GUNParticle(this.flame, 0.14F + (float) this.rand.nextGaussian() / 5.0F, -0.005F, 15 + this.rand.nextInt(10), 240, this.world, this.posX, this.posY, this.posZ, (float) this.rand.nextGaussian() / 8.0F, (float) this.rand.nextGaussian() / 8.0F + 0.05F, (float) this.rand.nextGaussian() / 8.0F, 1.0F, 0.8F + (float) this.rand.nextGaussian() / 5.0F, 1.0F, true, this.rand.nextInt(100) - 50);
                fire.alphaTickAdding = -0.04F;
                fire.alphaGlowing = true;
                fire.scaleTickAdding = 0.05F + this.rand.nextFloat() / 10.0F;
                this.world.spawnEntity(fire);
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit == null && this.world.getBlockState(result.getBlockPos()).getBlock().getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos()) != null) {
            if (this.TRADES != null) {
                this.explMerchant();
            }

            if (this.LOOT != null) {
                this.explChest();
            }
        }
    }

    public void explChest() {
        if (!this.world.isRemote) {
            BlockPos center = this.getPosition();
            int y = 0;

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = center.add(x, y, z);
                    this.world.setBlockState(pos, this.getBlockForBuild(0).getDefaultState());
                }
            }

            BlockPos poschest = center.up(this.TRADES != null ? 3 : 1);
            this.world.setBlockState(poschest, Blocks.CHEST.getDefaultState());
            TileEntity tile = this.world.getTileEntity(poschest);
            if (tile instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) tile;
                int indx = 0;

                for (ItemStack stack : this.LOOT) {
                    chest.setInventorySlotContents(indx, stack);
                    indx++;
                }
            }

            if (!this.isDead) {
                this.world.setEntityState(this, (byte) 5);
                this.setDead();
            }
        }
    }

    public void explMerchant() {
        if (!this.world.isRemote) {
            BlockPos center = this.getPosition();
            int y = 0;

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = center.add(x, y, z);
                    this.world.setBlockState(pos, this.getBlockForBuild(0).getDefaultState());
                    this.world.setBlockState(pos.up(4), this.getBlockForBuild(1).getDefaultState());
                }
            }

            int var6 = 1;

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 || z != 0) {
                        BlockPos pos = center.add(x, var6, z);
                        this.world.setBlockState(pos, this.getBlockForBuild(2).getDefaultState());
                        this.world.setBlockState(pos.up(2), this.getBlockForBuild(2).getDefaultState());
                    }
                }
            }

            var6 = 2;

            for (int x = -1; x <= 1; x++) {
                for (int zx = -1; zx <= 1; zx++) {
                    if (x != 0 && zx != 0) {
                        BlockPos pos = center.add(x, var6, zx);
                        this.world.setBlockState(pos, this.getBlockForBuild(2).getDefaultState());
                    }
                }
            }

            NPCMobsPack.NpcMerchant merchant = new NPCMobsPack.NpcMerchant(this.world);
            merchant.setPosition(center.getX() + 0.5, center.getY() + 1.1, center.getZ() + 0.5);
            merchant.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(merchant)), null);
            merchant.trades = this.TRADES;
            merchant.setEntityInvulnerable(true);
            this.world.spawnEntity(merchant);
            merchant.enablePersistence();
            if (!this.isDead) {
                this.world.setEntityState(this, (byte) 5);
                this.setDead();
            }
        }
    }

    public Block getBlockForBuild(int buildFor) {
        if (this.dimension == -1) {
            switch (buildFor) {
                case 0:
                    return Blocks.NETHER_BRICK;
                case 1:
                    return Blocks.GLOWSTONE;
                case 2:
                    return Blocks.NETHER_BRICK_FENCE;
                default:
            }
        } else {
            switch (buildFor) {
                case 0:
                    return Blocks.PLANKS;
                case 1:
                    return Blocks.WOODEN_SLAB;
                case 2:
                    return Blocks.GLASS_PANE;
                default:
            }
        }

        throw new IllegalArgumentException("Invalid block for build. Please report this issue to the developers.");
    }

}
