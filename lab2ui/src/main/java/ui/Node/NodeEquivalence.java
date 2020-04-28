package ui.Node;

public class NodeEquivalence extends Node {

    @Override
    public Node removeEquivalence() {
        Node firstAfterRemove = first.removeEquivalence();
        Node secondAfterRemove = second.removeEquivalence();

        Node firstCopy = firstAfterRemove.copy();
        Node secondCopy = secondAfterRemove.copy();

        return new NodeAnd(new NodeOr(firstAfterRemove.invert(), secondAfterRemove, false), new NodeOr(secondCopy.invert(), firstCopy, false), inverted);
    }

    @Override
    public Node removeImplication() {
        throw new UnsupportedOperationException();
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
        return new NodeEquivalence(first.copy(), second.copy(), inverted);
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    public NodeEquivalence(Node first, Node second, boolean inverted) {
        super(first, second, inverted);
    }
}
