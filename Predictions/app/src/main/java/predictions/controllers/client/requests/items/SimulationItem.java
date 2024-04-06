package predictions.controllers.client.requests.items;


public class SimulationItem {
    private final String SimulationName;

    public SimulationItem(String name) {

        this.SimulationName = name;
    }


    public String getName() {
        return SimulationName;
    }

}
