import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class HorseTest {
    private static final double TEST_SPEED = 101;
    private static final double TEST_DISTANCE = 1001;
    private static final String TEST_NAME = "Lucky";
    private static final String NAME_NOT_NULL = "Name cannot be null.";
    private static final String NAME_NOT_BLANK = "Name cannot be blank.";
    private static final String SPEED_NOT_NEGATIVE = "Speed cannot be negative.";
    private static final String DISTANCE_NOT_NEGATIVE = "Distance cannot be negative.";

    @Test
    @DisplayName("Constructor: passing null to the constructor's name, checking - exception")
    void Horse_name_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, TEST_SPEED);
        });
    }

    @Test
    @DisplayName("Constructor: passing null to the constructor's name, checking - exception message")
    void Horse_name_null_and_message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, TEST_SPEED);
        });
        assertEquals(NAME_NOT_NULL, exception.getMessage());
    }

    /*
    '\u0020' - SPACE.
    '\u0009' - HORIZONTAL TABULATION.
    '\u000B' - VERTICAL TABULATION.
    '\u000C' - FORM FEED.
    '\u001C' - FILE SEPARATOR.
    '\u001D' - GROUP SEPARATOR.
    '\u001E' - RECORD SEPARATOR.
    '\u001F' - UNIT SEPARATOR.
     */
    @DisplayName("Constructor: passing empty string and all whitespaces to the constructor's name, checking - exception")
    @ParameterizedTest
    @ValueSource(strings = {"", "\u0020", "\u0009", "\u000B", "\u000C", "\u001C", "\u001D", "\u001E", "\u001E", "\u001F"})
    void Horse_name_empty_string_or_whitespace(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, TEST_SPEED, TEST_DISTANCE);
        });
    }

    @ParameterizedTest
    @DisplayName("Constructor: passing empty string and all whitespaces to the constructor's name, checking - exception message")
    @ValueSource(strings = {"", "\u0020", "\u0009", "\u000B", "\u000C", "\u001C", "\u001D", "\u001E", "\u001E", "\u001F"})
    void Horse_name_empty_string_or_whitespace_and_message(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, TEST_SPEED, TEST_DISTANCE);
        });
        assertEquals(NAME_NOT_BLANK, exception.getMessage());
    }

    @Test
    @DisplayName("Constructor: passing negative speed into the constructor, checking - exception")
    void Horse_speed_negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(TEST_NAME, -1);
        });
    }

    @Test
    @DisplayName("Constructor: passing negative speed into the constructor, checking - exception message")
    void Horse_speed_negative_and_message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(TEST_NAME, -1);
        });
        assertEquals(SPEED_NOT_NEGATIVE, exception.getMessage());
    }

    @Test
    @DisplayName("Constructor: passing negative distance into the constructor, checking - exception")
    void Horse_distance_negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(TEST_NAME, TEST_SPEED, -1);
        });
    }

    @Test
    @DisplayName("Constructor: passing negative distance into the constructor, checking - exception message")
    void Horse_distance_negative_and_message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(TEST_NAME, TEST_SPEED, -1);
        });
        assertEquals(DISTANCE_NOT_NEGATIVE, exception.getMessage());
    }

    @Test
    @DisplayName("Checking getName() method")
    void getName() {
        String nameActual = new Horse(TEST_NAME, 0).getName();
        assertEquals(TEST_NAME, nameActual);
    }

    @Test
    @DisplayName("Checking getSpeed() method")
    void getSpeed() {
        double speedActual = new Horse(TEST_NAME, TEST_SPEED).getSpeed();
        assertEquals(TEST_SPEED, speedActual);
    }

    @Test
    @DisplayName("Checking getDistance() method")
    void getDistance() {
        double distanceActual = new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE).getDistance();
        double distanceZero = new Horse(TEST_NAME, TEST_SPEED).getDistance();
        assertAll(
                () -> assertEquals(TEST_DISTANCE, distanceActual),
                () -> assertEquals(0, distanceZero));
    }

    @Test
    @DisplayName("Checking call static method getRandomDouble() in non-static move() method")
    void move() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }
}