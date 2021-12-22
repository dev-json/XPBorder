package de.jxson.xpborder.settings;

import de.jxson.xpborder.config.ConfigManager;
import net.minecraft.network.protocol.Packet;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings
 * 21.12.2021 at 14:04
 */
public interface Setting {

    ConfigManager configManager = new ConfigManager("settings.yml");

    String name();
    String value();

    boolean isToggled();
    void toggle(boolean b);
}
