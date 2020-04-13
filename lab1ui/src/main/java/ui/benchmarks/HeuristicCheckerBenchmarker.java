package ui.benchmarks;

import ui.Inputter;
import ui.StatesMapBuilder;
import ui.algorithms.BFS;
import ui.structures.State;
import ui.structures.Transition;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static ui.StatesMapBuilder.getInitialStateNames;
import static ui.StatesMapBuilder.of;

public class HeuristicCheckerBenchmarker {

    private static final String IN_FILE_HEURISTICS_NAME = "3x3_misplaced_heuristic.txt";
    private static final String IN_FILE_NAME = "3x3_puzzle.txt";

    private static final Inputter inputter = new Inputter(IN_FILE_NAME, IN_FILE_HEURISTICS_NAME);

    public static void main(String[] args) {
        StatesMapBuilder statesMapBuilder = of(inputter.getDefinitionLines());

        getInitialStateNames(inputter.getEnd(), statesMapBuilder.getInvertedStates());
        Map<State, Double> stateHeuristics = inputter.getHeuristics(statesMapBuilder.getStates());
        Map<String, State> stringStateMap = statesMapBuilder.getStates();
        List<State> statesToBench = List.of(
                stringStateMap.get("123_x74_658"),
                stringStateMap.get("478_523_16x"),
                stringStateMap.get("318_425_67x"),
                stringStateMap.get("843_267_51x"),
                stringStateMap.get("573_864_12x"),
                stringStateMap.get("713_845_62x"),
                stringStateMap.get("273_641_85x"),
                stringStateMap.get("236_517_48x"),
                stringStateMap.get("531_764_28x"),
                stringStateMap.get("816_374_25x"));

        List<Integer> results = new ArrayList<>(2000);

        System.out.println(LocalDateTime.now().toLocalTime().toNanoOfDay());
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Supplier<List<State>> initialStateSupplier = () -> List.of(statesToBench.get(finalI));
            for (int j = 0; j < 100; j++) {

                BFS bfs = new BFS();
                bfs.find(initialStateSupplier, State::getTransitions, e -> false, stateHeuristics::get);

                Map<State, Double> stateCost = bfs.getStateCost();
                int countOptimisticErrors = 0;
                for (var entry : stateHeuristics.entrySet()) {
                    if (entry.getValue() > stateCost.get(entry.getKey())) {
                        countOptimisticErrors++;
                    }
                }

                int countConsistencyErrors = 0;
                for (State state : statesMapBuilder.getStates().values()) {
                    for (Transition transition : state.getTransitions()) {
                        if (stateHeuristics.get(state) > stateHeuristics.get(transition.getState()) + transition.getCost()) {
                            countConsistencyErrors++;
                        }
                    }
                }
                results.add(countConsistencyErrors);
                results.add(countOptimisticErrors);
            }
            System.out.println(LocalDateTime.now().toLocalTime().toNanoOfDay());
        }
    }
}
