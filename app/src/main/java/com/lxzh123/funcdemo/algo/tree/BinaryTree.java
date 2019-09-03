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

    private Mode mode = Mode.Recursion;

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

    public void setMode(Mode mode) {
        this.mode = mode;
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
            System.out.print("PreRecursion:");
            printPreOrderByRecursion(root);
        } else {
            System.out.print("PreIteration:");
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
        Stack<TreeNode> stack = new Stack<>();
        TreeNode tmp = root;
        while (tmp != null || !stack.isEmpty()) {
            if (tmp != null) {
                System.out.print(tmp.value.toString());
                if (tmp.hasRight()) {
                    stack.push(tmp.right);
                }
                tmp = tmp.left;
            } else if (!stack.isEmpty()) {
                tmp = stack.pop();
            }
        }
    }

    private void printInOrder() {
        if (mode == Mode.Recursion) {
            System.out.print("InRecursion :");
            printInOrderByRecursion(root);
        } else {
            System.out.print("InIteration :");
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
        Stack<TreeNode> stack = new Stack<>();
        TreeNode tmp = root;
        while (tmp != null || !stack.isEmpty()) {
            if (tmp != null) {
                if (tmp.hasChild()) {
                    stack.push(tmp);
                } else {
                    System.out.print(tmp.value.toString());
                }
                tmp = tmp.left;
            } else if (!stack.isEmpty()) {
                tmp = stack.pop();
                System.out.print(tmp.value.toString());
                tmp = tmp.right;
            }
        }
    }

    private void printPosOrder() {
        if (mode == Mode.Recursion) {
            System.out.print("PosRecursion:");
            printPosOrderByRecursion(root);
        } else {
            System.out.print("PosIteration:");
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
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> stack1 = new Stack<>();
        TreeNode tmp = root;
        boolean isPop = false;
        while (tmp != null || !stack.isEmpty()) {
            if (tmp != null) {
                if (tmp.hasChild()) {
                    if (isPop) {
                        if (tmp.hasRight()) {
                            stack.push(tmp);
                        } else {
                            System.out.print(tmp.value.toString());
                            stack.pop();
                        }
                        tmp = null;
                    } else {
                        stack.push(tmp);
                        tmp = tmp.left;
                    }
                    isPop = false;
                } else {
                    System.out.print(tmp.value.toString());
                    while (!stack.isEmpty()) {
                        TreeNode parent = stack.tryPop();
                        if (parent.right == tmp) {
                            System.out.print(parent.value.toString());
                            tmp = stack.pop();
                        } else {
                            break;
                        }
                    }
                    tmp = null;
                }
            } else if (!stack.isEmpty()) {
                if (stack.size() == 1) {
                    tmp = stack.pop();
                    stack1.push(tmp);
                } else {
                    tmp = stack.tryPop();
                }

                if (tmp.hasRight()) {
                    tmp = tmp.right;
                    isPop = false;
                } else {
                    isPop = true;
                }
            }
        }
        while (!stack1.isEmpty()) {
            tmp = stack1.pop();
            System.out.print(tmp.value.toString());
        }
    }

    private void printLevel() {
        if (mode == Mode.Recursion) {
            System.out.print("LvlRecursion:");
            printLevelByRecursion();
        } else {
            System.out.print("LvlIteration:");
            printLevelByIteration();
        }
    }

    private void printLevelByRecursion() {
        Queue<TreeNode> queue = new Queue<>();
        queue.push(root);
        printLevelByRecursion(queue);
    }

    private void printLevelByRecursion(Queue<TreeNode> queue) {
        Queue<TreeNode> nextLevel = new Queue<>();
        while (!queue.isEmpty()) {
            TreeNode tmp = queue.pop();
            System.out.print(tmp.value.toString());
            if (tmp.hasLeft()) {
                nextLevel.push(tmp.left);
            }
            if (tmp.hasRight()) {
                nextLevel.push(tmp.right);
            }
        }
        if (nextLevel.isEmpty()) {
            return;
        }
        System.out.println("");
        printLevelByRecursion(nextLevel);
    }

    private void printLevelByIteration() {
        Queue<TreeNode> queue = new Queue<>();
        TreeNode tmp = root;
        System.out.print(tmp.value.toString());
        int level = tmp.getLevel();
        while (tmp != null || !queue.isEmpty()) {
            if (tmp != null) {
                if (tmp.hasLeft()) {
                    queue.push(tmp.left);
                }
                if (tmp.hasRight()) {
                    queue.push(tmp.right);
                }
                tmp = null;
            } else {
                tmp = queue.pop();
                if (tmp.getLevel() != level) {
                    System.out.println("");
                    level = tmp.getLevel();
                }
                System.out.print(tmp.value.toString());
            }
        }
    }

    public void printTree() {

    }

    public static void main(String[] args) {
        System.out.println("arrInt1");
        Integer[] arrInt1 = new Integer[]{5, 3, 1, 4, 2, 8, 6, 9, 7};
        BinaryTree<Integer> integerBinaryTree1 = new BinaryTree<>();
        integerBinaryTree1.createTree(arrInt1);
        integerBinaryTree1.print(PrintMode.Pre);
        integerBinaryTree1.print(PrintMode.In);
        integerBinaryTree1.print(PrintMode.Pos);
        integerBinaryTree1.print(PrintMode.Level);
        integerBinaryTree1.setMode(Mode.Iteration);
        integerBinaryTree1.print(PrintMode.Pre);
        integerBinaryTree1.print(PrintMode.In);
        integerBinaryTree1.print(PrintMode.Pos);
        integerBinaryTree1.print(PrintMode.Level);


        System.out.println("arrInt2");
        Integer[] arrInt2 = new Integer[]{5, 3, 1, 4, 2, 8, 7, 9, 6};
        BinaryTree<Integer> integerBinaryTree2 = new BinaryTree<>();
        integerBinaryTree2.createTree(arrInt2);
        integerBinaryTree2.print(PrintMode.Pre);
        integerBinaryTree2.print(PrintMode.In);
        integerBinaryTree2.print(PrintMode.Pos);
        integerBinaryTree2.print(PrintMode.Level);
        integerBinaryTree2.setMode(Mode.Iteration);
        integerBinaryTree2.print(PrintMode.Pre);
        integerBinaryTree2.print(PrintMode.In);
        integerBinaryTree2.print(PrintMode.Pos);
        integerBinaryTree2.print(PrintMode.Level);

        System.out.println("arrInt3");
        Integer[] arrInt3 = new Integer[]{5, 3, 0, 1, 4, 2, 8, 7, 9, 6};
        BinaryTree<Integer> integerBinaryTree3 = new BinaryTree<>();
        integerBinaryTree3.createTree(arrInt3);
        integerBinaryTree3.print(PrintMode.Pre);
        integerBinaryTree3.print(PrintMode.In);
        integerBinaryTree3.print(PrintMode.Pos);
        integerBinaryTree3.print(PrintMode.Level);
        integerBinaryTree3.setMode(Mode.Iteration);
        integerBinaryTree3.print(PrintMode.Pre);
        integerBinaryTree3.print(PrintMode.In);
        integerBinaryTree3.print(PrintMode.Pos);
        integerBinaryTree3.print(PrintMode.Level);
    }
}
