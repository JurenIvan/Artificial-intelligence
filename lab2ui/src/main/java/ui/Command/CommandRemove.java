package ui.Command;

import ui.BOK;
import ui.Mode;
import ui.model.Clause;

public class CommandRemove extends AbstractCommand {

    private String commandLine;

    public CommandRemove(String commandLine, Mode mode) {
        super(mode);
        this.commandLine = commandLine;
    }

    @Override
    public String execute(BOK bok) {
        bok.removeClause(Clause.parse(commandLine));
        return "";
    }
}
