package predictions.controllers.client.cells.popup;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import dto.request.client.requests.TerminationInfoDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class TerminationTermsPopupController implements Initializable {

    @FXML
    private Label upleftTermName;

    @FXML
    private Label uprightTermValue;

    @FXML
    private Label botleftTermName;

    @FXML
    private Label botightTermValue;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Integer resSize = (Integer) resources.getObject("Size");
        if(resSize == 1) {
            TerminationInfoDTO dto = (TerminationInfoDTO) resources.getObject("Item 1");
            upleftTermName.setText(dto.getTermName());
            uprightTermValue.setText(dto.getTerminationTerm());

        } else if (resSize == 2) {
            TerminationInfoDTO dto = (TerminationInfoDTO) resources.getObject("Item 1");

            TerminationInfoDTO dto2 = (TerminationInfoDTO) resources.getObject("Item 2");
            upleftTermName.setText(dto.getTermName());
            uprightTermValue.setText(dto.getTerminationTerm());

            botleftTermName.setText(dto2.getTermName());
            botightTermValue.setText(dto2.getTerminationTerm());
        }

    }
}
