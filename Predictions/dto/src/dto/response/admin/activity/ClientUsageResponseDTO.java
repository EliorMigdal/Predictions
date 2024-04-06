package dto.response.admin.activity;

import dto.DTO;

import java.util.ArrayList;

public class ClientUsageResponseDTO implements DTO {
    private final ArrayList<ClientUsageDTO> usageDTOS = new ArrayList<>();
    private Integer version = -1;

    public ClientUsageResponseDTO() {
    }

    public ArrayList<ClientUsageDTO> getUsageDTOS() {
        return usageDTOS;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void addNewUsageUpdate(ClientUsageDTO usageDTO) {
        this.usageDTOS.add(usageDTO);
    }
}
