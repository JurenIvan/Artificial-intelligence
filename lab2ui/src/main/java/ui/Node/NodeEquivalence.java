package ui.Node;

public class NodeEquivalence extends Node {

    @Override
    public Node removeEquivalence() {
        Node firstAfterRemove = first.removeEquivalence();
        Node secondAfterRemove = second.removeEquivalence();

        Node firstCopy = firstAfterRemove.copy();
        Node secondCopy = secondAfterRemove.copy();

        return new NodeAnd(new NodeOr(firstAfterRemove.invert(), secondAfterRemove), new NodeOr(secondCopy.invert(), firstCopy));
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
        return new NodeEquivalence(first.copy(), second.copy());
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    public NodeEquivalence(Node first, Node second) {
        super(first, second);
    }
}
