package predictions.Models.logic.tasks.inprogress.entitiescount;

import firewall.FireWallService;
import firewall.request.UIRequest;
import firewall.request.exception.LoadSimulationError;
import firewall.request.exception.RequestException;
import firewall.request.type.runtime.SpecificEntityCountRequest;
import firewall.utility.exception.LoadException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import predictions.Controllers.Mutual.results.upperscreen.helpers.CardDetails;
import predictions.Models.logic.tasks.adapters.UIAdapter;
import predictions.Models.logic.threads.ThreatCountUtil;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
public class CalculateEntitiesCountTask extends Task<Boolean> {
    private final long SLEEP_TIME = 200;
    private final CountDownLatch pauseLatch = new CountDownLatch(1);

    private final Integer currSimulationID;
    private final LinkedHashMap<String, CardDetails> entityCardsMap;
    private final ArrayList<String> entitiesNames;
    private final UIAdapter uiAdapter;
    private final FireWallService appFireWall;
    private final UIRequest isRunning;
    private final UIRequest isPaused;
    private final UIRequest isQueued;
    private final UIRequest hasError;
    private final UIRequest progressManually;
    private final Map<String, Consumer<String>> entitiesBinds;

    public CalculateEntitiesCountTask(UIAdapter uiAdapter, Integer currSimulationID,
                                      ArrayList<String> entitiesNames, FireWallService appFireWall,
                                      UIRequest isRunning, UIRequest isPaused, UIRequest isQueued,
                                      UIRequest hasError, UIRequest progressManually, LinkedHashMap<String, CardDetails> entityCardsMap,
                                      Map<String, Consumer<String>> entitiesBinds) {
        this.currSimulationID = currSimulationID;
        this.uiAdapter = uiAdapter;
        this.entitiesNames = entitiesNames;
        this.appFireWall = appFireWall;
        this.isRunning = isRunning;
        this.isPaused = isPaused;
        this.isQueued = isQueued;
        this.progressManually = progressManually;
        this.entityCardsMap = entityCardsMap;
        this.hasError = hasError;
        this.entitiesBinds = entitiesBinds;
    }

    @Override
    protected Boolean call() throws Exception {
        updateMessage("Calculate entity count...");

        // Handle the case when the simulation is queued
        if ((Boolean) appFireWall.handleRequest(isQueued)) {
            uiAdapter.setSetQueueImageTile(this.currSimulationID, "Wait");
            while ((Boolean) appFireWall.handleRequest(isQueued)) {
                ThreatCountUtil.sleepForAWhile(SLEEP_TIME);
            }
            uiAdapter.setSetQueueImageTile(this.currSimulationID, "Resume");
        }

        // Start counting entities
        while ((Boolean) appFireWall.handleRequest(isRunning)) {
            ThreatCountUtil.sleepForAWhile(SLEEP_TIME);
            if (!(Boolean) appFireWall.handleRequest(isPaused) || !(Boolean)appFireWall.handleRequest(progressManually)) {
                releasePause();
                entitiesNames.forEach(entityName -> {
                    try {
                        UIRequest entityCountRequest = new SpecificEntityCountRequest(this.currSimulationID, entityName);
                        String currCardCount = (String) appFireWall.handleRequest(entityCountRequest);
                        CardDetails entityCardData = entityCardsMap.get(entityName);
                        if (entityCardData != null) {
                            String latestCount = entityCardData.getCount();
                            if (!latestCount.equals(currCardCount)) {
                                entityCardData.setCount(currCardCount);
                                // Update UI on the Jat application thread
                                updateUI(() -> entitiesBinds.get(entityName).accept(entityCardData.getCount()));
                            }
                        }
                    } catch (RequestException | LoadSimulationError | LoadException exception) {
                        throw new RuntimeException(exception);
                    }
                });
            } else{
                pauseLatch.await();

            }
        }

        // Check for error response and update UI
        Optional<String> hasErrorResponse = Optional.ofNullable((String) appFireWall.handleRequest(hasError));
        hasErrorResponse.ifPresent(err->uiAdapter.setSetStopSimulationErrorText(err,currSimulationID));

        updateMessage("Done");
        return Boolean.TRUE;
    }
    public void releasePause() {
        pauseLatch.countDown();
    }
    private void updateUI(Runnable uiUpdate) {
        if (Platform.isFxApplicationThread()) {
            uiUpdate.run();
        } else {
            Platform.runLater(uiUpdate);
        }
    }
}

