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

    public static void InsertSort(int[] array,boolean asc){
        int len=array.length;
        int tmp;
        int j;
        if(asc){
            for(int i=1;i<len;i++){
                tmp=array[i];
                for(j=i;j>0&&tmp<array[j-1];j--){
                    array[j]=array[j-1];
                }
                array[j]=tmp;
            }
        }else{
            for(int i=1;i<len;i++){
                tmp=array[i];
                for(j=i;j>0&&tmp>array[j-1];j--){
                    array[j]=array[j-1];
                }
                array[j]=tmp;
            }
        }
    }

    public static void SelectSort(int[] array,boolean asc){
        int len=array.length;
        int tmp;
        if(asc){
            int min;
            for(int i=0;i<len;i++){
                min=i;
                for(int j=i+1;j<len;j++){
                    if(array[j]<array[min]){
                        min=j;
                    }
                }
                tmp=array[i];
                array[i]=array[min];
                array[min]=tmp;
            }
        }else{
            int max;
            for(int i=0;i<len;i++){
                max=i;
                for(int j=i+1;j<len;j++){
                    if(array[j]>array[max]){
                        max=j;
                    }
                }
                tmp=array[i];
                array[i]=array[max];
                array[max]=tmp;
            }
        }
    }

    public static void ShellSort(int[] array,boolean asc){
        int len=array.length;
        int h=1;
        while(h<len/3){
            h=h*3+1;
        }
        int tmp;
        int j;
        if(asc){
            while(h>=1){
                for(int i=h;i<len;i++){
                    tmp=array[i];
                    for(j=i;j>=h&&tmp<array[j-h];j-=h){
                        array[j]=array[j-h];
                    }
                    array[j]=tmp;
                }
                h=h/3;
            }
        }else{
            while(h>=1){
                for(int i=h;i<len;i++){
                    tmp=array[i];
                    for(j=i;j>=h&&tmp>array[j-h];j-=h){
                        array[j]=array[j-h];
                    }
                    array[j]=tmp;
                }
                h=h/3;
            }
        }
    }

    public static void HeapSort(int[] array,boolean asc){

    }

    public static void MergeSort(int[] array,boolean asc){

    }

    public static void QuickSort(int[] array,boolean asc){

    }
}
