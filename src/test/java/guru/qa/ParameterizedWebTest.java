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

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ParameterizedWebTest {

    static Stream<Arguments> searchTestMethodSource() {
        return Stream.of(
                Arguments.of("Doom II",
                        "Mods - Doom II"),
                Arguments.of("grand theft auto san andreas",
                        "Mods - Grand Theft Auto: San Andreas ")
        );
    }

    @ValueSource(strings = {"Doom II", "grand theft auto: san andreas"})
    @ParameterizedTest(name = "Searching game mods on ModDB.com via ValueSource: {0}")
    void valueSourceSearchTest(String testData) {
        open("https://www.moddb.com/");
        $("#sitesearch").setValue(testData);
        $(".button").click();
        $(".gsc-thumbnail-inside")
                .shouldHave(Condition.text("Mods - " + testData));
    }

    @CsvSource(value = {
            "Doom II:Mods - Doom II",
            "grand theft auto san andreas:Mods - Grand Theft Auto: San Andreas"
            },
            delimiter = ':')
    @ParameterizedTest(name = "Searching game mods on ModDB.com via CsvSource: {0}")
    void csvSourceSearchTest(String testData, String expected) {
        open("https://www.moddb.com/");
        $("#sitesearch").setValue(testData);
        $(".button").click();
        $(".gsc-thumbnail-inside")
                .shouldHave(Condition.text(expected));
    }

    @MethodSource("searchTestMethodSource")
    @ParameterizedTest(name = "Searching game mods on ModDB.com via MethodSource: {0}")
    void methodSourceSearchTest(String testData, String expected) {
        open("https://www.moddb.com/");
        $("#sitesearch").setValue(testData);
        $(".button").click();
        $(".gsc-thumbnail-inside")
                .shouldHave(Condition.text(expected));
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }
}
