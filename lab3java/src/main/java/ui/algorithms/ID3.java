package ui.algorithms;

import ui.model.Entry;
import ui.model.Leaf;
import ui.model.Node;
import ui.model.TreeElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ui.model.Entry.*;

public class ID3 extends AbstractMLAlgorithm {

    private TreeElement root;
    private List<String> attrNames;

    public ID3(List<String> attrNames) {
        this.attrNames = attrNames;
    }

    @Override
    public void fit(List<Entry> dataset) {
        root = id3(dataset, attrNames, 0);
    }

    protected TreeElement id3(List<Entry> filtered, List<String> attrNames, int d) {
        if (filtered.isEmpty())
            return new Leaf(d, mostCommonClassifier(filtered)); //never executed

        if (attrNames.isEmpty()) {
            return new Leaf(d, mostCommonClassifier(filtered));
        }

        var classValued = filtered.get(0).getValue(getClassifierName());
        if (filtered.stream().allMatch(e -> e.getValue(getClassifierName()).equals(classValued)))
            return new Leaf(d, classValued);

        if (d == Integer.parseInt(args.getOrDefault("max_depth", "-1"))) {
            return new Leaf(d, mostCommonClassifier(filtered));
        }

        var x = maxInformationGainForAttr(filtered, attrNames);
        var subtrees = new HashMap<String, TreeElement>();
        for (var v : getPossibleValues(x)) {
            var attrSublist = new ArrayList<>(attrNames);
            attrSublist.remove(x);
            var filteredEntries = filterDataset(filtered, x, v);

            TreeElement t;
            if (filteredEntries.isEmpty())
                t = new Leaf(d + 1, mostCommonClassifier(filtered));
            else
                t = id3(filteredEntries, attrSublist, d + 1);
            subtrees.put(v, t);
        }

        return new Node(x, d, subtrees);
    }

    @Override
    public String predict(Entry testCase) {
        return root.result(testCase);
    }

    @Override
    public String toString() {
        return root.printTree();
    }
}
