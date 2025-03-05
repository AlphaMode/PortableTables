package me.alphamode.portabletables.client;

import me.alphamode.portabletables.tables.AllTables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.ItemInstance;

public class PortableButton extends Button {
    private final ItemRenderer itemRenderer = new ItemRenderer();
    private final ItemInstance item;
    private final AllTables type;

    public PortableButton(int id, ItemInstance item, AllTables type) {
        super(id, 0, 0, 15, 15, "");
        this.item = item;
        this.type = type;
    }

    @Override
    public void render(Minecraft client, int mouseX, int mouseY) {
        if (this.visible)
            itemRenderer.renderAndDecorateItem(client.font, client.textures, item, this.x, isMouseOver(client, mouseX, mouseY) ? this.y - 1 : this.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ItemInstance getItem() {
        return item;
    }

    public AllTables getType() {
        return type;
    }
}
