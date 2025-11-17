package xyz.matumaka.antiNetherite;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {
    public static String translateHexColors(String message) {
        message = message.replaceAll("&#([A-Fa-f0-9]{6})", "\u00A7x\u00A7$1\u00A7$2\u00A7$3\u00A7$4\u00A7$5\u00A7$6");
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
