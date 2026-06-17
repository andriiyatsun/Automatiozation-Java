import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class TextProcessorTest {

    private TextProcessor processor;

    // @BeforeEach запобігає впливу результатів одного тесту на інший.
    @BeforeEach
    void setUp() {
        processor = new TextProcessor();
    }

    /**
     * ПРОСТИЙ ТЕСТ
     */
    @Test
    @Tag("core") // Тег для 3-го завдання (Suite)
    @DisplayName("Простий тест: Перевірка підрахунку голосних")
    void testCountVowelsSimple() {
        // Для чого: Базова перевірка логіки. Очікуємо, що в слові "Java" 2 голосні (a, a).
        int result = processor.countVowels("Java");
        assertEquals(2, result, "У слові 'Java' має бути 2 голосні літери");
    }

    // 2. ASSUMPTIONS (ІЗ ЛОГІЧНИМ СЕНСОМ)
    @Test
    @Tag("environment")
    @DisplayName("Тест із Assumption: Перевірка тільки на 64-бітній ОС")
    void testOnlyOn64BitArchitecture() {
        // Assumptions (Припущення) дозволяють ПРОПУСТИТИ (Skip) тест, якщо умова не виконалася

        String architecture = System.getProperty("os.arch");
        boolean is64Bit = architecture.contains("64");

        // якщо система не 64-бітна, тест просто засвітиться сірим (Skipped).
        assumeTrue(is64Bit, "Цей тест виконується лише на 64-бітних системах");

        // перевірка буде тільки якщо assumeTrue пройшов
        assertEquals(2, processor.countVowels("JUnit"));
    }



    /**
     * ПАРАМЕТРИЗОВАНИЙ ТЕСТ (1 СТАТИЧНИЙ ПАРАМЕТР)
     * @param text
     */
    @ParameterizedTest
    @ValueSource(strings = {"Hello!", "Stop!", "Watch out!"}) // Статичний масив з 1 параметром
    @Tag("core")
    @DisplayName("Параметризований тест (1 параметр): Наявність знаку оклику")
    void testIsExclamatory(String text) {
        assertTrue(processor.isExclamatory(text), "Рядок '" + text + "' має закінчуватися на '!'");
    }

    /**
     * ПАРАМЕТРИЗОВАНИЙ ТЕСТ
     * @param name
     * @param time
     * @param expected
     */
    @ParameterizedTest
    @CsvSource({
            "Ivan, morning, 'Good morning, Ivan!'",
            "Olena, evening, 'Good evening, Olena!'",
            "Student, afternoon, 'Good afternoon, Student!'"
    })
    @Tag("core")
    @DisplayName("Параметризований тест (набір параметрів): Генерація вітання")
    void testCreateGreeting(String name, String time, String expected) {
        assertEquals(expected, processor.createGreeting(name, time));
    }

    /**
     * ДИНАМІЧНИЙ ТЕСТ (@TestFactory)
     * @return
     */
    @TestFactory
    @Tag("dynamic")
    @DisplayName("Динамічні тести: Підрахунок голосних у колекції")
    Stream<DynamicTest> dynamicTestsForVowels() {

        List<String> words = Arrays.asList("cat", "apple", "education");

        return words.stream()
                .map(word -> DynamicTest.dynamicTest(
                        "Тест для слова: " + word, // Назва кожного згенерованого тесту
                        () -> {
                            // логіка перевірки
                            assertTrue(processor.countVowels(word) > 0, "Слово повинно мати хоча б 1 голосну");
                        }
                ));
    }
}