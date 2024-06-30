package me.alphamode.portabletables;

import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class PortableTable<C> extends TemplateItem {
    protected final BiConsumer<Player, C> open;
    private final AllTables type;

    public PortableTable(Identifier id, BiConsumer<Player, C> player, AllTables type) {
        super(id);
        stacksTo(1);
        this.open = player;
        this.type = type;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        open.accept(player, getContext(level, player, item));
        return item;
    }

    @Nullable
    protected C getContext(Level world, Player player, ItemInstance item) {
        return null;
    }

    public AllTables getType() {
        return type;
    }
}
