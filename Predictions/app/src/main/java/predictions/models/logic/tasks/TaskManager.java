package predictions.Models.logic.tasks;


import javafx.concurrent.Task;

import predictions.Controllers.Client.results.controller.ResultsController;
import predictions.Controllers.Mutual.results.upperscreen.helpers.CardDetails;
import predictions.Models.logic.tasks.adapters.UIAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TaskManager {
    private Integer simulationID;
    private ResultsController resultScreenController;
    private ArrayList<Task<Boolean>> managedTasks;

    public TaskManager(ResultsController controller) {
        this.resultScreenController = controller;
        simulationID = -1;
        this.managedTasks = new ArrayList<>();
    }

    public void setSimulationID(int simulationID) {
        this.simulationID = simulationID;
    }

    public void collectTimeProgressValue(UIAdapter uiAdapter, Consumer<Double> timeProgressDelegate, Consumer<String> timeLabelDelegate) {
//        CollectTimeProgress currentRunningTask = new CollectTimeProgress(
//                uiAdapter,
//                timeLabelDelegate,
//                timeProgressDelegate,
//                new IsSimulationHasErrorRequest(this.simulationID),
//                new TimePercentProgressRequest(this.simulationID),
//                new IsSimulationRunningStatusRequest(this.simulationID),
//                new TimeProgressRequest(this.simulationID),
//                new IsSimulationPausedRequest(this.simulationID),
//                new IsSimulationQueuedRequest(this.simulationID),
//                resultScreenController.getFireWall(),
//                this.simulationID);
//
//        runTask(currentRunningTask);
    }

    public void collectTicksProgressValue(UIAdapter uiAdapter, Consumer<Double> tickProgressDelegate, Consumer<String> ticksLabelDelegate) {
//        CollectTicksProgress currentRunningTask = new CollectTicksProgress(
//                uiAdapter,
//                tickProgressDelegate,
//                ticksLabelDelegate,
//                resultScreenController.getFireWall(),
//                new TicksPercentProgressRequest(this.simulationID),
//                new IsSimulationRunningStatusRequest(this.simulationID),
//                new TicksProgressRequest(this.simulationID),
//                new IsSimulationPausedRequest(this.simulationID),
//                new IsSimulationQueuedRequest(this.simulationID),
//                new IsSimulationHasErrorRequest(this.simulationID),
//                new IsSimulationProgressManuallyRequest(this.simulationID),
//                this.simulationID);
//
//        runTask(currentRunningTask);
    }

    public void calculateEntitiesCount(UIAdapter uiAdapter, Map<String, Consumer<String>> entitiesBinds, LinkedHashMap<String, CardDetails> entityCardMap, ArrayList<String> entityNames) {
//        CalculateEntitiesCountTask currentRunningTask = new CalculateEntitiesCountTask(
//                uiAdapter,
//                this.simulationID,
//                entityNames,
//                resultScreenController.getFireWall(),
//                new IsSimulationRunningStatusRequest(this.simulationID),
//                new IsSimulationPausedRequest(this.simulationID),
//                new IsSimulationQueuedRequest(this.simulationID),
//                new IsSimulationHasErrorRequest(this.simulationID),
//                new IsSimulationProgressManuallyRequest(this.simulationID),
//                entityCardMap,
//                entitiesBinds
//        );
//
//        runTask(currentRunningTask);
    }

    public void terminateTasks() {
        for (Task<Boolean> task : managedTasks) {
            task.cancel();
        }
    }

    public Task<Boolean> getCurrentRunningTask() {
        return getCurrentRunningTask();
    }

    private void runTask(Task<Boolean> currentRunningTask) {
//        managedTasks.add(currentRunningTask);
//
//        Runnable onFinish = () -> {
//            resultScreenController.cleanInProgressButtonSet();
//            resultScreenController.showRerunButtonSet();
//            if (currentRunningTask instanceof CollectTicksProgress) {
//                try {
//                    inProgressSimulationObject controller = resultScreenController.getInProgressObjectBySimulationID(this.simulationID);
//                    resultScreenController.convertInProgressToFinishedItem(controller);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//
//        resultScreenController.onTaskFinished(currentRunningTask, Optional.of(onFinish));
//
//        new Thread(currentRunningTask).start();
    }
}
