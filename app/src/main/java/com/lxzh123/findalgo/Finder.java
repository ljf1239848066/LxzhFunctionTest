package com.lxzh123.findalgo;

/**
 * description: 常用查找算法公共类
 * author:      Created by a1239848066 on 2018/7/27.
 */
public class Finder {

    /**
     * 顺序查找
     * @param array 待查询的数组列表
     * @param val   待查询的值
     * @return 返回val在数组中的索引，未找到时返回-1
     */
    public static int SequentialFind(int[] array,int val){
        int len=array.length;
        for(int i=0;i<len;i++){
            if(array[i]==val){
                return i;
            }
        }
        return -1;
    }
}
