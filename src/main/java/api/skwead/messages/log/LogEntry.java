package api.skwead.messages.log;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores the data of a process
 */
public class LogEntry {
    private boolean enabled;
    private String name;
    private Set<LogEntry> subProcesses;

    public LogEntry() {
        this("sample");
    }

    public LogEntry(String name){
        this(true, name, new HashSet<>());
    }

    protected LogEntry(boolean enabled, String name, Set<LogEntry> subProcesses) {
        this.enabled = enabled;
        this.subProcesses = subProcesses;
        this.name = name;
    }

    public void registerSubProcess(LogEntry logEntry){
        subProcesses.add(logEntry);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<LogEntry> getSubProcesses() {
        return subProcesses;
    }

    public String getName() {
        return name;
    }
}
