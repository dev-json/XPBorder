package de.jxson.xpborder.inventory.menu.menus.submenus;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.inventory.menu.Menu;
import de.jxson.xpborder.inventory.menu.PlayerMenuUtility;
import de.jxson.xpborder.inventory.menu.menus.SettingsMenu;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.settings.setting.ExpandsizeSetting;
import de.jxson.xpborder.settings.setting.PercentdeathSetting;
import de.jxson.xpborder.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.inventory.menu.menus
 * 25.12.2021 at 09:15
 */
public class DeathSettingMenu extends Menu {

    SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();
    protected PercentdeathSetting setting;
    int percent;

    public DeathSettingMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        percent = Integer.parseInt(settingsManager.getSetting("death").value());
        setting = (PercentdeathSetting) settingsManager.getSetting("death");
    }

    @Override
    public String getMenuName() {
        return "§9§lDeath Settings";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        XPBorder.getInstance().getWorldborderManager().reload();
        event.setCancelled(true);
        for (int i = 0; i < settingsManager.getSettings().size(); i++) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains(settingsManager.getSettings().get(i).name()) && (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS || event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS)) {
                settingsManager.getSettings().get(i).toggle(!settingsManager.getSettings().get(i).isToggled());
            }
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("add")) {
            if(event.getClick().isLeftClick()) {
                if(event.getClick().isShiftClick()) {
                    if(percent + 5 > 100)
                        return;
                    percent += 5;
                } else {
                    if(percent + 1 > 100)
                        return;
                    percent += 1;
                }
            } else if(event.getClick().isRightClick() ) {
                if(event.getClick().isShiftClick()) {
                    percent = 100;
                } else {
                    if(percent + 10 > 100)
                        return;
                    percent += 10;
                }
            } else {
                if(percent + 1 > 100)
                    return;
                percent += 1;
            }

            setting.setPercent(percent);

        } else if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("sub")) {
            if (event.getClick().isLeftClick()) {
                if (event.getClick().isShiftClick()) {
                    if(percent - 5 < 0)
                        return;
                    percent -= 5;
                } else {
                    if(percent - 1 < 0)
                        return;
                    percent -= 1;
                }
            } else if (event.getClick().isRightClick()) {
                if (event.getClick().isShiftClick()) {
                    percent = 1;
                } else {
                    if(percent - 10 < 0)
                        return;
                    percent -= 10;
                }
            } else {
                if(percent - 1 < 0)
                    return;
                percent -= 1;
            }

            setting.setPercent(percent);

        } else if(event.getCurrentItem().getType() == Material.BARRIER) {
            new SettingsMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
            return;
        }
        open();
    }

    @Override
    public void setMenuItems() {

        if(settingsManager.getSetting("death").isToggled()) {
            new ItemCreator(Material.GREEN_STAINED_GLASS, 1).setName("§a§lPercentdeath §8[§2Enabled§8]").setLore(" ", "§eSetting §7for the §epercent death §7activation", " ", "§7[Click] §cClick to disable", " ").setSlot(inventory, 11);
        } else {
            new ItemCreator(Material.RED_STAINED_GLASS, 1).setName("§c§lPercentdeath §8[§4Disabled§8]").setLore(" ", "§eSetting §7for the §epercent death §7activation", " ", "§7[Click] §aClick to enable", " ").setSlot(inventory, 11);
        }

        new ItemCreator(Material.STONE_BUTTON, 1).setName("§9§lSub").setLore(" ", "§7Set your percentage of level loss", " ", "§7Currently §e" + percent + " percent", " ", "§7[Left Click] §e-1 Percent", "§7[Left Shift Click] §e-5 Percent", "§7[Right Click] §e-10 Percent", "§7[Right Shift Click] §eMinimum Percent", "").setSlot(inventory, 13);
        new ItemCreator(Material.PAPER, percent).setName("§9§lPercent").setLore(" ", "§7Your will lose §e" + percent + " percent §7of your", "§ecurrent level §7when you die", " ").setSlot(inventory, 14);
        new ItemCreator(Material.STONE_BUTTON, 1).setName("§9§lAdd").setLore(" ", "§7Set your percentage of level loss", " ", "§7Currently §e" + percent + " percent", " ", "§7[Left Click] §e+1 Percent", "§7[Left Shift Click] §e+5 Percent", "§7[Right Click] §e+10 Percent", "§7[Right Shift Click] §eMaximum Percent", "").setSlot(inventory, 15);

        new ItemCreator(Material.BARRIER, 1).setName("§c§lGo back").setLore(" ", "§eClose §7this menu and return to the previous menu", " ", "§7[Click] §cClick to close", " ").setSlot(inventory, 26);
        fillGlass();
    }
}
