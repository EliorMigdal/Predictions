package engine.utility.function.type.random;

import engine.utility.function.Function;

import java.util.Random;

public class GenerateRandomFloat implements Function {
    private Float maxValue;
    private Float minValue = 0F;

    public GenerateRandomFloat(Integer maxValue) {
        setMaxValue(maxValue.floatValue());
    }

    public GenerateRandomFloat(Float maxValue, Float minValue) {
        setMaxValue(maxValue);
        setMinValue(minValue);
    }

    public GenerateRandomFloat(String maxValue)
            throws NumberFormatException {
        this.maxValue = Float.parseFloat(maxValue);
    }

    @Override
    public String getName() {
        return "random";
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    @Override
    public Object run() {
        Random rand = new Random();
        return (rand.nextFloat() * (maxValue - minValue)) + minValue;
    }
}