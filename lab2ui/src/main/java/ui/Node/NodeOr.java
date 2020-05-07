package ui.Node;

public class NodeOr extends Node {

    @Override
    public Node removeEquivalence() {
        first = first.removeEquivalence();
        second = second.removeEquivalence();
        return this;
    }

    @Override
    public Node removeImplication() {
        first = first.removeImplication();
        second = second.removeImplication();
        return this;
    }

    @Override
    public Node pushInversion() {
        if (inverted) {
            first = first.invert();
            second = second.invert();

            first = first.pushInversion();
            second = second.pushInversion();

            return new NodeAnd(first, second, false);
        }

        first = first.pushInversion();
        second = second.pushInversion();

        return this;
    }

    @Override
    public Node pushDistributivity() {
        first = first.pushDistributivity();
        second = second.pushDistributivity();

        Node newFirst, newSecond;

        if (first instanceof NodeAnd) {
            newFirst = new NodeOr(second, first.getFirst(), false);
            newSecond = new NodeOr(second, first.getSecond(), false);
            return new NodeAnd(newFirst.pushDistributivity(), newSecond.pushDistributivity(), inverted);

        }
        if (second instanceof NodeAnd) {
            newFirst = new NodeOr(first, second.getFirst(), false);
            newSecond = new NodeOr(first, second.getSecond(), false);
            return new NodeAnd(newFirst.pushDistributivity(), newSecond.pushDistributivity(), inverted);
        }

        return this;
    }

    @Override
    public Node copy() {
        return new NodeOr(first.copy(), second.copy(), inverted);
    }

    @Override
    public String getValue() {
        return first.getValue() + " v " + second.getValue();
    }

    public NodeOr(Node first, Node second, boolean inverted) {
        super(first, second, inverted);
    }
}
