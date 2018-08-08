package com.lxzh123.funcdemo.unsafetest;

import java.io.Serializable;

public class TestModel implements Serializable {
    private int time;
    private int speed;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance(){
        return speed*time;
    }

    public int getSpeed() {
        return speed;
    }

    public TestModel(int time) {
        this.time = time;
    }
}
