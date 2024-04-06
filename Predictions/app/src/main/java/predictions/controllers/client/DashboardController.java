package predictions.controllers.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Text user_name;

    @FXML
    private Label login_date;

    @FXML
    private ListView<?> requests_listview;

    @FXML
    private Label waiting_lbl;

    @FXML
    private Label declined_lbl;

    @FXML
    private Label approved_lbl;

    @FXML
    private ListView<?> executions_listview;

    @FXML
    private ChoiceBox<?> simulations_choiceBox;

    @FXML
    private TextField amount_fld;

    @FXML
    private CheckBox time_chbox;

    @FXML
    private TextField time_term_fld;

    @FXML
    private CheckBox ticks_chbox;

    @FXML
    private TextField ticks_term_fld;

    @FXML
    private CheckBox user_chbox;

    @FXML
    private Button send_request_btn;

    public DashboardController() {
        Model.getInstance().getControllerFactory().setDashboardController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
