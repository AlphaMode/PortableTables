package me.alphamode.portablecrafting.tables.furnace.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.alphamode.portablecrafting.PortableTables;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FurnaceTooltipComponent implements TooltipComponent {

    public static Identifier TEXTURE = PortableTables.asResource("textures/tooltip/furnace_tooltip.png");

    protected final PortableFurnaceTooltipData data;

    public FurnaceTooltipComponent(PortableFurnaceTooltipData data) {
        this.data = data;
    }

    @Override
    public int getHeight() {
        return 70;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 96;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z) {
        // GUI
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, 96, 69, 121, 69);

        if (this.data.isBurning()) {
            int fuelProgress = this.data.getFuelProgress();
            DrawableHelper.drawTexture(matrices, x + 9, y + 28 + 12 - fuelProgress, 97, 12 - fuelProgress, 14, fuelProgress + 1, 121, 69);
        }

        int cookProgress = this.data.getCookProgress();
        DrawableHelper.drawTexture(matrices, x + 31, y + 27, 97, 14, cookProgress + 1, 16, 121, 69);

        // Items
        // Input
        itemRenderer.renderInGuiWithOverrides(data.getInventory().get(0), x + 8, y + 9);
        itemRenderer.renderGuiItemOverlay(textRenderer, data.getInventory().get(0), x + 8, y + 9);

        // Fuel
        itemRenderer.renderInGuiWithOverrides(data.getInventory().get(1), x + 8, y + 45);
        itemRenderer.renderGuiItemOverlay(textRenderer, data.getInventory().get(1), x + 8, y + 45);

        // Output
        itemRenderer.renderInGuiWithOverrides(data.getInventory().get(2), x + 68, y + 26);
        itemRenderer.renderGuiItemOverlay(textRenderer, data.getInventory().get(2), x + 68, y + 26);
    }
}
