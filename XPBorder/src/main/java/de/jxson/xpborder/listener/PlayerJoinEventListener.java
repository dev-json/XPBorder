package de.jxson.xpborder.listener;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 20.12.2021 at 20:54
 */
public class PlayerJoinEventListener implements Listener {

    WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setLevel(worldborderManager.getLevel());
        worldborderManager.sendBorder(player);

        if(!player.hasPlayedBefore())
            player.teleport(worldborderManager.getBorderCenter(player.getWorld().getName()));

        worldborderManager.checkIfPlayerIsOutside(player);

        if(player.hasPermission("xpb.admin"))
            XPBorder.getInstance().getUpdateChecker().checkForUpdates(player);
    }

}
