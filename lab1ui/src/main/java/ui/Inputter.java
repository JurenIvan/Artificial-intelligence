package ui;

import ui.structures.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Inputter {

    private static final String STANDARD_PATH_TO_RESOURCES = "lab1ui/src/main/resources/";

    private List<String> definition;
    private List<String> start;
    private List<String> end;

    private Map<String, Double> heuristics;

    public Inputter(String inFileName, String inFileHeuristicsName) {
        inFileName = STANDARD_PATH_TO_RESOURCES + inFileName;
        inFileHeuristicsName = STANDARD_PATH_TO_RESOURCES + inFileHeuristicsName;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inFileName)).stream().filter(e -> !e.startsWith("#")).collect(toList());

            this.definition = lines.subList(2, lines.size());
            this.start = Arrays.stream(lines.get(0).split(" ")).collect(toList());
            this.end = Arrays.stream(lines.get(1).split(" ")).collect(toList());

            heuristics = Files.readAllLines(Paths.get(inFileHeuristicsName))
                    .stream()
                    .filter(e -> !e.startsWith("#"))
                    .collect(Collectors.toMap(e -> e.split(":")[0].trim(), e -> Double.parseDouble(e.split(":")[1].trim())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getDefinitionLines() {
        return definition;
    }

    public Map<State, Double> getHeuristics(HashMap<String, State> states) {
        HashMap<State, Double> heuristicsMap = new HashMap<>();
        for (var i : heuristics.entrySet())
            heuristicsMap.put(states.get(i.getKey()), i.getValue());

        return heuristicsMap;
    }

    public List<String> getStart() {
        return start;
    }

    public List<String> getEnd() {
        return end;
    }
}
