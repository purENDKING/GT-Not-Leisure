package com.science.gtnl.common.block.Casings;

import gregtech.api.interfaces.IHasIndexedTexture;
import gregtech.api.util.GTUtility;

public interface ICasing extends IHasIndexedTexture {

    byte getTexturePageIndex();

    byte getTextureIndexInPage(int meta);

    default int getTextureId(byte page, byte index) {
        return GTUtility.getTextureId(page, index);
    }
}
