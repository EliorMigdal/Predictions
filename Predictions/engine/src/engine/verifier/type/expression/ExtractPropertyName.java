package engine.verifier.type.expression;

public class ExtractPropertyName {
    public String extractPropertyName(String argument) {
        StringBuilder propertyNameBuilder = new StringBuilder();
        boolean foundDot = false;

        for (char c : argument.toCharArray()) {
            if (foundDot) {
                propertyNameBuilder.append(c);
            } else if (c == '.') {
                foundDot = true;
            }
        }

        if (!propertyNameBuilder.toString().isEmpty()) {
            return propertyNameBuilder.toString();
        } else {
            return null;
        }
    }
}
