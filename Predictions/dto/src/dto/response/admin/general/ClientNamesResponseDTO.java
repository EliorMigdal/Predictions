package dto.response.admin.general;

import java.util.ArrayList;

public class ClientNamesResponseDTO {
    private final ArrayList<String> clientNames = new ArrayList<>();
    private Integer version = -1;

    public ArrayList<String> getClientNames() {
        return clientNames;
    }

    public void addNewName(String clientName) {
        clientNames.add(clientName);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
