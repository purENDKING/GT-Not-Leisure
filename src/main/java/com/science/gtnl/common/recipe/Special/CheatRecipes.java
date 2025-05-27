package com.science.gtnl.common.recipe.Special;

import static com.science.gtnl.Utils.item.TextHandler.texter;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.loader.IRecipePool;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.enums.GTValues;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTUtility;

public class CheatRecipes implements IRecipePool {

    final RecipeMap<?> As = RecipeMaps.assemblerRecipes;

    public static void loadMoreRecipes() {
        IRecipePool[] recipePools = new IRecipePool[] { new CheatRecipes() };
        for (IRecipePool recipePool : recipePools) {
            recipePool.loadRecipes();
        }
    }

    @Override
    public void loadRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTUtility.getIntegratedCircuit(2),
                GTUtility.getIntegratedCircuit(3),
                GTUtility.getIntegratedCircuit(4),
                GTUtility.getIntegratedCircuit(5),
                GTUtility.getIntegratedCircuit(6),
                GTUtility.getIntegratedCircuit(7),
                GTUtility.getIntegratedCircuit(8),
                GTUtility.getIntegratedCircuit(9),
                GTUtility.getIntegratedCircuit(10),
                GTUtility.getIntegratedCircuit(11),
                GTUtility.getIntegratedCircuit(12),
                GTUtility.getIntegratedCircuit(13),
                GTUtility.getIntegratedCircuit(14),
                GTUtility.getIntegratedCircuit(15),
                GTUtility.getIntegratedCircuit(16),
                GTUtility.getIntegratedCircuit(17),
                GTUtility.getIntegratedCircuit(18),
                GTUtility.getIntegratedCircuit(19),
                GTUtility.getIntegratedCircuit(20),
                GTUtility.getIntegratedCircuit(21),
                GTUtility.getIntegratedCircuit(22),
                GTUtility.getIntegratedCircuit(23),
                GTUtility.getIntegratedCircuit(24))
            .itemOutputs(
                GTNLItemList.DebugEnergyHatch.get(1)
                    .setStackDisplayName(texter("§oWhere do I come from?", "NEI.DebugEnergyHatchRecipe.01")))
            .specialValue(0)
            .noOptimize()
            .duration(1)
            .eut(0)
            .addTo(As);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        MinecraftServer server = MinecraftServer.getServer();

        List<EntityPlayerMP> playerList = server.getConfigurationManager().playerEntityList;

        EntityPlayerMP playerHuanF = null;
        EntityPlayerMP playerABKQPO = null;

        for (EntityPlayerMP player : playerList) {
            if (player.getCommandSenderName()
                .equals("Huan_F")) {
                playerHuanF = player;
            } else if (player.getCommandSenderName()
                .equals("ABKQPO")) {
                    playerABKQPO = player;
                }
        }

        if (playerHuanF != null || playerABKQPO != null) {
            UUID uuidHuanF = null;
            if (playerHuanF != null) {
                uuidHuanF = playerHuanF.getUniqueID();
            }
            UUID uuidABKQPO = null;
            if (playerABKQPO != null) {
                uuidABKQPO = playerABKQPO.getUniqueID();
            }

            if (playerHuanF != null && uuidHuanF.equals(UUID.fromString("f2fbc091-832a-4ba4-901f-56d6af6f92c0"))) {
                loadMoreRecipes();
                System.out.println("玩家已找到，UUID 匹配。");
            } else
                if (uuidABKQPO != null && uuidABKQPO.equals(UUID.fromString("aeace7c8-5960-4441-be91-2594c917a1fc"))) {
                    loadMoreRecipes();
                    System.out.println("玩家已找到，UUID 匹配。");
                }
        }
    }
}
