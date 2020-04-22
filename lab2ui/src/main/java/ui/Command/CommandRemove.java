package ui.Command;

import ui.BOK;
import ui.model.Clause;

public class CommandRemove extends AbstractCommand {

    private String commandLine;

    public CommandRemove(String commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public String execute(BOK bok) {
        bok.removeClause(Clause.parse(commandLine));
        return "";
    }
}
