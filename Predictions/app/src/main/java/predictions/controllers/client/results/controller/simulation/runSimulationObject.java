package predictions.Controllers.Client.results.controller.simulation;

import javafx.beans.property.SimpleStringProperty;
import predictions.Controllers.Client.results.controller.ResultsController;
import predictions.Controllers.Client.results.controller.simulation.properties.timeTicksProgressPropertiesObject;
import predictions.Models.logic.tasks.TaskManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class runSimulationObject {

    private final TaskManager taskManager;
    private final timeTicksProgressPropertiesObject propertiesBinds;
    private final Map<String, SimpleStringProperty> entityCountBinds;

    public runSimulationObject(ResultsController controller) {
        taskManager = new TaskManager(controller);
        this.entityCountBinds = new LinkedHashMap<>();
//        controller.getEntitiesNames().forEach(entName->{
//            this.entityCountBinds.put(entName,new SimpleStringProperty("0"));
//        });
//        taskManager.setSimulationID(controller.getCurrentSimulationID());
        propertiesBinds = new timeTicksProgressPropertiesObject();
    }

    public SimpleStringProperty getEntityCountBindsByName(String entName) {
        return entityCountBinds.get(entName);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public Map<String, SimpleStringProperty> getEntityCountBinds() {
        return entityCountBinds;
    }

    public timeTicksProgressPropertiesObject getPropertiesBinds() {
        return propertiesBinds;
    }

}
