package api.skwead.messages.log;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores the data of a process
 */
@SuppressWarnings("unused")
public class Process {
    private boolean enabled;
    private String name;
    private Set<Process> subProcesses;

    public Process() {
        this("sample");
    }

    public Process(String name){
        this(true, name, new HashSet<>());
    }

    protected Process(boolean enabled, String name, Set<Process> subProcesses) {
        this.enabled = enabled;
        this.subProcesses = subProcesses;
        this.name = name;
    }

    public void registerSubProcess(Process logEntry){
        subProcesses.add(logEntry);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Process> getSubProcesses() {
        return subProcesses;
    }

    public String getName() {
        return name;
    }
}
