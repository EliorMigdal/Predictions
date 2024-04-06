package engine.utility.function.type.evaluate;

import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

public class ExtractEntityName implements Function {
    private final String argument;

    public ExtractEntityName(String argument) {
        this.argument = argument;
    }

    @Override
    public String getName() {
        return "extractEntityName";
    }

    @Override
    public Object run() throws InvalidPropertyReference {
        StringBuilder entityName = new StringBuilder();

        for (char character : argument.toCharArray()) {
            if (character != '.') {
                entityName.append(character);
            } else {
                break;
            }
        }

        return entityName.toString();
    }
}
