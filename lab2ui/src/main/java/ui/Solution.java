package ui;

import ui.Command.AbstractCommand;
import ui.model.Clause;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static ui.Mode.*;

public class Solution {

    private static Inputter inputter;
    private static Mode mode = NORMAL;

    public static void main(String[] args) throws IOException {
        BOK bok = new BOK();

        if (args[0].equals("autocnf")) {

        }

        if (args[0].equals("resolution")) {
            inputter = new Inputter(args[1]);

            inputter.setQuerriesLines(List.of(inputter.getClausesLines().get(inputter.getClausesLines().size() - 1) + " ?"));
            inputter.setClausesLines(inputter.getClausesLines().subList(0, inputter.getClausesLines().size() - 1));

            if (args.length == 3 && "verbose".equals(args[2].trim().toLowerCase())) mode = NORMAL;
            else mode = QUIET;

            inputter.getClausesLines().forEach(e -> bok.addClause(Clause.parse(e)));


            for (String line : inputter.getCommandsLines())
                System.out.print(AbstractCommand.parse(line.toLowerCase().trim()).execute(bok, mode));
            return;


        } else if (args[0].equals("cooking_interactive")) {
            inputter = new Inputter(args[1]);
            if (args.length > 2) {
                mode = "verbose".equals(args[2].trim().toLowerCase()) ? LOUD : NORMAL;
            } else mode = NORMAL;
        } else if (args[0].equals("cooking_test")) {
            inputter = new Inputter(args[1], args[2]);
            mode = QUIET;
        }

        inputter.getClausesLines().forEach(e -> bok.addClause(Clause.parse(e)));

        if (mode == QUIET) {
            for (String line : inputter.getCommandsLines())
                System.out.print(AbstractCommand.parse(line.toLowerCase().trim()).execute(bok, mode));
            return;
        }

        //else is interactive
        Scanner sc = new Scanner(System.in);
        System.out.print(">>> ");
        while (sc.hasNextLine()) {
            String next = sc.nextLine().toLowerCase().trim();
            if ("exit".equals(next.trim().toLowerCase())) return;
            System.out.println(AbstractCommand.parse(next).execute(bok, mode));
            System.out.print(">>> ");
        }

    }
}
