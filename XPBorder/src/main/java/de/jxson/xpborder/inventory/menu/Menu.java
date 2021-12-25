package de.jxson.xpborder.inventory.menu;

import de.jxson.xpborder.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Author: Jason M.
 * de.jxson.xpborder.inventory
 * 25.12.2021 at 07:56
 */
public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;

    protected PlayerMenuUtility playerMenuUtility;
    private final ItemCreator FILLER_GLASS = new ItemCreator(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ");

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();
    public abstract int getSlots();
    public abstract void handleMenu(InventoryClickEvent event);
    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void fillGlass() {
        for(int i = 0; i < getSlots(); i++) {
            if(inventory.getItem(i) == null) {
                FILLER_GLASS.setSlot(inventory, i);
            }
        }
    }

}
