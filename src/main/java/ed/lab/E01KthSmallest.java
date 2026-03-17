package ed.lab;

public class E01KthSmallest {

    private int count = 0;
    private int result = 0;

    public int kthSmallest(TreeNode<Integer> root, int k) {
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode<Integer> node, int k) {
        if (node == null) return;

        // izquierda
        inorder(node.left, k);

        // visitar nodo
        count++;
        if (count == k) {
            result = node.value;
            return;
        }

        // derecha
        inorder(node.right, k);
    }
}