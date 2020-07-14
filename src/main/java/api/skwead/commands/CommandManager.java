package api.skwead.commands;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class CommandManager {

    private final CommandConfig config;

    public CommandManager(JavaPlugin plugin) {

//        final Set<ConfigCommand> s = new HashSet<>();
//        this.config = new JSONConfig<>(path, (Class<Set<CMD>>) s.getClass());
        this.config = new CommandConfig("commands", plugin);
    }

    public CommandConfig getConfig() {
        return config;
    }

    //    public void registerCommands(Set<ConfigCommand> cmds) {
//        final Set<CMD> cmdConf = new HashSet<>(config.getData());
//        final Set<CMD> cmdSet = new HashSet<>();
//        cmds.forEach(configCommand -> cmdSet.add(configCommand.serialize()));
//
//        // Adds missing commands to the config
//        cmdConf.addAll(cmdSet);
//
//        // Removes extra commands
////        cmdSet.forEach(cmd -> {
////            if (!cmdConf.contains(cmd))
////                cmdConf.remove(cmd);
////        });
//
//        // Updates from config
//        cmdConf.forEach(cmd -> {
//            ConfigCommand cc = null;
//
//            for (ConfigCommand c : cmds) {
//                if (c.getRef().equals(cmd.getRef())) {
//                    cc = c;
//                    break;
//                }
//            }
//            if (cc == null)
//                throw new IllegalStateException(
//                        "Somehow the comand was not found in the config, idk what u did but u fucked up real bad");
//
//            cc.setAliases(new ArrayList<>(cmd.getAliases()));
//            cc.setDescription(cmd.getDescription());
//            cc.setName(cmd.getName());
////            cc.setRef(cmd.getRef());
//            cc.setUsage(cmd.getUsageMessage());
//        });
//
//        cmds.forEach(cmd -> cmd.register(plugin));
//
//        config.setData(cmdConf);
//        try {
//            config.saveFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public JSONConfig<Set<CMD>> getConfig() {
//        return config;
//    }
}
