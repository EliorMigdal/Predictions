package engine.simulation.termination.type;

import engine.jaxb.generated.PRDBySecond;
import engine.simulation.Simulation;
import engine.simulation.termination.Termination;
import engine.simulation.termination.exception.InvalidSecondsValue;

public class ByTime implements Termination {
    private int numOfSeconds;

    public ByTime(PRDBySecond bySecond) throws InvalidSecondsValue {
        setNumOfSeconds(bySecond.getCount());
    }

    public ByTime(int bySecond) throws InvalidSecondsValue {
        setNumOfSeconds(bySecond);
    }

    public void setNumOfSeconds(int numOfSeconds) throws InvalidSecondsValue {
        if (numOfSeconds >= 1) {
            this.numOfSeconds = numOfSeconds;
        } else {
            throw new InvalidSecondsValue(numOfSeconds);
        }
    }

    public String getTerminationName() {
        return "Time";
    }

    @Override
    public Object getTerminationTerm() {
        return numOfSeconds;
    }

    @Override
    public boolean checkForTermination(Simulation simulation) {
        return (System.currentTimeMillis() - simulation.getPauseTime().getPauseDuration() - simulation.getStartTime())
                >= numOfSeconds * 1000L;
    }
}