package me.alphamode.portabletables.network.packets;

import me.alphamode.portabletables.tables.AllTables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketListener;
import net.minecraft.network.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Vanilla copy of {@link net.minecraft.network.packets.OpenScreenPacket}
 */
public class OpenTablePacket extends Packet {
    public static final int ID = 253; // IdentifiablePacket is broken ):

    public int containerId;
    public AllTables type;
    public String title;
    public int size;

    public OpenTablePacket() {
    }

    @Environment(EnvType.SERVER)
    public OpenTablePacket(int containerId, AllTables id, String name, int size) {
        this.containerId = containerId;
        this.type = id;
        this.title = name;
        this.size = size;
    }

    @Override
    public void read(DataInputStream data) {
        try {
            this.containerId = data.readByte();
            this.type = AllTables.values()[data.readByte()];
            this.title = data.readUTF();
            this.size = data.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream data) {
        try {
            data.writeByte(this.containerId);
            data.writeByte(this.type.ordinal());
            data.writeUTF(this.title);
            data.writeByte(this.size);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(PacketListener handler) {

    }

    @Override
    public int getSize() {
        return 3 + this.title.length();
    }
}
