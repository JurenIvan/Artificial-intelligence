package ui.Command;

import ui.BOK;
import ui.Mode;

public abstract class AbstractCommand {

    protected Mode mode;

    public static AbstractCommand parse(String line, Mode mode) {
        line = line.trim();
        String substring = line.substring(0, line.length() - 1);

        if (line.endsWith("?")) return new CommandResolve(substring, mode);
        if (line.endsWith("+")) return new CommandAdd(substring, mode);
        if (line.endsWith("-")) return new CommandRemove(substring, mode);

        throw new IllegalArgumentException("Line must end on ['?', '+', '-']");
    }

    private AbstractCommand(Mode mode) {
        this.mode = mode;
    }

    public abstract String execute(BOK bok);
}
