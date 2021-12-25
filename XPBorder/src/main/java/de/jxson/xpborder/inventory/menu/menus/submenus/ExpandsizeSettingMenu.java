package de.jxson.xpborder.inventory.menu.menus.submenus;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.inventory.menu.Menu;
import de.jxson.xpborder.inventory.menu.PlayerMenuUtility;
import de.jxson.xpborder.inventory.menu.menus.SettingsMenu;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.settings.setting.ExpandsizeSetting;
import de.jxson.xpborder.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Author: Jason M.
 * de.jxson.xpborder.inventory.menu.menus
 * 25.12.2021 at 09:16
 */
public class ExpandsizeSettingMenu extends Menu {

    SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();
    protected int expandSizeOnItem;
    protected ExpandsizeSetting setting;

    public ExpandsizeSettingMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        expandSizeOnItem = Integer.parseInt(settingsManager.getSetting("expandsize").value());
        XPBorder.getInstance().getWorldborderManager().reload();
        setting = (ExpandsizeSetting) settingsManager.getSetting("expandsize");
    }

    @Override
    public String getMenuName() {
        return "§9§lExpandsize Settings";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        for (int i = 0; i < settingsManager.getSettings().size(); i++) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains(settingsManager.getSettings().get(i).name()) && (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS || event.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS)) {
                settingsManager.getSettings().get(i).toggle(!settingsManager.getSettings().get(i).isToggled());
            }
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("add")) {
            if(event.getClick().isLeftClick()) {
                if(event.getClick().isShiftClick()) {
                    if(expandSizeOnItem + 5 > 64)
                        return;
                    expandSizeOnItem += 5;
                } else {
                    if(expandSizeOnItem + 1 > 64)
                        return;
                    expandSizeOnItem += 1;
                }
            } else if(event.getClick().isRightClick() ) {
                if(event.getClick().isShiftClick()) {
                    expandSizeOnItem = 64;
                } else {
                    if(expandSizeOnItem + 10 > 64)
                        return;
                    expandSizeOnItem += 10;
                }
            } else {
                if(expandSizeOnItem + 1 > 64)
                    return;
                expandSizeOnItem += 1;
            }

            setting.setSize(expandSizeOnItem);

        } else if(event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().replace("§", "").contains("sub")) {
                if (event.getClick().isLeftClick()) {
                    if (event.getClick().isShiftClick()) {
                        if(expandSizeOnItem - 5 < 1)
                            return;
                        expandSizeOnItem -= 5;
                    } else {
                        if(expandSizeOnItem - 1 < 1)
                            return;
                        expandSizeOnItem -= 1;
                    }
                } else if (event.getClick().isRightClick()) {
                    if (event.getClick().isShiftClick()) {
                        expandSizeOnItem = 1;
                    } else {
                        if(expandSizeOnItem - 10 < 1)
                            return;
                        expandSizeOnItem -= 10;
                    }
                } else {
                    if(expandSizeOnItem - 1 < 1)
                        return;
                    expandSizeOnItem -= 1;
            }

            setting.setSize(expandSizeOnItem);

        } else if(event.getCurrentItem().getType() == Material.BARRIER) {
            new SettingsMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
            return;
        }
        open();
    }

    @Override
    public void setMenuItems() {

        if(settingsManager.getSetting("expandsize").isToggled()) {
            new ItemCreator(Material.GREEN_STAINED_GLASS, 1).setName("§a§lExpandsize §8[§2Enabled§8]").setLore(" ", "§eSetting §7for the §eCustom expand size §7activation", " ", "§7[Click] §cClick to disable", " ").setSlot(inventory, 11);
        } else {
            new ItemCreator(Material.RED_STAINED_GLASS, 1).setName("§c§lExpandsize §8[§4Disabled§8]").setLore(" ", "§eSetting §7for the §eCustom expand size §7activation", " ", "§7[Click] §aClick to enable", " ").setSlot(inventory, 11);
        }

        new ItemCreator(Material.STONE_BUTTON, 1).setName("§9§lSub").setLore(" ", "§7Set your custom expand size", " ", "§7Currently §e" + expandSizeOnItem + " Blocks/Level", " ", "§7[Left Click] §e-1 Size", "§7[Left Shift Click] §e-5 Size", "§7[Right Click] §e-10 Size", "§7[Right Shift Click] §eMinimum size", "").setSlot(inventory, 13);
        new ItemCreator(Material.PAPER, expandSizeOnItem).setName("§9§lExpandsize").setLore(" ", "§7Your Border will increase §e" + expandSizeOnItem + " Blocks§7/§elevel", " ").setSlot(inventory, 14);
        new ItemCreator(Material.STONE_BUTTON, 1).setName("§9§lAdd").setLore(" ", "§7Set your custom expand size", " ", "§7Currently §e" + expandSizeOnItem + " Blocks/Level", " ", "§7[Left Click] §e+1 Size", "§7[Left Shift Click] §e+5 Size", "§7[Right Click] §e+10 Size", "§7[Right Shift Click] §eMaximum size", "").setSlot(inventory, 15);

        new ItemCreator(Material.BARRIER, 1).setName("§c§lGo back").setLore(" ", "§eClose §7this menu and return to the previous menu", " ", "§7[Click] §cClick to close", " ").setSlot(inventory, 26);
        fillGlass();
    }

}
