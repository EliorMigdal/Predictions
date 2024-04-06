package predictions.models.logic.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExtractor {

    private final String input;
    private final String pattern;

    public StringExtractor(String input, String pattern) {
        this.input = input;
        this.pattern = pattern;
    }

    public String extract() {
        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(input);

        if (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null) {
                return matcher.group(1) + " should be " + matcher.group(2);
            } else if (matcher.group(3) != null) {
                return matcher.group(3);
            }
        }
        return null;
    }
}
