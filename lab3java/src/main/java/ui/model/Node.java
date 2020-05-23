package ui.model;

import java.util.HashMap;

public class Node {

    private HashMap<String, Node> children;
    private final int d;
    private final String attr;

    public Node(int d, String attr) {
        this.d = d;
        this.attr = attr;
    }

    public Node(String attr, int d, HashMap<String, Node> children) {
        this.attr = attr;
        this.d = d;
        this.children = children;
    }

    public int getD() {
        return d;
    }
}
