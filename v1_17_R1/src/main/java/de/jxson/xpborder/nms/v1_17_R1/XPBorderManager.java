package de.jxson.xpborder.nms.v1_17_R1;

import de.jxson.xpborder.interfaces.I_XPBorderManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Author: Jason M.
 * de.jxson.xpborder.nms
 * 20.12.2021 at 19:51
 */
public class XPBorderManager implements I_XPBorderManager {

    private WorldBorder worldBorder;

    public XPBorderManager() {
        worldBorder = new WorldBorder();
    }

    public void setCenter(double x, double z) {
        worldBorder.setCenter(x, z);
    }

    public void setSize(double d) {
        worldBorder.setSize(d);
    }

    public void setSizeTransition(double from, double to, long time) {
        worldBorder.transitionSizeBetween(from, to, time);
    }

    public void setWorld(World world) {
        worldBorder.world = ((CraftWorld) world).getHandle();
    }

    public void sendBorderToPlayer(Player player) {
        sendPacket(player, new ClientboundInitializeBorderPacket(worldBorder));
    }

    public void updateBorderSize(Player player) {
        sendPacket(player, new ClientboundSetBorderSizePacket(worldBorder));
    }

    public void updateBorderSizeTransition(Player player) {
        sendPacket(player, new ClientboundSetBorderLerpSizePacket(worldBorder));
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().b.sendPacket(packet);
    }
}
