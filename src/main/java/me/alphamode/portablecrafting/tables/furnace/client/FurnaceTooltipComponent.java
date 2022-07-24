package me.alphamode.portablecrafting.tables.furnace.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;

public class FurnaceTooltipComponent implements ClientTooltipComponent {

    public static ResourceLocation TEXTURE = PortableTables.asResource("textures/tooltip/furnace_tooltip.png");

    protected final PortableFurnaceTooltipData data;

    public FurnaceTooltipComponent(PortableFurnaceTooltipData data) {
        this.data = data;
    }

    @Override
    public int getHeight() {
        return 70;
    }

    @Override
    public int getWidth(Font textRenderer) {
        return 96;
    }

    @Override
    public void renderImage(Font textRenderer, int x, int y, PoseStack matrices, ItemRenderer itemRenderer, int z) {
        // GUI
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        GuiComponent.blit(matrices, x, y, 0, 0, 96, 69, 121, 69);

        if (this.data.isBurning()) {
            int fuelProgress = this.data.getFuelProgress();
            GuiComponent.blit(matrices, x + 9, y + 28 + 12 - fuelProgress, 97, 12 - fuelProgress, 14, fuelProgress + 1, 121, 69);
        }

        int cookProgress = this.data.getCookProgress();
        GuiComponent.blit(matrices, x + 31, y + 27, 97, 14, cookProgress + 1, 16, 121, 69);

        // Items
        // Input
        itemRenderer.renderGuiItem(data.getInventory().get(0), x + 8, y + 9);
        itemRenderer.renderGuiItemDecorations(textRenderer, data.getInventory().get(0), x + 8, y + 9);

        // Fuel
        itemRenderer.renderGuiItem(data.getInventory().get(1), x + 8, y + 45);
        itemRenderer.renderGuiItemDecorations(textRenderer, data.getInventory().get(1), x + 8, y + 45);

        // Output
        itemRenderer.renderGuiItem(data.getInventory().get(2), x + 68, y + 26);
        itemRenderer.renderGuiItemDecorations(textRenderer, data.getInventory().get(2), x + 68, y + 26);
    }
}
