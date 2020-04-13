package ui.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {

    private String name;
    private List<Transition> transitions;

    public State(String name) {
        this.name = name;
        this.transitions = new ArrayList<>();
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public String getName() {
        return name;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return name.equals(state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "State{ name= '" + name + "'}";
    }
}
