package com.lxzh123.funcdemo.algo.tree;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * description 二叉树节点$
 * author      Created by lxzh
 * date        2019-09-03
 */
public class TreeNode<T extends Comparable> {
    public TreeNode left;
    public TreeNode right;
    public T value;
    private static final int NODE_TYPE_SHIFT = 30;
    private static final int NODE_TYPE_MASK = 0x3 << NODE_TYPE_SHIFT;
    private static final int NODE_LEVEL_MASK = ~NODE_TYPE_MASK;

    public static final int NODE_ROOT = 0 << NODE_TYPE_SHIFT;
    public static final int NODE_LEFT = 2 << NODE_TYPE_SHIFT;
    public static final int NODE_RIGHT = 1 << NODE_TYPE_SHIFT;

    @IntDef({NODE_ROOT, NODE_LEFT, NODE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NodeType {
    }

    /**
     * 32bit
     * 30bit level
     * 2bit type
     * 00: root
     * 10: left
     * 01: right
     */
    private int mode;

    public TreeNode(T value) {
        this.value = value;
    }

    public void setType(@NodeType int type) {
        mode = (mode & NODE_LEVEL_MASK) | (type & NODE_TYPE_MASK);
    }

    public void setLevel(int level) {
        mode = (mode & NODE_TYPE_MASK) | (level & NODE_LEVEL_MASK);
    }

    public void makeNodeMode(@IntRange(from = 0, to = (1 << NODE_TYPE_SHIFT) - 1) int level, @NodeType int type) {
        mode = (level & NODE_LEVEL_MASK) | (type & NODE_TYPE_MASK);
    }

    public int getType() {
        return (mode & NODE_TYPE_MASK);
    }

    public int getLevel() {
        return mode & NODE_LEVEL_MASK;
    }

    public boolean hasChild() {
        return (left != null || right != null);
    }

    public boolean isChild(TreeNode node) {
        return (node != null && (node == left || node == right));
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }

    public boolean isRoot() {
        return NODE_ROOT == getType();
    }

    public boolean isLeft() {
        return NODE_LEFT == getType();
    }

    public boolean isRight() {
        return NODE_RIGHT == getType();
    }

    @Override
    public String toString() {
        return "(" + value +
                ")[t=" + getType() +
                ", l=" + getLevel() +
                ']';
    }
}
