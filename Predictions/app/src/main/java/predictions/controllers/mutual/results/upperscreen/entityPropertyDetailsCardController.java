package predictions.Controllers.Mutual.results.upperscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import predictions.Controllers.Mutual.results.upperscreen.helpers.BasicEntityCardDetails;

import java.net.URL;
import java.util.ResourceBundle;

public class entityPropertyDetailsCardController extends BasicEntityCardDetails implements Initializable {

    @FXML private Label entityName;
    @FXML private Label entityCountLabel;

    public entityPropertyDetailsCardController() {
        super("","");
    }


    @FXML
    void showFullName(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.entityName.textProperty().bind(entityNameProp);
        this.entityCountLabel.textProperty().bind(entityCountProp);
    }
}
