package ui.model;

public class Leaf extends TreeElement {

    private final String result;

    public Leaf(int d, String result) {
        super(d);
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String result(Entry entry) {
        return result;
    }

    @Override
    public String printTree() {
        return "";
    }
}
