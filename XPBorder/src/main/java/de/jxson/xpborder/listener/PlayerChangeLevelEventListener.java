package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 21.12.2021 at 18:40
 */
public class PlayerChangeLevelEventListener implements Listener {

    private WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        worldborderManager.adjustSize(player);
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.setLevel(worldborderManager.getLevel());
            worldborderManager.createBossbar(player);
        });
    }

}
