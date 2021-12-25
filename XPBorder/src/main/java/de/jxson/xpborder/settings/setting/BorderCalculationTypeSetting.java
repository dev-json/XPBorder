package de.jxson.xpborder.settings.setting;

import de.jxson.xpborder.settings.Setting;
import de.jxson.xpborder.world.worldborder.BorderSizeCalculationType;

/**
 * Author: Jason M.
 * de.jxson.xpborder.settings.setting
 * 25.12.2021 at 05:30
 */
public class BorderCalculationTypeSetting implements Setting {

    BorderSizeCalculationType type;

    public BorderCalculationTypeSetting() {
        if (configManager.getConfiguration().get("setting." + name() + ".type") == null) {
            configManager.set("setting." + name() + ".type", BorderSizeCalculationType.CONFIG.name());
        }
        type = BorderSizeCalculationType.valueOf((String) configManager.get("setting." + name() + ".type"));


    }

    public String name() {
        return "calctype";
    }

    public String value() {
        return getType().name();
    }

    public void setType(BorderSizeCalculationType type) {
        this.type = type;
        configManager.set("setting." + name() + ".type", type.name());
    }

    public BorderSizeCalculationType getType() {
        return type;
    }

    public boolean isToggled() {
        return true;
    }

    public void toggle(boolean b) {

    }

}
