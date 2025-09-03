package compiler;

public class Token {
    public enum Type {
        IDENTIFIER, NUMBER, STRING,
        ASSIGN, SEMICOLON,
        PLUS, MINUS, MUL, DIV,
        LPAREN, RPAREN, LBRACE, RBRACE,
        PRINT, REPEAT,
        EOF
    }

    public final Type type;
    public final String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return type + "('" + text + "')";
    }
}
