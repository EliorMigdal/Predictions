package engine.version.types.executions;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExecutionsVersions {
    private final Map<Integer, ExecutionItem> executionVersions = new LinkedHashMap<>();
    private Integer latestVersion = 0;

    public Map<Integer, ExecutionItem> getExecutionVersions() {
        return executionVersions;
    }

    public void addExecutionItem(ExecutionItem newItem) {
        executionVersions.put(++latestVersion, newItem);
    }
}
