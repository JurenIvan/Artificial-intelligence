package ui.parser;

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
                return currentToken = new Token("~", TokenType.NOT);
            }
            if (chars[pointer] == '(') {
                pointer++;
                return currentToken = new Token("(", TokenType.PAR_L);
            }
            if (chars[pointer] == ')') {
                pointer++;
                return currentToken = new Token(")", TokenType.PAR_R);
            }
            if (chars[pointer] == 'v') {
                pointer++;
                return currentToken = new Token("v", TokenType.OR);
            }
            if (chars[pointer] == '=') {
                pointer++;
                return currentToken = new Token("=", TokenType.EKV);
            }
            if (chars[pointer] == '&') {
                pointer++;
                return currentToken = new Token("&", TokenType.AND);
            }
            if (chars[pointer] == '>') {
                pointer++;
                return currentToken = new Token(">", TokenType.IMP);
            }

            if (Character.isAlphabetic(chars[pointer])) {
                while (chars.length > pointer && Character.isAlphabetic(chars[pointer]))
                    sb.append(chars[pointer++]);
                return currentToken = new Token(sb.toString(), TokenType.TOK);
            }

        }
        return currentToken = new Token(null, TokenType.EOF);
    }

    public Token getCurrentToken() {
        return currentToken != null ? currentToken : nextToken();
    }
}
