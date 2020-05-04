package api.skwead.messages.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
/*
Code 	Official Name   MOTD code
§0 	    Black 	        \u00A70
§1 	    Dark Blue 	    \u00A71
§2 	    Dark Green 	    \u00A72
§3 	    Dark Aqua 	    \u00A73
§4 	    Dark Red 	    \u00A74
§5 	    Dark Purple 	\u00A75
§6 	    Gold 	        \u00A76
§7 	    Gray 	        \u00A77
§8 	    Dark Gray 	    \u00A78
§9 	    Blue 	        \u00A79
§a 	    Green 	        \u00A7a
§b 	    Aqua 	        \u00A7b
§c 	    Red 	        \u00A7c
§d 	    Light Purple 	\u00A7d
§e 	    Yellow 	        \u00A7e
§f 	    White 	        \u00A7f
§k 	    Obfuscated 	    \u00A7k
§l 	    Bold 	        \u00A7l
§m 	    Strikethrough 	\u00A7m
§n 	    Underline 	    \u00A7n
§o 	    Italic 	        \u00A7o
§r 	    Reset 	        \u00A7r
-- 	    Extra line 	    \n
 */

/**
 * A class to help handeling messages to Players and Console.
 * All methods handle the &'descriminator' syntax for colors and effects
 */
@SuppressWarnings("unused")
public class ChatUtils {
    private JavaPlugin plugin;

    /**
     * Returns a String with the placeholders replaced with their values.
     * @param p the Player for the Placeholder
     * @param message the message to be parsed
     * @return the parsed message
     */
    public static String withPAPI(UUID p, String message){
        return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(p), message);
    }

    /**
     * Cretaes an instance of ChatUtils for a specific JavaPlugin
     * @param instance the JavaPlugin instance
     */
    public ChatUtils(JavaPlugin instance) {
        plugin = instance;
    }

    /**
     * Logs some status to the console
     * @param type the log type, wether it is an ERROR, INFO or SUCSESS
     * @param message the message to be logged
     */
    public void log(MessageType type, String message){
        switch (type){
            case SUCCESS:
                consoleMessage("&3[&b" + plugin.getName() + "&3]" + "&a[SUCESSO] &2"+message);
                break;
            case INFO:
                consoleMessage("&3[&b" + plugin.getName() + "&3]" + "&e[INFO] &6"+message);
                break;
            case ERROR:
                consoleMessage("&3[&b" + plugin.getName() + "&3]" + "&c[ERRO] &4"+message);
                break;
        }
    }

    /**
     * Makes an info log
     * @param message message to be logged
     */
    public void log(String message){
        log(MessageType.INFO, message);
    }

    /**
     * Sends a message to the console
     * @param message the message to be sent
     */
    public void consoleMessage(String message){
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Sends a message to a Player
     * @param recipient Who recieves the message
     * @param message The message to be sent
     */
    public void playerMessage(UUID recipient, String message){
        Bukkit.getPlayer(recipient).sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
