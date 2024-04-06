package engine.version.types.usage;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientUsageVersions {
    private final Map<Integer, ClientUsageUpdate> usageUpdates = new LinkedHashMap<>();
    private Integer latestVersion = 0;

    public Map<Integer, ClientUsageUpdate> getUsageUpdates() {
        return usageUpdates;
    }

    public void addNewUsageUpdate(ClientUsageUpdate usageUpdate) {
        usageUpdates.put(++latestVersion, usageUpdate);
    }
}
