package de.jxson.xpborder.settings.setting;

import de.jxson.xpborder.settings.Setting;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings.setting
 * 21.12.2021 at 18:33
 */
public class SecuritySetting implements Setting {

    boolean b = false;

    public SecuritySetting() {
        if(configManager.getConfiguration().get("setting." + name() + ".enabled") != null) {
            b = configManager.getBool("setting." + name() + ".enabled");
        } else {
            configManager.set("setting." + name() + ".enabled", false);
            b = false;
        }

    }

    public String name() {
        return "security";
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
