package predictions.controllers.client.cells;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import predictions.models.templates.Executed;

import java.net.URL;
import java.util.ResourceBundle;

public class ExecutedCellController implements Initializable {
    @FXML
    public ImageView status_icon;

    @FXML
    public Label date_lbl;

    @FXML
    public Label simulation_name_lbl;

    @FXML
    public Label request_id_lbl;

    @FXML
    public ImageView info_icon;

    @FXML
    public Label simulation_id_lbl;

    private final Executed executed;

    public ExecutedCellController(Executed executed) {
        this.executed = executed;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date_lbl.textProperty().bind(executed.dataProperty().asString());
        simulation_id_lbl.textProperty().bind(executed.simulationIDProperty());
        simulation_name_lbl.textProperty().bind(executed.simulationNameProperty());
        request_id_lbl.textProperty().bind(executed.requestIDProperty());
    }
}
