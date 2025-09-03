package compiler;

import java.util.List;

public class AST {
    public interface Node {}

    public static class Program implements Node {
        public final List<Node> statements;
        public Program(List<Node> statements) { this.statements = statements; }
    }

    public static class Assign implements Node {
        public final String name;
        public final Node expr;
        public Assign(String name, Node expr) { this.name = name; this.expr = expr; }
    }

    public static class Print implements Node {
        public final Node expr;
        public Print(Node expr) { this.expr = expr; }
    }

    public static class Repeat implements Node {
        public final Node times;
        public final List<Node> body;
        public Repeat(Node times, List<Node> body) {
            this.times = times; this.body = body;
        }
    }

    public static class BinOp implements Node {
        public final Node left, right;
        public final String op;
        public BinOp(Node left, String op, Node right) {
            this.left = left; this.op = op; this.right = right;
        }
    }

    public static class Num implements Node {
        public final int value;
        public Num(int value) { this.value = value; }
    }

    public static class Var implements Node {
        public final String name;
        public Var(String name) { this.name = name; }
    }
}
