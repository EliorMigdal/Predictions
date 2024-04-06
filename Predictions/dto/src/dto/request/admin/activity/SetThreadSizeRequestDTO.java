package dto.request.admin.activity;

import dto.DTO;

public class SetThreadSizeRequestDTO implements DTO {
    private final String size;

    public SetThreadSizeRequestDTO(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
