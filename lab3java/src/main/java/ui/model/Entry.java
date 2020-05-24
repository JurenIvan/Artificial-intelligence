package ui.model;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Comparator.*;

public class Entry {

    private static List<String> attrNames;
    private static final Map<String, Integer> attrOrder = new HashMap<>();
    private static final Map<String, Set<String>> possibleValues = new HashMap<>();
    private static final Map<String, Integer> classifiersCount = new HashMap<>();
    private static String mostCommonClassifier;
    private static String classifierName;

    private List<String> values;

    public List<String> getValues() {
        return values;
    }

    public String getValue(String attr) {
        return values.get(attrOrder.get(attr));
    }

    public static void configure(String line) {
        attrNames = asList(line.split(","));
        attrNames.forEach(e -> attrOrder.put(e, attrOrder.size()));
        attrNames.forEach(e -> possibleValues.put(e, new HashSet<>()));
        classifierName = attrNames.get(attrNames.size() - 1);
    }

    public static Entry fromLine(String line) {
        Entry result = new Entry();
        result.values = asList(line.split(","));
        for (int i = 0; i < result.values.size(); i++) {
            possibleValues.get(attrNames.get(i)).add(result.values.get(i));
            if (i == result.values.size() - 1)
                classifiersCount.compute(result.values.get(i), (k, v) -> v == null ? 1 : v + 1);
        }
        return result;
    }

    public static List<String> getAttrNames() {
        return attrNames.subList(0, attrNames.size() - 1);
    }

    public static Integer indexOf(String arg) {
        return attrOrder.get(arg);
    }

    public static String getClassifierName() {
        return classifierName;
    }

    public static Set<String> getPossibleValues(String arg) {
        return possibleValues.get(arg);
    }

    public static String mostCommonClassifier() {
        if (mostCommonClassifier != null)
            return mostCommonClassifier;


        long cnt = classifiersCount.values().stream().mapToInt(integer -> integer).max().orElse(-1);
        return mostCommonClassifier = classifiersCount.entrySet().stream().filter(e->e.getValue()==cnt).max(Map.Entry.comparingByKey()).get().getKey();
    }

    private Entry() {
    }
}
