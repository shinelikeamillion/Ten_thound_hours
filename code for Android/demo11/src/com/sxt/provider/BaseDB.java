package com.sxt.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
   
public class BaseDB {
	protected SQLiteDatabase db;
	private static final String DB_NAME = "people.db";// ���ݿ��ļ�����
	protected static final String DB_TABLE = "userinfo";// ������
	private static final int DB_VERSION = 1;//
	private Context context;
	public BaseDB(Context context){
		this.context = context;
	}
	/**
	 * �����ݿ�
	 */
	public SQLiteDatabase open(){
		MyHelper helper = new MyHelper(context, DB_NAME, null, DB_VERSION);
		db = helper.getWritableDatabase();
		return db;
	}
	
	
	
	/**
	 * �ر����ݿ�
	 */
	public void close(){
		if(db!=null && db.isOpen()){
			db.close();
			db = null;
		}
	}
	/**
	 * db������ ���ݿ�����
	 */
	class MyHelper extends SQLiteOpenHelper{

		public MyHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		/**
		 * �����ݿⲻ����ʱ�����Զ�ִ�У�������
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			System.out.println("=========onCreate========");
			String sql = "create table "+DB_TABLE+"(uid integer primary key autoincrement,age int,uname varchar(30),email varchar(50))";
			db.execSQL(sql);
		}

		/**
		 * ����������°汾 ��ִ������
		 */
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			System.out.println("=========onUpgrade===========");
			
		}
		
	}
}
