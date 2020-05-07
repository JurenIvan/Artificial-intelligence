package ui.Command;

import ui.BOK;
import ui.Mode;
import ui.model.Clause;

import static java.lang.String.format;

public class CommandRemove extends AbstractCommand {

    private String commandLine;

    public CommandRemove(String commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public String execute(BOK bok, Mode mode) {
        Clause clause = Clause.parse(commandLine);
        boolean success = bok.removeClause(clause);

        return mode == Mode.LOUD ? (format("Removing %s ", clause) + (success ? "success" : "failed")) : "";
    }
}
