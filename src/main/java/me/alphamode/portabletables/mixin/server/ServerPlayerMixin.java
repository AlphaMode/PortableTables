package me.alphamode.portabletables.mixin.server;

import me.alphamode.portabletables.ext.FurnaceTileEntityExt;
import me.alphamode.portabletables.ext.PlayerExt;
import me.alphamode.portabletables.network.packets.OpenTablePacket;
import me.alphamode.portabletables.tables.AllTables;
import me.alphamode.portabletables.tables.menus.PortableCraftingMenu;
import me.alphamode.portabletables.tables.menus.PortableFurnaceMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.entity.FurnaceTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ContainerListener, PlayerExt {
    public ServerPlayerMixin(Level level) {
        super(level);
    }

    @Shadow public ServerGamePacketListener connection;

    @Shadow private int containerCounter;

    @Shadow protected abstract void nextContainerCounter();

    @Override
    public void openPortableTable(AllTables type, Object data) {
        this.nextContainerCounter();
        this.connection.send(new OpenTablePacket(this.containerCounter, type, "Crafting", 9));
        this.containerMenu = switch (type) {
            case CRAFTING -> new PortableCraftingMenu(this.inventory, this.level);
            case FURNACE -> {
                FurnaceTileEntity furnace;
                if (data != null)
                    furnace = (FurnaceTileEntity) data;
                else
                    furnace = new FurnaceTileEntity();
                ((FurnaceTileEntityExt)furnace).pt$setPortable(true);
                yield new PortableFurnaceMenu(this.inventory, furnace);
            }
        };
        this.containerMenu.containerId = this.containerCounter;
        this.containerMenu.addSlotListener(this);
    }
}
