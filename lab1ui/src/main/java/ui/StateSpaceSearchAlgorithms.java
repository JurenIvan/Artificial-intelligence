package ui;

import ui.algorithms.A_STAR;
import ui.algorithms.Algorithm;
import ui.algorithms.BFS;
import ui.algorithms.UNIFORM_COST;
import ui.structures.State;
import ui.algorithms.*;

import java.util.List;
import java.util.Map;

public class StateSpaceSearchAlgorithms {

    private static final String IN_FILE_HEURISTICS_NAME = "3x3_misplaced_heuristic.txt";
    private static final String IN_FILE_NAME = "3x3_puzzle.txt";

    private static final Inputter inputter = new Inputter(IN_FILE_NAME, IN_FILE_HEURISTICS_NAME);

    public static void main(String[] args) {
        StatesMapBuilder stateMapBuidler = StatesMapBuilder.of(inputter.getDefinitionLines());

        List<State> initialStates = StatesMapBuilder.getInitialStateNames(inputter.getStart(), stateMapBuidler.getStates());
        Map<State, Double> stateHeuristics = inputter.getHeuristics(stateMapBuidler.getStates());
        Map<State, Boolean> isGoal = StatesMapBuilder.getGoalStates(inputter.getEnd(), stateMapBuidler.getStates());

        System.out.println("Start state : " + initialStates);
        System.out.println("End state(s): " + isGoal.keySet());
        System.out.println("State space size: " + stateHeuristics.size());
        System.out.println("Total transitions: " + stateMapBuidler.getStates().values().stream().mapToInt(e -> e.getTransitions().size()).sum());
        System.out.println();

        List<Algorithm> algorithms = List.of(new BFS(), new UNIFORM_COST(), new A_STAR());
        for (Algorithm algorithm : algorithms) {
            var node = algorithm.find(() -> initialStates, State::getTransitions, isGoal::containsKey, stateHeuristics::get);

            if (node.isPresent()) {
                System.out.printf("%s\nVisited states %d\nTotal cost: %s\nVisited path: %s\nPath length: %d%n%n",
                        algorithm.getName(),
                        algorithm.getStateVisitedCount(),
                        node.get().getCost(),
                        AbstractAlgorithm.printPath(node),
                        AbstractAlgorithm.pathLength(node));
            } else
                System.out.println(algorithm.getName() + " has failed to get result\n");
        }
    }

}
