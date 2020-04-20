package api.skwead.messages.log;

import api.skwead.storage.file.json.JSONConfig;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to handle a logger common to the whole plugin. Deals with simple debugging
 * Ideally there should only be one Logger in action, so the indentation works perfectly
 */
@SuppressWarnings("unused")
public class Logger {
    private Set<Process> procs;
    private JSONConfig<Process> conf;

    /**
     * Creates a Logger instance.
     * Compares the processes from the plugin (passed as arguments to this constructor) with the
     * ones stored in the config.
     * @param procs The default processes
     */
    public Logger(Set<Process> procs) {
        Process main = null;
        try {
            main = conf.loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert main != null;
        Set<Process> confProc = this.decirialize(main);

        this.procs = areEqual(procs, confProc) ? confProc : procs;
    }

    private boolean areEqual(Set<Process> procs, Set<Process> confProc) {
        boolean notEqual = false;

        for (Process proc : procs) {
            boolean found = false;
            for (Process proc2 : confProc) {
                if(proc.getName().equals(proc2.getName())) {
                    found = true;
                    break;
                }
            }
            if(!found) return false;
        }
        return true;
    }

    private Set<Process> decirialize(Process p){
        Set<Process> res = new HashSet<>();

        for (Process sp : p.getSubProcesses()) {
            if (sp.getSubProcesses().isEmpty())
                res.add(sp);
            else
                decirialize(sp);
        }

        return res;
    }

    private Process parse(Process sp) {
        String[] components = sp.getName().split("-");
        String name = components.length > 0 ? components[0] : sp.getName();

        Process top = new Process(sp.isEnabled(), name, new HashSet<>());

        Process t = top;
        Process temp;

        for (int i = 1; i < components.length; i++) {

            String subName = "";
            for (int j = 0; j <= i; subName += "-" + components[j++]) ;
            subName = subName.substring(1);

            temp = new Process(sp.isEnabled(), subName, new HashSet<>());
            t.registerSubProcess(temp);
            t = temp;
        }

        return top;
    }
}
