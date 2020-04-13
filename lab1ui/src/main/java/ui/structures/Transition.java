package ui.structures;

public class Transition {

    private State state;
    private double cost;

    public Transition(State state, double cost) {
        this.state = state;
        this.cost = cost;
    }

    public State getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }
}
