package com.irateam.sixhandshakes.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Node node;
    private List<Node> children = new ArrayList<>();
    private int id;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addChildNode(Node node) {
        children.add(node);
    }
}
