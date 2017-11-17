package com.example.demo.aidl;
import com.example.demo.aidl.Book;
import com.example.demo.aidl.IOnNewBookListener;

interface IBookManager{
	List<Book> getBookList();
	void addBook(in Book book);
	
	void registerListener(IOnNewBookListener listener);
	void unregistenerListener(IOnNewBookListener listener);
	
}