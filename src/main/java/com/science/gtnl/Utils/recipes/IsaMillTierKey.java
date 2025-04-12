package com.science.gtnl.Utils.recipes;

import static gregtech.api.util.GTUtility.trans;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IsaMillTierKey extends RecipeMetadataKey<Integer> {

    public static final IsaMillTierKey INSTANCE = new IsaMillTierKey();

    private IsaMillTierKey() {
        super(Integer.class, "isamill_tier");
    }

    @Override
    public void drawInfo(RecipeDisplayInfo recipeInfo, @Nullable Object value) {
        int tier = cast(value, 1);
        switch (tier) {
            case 1 -> recipeInfo.drawText(trans("700", "Requires Alumina Milling Ball"));
            case 2 -> recipeInfo.drawText(trans("701", "Requires Soapstone Milling Ball"));
        }
    }
}
