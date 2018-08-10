package com.lxzh123.funcdemo.bindertest;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
    private String name;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public Book(String name, String price) {
//        this.name = name;
//        this.price = price;
//    }

    public Book(Parcel in){
        this.name=in.readString();
        this.price=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeInt(flags);
    }

    public static final Parcelable.Creator<Book> CREATOR=new Parcelable.Creator<Book>(){
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
