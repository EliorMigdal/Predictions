package engine.simulation.world.rule.activation.exception;

public class InvalidTicksValue extends Exception {
    private int ticks;

    public InvalidTicksValue(int ticks) {
        setTicks(ticks);
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public String getMessage() {
        return "Invalid ticks value, of -" + getTicks();
    }
}