package ui;

import ui.model.Entry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Inputter {

    private static Inputter inputter;

    public static Inputter getInstance(String trainingPath, String testPath, String configPath) {
        if (inputter != null)
            return inputter;
        return inputter = new Inputter(trainingPath, testPath, configPath);
    }

    private List<Entry> trainingEntries;
    private List<Entry> testEntries;
    private Map<String, String> configArgs;


    private Inputter(String trainingPath, String testPath, String configPath) {
        try {
            var trainingLines = readAllLines(get(trainingPath));
            Entry.configure(trainingLines.get(0));
            trainingEntries = trainingLines.subList(1, trainingLines.size()).stream().map(Entry::fromLine).collect(toList());

            var testLines = readAllLines(get(testPath));
            testEntries = testLines.subList(1, testLines.size()).stream().map(Entry::fromLine).collect(toList());

            configArgs = readAllLines(get(configPath)).stream().map(e -> e.split("=")).collect(toMap(e -> e[0], e -> e[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Entry> getTrainingEntries() {
        return trainingEntries;
    }

    public List<Entry> getTestEntries() {
        return testEntries;
    }

    public Map<String, String> getConfigArgs() {
        return configArgs;
    }
}
