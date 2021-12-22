package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 21.12.2021 at 18:41
 */
public class PlayerChangeWorldEventListener implements Listener {

    private WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        worldborderManager.sendBorder(player);
    }

}
