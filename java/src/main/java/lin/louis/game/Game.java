package lin.louis.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public abstract class Game {
    private ByteArrayOutputStream os;

    public Game() {
        os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
    }

    protected void setInput(String... input) {
        String finalInput = "";
        for (String line : input) {
            finalInput += line + "\n";
        }
        System.setIn(new ByteArrayInputStream(finalInput.getBytes(StandardCharsets.UTF_8)));
    }

    protected String readOutput() throws UnsupportedEncodingException {
        String output = os.toString(StandardCharsets.UTF_8.name());
        // Reset so we do not have the previous line
        os.reset();
        return output;
    }
}
