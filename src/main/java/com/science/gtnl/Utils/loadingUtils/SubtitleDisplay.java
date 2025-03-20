package com.science.gtnl.Utils.loadingUtils;

public interface SubtitleDisplay {

    default void showSubtitle() {}

    default void showSubtitle(String message) {}

    default void showSubtitle(String message, long cooldown) {}
}
