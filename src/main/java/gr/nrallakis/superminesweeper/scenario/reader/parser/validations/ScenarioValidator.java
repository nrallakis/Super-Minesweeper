package gr.nrallakis.superminesweeper.scenario.reader.parser.validations;

import gr.nrallakis.superminesweeper.scenario.Scenario;

import java.util.HashMap;
import java.util.Map;

public class ScenarioValidator {
    final Map<Integer, ScenarioValidationRules> rulesByDifficulty;

    /**
     * A scenario validator
     * @param rulesByDifficulty A map of [Difficulty, Rules] to validate the scenarios
     */
    public ScenarioValidator(Map<Integer, ScenarioValidationRules> rulesByDifficulty) {
        this.rulesByDifficulty = rulesByDifficulty;
    };

    /**
     * A scenario validator with the default validation rules
     */
    public ScenarioValidator() {
        Map<Integer, ScenarioValidationRules> validationRules = new HashMap<>();
        validationRules.put(1, new ScenarioValidationRules(9, 11, 120, 180, false));
        validationRules.put(2, new ScenarioValidationRules(35, 45, 240, 360, true));
        this.rulesByDifficulty = validationRules;
    };

    public boolean isValid(Scenario scenario) {
        if (rulesByDifficulty.containsKey(scenario.difficulty)) {
            var rules = rulesByDifficulty.get(scenario.difficulty);
            return rules.isValid(scenario);
        }
        return false;
    }
}


