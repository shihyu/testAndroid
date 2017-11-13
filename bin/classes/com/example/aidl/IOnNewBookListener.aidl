package com.example.aidl;
import com.example.aidl.Book;

interface IOnNewBookListener{
	void onNewBook(in Book newBook);
}