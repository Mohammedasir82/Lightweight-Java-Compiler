package compiler;

import compiler.Token.Type;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) { this.tokens = tokens; }

    private Token peek() { return tokens.get(pos); }
    private Token next() { return tokens.get(pos++); }
    private boolean match(Type type) {
        if (peek().type == type) { next(); return true; }
        return false;
    }

    public AST.Program parseProgram() {
        List<AST.Node> stmts = new ArrayList<>();
        while (peek().type != Type.EOF) {
            stmts.add(parseStatement());
        }
        return new AST.Program(stmts);
    }

    private AST.Node parseStatement() {
        if (match(Type.PRINT)) {
            AST.Node expr = parseExpression();
            match(Type.SEMICOLON);
            return new AST.Print(expr);
        } else if (match(Type.REPEAT)) {
            AST.Node times = parseExpression();
            match(Type.LBRACE);
            List<AST.Node> body = new ArrayList<>();
            while (!match(Type.RBRACE)) {
                body.add(parseStatement());
            }
            return new AST.Repeat(times, body);
        } else {
            Token id = next();
            if (id.type != Type.IDENTIFIER) throw new RuntimeException("Expected identifier");
            match(Type.ASSIGN);
            AST.Node expr = parseExpression();
            match(Type.SEMICOLON);
            return new AST.Assign(id.text, expr);
        }
    }

    private AST.Node parseExpression() {
        AST.Node left = parseTerm();
        while (peek().type == Type.PLUS || peek().type == Type.MINUS) {
            String op = next().text;
            AST.Node right = parseTerm();
            left = new AST.BinOp(left, op, right);
        }
        return left;
    }

    private AST.Node parseTerm() {
        AST.Node left = parseFactor();
        while (peek().type == Type.MUL || peek().type == Type.DIV) {
            String op = next().text;
            AST.Node right = parseFactor();
            left = new AST.BinOp(left, op, right);
        }
        return left;
    }

    private AST.Node parseFactor() {
        Token t = next();
        return switch (t.type) {
            case NUMBER -> new AST.Num(Integer.parseInt(t.text));
            case IDENTIFIER -> new AST.Var(t.text);
            case LPAREN -> {
                AST.Node expr = parseExpression();
                match(Type.RPAREN);
                yield expr;
            }
            default -> throw new RuntimeException("Unexpected token: " + t);
        };
    }
}
