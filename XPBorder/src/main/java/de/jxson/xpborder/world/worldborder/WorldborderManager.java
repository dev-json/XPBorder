package de.jxson.xpborder.world.worldborder;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.config.ConfigManager;
import de.jxson.xpborder.interfaces.I_XPBorderManager;
import de.jxson.xpborder.settings.SettingsManager;
import de.jxson.xpborder.tasks.IsPlayerInBorderTask;
import de.jxson.xpborder.utils.Data;
import de.jxson.xpborder.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Jason M.
 * de.jxson.xpborder.world.worldborder
 * 20.12.2021 at 19:35
 */
public class WorldborderManager {

    private I_XPBorderManager i_xpBorderManager;
    private ConfigManager worldborderConfig;
    private SettingsManager settingsManager;

    private int increaseSize;
    private int currentSize;
    private int level;

    private int sizeInBlocks;

    float expbar;

    public WorldborderManager() {
        worldborderConfig = new ConfigManager("worldborder.yml");
        i_xpBorderManager = XPBorder.getInstance().getXPBorderManager();
        settingsManager = XPBorder.getInstance().getSettingsManager();
    }

    public void initializeBorder(Player player) {
        createBorder(player);
    }

    private void createBorder(Player player) {
        if(worldborderConfig.getString("worldborder."+player.getWorld().getName()+".center.x") == null)
            worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.x", player.getLocation().getBlockX() + 0.5);
        if(worldborderConfig.getString("worldborder."+player.getWorld().getName()+".center.y") == null)
            worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.y", player.getLocation().getBlockY());
        if(worldborderConfig.getString("worldborder."+player.getWorld().getName()+".center.z") == null)
            worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.z", player.getLocation().getBlockZ() + 0.5);
        if(worldborderConfig.getString("worldborder."+player.getWorld().getName()+".center.world") == null)
            worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.world", player.getLocation().getWorld().getName());
        if(worldborderConfig.getString("worldborder.level") == null)
            worldborderConfig.set("worldborder.level", 0);
        if(worldborderConfig.getString("worldborder.size") == null) {
            if(worldborderConfig.getString("worldborder.level") == null)
                worldborderConfig.set("worldborder.level", 0);
            worldborderConfig.set("worldborder.size", worldborderConfig.getInt("worldborder.level"));
        }
        if(worldborderConfig.getString("worldborder.xpbar") == null)
            worldborderConfig.set("worldborder.xpbar", 0.0);
    }

    public void sendBorder(Player player) {
        initializeBorder(player);

        if(!settingsManager.getSetting("xpborder").isToggled())
            return;


        double cX = worldborderConfig.getDouble("worldborder."+player.getWorld().getName()+".center.x");
        double cZ = worldborderConfig.getDouble("worldborder."+player.getWorld().getName()+".center.z");
        this.currentSize = worldborderConfig.getInt("worldborder.size");
        this.increaseSize = Integer.parseInt(settingsManager.getSetting("expandsize").value());

        i_xpBorderManager.setWorld(player.getWorld());
        i_xpBorderManager.setCenter(cX, cZ);

        if(settingsManager.getSetting("calctype").value().equals(BorderSizeCalculationType.ADD.name())) {
            setSizeInBlocks(Bukkit.getOnlinePlayers().stream().mapToInt(Player::getLevel).sum());
        }

        for(int i = 0; i < currentSize; i++) {
            if(MathUtils.isEven(i)) {
                currentSize++;
            }
        }

        if(currentSize == 0 || currentSize == 1) {
            i_xpBorderManager.setSize(currentSize+1);
        } else {
            i_xpBorderManager.setSize(currentSize * increaseSize + 1);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                i_xpBorderManager.sendBorderToPlayer(player);
            }
        }.runTaskLater(XPBorder.getInstance(), 10L);

    }

    public void removeBorder(Player player) {
        i_xpBorderManager.setWorld(player.getWorld());
        i_xpBorderManager.setSize(Integer.MAX_VALUE);
        i_xpBorderManager.updateBorderSize(player);
    }

    public void adjustSize(Player player) {

        if(!settingsManager.getSetting("xpborder").isToggled())
            return;
        int a = getSizeInBlocks();

        if(settingsManager.getSetting("shrink").isToggled() || worldborderConfig.getInt("worldborder.size") < getSizeInBlocks() || settingsManager.getSetting("calctype").value().equals(BorderSizeCalculationType.ADD.name())) {
            if(settingsManager.getSetting("calctype").value().equals(BorderSizeCalculationType.CONFIG.name())) {
                setSizeInBlocks(player.getLevel());
            } else {
                setSizeInBlocks(Bukkit.getOnlinePlayers().stream().mapToInt(Player::getLevel).sum());
            }
        }

        int old = worldborderConfig.getInt("worldborder.size");

        setLevel(player.getLevel());

        i_xpBorderManager.setWorld(player.getWorld());
        this.currentSize = worldborderConfig.getInt("worldborder.size");
        this.increaseSize = Integer.parseInt(settingsManager.getSetting("expandsize").value());

        //prevent this from setting the size to 0
        if (this.increaseSize <= 0)
            this.increaseSize = 1;


        for (int i = 0; i < currentSize; i++) {
            if (MathUtils.isEven(i)) {
                currentSize++;
            }
        }

        if(settingsManager.getSetting("shrink").isToggled() || a < getSizeInBlocks()) {
            if (currentSize == 0 || currentSize == 1) {
                setAdjustSize(old, currentSize + 1);
            } else {
                setAdjustSize(old * increaseSize, (currentSize * increaseSize) + 1);
            }
        }
    }

    private void setAdjustSize(int from, int to) {
        for(int i = 0; i < from; i++) {
            if (MathUtils.isEven(i)) {
                from++;
            }
        }

        i_xpBorderManager.setSizeTransition(from, to, 3000);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> i_xpBorderManager.updateBorderSizeTransition(player));
            }
        }.runTaskLater(XPBorder.getInstance(), 10);
    }

    public void resetFile() {
        this.worldborderConfig.set("worldborder", null);
    }

    public void setCenter(Player player) {
        worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.x", player.getLocation().getBlockX() +0.5);
        worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.y", player.getLocation().getBlockY());
        worldborderConfig.set("worldborder."+player.getWorld().getName()+".center.z", player.getLocation().getBlockZ() +0.5);
    }

    public int calcRespawnLevel() {
        int level = getLevel();
        int percent = Integer.parseInt(settingsManager.getSetting("death").value());
        boolean isToggled = settingsManager.getSetting("death").isToggled();
        int calcedLevel = (int) (level*(percent/100f));
        if(isToggled) {
            return calcedLevel;
        } else {
            return 0;
        }
    }

    public int calcRespawnLevelFromPlayer(Player player) {
        int level = player.getLevel();
        int percent = Integer.parseInt(settingsManager.getSetting("death").value());
        boolean isToggled = settingsManager.getSetting("death").isToggled();
        int calcedLevel = (int) (level*(percent/100f));
        if(isToggled) {
            return calcedLevel;
        } else {
            return 0;
        }


    }

    public void checkIfPlayerIsOutside(Player player) {
        if(settingsManager.getSetting("security").isToggled()) {
            new IsPlayerInBorderTask(player).runTaskTimer(XPBorder.getInstance(), 60, 20);
        }
    }

    public void createBossbar(Player player) {
        if(settingsManager.getSetting("bossbar").isToggled()) {
            BossBar bossBar = Bukkit.createBossBar(Data.color("&a&l" + player.getLevel()), BarColor.GREEN, BarStyle.SEGMENTED_10, BarFlag.CREATE_FOG);
            bossBar.addPlayer(player);
            bossBar.setProgress(1);
            bossBar.setVisible(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    bossBar.setVisible(false);
                }
            }.runTaskLater(XPBorder.getInstance(), 60);

        }

    }

    /*
     * Setter and Getter down below here with some specifications, like storing the setters directly into the settings.yml
     * to prevent any issues and already having it for future plans.
     */

    public I_XPBorderManager getIXPBorderManager() {
        return i_xpBorderManager;
    }

    public void setLevel(int level) {

        String type = settingsManager.getSetting("calctype").value();

        if(BorderSizeCalculationType.valueOf(type) == BorderSizeCalculationType.CONFIG) {
            worldborderConfig.set("worldborder.level", level);
        }
        this.level = level;

    }

    public int getLevel() {
        level = worldborderConfig.getInt("worldborder.level");
        return level;
    }

    public void setSizeInBlocks(int size) {
        worldborderConfig.set("worldborder.size", size);
        this.sizeInBlocks = size;

    }

    public int getSizeInBlocks() {
        sizeInBlocks = worldborderConfig.getInt("worldborder.size");
        return sizeInBlocks;
    }

    public void setExpbar(float expbar) {
        worldborderConfig.set("worldborder.xpbar", expbar);
        this.expbar = expbar;
    }

    public float getExpbar() {
        expbar = worldborderConfig.getFloat("worldborder.xpbar");
        return expbar;
    }

    public void reload() {
        try {
            worldborderConfig.getConfiguration().load(worldborderConfig.getFile());
            settingsManager.initSettings();
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public Location getBorderCenter(String world) {
        return new Location(Bukkit.getWorld(world), worldborderConfig.getDouble("worldborder."+world+".center.x"), worldborderConfig.getDouble("worldborder."+world+".center.y"), worldborderConfig.getDouble("worldborder."+world+".center.z"));
    }
}
