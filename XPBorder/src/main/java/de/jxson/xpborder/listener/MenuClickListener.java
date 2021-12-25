package de.jxson.xpborder.listener;

import de.jxson.xpborder.inventory.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Author: Jason M.
 * de.jxson.xpborder.listener
 * 25.12.2021 at 08:00
 */
public class MenuClickListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if(holder instanceof Menu) {
            if(event.getCurrentItem() == null) {
                return;
            }
            Menu menu = (Menu) holder;
            menu.handleMenu(event);

        }
    }

}
