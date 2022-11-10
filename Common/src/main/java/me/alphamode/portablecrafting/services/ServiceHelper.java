package me.alphamode.portablecrafting.services;

import java.util.ServiceLoader;

public class ServiceHelper {
    public static PortablePlatformHelper PLATFORM_HELPER = ServiceLoader.load(PortablePlatformHelper.class).findFirst().orElseThrow();
    public static ClientPlatformHelper CLIENT_PLATFORM_HELPER = ServiceLoader.load(ClientPlatformHelper.class).findFirst().orElseThrow();
}
