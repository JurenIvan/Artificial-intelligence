package ui.model;

public abstract class TreeElement {

    protected int d;

    public TreeElement(int d) {
        this.d = d;
    }

    public int getD() {
        return d;
    }

    public abstract String result(Entry entry);

    public abstract String printTree();
}
