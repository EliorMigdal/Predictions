package engine.simulation.world.grid.exception;

public class NotEnoughSpace extends Exception {
    private final Integer remainingSpace;

    public NotEnoughSpace(Integer remainingSpace) {
        this.remainingSpace = remainingSpace;
    }

    @Override
    public String getMessage() {
        return "Grid has only " + remainingSpace + " places left!";
    }
}
