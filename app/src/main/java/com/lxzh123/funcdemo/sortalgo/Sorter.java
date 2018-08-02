package com.lxzh123.funcdemo.sortalgo;

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
     * 数组元素交换 直接交换
     * @param array 待交换的数组
     * @param i     待交换元素1索引
     * @param j     待交换元素2索引
     */
    public static void Swap1(int[] array,int i,int j){
        int tmp=array[i];
        array[i]=array[j];
        array[j]=tmp;
    }

    /**
     * 数组元素交换 异或交换
     * @param array 待交换的数组
     * @param i     待交换元素1索引
     * @param j     待交换元素2索引
     */
    public static void Swap2(int[] array,int i,int j){
        array[i]^=array[j];
        array[j]^=array[j];
        array[i]^=array[j];
    }

    /**
     * 数组元素交换 加法交换
     * @param array 待交换的数组
     * @param i     待交换元素1索引
     * @param j     待交换元素2索引
     */
    public static void Swap3(int[] array,int i,int j){
        array[i]=array[i]+array[j];
        array[j]=array[i]-array[j];
        array[i]=array[i]-array[j];
    }

    /**
     * 冒泡排序 前向冒泡
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    public static void BubbleSortForward(int[] array,boolean asc){
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
     * 冒泡排序 后向冒泡
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    public static void BubbleSortBackword(int[] array,boolean asc){
        int len=array.length;
        if(asc){
            for(int i=0;i<len-1;i++){
                for(int j=0;j<len-1-i;j++){
                    if(array[j]>array[j+1]){
                        Swap(array,j,j+1);
                    }
                }
            }
        }else{
            for(int i=0;i<len-1;i++){
                for(int j=0;j<len-1-i;j++){
                    if(array[j]<array[j+1]){
                        Swap(array,j,j+1);
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
        if(asc){
            //每一轮循环未排序序列中找到最小元素的下标min
            int min;
            for(int i=0;i<len-1;i++){
                min=i;
                for(int j=i+1;j<len;j++){
                    if(array[j]<array[min]){
                        min=j;
                    }
                }
                if(i!=min){
                    //最小元素依次与未排序序列首元素交换(放置于已排序序列末尾)
                    Swap(array,i,min);
                }
            }
        }else{
            int max;
            for(int i=0;i<len-1;i++){
                max=i;
                for(int j=i+1;j<len;j++){
                    if(array[j]>array[max]){
                        max=j;
                    }
                }
                if(i!=max){
                    Swap(array,i,max);
                }
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
            //定义中值值将序列从中间分割
            int mid=(l+r)/2;
            //对中值两侧的子序列进行迭代分割
            MergeSortDivide(array,l,mid,tmp,asc);
            MergeSortDivide(array,mid+1,r,tmp,asc);
            //按需合并两个子序列
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
                //依次选择左右两个子序列中较小值填充到新序列
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

        //左序列未遍历完，依次填充到新序列末尾
        while(i<=mid){
            tmp[k++]=array[i++];
        }
        //右序列未遍历完，依次填充到新序列末尾
        while(j<=r){
            tmp[k++]=array[j++];
        }
        k=0;
        //新序列拷贝回原始序列
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

    /**
     * 快速排序
     * @param array 待排序的数组
     * @param asc   是否为升序排序模式 true:升序 false:降序
     */
    public static void QuickSort1(int[] array,boolean asc) {
        int len=array.length;
        QuickSort1(array, 0 , len-1, asc);
    }

    private static void QuickSort1(int[] array, int low, int high,boolean asc) {
        //1,找到递归算法的出口
        if( low > high) {
            return;
        }
        //2, 存
        int i = low;
        int j = high;
        //3,key
        int key = array[low];
        //4，完成一趟排序
        if(asc) {
            while (i < j) {
                //4.1 ，从右往左找到第一个小于key的数
                while (i < j && array[j] > key) {
                    j--;
                }
                // 4.2 从左往右找到第一个大于key的数
                while (i < j && array[i] <= key) {
                    i++;
                }
                //4.3 交换
                if (i < j) {
                    Swap(array, i, j);
                }
            }
        }else{
            while (i < j) {
                //4.1 ，从右往左找到第一个小于key的数
                while (i < j && array[j] < key) {
                    j--;
                }
                // 4.2 从左往右找到第一个大于key的数
                while (i < j && array[i] >= key) {
                    i++;
                }
                //4.3 交换
                if (i < j) {
                    Swap(array, i, j);
                }
            }
        }
        // 4.4，调整key的位置
        Swap(array,i,low);
        //5, 对key左边的数快排
        QuickSort1(array, low, i-1, asc );
        //6, 对key右边的数快排
        QuickSort1(array, i+1, high, asc);
    }

    public static void CountingSort(int[] array, boolean asc) {
        int len=array.length;
        int maxValue=array[0];
        for(int i=1;i<len;i++){
            if(maxValue<array[i]){
                maxValue=array[i];
            }
        }
        CountingSort(array,maxValue,asc);
    }

    private static void CountingSort(int[] array, int maxValue, boolean asc) {
        int[] bucket = new int[maxValue + 1];
        int sortedIndex = 0;
        int len = array.length;
        int bucketLen = maxValue + 1;

        for (int i = 0; i < len; i++) {
            if (bucket[array[i]] > 0) {
                bucket[array[i]] = 0;
            }
            bucket[array[i]]++;
        }

        for (int j = 0; j < bucketLen; j++) {
            while (bucket[j] > 0) {
                array[sortedIndex++] = j;
                bucket[j]--;
            }
        }
    }

    /*
    public static void RadixSort(int[] array, boolean asc) {
        int len=array.length;
        int n=1;//代表位数对应的数：1,10,100...
        int k=0;//保存每一位排序后的结果用于下一位的排序输入
        int[][] bucket=new int[10][len];//排序桶用于保存每次排序后的结果，这一位上排序结果相同的数字放在同一个桶里
        int[] order=new int[len];//用于保存每个桶里有多少个数字
        while(n<d)
        {
            for(int num:array) //将数组array里的每个数字放在相应的桶里
            {
                int digit=(num/n)%10;
                bucket[digit][order[digit]]=num;
                order[digit]++;
            }
            for(int i=0;i<len;i++)//将前一个循环生成的桶里的数据覆盖到原数组中用于保存这一位的排序结果
            {
                if(order[i]!=0)//这个桶里有数据，从上到下遍历这个桶并将数据保存到原数组中
                {
                    for(int j=0;j<order[i];j++)
                    {
                        array[k]=bucket[i][j];
                        k++;
                    }
                }
                order[i]=0;//将桶里计数器置0，用于下一次位排序
            }
            n*=10;
            k=0;//将k置0，用于下一轮保存位排序结果
        }
    }*/
}
