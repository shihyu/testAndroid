package com.example.binder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.aidl.Book;
import com.example.aidl.IBookManager;
import com.example.aidl.IOnNewBookListener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class ServerService extends Service {
	private static final String TAG = "ServerService";
	
	//使用CopyOnWriteArrayList是为了支持并发读写
	private CopyOnWriteArrayList<Book> mList = new CopyOnWriteArrayList<Book>();
	private RemoteCallbackList<IOnNewBookListener> mListeners = new RemoteCallbackList<IOnNewBookListener>();
	
	@Override
	public IBinder onBind(Intent intent) {
		return mStub;
	}
	
	public final IBookManager.Stub mStub = new IBookManager.Stub() {
		@Override
		public List<Book> getBookList() throws RemoteException {
			Log.d(TAG,"addBook-size" + mList.size());
			return mList;
		}
		
		@Override
		public void addBook(Book book) throws RemoteException {
			Log.d(TAG,"addBook name=" + book.bookName + " " + Thread.currentThread().getName());
			if(!mList.contains(book)){
				mList.add(book);
				/*int N = mListeners.beginBroadcast();
				for (int i = 0; i < N ; i++) {
					IOnNewBookListener listener = mListeners.getBroadcastItem(i);
					try {
						listener.onNewBook(new Book(111, "Android xxx"));
					} catch (RemoteException e) {
						Log.d(TAG,"error");
						e.printStackTrace();
					}
				}
				mListeners.finishBroadcast();*/
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						int N = mListeners.beginBroadcast();
						for (int i = 0; i < N ; i++) {
							IOnNewBookListener listener = mListeners.getBroadcastItem(i);
							try {
								listener.onNewBook(new Book(111, "Android xxx"));
							} catch (RemoteException e) {
								Log.d(TAG,"error");
								e.printStackTrace();
							}
						}
						mListeners.finishBroadcast();
						
					}
				});
				thread.start();
				
			}else{
				Log.d(TAG,"addBook already exit" );
			}
		}

		@Override
		public void registerListener(IOnNewBookListener listener)
				throws RemoteException {
			mListeners.register(listener);
		}

		@Override
		public void unregistenerListener(IOnNewBookListener listener)
				throws RemoteException {
			mListeners.unregister(listener);
		}
	};
}
