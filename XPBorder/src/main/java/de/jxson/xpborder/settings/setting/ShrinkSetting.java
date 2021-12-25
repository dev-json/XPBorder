package de.jxson.xpborder.settings.setting;

import de.jxson.xpborder.settings.Setting;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings.setting
 * 25.12.2021 at 04:54
 */
public class ShrinkSetting implements Setting {

    boolean b = false;

    public ShrinkSetting() {
        if(configManager.getConfiguration().get("setting." + name() + ".enabled") != null) {
            b = configManager.getBool("setting." + name() + ".enabled");
        } else {
            configManager.set("setting." + name() + ".enabled", true);
            b = false;
        }

    }

    public String name() {
        return "shrink";
    }

    public String value() {
        return "";
    }

    public boolean isToggled() {
        return b;
    }

    public void toggle(boolean b) {
        configManager.set("setting." + name() + ".enabled", b);
        this.b = b;
    }

}
