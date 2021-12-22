package de.jxson.xpborder.tasks;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Author: Jason M.
 * de.jxson.xpborder.tasks
 * 21.12.2021 at 21:44
 */
public class IsPlayerInBorderTask extends BukkitRunnable {

    private final WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();
    private final SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();

    private Player player;
    private int x;

    public IsPlayerInBorderTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if(player.isOnline()) {
            x = worldborderManager.getLevel();
            if(settingsManager.getSetting("xpborder").isToggled()) {
                if(player.getLocation().getBlockX() <= worldborderManager.getBorderCenter(player.getWorld().getName()).add(x,0,x).getBlockX() && player.getLocation().getBlockX() >= worldborderManager.getBorderCenter(player.getWorld().getName()).add(-x,0,-x).getBlockX() && player.getLocation().getBlockZ() >= worldborderManager.getBorderCenter(player.getWorld().getName()).add(-x, 0, -x).getBlockZ() && player.getLocation().getBlockZ() <= worldborderManager.getBorderCenter(player.getWorld().getName()).add(x, 0, x).getBlockZ()) {
                } else {
                    player.teleport(worldborderManager.getBorderCenter(player.getWorld().getName()));
                }
            }
        } else {
            this.cancel();
        }
    }

}
