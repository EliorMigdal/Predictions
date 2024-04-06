package engine.utility.function.type.random;

import engine.utility.function.Function;

import java.util.Random;

public class GenerateRandomString implements Function {
    private final char[] legalChars = {' ', '?', '!', '_', ',', '-', '.', '(', ')', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'Q', 'Y', 'Z'};

    public GenerateRandomString() {
    }

    @Override
    public String getName() {
        return "randomString";
    }

    @Override
    public Object run() {
        Random rand = new Random();
        int MAX_LEN = 50;
        int randomSize = rand.nextInt(MAX_LEN) + 1;
        StringBuilder output = new StringBuilder();

        while (randomSize > 0) {
            output.append(legalChars[rand.nextInt(legalChars.length)]);
            randomSize--;
        }

        return output.toString();
    }
}