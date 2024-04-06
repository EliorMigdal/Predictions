package engine.simulation.world.rule.activation.type;

import engine.simulation.world.rule.activation.Activation;
import engine.simulation.world.rule.activation.exception.InvalidTicksValue;

public class Ticks implements Activation {
    private Integer numOfTicks = 1;

    public Ticks() {}

    public Ticks(Integer ticks) throws InvalidTicksValue {
        setNumOfTicks(ticks);
    }

    public void setNumOfTicks(Integer numOfTicks) throws InvalidTicksValue {
        if (numOfTicks >= 1) {
            this.numOfTicks = numOfTicks;
        } else {
            throw new InvalidTicksValue(numOfTicks);
        }
    }

    @Override
    public boolean checkForActivation(Integer tickNumber)  {
        return tickNumber % numOfTicks == 0;
    }

    @Override
    public Object getActivationTerm() {
        return this.numOfTicks;
    }

    public String getTermName() {
        return "Ticks";
    }
}