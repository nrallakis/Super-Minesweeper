package gr.nrallakis.superminesweeper.scenario;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScenarioValidatorTest {

    private static Stream<Arguments> invalidScenarios() {
        return Stream.of(
                Arguments.of(new EasyScenario(8, 120)),
                Arguments.of(new EasyScenario(12, 120)),
                Arguments.of(new EasyScenario(9, 110)),
                Arguments.of(new EasyScenario(9, 190)),
                Arguments.of(new HardScenario(  34, 240, false)),
                Arguments.of(new HardScenario(  35, 230, false)),
                Arguments.of(new HardScenario( 35, 370, false)),
                Arguments.of(new HardScenario( 46, 240, false))
        );
    }
    @ParameterizedTest
    @MethodSource("invalidScenarios")
    void is_invalid_if_scenario_has_invalid_values(Scenario scenario) {
        assertFalse(scenario.isValid());
    }

    private static Stream<Arguments> validScenariosOfDifficulty1() {
        return Stream.of(
                Arguments.of(new EasyScenario(9, 120)),
                Arguments.of(new EasyScenario(11, 180)),
                Arguments.of(new HardScenario(35, 240, false)),
                Arguments.of(new HardScenario(45, 360, true))

        );
    }

    @ParameterizedTest
    @MethodSource("validScenariosOfDifficulty1")
    void is_valid_if_scenario_has_valid_values(Scenario scenario) {
        assertTrue(scenario.isValid());
    }
}
