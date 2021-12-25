package de.jxson.xpborder.settings.setting;

import de.jxson.xpborder.settings.Setting;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings.setting
 * 21.12.2021 at 18:30
 */
public class PercentdeathSetting implements Setting {

    boolean b;
    int percent;

    public PercentdeathSetting() {
        if(configManager.getConfiguration().get("setting." + name() + ".enabled") != null) {
            b = configManager.getBool("setting." + name() + ".enabled");
            percent = configManager.getInt("setting." + name() + ".percent");
        } else {
            configManager.set("setting." + name() + ".enabled", false);
            configManager.set("setting." + name() + ".percent", 0);
            b = false;
            percent = 0;
        }

    }

    public String name() {
        return "death";
    }

    public String value() {
        return String.valueOf(percent);
    }

    public void setPercent(int percent) {
        this.percent = percent;
        configManager.set("setting." + name() + ".percent", percent);
    }

    public boolean isToggled() {
        return b;
    }

    public void toggle(boolean b) {
        configManager.set("setting." + name() + ".enabled", b);
        this.b = b;
    }

}
