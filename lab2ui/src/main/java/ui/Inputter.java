package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Inputter {

    private List<String> clausesLines;
    private List<String> querriesLines;


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

    public List<String> getClausesLines() {
        return clausesLines;
    }

    public void setClausesLines(List<String> clausesLines) {
        this.clausesLines = clausesLines;
    }

    public void setQuerriesLines(List<String> querriesLines) {
        this.querriesLines = querriesLines;
    }
}
