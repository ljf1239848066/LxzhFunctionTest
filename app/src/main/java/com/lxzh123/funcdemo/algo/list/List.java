package com.lxzh123.funcdemo.algo.list;

public class List {

    class ListNode<T> {
        ListNode next;
        T data;

        public ListNode() {
        }

        public ListNode(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "" + data;
        }
    }

    private ListNode<Integer> root;

    public List(int[] array) {
        root = buildList(array);
    }

    public ListNode buildList(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        ListNode root, cur;
        root = cur = new ListNode(array[0]);
        for (int i = 1; i < array.length; i++) {
            cur.next = new ListNode(array[i]);
            cur = cur.next;
        }
        return root;
    }

    public static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode cur = head;
        while (cur != null) {
            sb.append(cur.data);
            sb.append(" ");
            cur = cur.next;
        }
        System.out.println(sb.toString());
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    /**
     * 反转链表局部
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseRange(ListNode head, int m, int n) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode frontEnd;
        ListNode frontNewNext;
        int i = 0;
        while (i < m) {
            cur = (pre = cur).next;
            i++;
        }
        frontEnd = pre; // 记录反转区间前一个节点，指针指向反转后反转区间新的头节点
        frontNewNext = cur; // 记录反转区间第一个节点，反转后指针指向反转区间后第一个节点
        while (cur != null && i <= n) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
            i++;
        }
        frontEnd.next = pre; // 反转区间新的头结点
        frontNewNext.next = cur; // 反转区间之后的第一个节点
        return head;
    }

    public void printList() {
        printList(root);
    }

    public void reverseList() {
        root = reverseList(root);
    }

    public void reverseRange(int m, int n) {
        root = reverseRange(root, m, n);
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3 ,4 ,5};
        List list = new List(array);
        System.out.println("反转前:");
        list.printList();

        list.reverseList();
        System.out.println("反转后:");
        list.printList();

        list.reverseList();
        System.out.println("反转后:");
        list.printList();

        System.out.println("局部反转[1, 3]后:");
        list.reverseRange(1, 3);
        list.printList();

        System.out.println("局部反转[2, 5]后:");
        list.reverseRange(2, 5);
        list.printList();
    }
}
