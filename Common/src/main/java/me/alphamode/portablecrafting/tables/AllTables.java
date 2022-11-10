package me.alphamode.portablecrafting.tables;

public enum AllTables {
    MODDED,
    CRAFTING,
    FURNACE(true),
    SMOKER(true),
    BLAST(true),
    ANVIL,
    SMITHING,
    LOOM,
    GRINDSTONE,
    CARTOGRAPHY,
    STONECUTTER;

    AllTables() {
        this(false);
    }

    private final boolean furnaceLike;

    AllTables(boolean furnaceLike) {
        this.furnaceLike = furnaceLike;
    }

    public boolean isFurnaceLike() {
        return furnaceLike;
    }
}
