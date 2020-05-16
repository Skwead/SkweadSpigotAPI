package api.skwead.commands;

import api.skwead.storage.file.json.JSONConfig;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class CommandManager {
    private final JavaPlugin plugin;
    private final JSONConfig<Set<CommandBase>> config;

    @SuppressWarnings("unchecked")
    public CommandManager(JavaPlugin plugin, String path) {
        this.plugin = plugin;
        final Set<CommandBase> s = new HashSet<>();
        this.config = new JSONConfig<>(path, (Class<Set<CommandBase>>) s.getClass());
    }

    public void registerCommands(Set<CommandBase> cmds){
        final Set<CommandBase> commands = config.getData();

        final Set<CommandBase> deleted = new HashSet<>();
        for (CommandBase dlt : commands) if (!cmds.contains(dlt)) deleted.add(dlt);
        commands.removeAll(deleted);

        commands.addAll(cmds);

        commands.forEach(cmd -> ((CraftServer) this.plugin.getServer()).getCommandMap()
                .register(cmd.getName(), cmd));

        try {
            this.config.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONConfig<Set<CommandBase>> getConfig() {
        return config;
    }
}
