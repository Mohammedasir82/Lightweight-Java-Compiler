package compiler;

import java.util.*;

public class VM {
    private final List<String> code;
    private final Map<String, Integer> vars = new HashMap<>();
    private final Deque<Integer> stack = new ArrayDeque<>();

    public VM(List<String> code) {
        this.code = code;
    }

    public void run() {
        Deque<Integer> repeatStack = new ArrayDeque<>();
        Deque<Integer> pcStack = new ArrayDeque<>();
        for (int pc = 0; pc < code.size(); pc++) {
            String instr = code.get(pc);
            String[] parts = instr.split(" ");
            switch (parts[0]) {
                case "PUSH" -> stack.push(Integer.parseInt(parts[1]));
                case "LOAD" -> stack.push(vars.getOrDefault(parts[1], 0));
                case "STORE" -> vars.put(parts[1], stack.pop());
                case "ADD" -> stack.push(stack.pop() + stack.pop());
                case "SUB" -> { int b = stack.pop(); int a = stack.pop(); stack.push(a - b); }
                case "MUL" -> stack.push(stack.pop() * stack.pop());
                case "DIV" -> { int b = stack.pop(); int a = stack.pop(); stack.push(a / b); }
                case "PRINT" -> System.out.println(stack.pop());
                case "REPEAT_START" -> {
                    int times = stack.pop();
                    repeatStack.push(times);
                    pcStack.push(pc);
                }
                case "REPEAT_END" -> {
                    int times = repeatStack.pop() - 1;
                    if (times > 0) {
                        repeatStack.push(times);
                        pc = pcStack.peek();
                    } else {
                        pcStack.pop();
                    }
                }
            }
        }
    }
}
