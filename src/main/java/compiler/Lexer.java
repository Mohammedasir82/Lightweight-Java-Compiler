package compiler;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    private char peek() {
        if (pos >= input.length()) return '\0';
        return input.charAt(pos);
    }

    private char next() {
        char c = peek();
        pos++;
        return c;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (true) {
            char c = peek();
            if (c == '\0') {
                tokens.add(new Token(Token.Type.EOF, ""));
                break;
            } else if (Character.isWhitespace(c)) {
                next();
            } else if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (Character.isDigit(peek())) num.append(next());
                tokens.add(new Token(Token.Type.NUMBER, num.toString()));
            } else if (Character.isLetter(c)) {
                StringBuilder id = new StringBuilder();
                while (Character.isLetterOrDigit(peek())) id.append(next());
                String word = id.toString();
                if (word.equals("print"))
                    tokens.add(new Token(Token.Type.PRINT, word));
                else if (word.equals("repeat"))
                    tokens.add(new Token(Token.Type.REPEAT, word));
                else
                    tokens.add(new Token(Token.Type.IDENTIFIER, word));
            } else {
                switch (c) {
                    case '=' -> { next(); tokens.add(new Token(Token.Type.ASSIGN, "=")); }
                    case ';' -> { next(); tokens.add(new Token(Token.Type.SEMICOLON, ";")); }
                    case '+' -> { next(); tokens.add(new Token(Token.Type.PLUS, "+")); }
                    case '-' -> { next(); tokens.add(new Token(Token.Type.MINUS, "-")); }
                    case '*' -> { next(); tokens.add(new Token(Token.Type.MUL, "*")); }
                    case '/' -> { next(); tokens.add(new Token(Token.Type.DIV, "/")); }
                    case '(' -> { next(); tokens.add(new Token(Token.Type.LPAREN, "(")); }
                    case ')' -> { next(); tokens.add(new Token(Token.Type.RPAREN, ")")); }
                    case '{' -> { next(); tokens.add(new Token(Token.Type.LBRACE, "{")); }
                    case '}' -> { next(); tokens.add(new Token(Token.Type.RBRACE, "}")); }
                    default -> throw new RuntimeException("Unexpected char: " + c);
                }
            }
        }
        return tokens;
    }
}
