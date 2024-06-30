package me.alphamode.portabletables.tables;

public enum AllTables {
    CRAFTING,
    FURNACE(true);

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
