import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class MainTest {
    @Test
//    @Disabled
    @DisplayName("Check the execution time in the main method (< 22 sec)")
//    @Timeout(value = 22)
    void timeOutTest() {
        String[] args = new String[]{"arg1", "arg2"};
        assertTimeoutPreemptively(java.time.Duration.ofSeconds(22), () -> {
            PrintStream consoleStream = System.out;
            System.setOut(new PrintStream(OutputStream.nullOutputStream()));
            try {
                Main.main(args);
            } finally {
                System.setOut(consoleStream);
            }
        });
    }
}