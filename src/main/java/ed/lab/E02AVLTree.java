package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private class Node {
        T value;
        Node left;
        Node right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;
    private int size = 0;
    private final Comparator<T> comparator;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node node, T value) {

        if (node == null) {
            size++;
            return new Node(value);
        }

        int cmp = comparator.compare(value, node.value);

        if (cmp < 0)
            node.left = insert(node.left, value);
        else if (cmp > 0)
            node.right = insert(node.right, value);
        else
            return node;

        updateHeight(node);
        return balance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node delete(Node node, T value) {

        if (node == null)
            return null;

        int cmp = comparator.compare(value, node.value);

        if (cmp < 0)
            node.left = delete(node.left, value);
        else if (cmp > 0)
            node.right = delete(node.right, value);
        else {

            if (node.left == null || node.right == null) {

                size--;

                Node temp = (node.left != null) ? node.left : node.right;
                return temp;

            } else {

                Node successor = minValueNode(node.right);
                node.value = successor.value;
                node.right = delete(node.right, successor.value);
            }
        }

        updateHeight(node);
        return balance(node);
    }

    public T search(T value) {

        Node current = root;

        while (current != null) {

            int cmp = comparator.compare(value, current.value);

            if (cmp == 0)
                return current.value;
            else if (cmp < 0)
                current = current.left;
            else
                current = current.right;
        }

        return null;
    }

    public int height() {
        return root == null ? 0 : root.height;
    }

    public int size() {
        return size;
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private Node balance(Node node) {

        int balance = getBalance(node);

        if (balance > 1) {

            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);

            return rotateRight(node);
        }

        if (balance < -1) {

            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);

            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node y) {

        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node rotateLeft(Node x) {

        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node minValueNode(Node node) {

        Node current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }
}