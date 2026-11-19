package org.example.parser;

import org.example.parser.model.CellToken;
import org.example.parser.model.NumToken;
import org.example.parser.model.OpToken;
import org.example.parser.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// the math operation parser
// will use shanting yard and deliver the RPN notation for a math expression
// the input will be a list of tokens and the output is a re-arranged list of tokens
public class Parser {
    List<Token> parse(List<Token> expression) {
        List<Token> output = new ArrayList<>();
        Stack<OpToken> operators = new Stack<>();

        for (Token token : expression) {
            if (token instanceof OpToken operator) {
                while (!operators.isEmpty() && operators.peek().getPrecedence() >= operator.getPrecedence()) {
                    output.add(operators.pop());
                }
                operators.push(operator);
            } else if (token instanceof NumToken || token instanceof CellToken) {
                output.add(token);
            }
        }

        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }
}
