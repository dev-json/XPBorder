package de.jxson.xpborder.interfaces;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Author: Jason M.
 * PACKAGE_NAME
 * 20.12.2021 at 19:33
 */
public interface I_XPBorderManager {

    void setCenter(double x, double z);
    void setSize(double d);
    void setSizeTransition(double from, double to, long time);
    void setWorld(World world);

    void sendBorderToPlayer(Player player);
    void updateBorderSize(Player player);
    void updateBorderSizeTransition(Player player);

}
