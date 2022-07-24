package me.alphamode.portablecrafting.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alphamode.portablecrafting.network.OpenPacket;
import me.alphamode.portablecrafting.network.PortableNetwork;
import me.alphamode.portablecrafting.tables.AllTables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.PacketDistributor;

public class PortableWidget extends AbstractWidget {

    private boolean visible = false;
    private final Item item;
    private final AllTables type;

    public PortableWidget(Item item, AllTables type) {
        super(0,0, 15, 15, Component.empty());
        this.item = item;
        this.type = type;
    }

    public AllTables getTableType() {
        return type;
    }

    @Override
    public void updateNarration(NarrationElementOutput builder) {
        builder.add(NarratedElementType.USAGE, Component.empty());
    }

    @Override
    public void renderButton(PoseStack matrices, int mouseX, int mouseY, float delta) {
        if(isVisible())
            Minecraft.getInstance().getItemRenderer().renderGuiItem(item.getDefaultInstance(),  this.x, isHoveredOrFocused() ? this.y - 1 : this.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        PortableNetwork.CHANNEL.send(PacketDistributor.SERVER.noArg(), new OpenPacket(type));
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
