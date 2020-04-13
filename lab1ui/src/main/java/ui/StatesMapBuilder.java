package ui;

import ui.structures.State;
import ui.structures.Transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class StatesMapBuilder {

    private List<String> definitions;

    private HashMap<String, State> states;
    private HashMap<String, State> invertedStates;

    private StatesMapBuilder(List<String> definitions) {
        this.definitions = definitions;
    }

    public static StatesMapBuilder of(List<String> definitions) {
        return new StatesMapBuilder(definitions);
    }

    public HashMap<String, State> getStates() {
        return states == null ? states = calculateStates() : states;
    }

    public HashMap<String, State> getInvertedStates() {
        return invertedStates == null ? invertedStates = calculateInvertedStates() : invertedStates;
    }

    private HashMap<String, State> calculateStates() {
        HashMap<String, String[]> init = new HashMap<>();
        HashMap<String, State> states = new HashMap<>();

        for (String definition : definitions) {
            String[] splitted = definition.split(":");
            init.put(splitted[0], splitted.length == 2 ? splitted[1].trim().split(" ") : new String[]{});
        }

        init.forEach((name, transitions) -> states.put(name, new State(name)));
        init.forEach((name, transitions) ->
                states.get(name).setTransitions(stream(transitions)
                        .map(e -> new Transition(states.get(e.split(",")[0]), Double.parseDouble(e.split(",")[1])))
                        .collect(toList())));

        return states;
    }

    private HashMap<String, State> calculateInvertedStates() {
        HashMap<String, State> states = getStates();
        HashMap<String, State> invertedStates = new HashMap<>();

        states.forEach((key, value) -> invertedStates.put(key, new State(key)));

        for (State state : states.values())
            state.getTransitions().forEach(e -> invertedStates.get(e.getState().getName()).getTransitions().add(new Transition(invertedStates.get(state.getName()), e.getCost())));

        return invertedStates;
    }

    public static Map<State, Boolean> getGoalStates(List<String> endNames, Map<String, State> states) {
        return endNames.stream().collect(Collectors.toMap(states::get, e -> true));
    }

    public static List<State> getInitialStateNames(List<String> startNames, Map<String, State> states) {
        return startNames.stream().map(states::get).collect(toList());
    }
}
