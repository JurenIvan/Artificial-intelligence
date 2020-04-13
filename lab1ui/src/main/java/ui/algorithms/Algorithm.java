package ui.algorithms;

import ui.structures.Node;
import ui.structures.State;
import ui.structures.Transition;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Algorithm {

    Optional<Node> find(Supplier<List<State>> initialState, Function<State, List<Transition>> succ, Predicate<State> goal, Function<State, Double> heuristic);

    String getName();

    long getStateVisitedCount();
}
