package de.jxson.xpborder.utils.version;

import de.jxson.xpborder.XPBorder;
import de.jxson.xpborder.interfaces.I_XPBorderManager;
import org.bukkit.Bukkit;

/**
 * Author: Jason M.
 * de.jxson.xpborder.utils.version
 * 20.12.2021 at 19:42
 */
public class Version {

    private static String version = "no version found";

     public static I_XPBorderManager verifyVersion() {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException exception) {
            Bukkit.getLogger().warning("ArrayIndexOutOfBoundsExceptions, please make sure the path is correct and exists!");
        }

         XPBorder.getInstance().getLogger().info("Found version: " + version);

         return switch (version) {
             case "v1_17_R1" -> new de.jxson.xpborder.nms.v1_17_R1.XPBorderManager();
             case "v1_18_R1" -> new de.jxson.xpborder.nms.v1_18_R1.XPBorderManager();
             case "v1_18_R2" -> new de.jxson.xpborder.nms.v1_18_R2.XPBorderManager();
             case "v1_19_R1" -> new de.jxson.xpborder.nms.v1_19_R1.XPBorderManager();
             case "v1_19_R2" -> new de.jxson.xpborder.nms.v1_19_3_R1.XPBorderManager();
             case "v1_20_2_R2" -> new de.jxson.xpborder.nms.v1_20_2_R1.XPBorderManager();
             default -> null;
         };
     }




}
