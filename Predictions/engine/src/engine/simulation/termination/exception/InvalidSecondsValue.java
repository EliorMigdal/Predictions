package engine.simulation.termination.exception;

public class InvalidSecondsValue extends Exception {
    private double seconds;

    public InvalidSecondsValue(double seconds) {
        setSeconds(seconds);
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    @Override
    public String getMessage() {
        return "Invalid seconds value, of " + getSeconds();
    }
}