package predictions.controllers.client.initialize.popup;

import dto.response.client.init.InitialEnvironmentsResponseDTO;
import dto.response.client.init.InitialPopulationResponseDTO;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import predictions.controllers.client.executions.ExecutionsController;
import predictions.controllers.client.executions.items.SimulationItem;
import predictions.controllers.client.initialize.InitializationController;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.controllers.client.initialize.popup.items.EntityListItem;
import predictions.controllers.client.initialize.popup.items.EnvironmentListItem;
import predictions.controllers.client.initialize.popup.items.factories.EntityItemFactory;
import predictions.controllers.client.initialize.popup.items.factories.EnvironmentItemFactory;
import predictions.httpclient.client.executions.RunSimulation;
import predictions.httpclient.client.init.GetInitialEnvironmentValues;
import predictions.httpclient.client.init.GetInitialPopulations;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {
    @FXML private Button changeButton;
    @FXML private ListView<EntityListItem> entitiesList;
    @FXML private ListView<EnvironmentListItem> environmentList;
    @FXML private Button startButton;

    private final ObservableList<EntityListItem> entityListItemObservableList = FXCollections.observableArrayList();
    private final ObservableList<EnvironmentListItem> environmentListItemObservableList = FXCollections.observableArrayList();

    private ExecutionInfo executionInfo;
    private Stage currentStage;
    private InitializationController initializationController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeEntitiesList();
        initializeEnvironmentList();
        initializeChangeButton();
        initializeStartButton();
    }

    private void initializeEntitiesList() {
        entitiesList.setItems(entityListItemObservableList);
        ObjectProperty<Callback<ListView<EntityListItem>, ListCell<EntityListItem>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new EntityItemFactory());
        entitiesList.cellFactoryProperty().bind(cellFactoryProperty);
    }

    private void initializeEnvironmentList() {
        environmentList.setItems(environmentListItemObservableList);
        ObjectProperty<Callback<ListView<EnvironmentListItem>, ListCell<EnvironmentListItem>>> cellFactoryProperty =
                new SimpleObjectProperty<>(param -> new EnvironmentItemFactory());
        environmentList.cellFactoryProperty().bind(cellFactoryProperty);
    }

    public void updateEntitiesList() {
        entityListItemObservableList.add(new EntityListItem("Entity Name", "Population"));
        new Thread(() -> {
            InitialPopulationResponseDTO initialPopulations = new GetInitialPopulations().getInitialPopulations(
                    executionInfo.getClientName(), executionInfo.getRequestID(), executionInfo.getSimulationID());
            initialPopulations.getPopulations().forEach(entity ->
                    Platform.runLater(() -> entityListItemObservableList.add(new EntityListItem(
                    entity.getEntityName(), entity.getInitialPopulation().toString()))));
        }).start();
    }

    public void updateEnvironmentsList() {
        environmentListItemObservableList.add(new EnvironmentListItem("Property Name", "Value"));
        new Thread(() -> {
            InitialEnvironmentsResponseDTO initialPropertyValues = new GetInitialEnvironmentValues()
                    .getInitialEnvironmentValue(
                    executionInfo.getClientName(), executionInfo.getRequestID(), executionInfo.getSimulationID());
            initialPropertyValues.getEnvironments().forEach(environmentProperty ->
                    Platform.runLater(() -> environmentListItemObservableList.add(new EnvironmentListItem(
                    environmentProperty.getPropertyName(), environmentProperty.getPropertyValue()))));
        }).start();
    }

    private void initializeChangeButton() {
        changeButton.setOnAction(event -> this.currentStage.close());
    }

    private void initializeStartButton() {
        startButton.setOnAction(event -> {
            new RunSimulation().runSimulation(executionInfo.getClientName(), executionInfo.getRequestID(),
                    executionInfo.getSimulationID());
            this.currentStage.close();
            Model.getInstance().getControllerFactory().getClientMenuController().showSimulationResults();
            createNewSimulationItem();
        });
    }

    private void createNewSimulationItem() {
        ExecutionsController executionsController = Model.getInstance().getControllerFactory().getExecutionsController();
        executionsController.setSelectedID(executionInfo.getSimulationID());
        executionsController.initializeGraphs();
        executionsController.resetMenuItems();

        initializationController.clearAllLayouts();
    }

    public void setExecutionInfo(ExecutionInfo executionInfo) {
        this.executionInfo = executionInfo;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setInitializationController(InitializationController initializationController) {
        this.initializationController = initializationController;
    }
}
