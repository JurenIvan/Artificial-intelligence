package ui.Node;

import java.util.Arrays;

public class NodeRunner {

    public static void main(String[] args) {

        Node root = new NodeAnd(new NodeData("stela"), new NodeImplication(new NodeEquivalence(new NodeData("fortnite",true),new NodeData("panda")),new NodeData("Pubg")));

        System.out.println(Arrays.asList(root.convert().getValue().split("\n")));
    }
}
