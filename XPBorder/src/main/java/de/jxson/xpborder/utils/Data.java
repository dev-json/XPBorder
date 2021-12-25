package de.jxson.xpborder.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Author: Jason M.
 * de.jxson.xpborder.utils.version
 * 20.12.2021 at 19:42
 */
public class Data {

    //Initialize these default variables. [final] because they should not be edited manually
    //Use ChatColor.translateAlternateColorCodes to translate & to colors
    private final String prefix = "&bXP&aBorder &8| &r";
    private final String noPermissions = prefix + "&cYou don't have any permissions to perform that command.";
    private final String noPlayer = prefix + "&cYou need to be a player to execute this command.";

    public String getPrefix() {
        return prefix;
    }

    public String getNoPermissions() {
        return noPermissions;
    }

    public String getNoPlayer() {
        return noPlayer;
    }

    public void sendHelp(Player player) {
        player.sendMessage(color("&7&m---------&r &9XP&3Border &7&m---------"));
        player.sendMessage(color("&9/xpb &8- &eShow credits"));
        player.sendMessage(color("&9/xpb help &8- &eDisplay the help side"));
        player.sendMessage(color("&9/xpb refresh &8- &eReload the border for every player"));
        player.sendMessage(color("&9/xpb reload &8- &eReload the config file"));
        player.sendMessage(color("&9/xpb toggle &8- &eEnable/Disable the border"));
        player.sendMessage(color("&9/xpb center &8- &eSet the center of the border at your location"));
        player.sendMessage(color("&9/xpb reset &8- &eDeletes the current world and generates a new world"));
        player.sendMessage(color("&9/xpb settings &8- &eOpen the settings inventory"));
        player.sendMessage(color("&7&m---------------------------"));

    }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }


}
