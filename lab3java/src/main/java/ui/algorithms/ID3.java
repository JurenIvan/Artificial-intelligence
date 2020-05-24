package ui.algorithms;

import ui.model.Entry;
import ui.model.Leaf;
import ui.model.Node;
import ui.model.TreeElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ui.model.Entry.*;

public class ID3 extends AbstractMLAlgorithm {

    private TreeElement root;

    @Override
    public void fit(List<Entry> dataset) {
        root = id3(dataset, getAttrNames(), 0);
    }

    private TreeElement id3(List<Entry> filtered, List<String> attrNames, int d) {
        if (filtered.isEmpty())
            return new Leaf(d, mostCommonClassifier());

        var classValued = filtered.get(0).getValue(getClassifierName());
        if (attrNames.isEmpty() || filtered.stream().allMatch(e -> e.getValue(getClassifierName()).equals(classValued)))
            return new Leaf(d, classValued);

        var x = maxInformationGainForAttr(filtered);
        var subtrees = new HashMap<String, TreeElement>();
        for (var v : getPossibleValues(x)) {
            var attrSublist = new ArrayList<>(attrNames);
            attrSublist.remove(x);
            var t = id3(filterDataset(filtered, x, v), attrSublist, d + 1);
            subtrees.put(v, t);
        }

        return new Node(x, d, subtrees);
    }

    private List<Entry> filterDataset(List<Entry> filtered, String attrName, String attrValue) {
        return filtered.stream().filter(e -> e.getValue(attrName).equals(attrValue)).collect(toList());
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
