package com.ltw.device.config;

public interface Configured<Target> {
    void configured(Configurator<Target> configurator);
}
