package me.alphamode.portablecrafting.tables.furnace.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.alphamode.portablecrafting.tables.furnace.PortableFurnaceScreenHandler;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PortableFurnaceScreen extends AbstractContainerScreen<PortableFurnaceScreenHandler> implements RecipeUpdateListener {
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
    public final AbstractFurnaceRecipeBookComponent recipeBook;
    private boolean narrow;

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");

    public PortableFurnaceScreen(PortableFurnaceScreenHandler handler, Inventory inventory, Component title) {
        this(handler, new SmeltingRecipeBookComponent(), inventory, title);
    }

    public PortableFurnaceScreen(PortableFurnaceScreenHandler handler, AbstractFurnaceRecipeBookComponent recipeBook, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.recipeBook = recipeBook;
    }

    @Override
    public void init() {
        super.init();
        this.narrow = this.imageWidth < 379;
        this.recipeBook.init(this.imageWidth, this.imageHeight, this.minecraft, this.narrow, this.menu);
        this.leftPos = this.recipeBook.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, button -> {
            this.recipeBook.toggleVisibility();
            this.topPos = this.recipeBook.updateScreenPosition(this.width, this.imageWidth);
            ((ImageButton)button).setPosition(this.leftPos + 20, this.height / 2 - 49);
        }));
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.recipeBook.tick();
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        if (this.recipeBook.isVisible() && this.narrow) {
            this.renderBg(matrices, delta, mouseX, mouseY);
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
            this.recipeBook.renderGhostRecipe(matrices, this.leftPos, this.topPos, true, delta);
        }

        this.renderTooltip(matrices, mouseX, mouseY);
        this.recipeBook.renderTooltip(matrices, this.leftPos, this.topPos, mouseX, mouseY);
    }

    public int getFuelProgress() {
        SimpleContainerData delegate = PortableFurnaceHandler.getSyncedPropertyDelegate(this);
        int i = delegate.get(1);
        if (i == 0) {
            i = 200;
        }

        return delegate.get(0) * 13 / i;
    }

    public int getCookProgress() {
        SimpleContainerData delegate = PortableFurnaceHandler.getSyncedPropertyDelegate(this);
        int i = delegate.get(2);
        int j = delegate.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public boolean isBurning() {
        return PortableFurnaceHandler.getSyncedPropertyDelegate(this).get(0) > 0;
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (isBurning()) {
            int k = getFuelProgress();
            this.blit(matrices, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int k = getCookProgress();
        this.blit(matrices, i + 79, j + 34, 176, 14, k + 1, 16);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.narrow && this.recipeBook.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int button, ClickType actionType) {
        super.slotClicked(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.recipeBook.keyPressed(keyCode, scanCode, modifiers) ? false : super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left
                || mouseY < (double)top
                || mouseX >= (double)(left + this.width)
                || mouseY >= (double)(top + this.height);
        return this.recipeBook.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, button) && bl;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return this.recipeBook.charTyped(chr, modifiers) ? true : super.charTyped(chr, modifiers);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBook.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBook;
    }

    @Override
    public void removed() {
        this.recipeBook.removed();
        super.removed();
    }
}
