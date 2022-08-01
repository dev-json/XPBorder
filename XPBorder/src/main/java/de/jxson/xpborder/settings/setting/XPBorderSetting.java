package de.jxson.xpborder.settings.setting;

import de.jxson.xpborder.settings.Setting;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings.setting
 * 21.12.2021 at 18:29
 */
public class XPBorderSetting implements Setting {

    boolean b = false;

    public XPBorderSetting() {
        if(configManager.getConfiguration().get("setting." + name() + ".enabled") != null) {
            b = configManager.getBool("setting." + name() + ".enabled");
        } else {
            configManager.set("setting." + name() + ".enabled", true);
            b = true;
        }

    }

    public String name() {
        return "xpborder";
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
