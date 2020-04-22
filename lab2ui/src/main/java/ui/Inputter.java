package ui;

import ui.model.Clause;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Inputter {

    private List<String> clausesLines;
    private List<String> querriesLines;

    public List<Clause> getClausesLines() {     //todo remove
        List<Clause> clauses = new ArrayList<>();
        for (var line : clausesLines) {
            clauses.add(Clause.parse(line));
        }
        return clauses;
    }

    public Inputter(String firstFilePath, String secondFilePath) throws IOException {
        this.clausesLines = filterStrings(Files.readAllLines(Paths.get(firstFilePath)));
        this.querriesLines = filterStrings(Files.readAllLines(Paths.get(secondFilePath)));
    }

    public Inputter(String firstFilePath) throws IOException {
        this.clausesLines = filterStrings(Files.readAllLines(Paths.get(firstFilePath)));
    }

    private List<String> filterStrings(List<String> readAllLines) {
        return readAllLines.stream()
                .filter(e -> !e.startsWith("#"))
                .map(String::toLowerCase)
                .collect(toList());
    }

    public List<String> getCommandsLines() {
        return querriesLines;
    }
}
