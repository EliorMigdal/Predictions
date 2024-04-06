package predictions.controllers.client.executions.items;

import dto.response.mutual.executions.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import predictions.controllers.client.executions.ExecutionsController;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.httpclient.mutual.executions.*;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationItemController implements Initializable {
    @FXML private Label simulationID;
    @FXML private ImageView statusImage;
    @FXML private HBox tile;

    private final SimulationItem simulationItem;
    private final ExecutionsController parentController;
    private final ExecutionInfo executionInfo;

    public SimulationItemController(SimulationItem simulationItem) {
        this.simulationItem = simulationItem;
        this.parentController = Model.getInstance().getControllerFactory().getExecutionsController();
        this.executionInfo = simulationItem.getExecutionInfo();
        simulationItem.setMyController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.simulationID.textProperty().bind(simulationItem.simulationIDProperty());
        initializeTilePress();
    }

    private void initializeTilePress() {
        tile.setOnMousePressed(event -> {
            parentController.setSelectedID(executionInfo.getSimulationID());
            parentController.initializeGraphs();
            parentController.resetMenuItems();

            if (simulationItem.isRunning()) {
                Platform.runLater(() -> parentController.setCurrentlySelectedRunningItem(simulationItem));
            } else {
                Platform.runLater(() -> {
                    parentController.setCurrentlySelectedRunningItem(simulationItem);
                    parentController.enableEntitiesMenuButton();
                });
                singleRunTimeUpdate();
                updatePopulationLineChart();
                updateMenuItems();
            }
        });
    }

    private void singleRunTimeUpdate() {
        new Thread(() -> {
            SimulationRuntimeInfoResponseDTO runTimeInfo = new SimulationRuntimeInfoRequest().getRuntimeInfo(
                    executionInfo.getClientName(), executionInfo.getRequestID(), executionInfo.getSimulationID());

            Platform.runLater(() -> {
                parentController.setTicksValue(runTimeInfo.getTicksValue());
                parentController.setTicksPercentage(Double.parseDouble(runTimeInfo.getTicksPercentage()));
                parentController.setTimeValue(runTimeInfo.getTimeValue());
                parentController.setTimePercentage(Double.parseDouble(runTimeInfo.getTimePercentage()));
                parentController.setErrorProperty(runTimeInfo.getErrorMessage());
                parentController.updateEntitiesCount();
            });
        }).start();
    }

    public void updatePopulationLineChart() {
        new Thread(() -> {
            PopulationResultsResponseDTO populationResults = new PopulationResultsRequest().getPopulationResults(
                    simulationItem.getExecutionInfo().getClientName(),
                    simulationItem.getExecutionInfo().getRequestID(),
                    simulationItem.getExecutionInfo().getSimulationID());

            populationResults.getEntityPopulations().forEach(populationDTO ->
                    Platform.runLater(() -> parentController.generateLineChart(populationDTO)));
        }).start();
    }

    public void updateMenuItems() {
        new Thread (() -> {
            EntitiesNamesDTO entitiesNames = new EntitiesNamesRequest().getEntitiesNames(
                    simulationItem.getExecutionInfo().getSimulationName());

            entitiesNames.getEntityNames().forEach(name -> {
                MenuItem newItem = new MenuItem(name);
                newItem.setOnAction(event -> {
                    Platform.runLater(() -> parentController.setEntityMenuText(name));
                    Platform.runLater(parentController::clearPropertyMenuItems);
                    Platform.runLater(parentController::clearPropertyBarChart);
                    Platform.runLater(parentController::clearPropertyDetails);
                    generatePropertyItems(name);
                });
                Platform.runLater(() -> parentController.addNewEntityMenuItem(newItem));
            });
        }).start();
    }

    private void generatePropertyItems(String entityName) {
        Platform.runLater(parentController::enablePropertyMenuButton);
        EntityPropertiesResponseDTO entityProperties = new EntityPropertiesRequest().getEntityProperties(
                simulationItem.getExecutionInfo().getSimulationName(), entityName);

        entityProperties.getPropertyNames().forEach(propertyName -> {
            MenuItem newItem = new MenuItem(propertyName);
            newItem.setOnAction(event -> {
                Platform.runLater(() -> parentController.setPropertyMenuText(propertyName));
                Platform.runLater(parentController::clearPropertyBarChart);
                generateBarChart(entityName, propertyName);
            });
            Platform.runLater(() -> parentController.addNewPropertyMenuItem(newItem));
        });
    }

    private void generateBarChart(String entityName, String propertyName) {
        PropertyResultResponseDTO propertyResults = new PropertyResultsRequest().getPropertyResults(
                simulationItem.getExecutionInfo().getClientName(),
                simulationItem.getExecutionInfo().getRequestID(),
                simulationItem.getExecutionInfo().getSimulationID(),
                entityName,
                propertyName);

        Platform.runLater(() -> parentController.generateBarChart(propertyResults.getPropertyHistogram()));
        generatePropertyDetails(propertyResults);
    }

    private void generatePropertyDetails(PropertyResultResponseDTO propertyInfo) {
        Platform.runLater(() -> {
            parentController.setConsistencyProperty(propertyInfo.getPropertyConsistency());
            parentController.setConsistencyLabelProperty("Consistency:");
            parentController.setAverageProperty(propertyInfo.getPropertyAverage());
            if (propertyInfo.getPropertyAverage().equals("")) {
                parentController.setAverageLabelProperty("");
            } else {
                parentController.setAverageLabelProperty("Average:");
            }
        });
    }
}
