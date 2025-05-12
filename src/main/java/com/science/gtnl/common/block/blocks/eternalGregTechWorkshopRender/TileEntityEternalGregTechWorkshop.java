package com.science.gtnl.common.block.blocks.eternalGregTechWorkshopRender;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityEternalGregTechWorkshop extends TileEntity {

    private float rotAngle = 0, rotAxisX = 1, rotAxisY = 0, rotAxisZ = 0, offsetX, offsetY, offsetZ;
    private AxisAlignedBB renderBoundingBox;
    private int renderCount = 1;

    private static final String NBT_TAG = "EGTWRender:";
    private static final String ROT_ANGLE_NBT_TAG = NBT_TAG + "ROT_ANGLE";
    private static final String ROT_AXIS_X_NBT_TAG = NBT_TAG + "ROT_AXIS_X";
    private static final String ROT_AXIS_Y_NBT_TAG = NBT_TAG + "ROT_AXIS_Y";
    private static final String ROT_AXIS_Z_NBT_TAG = NBT_TAG + "ROT_AXIS_Z";
    private static final String OFFSET_X_NBT_TAG = NBT_TAG + "OFFSET__X";
    private static final String OFFSET_Y_NBT_TAG = NBT_TAG + "OFFSET_Y";
    private static final String OFFSET_Z_NBT_TAG = NBT_TAG + "OFFSET_Z";
    private static final String RENDERS_NBT_TAG = NBT_TAG + "RENDERS";

    private static final double RING_RADIUS = 63;
    private static final double BEAM_LENGTH = 59;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (renderBoundingBox == null) {
            double x = this.xCoord;
            double y = this.yCoord;
            double z = this.zCoord;

            // This could possibly be made smaller by figuring out the beam direction,
            // but since this is not always known (set dynamically by the MTE), this
            // currently just bounds as if the beam is in all 4 directions.
            renderBoundingBox = AxisAlignedBB.getBoundingBox(
                x - RING_RADIUS - BEAM_LENGTH,
                y - RING_RADIUS - BEAM_LENGTH,
                z - RING_RADIUS - BEAM_LENGTH,
                x + RING_RADIUS + BEAM_LENGTH + 1,
                y + RING_RADIUS + BEAM_LENGTH + 1,
                z + RING_RADIUS + BEAM_LENGTH + 1);
        }
        return renderBoundingBox;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return Double.MAX_VALUE;
    }

    public int getRenderCount() {
        return renderCount;
    }

    public void setRenderCount(int count) {
        if (renderCount < 1) return;
        renderCount = count;
    }

    public float getRotAngle() {
        return rotAngle;
    }

    public float getRotAxisX() {
        return rotAxisX;
    }

    public float getRotAxisY() {
        return rotAxisY;
    }

    public float getRotAxisZ() {
        return rotAxisZ;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public static float interpolate(float x0, float x1, float y0, float y1, float x) {
        return y0 + ((x - x0) * (y1 - y0)) / (x1 - x0);
    }

    public void setRenderRotation(ForgeDirection direction) {
        switch (direction) {
            case SOUTH -> rotAngle = 90;
            case NORTH -> rotAngle = 90;
            case WEST -> rotAngle = 0;
            case EAST -> rotAngle = 180;
            case UP -> rotAngle = -90;
            case DOWN -> rotAngle = -90;
        }
        rotAxisX = 0;
        rotAxisY = direction.offsetZ + direction.offsetX;
        rotAxisZ = direction.offsetY;
        offsetX = direction.offsetX;
        offsetY = direction.offsetY;
        offsetZ = direction.offsetZ;

        updateToClient();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(RENDERS_NBT_TAG, renderCount);
        compound.setFloat(ROT_ANGLE_NBT_TAG, rotAngle);
        compound.setFloat(ROT_AXIS_X_NBT_TAG, rotAxisX);
        compound.setFloat(ROT_AXIS_Y_NBT_TAG, rotAxisY);
        compound.setFloat(ROT_AXIS_Z_NBT_TAG, rotAxisZ);
        compound.setFloat(OFFSET_X_NBT_TAG, offsetX);
        compound.setFloat(OFFSET_Y_NBT_TAG, offsetY);
        compound.setFloat(OFFSET_Z_NBT_TAG, offsetZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        renderCount = compound.getInteger(RENDERS_NBT_TAG);
        if (renderCount < 1) renderCount = 1;

        rotAngle = compound.getFloat(ROT_ANGLE_NBT_TAG);
        rotAxisX = compound.getFloat(ROT_AXIS_X_NBT_TAG);
        rotAxisY = compound.getFloat(ROT_AXIS_Y_NBT_TAG);
        rotAxisZ = compound.getFloat(ROT_AXIS_Z_NBT_TAG);
        offsetX = compound.getFloat(OFFSET_X_NBT_TAG);
        offsetY = compound.getFloat(OFFSET_Y_NBT_TAG);
        offsetZ = compound.getFloat(OFFSET_Z_NBT_TAG);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    public void updateToClient() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}
