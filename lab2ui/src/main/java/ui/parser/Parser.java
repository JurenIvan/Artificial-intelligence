package ui.parser;

import ui.Node.*;

import java.util.Stack;

import static ui.parser.TokenType.*;

public class Parser {

    private Stack<Node> nodes;
    private Stack<Token> tokens;

    public Node parse(String line) {
        Lexer lexer = new Lexer(line);
        nodes = new Stack<>();
        tokens = new Stack<>();

        Token currToken;
        while ((currToken = lexer.nextToken()).getTokenType() != EOF) {

            if (isOperator(currToken.getTokenType()) || currToken.getTokenType() == PAR_L || currToken.getTokenType() == NOT) {
                tokens.push(currToken);
                continue;
            }

            if (currToken.getTokenType() == TOK) {
                nodes.push(new NodeData(currToken.getValue()));
                continue;
            }

            if (currToken.getTokenType() == PAR_R) {
                Node right = nodes.pop();
                while (tokens.peek().getTokenType() == NOT) {
                    right.invert();
                    tokens.pop();
                }
                Token token = tokens.pop();
                Node mother = getMotherNode(token);
                Node left = nodes.pop();
                while (!tokens.isEmpty() && tokens.peek().getTokenType() == NOT) {
                    left.invert();
                    tokens.pop();
                }
                mother.setFirst(left);
                mother.setSecond(right);
                if (tokens.peek().getTokenType() == PAR_L) {
                    tokens.pop();
                    while (!tokens.isEmpty() && tokens.peek().getTokenType() == NOT) {
                        mother.invert();
                        tokens.pop();
                    }
                }
                nodes.push(mother);
            }
        }
        while (nodes.size() > 1) {
            Node right = nodes.pop();
            while (!tokens.isEmpty() && tokens.peek().getTokenType() == NOT) {
                right.invert();
                tokens.pop();
            }
            Token token = tokens.pop();
            Node mother = getMotherNode(token);
            Node left = nodes.pop();
            while (!tokens.isEmpty() && tokens.peek().getTokenType() == NOT) {
                left.invert();
                tokens.pop();
            }
            mother.setFirst(left);
            mother.setSecond(right);
            nodes.push(mother);
        }
        return nodes.pop();
    }

    private Node getMotherNode(Token token) {
        Node mother;
        if (token.getTokenType() == IMP) mother = new NodeImplication(null, null);
        else if (token.getTokenType() == EKV) mother = new NodeEquivalence(null, null);
        else if (token.getTokenType() == AND) mother = new NodeAnd(null, null);
        else mother = new NodeOr(null, null);
        return mother;
    }
}
