package de.jxson.xpborder.inventory.menu.menus.submenus;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.inventory.menu.Menu;
import de.jxson.xpborder.inventory.menu.PlayerMenuUtility;
import de.jxson.xpborder.inventory.menu.menus.SettingsMenu;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.settings.setting.BorderCalculationTypeSetting;
import de.jxson.xpborder.utils.ItemCreator;
import de.jxson.xpborder.world.worldborder.BorderSizeCalculationType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.inventory.menu.menus
 * 25.12.2021 at 09:16
 */
public class CalcTypeMenu extends Menu {

    SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();

    public CalcTypeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§9§lCalcsize Setting";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        XPBorder.getInstance().getWorldborderManager().reload();
        BorderCalculationTypeSetting setting = (BorderCalculationTypeSetting) settingsManager.getSetting("calctype");
        if(event.getCurrentItem().getType() == Material.BARRIER) {
            new SettingsMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
            return;
        } else if(event.getCurrentItem().getType() == Material.WRITTEN_BOOK) {
            setting.setType(BorderSizeCalculationType.CONFIG);
        } else if(event.getCurrentItem().getType() == Material.REDSTONE) {
            setting.setType(BorderSizeCalculationType.ADD);
        }
        open();
    }

    @Override
    public void setMenuItems() {
        if(settingsManager.getSetting("calctype").value().equals(BorderSizeCalculationType.CONFIG.name())) {
            new ItemCreator(Material.WRITTEN_BOOK, 1).setName("§a§lConfig §8[§2Selected§8]").setLore(" ", "§eSetting §7for the §eBorder calculation", " ", "§7In the config mode, the level and size", "§7will be saved in a config and", "§7given to any player who joins", " ", "§7[Click] §eClick to select", " ").setSlot(inventory, 12);
            new ItemCreator(Material.LIME_STAINED_GLASS_PANE, 1).setName(" ").setSlot(inventory, 3);
        } else {
            new ItemCreator(Material.WRITTEN_BOOK, 1).setName("§a§lConfig §8[§cNot Selected§8]").setLore(" ", "§eSetting §7for the §eBorder calculation", " ", "§7In the config mode, the level and size", "§7will be saved in a config and", "§7given to any player who joins", " ", "§7[Click] §eClick to select", " ").setSlot(inventory, 12);
            new ItemCreator(Material.RED_STAINED_GLASS_PANE, 1).setName(" ").setSlot(inventory, 3);
        }

        if(settingsManager.getSetting("calctype").value().equals(BorderSizeCalculationType.ADD.name())) {
            new ItemCreator(Material.LIME_STAINED_GLASS_PANE, 1).setName(" ").setSlot(inventory, 5);
            new ItemCreator(Material.REDSTONE, 1).setName("§a§lAddition §8[§2Selected§8]").setLore(" ", "§eSetting §7for the §eBorder calculation", " ", "§7In the addition mode, the size", "§7will be all added together", " ", "§7[Click] §eClick to select", " ").setSlot(inventory, 14);
        } else {
            new ItemCreator(Material.REDSTONE, 1).setName("§a§lAddition §8[§cNot Selected§8]").setLore(" ", "§eSetting §7for the §eBorder calculation", " ", "§7In the addition mode, the size", "§7will be all added together", " ", "§7[Click] §eClick to select", " ").setSlot(inventory, 14);
            new ItemCreator(Material.RED_STAINED_GLASS_PANE, 1).setName(" ").setSlot(inventory, 5);
        }

        new ItemCreator(Material.BARRIER, 1).hideFlags().setName("§c§lGo back").setLore(" ", "§eClose §7this menu and return to the previous menu", " ", "§7[Click] §cClick to close", " ").setSlot(inventory, 26);
        fillGlass();
    }

}
