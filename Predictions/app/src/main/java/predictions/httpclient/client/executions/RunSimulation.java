package predictions.httpclient.client.executions;

import dto.request.client.executions.RunSimulationDTO;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

public class RunSimulation implements Request {
    public void runSimulation(String clientName, Integer requestID, Integer simulationID) {
        HttpRequest runSimulation = new Post();
        runSimulation.getResponse("/RunSimulation", new RunSimulationDTO(clientName, requestID, simulationID));
    }
}
