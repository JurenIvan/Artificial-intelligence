package ui.benchmarks;

import ui.Inputter;
import ui.StatesMapBuilder;
import ui.algorithms.A_STAR;
import ui.algorithms.Algorithm;
import ui.heuristics.*;
import ui.structures.State;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static ui.StatesMapBuilder.*;

public class StateSpaceSearchAlgorithmsBenchmarker {

    private static final String IN_FILE_HEURISTICS_NAME = "3x3_misplaced_heuristic.txt";
    private static final String IN_FILE_NAME = "3x3_puzzle.txt";

    private static final Inputter inputter = new Inputter(IN_FILE_NAME, IN_FILE_HEURISTICS_NAME);

    public static void main(String[] args) {
        StatesMapBuilder stateMapBuidler = of(inputter.getDefinitionLines());
        getInitialStateNames(inputter.getStart(), stateMapBuidler.getStates());
        Map<String, State> stringStateMap = stateMapBuidler.getStates();
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

        Map<State, Boolean> isGoal = getGoalStates(inputter.getEnd(), stateMapBuidler.getStates());

        Algorithm algorithm = new A_STAR();
        List<Heuristic> heuristics = List.of(
                new PuzzleIsInRightRow(),
                new PuzzleMisplacedHeuristic(),
                new PuzzleEuclideanHeuristic(),
                new PuzzleManhattanHeuristic(),
                new OracleHeuristics(inputter));

        for (var heuristic : heuristics) {
            long stateCount = 0;
            for (State initState : statesToBench) {
                Supplier<List<State>> initialStateSupplier = () -> List.of(initState);
                algorithm.find(initialStateSupplier, State::getTransitions, isGoal::containsKey, heuristic);
                stateCount += algorithm.getStateVisitedCount();
            }
            System.out.println("average for " + heuristic.name() + " is : " + (stateCount / statesToBench.size()));
        }
    }

}
