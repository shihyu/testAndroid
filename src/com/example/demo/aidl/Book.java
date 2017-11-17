package com.example.demo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable{
	public int bookId;
	public String bookName ;
	
	public Book(int bookId, String bookName) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
	}

	public Book(Parcel in){
		bookId = in.readInt();
		bookName = in.readString();
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(bookId);
		dest.writeString(bookName);
		
	}
	
	public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
		
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
		
		@Override
		public Book createFromParcel(Parcel source) {
			return new Book(source);
		}
	};
	
	
	
}
