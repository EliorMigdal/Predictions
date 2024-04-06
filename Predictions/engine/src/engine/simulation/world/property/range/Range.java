package engine.simulation.world.property.range;

import java.io.Serializable;

public interface Range extends Cloneable, Serializable {
    Object getMinValue();
    void setMinValue(Object minValue);
    Object getMaxValue();
    void setMaxValue(Object maxValue);
    Range clone() throws CloneNotSupportedException;
    String toString();
}