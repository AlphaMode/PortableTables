package me.alphamode.portabletables.ext;

import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.world.ItemInstance;

public interface PlayerExt {
    default void openPortableTable(AllTables type, Object data) {
        throw new RuntimeException("Player implementation does not support portable tables!");
    }
}
