import java.lang.Math;

/**
 * A Binary Search Tree implementation.
 * The data type E must implement the Comparable interface.
 * @author Behrooz Mansouri
 * @author Abby Pitcairn
 * @version November 3, 2025
 */
public class BinarySearchTree<E extends Comparable<E>> {

    // ---------------- Nested Class for a tree node ----------------
    protected static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;
        int height;

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1; // A new node is initially a leaf with height 1
        }
    }
    // ---------------- End of Nested Class ----------------

    protected Node<E> root;

    /**
     * Constructs an empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Returns the height of a node.
     * @param N The node.
     * @return The height of the subtree rooted at N, or 0 if N is null.
     */
    protected int height(Node<E> N) {
        if (N == null)
            return 0;
        return N.height;
    }

    /**
     * Updates the height of a given node based on its children's heights.
     * @param n The node to update.
     */
    private void updateHeight(Node<E> n) {
        if (n != null) { // Added null check for safety
            n.height = 1 + Math.max(height(n.left), height(n.right));
        }
    }

    /**
     * Gets the balance factor of a node (height of left subtree - height of right subtree).
     * @param N The node.
     * @return The balance factor, or 0 if N is null.
     */
    private int getBalance(Node<E> N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    /**
     * Public method to add a new item to the tree.
     * @param data The data to insert.
     */
    public void add(E data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the tree");
        }
        root = add(root, data);
    }

    /**
     * Private recursive helper method to add an item.
     * @param node The current node in the traversal.
     * @param data The data to insert.
     * @return The (potentially new) root of the modified subtree.
     */
    private Node<E> add(Node<E> node, E data) {
        // 1. Base Case: Found the correct empty spot.
        if (node == null) {
            return new Node<>(data);
        }

        // 2. Recursive Step: Compare and go left or right.
        int compare = data.compareTo(node.data);

        if (compare < 0) {
            // Data is smaller, go left
            node.left = add(node.left, data);
        } else if (compare > 0) {
            // Data is larger, go right
            node.right = add(node.right, data);
        } else {
            // Data is equal --> we have a duplicate.
            // We return the original node unchanged.
            return node;
        }

        // 3. Update Height: After insertion, update the height of this node.
        updateHeight(node);

        // 4. Get Balance:
        // The getBalance() method isn't needed for a simple BST add,
        // but it would be used here if you were implementing rotations for an AVL tree.

        // 4. Return the (possibly updated) node to its parent.
        return node;
    }

    /**
     * Public method to print the tree's elements in ascending order (in-order traversal).
     */
    public void printInOrder() {
        System.out.println("In-order Traversal:");
        printInOrder(root);
        System.out.println(); // Add a newline at the end
    }

    /**
     * Private recursive helper method for in-order traversal.
     * @param node The current node in the traversal.
     */
    private void printInOrder(Node<E> node) {
        // Base Case:
        if (node == null) {
            return;
        }

        // 1. Visit left subtree
        printInOrder(node.left);

        // 2. Visit/print current node
        System.out.print(node.data + " ");

        // 3. Visit right subtree
        printInOrder(node.right);
    }

    /**
     * Method 1: Sum of Depths of all nodes in the tree.
     * @return The sum of depths of all nodes.
     */
    public int sumDepths() {
        return sumDepths(root, 0);
    }

    /**
     * Helper method for sumDepths.
     * @param node the current node
     * @param depth the depth of the current node
     * @return The sum of depths of all nodes in the subtree rooted at node.
     */
    private int sumDepths(Node<E> node, int depth) {
        if (node == null) {
            return -1; // Base case: empty subtree.
        }
        else {
            return depth + sumDepths(node.left, depth + 1) + sumDepths(node.right, depth + 1);
        }
    }
    
    /**
     * Method 2: Finds 2L Nodes (nodes two levels deeper on the left side) and prints their values.
     */
    public void findAndPrint2LNodes() {
        findAndPrint2LNodes(root);
    }
    /**
     * Helper method for findAndPrint2LNodes.
     * @param node the current node
     */
    private void findAndPrint2LNodes(Node<E> node) {
        if (node == null) {
            return;
        }
        int balance = getBalance(node);
        if (balance == 2)
            System.out.print(node.data + " ");
        findAndPrint2LNodes(node.left);
        findAndPrint2LNodes(node.right);
    }
    
    /**
     * Method 3: Validates the binary search tree properties.
     * @return true if current structure satisfies BST definition, false otherwise.
     */
    public boolean isBST() {
        return isBST(root, null, null);
    }
    /**
     * Helper method for isBST.
     * @param node current node
     * @param min
     * @param max 
     * @return true if subtree rooted at node is a BST, false otherwise.
     */
    private boolean isBST(Node<E> node, E min, E max) {
        if (node == null) {
            return true;
        }
        if (max != null && node.data.compareTo(max) >= 0) {
            return false;
        }
        if (min != null && node.data.compareTo(min) <= 0) {
            return false;
        }
        return isBST(node.left, min, node.data) && isBST(node.right, node.data, max);
    }

    /**
     * Method 4: Checks if the tree is an AVL tree.
     * @return true if the tree is AVL, false otherwise.
     */
    public boolean isAVL() {
        return isAVL(root);
    }

    /**
     * Helper method for isAVL.
     * @param node current node
     * @return true if subtree rooted at node is AVL, false otherwise.
     */
    private boolean isAVL(Node<E> node) {
        if (node == null) {
            return true;
        }
        int balance = getBalance(node);
        if (balance < -1 || balance > 1) {
            return false;
        }
        return isAVL(node.left) && isAVL(node.right);
    }

    // Main Method for Testing
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        bst.add(10);
        bst.add(5);

        // Print in order
        bst.printInOrder(); // Expected: 20 30 40 50 60 70 80 

        // Test height
        System.out.println("Height of root (50): " + bst.height(bst.root)); // Expected: 3
        System.out.println("Height of 30: " + bst.height(bst.root.left)); // Expected: 2
        System.out.println("Height of 80: " + bst.height(bst.root.right.right)); // Expected: 1

        // Test sumDepths
        System.out.println("Sum of Depths: " + bst.sumDepths()); // Expected: 12
        // Test isBST
        System.out.println("Is BST: " + bst.isBST()); // Expected: true
        // Test isAVL
        System.out.println("Is AVL: " + bst.isAVL()); // Expected: false
        // Test findAndPrint2LNodes
        System.out.print("2L Nodes: ");
        bst.findAndPrint2LNodes();
    }
}
