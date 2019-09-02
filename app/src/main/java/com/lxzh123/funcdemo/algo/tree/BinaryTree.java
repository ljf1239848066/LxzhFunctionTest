package com.lxzh123.funcdemo.algo.tree;

import static com.lxzh123.funcdemo.algo.tree.TreeNode.NODE_LEFT;
import static com.lxzh123.funcdemo.algo.tree.TreeNode.NODE_RIGHT;
import static com.lxzh123.funcdemo.algo.tree.TreeNode.NODE_ROOT;

/**
 * description 二叉排序树$
 * author      Created by lxzh
 * date        2019-09-03
 */
public class BinaryTree<T extends Comparable> {
    private TreeNode<T> root;

    private static Mode mode = Mode.Recursion;

    enum Mode {
        /**
         * 递归
         */
        Recursion,
        /**
         * 迭代
         */
        Iteration
    }

    public BinaryTree() {
        root = null;
    }

    public static void setMode(Mode mode) {
        BinaryTree.mode = mode;
    }

    private TreeNode makeNode(T value, int level, @TreeNode.NodeType int type) {
        TreeNode node = new TreeNode(value);
        node.makeNodeMode(level, type);
        return node;
    }

    public void clear() {
        if (root != null) {
            clear(root);
        }
    }

    private void clear(TreeNode node) {
        if (node.hasLeft()) {
            clear(node.left);
            node.left = null;
        }
        if (node.hasRight()) {
            clear(node.right);
            node.right = null;
        }
        node = null;
    }

    public void createTree(T[] array) {
        int len = array.length;
        if (root != null) {
            clear();
        }
        root = makeNode(array[0], 0, NODE_ROOT);
        for (int i = 1; i < len; i++) {
            insert(array[i]);
        }
    }

    /**
     * 插入一个节点
     *
     * @param item
     */
    public void insert(T item) {
        if (mode == Mode.Recursion) {
            insertByRecursion(item);
        } else {
            insertByIteration(item);
        }
    }

    /**
     * 插入一个节点(迭代方式)
     *
     * @param item
     */
    private void insertByIteration(T item) {
        if (root == null) {
            root = makeNode(item, 0, NODE_ROOT);
        } else {
            TreeNode tmp = root;
            while (true) {
                if (item.compareTo(tmp.value) < 0) {
                    if (tmp.hasLeft()) {
                        tmp = tmp.left;
                    } else {
                        tmp.left = makeNode(item, tmp.getLevel() + 1, NODE_LEFT);
                        break;
                    }
                } else {
                    if (tmp.hasRight()) {
                        tmp = tmp.right;
                    } else {
                        tmp.right = makeNode(item, tmp.getLevel() + 1, NODE_RIGHT);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 插入一个节点(递归方式)
     *
     * @param item
     */
    private void insertByRecursion(T item) {
        insertByRecursion(root, item);
    }

    /**
     * 插入一个节点(递归方式)
     *
     * @param node
     * @param item
     */
    private void insertByRecursion(TreeNode node, T item) {
        if (item.compareTo(node.value) < 0) {
            if (node.hasLeft()) {
                insertByRecursion(node.left, item);
            } else {
                node.left = makeNode(item, node.getLevel() + 1, NODE_LEFT);
                return;
            }
        } else {
            if (node.hasRight()) {
                insertByRecursion(node.right, item);
            } else {
                node.right = makeNode(item, node.getLevel() + 1, NODE_RIGHT);
                return;
            }
        }
    }

    enum PrintMode {
        Pre,
        In,
        Pos,
        Level
    }

    public void print(PrintMode mode) {
        if (root == null) {
            return;
        }
        switch (mode) {
            case Pre:
                printPreOrder();
                break;
            case In:
                printInOrder();
                break;
            case Pos:
                printPosOrder();
                break;
            case Level:
                printLevel();
                break;
        }
        System.out.println("");
    }

    private void printPreOrder() {
        if (mode == Mode.Recursion) {
            printPreOrderByRecursion(root);
        } else {
            printPreOrderByIteration();
        }
    }

    private void printPreOrderByRecursion(TreeNode root) {
        System.out.print(root.value.toString());
        if (root.hasLeft()) {
            printPreOrderByRecursion(root.left);
        }
        if (root.hasRight()) {
            printPreOrderByRecursion(root.right);
        }
    }

    private void printPreOrderByIteration() {
        Stack<T> stack = new Stack<>();
        TreeNode tmp = root;

        while (tmp!=null || !stack.isEmpty()){
            if(tmp!=null) {
                System.out.print(root.value.toString());
                if(tmp.hasLeft()) {
                    
                }
            }
        }
        if (root.hasLeft()) {
            printPreOrderByRecursion(root.left);
        }
        if (root.hasRight()) {
            printPreOrderByRecursion(root.right);
        }
    }

    private void printInOrder() {
        if (mode == Mode.Recursion) {
            printInOrderByRecursion(root);
        } else {
            printInOrderByIteration();
        }
    }

    private void printInOrderByRecursion(TreeNode root) {
        if (root.hasLeft()) {
            printInOrderByRecursion(root.left);
        }
        System.out.print(root.value.toString());
        if (root.hasRight()) {
            printInOrderByRecursion(root.right);
        }
    }

    private void printInOrderByIteration() {

    }

    private void printPosOrder() {
        if (mode == Mode.Recursion) {
            printPosOrderByRecursion(root);
        } else {
            printPosOrderByIteration();
        }
    }

    private void printPosOrderByRecursion(TreeNode root) {
        if (root.hasLeft()) {
            printPosOrderByRecursion(root.left);
        }
        if (root.hasRight()) {
            printPosOrderByRecursion(root.right);
        }
        System.out.print(root.value.toString());
    }

    private void printPosOrderByIteration() {

    }

    private void printLevel() {
        if (mode == Mode.Recursion) {
            printLevelByRecursion();
        } else {
            printLevelByIteration();
        }
    }

    private void printLevelByRecursion() {

    }

    private void printLevelByIteration() {

    }

    public static void main(String[] args) {
        Integer[] arrInt = new Integer[]{5, 3, 1, 4, 2, 8, 6, 9, 7};
        BinaryTree<Integer> integerBinaryTree = new BinaryTree<>();
        integerBinaryTree.createTree(arrInt);
        integerBinaryTree.print(PrintMode.Pre);
        integerBinaryTree.print(PrintMode.In);
        integerBinaryTree.print(PrintMode.Pos);
    }
}
