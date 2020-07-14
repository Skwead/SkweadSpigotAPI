package api.skwead.commands;

import api.skwead.storage.file.yml.YMLConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommandConfig extends YMLConfig {

    private final JavaPlugin plugin;
    /**
     * Creates the configuration
     *
     * @param name   The file name
     * @param plugin The plugin
     */
    public CommandConfig(String name, JavaPlugin plugin) {
        super(name, plugin);
        this.plugin = plugin;
    }

    public void generateFrom(Set<ConfigCommand> commands) throws IOException {
        final Set<CMD> cmds = new HashSet<>();
        commands.forEach(c -> cmds.add(c.serialize()));

        // Add missing cmds
        for (CMD c : cmds) {
            if (super.getConfig().get(c.getRef() + ".ref") == null) {
                System.out.println("ref not found");
                super.getConfig().createSection(c.getRef() + ".ref");
                super.getConfig().set(c.getRef() + ".ref", c.getRef());
                super.getConfig().save(super.getFile());
            }

            if (super.getConfig().get(c.getRef() + ".name") == null) {
                System.out.println("name not found");
                super.getConfig().createSection(c.getRef() + ".name");
                super.getConfig().set(c.getRef() + ".name", c.getName());
                super.getConfig().save(super.getFile());
            }

            if (super.getConfig().get(c.getRef() + ".usage") == null) {
                System.out.println("usage not found");
                super.getConfig().createSection(c.getRef() + ".usage");
                super.getConfig().set(c.getRef() + ".usage", c.getUsageMessage());
                super.getConfig().save(super.getFile());
            }

            if (super.getConfig().get(c.getRef() + ".description") == null) {
                System.out.println("description not found");
                super.getConfig().createSection(c.getRef() + ".description");
                super.getConfig().set(c.getRef() + ".description", c.getDescription());
                super.getConfig().save(super.getFile());
            }

            if (super.getConfig().get(c.getRef() + ".aliases") == null) {
                System.out.println("aliases not found");
                super.getConfig().createSection(c.getRef() + ".aliases");
                super.getConfig().set(c.getRef() + ".aliases", c.getAliases().toArray());
                super.getConfig().save(super.getFile());
            }
        }

        // TODO: Remove extra cmds

        // Update existing commands
        for (ConfigCommand c : commands) {
            c.setName((String) Objects.requireNonNull(super.getConfig().get(c.getRef() + ".name")));
            c.setUsage((String) Objects.requireNonNull(super.getConfig().get(c.getRef() + ".usage")));
            c.setDescription((String) Objects.requireNonNull(super.getConfig().get(c.getRef() + ".description")));
            c.setAliases(Objects.requireNonNull(super.getConfig().getStringList(c.getRef() + ".aliases")));

            // Register all commands
            c.register(plugin);
        }
    }

    @Override
    public void createConfig(Set<String> keys) throws IOException {

    }
}
