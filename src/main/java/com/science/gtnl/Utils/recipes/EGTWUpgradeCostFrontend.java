package com.science.gtnl.Utils.recipes;

import static gregtech.api.util.GTRecipeConstants.FOG_UPGRADE_NAME_SHORT;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import com.gtnewhorizons.modularui.api.math.Pos2d;

import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.gui.modularui.UIHelper;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EGTWUpgradeCostFrontend extends RecipeMapFrontend {

    public EGTWUpgradeCostFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(uiPropertiesBuilder, neiPropertiesBuilder);
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return UIHelper.getGridPositions(itemInputCount, 8, 8, 5);
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return UIHelper.getGridPositions(itemOutputCount, 134, 8, 1);
    }

    @Override
    protected void drawDurationInfo(RecipeDisplayInfo recipeInfo) {}

    @Override
    protected void drawEnergyInfo(RecipeDisplayInfo recipeInfo) {}

    @Override
    protected void drawSpecialInfo(RecipeDisplayInfo recipeInfo) {
        String upgradeName = recipeInfo.recipe.getMetadataOrDefault(FOG_UPGRADE_NAME_SHORT, "");
        int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(upgradeName);
        if (width % 2 == 1) width -= 1;
        int xOffset = 18 - width / 2 - 1;
        recipeInfo.drawText(
            EnumChatFormatting.BLUE.toString() + EnumChatFormatting.UNDERLINE + EnumChatFormatting.BOLD + upgradeName,
            119 + xOffset,
            0);
    }
}
