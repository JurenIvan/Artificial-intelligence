package ui.Node;

import ui.parser.Parser;

public class NodeRunner {

    private static final String input = "A v B \n" +
            "A > B \n" +
            "A = B \n" +
            "A & B \n" +
            "~(A > B) \n" +
            "~(A = B) \n" +
            "(C v D) > (~A = B) \n" +
            "(C v D) > (~~~A = B) \n" +
            "~(C v D) > (~A = B) \n" +
            "~(~(C v D) > (~A = B)) \n" +
            "~~(~(C v D) > (~A = B)) \n";

    public static void main(String[] args) {

        String[] lines = input.split("\n");
        for (var line : lines) {
            Parser parser = new Parser();
            System.out.println(parser.parse(line).convert().getValue());
        }

    }
}
