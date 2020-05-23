package ui.model;

public class Leaf extends Node {

    private final String result;

    public Leaf(int d, String attr) {
        super(d, attr);
        result = attr;
    }
}
