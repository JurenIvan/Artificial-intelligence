package ui.Node;

public class NodeAnd extends Node {

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

            return new NodeOr(first, second);
        }
        first = first.pushInversion();
        second = second.pushInversion();
        return this;
    }

    @Override
    public Node pushDistributivity() {
        first = first.pushDistributivity();
        second = second.pushDistributivity();
        return this;
    }

    @Override
    public Node copy() {
        return new NodeAnd(first.copy(), second.copy());
    }


    @Override
    public String getValue() {
        return "(" + first.getValue() + ") & (" + second.getValue() + ")";
    }

    public NodeAnd(Node first, Node second) {
        super(first, second);
    }
}
