package me.alphamode.portabletables.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class PortableConfig {
    @ConfigEntry(name = "Crafting Widget Always Enabled", description = "When enabled the portable crafting widget in your inventory will always be active.")
    public Boolean craftingWidgetAlwaysEnabled = false;
}
