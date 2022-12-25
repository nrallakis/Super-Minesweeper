package gr.nrallakis.superminesweeper.game.scenario;

import java.util.HashMap;
import java.util.Map;

public class ScenarioFactory {
    private final Map<Integer, ScenarioRules> rulesMap;

    public ScenarioFactory() {
        rulesMap = new HashMap<>();
        rulesMap.put(1, new ScenarioRules(1, 9, 9, 11, 120, 180, false));
        rulesMap.put(2, new ScenarioRules(2, 16, 35, 45, 240, 360, true));
    }

    public ScenarioRules getRules(int difficulty) {
        var rules = rulesMap.get(difficulty);
        if (rules == null) return rulesMap.get(2);
        return rules;
    }

    public Scenario buildScenario(int difficulty, int minesCount, int totalTime, boolean hasSuperMine) {
        var rules = getRules(difficulty);
        return new Scenario(minesCount, totalTime, hasSuperMine, rules);
    }
}
