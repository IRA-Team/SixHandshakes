package com.irateam.sixhandshakes.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Node {
    private Node parent;
    private Map<Integer, Node> children = new LinkedHashMap<>();
    private int id;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Map<Integer, Node> getChildren() {
        return children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addChildNode(Node node) {
        children.put(node.getId(), node);
    }

    public Node findById(int id) {
        if (children.get(id) != null) {
            return children.get(id);
        }
        for (Map.Entry<Integer, Node> entry : children.entrySet()) {
            Node node = entry.getValue().getChildren().get(id);
            if (node != null) {
                return node;
            }
        }
        return null;
    }
}
