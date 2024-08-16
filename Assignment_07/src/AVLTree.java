/**
 * The AVLTree class represents a self-balancing binary search tree where the heights of the two child subtrees of any
 * node differ by no more than one. It provides insertion, deletion, and searching operations, all maintaining the
 * balanced nature of the tree.
 */
public class AVLTree {

    /**
     * The Node class represents a single node of the AVL tree, containing a stock, pointers to the left and right
     * children, and the height of the node.
     */
    private class Node {
        Stock stock;     // The stock information stored in the node.
        Node left, right; // Pointers to left and right child nodes.
        int height;       // The height of this node in the tree.

        /**
         * Constructs a node with the given stock.
         * @param stock The stock data to be stored in this node.
         */
        Node(Stock stock) {
            this.stock = stock;
            this.height = 1;
        }
    }

    private Node root; // Root of the AVL tree.

    /**
     * Retrieves the height of a given node.
     * @param node The node whose height is to be computed.
     * @return The height of the node, or 0 if the node is null.
     */
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    /**
     * Computes the balance factor of the given node as the difference in height between its right and left children.
     * @param node The node whose balance factor is to be computed.
     * @return The balance factor of the node.
     */
    private int getBalanceFactor(Node node) {
        return (node == null) ? 0 : height(node.right) - height(node.left);
    }

    /**
     * Updates the height of the given node based on the heights of its children.
     * @param node The node whose height is to be updated.
     */
    private void updateHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * Performs a left rotation on the given node.
     * @param x The node around which the left rotation is to be performed.
     * @return The new root after the rotation.
     */
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /**
     * Performs a right rotation on the given node.
     * @param y The node around which the right rotation is to be performed.
     * @return The new root after the rotation.
     */
    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    /**
     * Balances the subtree rooted at the given node if it has become unbalanced.
     * @param node The root node of the subtree to balance.
     * @return The new root of the balanced subtree.
     */
    private Node balance(Node node) {
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.right) < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.left) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        return node;
    }

    /**
     * Inserts a new stock into the AVL tree, maintaining the balance of the tree.
     * @param node The root node of the subtree into which the stock is to be inserted.
     * @param stock The stock to insert.
     * @return The new root of the balanced subtree.
     */
    private Node insert(Node node, Stock stock) {
        if (node == null) return new Node(stock);
        if (stock.getSymbol().compareTo(node.stock.getSymbol()) < 0) {
            node.left = insert(node.left, stock);
        } else if (stock.getSymbol().compareTo(node.stock.getSymbol()) > 0) {
            node.right = insert(node.right, stock);
        } else {
            // Update existing node
            node.stock.setPrice(stock.getPrice());
            node.stock.setVolume(stock.getVolume());
            node.stock.setMarketCap(stock.getMarketCap());
            return node;
        }
        return balance(node);
    }

    /**
     * Finds the node with the minimum key value found in the given tree.
     * @param node The root node of the subtree.
     * @return The node with the minimum key value.
     */
    private Node findMin(Node node) {
        return (node.left == null) ? node : findMin(node.left);
    }

    /**
     * Removes a stock from the AVL tree, identified by the symbol, maintaining the balance of the tree.
     * @param node The root node of the subtree from which the stock is to be removed.
     * @param symbol The stock symbol of the stock to remove.
     * @return The new root of the balanced subtree.
     */
    private Node remove(Node node, String symbol) {
        if (node == null) return null;
        if (symbol.compareTo(node.stock.getSymbol()) < 0) {
            node.left = remove(node.left, symbol);
        } else if (symbol.compareTo(node.stock.getSymbol()) > 0) {
            node.right = remove(node.right, symbol);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node temp = findMin(node.right);
                node.stock = temp.stock;
                node.right = remove(node.right, temp.stock.getSymbol());
            }
        }
        return (node == null) ? null : balance(node);
    }

    /**
     * Searches for a stock in the AVL tree by its symbol.
     * @param node The root node of the subtree to search through.
     * @param symbol The symbol of the stock to find.
     * @return The node containing the stock, or null if not found.
     */
    private Node search(Node node, String symbol) {
        if (node == null || symbol.equals(node.stock.getSymbol())) return node;
        return search((symbol.compareTo(node.stock.getSymbol()) < 0) ? node.left : node.right, symbol);
    }

    /**
     * Public method to insert a stock into the AVL tree.
     * @param stock The stock to insert.
     */
    public void insert(Stock stock) {
        root = insert(root, stock);
    }

    /**
     * Public method to search for a stock by its symbol.
     * @param symbol The symbol of the stock to search for.
     * @return The stock found, or null if no such stock exists.
     */
    public Stock search(String symbol) {
        Node result = search(root, symbol);
        return (result == null) ? null : result.stock;
    }

    /**
     * Public method to remove a stock from the AVL tree by its symbol.
     * @param symbol The symbol of the stock to remove.
     */
    public void delete(String symbol) {
        root = remove(root, symbol);
    }

    // Traversal methods remain similar, refactor if needed
    /**
     * Performs an in-order traversal of the AVL tree, printing each stock in the tree.
     */
    public void inOrderTraversal() {
        traverseInOrder(root);
    }

    /**
     * Helper method to perform an in-order traversal of a subtree.
     * @param node The root node of the subtree to traverse.
     */
    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(node.stock);
            traverseInOrder(node.right);
        }
    }

    /**
     * Performs a pre-order traversal of the AVL tree, printing each stock in the tree.
     */
    public void preOrderTraversal() {
        traversePreOrder(root);
    }

    /**
     * Helper method to perform a pre-order traversal of a subtree.
     * @param node The root node of the subtree to traverse.
     */
    private void traversePreOrder(Node node) {
        if (node != null) {
            System.out.println(node.stock);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    /**
     * Performs a post-order traversal of the AVL tree, printing each stock in the tree.
     */
    public void postOrderTraversal() {
        traversePostOrder(root);
    }

    /**
     * Helper method to perform a post-order traversal of a subtree.
     * @param node The root node of the subtree to traverse.
     */
    private void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.println(node.stock);
        }
    }
}
