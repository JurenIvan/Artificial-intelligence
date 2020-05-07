package ui.parser;

import static ui.parser.TokenType.*;

public class Lexer {

    private int pointer = 0;
    private char[] chars;
    private Token currentToken;

    public Lexer(String line) {
        this.chars = line.trim().toCharArray();
        this.pointer = 0;
    }

    public Token nextToken() {
        StringBuilder sb = new StringBuilder();

        while (pointer < chars.length) {
            if (chars[pointer] == ' ') pointer++;

            if (chars[pointer] == '~') {
                pointer++;
                return currentToken = new Token("~", NOT);
            }
            if (chars[pointer] == '(') {
                pointer++;
                return currentToken = new Token("(", PAR_L);
            }
            if (chars[pointer] == ')') {
                pointer++;
                return currentToken = new Token(")", PAR_R);
            }
            if (chars[pointer] == 'v') {
                pointer++;
                return currentToken = new Token("v", OR);
            }
            if (chars[pointer] == '=') {
                pointer++;
                return currentToken = new Token("=", EKV);
            }
            if (chars[pointer] == '&') {
                pointer++;
                return currentToken = new Token("&", AND);
            }
            if (chars[pointer] == '>') {
                pointer++;
                return currentToken = new Token(">", IMP);
            }

            if (Character.isAlphabetic(chars[pointer])) {
                while (chars.length > pointer && Character.isAlphabetic(chars[pointer]))
                    sb.append(chars[pointer++]);
                return currentToken = new Token(sb.toString(), TOK);
            }

        }
        return currentToken = new Token(null, EOF);
    }

    public Token getCurrentToken() {
        return currentToken != null ? currentToken : nextToken();
    }
}
