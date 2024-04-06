package predictions.controllers.client.requests.items;

import dto.response.client.init.NewExecutionDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import predictions.controllers.client.initialize.InitializationController;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.httpclient.client.init.NewExecution;
import predictions.models.Model;
import predictions.models.logic.fxml.requests.FXMLResponseObj;
import predictions.models.logic.fxml.requests.runtime.type.Client.GetClientRequestsFXML;
import utills.FXMLConstants;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientRequestsCellController implements Initializable {
    @FXML private Circle statusCircle;
    @FXML private Label dateLabel;
    @FXML private Label amountLabel;
    @FXML private Label simulationNameLabel;
    @FXML private Label requestIDLabel;
    @FXML private ImageView executeButton;
    @FXML private ImageView infoButton;

    private final ClientRequest clientRequest;
    private final FXMLResponseObj workingFXMLResponder;
    private Stage popupStage;
    private final SimpleStringProperty requestStatusProperty = new SimpleStringProperty("Waiting");
    private final SimpleStringProperty clientName = new SimpleStringProperty(
            Model.getInstance().getCurrConnectedEntity());

    public ClientRequestsCellController(ClientRequest clientRequest) throws IOException {
        this.clientRequest = clientRequest;
        this.workingFXMLResponder = (FXMLResponseObj) Model.getInstance().getMainFXMLRequestHandler()
                .handleRequest(
                        new GetClientRequestsFXML(),
                        FXMLConstants.ClientBundle.requestsBundle.TERMINATION_TERM_POPUP_RESOURCE,
                        clientRequest.getTerminationInfoDTOArrayList()
                );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        initializeExecutionButton();
        initializeInfoButton();
    }

    private void bindAll() {
        dateLabel.textProperty().bind(clientRequest.dataDateProperty().asString());
        amountLabel.textProperty().bind(clientRequest.total_RemainingProperty());
        simulationNameLabel.textProperty().bind(clientRequest.simulationNameProperty());
        requestIDLabel.textProperty().bind(clientRequest.requestIDProperty());
        requestStatusProperty.bind(clientRequest.reqStatusProperty());
        handleStatusCircleBind();
        handleExecuteButtonBind();
    }

    private void handleStatusCircleBind() {
        statusCircle.fillProperty().bind(Bindings.createObjectBinding(() -> {
            String requestStatus = requestStatusProperty.get();
            if ("Waiting".equals(requestStatus)) {
                return Color.LIGHTGOLDENRODYELLOW;
            } else if ("Approved".equals(requestStatus)) {
                return Color.DARKGREEN;
            } else {
                return Color.DARKRED;
            }
        }, requestStatusProperty));
    }

    private void handleExecuteButtonBind() {
        executeButton.visibleProperty().bind(Bindings.createObjectBinding(() -> {
            String requestStatus = requestStatusProperty.get();
            return requestStatus.equals("Approved") && Arrays
                    .stream(clientRequest.total_RemainingProperty().get().split("/"))
                    .skip(1)
                    .map(String::trim)
                    .findFirst()
                    .map(Integer::parseInt)
                    .filter(value -> value > 0)
                    .isPresent();
        }, requestStatusProperty));
    }

    private void initializeExecutionButton() {
        executeButton.setOnMousePressed(event -> {
            Model.getInstance().getControllerFactory().getClientMenuController().showSimulationExecute();
            InitializationController initializationController = Model.getInstance().getControllerFactory().getExecutionController();
            NewExecutionDTO newExecution = new NewExecution().createNewExecution(clientName.get(),
                    Integer.parseInt(requestIDLabel.textProperty().get()), simulationNameLabel.getText());
            ExecutionInfo newExecutionItem = new ExecutionInfo(clientName.get(),
                    Integer.parseInt(requestIDLabel.textProperty().get()), simulationNameLabel.getText(),
                    newExecution.getSimulationID());
            initializationController.setExecutionItem(newExecutionItem);
            initializationController.generateEntitiesLayout();
            initializationController.generateEnvironmentLayout();
        });
    }

    private void initializeInfoButton() {
        infoButton.setOnMousePressed(event -> {
            if (popupStage == null) {
                createPopUpStage(this.workingFXMLResponder.getCurrBox());
            }
            popupStage.show();
        });
    }

    private void createPopUpStage(Parent currParent) {
        popupStage = new Stage();
        popupStage.setTitle("Termination terms");
        Scene scene = new Scene(currParent, 320, 191);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.DECORATED);
        scene.setFill(Color.TRANSPARENT);
        popupStage.setResizable(false);
        popupStage.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = (screenBounds.getWidth() - 320) / 2;
        double y = (screenBounds.getHeight() - 191) / 2;
        popupStage.setX(x);
        popupStage.setY(y);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/predictions" +
                "/controllers/mutual/details/tree/images/terminationImg.png")));
        popupStage.getIcons().add(icon);
        popupStage.setOnCloseRequest(event -> popupStage.hide());
    }
}
