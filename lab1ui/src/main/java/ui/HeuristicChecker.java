package ui;

import ui.algorithms.UNIFORM_COST;
import ui.structures.State;
import ui.structures.Transition;

import java.util.List;
import java.util.Map;

public class HeuristicChecker {

    private static final String IN_FILE_HEURISTICS_NAME = "3x3_misplaced_heuristic.txt";
    private static final String IN_FILE_NAME = "3x3_puzzle.txt";

    private static final Inputter inputter = new Inputter(IN_FILE_NAME, IN_FILE_HEURISTICS_NAME);

    public static void main(String[] args) {
        StatesMapBuilder statesMapBuilder = StatesMapBuilder.of(inputter.getDefinitionLines());

        List<State> initialStates = StatesMapBuilder.getInitialStateNames(inputter.getEnd(), statesMapBuilder.getInvertedStates());
        Map<State, Double> stateHeuristics = inputter.getHeuristics(statesMapBuilder.getStates());

        UNIFORM_COST uniform_cost = new UNIFORM_COST();
        uniform_cost.find(() -> initialStates, State::getTransitions, e -> false, stateHeuristics::get);

        Map<State, Double> stateCost = uniform_cost.getStateCost();
        System.out.println("Checking whether heuristic is optimistic:");
        boolean isOptimistic = true;
        int countOptimisticErrors = 0;
        for (var entry : stateHeuristics.entrySet()) {
            if (entry.getValue() > stateCost.get(entry.getKey())) {
                System.out.printf("State %s H= %s and O= %s%n",
                        entry.getKey().getName(),
                        stateHeuristics.get(entry.getKey()),
                        stateCost.get(entry.getKey()));
                isOptimistic = false;
                countOptimisticErrors++;
            }
        }

        System.out.println("Checking if heuristic is consistent.");
        boolean consistent = true;
        int countConsistencyErrors = 0;
        for (State state : statesMapBuilder.getStates().values()) {
            for (Transition transition : state.getTransitions()) {
                if (stateHeuristics.get(state) > stateHeuristics.get(transition.getState()) + transition.getCost()) {
                    System.out.printf("[ERR] h(%s) > h(%s) + c: %s > %s + %s\n", state.getName(), transition.getState().getName(), stateHeuristics.get(state), stateHeuristics.get(transition.getState()), transition.getCost());
                    consistent = false;
                    countConsistencyErrors++;
                }
            }
        }

        System.out.printf("Heuristic %s optimistic! Errors count: %d %n", isOptimistic ? "IS" : "IS NOT", countOptimisticErrors);
        System.out.printf("Heuristic %s consistent! Errors count: %d%n", consistent ? "IS" : "IS NOT", countConsistencyErrors);
    }
}
