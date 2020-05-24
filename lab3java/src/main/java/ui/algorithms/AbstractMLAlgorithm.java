package ui.algorithms;

import ui.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public abstract class AbstractMLAlgorithm implements MLAlgorithm {

    protected Map<String, String> args;

    @Override
    public void configure(Map<String, String> args) {
        if (this.args != null) {
            throw new IllegalStateException();
        }
        this.args = args;
    }

    public static double entropy(List<Entry> entries, String arg) {
        int index = Entry.indexOf(arg);

        HashMap<String, Integer> nums = new HashMap<>();
        entries.forEach(e -> nums.compute(e.getValues().get(index), (k, v) -> v == null ? 1 : v + 1));

        double total = nums.values().stream().mapToInt(e -> e).sum();
        double e = 0;
        for (var a : nums.values()) {
            e += (a / total) * Math.log(a / total) / Math.log(2);
        }
        return -e;
    }

    public static double informationGain(List<Entry> entries, String arg) {
        double ig = entropy(entries, Entry.getClassifierName());

        for (var val : Entry.getPossibleValues(arg)) {
            List<Entry> filtered = entries.stream().filter(e -> e.getValue(arg).equals(val)).collect(toList());
            ig -= filtered.size() * entropy(filtered, Entry.getClassifierName()) / entries.size();
        }

        return ig;
    }

    protected String maxInformationGainForAttr(List<Entry> dataset) {
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

    protected String mostCommonClassifier(List<Entry> filtered) {

        HashMap<String, Integer> nums = new HashMap<>();
        filtered.forEach(e -> nums.compute(e.getValue(Entry.getClassifierName()), (k, v) -> v == null ? 1 : v + 1));
        long cnt = nums.values().stream().mapToInt(integer -> integer).max().orElse(-1);
        return nums.entrySet().stream().filter(e -> e.getValue() == cnt).max(Map.Entry.comparingByKey()).get().getKey();

    }
}
