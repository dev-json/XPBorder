package de.jxson.xpborder.utils.updater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.utils.Data;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.io.IOUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Author: Jason M.
 * de.jxson.xpborder.utils.updater
 * 20.12.2021 at 20:37
 */
public class UpdateChecker {

    public final int pluginId;
    public String version = "unknown";
    private Data data = XPBorder.getInstance().getData();

    public UpdateChecker(int resourceId) {
        pluginId = resourceId;
        try {
            URL apiUrl = new URL("https://api.spiget.org/v2/resources/"+resourceId+"/versions/latest");
            URLConnection connection = apiUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(inputStream, encoding);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(body);
            JsonObject jsonObject = element.getAsJsonObject();

            version = jsonObject.get("name").toString().replace("\"", "");
            XPBorder.getInstance().getLogger().info("Checking for updates");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void checkForUpdates(Player player) {
        if(!XPBorder.getInstance().getDescription().getVersion().equals(getVersion())) {
            player.sendMessage(Data.color(data.getPrefix() + "&eThere is a new update available."));
            TextComponent component = new TextComponent(Data.color(data.getPrefix() + "&eTo download the latest version press here"));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open the download site").create()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/xpborder-a-new-individual-player-experience.97510/"));
            player.spigot().sendMessage(component);
        }
    }

    public String getVersion() {
        return version;
    }

    public int getPluginId() {
        return pluginId;
    }
}
