package ui.algorithms;

import ui.model.Entry;
import ui.model.Leaf;
import ui.model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ID3 extends AbstractMLAlgorithm {

    private Node root;

    @Override
    public void fit(List<Entry> dataset) {
        root = id3(dataset, Entry.getAttrNames(), 0);
    }

    private Node id3(List<Entry> filtered, List<String> attrNames, int d) {
        if (filtered.isEmpty())
            return new Leaf(d, Entry.mostCommonClassifier());

        var classValued = filtered.get(0).getValue(Entry.getClassifierName());
        if (attrNames.isEmpty() || filtered.stream().allMatch(e -> e.getValue(Entry.getClassifierName()).equals(classValued))) {
            return new Leaf(d, classValued);
        }

        var x = maxAttr(filtered);
        var subtrees = new HashMap<String, Node>();
        for (var v : Entry.getPossibleValues(x)) {
            ArrayList<String> attrSublist = new ArrayList<>(attrNames);
            attrSublist.remove(x);
            var t = id3(filtered.stream().filter(e -> e.getValue(x).equals(v)).collect(toList()), attrSublist, d + 1);
            subtrees.put(v, t);
        }

        return new Node(x, d, subtrees);
    }

    private String maxAttr(List<Entry> dataset) {
        String maxAttr = null;
        double maxIg = 0;
        for (var attr : Entry.getAttrNames()) {
            double ig = informationGain(dataset, attr);
            if (ig > maxIg || maxAttr == null) {
                maxAttr = attr;
                maxIg = ig;
            }
        }
        return maxAttr;
    }


    @Override
    public String predict(Entry testCase) {
        return null;
    }


}
