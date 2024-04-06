package engine.verifier.type.grid.exception;

public class InvalidGrid extends Exception {
    private final Integer YAxis;
    private final Integer XAxis;

    public InvalidGrid(Integer YAxis, Integer XAxis) {
        this.YAxis = YAxis;
        this.XAxis = XAxis;
    }

    @Override
    public String getMessage() {
        return "Invalid grid: got size of " + YAxis+"x"+XAxis + " should be (10-100)x(10-100)!";
    }
}
