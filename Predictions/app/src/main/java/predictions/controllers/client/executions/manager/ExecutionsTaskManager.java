package predictions.controllers.client.executions.manager;

import dto.response.client.executions.AllExecutionsResponseDTO;
import dto.response.mutual.executions.EntitiesCountResponseDTO;
import dto.response.mutual.executions.EntityCountDTO;
import dto.response.mutual.executions.SimulationRuntimeInfoResponseDTO;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import predictions.controllers.client.executions.ExecutionsController;
import predictions.controllers.client.executions.items.SimulationItem;
import predictions.controllers.client.initialize.items.ExecutionInfo;
import predictions.controllers.mutual.executions.items.EntityCountCellController;
import predictions.controllers.mutual.executions.items.EntityItem;
import predictions.httpclient.client.executions.AllExecutions;
import predictions.httpclient.mutual.executions.EntitiesCountRequest;
import predictions.httpclient.mutual.executions.SimulationRuntimeInfoRequest;
import predictions.manager.TaskManager;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutionsTaskManager implements TaskManager {
    private final ExecutionsController executionsController;

    private ScheduledExecutorService simulationsListUpdater;
    private ScheduledExecutorService runTimeInfoUpdater;

    public ExecutionsTaskManager(ExecutionsController executionsController) {
        this.executionsController = executionsController;
    }

    private void initializeListUpdater() {
        this.simulationsListUpdater = Executors.newSingleThreadScheduledExecutor();
        simulationsListUpdater.scheduleAtFixedRate(() -> {
            AllExecutionsResponseDTO allExecutions = new AllExecutions().getAllExecutions(executionsController.getClientName(),
                    executionsController.getSimulationsVersion());

            allExecutions.getExecutions().forEach(executionDTO -> {
                Optional<SimulationItem> matchingItem = executionsController.getAllSimulationItems().stream()
                        .filter(simulationItem -> simulationItem.getExecutionInfo().getSimulationID().equals(executionDTO.getSimulationID()))
                        .findFirst();

                if (matchingItem.isPresent()) {
                    Platform.runLater(() -> matchingItem.get().setRunning(executionDTO.getRunning()));

                    if (executionDTO.getSimulationID().equals(executionsController.getCurrentlySelectedID()) &&
                    executionsController.getCurrentlySelectedItem() != matchingItem.get()) {
                        if (matchingItem.get().isRunning()) {
                            Platform.runLater(() -> executionsController.setCurrentlySelectedRunningItem(matchingItem.get()));
                        } else {
                            Platform.runLater(() -> executionsController.setCurrentlySelectedFinishedItem(matchingItem.get()));
                        }
                    }
                } else {
                    SimulationItem newItem = new SimulationItem(executionDTO.getSimulationID());
                    newItem.setExecutionInfo(new ExecutionInfo(executionsController.getClientName(), executionDTO.getRequestID(),
                            executionDTO.getSimulationName(), executionDTO.getSimulationID()));
                    newItem.setRunning(executionDTO.getRunning());

                    newItem.isRunningProperty().addListener(((observable, oldValue, newValue) -> {
                        if (!newValue && newItem.getExecutionInfo().getSimulationID().equals
                                (executionsController.getCurrentlySelectedRunningItem().getExecutionInfo().getSimulationID())) {
                            Platform.runLater(() -> {
                                executionsController.setCurrentlySelectedFinishedItem(newItem);
                                executionsController.initializeGraphs();
                                newItem.updateMenuItems();
                                newItem.updateLineChart();
                            });
                        }
                    }));

                    initializeEntitiesBox(newItem.getExecutionInfo());
                    Platform.runLater(() -> executionsController.getAllSimulationItems().add(newItem));

                    if (executionDTO.getSimulationID().equals(executionsController.getCurrentlySelectedID())) {
                        if (newItem.isRunning()) {
                            Platform.runLater(() -> executionsController.setCurrentlySelectedRunningItem(newItem));
                        } else {
                            Platform.runLater(() -> executionsController.setCurrentlySelectedFinishedItem(newItem));
                        }
                    }
                }
            });

            updateRunningList();
            updateFinishedList();
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void updateRunningList() {
        executionsController.getAllSimulationItems().stream()
                .filter(simulationItem -> simulationItem.isRunning() &&
                        !executionsController.getRunningSimulations().contains(simulationItem))
                .forEach(simulationItem -> Platform.runLater(() -> executionsController
                        .getRunningSimulations().add(simulationItem)));

        executionsController.getAllSimulationItems().stream()
                .filter(simulationItem -> !simulationItem.isRunning() &&
                        executionsController.getRunningSimulations().contains(simulationItem))
                .forEach(simulationItem -> Platform.runLater(() -> executionsController
                        .getRunningSimulations().remove(simulationItem)));
    }

    private void updateFinishedList() {
        executionsController.getAllSimulationItems().stream()
                .filter(simulationItem -> !simulationItem.isRunning() &&
                        !executionsController.getFinishedSimulations().contains(simulationItem))
                .forEach(simulationItem -> Platform.runLater(() -> executionsController
                        .getFinishedSimulations().add(simulationItem)));
    }

    public void initializeEntitiesBox(ExecutionInfo executionInfo) {
        EntitiesCountResponseDTO entitiesCount = new EntitiesCountRequest().getEntitiesCount(
                executionInfo.getClientName(), executionInfo.getRequestID(), executionInfo.getSimulationID());

        for (EntityCountDTO entityCount : entitiesCount.getEntitiesCount()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Mutual/results/upperscreen" +
                        "/entitypropertyDetailsCard.fxml"));

                BorderPane borderPane = loader.load();
                EntityCountCellController controller = loader.getController();
                EntityItem entityItem = new EntityItem(entityCount.getEntityName(),
                        entityCount.getEntityCount().toString(), borderPane);
                controller.setEntityItem(entityItem);
                executionInfo.addNewEntityItem(entityItem);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void initializeRunTimeTask() {
        this.runTimeInfoUpdater = Executors.newSingleThreadScheduledExecutor();
        runTimeInfoUpdater.scheduleAtFixedRate(() -> {
            if (executionsController.getCurrentlySelectedItem() != null &&
                    executionsController.getCurrentlySelectedItem().isRunning()) {
                SimulationRuntimeInfoResponseDTO runTimeInfo = new SimulationRuntimeInfoRequest().getRuntimeInfo(
                        executionsController.getCurrentlySelectedItem().getExecutionInfo().getClientName(),
                        executionsController.getCurrentlySelectedItem().getExecutionInfo().getRequestID(),
                        executionsController.getCurrentlySelectedItem().getExecutionInfo().getSimulationID());

                Platform.runLater(() -> {
                    executionsController.setTicksValue(runTimeInfo.getTicksValue());
                    executionsController.setTicksPercentage(Double.parseDouble(runTimeInfo.getTicksPercentage()));
                    executionsController.setTimeValue(runTimeInfo.getTimeValue());
                    executionsController.setTimePercentage(Double.parseDouble(runTimeInfo.getTimePercentage()));
                    executionsController.setErrorProperty(runTimeInfo.getErrorMessage());
                });

                executionsController.updateEntitiesCount();
            }
        }, 0, 300, TimeUnit.MILLISECONDS);
    }

    @Override
    public void startAllTasks() {
        initializeListUpdater();
        initializeRunTimeTask();
    }

    @Override
    public void stopAllTasks() {
        simulationsListUpdater.shutdown();
        runTimeInfoUpdater.shutdown();
    }
}
