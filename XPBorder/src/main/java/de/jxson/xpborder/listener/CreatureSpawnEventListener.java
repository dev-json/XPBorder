package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 25.01.2022 at 18:09
 */
public class CreatureSpawnEventListener implements Listener {

    private final WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();
    private final SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();

    private int x = 0;

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {

        if(!XPBorder.getInstance().getSettingsManager().getSetting("xpborder").isToggled()) {
            return;
        }

        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            x = worldborderManager.getSizeInBlocks();
            if(settingsManager.getSetting("MobsSpawnOutside").isToggled()) {
                if(event.getLocation().getBlockX() <= worldborderManager.getBorderCenter(event.getLocation().getWorld().getName()).add(x,0,x).getBlockX() && event.getLocation().getBlockX() >= worldborderManager.getBorderCenter(event.getLocation().getWorld().getName()).add(-x,0,-x).getBlockX() && event.getLocation().getBlockZ() >= worldborderManager.getBorderCenter(event.getLocation().getWorld().getName()).add(-x, 0, -x).getBlockZ() && event.getLocation().getBlockZ() <= worldborderManager.getBorderCenter(event.getLocation().getWorld().getName()).add(x, 0, x).getBlockZ()) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

}
