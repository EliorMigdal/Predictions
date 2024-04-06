package predictions.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import predictions.models.Model;
import predictions.models.requests.Request;
import predictions.models.requests.type.Interaction.LoginRequest;
import predictions.views.Enums.AccountType;
import predictions.views.Enums.HttpMethods;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label account_username_lbl;
    public TextField username_fld;
    public PasswordField password_fld;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT,AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable
                -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        String isClient = acc_selector.getValue().toString().equalsIgnoreCase("client") ? "true" : " false";

        Request request = new LoginRequest(username_fld.getText(), isClient,"/Login");
        error_lbl.setText((String) request.handleRequest(HttpMethods.POST));

        if (error_lbl.textProperty().get().isEmpty()) {
            if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
                Model.getInstance().getViewFactory().showClientWindow();
            } else {
                Model.getInstance().getViewFactory().showAdminWindow();
            }

            Model.getInstance().setCurrConnectedEntity(username_fld.getText());
            Model.getInstance().getViewFactory().closeStage(stage);
        }
    }
}
