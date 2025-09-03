package compiler;

import java.util.ArrayList;
import java.util.List;

public class BytecodeEmitter {
    private final List<String> code = new ArrayList<>();

    public List<String> emit(AST.Program program) {
        for (AST.Node stmt : program.statements) emitStmt(stmt);
        return code;
    }

    private void emitStmt(AST.Node node) {
        if (node instanceof AST.Assign a) {
            emitExpr(a.expr);
            code.add("STORE " + a.name);
        } else if (node instanceof AST.Print p) {
            emitExpr(p.expr);
            code.add("PRINT");
        } else if (node instanceof AST.Repeat r) {
            emitExpr(r.times);
            code.add("REPEAT_START");
            for (AST.Node stmt : r.body) emitStmt(stmt);
            code.add("REPEAT_END");
        } else {
            throw new RuntimeException("Unknown stmt: " + node);
        }
    }

    private void emitExpr(AST.Node node) {
        if (node instanceof AST.Num n) {
            code.add("PUSH " + n.value);
        } else if (node instanceof AST.Var v) {
            code.add("LOAD " + v.name);
        } else if (node instanceof AST.BinOp b) {
            emitExpr(b.left);
            emitExpr(b.right);
            switch (b.op) {
                case "+" -> code.add("ADD");
                case "-" -> code.add("SUB");
                case "*" -> code.add("MUL");
                case "/" -> code.add("DIV");
            }
        } else {
            throw new RuntimeException("Unknown expr: " + node);
        }
    }
}
