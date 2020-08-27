package lin.louis.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReplaceAllRegex {

    @Test
    void replaceAll() {
        Pattern pattern = Pattern.compile("([a-z]+)([0-9]+)");

        String foobar = "12345foobar6789popo";

        Matcher matcher = pattern.matcher(foobar);

        String result = matcher.replaceAll("---");

        assertEquals("12345---popo", result);
    }
}
