package predictions.controllers.mutual.simulations;

import dto.response.mutual.simulations.SimulationInfoDTO;
import dto.response.mutual.simulations.SimulationsListResponseDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import predictions.controllers.mutual.MutualControllers;
import predictions.controllers.mutual.simulations.items.Simulation;
import predictions.httpclient.mutual.simulations.SimulationsListRequest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationsListController implements MutualControllers {
    private ObservableList<Simulation> simulationsList;
    private SimpleIntegerProperty simulationsVersion;

    public void setSimulationsList(ObservableList<Simulation> simulationsList) {
        this.simulationsList = simulationsList;
    }

    public void setSimulationsVersion(SimpleIntegerProperty simulationsVersion) {
        this.simulationsVersion = simulationsVersion;
    }

    public void initializeSimulationsTask() {
        ScheduledExecutorService simulationTask = Executors.newSingleThreadScheduledExecutor();
        simulationTask.scheduleAtFixedRate(() -> {
            SimulationsListRequest listRequest = new SimulationsListRequest();
            SimulationsListResponseDTO listResponseDTO = listRequest.getSimulationsList(simulationsVersion.get());

            if (listResponseDTO.getVersion() > simulationsVersion.get()) {
                listResponseDTO.getSimulationsList().forEach(this::updateObservableList);
                simulationsVersion.set(listResponseDTO.getVersion());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void updateObservableList(SimulationInfoDTO simulationInfo) {
        boolean foundSimulation = simulationsList.stream()
                .anyMatch(simulation -> simulation.simulationNameProperty().get()
                        .equals(simulationInfo.getSimulationName()));

        if (!foundSimulation) {
            Simulation newItem = new Simulation(simulationInfo.getSimulationName(), simulationInfo.getAddedDate());
            Platform.runLater(() -> simulationsList.add(newItem));
        }
    }

    public void unFillAllCircles() {
        simulationsList.forEach(Simulation::removeCircleFill);
    }
}
