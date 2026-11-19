package org.example.parser.model;

public class OpToken extends Token {
    private final Operator op;
    public OpToken(String stringRpr) {
        switch (stringRpr) {
            case "+": this.op = Operator.PLUS; break;
            case "-": this.op = Operator.MINUS; break;
            case "*": this.op = Operator.MULT; break;
            case "/": this.op = Operator.DIV; break;
            default: throw new IllegalArgumentException("Invalid operator entered");
        }
    }

    public Operator getOp() {
        return op;
    }

    public int getPrecedence() {
        return switch (op) {
            case PLUS, MINUS -> 1;
            case MULT, DIV -> 2;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OpToken opToken)) return false;
        return op == opToken.op;
    }
}
