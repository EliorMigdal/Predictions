package engine.verifier.type.expression;

public class StringToNumber {
    public boolean isItANumber(String argument) {
        return argument.matches("-?\\d+(\\.\\d+)?");
    }
}
