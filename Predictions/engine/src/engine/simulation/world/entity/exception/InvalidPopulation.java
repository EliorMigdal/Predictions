package engine.simulation.world.entity.exception;

public class InvalidPopulation extends Exception{
    private int numOfPopulation;

    public InvalidPopulation(int numOfPopulation) {
        setNumOfPopulation(numOfPopulation);
    }

    public int getNumOfPopulation() {
        return numOfPopulation;
    }

    public void setNumOfPopulation(int numOfPopulation) {
        this.numOfPopulation = numOfPopulation;
    }

    @Override
    public String getMessage() {
        return "Population number cannot be " + getNumOfPopulation();
    }
}