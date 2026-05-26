package com.vivern.arpg.main;

import com.google.common.base.Predicate;

import java.util.*;

import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GetMOP {
   public static final Random rand = new Random();
   public static final EnumFacing[] XY_TICALS = new EnumFacing[]{EnumFacing.UP, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.WEST};
   public static final EnumFacing[] ZY_TICALS = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.DOWN, EnumFacing.SOUTH};
   public static final Predicate<IBlockState> SOLID_BLOCKS = input ->
           input.isFullCube() && input.getMaterial().blocksMovement();
   public static final Predicate<IBlockState> AIR_BLOCKS = input ->
           input.getBlock() == Blocks.AIR || input.getMaterial() == Material.AIR;
   public static final Predicate<IBlockState> SOLID_NON_PLANTS_BLOCKS = input ->
           input.isFullCube()
           && input.getMaterial().blocksMovement()
           && input.getMaterial() != Material.LEAVES
           && input.getMaterial() != Material.VINE
           && input.getMaterial() != Material.CACTUS
           && input.getMaterial() != Material.PLANTS
           && input.getMaterial() != Material.WOOD
           && input.getMaterial() != Material.SNOW;
   public static final Predicate<IBlockState> ALL_BLOCKS = state -> true;
   public static final Predicate<IBlockState> WATER_BLOCKS = input ->
           input.getBlock() == Blocks.WATER || input.getBlock() == Blocks.FLOWING_WATER;
   public static final Predicate<IBlockState> FLUID_BLOCKS = input ->
           input.getBlock() instanceof IFluidBlock || input.getBlock() instanceof BlockLiquid;

   public static List<EntityLivingBase> mopRayTrace(double blockReachDistance, float partialTicks, EntityLivingBase entity, double size, double step) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = entity.getLook(partialTicks);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEntitiesLivingOnPath(vec3d, vec3d2, entity.world, entity, size, step);
   }

   protected static List<EntityLivingBase> findEntitiesLivingOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double rayStep) {
      double dx = end.x - start.x;
      double dy = end.y - start.y;
      double dz = end.z - start.z;
      double length = Math.sqrt(dx * dx + dy * dy + dz * dz);

      if (length < 0.0001) return Collections.emptyList();

      double stepIncrement = rayStep / length;
      double halfSize = size / 2.0;

      for (double k = 0.0; k <= 1.0; k += stepIncrement) {
         double x = start.x + dx * k;
         double y = start.y + dy * k;
         double z = start.z + dz * k;

         AxisAlignedBB aabb = new AxisAlignedBB(
                 x - halfSize, y - halfSize, z - halfSize,
                 x + halfSize, y + halfSize, z + halfSize
         );

         List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);

         for (EntityLivingBase entity : list) {
            if (entity != shooter && entity.isEntityAlive()) {
               return Collections.singletonList(entity);
            }
         }
      }

      return Collections.emptyList();
   }

   public static boolean isCollideBlockOnPos(World world, double x, double y, double z) {
      BlockPos pos = new BlockPos(x, y, z);
      return world.getBlockState(pos).getCollisionBoundingBox(world, pos) != null;
   }

   public static Vec3d posRayTrace(double blockReachDistance, float partialTicks, EntityLivingBase entity, double size, double step) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = entity.getLook(partialTicks);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step);
   }

   public static Vec3d posRayTrace(double blockReachDistance, float partialTicks, EntityLivingBase entity, boolean checkTeam, double size, double step) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = entity.getLook(partialTicks);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step, checkTeam);
   }

   public static Vec3d logicRayTrace(
           World world, Vec3d from, Vec3d to, Predicate<? super Entity> filterEntityToIgnore, double size, double step, boolean stoponLiquid
   ) {
      RayTraceResult raytraceresult = normalizeRayTraceResult(world.rayTraceBlocks(from, to, stoponLiquid, true, false));
      if (raytraceresult != null) {
         to = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(from, to, world, filterEntityToIgnore, size, step);
   }

   public static Vec3d logicRayTraceIgnoreMobs(
           World world, Vec3d from, Vec3d to, Predicate<? super IBlockState> filterBlockToIgnore, boolean stoponLiquid
   ) {
      RayTraceResult raytraceresult = rayTraceBlocks(world, from, to, filterBlockToIgnore, stoponLiquid, true, false);
      if (raytraceresult != null) {
         to = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return to;
   }

   public static boolean thereIsNoBlockCollidesBetween(
           World world, Vec3d from, Vec3d to, @Nullable Predicate<? super IBlockState> filter, boolean stopOnLiquid
   ) {
      RayTraceResult result = rayTraceBlocks(world, from, to, filter, stopOnLiquid, true, false);

      if (result == null || result.typeOfHit == RayTraceResult.Type.MISS) {
         return true;
      }

      BlockPos pos = result.getBlockPos();
      IBlockState state = world.getBlockState(pos);

      return state.getCollisionBoundingBox(world, pos) == Block.NULL_AABB;
   }

   public static EntityLivingBase findEntityOnPath(
           Vec3d start, Vec3d end, EntityLivingBase entity, boolean checkTeam, double size, double step
   ) {
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(start, end, false, true, false);
      if (raytraceresult != null) {
         end = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEntityOnPath(start, end, entity.world, entity, size, step, checkTeam);
   }

   public static EntityLivingBase findEntityOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double rayStep, boolean checkTeam) {
      double dx = end.x - start.x;
      double dy = end.y - start.y;
      double dz = end.z - start.z;
      double length = Math.sqrt(dx * dx + dy * dy + dz * dz);

      if (length < 0.0001) return null;

      double stepIncrement = rayStep / length;
      double halfSize = size / 2.0;

      for (double k = 0.0; k <= 1.0; k += stepIncrement) {
         double curX = start.x + dx * k;
         double curY = start.y + dy * k;
         double curZ = start.z + dz * k;

         AxisAlignedBB aabb = new AxisAlignedBB(
                 curX - halfSize, curY - halfSize, curZ - halfSize,
                 curX + halfSize, curY + halfSize, curZ + halfSize
         );

         List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);

         for (EntityLivingBase entityLiving : list) {
            if (entityLiving != shooter && entityLiving.isEntityAlive()) {
               if (!checkTeam || Team.checkIsOpponent(shooter, entityLiving)) {
                  return entityLiving;
               }
            }
         }
      }

      return null;
   }

   public static Entity findEntity2OnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double rayStep, boolean checkTeam) {
      double dx = end.x - start.x;
      double dy = end.y - start.y;
      double dz = end.z - start.z;
      double lengthSq = dx * dx + dy * dy + dz * dz;

      if (lengthSq < 0.0001) return null;

      double length = Math.sqrt(lengthSq);
      double stepIncrement = rayStep / length;
      double halfSize = size / 2.0;

      for (double k = 0.0; k <= 1.0; k += stepIncrement) {
         double curX = start.x + dx * k;
         double curY = start.y + dy * k;
         double curZ = start.z + dz * k;

         AxisAlignedBB cube = new AxisAlignedBB(
                 curX - halfSize, curY - halfSize, curZ - halfSize,
                 curX + halfSize, curY + halfSize, curZ + halfSize
         );

         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, cube);

         for (Entity entity : list) {
            if (entity.canBeCollidedWith()) {
               if (!checkTeam || Team.checkIsOpponent(shooter, entity)) {
                  return entity;
               }
            }
         }
      }

      return null;
   }

   public static List<Entity> findEntitiesOnPath(Vec3d start, Vec3d end, World world, @Nullable Entity shooter, double size, double raystep) {
      double dx = end.x - start.x;
      double dy = end.y - start.y;
      double dz = end.z - start.z;
      double length = Math.sqrt(dx * dx + dy * dy + dz * dz);

      if (length < 0.0001) return Collections.emptyList();

      Set<Entity> foundSet = new LinkedHashSet<>();
      double halfSize = size / 2.0;
      double stepIncrement = raystep / length;

      for (double k = 0.0; k <= 1.0; k += stepIncrement) {
         double x = start.x + dx * k;
         double y = start.y + dy * k;
         double z = start.z + dz * k;

         AxisAlignedBB cube = new AxisAlignedBB(
                 x - halfSize, y - halfSize, z - halfSize,
                 x + halfSize, y + halfSize, z + halfSize
         );

         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, cube);

         for (Entity entity : list) {
            if (entity.canBeCollidedWith()) {
               foundSet.add(entity);
            }
         }
      }

      return new ArrayList<>(foundSet);
   }

   public static Vec3d findEndCoordOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep) {
      double dx = end.x - start.x;
      double dy = end.y - start.y;
      double dz = end.z - start.z;
      double length = Math.sqrt(dx * dx + dy * dy + dz * dz);

      if (length < 0.0001) return start;

      double halfSize = size / 2.0;
      double stepIncrement = raystep / length;

      for (double k = 0.0; k <= 1.0; k += stepIncrement) {
         double curX = start.x + dx * k;
         double curY = start.y + dy * k;
         double curZ = start.z + dz * k;

         AxisAlignedBB cube = new AxisAlignedBB(
                 curX - halfSize, curY - halfSize, curZ - halfSize,
                 curX + halfSize, curY + halfSize, curZ + halfSize
         );

         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, cube);

         for (Entity entity : list) {
            if (entity.canBeCollidedWith()) {
               return new Vec3d(curX, curY, curZ);
            }
         }
      }

      return end;
   }

   public static Vec3d findEndCoordOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep, boolean checkTeam) {
      double diffX = end.x - start.x;
      double diffY = end.y - start.y;
      double diffZ = end.z - start.z;
      double length = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

      if (length < 0.0001) return end;

      double halfSize = size / 2.0;
      double stepIncrement = raystep / length;

      for (double k = 0.0; k <= 1.0; k += stepIncrement) {
         double centerX = start.x + diffX * k;
         double centerY = start.y + diffY * k;
         double centerZ = start.z + diffZ * k;

         AxisAlignedBB cube = new AxisAlignedBB(
                 centerX - halfSize, centerY - halfSize, centerZ - halfSize,
                 centerX + halfSize, centerY + halfSize, centerZ + halfSize
         );

         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, cube);

         for (Entity entity : list) {
            if (entity.canBeCollidedWith()) {
               if (!checkTeam || Team.checkIsOpponent(shooter, entity)) {
                  return new Vec3d(centerX, centerY, centerZ);
               }
            }
         }
      }

      return end;
   }

   public static Vec3d findEndCoordOnPath(Vec3d start, Vec3d end, World world, Predicate<? super Entity> filter, double size, double raystep) {
      Vec3d dir = end.subtract(start);
      double length = dir.length();

      if (length < 0.001) return start;

      double halfSize = size / 2.0;
      double step = raystep / length;

      for (double k = 0.0; k <= 1.0; k += step) {
         double x = start.x + dir.x * k;
         double y = start.y + dir.y * k;
         double z = start.z + dir.z * k;

         AxisAlignedBB cube = new AxisAlignedBB(x - halfSize, y - halfSize, z - halfSize,
                 x + halfSize, y + halfSize, z + halfSize);

         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, cube);

         for (Entity entity : list) {
            if (entity.canBeCollidedWith() && !filter.apply(entity)) {
               return new Vec3d(x, y, z);
            }
         }
      }

      return end;
   }

   public static Vec3d rotatedPosRayTrace(
      double blockReachDistance, float partialTicks, EntityLivingBase entity, double size, double step, float rotationPitch, float rotationYaw
   ) {
      return rotatedPosRayTrace(blockReachDistance, partialTicks, entity, size, step, rotationPitch, rotationYaw, false);
   }

   public static Vec3d rotatedPosRayTrace(
           double blockReachDistance, Vec3d start, EntityLivingBase entity, double size, double step, float rotationPitch, float rotationYaw
   ) {
      Vec3d vec3d1 = pitchYawToVec3D(rotationPitch, rotationYaw);
      Vec3d vec3d2 = start.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(start, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(start, vec3d2, entity.world, entity, size, step);
   }

   public static Vec3d rotatedPosRayTrace(
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      double step,
      float rotationPitch,
      float rotationYaw,
      boolean checkTeam
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = pitchYawToVec3D(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = normalizeRayTraceResult(entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false));
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEndCoordOnPath(vec3d, vec3d2, entity.world, entity, size, step, checkTeam);
   }

   public static Vec3d findRandPosNearEntity(Entity entity, float radius) {
      return findRandPosNearPoint(entity.posX, entity.posY, entity.posZ, radius);
   }

   public static Vec3d findRandPosNearPoint(double xx, double yy, double zz, float radius) {
      float rand1 = rand.nextFloat() * 2.0F - 1.0F;
      float rand2 = rand.nextFloat() * 2.0F - 1.0F;
      float X = rand1 * radius;
      float newR = (float)Math.sqrt(radius * radius - X * X);
      float Y = rand2 * newR;
      float Z = (float)Math.sqrt(newR * newR - Y * Y);
      if (rand.nextBoolean()) {
         Z *= -1.0F;
      }

      return new Vec3d(xx, yy, zz).add(X, Y, Z);
   }

   public static Vec3d pitchYawToVec3D(float pitch, float yaw) {
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float f3 = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      return new Vec3d(f1 * f2, f3, f * f2);
   }

   public static Vec3d yawToVec3D(float yaw) {
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      return new Vec3d(-f1, 0.0, -f);
   }

   @Deprecated
   public static Vec3d vec3DToPitchYaw(Vec3d vec) {
      float f = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z);
      float rotationYaw = (float)(MathHelper.atan2(vec.x, -vec.z) * (180.0 / Math.PI));
      float rotationPitch = (float)(MathHelper.atan2(vec.y, f) * (180.0 / Math.PI));
      return new Vec3d(rotationPitch, rotationYaw, 0.0);
   }

   public static Vec2f vec3DToPitchYawFixed(Vec3d vec) {
      float f = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z);
      float rotationYaw = (float)(MathHelper.atan2(vec.x, -vec.z) * (180.0 / Math.PI));
      float rotationPitch = (float)(MathHelper.atan2(vec.y, f) * (180.0 / Math.PI));
      return new Vec2f(-rotationPitch, MathHelper.wrapDegrees(rotationYaw + 180.0F));
   }

   public static float vec2DToYaw(double x, double z) {
      float f = MathHelper.sqrt(x + z);
      return (float)(MathHelper.atan2(x, -z) * (180.0 / Math.PI));
   }

   public static boolean approximatelyEqual(double value, double value2, double range) {
      return Math.abs(value - value2) <= range;
   }

   public static boolean approximatelyEqual(int value, int value2, int range) {
      return Math.abs(value - value2) <= range;
   }

   public static int floatToIntWithChance(float value, Random rand) {
      int full = MathHelper.floor(value);
      if (rand.nextFloat() < value - full) {
         full++;
      }

      return full;
   }

   public static boolean collidesWithBlock(IBlockAccess world, BlockPos pos, Block block) {
      for (EnumFacing facing : EnumFacing.values()) {
         if (world.getBlockState(pos.offset(facing)).getBlock() == block) {
            return true;
         }
      }
      return false;
   }

   public static boolean collidesWithBlockHorizontal(IBlockAccess world, BlockPos pos, Block block) {
      for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
         if (world.getBlockState(pos.offset(facing)).getBlock() == block) {
            return true;
         }
      }
      return false;
   }

   public static boolean collidesWithAnyBlock(World world, BlockPos pos) {
      for (EnumFacing facing : EnumFacing.values()) {
         BlockPos neighborPos = pos.offset(facing);
         if (world.getBlockState(neighborPos).getCollisionBoundingBox(world, neighborPos) != null) {
            return true;
         }
      }
      return false;
   }

   public static boolean collidesWithBlockExcept(World world, BlockPos pos, Block block, EnumFacing facingExcept) {
      for (EnumFacing facing : EnumFacing.VALUES) {
         if (facing != facingExcept && world.getBlockState(pos.offset(facing)).getBlock() == block) {
            return true;
         }
      }

      return false;
   }

   public static int next(int value, int amountNext, int bound) {
      int result = value + amountNext;
      if (result >= bound && amountNext < bound) {
         result -= bound;
      }

      if (amountNext == bound) {
         return value;
      } else {
         if (amountNext > bound) {
            int perc = amountNext % bound;
            result = value + perc;
         }

         return result;
      }
   }

   public static int followNumber(int baseValue, int targetValue, int followAmount) {
      if (baseValue < targetValue) {
         return Math.min(baseValue + followAmount, targetValue);
      } else {
         return baseValue > targetValue ? Math.max(baseValue - followAmount, targetValue) : baseValue;
      }
   }

   public static float followNumber(float baseValue, float targetValue, float followAmount) {
      if (baseValue < targetValue) {
         return Math.min(baseValue + followAmount, targetValue);
      } else {
         return baseValue > targetValue ? Math.max(baseValue - followAmount, targetValue) : baseValue;
      }
   }

   public static double followNumber(double baseValue, double targetValue, double followAmount) {
      if (baseValue < targetValue) {
         return Math.min(baseValue + followAmount, targetValue);
      } else {
         return baseValue > targetValue ? Math.max(baseValue - followAmount, targetValue) : baseValue;
      }
   }

   public static int cycle(int value, int cycleBound) {
      if (cycleBound == 1) {
         return 0;
      } else {
         return value >= 0 ? value % cycleBound : value + (-value / cycleBound + 1) * cycleBound;
      }
   }

   public static double cycle(double value, double cycleBound) {
      return value >= 0.0 ? value % cycleBound : value + (-value / cycleBound + 1.0) * cycleBound;
   }

   public static double arcsinusoid(double value) {
      double shifted = cycle(value, 4.0);
      return arcsinusoidPart(shifted);
   }

   private static double arcsinusoidPart(double value) {
      if (value >= 0.0 && value < 2.0) {
         return Math.asin(value - 1.0) / Math.PI * 2.0;
      } else {
         return value >= 2.0 && value < 4.0 ? Math.asin(-value + 3.0) / Math.PI * 2.0 : 0.0;
      }
   }

   public static List<BlockPos> getPosesInsideSphere(int x, int y, int z, int radius) {
      List<BlockPos> list = new ArrayList<>();
      int radiusSq = radius * radius;

      for (int xr = -radius; xr <= radius; xr++) {
         for (int yr = -radius; yr <= radius; yr++) {
            for (int zr = -radius; zr <= radius; zr++) {
               if (xr * xr + yr * yr + zr * zr <= radiusSq) {
                  list.add(new BlockPos(xr + x, yr + y, zr + z));
               }
            }
         }
      }

      return list;
   }

   @Nullable
   public static RayTraceResult rayTraceBlocks(
           World world, Vec3d start, Vec3d end,
           Predicate<? super IBlockState> filter,
           boolean stopOnLiquid,
           boolean ignoreNoBox,
           boolean returnLastUncollidable
   ) {
      if (isInvalid(start) || isInvalid(end)) return null;

      int endX = MathHelper.floor(end.x);
      int endY = MathHelper.floor(end.y);
      int endZ = MathHelper.floor(end.z);

      int curX = MathHelper.floor(start.x);
      int curY = MathHelper.floor(start.y);
      int curZ = MathHelper.floor(start.z);

      BlockPos pos = new BlockPos(curX, curY, curZ);
      RayTraceResult lastResult = null;

      IBlockState state = world.getBlockState(pos);
      if (shouldCollide(world, pos, state, filter, stopOnLiquid, ignoreNoBox)) {
         return state.collisionRayTrace(world, pos, start, end);
      }

      for (int k1 = 200; k1-- >= 0; ) {
         if (curX == endX && curY == endY && curZ == endZ) {
            return returnLastUncollidable ? lastResult : null;
         }

         double nextBoundX = (endX > curX) ? curX + 1 : (endX < curX ? curX : 999);
         double nextBoundY = (endY > curY) ? curY + 1 : (endY < curY ? curY : 999);
         double nextBoundZ = (endZ > curZ) ? curZ + 1 : (endZ < curZ ? curZ : 999);

         double diffX = end.x - start.x;
         double diffY = end.y - start.y;
         double diffZ = end.z - start.z;

         double tX = (nextBoundX != 999) ? (nextBoundX - start.x) / diffX : 999;
         double tY = (nextBoundY != 999) ? (nextBoundY - start.y) / diffY : 999;
         double tZ = (nextBoundZ != 999) ? (nextBoundZ - start.z) / diffZ : 999;

         if (tX == -0.0) tX = -1.0E-4;
         if (tY == -0.0) tY = -1.0E-4;
         if (tZ == -0.0) tZ = -1.0E-4;

         EnumFacing facing;
         if (tX < tY && tX < tZ) {
            facing = endX > curX ? EnumFacing.WEST : EnumFacing.EAST;
            start = new Vec3d(nextBoundX, start.y + diffY * tX, start.z + diffZ * tX);
         } else if (tY < tZ) {
            facing = endY > curY ? EnumFacing.DOWN : EnumFacing.UP;
            start = new Vec3d(start.x + diffX * tY, nextBoundY, start.z + diffZ * tY);
         } else {
            facing = endZ > curZ ? EnumFacing.NORTH : EnumFacing.SOUTH;
            start = new Vec3d(start.x + diffX * tZ, start.y + diffY * tZ, nextBoundZ);
         }

         curX = MathHelper.floor(start.x) - (facing == EnumFacing.EAST ? 1 : 0);
         curY = MathHelper.floor(start.y) - (facing == EnumFacing.UP ? 1 : 0);
         curZ = MathHelper.floor(start.z) - (facing == EnumFacing.SOUTH ? 1 : 0);

         pos = new BlockPos(curX, curY, curZ);
         state = world.getBlockState(pos);

         if (shouldCollide(world, pos, state, filter, stopOnLiquid, ignoreNoBox)) {
            return state.collisionRayTrace(world, pos, start, end);
         } else {
            lastResult = new RayTraceResult(RayTraceResult.Type.MISS, start, facing, pos);
         }
      }

      return returnLastUncollidable ? lastResult : null;
   }

   private static boolean shouldCollide(World world, BlockPos pos, IBlockState state,
                                        Predicate<? super IBlockState> filter,
                                        boolean stopOnLiquid, boolean ignoreNoBox) {
      if (filter != null && filter.apply(state)) return false;

      boolean hasBox = state.getCollisionBoundingBox(world, pos) != Block.NULL_AABB;
      boolean isLiquid = state.getMaterial().isLiquid();
      boolean isPortal = state.getMaterial() == Material.PORTAL;

      if (ignoreNoBox && !hasBox && !isPortal && !(stopOnLiquid && isLiquid)) {
         return false;
      }

      return state.getBlock().canCollideCheck(state, stopOnLiquid);
   }

   private static boolean isInvalid(Vec3d vec) {
      return Double.isNaN(vec.x) || Double.isNaN(vec.y) || Double.isNaN(vec.z);
   }

   @Nullable
   public static RayTraceResult rayTraceLiquids(World world, Vec3d start, Vec3d end) {
      if (Double.isNaN(start.x) || Double.isNaN(start.y) || Double.isNaN(start.z) ||
              Double.isNaN(end.x) || Double.isNaN(end.y) || Double.isNaN(end.z)) {
         return null;
      }

      int endX = MathHelper.floor(end.x);
      int endY = MathHelper.floor(end.y);
      int endZ = MathHelper.floor(end.z);
      int x = MathHelper.floor(start.x);
      int y = MathHelper.floor(start.y);
      int z = MathHelper.floor(start.z);

      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);

      RayTraceResult startCheck = checkLiquid(world, pos, start);
      if (startCheck != null) return startCheck;

      int limit = 200; // Чтобы не было зацикливания
      while (limit-- >= 0) {
         if (x == endX && y == endY && z == endZ) return null;

         boolean moveX = true;
         boolean moveY = true;
         boolean moveZ = true;
         double nextX = 999.0;
         double nextY = 999.0;
         double nextZ = 999.0;

         if (endX > x) nextX = x + 1.0; else if (endX < x) nextX = x; else moveX = false;
         if (endY > y) nextY = y + 1.0; else if (endY < y) nextY = y; else moveY = false;
         if (endZ > z) nextZ = z + 1.0; else if (endZ < z) nextZ = z; else moveZ = false;

         double stepX = moveX ? (nextX - start.x) / (end.x - start.x) : 999.0;
         double stepY = moveY ? (nextY - start.y) / (end.y - start.y) : 999.0;
         double stepZ = moveZ ? (nextZ - start.z) / (end.z - start.z) : 999.0;

         if (stepX < stepY && stepX < stepZ) {
            x += (endX > x ? 1 : -1);
            start = new Vec3d(nextX, start.y + (end.y - start.y) * stepX, start.z + (end.z - start.z) * stepX);
         } else if (stepY < stepZ) {
            y += (endY > y ? 1 : -1);
            start = new Vec3d(start.x + (end.x - start.x) * stepY, nextY, start.z + (end.z - start.z) * stepY);
         } else {
            z += (endZ > z ? 1 : -1);
            start = new Vec3d(start.x + (end.x - start.x) * stepZ, start.y + (end.y - start.y) * stepZ, nextZ);
         }

         pos.setPos(x, y, z);
         RayTraceResult result = checkLiquid(world, pos, start);
         if (result != null) return result;
      }

      return null;
   }

   private static RayTraceResult checkLiquid(World world, BlockPos pos, Vec3d hitVec) {
      IBlockState state = world.getBlockState(pos);
      if (state.getMaterial().isLiquid()) {
         Block block = state.getBlock();
         boolean isFull = false;

         if (block instanceof IFluidBlock) {
            isFull = ((IFluidBlock) block).getFilledPercentage(world, pos) >= 1.0F;
         } else if (block instanceof BlockLiquid) {
            isFull = state.getValue(BlockLiquid.LEVEL) == 0;
         }

         if (isFull) {
            return new RayTraceResult(RayTraceResult.Type.BLOCK, hitVec, EnumFacing.UP, pos.toImmutable());
         }
      }
      return null;
   }

   public static boolean containsBlock(World world, AxisAlignedBB bb, Block cblock) {
      int minX = MathHelper.floor(bb.minX);
      int maxX = MathHelper.ceil(bb.maxX);
      int minY = MathHelper.floor(bb.minY);
      int maxY = MathHelper.ceil(bb.maxY);
      int minZ = MathHelper.floor(bb.minZ);
      int maxZ = MathHelper.ceil(bb.maxZ);

      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

      int lastChunkX = Integer.MIN_VALUE;
      int lastChunkZ = Integer.MIN_VALUE;
      Chunk chunk = null;

      for (int x = minX; x < maxX; ++x) {
         for (int z = minZ; z < maxZ; ++z) {

            int cx = x >> 4;
            int cz = z >> 4;
            if (chunk == null || cx != lastChunkX || cz != lastChunkZ) {
               if (!world.getChunkProvider().isChunkGeneratedAt(cx, cz)) continue;
               chunk = world.getChunk(cx, cz);
               lastChunkX = cx;
               lastChunkZ = cz;
            }

            for (int y = minY; y < maxY; ++y) {
               pos.setPos(x, y, z);

               IBlockState state = chunk.getBlockState(pos);

               if (state.getBlock() == cblock && state.getMaterial().isLiquid()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   public static IBlockState firstBlockStateContains(World world, AxisAlignedBB bb, Predicate<IBlockState> blocksToAccess) {
      int minX = MathHelper.floor(bb.minX);
      int maxX = MathHelper.ceil(bb.maxX);
      int minY = MathHelper.floor(bb.minY);
      int maxY = MathHelper.ceil(bb.maxY);
      int minZ = MathHelper.floor(bb.minZ);
      int maxZ = MathHelper.ceil(bb.maxZ);

      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
      Chunk lastChunk = null;
      int lastChunkX = Integer.MIN_VALUE;
      int lastChunkZ = Integer.MIN_VALUE;

      for (int x = minX; x < maxX; x++) {
         for (int z = minZ; z < maxZ; z++) {
            int chunkX = x >> 4;
            int chunkZ = z >> 4;

            if (lastChunk == null || chunkX != lastChunkX || chunkZ != lastChunkZ) {
               lastChunk = world.getChunk(chunkX, chunkZ);
               lastChunkX = chunkX;
               lastChunkZ = chunkZ;
            }

            for (int y = minY; y < maxY; y++) {
               pos.setPos(x, y, z);
               IBlockState state = lastChunk.getBlockState(pos);

               if (blocksToAccess.apply(state)) {
                  return state;
               }
            }
         }
      }

      return null;
   }

   @Nullable
   public static Entity findNearestEntityWithinAABB(World world, Class<? extends Entity> entityType, AxisAlignedBB aabb, Vec3d closestTo) {
      List<Entity> list = world.getEntitiesWithinAABB(entityType, aabb);
      Entity t = null;
      double d0 = Double.MAX_VALUE;

       for (Entity value : list) {
           if (EntitySelectors.NOT_SPECTATING.apply(value)) {
               double distance = closestTo.distanceTo(value.getPositionVector());
               if (distance <= d0) {
                   t = value;
                   d0 = distance;
               }
           }
       }

      return t;
   }

   public static double getAngleBetweenVectors(double vec1x, double vec1y, double vec2x, double vec2y) {
      double scalar = vec1x * vec2x + vec1y * vec2y;
      double m1 = Math.sqrt(vec1x * vec1x + vec1y * vec1y);
      double m2 = Math.sqrt(vec2x * vec2x + vec2y * vec2y);
      return Math.acos(scalar / (m1 * m2)) * 57.2958;
   }

   public static double getAngleBetweenVectors(double vec1x, double vec1y, double vec1z, double vec2x, double vec2y, double vec2z) {
      double scalar = vec1x * vec2x + vec1y * vec2y + vec1z * vec2z;
      double m1 = Math.sqrt(vec1x * vec1x + vec1y * vec1y + vec1z * vec1z);
      double m2 = Math.sqrt(vec2x * vec2x + vec2y * vec2y + vec2z * vec2z);
      return Math.acos(scalar / (m1 * m2)) * 57.2958;
   }

   public static double getAngleBetweenVectors(Vec3d vec1, Vec3d vec2, double vec1length, double vec2length) {
      double scalar = vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
      return Math.acos(scalar / (vec1length * vec2length)) * 57.2958;
   }

   public static List<Entity> entityMopRayTrace(
      Class<? extends Entity> eclass,
      double blockReachDistance,
      float partialTicks,
      EntityLivingBase entity,
      double size,
      float rotationPitch,
      float rotationYaw
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = pitchYawToVec3D(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findCustomEntityOnPath(eclass, vec3d, vec3d2, entity.world, entity, size);
   }

   public static EntityLivingBase entityRayTrace(
      World world, Vec3d start, Vec3d end, double size, double step, Predicate<? super IBlockState> filterBlockToIgnore
   ) {
      RayTraceResult raytraceresult = filterBlockToIgnore == null
         ? world.rayTraceBlocks(start, end, false, true, false)
         : rayTraceBlocks(world, start, end, filterBlockToIgnore, false, true, false);
      if (raytraceresult != null) {
         end = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      return findEntityOnPath(start, end, world, null, size, step, false);
   }

   protected static List<Entity> findCustomEntityOnPath(
           Class<? extends Entity> eclass, Vec3d start, Vec3d end, World world, Entity shooter, double size
   ) {
      AxisAlignedBB traceBox = new AxisAlignedBB(start.x, start.y, start.z, end.x, end.y, end.z)
              .grow(size);

      List<Entity> candidates = world.getEntitiesWithinAABB(eclass, traceBox);
      List<Entity> result = new ArrayList<>();

      Entity closestEntity = null;
      double closestDistance = Double.MAX_VALUE;

      for (Entity entity : candidates) {
         if (entity == shooter || !entity.canBeCollidedWith()) {
            continue;
         }

         AxisAlignedBB entityBB = entity.getEntityBoundingBox().grow(size / 2.0);
         RayTraceResult intercept = entityBB.calculateIntercept(start, end);

         if (intercept != null) {
            double dist = start.squareDistanceTo(intercept.hitVec);
            if (dist < closestDistance) {
               closestDistance = dist;
               closestEntity = entity;
            }
         }
      }

      if (closestEntity != null) {
         result.add(closestEntity);
      }

      return result;
   }

   public static BlockPos getTrueHeight(World world, BlockPos pos) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         Material mat = chunk.getBlockState(pos.getX(), y, pos.getZ()).getMaterial();
         if (!mat.isLiquid() && mat != Material.AIR) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   public static BlockPos getTopBlock(World world, BlockPos pos, Block block) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         if (chunk.getBlockState(pos.getX(), y, pos.getZ()).getBlock() == block) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   public static BlockPos getTopBlocks(World world, BlockPos pos, Block... blocks) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         Block gotBlock = chunk.getBlockState(pos.getX(), y, pos.getZ()).getBlock();

         for (Block block : blocks) {
            if (gotBlock == block) {
               return new BlockPos(pos.getX(), y, pos.getZ());
            }
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   public static BlockPos getTopBlockExcluding(World world, BlockPos pos, Block block, boolean useLiquid) {
      Chunk chunk = world.getChunk(pos);

      for (int y = pos.getY(); y > 0; y--) {
         IBlockState state = chunk.getBlockState(pos.getX(), y, pos.getZ());
         Material mat = state.getMaterial();
         if (mat != Material.AIR && state.getBlock() != block && (useLiquid || !mat.isLiquid())) {
            return new BlockPos(pos.getX(), y, pos.getZ());
         }
      }

      return new BlockPos(pos.getX(), 1, pos.getZ());
   }

   @Nullable
   public static BlockTraceResult blockTrace(
           World world, BlockPos startPos, EnumFacing direction, int distance, @Nullable Predicate<? super IBlockState> filter
   ) {
      IBlockState lastState = world.getBlockState(startPos);
      PooledMutableBlockPos mutablePos = PooledMutableBlockPos.retain();

      try {
         for (int i = 1; i <= distance; i++) {
            mutablePos.setPos(
                    startPos.getX() + direction.getXOffset() * i,
                    startPos.getY() + direction.getYOffset() * i,
                    startPos.getZ() + direction.getZOffset() * i
            );

            IBlockState currentState = world.getBlockState(mutablePos);

            boolean hit = (filter == null)
                    ? (currentState.getBlock() != lastState.getBlock())
                    : filter.apply(currentState);

            if (hit) {
               BlockTraceResult res = new BlockTraceResult();
               res.impactState = currentState;
               res.prevState = lastState;
               res.pos = mutablePos.toImmutable();
               res.prevPos = res.pos.offset(direction.getOpposite());
               res.facing = direction;
               return res;
            }

            lastState = currentState;
         }
      } finally {
         mutablePos.release();
      }

      return null;
   }

   public static List<BlockPos> getBlockPosesCollidesAABB(World world, AxisAlignedBB bb, boolean getLiquids) {
      List<BlockPos> list = new ArrayList<>();

      Iterable<BlockPos.MutableBlockPos> box = BlockPos.getAllInBoxMutable(
              MathHelper.floor(bb.minX), MathHelper.floor(bb.minY), MathHelper.floor(bb.minZ),
              MathHelper.ceil(bb.maxX) - 1, MathHelper.ceil(bb.maxY) - 1, MathHelper.ceil(bb.maxZ) - 1
      );

      for (BlockPos.MutableBlockPos pos : box) {
         IBlockState state = world.getBlockState(pos);

         if (state.getMaterial() != Material.AIR && (getLiquids || !state.getMaterial().isLiquid())) {
            list.add(pos.toImmutable()); // Превращаем Mutable в обычный BlockPos для списка
         }
      }

      return list;
   }

   public static List<BlockPos> getCollidingBlocks(World world, AxisAlignedBB bb, boolean getLiquids) {
      List<BlockPos> list = new ArrayList<>();

      Iterable<BlockPos.MutableBlockPos> box = BlockPos.getAllInBoxMutable(
              MathHelper.floor(bb.minX), MathHelper.floor(bb.minY), MathHelper.floor(bb.minZ),
              MathHelper.ceil(bb.maxX) - 1, MathHelper.ceil(bb.maxY) - 1, MathHelper.ceil(bb.maxZ) - 1
      );

      for (BlockPos.MutableBlockPos pos : box) {
         IBlockState state = world.getBlockState(pos);

         if (state.getMaterial() != Material.AIR && (getLiquids || !FLUID_BLOCKS.apply(state)) && state.getBoundingBox(world, pos).offset(pos).intersects(bb)) {
            list.add(pos.toImmutable());
         }
      }

      return list;
   }

   //TODO упростить группу методов
   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double blockReachDistance,
      double size,
      boolean checkTeam,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock,
      float rotationPitch,
      float rotationYaw
   ) {
      Vec3d vec3d = shooter.getPositionEyes(1.0F);
      Vec3d vec3d1 = pitchYawToVec3D(rotationPitch, rotationYaw);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      return fixedRayTraceBlocks(
         world, shooter, size, checkTeam, vec3d, vec3d2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock
      );
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double blockReachDistance,
      double size,
      boolean checkTeam,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock
   ) {
      Vec3d vec3d = shooter.getPositionEyes(1.0F);
      Vec3d vec3d1 = shooter.getLook(1.0F);
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      return fixedRayTraceBlocks(
         world, shooter, size, checkTeam, vec3d, vec3d2, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock
      );
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double size,
      boolean checkTeam,
      Vec3d start,
      Vec3d end,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock,
      @Nullable Predicate<? super IBlockState> filterBlockToIgnore
   ) {
      RayTraceResult result = rayTraceBlocks(world, start, end, filterBlockToIgnore, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
      if (result != null && result.typeOfHit == Type.BLOCK && result.hitVec != null) {
         RayTraceResult baseRes = findEntityAndPosOnPath(start, result.hitVec, world, shooter, size, checkTeam);
         if (baseRes != null) {
            result.entityHit = baseRes.entityHit;
            result.typeOfHit = Type.ENTITY;
            result.hitVec = baseRes.hitVec;
         }
      } else if (result == null || result.hitVec == null || result.typeOfHit == Type.MISS) {
         RayTraceResult baseRes = findEntityAndPosOnPath(start, end, world, shooter, size, checkTeam);
         if (baseRes != null) {
            result = new RayTraceResult(baseRes.entityHit, baseRes.hitVec);
            result.typeOfHit = Type.ENTITY;
         } else {
            result = new RayTraceResult(Type.MISS, end, null, null);
         }
      }

      return normalizeRayTraceResult(result);
   }

   public static RayTraceResult fixedRayTraceBlocks(
      World world,
      Entity shooter,
      double size,
      boolean checkTeam,
      Vec3d start,
      Vec3d end,
      boolean stopOnLiquid,
      boolean ignoreBlockWithoutBoundingBox,
      boolean returnLastUncollidableBlock
   ) {
      return fixedRayTraceBlocks(
         world, shooter, size, checkTeam, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, null
      );
   }

   public static RayTraceResult findEntityAndPosOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, boolean checkTeam) {
      Entity pointedEntity = null;
      Vec3d hitVec = null;

      AxisAlignedBB searchBox = new AxisAlignedBB(start.x, start.y, start.z, end.x, end.y, end.z).grow(size);
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(shooter, searchBox);

      double minDistance = start.distanceTo(end);

      for (Entity entity : list) {
         if (entity.canBeCollidedWith()) {
            if (checkTeam && !Team.checkIsOpponent(shooter, entity)) continue;

            float collisionSize = entity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(collisionSize + size / 2.0);

            RayTraceResult intercept = axisalignedbb.calculateIntercept(start, end);

            if (intercept != null) {
               double dist = start.distanceTo(intercept.hitVec);
               if (dist < minDistance) {
                  pointedEntity = entity;
                  hitVec = intercept.hitVec;
                  minDistance = dist;
               }
            }
         }
      }

      return pointedEntity != null ? new RayTraceResult(pointedEntity, hitVec) : null;
   }

   public static Vec3d rotateVecAroundAxis(Vec3d vector, Vec3d axisVector, float angle) {
      axisVector = axisVector.normalize();
      double cosAngle = Math.cos(angle);
      return axisVector.scale(axisVector.dotProduct(vector) * (1.0 - cosAngle))
         .add(axisVector.crossProduct(vector).scale(Math.sin(angle)))
         .add(vector.scale(cosAngle));
   }

   public static Vec3d rotateVecAroundAxis(Vec3d vector, Vec3d axis, Rotation rotation) {
      double c;
      double s;
      switch (rotation) {
         case CLOCKWISE_90:        c = 0;  s = 1;  break;
         case CLOCKWISE_180:       c = -1; s = 0;  break;
         case COUNTERCLOCKWISE_90: c = 0;  s = -1; break;
         default:                  c = 1;  s = 0;  break;
      }

      return axis.scale(axis.dotProduct(vector) * (1.0 - c))
              .add(axis.crossProduct(vector).scale(s))
              .add(vector.scale(c));
   }

   public static Vec3d getNormalForRotation(float rotationX, float rotationY, float rotationZ) {
      double radX = Math.toRadians(rotationX);
      double radY = Math.toRadians(rotationY);
      double radZ = Math.toRadians(rotationZ);

      double x = Math.sin(radZ) * Math.cos(radY) + Math.cos(radZ) * Math.sin(radY) * Math.sin(radX);
      double y = Math.cos(radZ) * Math.cos(radX);
      double z = -Math.sin(radZ) * Math.sin(radY) * Math.sin(radX) + Math.cos(radZ) * Math.sin(radY);

      return new Vec3d(x, y, z);
   }

   public static float getFromTo(float mainNumber, float from, float to) {
      if (mainNumber < from) {
         return 0.0F;
      } else if (mainNumber > to) {
         return 1.0F;
      } else {
         float n = Math.max(mainNumber - from, 0.0F);
         float f = 1.0F / (to - from);
         return n * f;
      }
   }

   public static double getFromTo(double mainNumber, double from, double to) {
      if (mainNumber < from) {
         return 0.0;
      } else if (mainNumber > to) {
         return 1.0;
      } else {
         double n = Math.max(mainNumber - from, 0.0);
         double f = 1.0 / (to - from);
         return n * f;
      }
   }

   public static float softFromTo(float mainNumber, float from, float to) {
      if (mainNumber < from) {
         return 0.0F;
      } else {
         return mainNumber > to ? 1.0F : -MathHelper.cos((mainNumber - from) * (float) Math.PI / (to - from)) / 2.0F + 0.5F;
      }
   }

   public static Vec3d getNearestPointInAABBtoPoint(Vec3d point, AxisAlignedBB aabb) {
      double clampedX = Math.max(aabb.minX, Math.min(point.x, aabb.maxX));
      double clampedY = Math.max(aabb.minY, Math.min(point.y, aabb.maxY));
      double clampedZ = Math.max(aabb.minZ, Math.min(point.z, aabb.maxZ));

      return new Vec3d(clampedX, clampedY, clampedZ);
   }

   public static Vec3d entityCenterPos(Entity entity) {
      return new Vec3d(entity.posX, entity.posY + entity.height / 2.0F, entity.posZ);
   }

   public static Vec3d entityCenterPos(Entity entity, float partialTicks) {
      float he2 = entity.height / 2.0F;
      return new Vec3d(
         partial(entity.posX, entity.prevPosX, partialTicks),
         partial(entity.posY + he2, entity.prevPosY + he2, partialTicks),
         partial(entity.posZ, entity.prevPosZ, partialTicks)
      );
   }

   public static float partial(float value, float previousValue, float partialTicks) {
      return previousValue + (value - previousValue) * partialTicks;
   }

   @SideOnly(Side.CLIENT)
   public static float partial(float value, float previousValue) {
      return partial(value, previousValue, Minecraft.getMinecraft().getRenderPartialTicks());
   }

   public static double partial(double value, double previousValue, double partialTicks) {
      return previousValue + (value - previousValue) * partialTicks;
   }

   @SideOnly(Side.CLIENT)
   public static double partial(double value, double previousValue) {
      return partial(value, previousValue, Minecraft.getMinecraft().getRenderPartialTicks());
   }

   public static EnumFacing rotate(EnumFacing face, int rotation) {
      if (rotation > 3) {
         rotation %= 4;
      }

      switch (rotation) {
         case 0: return face;
         case 1: return face.rotateY();
         case 2: return face.getOpposite();
         default: return rotation == 3 ? face.rotateYCCW() : face;
      }
   }

   public static EnumFacing rotateZ(EnumFacing face, boolean clockwise) {
      if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
         return face;
      }

      if (clockwise) {
         switch (face) {
            case WEST:  return EnumFacing.UP;
            case UP:    return EnumFacing.EAST;
            case EAST:  return EnumFacing.DOWN;
            case DOWN:  return EnumFacing.WEST;
            default:    return face;
         }
      } else {
         switch (face) {
            case UP:    return EnumFacing.WEST;
            case EAST:  return EnumFacing.UP;
            case DOWN:  return EnumFacing.EAST;
            case WEST:  return EnumFacing.DOWN;
            default:    return face;
         }
      }
   }

   public static EnumFacing rotateX(EnumFacing face, boolean clockwise) {
      if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
         return face;
      }

      if (clockwise) {
         switch (face) {
            case SOUTH: return EnumFacing.UP;
            case UP:    return EnumFacing.NORTH;
            case NORTH: return EnumFacing.DOWN;
            case DOWN:  return EnumFacing.SOUTH;
            default:    return face;
         }
      } else {
         switch (face) {
            case UP:    return EnumFacing.SOUTH;
            case NORTH: return EnumFacing.UP;
            case DOWN:  return EnumFacing.NORTH;
            case SOUTH: return EnumFacing.DOWN;
            default:    return face;
         }
      }
   }

   public static EnumFacing rotate(EnumFacing face, boolean clockwise, Axis axis) {
      if (axis == Axis.X) {
         return rotateX(face, clockwise);
      } else if (axis == Axis.Y) {
         return clockwise ? face.rotateY() : face.rotateYCCW();
      } else {
         return rotateZ(face, clockwise);
      }
   }

   @Nullable
   public static EntityLivingBase findNearestEnemy(World world, Entity enemyTo, double x, double y, double z, double radius, boolean bestSearch) {
      AxisAlignedBB area = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
      List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, area);

      if (entities.isEmpty()) return null;

      Predicate<EntityLivingBase> baseFilter = e ->
              Team.checkIsOpponent(enemyTo, e) && EntitySelectors.NOT_SPECTATING.apply(e);

      if (bestSearch) {
         Predicate<EntityLivingBase> aliveFilter = Predicates.and(baseFilter, e -> e.getHealth() > 0.0F);
         Predicate<EntityLivingBase> monsterFilter = Predicates.and(aliveFilter, e -> e.isCreatureType(EnumCreatureType.MONSTER, false));

         EntityLivingBase target = entities.stream()
                 .filter(monsterFilter::apply)
                 .min(Comparator.comparingDouble(e -> e.getDistanceSq(x, y, z)))
                 .orElse(null);

         if (target == null) {
            target = entities.stream()
                    .filter(aliveFilter::apply)
                    .min(Comparator.comparingDouble(e -> e.getDistanceSq(x, y, z)))
                    .orElse(null);
         }
         return target;
      }

      return entities.stream()
              .filter(baseFilter::apply)
              .min(Comparator.comparingDouble(e -> e.getDistanceSq(x, y, z)))
              .orElse(null);
   }

   public static List<EntityLivingBase> getHostilesInAABBto(
      World world, Vec3d forPosition, double radiusXZ, double radiusY, @Nullable EntityLivingBase hostileTo
   ) {
      AxisAlignedBB bb = new AxisAlignedBB(
         forPosition.x - radiusXZ,
         forPosition.y - radiusY,
         forPosition.z - radiusXZ,
         forPosition.x + radiusXZ,
         forPosition.y + radiusY,
         forPosition.z + radiusXZ
      );
      List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
      List<EntityLivingBase> list2 = new ArrayList<>();

      for (EntityLivingBase entity : list) {
         if (Team.checkIsOpponent(hostileTo, entity)) {
            list2.add(entity);
         }
      }

      return list2;
   }

   public static List<Entity> getEntitiesInAABBof(World world, Entity forPosition, double radius, @Nullable Entity entityExcluding) {
      Vec3d cenVec3d = entityCenterPos(forPosition);
      AxisAlignedBB bb = new AxisAlignedBB(
         cenVec3d.x - radius,
         cenVec3d.y - radius,
         cenVec3d.z - radius,
         cenVec3d.x + radius,
         cenVec3d.y + radius,
         cenVec3d.z + radius
      );
      return world.getEntitiesWithinAABBExcludingEntity(entityExcluding, bb);
   }

   public static List<Entity> getEntitiesInAABBof(World world, Vec3d forPosition, double radius, @Nullable Entity entityExcluding) {
      AxisAlignedBB bb = new AxisAlignedBB(
         forPosition.x - radius,
         forPosition.y - radius,
         forPosition.z - radius,
         forPosition.x + radius,
         forPosition.y + radius,
         forPosition.z + radius
      );
      return world.getEntitiesWithinAABBExcludingEntity(entityExcluding, bb);
   }

   public static List<Entity> getEntitiesInAABBof(World world, BlockPos forPosition, double radius, @Nullable Entity entityExcluding) {
      AxisAlignedBB bb = new AxisAlignedBB(
         forPosition.getX() - radius,
         forPosition.getY() - radius,
         forPosition.getZ() - radius,
         forPosition.getX() + 1 + radius,
         forPosition.getY() + 1 + radius,
         forPosition.getZ() + 1 + radius
      );
      return world.getEntitiesWithinAABBExcludingEntity(entityExcluding, bb);
   }

   public static double getSpeed(Entity entity) {
      return Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
   }

   public static AxisAlignedBB newAABB(Vec3d center, double radius) {
      return new AxisAlignedBB(
         center.x - radius,
         center.y - radius,
         center.z - radius,
         center.x + radius,
         center.y + radius,
         center.z + radius
      );
   }

   public static AxisAlignedBB newAABB(Entity center, double radius) {
      return new AxisAlignedBB(
         center.posX - radius,
         center.posY - radius,
         center.posZ - radius,
         center.posX + radius,
         center.posY + radius,
         center.posZ + radius
      );
   }

   public static AxisAlignedBB newAABB(int pixelsWidth, int pixelsHeight, int pixelsYAOffset) {
      double radius = 0.03125 * pixelsWidth;
      double height = 0.0625 * pixelsHeight;
      double y = 0.0625 * pixelsYAOffset;
      return new AxisAlignedBB(0.5 - radius, y, 0.5 - radius, 0.5 + radius, y + height, 0.5 + radius);
   }

   public static List<Entity> entityUncollidedRayTrace(
           Class<? extends Entity> eclass,
           double blockReachDistance,
           float partialTicks,
           EntityLivingBase entity,
           double size,
           float rotationPitch,
           float rotationYaw
   ) {
      Vec3d vec3d = entity.getPositionEyes(partialTicks);
      Vec3d vec3d1 = pitchYawToVec3D(rotationPitch, rotationYaw); // Сохраняем твою кастомную логику

      Vec3d vec3d2 = vec3d.add(
              vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );

      RayTraceResult raytraceresult = entity.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = raytraceresult.hitVec;
      }

      return findUncollidedEntityOnPath(eclass, vec3d, vec3d2, entity.world, entity, size);
   }

   protected static List<Entity> findUncollidedEntityOnPath(
           Class<? extends Entity> eclass, Vec3d start, Vec3d end, World world, Entity shooter, double size
   ) {
      List<Entity> moblist = new ArrayList<>();
      double expand = size / 2.0;

      AxisAlignedBB pathBox = new AxisAlignedBB(start.x, start.y, start.z, end.x, end.y, end.z).grow(expand);

      List<? extends Entity> list = world.getEntitiesWithinAABB(eclass, pathBox);

      Entity closestEntity = null;
      double minDistanceSq = Double.MAX_VALUE;

      for (Entity entityLiving : list) {
         if (entityLiving != shooter) {
            AxisAlignedBB entityBox = entityLiving.getEntityBoundingBox().grow(expand);
            RayTraceResult intercept = entityBox.calculateIntercept(start, end);

            if (intercept != null) {
               double distanceSq = start.squareDistanceTo(intercept.hitVec);

               if (distanceSq < minDistanceSq) {
                  minDistanceSq = distanceSq;
                  closestEntity = entityLiving;
               }
            } else if (entityBox.contains(start) && minDistanceSq > 0.0D) {
               closestEntity = entityLiving;
               minDistanceSq = 0.0D;
            }
         }
      }

      if (closestEntity != null) {
         moblist.add(closestEntity);
      }

      return moblist;
   }

   public static @NotNull RayTraceResult rayTrace(World world, double blockReachDistance, EntityLivingBase entity, boolean useLiquids) {
      Vec3d startPos = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);

      Vec3d lookVec = entity.getLook(1.0F);

      Vec3d endPos = startPos.add(lookVec.x * blockReachDistance,
              lookVec.y * blockReachDistance,
              lookVec.z * blockReachDistance);

      RayTraceResult res = world.rayTraceBlocks(startPos, endPos, useLiquids, false, true);

      return res != null ? res : new RayTraceResult(RayTraceResult.Type.MISS, endPos, null, new BlockPos(endPos));
   }

   @Nullable
   public static Entity loadEntity(World world, int chunkX, int chunkZ, UUID entityUuid) {
      ClassInheritanceMultiMap<Entity>[] entityLists = world.getChunk(chunkX, chunkZ).getEntityLists();

       for (ClassInheritanceMultiMap<Entity> entityList : entityLists) {
           if (!entityList.isEmpty()) {
               for (Entity entity : entityList) {
                   if (entityUuid.equals(entity.getUniqueID())) {
                       return entity;
                   }
               }
           }
       }

      return null;
   }

   @Nullable
   public static Entity loadEntity(World world, ChunkPos chunk, UUID entityUuid) {
      return loadEntity(world, chunk.x, chunk.z, entityUuid);
   }

   public static int byWeight(Random rand, int... weights) {
      int summ = 0;

      for (int i = 0; i < weights.length; i++) {
         summ += weights[i];
      }

      return byWeight(summ, rand, weights);
   }

   public static int byWeight(int summ, Random rand, int... weights) {
      int r = rand.nextInt(summ);
      int all = 0;

      for (int i = 0; i < weights.length; i++) {
         int weight = weights[i];
         all += weight;
         if (r < all) {
            return i;
         }
      }

      return -1;
   }

   @Nullable
   public static RayTraceResult normalizeRayTraceResult(@Nullable RayTraceResult sourceResult, double offset) {
      if (sourceResult != null && sourceResult.sideHit != null && sourceResult.hitVec != null) {
         Vec3d vec = sourceResult.hitVec;
         sourceResult.hitVec = new Vec3d(
            vec.x + sourceResult.sideHit.getXOffset() * offset,
            vec.y + sourceResult.sideHit.getYOffset() * offset,
            vec.z + sourceResult.sideHit.getZOffset() * offset
         );
      }

      return sourceResult;
   }

   @Nullable
   public static RayTraceResult normalizeRayTraceResult(@Nullable RayTraceResult sourceResult) {
      return normalizeRayTraceResult(sourceResult, 0.001);
   }

   public static double isPointInPlane(Vec3d planePoint, Vec3d normalVector, Vec3d point) {
      return normalVector.x * (point.x - planePoint.x)
         + normalVector.y * (point.y - planePoint.y)
         + normalVector.z * (point.z - planePoint.z);
   }

   public static boolean isBoxInPlane(Vec3d planePoint, Vec3d normalVector, AxisAlignedBB aabb) {
      boolean hasPositive = false;
      boolean hasNegative = false;

      double[] xSide = {aabb.minX, aabb.maxX};
      double[] ySide = {aabb.minY, aabb.maxY};
      double[] zSide = {aabb.minZ, aabb.maxZ};

      for (double x : xSide) {
         for (double y : ySide) {
            for (double z : zSide) {
               double sign = isPointInPlane(planePoint, normalVector, new Vec3d(x, y, z));

               if (sign > 0) hasPositive = true;
               else if (sign < 0) hasNegative = true;
               else return true;

               if (hasPositive && hasNegative) return true;
            }
         }
      }
      return false;
   }

   public static Vec3d getNearestPointInLineToPoint(Vec3d linePoint, Vec3d lineDirection, Vec3d point) {
      Vec3d ac = point.subtract(linePoint);
      double t = ac.dotProduct(lineDirection) / lineDirection.dotProduct(lineDirection);

      return linePoint.add(lineDirection.scale(t));
   }

   public static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
      float f = yawOffset - prevYawOffset;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevYawOffset + partialTicks * f;
   }

   public static double linearDistance(Vec3d vec1, Vec3d vec2, Axis axis) {
      if (axis == Axis.X) {
         return Math.abs(vec1.x - vec2.x);
      } else {
         return axis == Axis.Y ? Math.abs(vec1.y - vec2.y) : Math.abs(vec1.z - vec2.z);
      }
   }

   public static float flatDistance(Vec3d vec1, Vec3d vec2) {
      float f = (float)(vec1.x - vec2.x);
      float f2 = (float)(vec1.z - vec2.z);
      return MathHelper.sqrt(f * f + f2 * f2);
   }

   public static EnumFacing getRandomFacingExcept(@Nullable EnumFacing except) {
      int randi = rand.nextInt(6);

      for (int i = 0; i < 6; i++) {
         EnumFacing got = EnumFacing.byIndex(randi);
         if (got != except) {
            return got;
         }

         randi = next(randi, 1, 6);
      }

      return EnumFacing.SOUTH;
   }

   public static List<Entity> getEntitiesWithinAABBExcludingEntity(World world, Entity excluding, AxisAlignedBB aabb) {
      return world.getEntitiesWithinAABBExcludingEntity(excluding, aabb);
   }

   public static float isInCaves(World world, BlockPos pos) {
      int airs = 0;
      int[] radii = {3, 8};

      Vec3d[] directions = {
              new Vec3d(1, 0, 0), new Vec3d(-1, 0, 0),  // East, West
              new Vec3d(0, 0, 1), new Vec3d(0, 0, -1),  // South, North
              new Vec3d(0, 1, 0), new Vec3d(0, -1, 0),  // Up, Down

              new Vec3d(0.707, 0, 0.707), new Vec3d(-0.707, 0, 0.707),
              new Vec3d(0.707, 0, -0.707), new Vec3d(-0.707, 0, -0.707),
              new Vec3d(0, 0.707, 0.707), new Vec3d(0, 0.707, -0.707),
              new Vec3d(0, -0.707, 0.707), new Vec3d(0, -0.707, -0.707),
              new Vec3d(0.707, 0.707, 0), new Vec3d(-0.707, 0.707, 0),
              new Vec3d(0.707, -0.707, 0), new Vec3d(-0.707, -0.707, 0)
      };

      for (int r : radii) {
         for (Vec3d dir : directions) {
            BlockPos checkPos = new BlockPos(
                    pos.getX() + dir.x * r,
                    pos.getY() + dir.y * r,
                    pos.getZ() + dir.z * r
            );

            if (world.isAirBlock(checkPos)) {
               airs++;
            }
         }
      }

      return 1.0F - (airs / 36.0F);
   }

   public static Random getBestRandomBasedOnCoordinates(int c1, int c2, long c3) {
      Random rand = new Random(c3 ^ c1 ^ c2);

      int exit = 0;
      for (int i = 0; i < 32; i++) {
         exit |= doSomeWithBits(c1, c2, i, rand.nextInt(6));
      }

      rand.setSeed(exit);
      int finalExit = 0;

      for (int i = 0; i < 32; i++) {
         finalExit |= doSomeWithBits(rand.nextInt(), rand.nextInt(), i, rand.nextInt(6));
      }

      return new Random(finalExit);
   }

   private static int doSomeWithBits(int n1, int n2, int offset, int op) {
      int res;
      switch (op) {
         case 0:  res = n1 & n2; break;
         case 1:  res = n1 | n2; break;
         case 2:  res = n1 ^ n2; break;
         case 3:  res = ~(n1 | n2); break;
         case 4:  res = ~(n1 ^ n2); break;
         case 5:  res = ~(n1 & n2); break;
         default: res = 0;
      }
      return res & (1 << offset);
   }

   public static class BlockTraceResult {
      public BlockPos prevPos;
      public BlockPos pos;
      public IBlockState impactState;
      public IBlockState prevState;
      public EnumFacing facing;

      @Override
      public String toString() {
         return "prevPos: "
            + this.prevPos
            + " | pos: "
            + this.pos
            + " | prevState: "
            + this.prevState.getBlock().getRegistryName()
            + " | impactState: "
            + this.impactState.getBlock().getRegistryName();
      }
   }
}
