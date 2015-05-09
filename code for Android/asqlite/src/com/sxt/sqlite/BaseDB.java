package com.sxt.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
   
public class BaseDB {
	protected SQLiteDatabase db;
	private static final String DB_NAME = "people2.db";// 数据库文件名称
	protected static final String DB_TABLE = "userinfo";// 表名称
	private static final int DB_VERSION = 1;//
	private Context context;
	public BaseDB(Context context){
		this.context = context;
	}
	/**
	 * 打开数据库
	 */
	public void open(){
		MyHelper helper = new MyHelper(context, DB_NAME, null, DB_VERSION);
		db = helper.getWritableDatabase();
	}
	
	public Cursor query(String sql,String[] data){
		open();
		Cursor cur = db.rawQuery(sql, data);
		return cur;
	}
	
	/**
	 * 执行数据更新
	 */
	public void update(String sql,Object[] data){
		System.out.println(">>>==="+sql);
		open();
//		db.execSQL(sql);
		db.execSQL(sql, data);
		close();
	}
	
	/**
	 * 关闭数据库
	 */
	public void close(){
		if(db!=null && db.isOpen()){
			db.close();
			db = null;
		}
	}
	/**
	 * db帮助类 数据库助手
	 */
	class MyHelper extends SQLiteOpenHelper{

		public MyHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		/**
		 * 在数据库不存在时，会自动执行，创建表
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			System.out.println("=========onCreate========");
			String sql = "create table "+DB_TABLE+"(uid integer primary key,age int,uname varchar(30),email varchar(50))";
			db.execSQL(sql);
		}

		/**
		 * 如果发现最新版本 则执行这里
		 */
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			System.out.println("=========onUpgrade===========");
			
		}
		
	}
}
