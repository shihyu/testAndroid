package com.example.demo.aidl;
import com.example.demo.aidl.Book;

interface IOnNewBookListener{
	void onNewBook(in Book newBook);
}