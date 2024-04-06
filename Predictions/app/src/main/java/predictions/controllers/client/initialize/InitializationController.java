package predictions.controllers.client.initialize;

import dto.response.client.init.AllEnvPropertiesDTO;
import dto.response.mutual.executions.EntitiesNamesDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import predictions.controllers.client.initialize.items.EntityCardController;
import predictions.controllers.client.initialize.items.EnvironmentCardController;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.controllers.client.initialize.popup.PopupController;
import predictions.httpclient.client.init.GetEnvironmentProperties;
import predictions.httpclient.mutual.executions.EntitiesNamesRequest;
import predictions.models.Model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class InitializationController extends ResourceBundle implements Initializable {
    @FXML private Button clearButton;
    @FXML private VBox entitiesLayout;
    @FXML private VBox environmentLayout;
    @FXML private Button startButton;

    private ExecutionInfo executionItem;
    private final ArrayList<EntityCardController> entityControllers = new ArrayList<>();
    private final ArrayList<EnvironmentCardController> environmentControllers = new ArrayList<>();

    public InitializationController() {
        Model.getInstance().getControllerFactory().setExecutionController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeClearButton();
        initializeStartButton();
    }

    public void generateEntitiesLayout() {
        entityControllers.clear();
        entitiesLayout.getChildren().clear();
        EntitiesNamesDTO entitiesNames = new EntitiesNamesRequest().getEntitiesNames(executionItem.getSimulationName());

        for (int index = 0; index < entitiesNames.getEntityNames().size(); index++) {
            try {
                HBox currHBox = new HBox(60);
                HBox.setMargin(currHBox, new Insets(0, 10, 0, 10));
                currHBox.setAlignment(Pos.CENTER);
                entitiesLayout.getChildren().add(currHBox);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/execution" +
                        "/Views/EntityCard.fxml"));
                Node node = loader.load();

                EntityCardController controller = loader.getController();
                controller.setEntityName(entitiesNames.getEntityNames().get(index));
                controller.setExecutionItem(this.executionItem);
                entityControllers.add(controller);

                currHBox.getChildren().add(node);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void generateEnvironmentLayout() {
        environmentControllers.clear();
        environmentLayout.getChildren().clear();
        AllEnvPropertiesDTO environmentProperties = new GetEnvironmentProperties().getEnvironmentProperties(
                executionItem.getSimulationName());

        try {
            for (int index = 0; index < environmentProperties.getEnvironments().size(); index++) {
                HBox currHBox = new HBox(60);
                HBox.setMargin(currHBox, new Insets(0, 10, 0, 10));
                currHBox.setAlignment(Pos.CENTER);
                environmentLayout.getChildren().add(currHBox);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/execution" +
                        "/Views/EnvironmentCard.fxml"));
                Node node = loader.load();

                EnvironmentCardController controller = loader.getController();
                controller.setEnvironmentNameProperty(environmentProperties.getEnvironments().get(index).getPropertyName());
                controller.setEnvironmentTypeProperty(environmentProperties.getEnvironments().get(index).getPropertyType());
                controller.setRangeValueProperty(environmentProperties.getEnvironments().get(index).getPropertyRange());
                controller.setExecutionItem(this.executionItem);
                environmentControllers.add(controller);

                currHBox.getChildren().add(node);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    private void initializeClearButton() {
        clearButton.setOnAction(event -> {
            entityControllers.forEach(EntityCardController::resetPopulationValue);
            environmentControllers.forEach(EnvironmentCardController::resetEnvironmentValue);
        });
    }

    private void initializeStartButton() {
        startButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Views/" +
                        "execution/Views/StartButtonPopup.fxml"));
                Parent root = loader.load();
                Stage popUpStage = new Stage();

                PopupController controller = loader.getController();
                controller.setExecutionInfo(executionItem);
                controller.setCurrentStage(popUpStage);
                controller.setInitializationController(this);
                controller.updateEntitiesList();
                controller.updateEnvironmentsList();


                popUpStage.setScene(new Scene(root));
                popUpStage.initModality(Modality.APPLICATION_MODAL);
                popUpStage.setResizable(false);
                popUpStage.show();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public void setExecutionItem(ExecutionInfo executionItem) {
        this.executionItem = executionItem;
    }

    public void clearAllLayouts() {
        entitiesLayout.getChildren().clear();
        environmentLayout.getChildren().clear();
        entityControllers.clear();
        environmentControllers.clear();
    }

    @Override
    protected Object handleGetObject(String key) {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
