package ui.Node;

public class NodeEquivalence extends Node {

    @Override
    public Node removeEquivalence() {   //todo fix
        Node firstAfterRemove = first.removeEquivalence();
        Node secondAfterRemove = second.removeEquivalence();

        return new NodeAnd(new NodeOr(firstAfterRemove.invert(), secondAfterRemove), new NodeOr(secondAfterRemove.invert(), firstAfterRemove));
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
        return new NodeEquivalence(first.copy(), second.copy());    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    public NodeEquivalence(Node first, Node second) {
        super(first, second);
    }
}
