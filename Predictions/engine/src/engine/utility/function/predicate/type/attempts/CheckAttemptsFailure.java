package engine.utility.function.predicate.type.attempts;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.predicate.Predicate;

import java.util.Map;

public class CheckAttemptsFailure implements Predicate {
    private final Map<Integer, Boolean> attemptsMap;

    public CheckAttemptsFailure(Map<Integer, Boolean> attemptsMap) {
        this.attemptsMap = attemptsMap;
    }

    @Override
    public boolean run()
            throws InvalidPropertyReference, IncompatibleTypes {
        for (Integer key : attemptsMap.keySet()) {
            if (attemptsMap.get(key)) {
                return true;
            }
        }

        return false;
    }
}
