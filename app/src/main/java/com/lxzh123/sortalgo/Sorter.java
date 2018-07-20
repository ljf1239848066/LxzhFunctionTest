package com.lxzh123.sortalgo;

public class Sorter {
    public static void BubbleSort(int[] array,boolean asc){
        int len=array.length;
        int tmp;
        if(asc){
            for(int i=0;i<len-1;i++){
                for(int j=i+1;j<len;j++){
                    if(array[i]>array[j]){
                        tmp=array[i];
                        array[i]=array[j];
                        array[j]=tmp;
                    }
                }
            }
        }else{
            for(int i=0;i<len-1;i++){
                for(int j=i+1;j<len;j++){
                    if(array[i]<array[j]){
                        tmp=array[i];
                        array[i]=array[j];
                        array[j]=tmp;
                    }
                }
            }
        }
    }
}
