package ui;

import ui.Command.AbstractCommand;

import java.io.IOException;
import java.util.Scanner;

public class Solution {

    private static AbstractCommand abstractCommander;

    public static void main(String[] args) throws IOException {
        Inputter inputter;
        if (args.length == 2) inputter = new Inputter(args[0], args[1]);
        else if (args.length == 1) inputter = new Inputter(args[0]);
        else throw new IllegalArgumentException("Needs one or two arguments");

        BOK bok = new BOK();
        inputter.getClausesLines().forEach(bok::addClause);
        Mode mode = Mode.LOUD;

        if (args.length == 2) {
            for (String line : inputter.getCommandsLines()) {
                System.out.println(AbstractCommand.parse(line.toLowerCase(), mode).execute(bok));
            }
        }

        if (args.length == 1) {
            Scanner sc = new Scanner(System.in);
            System.out.print(">>> ");
            while (sc.hasNextLine()) {
                String next = sc.nextLine().toLowerCase().trim();
                if ("exit".equals(next.trim().toLowerCase())) return;
                System.out.println(AbstractCommand.parse(next, mode).execute(bok));
                System.out.print(">>> ");
            }
        }

        return;
    }
}
