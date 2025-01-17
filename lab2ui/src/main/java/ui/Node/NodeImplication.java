package ui.Node;

public class NodeImplication extends Node {

    @Override
    public Node removeEquivalence() {
        first = first.removeEquivalence();
        second = second.removeEquivalence();
        return this;
    }

    @Override
    public Node removeImplication() {
        return new NodeOr(first.invert().removeImplication(), second.removeImplication(), inverted);
    }

    @Override
    public Node pushInversion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node pushDistributivity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node copy() {
        return new NodeImplication(first.copy(), second.copy(), inverted);
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    public NodeImplication(Node first, Node second, boolean inverted) {
        super(first, second, inverted);
    }
}
