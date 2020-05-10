package api.skwead.storage.file.json;

import api.skwead.commands.CustomCommand;

public class CommandConfig extends JSONConfig {
    /**
     * Creates a configuration object
     *
     * @param path   the path to the file
     */
    public CommandConfig(String path) {
        super(path, CustomCommand.class);
    }
}
