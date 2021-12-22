package de.jxson.xpborder.config;

import de.jxson.xpborder.XPBorder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Author: Jason M.
 * de.jxson.xpborder.config
 * 21.12.2021 at 13:48
 */
public class ConfigManager {

    private final File file;
    private final YamlConfiguration configuration;

    private String PLUGIN_PATH;

    public ConfigManager(String filename) {
        PLUGIN_PATH = XPBorder.getInstance().getDataFolder().getPath();
        file = new File(PLUGIN_PATH, filename);
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigManager(String path, String filename) {
        file = new File(path, filename);
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigManager(File file) {
        this.file = file;
        configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void set(String path, Object val) {
        configuration.set(path, val);
        save();
    }

    public String getString(String path) {
        return configuration.getString(path);
    }

    public double getDouble(String path) {return configuration.getDouble(path);}

    public int getInt(String path) {
        return configuration.getInt(path);
    }

    public boolean getBool(String path) { return configuration.getBoolean(path); }

    public float getFloat(String path) { return (float) configuration.getDouble(path);}

    //Prüft ob Datei existiert
    public boolean isFilealreadyExists() {
        return file.exists();
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

}
