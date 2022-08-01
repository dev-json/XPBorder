package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 25.12.2021 at 07:00
 */
public class PlayerQuitEventListener implements Listener {

    WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!XPBorder.getInstance().getSettingsManager().getSetting("xpborder").isToggled()) {
            return;
        }
        worldborderManager.setSizeInBlocks(Bukkit.getOnlinePlayers().stream().mapToInt(Player::getLevel).sum());
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player ->  {
                    worldborderManager.adjustSize(player);
                });
            }
        }.runTaskLater(XPBorder.getInstance(), 1);
    }

}
