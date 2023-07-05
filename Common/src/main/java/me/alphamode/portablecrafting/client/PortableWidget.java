package me.alphamode.portablecrafting.client;

import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.services.ServiceHelper;
import me.alphamode.portablecrafting.tables.AllTables;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class PortableWidget extends ClickableWidget {

    private boolean visible = false;
    private Item item;
    private AllTables type;

    public PortableWidget(Item item, AllTables type) {
        super(0,0, 15, 15, Text.empty());
        this.item = item;
        this.type = type;
    }

    public AllTables getTableType() {
        return type;
    }

    @Override
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, Text.empty());
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if(isVisible())
            context.drawItem(item.getDefaultStack(), this.getX(), isHovered() ? this.getY() - 1 : this.getY());
    }

    public void setType(AllTables type) {
        this.type = type;
        if (type == AllTables.FURNACE)
            this.item = Items.FURNACE;
        else if (type == AllTables.BLAST)
            this.item = Items.BLAST_FURNACE;
        else if (type == AllTables.SMOKER)
            this.item = Items.SMOKER;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        ServiceHelper.CLIENT_PLATFORM_HELPER.sendOpenPacket(new OpenPacket(type));
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
