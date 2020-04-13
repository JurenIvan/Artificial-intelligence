package ui.structures;

public class Node {

    private State state;
    private Node previous;
    private double cost;

    public Node(State state, Node previous, double cost) {
        this.state = state;
        this.previous = previous;
        this.cost = cost;
    }

    public State getState() {
        return state;
    }

    public Node getPrevious() {
        return previous;
    }

    public double getCost() {
        return cost;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Node reduceCost(double amount) {
        this.cost -= amount;
        return this;
    }
}
