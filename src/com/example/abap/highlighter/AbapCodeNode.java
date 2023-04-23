package com.example.abap.highlighter;

import java.util.ArrayList;
import java.util.List;

public class AbapCodeNode {
    private String label;
    private int offset;
    private int length;
    private List<AbapCodeNode> children;

    public AbapCodeNode(String label, int offset, int length) {
        this.label = label;
        this.offset = offset;
        this.length = length;
        this.children = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public List<AbapCodeNode> getChildren() {
        return children;
    }

    public void addChild(AbapCodeNode child) {
        children.add(child);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}

