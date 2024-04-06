package predictions.controllers.admin.executions.items.controllers;

import dto.response.mutual.executions.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import predictions.controllers.admin.executions.ExecutionHistoryController;
import predictions.controllers.admin.executions.items.ExecutedSimulation;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.httpclient.mutual.executions.*;
import predictions.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminExecutedCellController implements Initializable {
    @FXML private Text clientName;
    @FXML private Text requestID;
    @FXML private Text simulationID;
    @FXML private Text simulationName;
    @FXML private HBox tileBox;

    private final ExecutedSimulation executedTile;
    private final ExecutionHistoryController parentController;
    private final ExecutionInfo executionInfo;

    public AdminExecutedCellController(ExecutedSimulation executedTile) {
        this.executedTile = executedTile;
        this.parentController = Model.getInstance().getControllerFactory().getExecutionHistoryController();
        this.executionInfo = new ExecutionInfo(executedTile.clientNameProperty().get(),
                Integer.parseInt(executedTile.requestIDProperty().get()),
                executedTile.simulationNameProperty().get(),
                Integer.parseInt(executedTile.simulationIDProperty().get()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindAll();
        initializeTilePress();
    }

    private void bindAll() {
        this.clientName.textProperty().bind(executedTile.clientNameProperty());
        this.requestID.textProperty().bind(executedTile.requestIDProperty());
        this.simulationID.textProperty().bind(executedTile.simulationIDProperty());
        this.simulationName.textProperty().bind(executedTile.simulationNameProperty());
    }

    private void initializeTilePress() {
        tileBox.setOnMousePressed(event -> {
            Platform.runLater(() -> {
                parentController.initializeGraphs();
                parentController.resetMenuItems();
            });
            updateSimulationInfo();
            updatePopulationLineChart();
            updateMenuItems();
        });
    }

    private void updateSimulationInfo() {
        new Thread(() -> {
            SimulationRuntimeInfoResponseDTO runTimeInfo = new SimulationRuntimeInfoRequest().getRuntimeInfo(
                    clientName.getText(), Integer.parseInt(requestID.getText()),
                    Integer.parseInt(simulationID.getText()));

            Platform.runLater(() -> {
                parentController.setCurrentlySelectedClient(clientName.getText());
                parentController.setCurrentlySelectedID(requestID.getText());
                parentController.setTicksValue(runTimeInfo.getTicksValue());
                parentController.setTicksPercentage(Double.parseDouble(runTimeInfo.getTicksPercentage()));
                parentController.setTimeValue(runTimeInfo.getTimeValue());
                parentController.setTimePercentage(Double.parseDouble(runTimeInfo.getTimePercentage()));
                parentController.setErrorProperty(runTimeInfo.getErrorMessage());
            });

            EntitiesCountResponseDTO entitiesCount = new EntitiesCountRequest().getEntitiesCount(
                    clientName.getText(), Integer.parseInt(requestID.getText()),
                    Integer.parseInt(simulationID.getText()));

            Platform.runLater(parentController::clearEntitiesLayout);

            entitiesCount.getEntitiesCount().forEach(parentController::generateEntityCell);
        }).start();
    }

    private void updatePopulationLineChart() {
        new Thread(() -> {
            PopulationResultsResponseDTO populationResults = new PopulationResultsRequest().getPopulationResults(
                    clientName.getText(), Integer.parseInt(requestID.getText()),
                    Integer.parseInt(simulationID.getText()));

            populationResults.getEntityPopulations().forEach(parentController::generateLineChart);
        }).start();
    }

    private void updateMenuItems() {
        new Thread (() -> {
            EntitiesNamesDTO entitiesNames = new EntitiesNamesRequest().getEntitiesNames(simulationName.getText());

            entitiesNames.getEntityNames().forEach(name -> {
                MenuItem newItem = new MenuItem(name);
                newItem.setOnAction(event -> generatePropertyItems(name));
                Platform.runLater(() -> parentController.addNewEntityMenuItem(newItem));
            });
        }).start();
    }

    private void generatePropertyItems(String entityName) {
        Platform.runLater(parentController::enablePropertyMenuButton);
        EntityPropertiesResponseDTO entityProperties = new EntityPropertiesRequest().getEntityProperties(
                simulationName.getText(), entityName);

        entityProperties.getPropertyNames().forEach(propertyName -> {
            MenuItem newItem = new MenuItem(propertyName);
            newItem.setOnAction(event -> generateBarChart(entityName, propertyName));
            Platform.runLater(() -> parentController.addNewPropertyMenuItem(newItem));
        });
    }

    private void generateBarChart(String entityName, String propertyName) {
        PropertyResultResponseDTO propertyResults = new PropertyResultsRequest().getPropertyResults(
                clientName.getText(), Integer.parseInt(requestID.getText()),
                Integer.parseInt(simulationID.getText()), entityName, propertyName);

        parentController.generateBarChart(propertyResults.getPropertyHistogram());
        generatePropertyDetails(propertyResults);
    }

    private void generatePropertyDetails(PropertyResultResponseDTO propertyInfo) {
        parentController.setConsistencyProperty(propertyInfo.getPropertyConsistency());
        parentController.setConsistencyLabelProperty("Consistency:");
        parentController.setAverageProperty(propertyInfo.getPropertyAverage());
        if (propertyInfo.getPropertyAverage().equals("")) {
            parentController.setAverageLabelProperty("");
        } else {
            parentController.setAverageLabelProperty("Average:");
        }
    }
}
