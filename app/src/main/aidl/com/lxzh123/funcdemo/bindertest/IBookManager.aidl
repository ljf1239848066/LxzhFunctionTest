// IBookManager.aidl
package com.lxzh123.funcdemo.bindertest;

import com.lxzh123.funcdemo.bindertest.Book;

interface IBookManager {
    void addBook(in Book book);
    List<Book> getBookList();
}
