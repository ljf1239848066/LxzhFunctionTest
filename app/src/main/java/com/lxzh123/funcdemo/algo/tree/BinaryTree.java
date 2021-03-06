package com.lxzh123.funcdemo.algo.tree;

import static com.lxzh123.funcdemo.algo.tree.TreeNode.NODE_LEFT;
import static com.lxzh123.funcdemo.algo.tree.TreeNode.NODE_RIGHT;
import static com.lxzh123.funcdemo.algo.tree.TreeNode.NODE_ROOT;

/**
 * description 二叉排序树
 * author      Created by lxzh
 * date        2019-09-03
 */
public class BinaryTree<T extends Comparable> {
    /**
     * 遍历打印方式
     */
    public enum PrintMode {
        /**
         * 前序遍历
         */
        Pre,
        /**
         * 中序遍历
         */
        In,
        /**
         * 后序遍历
         */
        Pos,
        /**
         * 逐层遍历
         */
        Level
    }

    /**
     * 算法实现模式
     */
    enum AlgorithmMode {
        /**
         * 递归
         */
        Recursion,
        /**
         * 迭代
         */
        Iteration
    }

    /**
     * 根节点
     */
    private TreeNode<T> root;

    /**
     * 默认算法实现模式
     */
    private AlgorithmMode mode = AlgorithmMode.Recursion;

    public BinaryTree() {
        root = null;
    }

    public void setMode(AlgorithmMode mode) {
        this.mode = mode;
    }

    private TreeNode makeNode(T value, int level, @TreeNode.NodeType int type) {
        TreeNode node = new TreeNode(value);
        node.makeNodeMode(level, type);
        return node;
    }

    /**
     * 清空二叉排序树
     */
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

    /**
     * 根据数据创建二叉排序树
     * @param array
     */
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
        if (mode == AlgorithmMode.Recursion) {
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

    /**
     * 前序遍历
     */
    private void printPreOrder() {
        if (mode == AlgorithmMode.Recursion) {
            System.out.print("PreRecursion:");
            printPreOrderByRecursion(root);
        } else {
            System.out.print("PreIteration:");
            printPreOrderByIteration();
        }
    }

    /**
     * 前序遍历: 递归模式
     *
     * @param root
     */
    private void printPreOrderByRecursion(TreeNode root) {
        System.out.print(root.value.toString());
        if (root.hasLeft()) {
            printPreOrderByRecursion(root.left);
        }
        if (root.hasRight()) {
            printPreOrderByRecursion(root.right);
        }
    }

    /**
     * 前序遍历: 迭代模式
     */
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

    /**
     * 中序遍历
     */
    private void printInOrder() {
        if (mode == AlgorithmMode.Recursion) {
            System.out.print("InRecursion :");
            printInOrderByRecursion(root);
        } else {
            System.out.print("InIteration :");
            printInOrderByIteration();
        }
    }

    /**
     * 中序遍历: 递归模式
     *
     * @param root
     */
    private void printInOrderByRecursion(TreeNode root) {
        if (root.hasLeft()) {
            printInOrderByRecursion(root.left);
        }
        System.out.print(root.value.toString());
        if (root.hasRight()) {
            printInOrderByRecursion(root.right);
        }
    }

    /**
     * 中序遍历: 迭代模式
     */
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

    /**
     * 后序遍历
     */
    private void printPosOrder() {
        if (mode == AlgorithmMode.Recursion) {
            System.out.print("PosRecursion:");
            printPosOrderByRecursion(root);
        } else {
            System.out.print("PosIteration:");
            printPosOrderByIteration();
        }
    }

    /**
     * 后序遍历: 递归模式
     *
     * @param root
     */
    private void printPosOrderByRecursion(TreeNode root) {
        if (root.hasLeft()) {
            printPosOrderByRecursion(root.left);
        }
        if (root.hasRight()) {
            printPosOrderByRecursion(root.right);
        }
        System.out.print(root.value.toString());
    }

    /**
     * 后序遍历: 迭代模式
     */
    private void printPosOrderByIteration() {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastNode = null;
        TreeNode tmp = root;
        while (tmp != null || !stack.isEmpty()) {
            if (tmp != null) {
                if (tmp.hasLeft() && (lastNode == null || !tmp.isChild(lastNode))) {
                    stack.push(tmp);
                    tmp = tmp.left;
                    continue;
                }
                if (tmp.hasRight() && lastNode != tmp.right) {
                    stack.push(tmp);
                    tmp = tmp.right;
                    continue;
                }
                System.out.print(tmp.value.toString());
                lastNode = tmp;
                tmp = null;
            } else if (!stack.isEmpty()) {
                tmp = stack.pop();
            }
        }
    }

    /**
     * 逐层遍历
     */
    private void printLevel() {
        if (mode == AlgorithmMode.Recursion) {
            System.out.print("LvlRecursion:");
            printLevelByRecursion();
        } else {
            System.out.print("LvlIteration:");
            printLevelByIteration();
        }
    }

    /**
     * 逐层遍历: 递归模式
     */
    private void printLevelByRecursion() {
        Queue<TreeNode> queue = new Queue<>();
        queue.push(root);
        printLevelByRecursion(queue);
    }

    /**
     * 逐层遍历: 递归模式
     *
     * @param queue
     */
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

    /**
     * 逐层遍历: 迭代模式
     */
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

    /**
     * 根据打印显示规则，获取子树占用的总宽度
     * TODO: 待优化重复调用问题
     * @param node
     * @return
     */
    private int getTreeWidth(TreeNode node) {
        int l = 0;
        int r = 0;
        int w = 0;
        if (node.hasLeft()) {
            l = getTreeWidth(node.left);
        }
        if (node.hasRight()) {
            r = getTreeWidth(node.right);
        }
        w += l > 0 ? l + 1 : 0;
        w += r > 0 ? r + 1 : 0;
        w += node.value.toString().length();
        return w;
    }

    private int getTreeDepth(TreeNode node) {
        int l = 0;
        int r = 0;
        if (node.hasLeft()) {
            l = getTreeDepth(node.left);
        }
        if (node.hasRight()) {
            r = getTreeDepth(node.right);
        }
        return Math.max(l, r) + 1;
    }

    /**
     * 树形结构打印
     * 左子树所有节点在父节点左侧，右子树所有节点在父节点右侧
     * 将叶子树视为一个整体时，父节点与叶子树之间保留一个单位间隙
     */
    public void printTree() {
        if (root == null) {
            return;
        }
        int depth = getTreeDepth(root);
        int width = getTreeWidth(root);
        int height = depth * 2 - 1;
        char[][] outStrArr = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                outStrArr[i][j] = '.';
            }
        }

        fillStrArrByTree(outStrArr, root, 0, 0, false, 0);
        for (int i = 0; i < height; i++) {
            System.out.println(outStrArr[i]);
        }
    }

    private void printCopy(char[] arr, String value, int start) {
        int len = value.length();
        for (int i = 0; i < len; i++) {
            arr[i + start] = value.charAt(i);
        }
    }

    private void fillStrArrByTree(char[][] arr, TreeNode node, int rowIndex, int parentR, boolean isLeft, int x1) {
        int left = node.hasLeft() ? getTreeWidth(node.left) + 1 : 0;
        int width = node.value.toString().length();
        int startX = parentR + left;
        printCopy(arr[rowIndex], node.value.toString(), startX);
        if (!node.isRoot()) {
            if (isLeft) {
                arr[rowIndex - 1][(startX + x1) / 2] = '/';
            } else {
                arr[rowIndex - 1][(startX + x1) / 2] = '\\';
            }
        }
        if (node.hasLeft()) {
            fillStrArrByTree(arr, node.left, rowIndex + 2, parentR, true, startX);
        }
        if (node.hasRight()) {
            fillStrArrByTree(arr, node.right, rowIndex + 2, startX + width + 1, false, startX + width);
        }
    }

    public static void test() {
        System.out.println("arrInt1");
        Integer[] arrInt1 = new Integer[]{5, 3, 1, 4, 2, 8, 6, 9, 7};
        BinaryTree<Integer> integerBinaryTree1 = new BinaryTree<>();
        integerBinaryTree1.createTree(arrInt1);
        integerBinaryTree1.print(PrintMode.Pre);
        integerBinaryTree1.print(PrintMode.In);
        integerBinaryTree1.print(PrintMode.Pos);
        integerBinaryTree1.print(PrintMode.Level);
        integerBinaryTree1.setMode(AlgorithmMode.Iteration);
        integerBinaryTree1.print(PrintMode.Pre);
        integerBinaryTree1.print(PrintMode.In);
        integerBinaryTree1.print(PrintMode.Pos);
        integerBinaryTree1.print(PrintMode.Level);
        integerBinaryTree1.printTree();


        System.out.println("arrInt2");
        Integer[] arrInt2 = new Integer[]{5, 3, 1, 4, 2, 8, 7, 9, 6};
        BinaryTree<Integer> integerBinaryTree2 = new BinaryTree<>();
        integerBinaryTree2.createTree(arrInt2);
        integerBinaryTree2.print(PrintMode.Pre);
        integerBinaryTree2.print(PrintMode.In);
        integerBinaryTree2.print(PrintMode.Pos);
        integerBinaryTree2.print(PrintMode.Level);
        integerBinaryTree2.setMode(AlgorithmMode.Iteration);
        integerBinaryTree2.print(PrintMode.Pre);
        integerBinaryTree2.print(PrintMode.In);
        integerBinaryTree2.print(PrintMode.Pos);
        integerBinaryTree2.print(PrintMode.Level);
        integerBinaryTree2.printTree();

        System.out.println("arrInt3");
        Integer[] arrInt3 = new Integer[]{5, 3, 0, 1, 4, 2, 8, 7, 9, 6};
        BinaryTree<Integer> integerBinaryTree3 = new BinaryTree<>();
        integerBinaryTree3.createTree(arrInt3);
        integerBinaryTree3.print(PrintMode.Pre);
        integerBinaryTree3.print(PrintMode.In);
        integerBinaryTree3.print(PrintMode.Pos);
        integerBinaryTree3.print(PrintMode.Level);
        integerBinaryTree3.setMode(AlgorithmMode.Iteration);
        integerBinaryTree3.print(PrintMode.Pre);
        integerBinaryTree3.print(PrintMode.In);
        integerBinaryTree3.print(PrintMode.Pos);
        integerBinaryTree3.print(PrintMode.Level);
        integerBinaryTree3.printTree();

        System.out.println("arrInt4");
        Integer[] arrInt4 = new Integer[]{6, 3, 0, 1, 4, 5, 2, 9, 8, 10, 7};
        BinaryTree<Integer> integerBinaryTree4 = new BinaryTree<>();
        integerBinaryTree4.createTree(arrInt4);
        integerBinaryTree4.print(PrintMode.Pre);
        integerBinaryTree4.print(PrintMode.In);
        integerBinaryTree4.print(PrintMode.Pos);
        integerBinaryTree4.print(PrintMode.Level);
        integerBinaryTree4.setMode(AlgorithmMode.Iteration);
        integerBinaryTree4.print(PrintMode.Pre);
        integerBinaryTree4.print(PrintMode.In);
        integerBinaryTree4.print(PrintMode.Pos);
        integerBinaryTree4.print(PrintMode.Level);
        integerBinaryTree4.printTree();

        System.out.println("arrStr1");
        String[] arrStr1 = new String[]{"f82js", "9dj2", "23", "dsawo", "2d9qjasss", "djso", "s9sj2", "dsss", "s02jd", "4s9sjk", "fosm2", "d2js", "38sje", "m2ks"};
        BinaryTree<String> strBinaryTree1 = new BinaryTree<>();
        strBinaryTree1.createTree(arrStr1);
        strBinaryTree1.print(PrintMode.Pre);
        strBinaryTree1.print(PrintMode.In);
        strBinaryTree1.print(PrintMode.Pos);
        strBinaryTree1.print(PrintMode.Level);
        strBinaryTree1.setMode(AlgorithmMode.Iteration);
        strBinaryTree1.print(PrintMode.Pre);
        strBinaryTree1.print(PrintMode.In);
        strBinaryTree1.print(PrintMode.Pos);
        strBinaryTree1.print(PrintMode.Level);
        strBinaryTree1.printTree();
    }
}