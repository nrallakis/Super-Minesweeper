package gr.nrallakis.superminesweeper.scenario;

import gr.nrallakis.superminesweeper.scenario.reader.parser.validations.ScenarioValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScenarioValidatorTest {
    ScenarioValidator validator = new ScenarioValidator();

    private static Stream<Arguments> invalidScenariosOfDifficulty1() {
        return Stream.of(
                Arguments.of(new Scenario(1, 9, 8, 120, false)),
                Arguments.of(new Scenario(1, 9, 12, 120, false)),
                Arguments.of(new Scenario(1, 9, 10, 110, false)),
                Arguments.of(new Scenario(1, 9, 10, 190, false)),
                Arguments.of(new Scenario(1, 9, 10, 190, true))
        );
    }

    private static Stream<Arguments> validScenariosOfDifficulty1() {
        return Stream.of(
                Arguments.of(new Scenario(1, 9, 9, 120, false)),
                Arguments.of(new Scenario(1, 9, 11, 180, false))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidScenariosOfDifficulty1")
    void is_invalid_if_difficulty_1_scenario_has_invalid_values(Scenario scenario) {
        assertFalse(validator.isValid(scenario));
    }

    @ParameterizedTest
    @MethodSource("validScenariosOfDifficulty1")
    void is_valid_if_difficulty_1_scenario_has_valid_values(Scenario scenario) {
        assertTrue(validator.isValid(scenario));
    }

    private static Stream<Arguments> invalidScenariosOfDifficulty2() {
        return Stream.of(
                Arguments.of(new Scenario(2, 16, 34, 120, false)),
                Arguments.of(new Scenario(2, 16, 46, 120, false)),
                Arguments.of(new Scenario(2, 16, 35, 239, false)),
                Arguments.of(new Scenario(2, 16, 35, 361, false))
        );
    }

    private static Stream<Arguments> validScenariosOfDifficulty2() {
        return Stream.of(
                Arguments.of(new Scenario(2, 16, 35, 240, false)),
                Arguments.of(new Scenario(2, 16, 45, 360, true))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidScenariosOfDifficulty2")
    void is_invalid_if_difficulty_2_scenario_has_invalid_values(Scenario scenario) {
        assertFalse(validator.isValid(scenario));
    }

    @ParameterizedTest
    @MethodSource("validScenariosOfDifficulty2")
    void is_valid_if_difficulty_2_scenario_has_valid_values(Scenario scenario) {
        assertTrue(validator.isValid(scenario));
    }
}
