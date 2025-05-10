package com.science.gtnl.common.machine.multiblock.ModuleMachine.EGTW;

import com.science.gtnl.Utils.item.TextLocalization;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.MultiblockTooltipBuilder;

public class EGTWFusionModule extends EternalGregTechWorkshopModule {

    public EGTWFusionModule(String aName) {
        super(aName);
    }

    public EGTWFusionModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new EGTWFusionModule(this.mName);
    }

    @Override
    public int getMaxParallelRecipes() {
        return 1;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.fusionRecipes;
    }

    @Override
    public MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(TextLocalization.EGTWFusionModuleRecipeType)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .beginStructureBlock(9, 5, 7, true)
            .addInputBus(TextLocalization.Tooltip_EGTWFusionModule_Casing, 1)
            .addOutputBus(TextLocalization.Tooltip_EGTWFusionModule_Casing, 1)
            .addInputHatch(TextLocalization.Tooltip_EGTWFusionModule_Casing, 1)
            .addOutputHatch(TextLocalization.Tooltip_EGTWFusionModule_Casing, 1)
            .toolTipFinisher();
        return tt;
    }
}
