package predictions.Models.logic.tasks.inprogress.time;

import firewall.FireWallService;
import firewall.request.UIRequest;
import javafx.application.Platform;
import javafx.concurrent.Task;
import predictions.Models.logic.tasks.adapters.UIAdapter;
import predictions.Models.logic.threads.ThreatCountUtil;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class CollectTimeProgress extends Task<Boolean> {
    private final int SLEEP_TIME = 200;
    private final CountDownLatch pauseLatch = new CountDownLatch(1);
    private final Integer currSimulationID;

    private final Consumer<Double> timeProgress;
    private final Consumer<String> timeLabel;
    private final UIAdapter uiAdapter;
    private final FireWallService appFireWall;
    private final UIRequest timePercentReq;
    private final UIRequest isRunning;
    private final UIRequest timeReq;
    private final UIRequest isPaused;
    private final UIRequest isQueued;
    private final UIRequest hasError;

    public CollectTimeProgress(UIAdapter uiAdapter, Consumer<String> timeLabel, Consumer<Double> timeProgress,
                               UIRequest hasError, UIRequest timePercentReq, UIRequest isRunning, UIRequest timeReq, UIRequest isPaused, UIRequest isQueued, FireWallService appFireWall, Integer currSimulationID) {
        this.uiAdapter = uiAdapter;
        this.timeProgress = timeProgress;
        this.timeLabel = timeLabel;
        this.hasError = hasError;
        this.appFireWall = appFireWall;
        this.timePercentReq = timePercentReq;
        this.isRunning = isRunning;
        this.timeReq = timeReq;
        this.isPaused = isPaused;
        this.isQueued = isQueued;
        this.currSimulationID = currSimulationID;
    }

    @Override
    protected Boolean call() throws Exception {
        updateMessage("Gathering time progress...");

        // Handle the case when the simulation is queued
        while ((Boolean) appFireWall.handleRequest(isQueued)) {
            ThreatCountUtil.sleepForAWhile(SLEEP_TIME);
        }

        // Start collecting time progress
        while ((Boolean) appFireWall.handleRequest(isRunning)) {
            ThreatCountUtil.sleepForAWhile(SLEEP_TIME);
            if (!(Boolean) appFireWall.handleRequest(isPaused)) {
                releasePause();
                // Collect time progress data
                Optional<Double> currSimulationPercentTime = Optional.ofNullable((Double) appFireWall.handleRequest(timePercentReq));
                String currSimulationTime = ((String) appFireWall.handleRequest(timeReq));

                if (currSimulationPercentTime.isPresent()) {
                    grabTimeData(currSimulationPercentTime.get(), currSimulationTime);
                } else {
                    grabTimeData(0D, currSimulationTime);
                }
            } else {
                pauseLatch.await();
            }
        }
        releasePause();

        // Collect time progress data one final time
        Optional<Double> currSimulationPercentTime = Optional.ofNullable((Double) appFireWall.handleRequest(timePercentReq));
        String currSimulationTime = ((String) appFireWall.handleRequest(timeReq));

        if (currSimulationPercentTime.isPresent()) {
            grabTimeData(currSimulationPercentTime.get(), currSimulationTime);
        } else {
            grabTimeData(0D, currSimulationTime);
        }

        // Check for error response and update UI
        Optional<String> hasErrorResponse = Optional.ofNullable((String) appFireWall.handleRequest(hasError));
        hasErrorResponse.ifPresent(err->uiAdapter.setSetStopSimulationErrorText(err,currSimulationID));

        updateMessage("Done");
        return Boolean.TRUE;
    }

    private void grabTimeData(Double currSimulationPercentTime, String currSimulationTime) {
        Platform.runLater(() -> {
            timeProgress.accept(currSimulationPercentTime);
            timeLabel.accept(currSimulationTime);
        });
    }

    private void releasePause() {
        pauseLatch.countDown();
    }


}
