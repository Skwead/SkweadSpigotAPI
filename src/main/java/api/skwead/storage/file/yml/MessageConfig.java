package api.skwead.storage.file.yml;

import api.skwead.messages.chat.ChatUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Handles a default message config
 */
@SuppressWarnings("unused")
public class MessageConfig extends YMLConfig {

    private Map<String, String> messages = new HashMap<>();

    /**
     * Creates a Config for the plugin messages given a {@link ChatUtils} to message a player
     * @param chatUtils The ChatUtils to handle the messages
     * @param plugin The {@link JavaPlugin} to with this config is relative
     * @param fileName The name of the file
     */
    public MessageConfig(JavaPlugin plugin, ChatUtils chatUtils, String fileName) {
        super(fileName, plugin);
    }

    /**
     * Sets the keys and values of the config file
     * @param keys the list of each key that will appear on the config
     * @throws IOException if anyting bad happens when saving the file, see {@link FileConfiguration#save(File)}
     */
    public void createConfig(Set<String> keys) throws IOException {
        for (String key : keys) {
            if (!super.getConfig().isConfigurationSection(key))
                if (super.getConfig().get(key) == null) {
                    super.getConfig().createSection(key);
                    super.getConfig().set(key, "null");
                    messages.put(key, "null");
                    super.getConfig().save(super.getFile());
                }
                else
                    messages.put(key, super.getConfig().getString(key));
        }
    }

    /**
     * Gets a message replacing placeholders
     * @param key
     * @param player
     * @return
     */
    public String getMessageForPlayer(String key, UUID player){
        return PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(player), messages.get(key));
    }

    public String getRawMessage(String key, UUID player, boolean b) {
        return messages.get(key);
    }
}
