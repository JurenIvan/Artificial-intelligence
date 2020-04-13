package ui.heuristics;

import ui.structures.State;

import java.util.function.Function;

public interface Heuristic extends Function<State, Double> {

    String name();
}
