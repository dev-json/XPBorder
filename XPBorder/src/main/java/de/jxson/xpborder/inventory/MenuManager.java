package de.jxson.xpborder.inventory;

import de.jxson.xpborder.inventory.menu.PlayerMenuUtility;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Author: Jason M.
 * de.jxson.ecwars.handlers.inventorymenu
 * 22.09.2021 at 20:32
 */
public class MenuManager {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<Player, PlayerMenuUtility>();

    public MenuManager() {

    }

    public PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;
        if(!playerMenuUtilityMap.containsKey(player)) {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);
            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(player);
        }
    }



}
