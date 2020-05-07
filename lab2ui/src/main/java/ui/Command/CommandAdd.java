package ui.Command;

import ui.BOK;
import ui.Mode;
import ui.model.Clause;

import static java.lang.String.format;

public class CommandAdd extends AbstractCommand {

    private String commandLine;

    public CommandAdd(String commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public String execute(BOK bok, Mode mode) {
        Clause clause = Clause.parse(commandLine);
        bok.addClause(clause);

        return mode == Mode.LOUD ? format("Added %s", clause) : "";
    }
}
