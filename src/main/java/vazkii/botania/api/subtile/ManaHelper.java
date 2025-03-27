package vazkii.botania.api.subtile;

import net.minecraft.tileentity.TileEntity;

public class ManaHelper {

    public static int getMana(SubTileGenerating flower) {
        return flower.mana;
    }

    public static void setMana(SubTileGenerating flower, int mana) {
        flower.mana = mana;
    }

    public static TileEntity getSupertile(SubTileEntity flower) {
        return flower.supertile;
    }
}
