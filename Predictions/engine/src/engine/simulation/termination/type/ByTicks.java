package engine.simulation.termination.type;

import engine.jaxb.generated.PRDByTicks;
import engine.simulation.Simulation;
import engine.simulation.termination.Termination;
import engine.simulation.world.rule.activation.exception.InvalidTicksValue;

public class ByTicks implements Termination {
    private int numOfTicks = 0;

    public ByTicks(int count)
            throws InvalidTicksValue {
        setNumOfTicks(count);
    }

    public void setNumOfTicks(int numOfTicks) throws InvalidTicksValue {
        if (numOfTicks >= 1) {
            this.numOfTicks = numOfTicks;
        } else {
            throw new InvalidTicksValue(numOfTicks);
        }
    }

    public String getTerminationName() {
        return "Ticks";
    }

    @Override
    public Object getTerminationTerm() {
        return numOfTicks;
    }

    @Override
    public boolean checkForTermination(Simulation simulation) {
        return simulation.getCurrentTick() >= numOfTicks;
    }
}