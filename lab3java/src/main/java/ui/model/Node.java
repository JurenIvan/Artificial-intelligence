package ui.model;

import java.util.HashMap;

public class Node extends TreeElement {

    private final HashMap<String, TreeElement> children;
    private final String attr;
    private final String defaultAnswer;

    public Node(String attr, int d, HashMap<String, TreeElement> children, String defaultAnswer) {
        super(d);
        this.attr = attr;
        this.children = children;
        this.defaultAnswer = defaultAnswer;
    }


    @Override
    public String result(Entry entry) {
        var child = children.get(entry.getValue(attr));
        if (child == null) return defaultAnswer;
        return child.result(entry);
    }

    @Override
    public String printTree() {
        StringBuilder sb = new StringBuilder();
        sb.append(d).append(":").append(attr);
        for (var child : children.values()) {
            if (child instanceof Leaf) continue;
            sb.append(", ");
            sb.append(child.printTree());
        }
        return sb.toString();
    }
}
