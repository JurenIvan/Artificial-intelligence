package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.toList;

public class Inputter {

    private List<String> clausesLines;
    private List<String> queriesLines;

    public Inputter(List<String> clausesLines, List<String> queriesLines) {
        this.clausesLines = new ArrayList<>();

        outher:
        for (var line : clausesLines) {
            HashMap<String, Boolean> literals = new HashMap<>();

            for (String token : line.split(" ")) {
                token = token.trim().toLowerCase();
                if (token.isBlank()) continue;
                if (token.equals("v")) continue;
                if (token.startsWith("~")) {
                    if (literals.containsKey(token.substring(1)) && FALSE.equals(literals.get(token.substring(1))))
                        continue outher;
                    else literals.put(token.substring(1), true);
                } else {
                    if (literals.containsKey(token.substring(1)) && TRUE.equals(literals.get(token.substring(1))))
                        continue outher;
                    literals.put(token, false);
                }
            }
            this.clausesLines.add(line);
        }


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
