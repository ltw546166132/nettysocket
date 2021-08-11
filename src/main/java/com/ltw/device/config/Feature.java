package com.ltw.device.config;

public interface Feature {
    boolean enabledByDefault();

    int getMask();

    boolean enabledIn(int flags);

    static <F extends Enum<F> & Feature> int collectFeatureDefaults(Class<F> enumClass) {
        int flags = 0;
        Enum[] var2 = (Enum[])enumClass.getEnumConstants();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Enum value = var2[var4];
            if (((Feature)value).enabledByDefault()) {
                flags |= ((Feature)value).getMask();
            }
        }

        return flags;
    }
}
