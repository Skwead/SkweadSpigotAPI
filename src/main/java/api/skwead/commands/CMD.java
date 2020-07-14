package api.skwead.commands;

import java.util.Objects;
import java.util.Set;

public class CMD {
    private final String ref;
    private final String name;
    private final String description;
    private final String usageMessage;
    private final Set<String> aliases;
    private final Set<CMD> subCmds;

    public CMD(String ref, String name, String description, String usageMessage, Set<String> aliases, Set<CMD> subCmds) {
        this.ref = ref;
        this.name = name;
        this.description = description;
        this.usageMessage = usageMessage;
        this.aliases = aliases;
        this.subCmds = subCmds;
    }

    public String getRef() {
        return ref;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsageMessage() {
        return usageMessage;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public Set<CMD> getSubCmds() {
        return subCmds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CMD)) return false;
        final CMD cmd = (CMD) o;
        return Objects.equals(ref, cmd.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }
}
