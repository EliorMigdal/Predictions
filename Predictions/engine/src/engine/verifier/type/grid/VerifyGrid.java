package engine.verifier.type.grid;

import engine.jaxb.generated.PRDWorld;
import engine.verifier.type.grid.exception.InvalidGrid;

public class VerifyGrid {
    public void verifyGrid(PRDWorld.PRDGrid grid)
            throws InvalidGrid {
        if (grid.getColumns() < 10 || grid.getColumns() > 100 || grid.getRows() < 10 || grid.getRows() > 100) {
            throw new InvalidGrid(grid.getColumns(), grid.getRows());
        }
    }
}
