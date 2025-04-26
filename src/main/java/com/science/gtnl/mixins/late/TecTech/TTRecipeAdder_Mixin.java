package com.science.gtnl.mixins.late.TecTech;

import java.util.Comparator;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import cpw.mods.fml.common.registry.GameRegistry;
import tectech.recipe.TTRecipeAdder;

@SuppressWarnings("UnusedMixin")
@Mixin(value = TTRecipeAdder.class, remap = false)
public abstract class TTRecipeAdder_Mixin {

    @Redirect(
        method = "addResearchableAssemblylineRecipe(Lnet/minecraft/item/ItemStack;IIII[Ljava/lang/Object;[Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/item/ItemStack;II)Z",
        at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"))
    private static void redirectSort(List<ItemStack> tList, Comparator<ItemStack> original) {
        tList.sort(Comparator.<ItemStack, Boolean>comparing(s -> {
            GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(s.getItem());
            return uid != null && "dreamcraft".equals(uid.modId);
        }, Comparator.reverseOrder())
            .thenComparing(s -> {
                GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(s.getItem());
                return uid == null ? "" : uid.modId;
            })
            .thenComparing(s -> {
                GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(s.getItem());
                return uid == null ? "" : uid.name;
            })
            .thenComparingInt(Items.feather::getDamage)
            .thenComparingInt(s -> s.stackSize));
    }
}
