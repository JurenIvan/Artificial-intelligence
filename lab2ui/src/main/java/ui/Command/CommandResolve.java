package ui.Command;

import ui.BOK;
import ui.model.Clause;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandResolve extends AbstractCommand {

    private Clause clause;

    public CommandResolve(String commandLine) {
        commandLine = commandLine.trim();
        if (commandLine.startsWith("~"))
            this.clause = new Clause(Map.of(commandLine.substring(1), false), null, null);
        else
            this.clause = new Clause(Map.of(commandLine, true), null, null);
    }

    @Override
    public String execute(BOK bok) {
        StringBuilder sb = new StringBuilder();
        var result = bok.resolve(clause);

        for (Clause value : bok.getClauses()) {
            sb.append(value.toString()).append("\n");
        }
        sb.append("=============\n");
        sb.append(clause.toString()).append("\n");
        sb.append("=============\n");


        var result2 = result.stream().sorted(Comparator.comparingInt(Clause::getId)).collect(Collectors.toList());
        for (var cause : result2) {
            sb.append(cause.toString()).append("\n");
        }
        sb.append("=============\n");

        return sb.toString();
    }
}
