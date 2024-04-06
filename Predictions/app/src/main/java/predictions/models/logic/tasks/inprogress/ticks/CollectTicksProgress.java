package predictions.Models.logic.tasks.inprogress.ticks;

import firewall.FireWallService;
import firewall.request.UIRequest;
import javafx.application.Platform;
import javafx.concurrent.Task;
import predictions.Models.logic.tasks.adapters.UIAdapter;
import predictions.Models.logic.threads.ThreatCountUtil;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class CollectTicksProgress extends Task<Boolean> {
    private final long SLEEP_TIME = 200;
    private final CountDownLatch pauseLatch = new CountDownLatch(1);
    private final Integer currSimulationID;

    private final UIAdapter uiAdapter;
    private final Consumer<Double> ticksProgress;
    private final Consumer<String> ticksLabel;
    private final FireWallService appFireWall;
    private final UIRequest ticksPercentReq;
    private final UIRequest isRunning;
    private final UIRequest ticksReq;
    private final UIRequest isPaused;
    private final UIRequest isQueued;
    private final UIRequest hasError;
    private final UIRequest progressManually;


    public CollectTicksProgress(UIAdapter uiAdapter, Consumer<Double> ticksProgress, Consumer<String> ticksLabel,
                                FireWallService appFireWall, UIRequest ticksPercentReq,
                                UIRequest isRunning, UIRequest ticksReq, UIRequest isPaused, UIRequest isQueued, UIRequest hasError, UIRequest progressManually, Integer currSimulationID) {
        this.uiAdapter = uiAdapter;
        this.ticksProgress = ticksProgress;
        this.ticksLabel = ticksLabel;
        this.appFireWall = appFireWall;
        this.ticksPercentReq = ticksPercentReq;
        this.isRunning = isRunning;
        this.ticksReq = ticksReq;
        this.isPaused = isPaused;
        this.isQueued = isQueued;
        this.hasError = hasError;
        this.progressManually = progressManually;
        this.currSimulationID = currSimulationID;
    }

    @Override
    protected Boolean call() throws Exception {
        updateMessage("Gathering ticks progress...");

        // Handle the case when the simulation is queued
        while ((Boolean) appFireWall.handleRequest(isQueued)) {
            ThreatCountUtil.sleepForAWhile(SLEEP_TIME);
        }

        // Start collecting ticks progress
        while ((Boolean) appFireWall.handleRequest(isRunning)) {
            ThreatCountUtil.sleepForAWhile(SLEEP_TIME);
            if (!(Boolean) appFireWall.handleRequest(isPaused) || !(Boolean)appFireWall.handleRequest(progressManually)) {
                releasePause();

                // Collect ticks progress data
                Optional<Double> currSimulationTickPercent = Optional.ofNullable((Double) appFireWall.handleRequest(ticksPercentReq));
                String currSimulationTick = String.valueOf(appFireWall.handleRequest(ticksReq));

                if (currSimulationTickPercent.isPresent()) {
                    grabTicksData(currSimulationTickPercent.get(), currSimulationTick);
                } else {
                    grabTicksData(0D, currSimulationTick);
                }
            } else{
                pauseLatch.await();
            }
        }
        releasePause();

        // Collect ticks progress data one final time
        Optional<Double> currSimulationTickPercent = Optional.ofNullable((Double) appFireWall.handleRequest(ticksPercentReq));
        String currSimulationTick = String.valueOf(appFireWall.handleRequest(ticksReq));

        if (currSimulationTickPercent.isPresent()) {
            grabTicksData(currSimulationTickPercent.get(), currSimulationTick);
        } else {
            grabTicksData(0D, currSimulationTick);
        }

        // Check for error response and update UI
        Optional<String> hasErrorResponse = Optional.ofNullable((String) appFireWall.handleRequest(hasError));
        hasErrorResponse.ifPresent(err->uiAdapter.setSetStopSimulationErrorText(err,currSimulationID));

        updateMessage("Done");
        return Boolean.TRUE;
    }

    private void grabTicksData(Double currSimulationTickPercent, String currSimulationTick) {
        Platform.runLater(() -> {
            ticksLabel.accept(currSimulationTick);
            ticksProgress.accept(currSimulationTickPercent);
        });
    }

    private void releasePause() {
        pauseLatch.countDown();
    }


}
