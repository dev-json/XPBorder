package de.jxson.xpborder.settings;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.config.ConfigManager;
import de.jxson.xpborder.settings.setting.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings
 * 21.12.2021 at 14:03
 */
public class SettingsManager {

    private ConfigManager settingsConfig;

    private final List<Setting> settings;

    public SettingsManager() {
        migrateData();
        settings = new ArrayList<>();
        settingsConfig = Setting.configManager;
        initSettings();
        XPBorder.getInstance().getLogger().info("Loading " + settings.size() + " settings...");
    }

    public void initSettings() {
        if(!settings.isEmpty())
            settings.clear();

        settings.add(new WorldresetSetting());
        settings.add(new XPBorderSetting());
        settings.add(new PercentdeathSetting());
        settings.add(new SecuritySetting());
        settings.add(new ExpandsizeSetting());
        settings.add(new BossbarSetting());
        settings.add(new ShrinkSetting());
        settings.add(new BorderCalculationTypeSetting());
    }

    public Setting getSetting(String name) {
        for(int i = 0; i < settings.size(); i++) {
            for(Setting setting : settings) {
                if(setting.name().equals(name)) {
                    return setting;
                }
            }
        }
        return null;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    private void migrateData() {
        File oldFile = new File("plugins/XPBorder/config.yml");
        File newFile = new File("plugins/XPBorder/settings.yml");

        if(new ConfigManager("config.yml").isFilealreadyExists()) {
            XPBorder.getInstance().getLogger().info("Starting to migrate old config file.");
            File backupFolder = new File("plugins/XPBorder/backup");
            if(backupFolder.mkdirs()) {
                XPBorder.getInstance().getLogger().info("Backup folder was created!");
                if(oldFile.exists() && !newFile.exists()) {
                    try {
                        //temporary to migrate the old data into the new file
                        settingsConfig = new ConfigManager("settings.yml");
                        FileUtils.moveFile(oldFile, new File(backupFolder + "/" + oldFile.getName()));
                        ConfigManager oldCm = new ConfigManager(new File(backupFolder + "/" + oldFile.getName()));

                        settingsConfig.set("setting.worldreset.enabled", oldCm.getBool("xpborder.doreset"));
                        settingsConfig.set("setting.xpborder.enabled", oldCm.getBool("xpborder.enabled"));
                        settingsConfig.set("setting.death.enabled", oldCm.getBool("xpborder.percentdeath.enable"));
                        settingsConfig.set("setting.death.percent", oldCm.getInt("xpborder.percentdeath.percent"));
                        settingsConfig.set("setting.expandsize.enabled", true);
                        settingsConfig.set("setting.expandsize.size", oldCm.getInt("xpborder.custom.size"));
                        settingsConfig.set("setting.security.enabled", oldCm.getBool("xpborder.security.teleport"));
                        settingsConfig.set("setting.bossbar.enabled", oldCm.getBool("xpborder.noticer.bossbar"));

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        } else {
            XPBorder.getInstance().getLogger().info("No data was migrated!");
        }
    }

}
