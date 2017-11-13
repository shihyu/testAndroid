package com.example.aidl;
import com.example.aidl.Book;
import com.example.aidl.IOnNewBookListener;

interface IBookManager{
	List<Book> getBookList();
	void addBook(in Book book);
	
	void registerListener(IOnNewBookListener listener);
	void unregistenerListener(IOnNewBookListener listener);
	
}