package api.skwead.storage.file.yml;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@SuppressWarnings("unused")
public abstract class YMLConfig {
    private final String name;
    private File file;
    private FileConfiguration config;

    /**
     * Creates the configuration
     *
     * @param name   The file name
     * @param plugin The plugin
     */
    public YMLConfig(String name, JavaPlugin plugin) {
        this.name = name;
        create(plugin);
    }

    private void create(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), name + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * The logic to put data in the config
     *
     * @param keys The keys to be put
     * @throws IOException while processing some stuff related to files
     */
    public abstract void createConfig(Set<String> keys) throws IOException;

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
