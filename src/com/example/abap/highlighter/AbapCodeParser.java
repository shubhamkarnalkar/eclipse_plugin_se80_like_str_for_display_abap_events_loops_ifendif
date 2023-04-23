package com.example.abap.highlighter;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbapCodeParser {
    public static AbapCodeNode parse(String code) {
        AbapCodeNode root = new AbapCodeNode("root", 0, code.length());

        Stack<AbapCodeNode> stack = new Stack<>();
        stack.push(root);

        Matcher matcher = Pattern.compile("(PERFORM|IF|CASE|WHILE|DO)[^\\.]*\\.").matcher(code);

        while (matcher.find()) {
            String statement = matcher.group();
            int offset = matcher.start();
            int length = statement.length();

            AbapCodeNode node = new AbapCodeNode(statement, offset, length);

            while (!stack.isEmpty() && !isParent(stack.peek(), node)) {
                stack.pop();
            }

            AbapCodeNode parent = stack.peek();
            parent.addChild(node);
            stack.push(node);
        }

        return root;
    }

    private static boolean isParent(AbapCodeNode parent, AbapCodeNode child) {
        return parent.getOffset() <= child.getOffset() && parent.getOffset() + parent.getLength() >= child.getOffset() + child.getLength();
    }
}

