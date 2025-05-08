package com.science.gtnl.common.machine.hatch;

import static com.science.gtnl.Utils.SteamWirelessNetworkManager.addSteamToGlobalSteamMap;
import static gregtech.api.interfaces.tileentity.IWirelessEnergyHatchInformation.number_of_energy_additions;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.ImmutableSet;
import com.science.gtnl.Utils.item.TextLocalization;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.common.misc.spaceprojects.SpaceProjectManager;
import gtPlusPlus.core.util.minecraft.FluidUtils;

public class WirelessSteamDynamoHatch extends CustomFluidHatch {

    private UUID owner_uuid;

    public WirelessSteamDynamoHatch(final int aID, final String aName, final String aNameRegional, int aTier) {
        super(
            ImmutableSet.of(
                FluidUtils.getSteam(1)
                    .getFluid()),
            aTier == 0 ? 128000000 : Integer.MAX_VALUE,
            aID,
            aName,
            aNameRegional,
            aTier);
    }

    public WirelessSteamDynamoHatch(final String aName, final ITexture[][][] aTextures, int aTier) {
        super(
            ImmutableSet.of(
                FluidUtils.getSteam(1)
                    .getFluid()),
            aTier == 0 ? 128000000 : Integer.MAX_VALUE,
            aName,
            aTier,
            new String[] { "" },
            aTextures);
    }

    @Override
    public MetaTileEntity newMetaEntity(final IGregTechTileEntity aTileEntity) {
        return new WirelessSteamDynamoHatch(this.mName, this.mTextures, mTier);
    }

    @Override
    public String[] getDescription() {
        ArrayList<String> desc = new ArrayList<>();

        desc.add(StatCollector.translateToLocal("Tooltip_WirelessSteamDynamoHatch_00"));
        desc.add(StatCollector.translateToLocal("Tooltip_WirelessSteamDynamoHatch_01"));
        desc.add(TextLocalization.HatchCustomFluid_01 + getCapacity() + "L");
        if (mTier == 0) {
            desc.add(StatCollector.translateToLocal("Tooltip_PipelessSteamVent_00"));
            desc.add(StatCollector.translateToLocal("Tooltip_PipelessSteamVent_01"));
            desc.add(StatCollector.translateToLocal("Tooltip_PipelessSteamVent_02"));
        } else {
            desc.add(StatCollector.translateToLocal("Tooltip_PipelessJetstreamVent_00"));
            desc.add(StatCollector.translateToLocal("Tooltip_PipelessJetstreamVent_01"));
            desc.add(StatCollector.translateToLocal("Tooltip_PipelessJetstreamVent_02"));
        }

        return desc.toArray(new String[] {});
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_WIRELESS_ON[0] };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, Textures.BlockIcons.OVERLAYS_ENERGY_IN_MULTI_WIRELESS_ON[0] };
    }

    @Override
    public ITexture getBaseTexture(int colorIndex) {
        if (mTier == 0) {
            return TextureFactory.of(Textures.BlockIcons.MACHINE_BRONZE_SIDE);
        }
        return TextureFactory.of(Textures.BlockIcons.MACHINE_STEEL_SIDE);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);

        if (!aBaseMetaTileEntity.isServerSide()) return;

        owner_uuid = aBaseMetaTileEntity.getOwnerUuid();

        SpaceProjectManager.checkOrCreateTeam(owner_uuid);

        tryFetchingSteam();
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {

        super.onPreTick(aBaseMetaTileEntity, aTick);

        if (aBaseMetaTileEntity.isServerSide()) {
            if (aTick % number_of_energy_additions == 0L) {
                tryFetchingSteam();
            }
        }
    }

    private void tryFetchingSteam() {
        FluidStack currentSteamStack = getFillableStack();
        if (currentSteamStack != null && currentSteamStack.amount > 0) {

            int currentSteam = currentSteamStack.amount;

            if (!addSteamToGlobalSteamMap(owner_uuid, currentSteam)) return;
            drain(currentSteam, true);
        }
    }
}
