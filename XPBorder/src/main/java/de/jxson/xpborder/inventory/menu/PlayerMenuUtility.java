package de.jxson.xpborder.inventory.menu;

import org.bukkit.entity.Player;

/**
 * Author: Jason M.
 * de.jxson.ecwars.handlers.inventorymenu
 * 22.09.2021 at 20:33
 */
public class PlayerMenuUtility {

    private Player owner;

    public PlayerMenuUtility(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
