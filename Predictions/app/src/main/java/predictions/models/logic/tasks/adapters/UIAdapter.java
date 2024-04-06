package predictions.Models.logic.tasks.adapters;

import javafx.application.Platform;

import java.util.function.BiConsumer;

public class UIAdapter {
    private  final  BiConsumer<Integer,String> setQueueImageTile;
    private final BiConsumer<String,Integer> setStopSimulationErrorText;
    public UIAdapter(
            BiConsumer<Integer,String> setQueueImageTile, BiConsumer<String,Integer> setStopSimulationErrorText) {
        this.setQueueImageTile = setQueueImageTile;
        this.setStopSimulationErrorText = setStopSimulationErrorText;
    }

    public  void setSetStopSimulationErrorText(String errorText,Integer simulationID){
        Platform.runLater(
                ()->setStopSimulationErrorText.accept(errorText,simulationID)
        );
    }
    public  void setSetQueueImageTile(Integer simulationID,String status){
        Platform.runLater(
                ()-> setQueueImageTile.accept(simulationID,status)
        );
    }

}
