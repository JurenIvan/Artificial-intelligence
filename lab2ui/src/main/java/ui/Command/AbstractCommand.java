package ui.Command;

import ui.BOK;
import ui.Mode;

public abstract class AbstractCommand {

    public static AbstractCommand parse(String line) {
        line = line.trim();
        String substring = line.substring(0, line.length() - 1);

        if (line.endsWith("?")) return new CommandResolve(substring);
        if (line.endsWith("+")) return new CommandAdd(substring);
        if (line.endsWith("-")) return new CommandRemove(substring);

        throw new IllegalArgumentException("Line must end on ['?', '+', '-']");
    }

    public abstract String execute(BOK bok, Mode mode);
}
