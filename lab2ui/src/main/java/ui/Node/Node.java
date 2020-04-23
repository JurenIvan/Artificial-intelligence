package ui.Node;

public abstract class Node {

    protected Node first;
    protected Node second;
    protected boolean inverted;

    public Node invert() {
        this.inverted = !this.inverted;
        return this;
    }

    public abstract Node removeEquivalence();

    public abstract Node removeImplication();

    public abstract Node pushInversion();

    public abstract Node pushDistributivity();

    public abstract Node copy();

    public abstract String getValue();

    public Node convert() {
        this.removeEquivalence();
        this.removeImplication();
        this.pushInversion();
        this.pushDistributivity();
        return this;
    }

    public Node() {
    }

    public Node(Node first, Node second) {
        this.first = first;
        this.second = second;
        this.inverted = false;
    }

    public Node getFirst() {
        return first;
    }

    public Node getSecond() {
        return second;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public void setSecond(Node second) {
        this.second = second;
    }
}
