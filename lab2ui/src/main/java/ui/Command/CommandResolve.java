package ui.Command;

import ui.BOK;
import ui.Mode;
import ui.model.Clause;

import java.util.Comparator;
import java.util.stream.Collectors;

public class CommandResolve extends AbstractCommand {

    private Clause clause;
    private String commandLine;

    public CommandResolve(String commandLine) {
        this.commandLine = commandLine.trim();
        commandLine = commandLine.trim();
        this.clause = Clause.parse(commandLine);
    }

    @Override
    public String execute(BOK bok, Mode mode) {
        StringBuilder sb = new StringBuilder();
        var result = bok.resolve(clause, mode);

        if (mode == Mode.QUIET) {
            if (result.stream().anyMatch(e -> e.getLiterals().isEmpty()))
                return commandLine + " is true\n";
            else
                return commandLine + " is unknown\n";
        }

        result.remove(clause);

        for (Clause value : bok.getClauses()) {
            sb.append(value.toString()).append("\n");
        }
        sb.append("=============\n");
        sb.append(" ").append(clause.getId()).append(". ~(").append(commandLine).append(")").append("\n");
        sb.append("=============\n");


        var result2 = result.stream().sorted(Comparator.comparingInt(Clause::getId)).collect(Collectors.toList());
        for (var cause : result2) {
            sb.append(cause.toString()).append("\n");
        }
        sb.append("=============\n");

        if (result.stream().anyMatch(e -> e.getLiterals().isEmpty()))
            sb.append(commandLine).append(" is true");
        else
            sb.append(commandLine).append(" is unknown");

        return sb.toString();
    }
}
