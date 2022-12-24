package gr.nrallakis.superminesweeper.scenario;

import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidDescriptionException;
import gr.nrallakis.superminesweeper.scenario.exceptions.InvalidValueException;
import gr.nrallakis.superminesweeper.scenario.parser.ScenarioParser;
import gr.nrallakis.superminesweeper.scenario.parser.ScenarioTextParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioParserTest {

    private final ScenarioParser parser;

    public ScenarioParserTest() {
        this.parser = new ScenarioTextParser();
    }

    @Test
    void parse_scenario_with_difficulty_1() throws Exception {
        String scenarioText =
                "1\n" +    // difficulty
                "11\n" +   // mines
                "120\n" +  // seconds
                "0";       // No super-mines allowed

        Scenario scenario = parser.parse(scenarioText);
        assertEquals(scenario.rules.boardSize, 9); // Difficulty 1 has 9x9 board
        assertEquals(scenario.minesCount, 11);
        assertEquals(scenario.totalTime, 120);
        assertFalse(scenario.hasSuperMine);
    }

    @Test
    void pares_scenario_with_difficulty_2() throws Exception {
        String scenarioText =
                "2\n" +    // difficulty
                "35\n" +   // mines
                "240\n" +  // seconds
                "1";       // Super-mines allowed
        var scenario = parser.parse(scenarioText);
        assertEquals(scenario.rules.boardSize, 16); // Difficulty 2 has 16x16 board
        assertEquals(scenario.minesCount, 35);
        assertEquals(scenario.totalTime, 240);
        assertTrue(scenario.hasSuperMine);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        // No lines
        "",

        // Extra line
        "test\n" +
        "2\n" +    // difficulty
        "35\n" +   // mines
        "240\n" +  // seconds
        "1",

        // One line omitted
        "35\n" +   // mines
        "240\n" +  // seconds
        "1"        // Super-mines allowed
    })
    void throws_InvalidDescriptionException_if_text_contains_more_or_less_than_4_lines(String input) {
        assertThrows(InvalidDescriptionException.class, () -> parser.parse(input));
    }

    @Test
    void throws_InvalidValueException_if_text_contains_invalid_scenario() {
        assertThrows(InvalidValueException.class, () -> {
            String invalidScenarioText =
                    "1\n" +    // difficulty
                    "9\n" +   // mines
                    "10\n" +  // seconds
                    "110";
            parser.parse(invalidScenarioText);
        });
    }
}
