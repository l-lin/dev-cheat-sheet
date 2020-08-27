package lin.louis.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindRegex {

    @Test
    void find() {
        Pattern pattern = Pattern.compile("([a-z]+)([0-9]+)");

        String foobar = "foobar6789";

        Matcher matcher = pattern.matcher(foobar);

        assertTrue(matcher.matches());
        assertEquals(2, matcher.groupCount());
        assertEquals(foobar, matcher.group(0));
        assertEquals("foobar", matcher.group(1));
        assertEquals("6789", matcher.group(2));
        assertFalse(matcher.find());
    }
}
