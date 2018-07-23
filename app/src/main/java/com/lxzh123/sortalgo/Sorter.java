package com.lxzh123.sortalgo;

/**
 * description: 常用排序公共类
 * author:      Created by a1239848066 on 2018/7/20.
 */
public class Sorter {
    /**
     * 数组元素交换
     * @param array 待交换的数组
     * @param i     待交换元素1索引
     * @param j     待交换元素2索引
     */
    private static void Swap(int[] array,int i,int j){
        int tmp=array[i];
        array[i]=array[j];
        array[j]=tmp;
    }

    /**
     * 冒泡排序
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    public static void BubbleSort(int[] array,boolean asc){
        int len=array.length;
        if(asc){
            for(int i=0;i<len-1;i++){
                for(int j=i+1;j<len;j++){
                    if(array[i]>array[j]){
                        Swap(array,i,j);
                    }
                }
            }
        }else{
            for(int i=0;i<len-1;i++){
                for(int j=i+1;j<len;j++){
                    if(array[i]<array[j]){
                        Swap(array,i,j);
                    }
                }
            }
        }
    }

    /**
     * 插入排序
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
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

    /**
     * 选择排序
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
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

    /**
     * 希尔排序 参考:https://www.cnblogs.com/alsf/p/6606287.html
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
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

    /**
     * 堆排序函数 参考:https://www.cnblogs.com/chengxiao/p/6129630.html
     * @param array 待排序的数组
     * @param asc 是否为升序排序模式 true:升序 false:降序
     */
    public static void HeapSort(int[] array,boolean asc){
        int len=array.length;
        for(int i=len/2-1;i>=0;i--){
            AdjustHeap(array,i,len,asc);
        }
        for(int i=len-1;i>0;i--){
            Swap(array,0,i);//将堆顶元素与最后一个元素交换
            AdjustHeap(array,0,i,asc);//重新调整余下len-1个元素
        }
    }

    /**
     * 堆排序 堆调整函数
     * @param array 堆数组
     * @param i 待调整的节点头元素索引
     * @param len 待调整的数组长度
     * @param asc 是否为升序排序模式 true:升序 false:降序
     */
    private static void AdjustHeap(int[] array,int i,int len,boolean asc){
        int tmp=array[i];//先取出当前元素i
        if(asc){//升序采用大顶堆
            for(int k=2*i+1;k<len;k=k*2+1){
                if(k+1<len&&array[k]<array[k+1]){//如果左子节点小于右子节点，k指向右子节点
                    k++;
                }
                if(tmp<array[k]){//如果跟节点小于子节点
                    array[i]=array[k];//根节点赋值为较大子节点
                    i=k;//跟节点指向右子节点 迭代
                }else{
                    break;
                }
            }
        }else{//降序采用小顶堆
            for(int k=2*i+1;k<len;k=k*2+1){
                if(k+1<len&&array[k]>array[k+1]){//如果左子节点大于右子节点，k指向右子节点
                    k++;
                }
                if(tmp>array[k]){//如果跟节点大于子节点
                    array[i]=array[k];//根节点赋值为较小子节点
                    i=k;//跟节点指向右子节点 迭代
                }else{
                    break;
                }
            }
        }
        array[i]=tmp;//右子节点赋值为跟节点 完成调整
    }

    /**
     * 归并排序 参考:https://www.cnblogs.com/chengxiao/p/6194356.html
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    public static void MergeSort(int[] array,boolean asc){
        int len=array.length;
        int[] tmp=new int[len];
        MergeSortDivide(array,0,len-1,tmp,asc);
    }

    /**
     * 归并排序(分治)——分
     * @param array 待排序的数组
     * @param l     左
     * @param r     右
     * @param tmp   与array相同大小的临时数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    private static void MergeSortDivide(int[] array,int l,int r,int[] tmp,boolean asc){
        if(l<r){
            int mid=(l+r)/2;
            MergeSortDivide(array,l,mid,tmp,asc);
            MergeSortDivide(array,mid+1,r,tmp,asc);
            MergeSortMerge(array,l,mid,r,tmp,asc);
        }
    }

    /**
     * 归并排序(分治)——治(合)
     * @param array 待排序的数组
     * @param l     左
     * @param mid   中
     * @param r     右
     * @param tmp   与array相同大小的临时数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    private static void MergeSortMerge(int[] array,int l,int mid,int r,int[] tmp,boolean asc){
        int i=l;
        int j=mid+1;
        int k=0;
        if(asc){
            while(i<=mid&&j<=r){
                if(array[i]<array[j]){
                    tmp[k++]=array[i++];
                }else{
                    tmp[k++]=array[j++];
                }
            }
        }else{
            while(i<=mid&&j<=r){
                if(array[i]>array[j]){
                    tmp[k++]=array[i++];
                }else{
                    tmp[k++]=array[j++];
                }
            }
        }

        while(i<=mid){
            tmp[k++]=array[i++];
        }
        while(j<=r){
            tmp[k++]=array[j++];
        }
        k=0;
        while(l<=r){
            array[l++]=tmp[k++];
        }
    }

    /**
     * 快速排序
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    public static void QuickSort(int[] array,boolean asc){
        int len=array.length;
        QuickSort(array,0,len-1,asc);
    }

    /**
     * 快速排序——递归函数
     * @param array 待排序的数组
     * @param l     左
     * @param r     右
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    private static void QuickSort(int[] array,int l,int r,boolean asc){
        if(l>=r){
            return;
        }
        int j=QuickSortPartion(array,l,r,asc);
        QuickSort(array,l,j-1,asc);
        QuickSort(array,j+1,r,asc);
    }

    /**
     * 快速排序——分区操作
     * @param array 待排序的数组
     * @param l     左
     * @param r     右
     * @param asc   是否为升序排序模式 true:升序 false:降序
     * @return
     */
    private static int QuickSortPartion(int[] array,int l,int r,boolean asc){
        int i=l,j=r+1;
        int tmp=array[l];
        if(asc){
            while(true){
                while(array[++i]<tmp){
                    if(i==r){
                        break;
                    }
                }
                while(tmp<array[--j]){
                    if(j==l){
                        break;
                    }
                }
                if(i>=j){
                    break;
                }
                Swap(array,i,j);
            }
        }else{
            while(true){
                while(array[++i]>tmp){
                    if(i==r){
                        break;
                    }
                }
                while(tmp>array[--j]){
                    if(j==l){
                        break;
                    }
                }
                if(i>=j){
                    break;
                }
                Swap(array,i,j);
            }
        }

        Swap(array,l,j);
        return j;
    }
}
