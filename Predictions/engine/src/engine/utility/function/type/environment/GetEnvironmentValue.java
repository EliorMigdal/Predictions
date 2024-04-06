package engine.utility.function.type.environment;

import engine.simulation.world.environment.Environment;
import engine.utility.function.Function;

public class GetEnvironmentValue implements Function {
    private final static String name = "environment";
    private String argument;
    private final Environment environment;

    public GetEnvironmentValue(String argument, Environment environment) {
        setArgument(argument);
        this.environment = environment;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public Object run() {
        return environment.getPropertyValue(argument);
    }
}