package com.science.gtnl.common.recipe.Special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import gregtech.api.enums.ItemList;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTRecipe;

public class RemoveRecipes {

    private static boolean hasRun = false;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !hasRun && MainConfig.enableDeleteRecipe) {
            removeRecipes();
            hasRun = true;
        }
    }

    public static void removeRecipes() {
        RecipeMapBackend AutoClaveRecipe = RecipeMaps.autoclaveRecipes.getBackend();
        RecipeMapBackend CircuitAssemblerRecipe = RecipeMaps.circuitAssemblerRecipes.getBackend();
        Map<String, Integer> removedRecipeCounts = new HashMap<>();

        List<GTRecipe> recipesToRemoveFromAutoClave = new ArrayList<>();
        for (GTRecipe recipe : AutoClaveRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output != null) {
                    if (output.isItemEqual(ItemList.Circuit_Wafer_Bioware.get(1))) {
                        recipesToRemoveFromAutoClave.add(recipe);
                        break;
                    }
                }
            }
        }
        AutoClaveRecipe.removeRecipes(recipesToRemoveFromAutoClave);

        List<GTRecipe> recipesToRemoveFromCircuitAssembler = new ArrayList<>();
        for (GTRecipe recipe : CircuitAssemblerRecipe.getAllRecipes()) {
            for (ItemStack output : recipe.mOutputs) {
                if (output != null) {
                    if (output.isItemEqual(ItemList.Circuit_Crystalprocessor.get(1))) {
                        recipesToRemoveFromCircuitAssembler.add(recipe);
                        break;
                    }
                    if (output.isItemEqual(ItemList.Circuit_Crystalcomputer.get(1))) {
                        recipesToRemoveFromCircuitAssembler.add(recipe);
                        break;
                    }
                    if (output.isItemEqual(ItemList.Circuit_Ultimatecrystalcomputer.get(1))) {
                        recipesToRemoveFromCircuitAssembler.add(recipe);
                        break;
                    }
                    if (output.isItemEqual(ItemList.Circuit_Crystalmainframe.get(1))) {
                        recipesToRemoveFromCircuitAssembler.add(recipe);
                        break;
                    }
                    if (output.isItemEqual(ItemList.Circuit_Bioprocessor.get(1))) {
                        recipesToRemoveFromCircuitAssembler.add(recipe);
                        break;
                    }
                }
            }
        }
        CircuitAssemblerRecipe.removeRecipes(recipesToRemoveFromCircuitAssembler);

        if (MainConfig.enableDebugMode) {
            removedRecipeCounts.put("高压釜", recipesToRemoveFromAutoClave.size());
            removedRecipeCounts.put("电路组装机", recipesToRemoveFromCircuitAssembler.size());

            StringBuilder logMessage = new StringBuilder("GTNL: 移除了以下配方池中的配方：");
            for (Map.Entry<String, Integer> entry : removedRecipeCounts.entrySet()) {
                logMessage.append("\n- ")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("个");
            }
            System.out.println(logMessage.toString());
        }
    }

}
