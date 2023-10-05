import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class HippodromeTest {
    private static final String HORSE_NOT_NULL = "Horses cannot be null.";
    private static final String HORSE_NOT_EMPTY = "Horses cannot be empty.";
    private static final int NUM_ITERATIONS = 50;
    private static final int NUM_OF_HORSES = 30;

    @Test
    @DisplayName("Constructor: passing null, checking - exception")
    void Hippodrome_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }

    @Test
    @DisplayName("Constructor: passing null, checking - exception message")
    void Hippodrome_null_message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals(HORSE_NOT_NULL, exception.getMessage());
    }

    @Test
    @DisplayName("Constructor: passing empty list, checking - exception")
    void Hippodrome_empty_list() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });
    }

    @Test
    @DisplayName("Constructor: passing empty list, checking - exception message")
    void Hippodrome_empty_list_message() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });
        assertEquals(HORSE_NOT_EMPTY, exception.getMessage());
    }

    @Test
    @DisplayName("Checking getHorses() method")
    void getHorses() {
        List<Horse> expectedHorsesList = new ArrayList<>();
        for (int i = 0; i < NUM_OF_HORSES; i++) {
            expectedHorsesList.add(mock(Horse.class));
        }
        List<Horse> actualHorsesList = new Hippodrome(expectedHorsesList).getHorses();
        assertIterableEquals(expectedHorsesList, actualHorsesList);
    }

    @Test
    @DisplayName("Checking move() method")
    void move() {
        Horse mockHorse = mock(Horse.class);
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            horses.add(mockHorse);
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        Mockito.verify(mockHorse, times(50)).move();
    }

    @Test
    @DisplayName("Checking getWinner() method")
    void getWinner() {
        List<Horse> horses = List.of(
                new Horse("Bucephalus", 2.4),
                new Horse("Ace of Spades", 2.5),
                new Horse("Zephyr", 2.6),
                new Horse("Blaze", 2.7),
                new Horse("Lobster", 2.8),
                new Horse("Pegasus", 2.9),
                new Horse("Cherry", 3)
        );

        Hippodrome hippodrome = new Hippodrome(horses);
        for (int i = 0; i < 100; i++) {
            hippodrome.move();
        }

        Horse horseExpectedMaxDistance = null;
        double expectedMaxDistance = 0;
        for (Horse horse : horses) {
            double distance = horse.getDistance();
            if (expectedMaxDistance < distance) {
                expectedMaxDistance = distance;
                horseExpectedMaxDistance = horse;
            }
        }

        Horse horseActualMaxDistance = hippodrome.getWinner();
        assertEquals(horseExpectedMaxDistance, horseActualMaxDistance);
    }
}