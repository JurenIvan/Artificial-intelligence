package ui.algorithms;

import ui.structures.Node;
import ui.structures.State;
import ui.structures.Transition;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

public class UNIFORM_COST extends AbstractAlgorithm {

    @Override
    public Optional<Node> find(Supplier<List<State>> initialState, Function<State, List<Transition>> succ, Predicate<State> goal, Function<State, Double> heuristic) {
        resetMetrics();

        PriorityQueue<Node> toExplore = new PriorityQueue<>(Comparator.comparingDouble(Node::getCost));
        toExplore.addAll(initialState.get().stream().map(e -> new Node(e, null, 0)).collect(toList()));

        while (!toExplore.isEmpty()) {
            Node head = toExplore.poll();


            if (stateCost.containsKey(head.getState()) && stateCost.get(head.getState()) <= head.getCost()) continue;

            stateVisitedCounter++;
            stateCost.put(head.getState(), head.getCost());

            if (goal.test(head.getState())) return of(head);

            for (var elem : succ.apply(head.getState())) {
                if (stateCost.containsKey(elem.getState()) && stateCost.get(elem.getState()) <= head.getCost() + elem.getCost())
                    continue;
                toExplore.add(new Node(elem.getState(), head, head.getCost() + elem.getCost()));
            }
        }
        return empty();
    }

    @Override
    public String getName() {
        return "UNIFORM_COST";
    }
}
