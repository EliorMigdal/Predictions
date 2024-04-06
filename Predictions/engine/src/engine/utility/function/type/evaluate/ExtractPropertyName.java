package engine.utility.function.type.evaluate;

import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

public class ExtractPropertyName implements Function {
    private final String argument;

    public ExtractPropertyName(String argument) {
        this.argument = argument;
    }

    @Override
    public String getName() {
        return "extractPropertyName";
    }

    @Override
    public Object run() throws InvalidPropertyReference {
        StringBuilder propertyName = new StringBuilder();
        boolean foundDot = false;

        for (char character : argument.toCharArray()) {
            if (character != '.' && foundDot) {
                propertyName.append(character);
            } else if (character == '.') {
                foundDot = true;
            }
        }

        return propertyName.toString();
    }
}
