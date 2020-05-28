package ui.algorithms;

import ui.model.Entry;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static ui.model.Entry.getClassifierName;

public class RF extends AbstractMLAlgorithm {

    private final List<ID3> id3s = new ArrayList<>();

    public void fit(List<Entry> dataset) {
//        List<List<String>> a1 = List.of(
//                java.util.List.of("temperature", "weather"),
//                java.util.List.of("humidity", "wind"),
//                java.util.List.of("humidity", "temperature"),
//                java.util.List.of("temperature", "humidity"),
//                java.util.List.of("humidity", "temperature"));
//
//        List<List<Integer>> n1 = List.of(
//                List.of(2, 8, 6, 5, 9, 12, 4),
//                List.of(1, 13, 11, 8, 9 ,0, 3),
//                List.of(12, 10, 1, 6, 3, 11, 9),
//                List.of(10, 3, 9, 12, 2, 11, 4),
//                List.of(4, 0, 2, 7, 5, 3, 6));

//        List<List<String>> a1 = List.of(
//                java.util.List.of("weather", "temperature"),
//                java.util.List.of("temperature", "wind"),
//                java.util.List.of("weather", "temperature"),
//                java.util.List.of("temperature", "humidity"),
//                java.util.List.of("temperature", "humidity"));
////
//        List<List<Integer>> n1 = List.of(
//                List.of(8, 2, 10, 13, 6, 13, 7),
//                List.of(11, 12, 5, 2, 2, 6, 9),
//                List.of(13, 8, 10, 11, 6, 11, 4),
//                List.of(11, 9, 1, 13, 3, 13, 0),
//                List.of(3, 7, 8, 3, 0, 11, 8));

//        List<List<String>> a1 = List.of(
//                java.util.List.of("A", "B"),
//                java.util.List.of("A", "B"),
//                java.util.List.of("A", "B"),
//                java.util.List.of("A", "B"),
//                java.util.List.of("B", "C"),
//                java.util.List.of("A", "C"),
//                java.util.List.of("A", "C"),
//                java.util.List.of("B", "D"),
//                java.util.List.of("B", "D"));
//
//        List<List<Integer>> n1 = List.of(
//                List.of(6, 1 ,7, 9, 4, 9, 5),
//                List.of(6, 4 ,2, 2, 6, 6, 4),
//                List.of(1, 6, 4, 8, 5, 4, 0),
//                List.of(9, 2, 8, 6, 0, 9, 2),
//                List.of(6, 3, 2, 5, 6, 2, 0),
//                List.of(2, 8, 2 ,2, 1, 9, 3),
//                List.of(7, 8, 6 ,0, 2, 2, 9),
//                List.of(0, 0, 3 ,8, 9, 8, 1),
//                List.of(8, 8, 6 ,0, 3, 0, 4)
//               );

//        List<List<String>> a1 = List.of(
//                java.util.List.of("weather", "temperature", "humidity", "wind"));
//
//        List<List<Integer>> n1 = List.of(
//                List.of(0,1,2,3,4,5,6,7,8,9,10,11,12,13));

        for (int i = 0; i < Integer.parseInt(args.get("num_trees")); i++) {
//            List<Entry> filtered = n1.get(i).stream().map(e -> dataset.get(e)).collect(toList());
//            var id3 = new ID3(a1.get(i));

            double exampleRatio = Double.parseDouble(args.get("example_ratio"));
            int instanceSubset = (int) Math.round(exampleRatio * dataset.size());
            int featureSubset = (int) Math.round(exampleRatio * Entry.getAttrNames().size() - 1);

            var copiedAttrs = new ArrayList<>(Entry.getAttrNames());
            var copiedInstances = new ArrayList<>(IntStream.range(0, dataset.size()).boxed().collect(toCollection(ArrayList::new)));

            Collections.shuffle(copiedAttrs);
            Collections.shuffle(copiedInstances);

            List<Entry> filtered = IntStream.range(0, instanceSubset).mapToObj(e -> dataset.get(copiedInstances.get(e))).collect(toList());
            List<String> attrs = IntStream.range(0, featureSubset).mapToObj(e -> copiedAttrs.get(e)).collect(toList());
            attrs.add(getClassifierName());

            System.out.println("".join(" ",attrs));
            System.out.println(copiedInstances.subList(0,instanceSubset).stream().map(Object::toString).collect(joining(" ")));

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
