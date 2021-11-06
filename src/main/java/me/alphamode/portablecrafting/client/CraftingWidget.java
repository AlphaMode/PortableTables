package me.alphamode.portablecrafting.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class CraftingWidget extends ClickableWidget {

    public CraftingWidget() {
        super(0,0, 15, 15, new TranslatableText(""));
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, new TranslatableText(""));
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(new ItemStack(Items.CRAFTING_TABLE),  this.x, isHovered() ? this.y - 1 : this.y);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        ClientPlayNetworking.send(new Identifier("portable_tables", "open"), PacketByteBufs.empty());
    }
}
