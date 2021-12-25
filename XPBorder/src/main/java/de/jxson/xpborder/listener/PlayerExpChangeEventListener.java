package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.BorderSizeCalculationType;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 21.12.2021 at 23:45
 */
public class PlayerExpChangeEventListener implements Listener {

    private WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        if (XPBorder.getInstance().getSettingsManager().getSetting("calctype").value().equals(BorderSizeCalculationType.CONFIG.name())) {
            Bukkit.getScheduler().runTaskLater(XPBorder.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.setExp(event.getPlayer().getExp());
                        worldborderManager.setExpbar(event.getPlayer().getExp());
                    });
                }
            }, 1);
        }
    }
}
