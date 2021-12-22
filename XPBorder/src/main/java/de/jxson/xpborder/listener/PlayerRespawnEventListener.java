package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 21.12.2021 at 18:42
 */
public class PlayerRespawnEventListener implements Listener {

    private WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        worldborderManager.sendBorder(player);
        event.setRespawnLocation(worldborderManager.getBorderCenter(player.getWorld().getName()));
        player.setLevel(worldborderManager.getLevel());
        Bukkit.getOnlinePlayers().forEach(players -> players.setLevel(worldborderManager.getLevel()));
    }

}
