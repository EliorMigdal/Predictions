package dto.response.admin.activity;

public class ClientUsageDTO {
    private final String clientName;
    private final Integer numOfRunning;
    private final Integer numOfWaiting;
    private final Integer numOfFinished;

    public ClientUsageDTO(String clientName, Integer numOfRunning, Integer numOfWaiting, Integer numOfFinished) {
        this.clientName = clientName;
        this.numOfRunning = numOfRunning;
        this.numOfWaiting = numOfWaiting;
        this.numOfFinished = numOfFinished;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getNumOfRunning() {
        return numOfRunning;
    }

    public Integer getNumOfWaiting() {
        return numOfWaiting;
    }

    public Integer getNumOfFinished() {
        return numOfFinished;
    }
}
