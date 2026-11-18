package org.example.parser;

import org.apache.commons.lang3.CharUtils;
import org.example.parser.model.CellToken;
import org.example.parser.model.NumToken;
import org.example.parser.model.OpToken;
import org.example.parser.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tokenizer {
    List<Token> tokenize(String expression) {
        int index = 0;
        char[] exp = expression.toCharArray();
        List<Token> tokens = new ArrayList<>();
        Stack<Character> perTokenStack = new Stack<>();
        CharType prevCharType = null;

        for (index = 0; index < expression.length();) {
            char ch = exp[index];
            CharType currCharType = getCharType(ch);

            if (currCharType == CharType.WS) {
                flush(perTokenStack, tokens);
            } else if (currCharType == CharType.OP) {
                flush(perTokenStack, tokens);
                tokens.add(new OpToken(String.valueOf(ch)));
            } else if (currCharType == CharType.INT) {
                perTokenStack.push(ch);
            } else if (currCharType == CharType.ALPH) {
                if (prevCharType == CharType.INT) {
                    // letters after digits: "12A" is neither a number nor a cell
                    throw new IllegalArgumentException("Unexpected letter '" + ch + "' at index " + index);
                }
                perTokenStack.push(ch);
            }

            prevCharType = currCharType;
            index++;
        }
        flush(perTokenStack, tokens);
        return tokens;
    }

    // turns whatever is accumulated in the stack into a token
    private void flush(Stack<Character> perTokenStack, List<Token> tokens) {
        if (perTokenStack.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean hasAlpha = false;
        for (char c : perTokenStack) {
            sb.append(c);
            if (getCharType(c) == CharType.ALPH) {
                hasAlpha = true;
            }
        }
        perTokenStack.clear();

        String repr = sb.toString();
        if (!hasAlpha) {
            tokens.add(new NumToken(repr));
        } else if (CharUtils.isAsciiNumeric(repr.charAt(repr.length() - 1))) {
            tokens.add(new CellToken(repr));   // A1, BA1 ...
        }
    }

    enum CharType {
        INT,
        ALPH,
        OP,
        WS;
    }

    private CharType getCharType(char ch) {
        switch (ch) {
            case '+':
            case '*':
            case '-':
            case '/': return CharType.OP;
            case ' ': return CharType.WS;
        }
        if (CharUtils.isAsciiAlpha(ch)) {
            return CharType.ALPH;
        } else if (CharUtils.isAsciiNumeric(ch)) {
            return CharType.INT;
        }
        throw new IllegalArgumentException("Invalid character: " + ch);
    }
}