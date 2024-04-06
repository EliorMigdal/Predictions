package predictions.httpclient.client.requests;

import dto.request.client.requests.SubmitRequestDTO;
import dto.request.client.requests.TerminationInfoDTO;
import predictions.httpclient.Request;
import predictions.httpclient.requests.HttpRequest;
import predictions.httpclient.requests.type.Post;

import java.util.ArrayList;

public class SubmitRequest implements Request {
    public void submitNewRequest(String clientName, String simulationName, Integer amount,
                                             ArrayList<TerminationInfoDTO> terminationInfo) {
        HttpRequest submitRequest = new Post();
        submitRequest.getResponse("/SubmitRequest",
                new SubmitRequestDTO(clientName, simulationName, amount, terminationInfo));
    }
}
