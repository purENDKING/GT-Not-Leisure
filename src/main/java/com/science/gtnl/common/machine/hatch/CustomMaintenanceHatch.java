package com.science.gtnl.common.machine.hatch;

import static net.minecraft.util.StatCollector.translateToLocal;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import com.gtnewhorizons.modularui.common.widget.textfield.TextFieldWidget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.gui.modularui.GTUIInfos;
import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatchMaintenance;
import gregtech.api.render.TextureFactory;
import tectech.util.CommonValues;

public class CustomMaintenanceHatch extends MTEHatchMaintenance
    implements ICleanRoomMaintenance, IConfigurationMaintenance {

    private static Textures.BlockIcons.CustomIcon face;
    protected int configTime = 100;

    public CustomMaintenanceHatch(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier);
    }

    public CustomMaintenanceHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures, false);
    }

    @Override
    public String[] getDescription() {
        return new String[] { CommonValues.THETA_MOVEMENT,
            translateToLocal("gt.blockmachines.debug.tt.maintenance.desc.0"), // For automatically maintaining
            // Multiblocks
            translateToLocal("gt.blockmachines.debug.tt.maintenance.desc.1"), // Does fix everything but itself.
            EnumChatFormatting.AQUA + translateToLocal("gt.blockmachines.debug.tt.maintenance.desc.2") // Fixing is
            // for plebs!
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        super.registerIcons(aBlockIconRegister);
        face = new Textures.BlockIcons.CustomIcon("iconsets/OVERLAY_FULLAUTOMAINTENANCE");
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(face) };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(face) };
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new CustomMaintenanceHatch(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        this.mWrench = this.mScrewdriver = this.mSoftHammer = this.mHardHammer = this.mCrowbar = this.mSolderingTool = true;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, ForgeDirection side,
        float aX, float aY, float aZ) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    public int getConfigTime() {
        return configTime;
    }

    @Override
    public int getCleanroomTier() {
        return 0;
    }

    @Override
    public boolean isConfiguration() {
        return false;
    }

    @Override
    public int minConfigTime() {
        return 100;
    }

    @Override
    public int maxConfigTime() {
        return 100;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("configTime", configTime);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        configTime = aNBT.getInteger("configTime");
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }

        if (isConfiguration()) GTUIInfos.openGTTileEntityUI(aBaseMetaTileEntity, aPlayer);
        return true;
    }

    @Override
    public void addUIWidgets(ModularWindow.Builder builder, UIBuildContext buildContext) {
        if (isConfiguration()) {
            builder.widget(
                TextWidget.localised("Info_ConfigurationMaintenanceHatch_00")
                    .setPos(49, 18)
                    .setSize(81, 14))
                .widget(
                    new TextFieldWidget().setSetterInt(val -> configTime = val)
                        .setGetterInt(() -> configTime)
                        .setNumbers(minConfigTime(), maxConfigTime())
                        .setOnScrollNumbers(1, 4, 64)
                        .setTextAlignment(Alignment.Center)
                        .setTextColor(Color.WHITE.normal)
                        .setSize(70, 18)
                        .setPos(54, 36)
                        .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD));
        }
    }
}
