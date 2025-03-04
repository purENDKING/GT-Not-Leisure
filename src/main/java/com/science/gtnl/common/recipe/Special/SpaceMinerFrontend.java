package com.science.gtnl.common.recipe.Special;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.gtnewhorizons.gtnhintergalactic.recipe.IGRecipeMaps;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.misc.spaceprojects.SpaceProjectManager;
import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpaceMinerFrontend extends RecipeMapFrontend {

    public SpaceMinerFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(
            uiPropertiesBuilder,
            neiPropertiesBuilder.neiSpecialInfoFormatter(new SpaceAssemblerSpecialValueFormatter()));
    }

    private static class SpaceAssemblerSpecialValueFormatter implements INEISpecialInfoFormatter {

        @Override
        public List<String> format(RecipeDisplayInfo recipeInfo) {
            List<String> specialInfo = new ArrayList<>();
            specialInfo.add(GCCoreUtil.translateWithFormat("ig.nei.module", recipeInfo.recipe.mSpecialValue));

            String neededProject = recipeInfo.recipe.getMetadata(IGRecipeMaps.SPACE_PROJECT);
            String neededProjectLocation = recipeInfo.recipe.getMetadata(IGRecipeMaps.SPACE_LOCATION);
            if (neededProject != null && !neededProject.isEmpty()) {
                specialInfo.add(
                    String.format(
                        GCCoreUtil.translate("ig.nei.spaceassembler.project"),
                        SpaceProjectManager.getProject(neededProject)
                            .getLocalizedName()));
                specialInfo.add(
                    String.format(
                        GCCoreUtil.translate("ig.nei.spaceassembler.projectAt"),
                        neededProjectLocation == null || neededProjectLocation.isEmpty()
                            ? GCCoreUtil.translate("ig.nei.spaceassembler.projectAnyLocation")
                            : GCCoreUtil.translate(
                                SpaceProjectManager.getLocation(neededProjectLocation)
                                    .getUnlocalizedName())));
            }
            return specialInfo;
        }
    }

}
