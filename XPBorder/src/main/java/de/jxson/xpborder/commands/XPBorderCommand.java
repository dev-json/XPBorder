package de.jxson.xpborder.commands;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.inventory.menu.menus.SettingsMenu;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.utils.Data;
import de.jxson.xpborder.world.WorldManager;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jason M.
 * de.jxson.xpborder.commands
 * 09.11.2021 at 16:23
 */
public class XPBorderCommand implements CommandExecutor, TabCompleter {

    private final Data data = XPBorder.getInstance().getData();
    private final WorldborderManager worldborderManager = XPBorder.getInstance().getWorldborderManager();
    private final SettingsManager settingsManager = XPBorder.getInstance().getSettingsManager();

    private ArrayList<Player> players = XPBorder.getInstance().getWorldManager().getPlayerNeedsConfirm();
    private long executedAt;
    private final long TIME_TO_CONFIRM_IN_MS = 10000; //10 sec

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player player) {
            if(args.length == 0) {
                player.sendMessage("");
                player.sendMessage(Data.color("        &a&m--&8&m]-&r &9XP&3Border &8&m-[&a&m--"));
                player.sendMessage(Data.color("    &eDeveloped by &b&nJason aka SirSpig0tOffical"));
                player.sendMessage("");
            } else if(args.length >= 1){
                if(player.hasPermission("xpb.admin")) {
                    switch (args[0]) {
                        case "refresh":
                            player.sendMessage(Data.color(data.getPrefix() + "&eWorldborder refreshed!"));
                            if(settingsManager.getSetting("xpborder").isToggled())
                                Bukkit.getOnlinePlayers().forEach(worldborderManager::sendBorder);
                            break;
                        case "reload":
                            player.sendMessage(Data.color(data.getPrefix() + "&eReloaded config file!"));
                            worldborderManager.reload();
                            break;
                        case "toggle":
                            if(settingsManager.getSetting("xpborder").isToggled()) {
                                player.sendMessage(Data.color(data.getPrefix() + "&eWorldborder disabled!"));
                                settingsManager.getSetting("xpborder").toggle(false);
                                Bukkit.getOnlinePlayers().forEach(worldborderManager::removeBorder);
                            } else {
                                player.sendMessage(Data.color(data.getPrefix() + "&eWorldborder enabled!"));
                                settingsManager.getSetting("xpborder").toggle(true);
                                Bukkit.getOnlinePlayers().forEach(worldborderManager::sendBorder);
                            }
                            worldborderManager.reload();
                            break;
                        case "center":
                            if(!settingsManager.getSetting("xpborder").isToggled()) {
                                worldborderManager.setCenter(player);
                                player.sendMessage(Data.color(data.getPrefix() + "&eCenter was set to your location!"));
                                worldborderManager.reload();
                            } else {
                                player.sendMessage(Data.color(data.getPrefix() + "&cPlease disable the border before setting a new center!"));
                                player.sendMessage(Data.color(data.getPrefix() + "&cUse &e/xpb toggle"));
                            }
                            break;
                        case "reset":
                            if(args.length == 1) {
                                 if(!players.contains(player)) {
                                     player.sendMessage(Data.color(data.getPrefix() + "&cAre you sure you want to reset your world?"));
                                     player.sendMessage(Data.color(data.getPrefix() + "&cIf you still want to continue execute &e/xpb reset confirm"));
                                     players.add(player);
                                    executedAt = System.currentTimeMillis();
                                } else {
                                     if(executedAt + TIME_TO_CONFIRM_IN_MS < System.currentTimeMillis()) {
                                         players.remove(player);
                                         player.performCommand("xpb reset");
                                        return false;
                                     }
                                    player.sendMessage(Data.color(data.getPrefix() + "§cYou need to run &e/xpb reset confirm &cbefore requesting a new reset!"));
                                }

                            } else if(args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                                if(players.contains(player)) {
                                    if(executedAt + TIME_TO_CONFIRM_IN_MS > System.currentTimeMillis()) {
                                        /*Bukkit.getOnlinePlayers().forEach(players -> players.kickPlayer(Data.color("&9World reset!")));
                                        worldManager.toggleReset(true);
                                        Bukkit.spigot().restart();*/
                                        player.sendMessage(Data.color(data.getPrefix() + "§eWorld will be resetted!"));
                                        players.remove(player);
                                    } else {
                                        players.remove(player);
                                        player.sendMessage(Data.color(data.getPrefix() + "§cYou need to confirm in within 10 seconds! Please try again!"));
                                    }
                                } else {
                                    data.sendHelp(player);
                                }
                            }

                            break;
                        case "settings":
                            new SettingsMenu(XPBorder.getInstance().getMenuManager().getPlayerMenuUtility(player)).open();
                            break;
                        default:
                            data.sendHelp(player);
                            break;
                    }
                } else {
                    player.sendMessage(Data.color(data.getNoPermissions()));
                }
            }
        } else {
            commandSender.sendMessage(Data.color(data.getNoPlayer()));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> arrayList = new ArrayList<>();
        if(strings.length == 1) {
            arrayList.add("refresh");
            arrayList.add("reload");
            arrayList.add("toggle");
            arrayList.add("center");
            arrayList.add("reset");
            arrayList.add("settings");

            return arrayList;
        }

        return null;
    }
}
