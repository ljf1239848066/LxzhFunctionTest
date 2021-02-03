package com.lxzh123.funcdemo.algo.sortalgo;

/**
 * description $
 * author      Created by lxzh
 * date        2021/2/3
 */
public class ThreadSorter {
    private volatile int index = 0;
    private int[] mArray;

    public ThreadSorter() {
        this.index = 0;
    }

    public void sort(int[] array, boolean asc) {
        mArray = array;
        this.index = 0;
        int len = array.length;
        SortThread[] sortThreads = new SortThread[len];
        if (asc) {
            for (int i = 0; i < len; i++) {
                sortThreads[i] = new SortThread(array[i], array[i]);
                sortThreads[i].start();
            }
        } else {
            int max = array[0];
            for (int i = 0; i < len; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            max += 1;
            for (int i = 0; i < len; i++) {
                sortThreads[i] = new SortThread(max - array[i], array[i]);
                sortThreads[i].start();
            }
        }
        try {
            for (int i = 0; i < len; i++) {
                sortThreads[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void update(int value) {
        mArray[index++] = value;
    }

    class SortThread extends Thread {
        private int time;
        private int value;

        public SortThread(int time, int value) {
            this.time = time;
            this.value = value;
        }

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(time * 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            update(value);
        }
    }
}
