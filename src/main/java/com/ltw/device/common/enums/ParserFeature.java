package com.ltw.device.common.enums;

import com.ltw.device.config.Feature;

public enum ParserFeature implements Feature {
    HEADER_CONSTANT(true),
    FOOTER_CONSTANT(false);

    private final boolean defaultState;
    private final int mask;

    private ParserFeature(boolean defaultState) {
        this.defaultState = defaultState;
        this.mask = 1 << this.ordinal();
    }

    @Override
    public boolean enabledByDefault() {
        return this.defaultState;
    }

    @Override
    public int getMask() {
        return this.mask;
    }

    @Override
    public boolean enabledIn(int flags) {
        return (flags & this.mask) != 0;
    }
}
