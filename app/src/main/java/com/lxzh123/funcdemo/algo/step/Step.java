package com.lxzh123.funcdemo.algo.step;

/**
 * description 台阶问题
 *             n级台阶，一次只能上1步或2步，总共有多少种走法
 * author      Created by lxzh
 * date        2018/9/4
 */
public class Step {
    /**
     * 递归解法 复杂问题的解法拆分为子问题的解法
     * @param n
     * @return
     */
    public static int Fun1(int n){
        if(n<=2)return n;
        else return Fun1(n-2)+Fun1(n-1);
    }

    /**
     * 递归解法优化版
     * 利用空间换时间，减少已经计算过的的重复计算时间(节省函数的入栈出栈，以及上下文保存时间)
     * @param n
     * @return
     */
    public static int Fun2(int n){
        if(n<=0)return n;
        int[] memo=new int[n+1];
        for(int i=0;i<=n;i++){
            memo[i]=-1;
        }
        return Fun2Sub(n,memo);
    }

    private static int Fun2Sub(int n,int[] memo){
        if(memo[n]!=-1)return memo[n];
        if(n<2)memo[n]=n;
        else memo[n]=Fun2Sub(n-2,memo)+Fun2Sub(n-1,memo);
        return memo[n];
    }

    /**
     * 迭代解法 用空间换时间，记录已经计算过的记录
     *        缺点是n越大需要的额外空间越大
     *        对于经常用到的情况可以考虑将数组作为全局变量，可以大大节省重复计算时间
     * @param n
     * @return
     */
    public static int Fun3(int n){
        if(n<=0)return n;
        int[] memo=new int[n+1];
        memo[0]=0;
        memo[1]=1;
        for(int i=2;i<=n;i++){
            memo[i]=memo[i-2]+memo[i-1];
        }
        return memo[n];
    }

    /**
     * 迭代解法优化版 用前面两个数循环记录递归中的f(n-2)与f(n-1)
     * @param n
     * @return
     */
    public static int Fun4(int n){
        if(n<2)return n;
        int first=1,second=2;
        int third=0;
        for(int i=3;i<=n;i++){
            third=first+second;
            first=second;
            second=third;
        }
        return third;
    }


}
