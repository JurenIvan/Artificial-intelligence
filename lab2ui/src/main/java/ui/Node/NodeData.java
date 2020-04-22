package ui.Node;

public class NodeData extends Node {

    private String name;

    public NodeData(String name) {
        super();
        super.inverted = false;
        this.name = name;
    }

    public NodeData(String name, boolean inverted) {
        super();
        super.inverted = inverted;
        this.name = name;
    }

    @Override
    public Node removeEquivalence() {
        return this;
    }

    @Override
    public Node removeImplication() {
        return this;
    }

    @Override
    public Node pushInversion() {
        return this;
    }

    @Override
    public Node pushDistributivity() {
        return this;
    }

    @Override
    public Node copy() {
        return new NodeData(name, inverted);
    }

    @Override
    public String getValue() {
        return inverted ? "~" + name : name;
    }


}
