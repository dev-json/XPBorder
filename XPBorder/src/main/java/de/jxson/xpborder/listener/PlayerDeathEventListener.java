package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.BorderSizeCalculationType;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 21.12.2021 at 18:41
 */
public class PlayerDeathEventListener implements Listener {

    private WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if(!XPBorder.getInstance().getSettingsManager().getSetting("xpborder").isToggled()) {
            return;
        }

        event.setDroppedExp(0);
        event.setKeepLevel(true);

        if(XPBorder.getInstance().getSettingsManager().getSetting("calctype").value().equals(BorderSizeCalculationType.CONFIG.name())) {
            worldborderManager.setLevel(worldborderManager.calcRespawnLevel());
            event.setNewLevel(worldborderManager.getLevel());
        } else {
            event.setNewLevel(worldborderManager.calcRespawnLevelFromPlayer(event.getEntity()));
        }

    }


}
