package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

public class ParameterizedWebTest {

    static Stream<Arguments> searchTestMethodSource() {
        return Stream.of(
                Arguments.of("Doom II",
                        "Mods - Doom II"),
                Arguments.of("grand theft auto san andreas",
                        "Mods - Grand Theft Auto: San Andreas ")
        );
    }

    @CsvSource(value = {
            "Doom II:Mods - Doom II",
            "grand theft auto san andreas:Mods - Grand Theft Auto: San Andreas"
            },
            delimiter = ':')
    @ParameterizedTest(name = "Searching game mods on ModDB.com via CsvSource: {0}")
    void csvSourceSearchTest(String testData, String expected) {
        Selenide.open("https://www.moddb.com/");
        Selenide.$("#sitesearch").setValue(testData);
        Selenide.$(".button").click();
        Selenide.$$(".gsc-thumbnail-inside")
                .first()
                .shouldHave(Condition.text(expected));
    }

    @MethodSource("searchTestMethodSource")
    @ParameterizedTest(name = "Searching game mods on ModDB.com via MethodSource: {0}")
    void methodSourceSearchTest(String testData, String expected) {
        Selenide.open("https://www.moddb.com/");
        Selenide.$("#sitesearch").setValue(testData);
        Selenide.$(".button").click();
        Selenide.$$(".gsc-thumbnail-inside")
                .first()
                .shouldHave(Condition.text(expected));
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }
}
