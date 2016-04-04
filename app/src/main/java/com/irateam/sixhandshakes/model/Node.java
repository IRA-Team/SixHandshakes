package com.irateam.sixhandshakes.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represent a node of non-binary tree for storing relatives between users.
 * Every node holds id of user. Every node (except main) stores a parent node.
 * Main goals are ordered insertions and fast search of nodes.
 */
public class Node {

    /**
     * Identifier of user
     */
    private int id;

    /**
     * Parent node. Can be null when current node is main node of tree
     */
    private Node parent;

    /**
     * Map that stores pair of children nodes by their identifiers
     */
    private Map<Integer, Node> children = new LinkedHashMap<>();

    public Node(int id) {
        this.id = id;
    }

    public Node(int id, Node parent) {
        this(id);
        this.parent = parent;
    }

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

    public void addChild(Node node) {
        children.put(node.getId(), node);
    }

    public Node search(int id) {
        return search(id, this);
    }

    /**
     * @param id   - Requested identifier of node
     * @param node - Node from which search would be began
     * @return Node with requested id if such node would be found otherwise null
     */
    public static Node search(int id, Node node) {
        if (node.children.get(id) != null) {
            return node.children.get(id);
        }
        for (Map.Entry<Integer, Node> entry : node.children.entrySet()) {
            Node res = search(id, entry.getValue());
            if (res != null) return res;
        }
        return null;
    }
}
