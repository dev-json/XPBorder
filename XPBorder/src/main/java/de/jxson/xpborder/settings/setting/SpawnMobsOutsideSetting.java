package de.jxson.xpborder.settings.setting;

import de.jxson.xpborder.settings.Setting;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings.setting
 * 25.01.2022 at 18:54
 */
public class SpawnMobsOutsideSetting implements Setting {

    boolean b = false;

    public SpawnMobsOutsideSetting() {
        if(configManager.getConfiguration().get("setting." + name() + ".enabled") != null) {
            b = configManager.getBool("setting." + name() + ".enabled");
        } else {
            configManager.set("setting." + name() + ".enabled", true);
            b = true;
        }
    }

    @Override
    public String name() {
        return "MobsSpawnOutside";
    }

    @Override
    public String value() {
        return "";
    }

    @Override
    public boolean isToggled() {
        return b;
    }

    @Override
    public void toggle(boolean b) {
        configManager.set("setting." + name() + ".enabled", b);
        this.b = b;
    }
}
