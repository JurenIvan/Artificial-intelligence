package ui.heuristics;

import ui.Inputter;
import ui.StatesMapBuilder;
import ui.algorithms.UNIFORM_COST;
import ui.structures.State;

import java.util.List;
import java.util.Map;

import static ui.StatesMapBuilder.getInitialStateNames;
import static ui.StatesMapBuilder.of;

public class OracleHeuristics implements Heuristic {

    private Map<State, Double> stateCost;

    public OracleHeuristics(Inputter inputter) {
        StatesMapBuilder statesMapBuilder = of(inputter.getDefinitionLines());

        List<State> initialStates = getInitialStateNames(inputter.getEnd(), statesMapBuilder.getInvertedStates());
        Map<State, Double> stateHeuristics = inputter.getHeuristics(statesMapBuilder.getStates());

        UNIFORM_COST uniform_cost = new UNIFORM_COST();
        uniform_cost.find(() -> initialStates, State::getTransitions, e -> false, stateHeuristics::get);
        stateCost = uniform_cost.getStateCost();
    }

    @Override
    public Double apply(State state) {
        return stateCost.get(state);
    }

    @Override
    public String name() {
        return "Oracle";
    }
}
