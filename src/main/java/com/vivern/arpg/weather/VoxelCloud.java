package com.vivern.arpg.weather;

import com.vivern.arpg.events.Debugger;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class VoxelCloud {

    public static float[] colorBuffer = new float[4];
    public float[] lightBuffer = new float[6];
    public boolean up;
    public boolean down;
    public boolean west;
    public boolean east;
    public boolean north;
    public boolean south;
    public int columnTop;
    public int columnBottom;
    public int vertexNum;

    public boolean hasFacing(EnumFacing facing) {
        if (facing == EnumFacing.DOWN) {
            return this.down;
        } else if (facing == EnumFacing.UP) {
            return this.up;
        } else if (facing == EnumFacing.NORTH) {
            return this.north;
        } else if (facing == EnumFacing.SOUTH) {
            return this.south;
        } else {
            return facing == EnumFacing.WEST ? this.west : this.east;
        }
    }

    public void setFacesLight(VoxelCloudField field, int arrayY) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (this.hasFacing(facing)) {
                this.lightBuffer[facing.getIndex()] = field.voxelCloudInfo.getLightInFace(field, this, arrayY, facing);
            }
        }
    }

    public void render(VoxelCloudField field, BufferBuilder bufferbuilder, double width, double x, double y, double z, double cameraX, double cameraY, double cameraZ, int arrayX, int arrayY, int arrayZ) {
        this.vertexNum = 0;
        if (this.south) {
            this.vertex(field, bufferbuilder, x, y, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.SOUTH_WEST_DOWN);
            this.vertex(field, bufferbuilder, x + width, y, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.SOUTH_EAST_DOWN);
            this.vertex(field, bufferbuilder, x + width, y + width, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.SOUTH_EAST_UP);
            this.vertex(field, bufferbuilder, x, y + width, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.SOUTH_WEST_UP);
        }

        if (this.north) {
            this.vertex(field, bufferbuilder, x, y + width, z, arrayX, arrayY, arrayZ, EnumCubeVertex.NORTH_WEST_UP);
            this.vertex(field, bufferbuilder, x + width, y + width, z, arrayX, arrayY, arrayZ, EnumCubeVertex.NORTH_EAST_UP);
            this.vertex(field, bufferbuilder, x + width, y, z, arrayX, arrayY, arrayZ, EnumCubeVertex.NORTH_EAST_DOWN);
            this.vertex(field, bufferbuilder, x, y, z, arrayX, arrayY, arrayZ, EnumCubeVertex.NORTH_WEST_DOWN);
        }

        if (this.east) {
            this.vertex(field, bufferbuilder, x + width, y + width, z, arrayX, arrayY, arrayZ, EnumCubeVertex.EAST_NORTH_UP);
            this.vertex(field, bufferbuilder, x + width, y + width, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.EAST_SOUTH_UP);
            this.vertex(field, bufferbuilder, x + width, y, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.EAST_SOUTH_DOWN);
            this.vertex(field, bufferbuilder, x + width, y, z, arrayX, arrayY, arrayZ, EnumCubeVertex.EAST_NORTH_DOWN);
        }

        if (this.west) {
            this.vertex(field, bufferbuilder, x, y, z, arrayX, arrayY, arrayZ, EnumCubeVertex.WEST_NORTH_DOWN);
            this.vertex(field, bufferbuilder, x, y, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.WEST_SOUTH_DOWN);
            this.vertex(field, bufferbuilder, x, y + width, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.WEST_SOUTH_UP);
            this.vertex(field, bufferbuilder, x, y + width, z, arrayX, arrayY, arrayZ, EnumCubeVertex.WEST_NORTH_UP);
        }

        if (this.down) {
            this.vertex(field, bufferbuilder, x, y, z, arrayX, arrayY, arrayZ, EnumCubeVertex.DOWN_NORTH_WEST);
            this.vertex(field, bufferbuilder, x + width, y, z, arrayX, arrayY, arrayZ, EnumCubeVertex.DOWN_NORTH_EAST);
            this.vertex(field, bufferbuilder, x + width, y, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.DOWN_EAST_SOUTH);
            this.vertex(field, bufferbuilder, x, y, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.DOWN_WEST_SOUTH);
        }

        if (this.up) {
            this.vertex(field, bufferbuilder, x, y + width, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.UP_WEST_SOUTH);
            this.vertex(field, bufferbuilder, x + width, y + width, z + width, arrayX, arrayY, arrayZ, EnumCubeVertex.UP_EAST_SOUTH);
            this.vertex(field, bufferbuilder, x + width, y + width, z, arrayX, arrayY, arrayZ, EnumCubeVertex.UP_NORTH_EAST);
            this.vertex(field, bufferbuilder, x, y + width, z, arrayX, arrayY, arrayZ, EnumCubeVertex.UP_NORTH_WEST);
        }
    }

    public void vertex(VoxelCloudField field, BufferBuilder bufferbuilder, double x, double y, double z, int arrayX, int arrayY, int arrayZ, EnumCubeVertex vertex) {
        if (Debugger.floats[13] == 0.0F) {
            field.writeColorInPoint(this, colorBuffer, x, y, z, arrayX, arrayY, arrayZ, vertex);
        }

        if (Debugger.floats[14] == 0.0F) {
            bufferbuilder.pos(x, y, z).color(colorBuffer[0], colorBuffer[1], colorBuffer[2], colorBuffer[3]).endVertex();
        }

        this.vertexNum++;
    }

    public float getMostSelfLightning(EnumCubeVertex vertex) {
        float light1 = this.hasFacing(vertex.mainFacing) ? this.lightBuffer[vertex.mainFacing.getIndex()] : 0.0F;
        float light2 = this.hasFacing(vertex.facing2) ? this.lightBuffer[vertex.facing2.getIndex()] : 0.0F;
        float light3 = this.hasFacing(vertex.facing3) ? this.lightBuffer[vertex.facing3.getIndex()] : 0.0F;
        return Math.max(light1, Math.max(light2, light3));
    }

    public float getMostSurroundingsLightning(VoxelCloudField field, EnumCubeVertex vertexm, int arrayX, int arrayY, int arrayZ) {
        float light = this.getMostSelfLightning(vertexm);

        for (int i = 0; i < 7 && !(light >= 1.0F); i++) {
            Vec3i surroundOffset = vertexm.surroundings[i];
            VoxelCloud surroundCloud = field.getVoxel(arrayX + surroundOffset.getX(), arrayY + surroundOffset.getY(), arrayZ + surroundOffset.getZ());
            if (surroundCloud != null) {
                float surroundLight = surroundCloud.getMostSelfLightning(vertexm.surroundingsVertexes[i]);
                if (surroundLight > light) {
                    light = surroundLight;
                }
            }
        }

        return light;
    }

    public static boolean isDownVertex(int vertexNum) {
        return vertexNum == 0 || vertexNum == 1 || vertexNum == 6 || vertexNum == 7 || vertexNum >= 10 && vertexNum <= 13 || vertexNum >= 16 && vertexNum <= 19;
    }

    public enum EnumCubeVertex {
        SOUTH_WEST_DOWN(EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN, 0),
        SOUTH_EAST_DOWN(EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.DOWN, 1),
        SOUTH_EAST_UP(EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP, 2),
        SOUTH_WEST_UP(EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, 3),
        NORTH_WEST_UP(EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.UP, 4),
        NORTH_EAST_UP(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.UP, 5),
        NORTH_EAST_DOWN(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.DOWN, 6),
        NORTH_WEST_DOWN(EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.DOWN, 7),
        EAST_NORTH_UP(EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.UP, 8),
        EAST_SOUTH_UP(EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.UP, 9),
        EAST_SOUTH_DOWN(EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.DOWN, 10),
        EAST_NORTH_DOWN(EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.DOWN, 11),
        WEST_NORTH_DOWN(EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.DOWN, 12),
        WEST_SOUTH_DOWN(EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.DOWN, 13),
        WEST_SOUTH_UP(EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.UP, 14),
        WEST_NORTH_UP(EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.UP, 15),
        DOWN_NORTH_WEST(EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.WEST, 16),
        DOWN_NORTH_EAST(EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST, 17),
        DOWN_EAST_SOUTH(EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.SOUTH, 18),
        DOWN_WEST_SOUTH(EnumFacing.DOWN, EnumFacing.WEST, EnumFacing.SOUTH, 19),
        UP_WEST_SOUTH(EnumFacing.UP, EnumFacing.WEST, EnumFacing.SOUTH, 20),
        UP_EAST_SOUTH(EnumFacing.UP, EnumFacing.EAST, EnumFacing.SOUTH, 21),
        UP_NORTH_EAST(EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, 22),
        UP_NORTH_WEST(EnumFacing.UP, EnumFacing.NORTH, EnumFacing.WEST, 23);

        public final EnumFacing mainFacing;
        public final EnumFacing facing2;
        public final EnumFacing facing3;

        private final int id;
        private final Vec3i[] surroundings;
        private EnumCubeVertex[] surroundingsVertexes;

        private static final EnumCubeVertex[] VALUES = values();

        EnumCubeVertex(EnumFacing mainFacing, EnumFacing facing2, EnumFacing facing3, int id) {
            this.mainFacing = mainFacing;
            this.facing2 = facing2;
            this.facing3 = facing3;
            this.id = id;
            this.surroundings = this.initSurroundings();
        }

        private Vec3i[] initSurroundings() {
            Vec3i[] vecs = new Vec3i[7];
            for (int i = 1; i <= 7; i++) {
                int i1 = (i >> 2) & 1;
                int i2 = (i >> 1) & 1;
                int i3 = i & 1;

                vecs[i - 1] = new Vec3i(
                        this.mainFacing.getXOffset() * i1 + this.facing2.getXOffset() * i2 + this.facing3.getXOffset() * i3,
                        this.mainFacing.getYOffset() * i1 + this.facing2.getYOffset() * i2 + this.facing3.getYOffset() * i3,
                        this.mainFacing.getZOffset() * i1 + this.facing2.getZOffset() * i2 + this.facing3.getZOffset() * i3
                );
            }
            return vecs;
        }

        private void initSurroundingsVertex() {
            this.surroundingsVertexes = new EnumCubeVertex[7];
            for (int i = 1; i <= 7; i++) {
                EnumFacing mf = ((i >> 2) & 1) > 0 ? this.mainFacing.getOpposite() : this.mainFacing;
                EnumFacing f2 = ((i >> 1) & 1) > 0 ? this.facing2.getOpposite() : this.facing2;
                EnumFacing f3 = (i & 1) > 0 ? this.facing3.getOpposite() : this.facing3;

                EnumCubeVertex found = this.find(mf, f2, f3);
                if (found == null) {
                    throw new RuntimeException("initSurroundingsVertex: Vertex not found for " + this.name());
                }
                this.surroundingsVertexes[i - 1] = found;
            }
        }

        public EnumCubeVertex find(EnumFacing mainf, EnumFacing face2, EnumFacing face3) {
            for (EnumCubeVertex vertex : VALUES) {
                if (vertex.mainFacing == mainf &&
                        ((vertex.facing2 == face2 && vertex.facing3 == face3) ||
                                (vertex.facing2 == face3 && vertex.facing3 == face2))) {
                    return vertex;
                }
            }
            return null;
        }

        public double calculatePlanarDistance(double x, double y, double z, Vec3d pointTo) {
            double f1, f2;
            switch (this.mainFacing.getAxis()) {
                case X:
                    f1 = y - pointTo.y;
                    f2 = z - pointTo.z;
                    break;
                case Y:
                    f1 = x - pointTo.x;
                    f2 = z - pointTo.z;
                    break;
                default:
                    f1 = x - pointTo.x;
                    f2 = y - pointTo.y;
                    break;
            }
            return Math.sqrt(f1 * f1 + f2 * f2);
        }

        public double highestCollinearity(VoxelCloud voxelCloud, double x, double y, double z, Vec3d pointTo) {
            return Math.max(axisCollinearity(voxelCloud, this.mainFacing, x, y, z, pointTo),
                    Math.max(axisCollinearity(voxelCloud, this.facing2, x, y, z, pointTo),
                            axisCollinearity(voxelCloud, this.facing3, x, y, z, pointTo)));
        }

        public double highestCollinearity(double x, double y, double z, Vec3d pointTo) {
            return Math.max(axisCollinearity(this.mainFacing, x, y, z, pointTo),
                    Math.max(axisCollinearity(this.facing2, x, y, z, pointTo),
                            axisCollinearity(this.facing3, x, y, z, pointTo)));
        }

        public static double axisCollinearity(EnumFacing facing, double x, double y, double z, Vec3d pointTo) {
            switch (facing) {
                case SOUTH: return pointTo.z - z;
                case NORTH: return z - pointTo.z;
                case EAST:  return pointTo.x - x;
                case WEST:  return x - pointTo.x;
                case UP:    return pointTo.y - y;
                case DOWN:  return y - pointTo.y;
                default:    return 0.0;
            }
        }

        public static double axisCollinearity(VoxelCloud voxelCloud, EnumFacing facing, double x, double y, double z, Vec3d pointTo) {
            switch (facing) {
                case SOUTH: if (voxelCloud.south) return pointTo.z - z; break;
                case NORTH: if (voxelCloud.north) return z - pointTo.z; break;
                case EAST:  if (voxelCloud.east)  return pointTo.x - x; break;
                case WEST:  if (voxelCloud.west)  return x - pointTo.x; break;
                case UP:    if (voxelCloud.up)    return pointTo.y - y; break;
                case DOWN:  if (voxelCloud.down)  return y - pointTo.y; break;
            }
            return 0.0;
        }

        public int getId() {
            return this.id;
        }

        public Vec3i[] getSurroundings() {
            return this.surroundings;
        }

        public EnumCubeVertex[] getSurroundingsVertexes() {
            return this.surroundingsVertexes;
        }

        static {
            for (EnumCubeVertex vertex : VALUES) {
                vertex.initSurroundingsVertex();

                for (int i = 0; i < 7; i++) {
                    if (vertex.surroundings[i] == null || vertex.surroundingsVertexes[i] == null) {
                        throw new RuntimeException("EnumCubeVertex: Critical initialization failure for " + vertex.name());
                    }
                }
            }
        }
    }

}
