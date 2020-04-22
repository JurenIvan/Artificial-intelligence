package ui.Command;

import ui.BOK;
import ui.Mode;
import ui.model.Clause;

public class CommandAdd extends AbstractCommand {

    private String commandLine;

    public CommandAdd(String commandLine, Mode mode) {
        super(mode);
        this.commandLine = commandLine;
    }

    @Override
    public String execute(BOK bok) {
        bok.addClause(Clause.parse(commandLine));
        return "";
    }
}
