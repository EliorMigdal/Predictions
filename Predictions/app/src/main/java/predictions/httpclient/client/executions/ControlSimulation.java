package predictions.httpclient.client.executions;

import dto.request.client.executions.ControlSimulationDTO;
import dto.request.client.executions.enums.ControlOptions;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

public class ControlSimulation implements Request {
    public void controlSimulation(String clientName, Integer requestID, Integer simulationID, ControlOptions controlOption) {
        HttpRequest runSimulation = new Post();
        runSimulation.getResponse("/ControlSimulation",
                new ControlSimulationDTO(clientName, requestID, simulationID, controlOption));
    }
}
