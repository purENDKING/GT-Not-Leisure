package com.science.gtnl.common.recipe.Special;

import static gregtech.api.util.GTUtility.trans;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.nei.RecipeDisplayInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ResourceCollectionModuleTierKey extends RecipeMetadataKey<Integer> {

    public static final ResourceCollectionModuleTierKey INSTANCE = new ResourceCollectionModuleTierKey();

    private ResourceCollectionModuleTierKey() {
        super(Integer.class, "resourcecollectionmoduletierkey_tier");
    }

    @Override
    public void drawInfo(RecipeDisplayInfo recipeInfo, @Nullable Object value) {
        int tier = cast(value, 1);
        switch (tier) {
            case 1 -> recipeInfo.drawText(trans("702", "Requires Mining Drone MK-VIII x 16"));
            case 2 -> recipeInfo.drawText(trans("703", "Requires Mining Drone MK-IX x 16"));
            case 3 -> recipeInfo.drawText(trans("704", "Requires Mining Drone MK-X x 16"));
            case 4 -> recipeInfo.drawText(trans("705", "Requires Mining Drone MK-XI x 16"));
        }
    }

}
