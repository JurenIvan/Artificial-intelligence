package ui.algorithms;

import ui.structures.Node;
import ui.structures.State;

import java.util.*;

public abstract class AbstractAlgorithm implements Algorithm {

    protected long stateVisitedCounter;
    protected Map<State, Double> stateCost;

    public long getStateVisitedCount() {
        return stateVisitedCounter;
    }

    public static List<String> printPath(Optional<Node> result) {
        LinkedList<String> stateNames = new LinkedList<>();
        if (result.isPresent()) {
            Node node = result.get();
            while (node != null) {
                stateNames.addFirst(node.getState().getName());
                node = node.getPrevious();
            }
        }
        return stateNames;
    }

    public static int pathLength(Optional<Node> result) {
        if (result.isEmpty())
            return -1;
        int pathLength = 0;
        var node = result.get();
        while (node != null) {
            pathLength++;
            node = node.getPrevious();
        }
        return pathLength;
    }

    public Map<State, Double> getStateCost() {
        return stateCost;
    }

    protected void resetMetrics() {
        this.stateVisitedCounter = 0;
        this.stateCost = new HashMap<>();
    }
}
