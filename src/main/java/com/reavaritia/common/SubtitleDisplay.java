package com.reavaritia.common;

public interface SubtitleDisplay {

    default void showSubtitle() {}

    default void showSubtitle(String message) {}

    default void showSubtitle(String message, long something) {}

    default void showSubtitle(String message, int something) {}
}
