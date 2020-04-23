package ui.Node;

import ui.parser.Parser;

public class NodeRunner {

    private static final String input = "A v B \n" +
            "~A v B \n" +
            "(~A v B) & (A v ~B) \n" +
            "A & B \n" +
            "A & ~B \n" +
            "(~A v A) & (~A v ~B) & (B v A) & (B v ~B) \n" +
            "((A v B) v ~C) & ((A v B) v ~D) & ((~A v ~B) v ~C) & ((~A v ~B) v ~D) \n" +
            "((A v B) v ~C) & ((A v B) v ~D) & ((~A v ~B) v ~C) & ((~A v ~B) v ~D) \n" +
            "((C v D) v (A v B)) & ((C v D) v (~A v ~B)) \n" +
            "~C & ~D & (A v ~A) & (A v ~B) & (B v ~A) & (B v ~B) \n" +
            "((C v D) v (A v B)) & ((C v D) v (~A v ~B)) ";

    public static void main(String[] args) {

        String[] lines = input.split("\n");
        for (var line : lines) {
            Parser parser = new Parser();
            System.out.println(parser.parse(line).convert().getValue());
        }

    }
}
