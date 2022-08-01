package de.jxson.xpborder.world;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.config.ConfigManager;
import de.jxson.xpborder.settings.SettingsManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Jason M.
 * de.jxson.xpborder.world.worldborder
 * 21.12.2021 at 16:10
 */
public class WorldManager {

    private final SettingsManager settingsManager;
    private ArrayList<Player> playerNeedsConfirm;

    public WorldManager() {
        settingsManager = XPBorder.getInstance().getSettingsManager();
        playerNeedsConfirm = new ArrayList<>();
    }

    public static void reset() {

        if(!checkIfReset())
            return;

        deleteWorld("world");
        deleteWorld("world_nether");
        deleteWorld("world_the_end");
    }

    public void toggleReset(boolean value) {
        new ConfigManager("plugins/XPBorder/", "settings.yml").set("setting.worldreset.enabled", value);
    }

    private static void deleteWorld(String name) {
        File worldDir = new File(Bukkit.getWorldContainer(), name);
        try {
            FileUtils.deleteDirectory(worldDir);
            worldDir.mkdirs();

            new File(worldDir, "data").mkdirs();
            new File(worldDir, "datapacks").mkdirs();
            new File(worldDir, "playerdata").mkdirs();
            new File(worldDir, "poi").mkdirs();
            new File(worldDir, "region").mkdirs();
        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }

    private static boolean checkIfReset() {
        return new ConfigManager("plugins/XPBorder/", "settings.yml").getBool("setting.worldreset.enabled");
    }

    public ArrayList<Player> getPlayerNeedsConfirm() {
        return playerNeedsConfirm;
    }
}
