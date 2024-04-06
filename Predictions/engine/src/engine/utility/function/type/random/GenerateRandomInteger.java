package engine.utility.function.type.random;

import engine.utility.function.Function;

import java.util.Random;

public class GenerateRandomInteger implements Function {
    private Integer maxValue;
    private Integer minValue = 0;

    public GenerateRandomInteger(Integer maxValue) {
        setMaxValue(maxValue);
    }

    public GenerateRandomInteger(Integer maxValue, Integer minValue) {
        setMaxValue(maxValue);
        setMinValue(minValue);
    }

    @Override
    public String getName() {
        return "random";
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    @Override
    public Object run() {
        Random rand = new Random();
        return rand.nextInt(maxValue - minValue + 1) + minValue;
    }
}