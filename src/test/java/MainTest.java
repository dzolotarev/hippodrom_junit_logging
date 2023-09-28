import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.OutputStream;
import java.io.PrintStream;

class MainTest {
    @Test
    @Disabled
    @DisplayName("Check the execution time in the main method (< 22 sec)")
    @Timeout(value = 22)
    void timeOutTest() {
        PrintStream consoleStream = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        try {
            Main.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.setOut(consoleStream);
        }
    }
}