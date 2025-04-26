package com.science.gtnl.mixins.late.Gregtech;

import static gregtech.api.recipe.RecipeMaps.scannerFakeRecipes;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_ITEM;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_TIME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.IRecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.GTUtility;

@SuppressWarnings("UnusedMixin")
@Mixin(value = GTRecipeConstants.class, remap = false)
public abstract class GTRecipeConstants_Mixin {

    @Shadow
    @Mutable
    @Final
    public static IRecipeMap AssemblyLine;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onClinit(CallbackInfo ci) {
        AssemblyLine = IRecipeMap.newRecipeMap(builder -> {
            Optional<GTRecipe.GTRecipe_WithAlt> rr = builder.forceOreDictInput()
                .validateInputCount(4, 16)
                .validateOutputCount(1, 1)
                .validateOutputFluidCount(-1, 0)
                .validateInputFluidCount(1, 4)
                .buildWithAlt();
            if (!rr.isPresent()) return Collections.emptyList();
            GTRecipe.GTRecipe_WithAlt r = rr.get();
            ItemStack[][] mOreDictAlt = r.mOreDictAlt;
            Object[] inputs = builder.getItemInputsOreDict();
            ItemStack aResearchItem = builder.getMetadata(RESEARCH_ITEM);
            if (aResearchItem == null) return Collections.emptyList();
            ItemStack aOutput = r.mOutputs[0];
            int tPersistentHash = 1;

            for (int i = 0; i < mOreDictAlt.length; i++) {
                ItemStack[] alts = mOreDictAlt[i];
                Object input = inputs[i];

                if (input == null) {
                    GTLog.err.println(
                        "addAssemblingLineRecipe " + aResearchItem.getDisplayName()
                            + " --> "
                            + aOutput.getUnlocalizedName()
                            + " has null in recipe");
                }

                if (input instanceof ItemStack) {
                    tPersistentHash = tPersistentHash * 31 + GTUtility.persistentHash((ItemStack) input, true, false);
                } else if (input instanceof ItemStack[]) {
                    for (ItemStack alt : (ItemStack[]) input) {
                        tPersistentHash = tPersistentHash * 31 + GTUtility.persistentHash(alt, true, false);
                        if (alt == null) {
                            GTLog.err.println(
                                "addAssemblingLineRecipe " + aResearchItem.getDisplayName()
                                    + " --> "
                                    + aOutput.getUnlocalizedName()
                                    + " has null alt");
                        }
                    }
                    tPersistentHash *= 31;
                } else if (input instanceof Object[]objs) {
                    Arrays.sort(
                        alts,
                        Comparator
                            // 判断是否是 NHcore 的物品
                            .<ItemStack, Boolean>comparing(s -> {
                                GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(s.getItem());
                                return uid != null && "dreamcraft".equals(uid.modId);
                            }, Comparator.reverseOrder())
                            // 原排序逻辑
                            .thenComparing(s -> GameRegistry.findUniqueIdentifierFor(s.getItem()).modId)
                            .thenComparing(s -> GameRegistry.findUniqueIdentifierFor(s.getItem()).name)
                            .thenComparingInt(Items.feather::getDamage)
                            .thenComparingInt(s -> s.stackSize));

                    tPersistentHash = tPersistentHash * 31 + (objs[0] == null ? "" : objs[0].toString()).hashCode();
                    tPersistentHash = tPersistentHash * 31 + ((Number) objs[1]).intValue();
                }
            }

            tPersistentHash = tPersistentHash * 31 + GTUtility.persistentHash(aResearchItem, true, false);
            tPersistentHash = tPersistentHash * 31 + GTUtility.persistentHash(aOutput, true, false);
            for (FluidStack f : r.mFluidInputs) {
                if (f != null) {
                    tPersistentHash = tPersistentHash * 31 + GTUtility.persistentHash(f, true, false);
                }
            }
            int aResearchTime = builder.getMetadataOrDefault(RESEARCH_TIME, 0);
            tPersistentHash = tPersistentHash * 31 + aResearchTime;
            tPersistentHash = tPersistentHash * 31 + r.mDuration;
            tPersistentHash = tPersistentHash * 31 + r.mEUt;

            GTRecipe.RecipeAssemblyLine tRecipe = new GTRecipe.RecipeAssemblyLine(
                aResearchItem,
                aResearchTime,
                r.mInputs,
                r.mFluidInputs,
                aOutput,
                r.mDuration,
                r.mEUt,
                r.mOreDictAlt);
            tRecipe.setPersistentHash(tPersistentHash);
            GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes.add(tRecipe);
            AssemblyLineUtils.addRecipeToCache(tRecipe);

            Collection<GTRecipe> ret = new ArrayList<>(3);
            ret.addAll(
                GTValues.RA.stdBuilder()
                    .itemInputs(aResearchItem)
                    .itemOutputs(aOutput)
                    .special(tRecipe.newDataStickForNEI("Writes Research result"))
                    .duration(aResearchTime)
                    .eut(TierEU.RECIPE_LV)
                    .specialValue(-201)
                    .noOptimize()
                    .ignoreCollision()
                    .fake()
                    .addTo(scannerFakeRecipes));

            ret.add(
                RecipeMaps.assemblylineVisualRecipes.addFakeRecipe(
                    false,
                    r.mInputs,
                    new ItemStack[] { aOutput },
                    new ItemStack[] { tRecipe.newDataStickForNEI("Reads Research result") },
                    r.mFluidInputs,
                    null,
                    r.mDuration,
                    r.mEUt,
                    0,
                    r.mOreDictAlt,
                    false));

            return ret;
        });
    }
}
