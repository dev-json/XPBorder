package de.jxson.xpborder;

import de.jxson.xpborder.commands.XPBorderCommand;
import de.jxson.xpborder.interfaces.I_XPBorderManager;
import de.jxson.xpborder.inventory.MenuManager;
import de.jxson.xpborder.listener.*;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.utils.Data;
import de.jxson.xpborder.utils.updater.UpdateChecker;
import de.jxson.xpborder.utils.version.Version;
import de.jxson.xpborder.world.WorldManager;
import de.jxson.xpborder.world.worldborder.WorldborderManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Author: Jason M.
 * de.jxson.xpborder
 * 20.12.2021 at 19:23
 *
 * Testers: Jagenauder, Jan
 */
public class XPBorder extends JavaPlugin {

    private static XPBorder instance;
    private Data data;
    private UpdateChecker updateChecker;
    private SettingsManager settingsManager;
    private WorldManager worldManager;
    private I_XPBorderManager xpWorldborderManager;
    private WorldborderManager worldborderManager;
    private MenuManager menuManager;

    public void onLoad() {
        WorldManager.reset();
    }

    public void onEnable() {
        instance = this;

        if((this.xpWorldborderManager = Version.verifyVersion()) == null)
        {
            System.out.println("Version is not supported!");
            instance.setEnabled(false);
            return;
        }

        this.data = new Data();
        this.updateChecker = new UpdateChecker(97510);
        this.settingsManager = new SettingsManager();

        this.worldManager = new WorldManager();
        this.worldborderManager = new WorldborderManager();
        this.menuManager = new MenuManager();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeWorldEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeLevelEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerExpChangeEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnEventListener(), this);

        getCommand("xpborder").setExecutor(new XPBorderCommand());

        if(Boolean.parseBoolean(settingsManager.getSetting("worldreset").value()))
            worldborderManager.resetFile();
        this.worldManager.toggleReset(false);


        Bukkit.getOnlinePlayers().forEach(players -> {
            worldborderManager.sendBorder(players);
            worldborderManager.checkIfPlayerIsOutside(players);
        });

    }

    public void onDisable() {

    }

    public static XPBorder getInstance() {
        return instance;
    }

    public Data getData() {
        return data;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public I_XPBorderManager getXPBorderManager() {
        return xpWorldborderManager;
    }

    public WorldborderManager getWorldborderManager() {
        return worldborderManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
