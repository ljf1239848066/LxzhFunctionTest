package com.lxzh123.findalgo;

/**
 * description: 常用查找算法公共类
 * author:      Created by a1239848066 on 2018/7/27.
 */
public class Finder {

    /**
     * 顺序查找
     *
     * @param array 待查询的数组列表
     * @param x     待查询的值
     * @return 返回x在数组中的索引，未找到时返回-1
     */
    public static int SequentialSearch(int[] array, int x) {
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i] == x) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 二分查找算法   循环实现
     *
     * @param array 待查询的数组列表(要求已经进行过升序排序)
     * @param x     待查询的值
     * @return 返回x在数组中的索引，未找到时返回-1
     */
    public static int BinarySearch1(int[] array, int x) {
        int len = array.length;
        int sta = 0;
        int end = len - 1;
        int mid = -1;
        while (sta <= end) {
            mid = (sta + end) / 2;
            if (array[mid] > x) {
                end = mid - 1;
            } else if (array[mid] < x) {
                sta = mid + 1;
            } else {
                return mid;
            }
        }
        if (array[mid] != x) {
            mid = -1;
        }
        return mid;
    }

    /**
     * 二分查找算法   递归实现
     *
     * @param array 待查询的数组列表(要求已经进行过升序排序)
     * @param x     待查询的值
     * @return 返回x在数组中的索引，未找到时返回-1
     */
    public static int BinarySearch2(int[] array, int x) {
        int len = array.length;
        return BinarySearch2(array, x, 0, len - 1);
    }

    /**
     * 二分查找算法   递归实现
     *
     * @param array 待查询的数组列表(要求已经进行过升序排序)
     * @param x     待查询的值
     * @return 返回x在数组中的索引，未找到时返回-1
     */
    private static int BinarySearch2(int[] array, int x, int low, int high) {
        int mid = (low+high)/2;
        if (low <= high) {
            if (array[mid] > x) {
                mid = BinarySearch2(array, x, low, mid - 1);
            } else if (array[mid] < x) {
                mid = BinarySearch2(array, x, mid + 1, high);
            }
        }
        return mid;
    }
}
