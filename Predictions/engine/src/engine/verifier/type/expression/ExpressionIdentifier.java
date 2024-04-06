package engine.verifier.type.expression;

public class ExpressionIdentifier {
    public boolean identify(String argument) {
        return argument.startsWith("ticks")
                || argument.startsWith("evaluate")
                || argument.startsWith("percent")
                || argument.startsWith("random")
                || argument.startsWith("environment");
    }
}