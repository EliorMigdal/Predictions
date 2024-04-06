package predictions.models.logic.fxml.requests.runtime.type.Client;

import dto.request.client.CostumeArrayDTOList;
import dto.request.client.requests.TerminationInfoDTO;
import javafx.fxml.FXMLLoader;
import predictions.models.logic.fxml.requests.FXMLResponseObj;
import predictions.models.logic.fxml.requests.runtime.RuntimeRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetClientRequestsFXML implements RuntimeRequest {
    private FXMLLoader fxmlLoader;

    public GetClientRequestsFXML(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }
    public GetClientRequestsFXML() {
        this.fxmlLoader = new FXMLLoader();

    }
    @Override
    public Object generateResponse(Object... args) throws IOException {
        URL currURLConstant = null;
        ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList = null;
        for (Object argument : args) {
            if (argument instanceof URL) {
                currURLConstant = (URL) argument;
            }
            if(argument instanceof ArrayList){
                terminationInfoDTOArrayList = (ArrayList<TerminationInfoDTO>) argument;
            }
        }
        if(currURLConstant != null ) {
            this.fxmlLoader.setLocation(currURLConstant);
            if (terminationInfoDTOArrayList != null) {
                CostumeArrayDTOList costumeArrayDTOList = new CostumeArrayDTOList(terminationInfoDTOArrayList);
                this.fxmlLoader.setResources(costumeArrayDTOList);
            }
            return new FXMLResponseObj(this.fxmlLoader.load(), this.fxmlLoader.getController());

        }

        return null;
    }
}
