package ui.parser;

public enum TokenType {
    TOK, PAR_L, PAR_R, NOT, AND, IMP, EKV, OR, EOF;

    public static boolean isOperator(TokenType tokenType) {
        return tokenType == AND || tokenType == EKV || tokenType == OR || tokenType == IMP;
    }
}