package dto.response.client.executions;

import dto.DTO;

public class RemainingExecutionsResponseDTO implements DTO {
    private Integer remainingExecutions;

    public void setRemainingExecutions(Integer remainingExecutions) {
        this.remainingExecutions = remainingExecutions;
    }

    public Integer getRemainingExecutions() {
        return remainingExecutions;
    }
}
