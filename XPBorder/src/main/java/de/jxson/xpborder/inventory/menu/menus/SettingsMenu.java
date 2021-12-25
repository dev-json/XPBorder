package de.jxson.xpborder.inventory.menu.menus;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.inventory.menu.Menu;
import de.jxson.xpborder.inventory.menu.PlayerMenuUtility;
import de.jxson.xpborder.inventory.menu.menus.submenus.CalcTypeMenu;
import de.jxson.xpborder.inventory.menu.menus.submenus.DeathSettingMenu;
import de.jxson.xpborder.inventory.menu.menus.submenus.ExpandsizeSettingMenu;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.utils.Data;
import de.jxson.xpborder.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.inventory.menu.menus
 * 25.12.2021 at 07:58
 */
public class SettingsMenu extends Menu {

    SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();

    public SettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Data.color("&9&lSettings");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        for (int i = 0; i < settingsManager.getSettings().size(); i++) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains(settingsManager.getSettings().get(i).name()) && (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS || event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS)) {
                if(settingsManager.getSettings().get(i).isToggled()) {
                    settingsManager.getSettings().get(i).toggle(false);
                } else {
                    settingsManager.getSettings().get(i).toggle(true);
                }
                open();
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("death")) {
                new DeathSettingMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("expandsize")) {
                new ExpandsizeSettingMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("calcsize")) {
                new CalcTypeMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
            } else if(event.getCurrentItem().getType() == Material.BARRIER) {
                player.closeInventory();
            }
        }
    }

    @Override
    public void setMenuItems() {
        if(settingsManager.getSetting("xpborder").isToggled()) {
            new ItemCreator(Material.GREEN_STAINED_GLASS, 1).setName("§a§lXPBorder §8[§2Enabled§8]").setLore(" ", "§eSetting §7for the §eBorder §7activation", " ", "§7[Click] §cClick to disable", " ").setSlot(inventory, 0);
        } else {
            new ItemCreator(Material.RED_STAINED_GLASS, 1).setName("§c§lXPBorder §8[§4Disabled§8]").setLore(" ", "§eSetting §7for the §eBorder §7activation", " ", "§7[Click] §aClick to enable", " ").setSlot(inventory, 0);
        }

        if(settingsManager.getSetting("security").isToggled()) {
            new ItemCreator(Material.GREEN_STAINED_GLASS, 1).setName("§a§lSecurity §8[§2Enabled§8]").setLore(" ", "§eSetting §7for the §eSecurity §7activation", " ", "§7[Click] §cClick to disable", " ").setSlot(inventory, 1);
        } else {
            new ItemCreator(Material.RED_STAINED_GLASS, 1).setName("§c§lSecurity §8[§4Disabled§8]").setLore(" ", "§eSetting §7for the §eSecurity §7activation", " ", "§7[Click] §aClick to enable", " ").setSlot(inventory, 1);
        }


        if(settingsManager.getSetting("bossbar").isToggled()) {
            new ItemCreator(Material.GREEN_STAINED_GLASS, 1).setName("§a§lBossbar §8[§2Enabled§8]").setLore(" ", "§eSetting §7for the §eBossbar §7activation", " ", "§7[Click] §cClick to disable", " ").setSlot(inventory, 2);
        } else {
            new ItemCreator(Material.RED_STAINED_GLASS, 1).setName("§c§lBossbar §8[§4Disabled§8]").setLore(" ", "§eSetting §7for the §eBossbar §7activation", " ", "§7[Click] §aClick to enable", " ").setSlot(inventory, 2);
        }

        if(settingsManager.getSetting("shrink").isToggled()) {
            new ItemCreator(Material.GREEN_STAINED_GLASS, 1).setName("§a§lShrink §8[§2Enabled§8]").setLore(" ", "§eSetting §7for the §eShrink §7activation", " ", "§7[Click] §cClick to disable", " ").setSlot(inventory, 3);
        } else {
            new ItemCreator(Material.RED_STAINED_GLASS, 1).setName("§c§lShrink §8[§4Disabled§8]").setLore(" ", "§eSetting §7for the §eShrink §7activation", " ", "§7[Click] §aClick to enable", " ").setSlot(inventory, 3);
        }

        new ItemCreator(Material.ORANGE_STAINED_GLASS, 1).setName("§a§lDeath §8[§2Setting§8]").setLore(" ", "§eSetting §7for the §eDeath §7setting", " ", "§7[Click] §cClick to configure", " ").setSlot(inventory, 6);
        new ItemCreator(Material.ORANGE_STAINED_GLASS, 1).setName("§a§lExpandsize §8[§2Setting§8]").setLore(" ", "§eSetting §7for the §eExpandsize §7setting", " ", "§7[Click] §cClick to configure", " ").setSlot(inventory, 7);
        new ItemCreator(Material.ORANGE_STAINED_GLASS, 1).setName("§a§lCalcsize §8[§2Setting§8]").setLore(" ", "§eSetting §7for the §eBorder Calculation §7setting", " ", "§7[Click] §cClick to configure", " ").setSlot(inventory, 8);

        new ItemCreator(Material.BARRIER, 1).setName("§c§lClose").setLore(" ", "§eClose §7this menu", " ", "§7[Click] §cClick to close", " ").setSlot(inventory, 35);

        fillGlass();
    }
}
