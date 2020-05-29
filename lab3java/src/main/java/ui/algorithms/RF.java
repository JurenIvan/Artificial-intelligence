package ui.algorithms;

import ui.model.Entry;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.round;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static ui.model.Entry.getAttrNames;

public class RF extends AbstractMLAlgorithm {

    private final List<ID3> id3s = new ArrayList<>();

    public void fit(List<Entry> dataset) {
//
        double exampleRatio = Double.parseDouble(args.get("example_ratio"));
        double featureRatio = Double.parseDouble(args.get("feature_ratio"));
//        System.out.println("exampleRatio          " + exampleRatio);
//        System.out.println("featureRatio          " + featureRatio);
//        System.out.println("dataset.size()        " + dataset.size());
//        System.out.println("getAttrNames().size() " + getAttrNames().size());
//        System.out.println();
//        System.out.println("round       " + round(featureRatio * getAttrNames().size()));
//        System.out.println("floor       " + floor(featureRatio * getAttrNames().size()));

        Random random = new Random();
        for (int i = 0; i < Integer.parseInt(args.get("num_trees")); i++) {
//            List<Entry> filtered = Helper.n2.get(i).stream().map(e -> dataset.get(e)).collect(toList());
//            var id3 = new ID3(Helper.a2.get(i));

            int instanceSubset = (int) round(exampleRatio * dataset.size() - 0.001);
            int featureSubset = (int) round(featureRatio * getAttrNames().size() - 0.001);

            var copiedAttrs = new ArrayList<>(Entry.getAttrNames());
            Collections.shuffle(copiedAttrs);

            var copiedInstances = new ArrayList<Integer>();
            for (int j = 0; j < instanceSubset; j++) {
                copiedInstances.add(Math.abs(random.nextInt()% dataset.size()) );
            }

            List<Entry> filtered = IntStream.range(0, instanceSubset).mapToObj(e -> dataset.get(copiedInstances.get(e))).collect(toList());
            List<String> attrs = IntStream.range(0, featureSubset).mapToObj(e -> copiedAttrs.get(e)).collect(toList());

            System.out.println("".join(" ", attrs));
            System.out.println(copiedInstances.subList(0, instanceSubset).stream().map(Object::toString).collect(joining(" ")));

            var id3 = new ID3(attrs);
            id3.configure(args);
            id3.fit(filtered);
            id3s.add(id3);
        }
    }

    public String predict(Entry testCase) {
        HashMap<String, Integer> counter = new HashMap<>();
        id3s.forEach(e -> counter.compute(e.predict(testCase), (k, v) -> (v == null) ? 1 : v + 1));
        return counter.entrySet().stream().max(comparingInt(Map.Entry::getValue)).get().getKey();
    }
}
