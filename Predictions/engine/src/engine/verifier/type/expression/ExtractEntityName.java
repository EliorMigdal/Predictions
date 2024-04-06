package engine.verifier.type.expression;

public class ExtractEntityName {
    public String extractEntityName(String argument) {
        StringBuilder entityNameBuilder = new StringBuilder();

        for (char c : argument.toCharArray()) {
            if (c == '.') {
                break;
            } else {
                entityNameBuilder.append(c);
            }
        }

        if (!entityNameBuilder.toString().isEmpty()) {
            return entityNameBuilder.toString();
        } else {
            return null;
        }
    }
}
