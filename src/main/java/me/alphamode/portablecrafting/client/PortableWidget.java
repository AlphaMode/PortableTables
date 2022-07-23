package me.alphamode.portablecrafting.client;

import me.alphamode.portablecrafting.PortableTables;
import me.alphamode.portablecrafting.tables.AllTables;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class PortableWidget extends ClickableWidget {

    private boolean visible = false;
    private final Item item;
    private final AllTables type;

    public PortableWidget(Item item, AllTables type) {
        super(0,0, 15, 15, new TranslatableText(""));
        this.item = item;
        this.type = type;
    }

    public AllTables getTableType() {
        return type;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, new TranslatableText(""));
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if(isVisible())
            MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(item.getDefaultStack(),  this.x, isHovered() ? this.y - 1 : this.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        ClientPlayNetworking.send(PortableTables.asResource("open"), PacketByteBufs.create().writeEnumConstant(type));
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        if(isVisible())
            return super.clicked(mouseX, mouseY);
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
