package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Inputter {

    private List<String> clausesLines;
    private List<String> queriesLines;

    public Inputter(List<String> clausesLines, List<String> queriesLines) {
        this.clausesLines = clausesLines;
        this.queriesLines = queriesLines;
    }

    public static Inputter forResolution(String firstFilePath) throws IOException {
        List<String> fileLines = filterStrings(Files.readAllLines(Paths.get(firstFilePath)));
        return new Inputter(fileLines.subList(0, fileLines.size() - 1), List.of(fileLines.get(fileLines.size() - 1) + " ?"));
    }

    public static Inputter onlyClauses(String firstFilePath) throws IOException {
        return new Inputter(filterStrings(Files.readAllLines(Paths.get(firstFilePath))), null);
    }

    public static Inputter clausesAndQueries(String firstFilePath, String secondFilePath) throws IOException {
        return new Inputter(filterStrings(Files.readAllLines(Paths.get(firstFilePath))), filterStrings(Files.readAllLines(Paths.get(secondFilePath))));
    }

    private static List<String> filterStrings(List<String> readAllLines) {
        return readAllLines.stream()
                .filter(e -> !e.startsWith("#"))
                .map(String::toLowerCase)
                .collect(toList());
    }

    public List<String> getCommandsLines() {
        return queriesLines;
    }

    public List<String> getClausesLines() {
        return clausesLines;
    }
}
