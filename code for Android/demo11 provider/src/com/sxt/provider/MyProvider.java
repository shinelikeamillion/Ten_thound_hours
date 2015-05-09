package com.sxt.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * 1.在一个工程1中新建类继承ContentProvider 并重写方法 2.在工程1中的项目清单中配置 <provider
 * android:name=".MyProvider" android:authorities="com.sxt.provider"></provider>
 * 注意：这里android:authorities="com.sxt.provider" 将来被使用者调用的URI
 * 
 *3.数据访问者：（在其他应用工程2中） 需要在activity中 ContentResolver resolver =
 * getContentResolver();获得解析器，再使用uri来访问
 * 
 */
public class MyProvider extends ContentProvider {
	public static final String AUTHORITY = "com.sxt.provider";
	public static final String PATH_SINGLE = "people/#";
	public static final String PATH_MULTIPLE = "people";

	public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";// 数据集
	public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";// 单条记录

	public static final String CONTENT_URI_STRING = "content://" + AUTHORITY
			+ "/" + PATH_MULTIPLE;
	public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

	private static final int MULTIPLE_PEOPLE = 1;
	private static final int SINGLE_PEOPLE = 2;

	private static final UriMatcher uriMatcher;
	
	private BaseDB baseDB;
	
	static {
		// 在匹配器中添加数据集样本
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH_SINGLE, MULTIPLE_PEOPLE);
		uriMatcher.addURI(AUTHORITY, PATH_MULTIPLE, SINGLE_PEOPLE);
	}

	/**
	 * uri=== content://com.sxt.provider/people
	 * content://com.sxt.provider/people/1 根据uri格式 来匹配出你要操作的数据类型
	 * 是vnd.android.cursor.dir还是vnd.android.cursor.item
	 */
	public String getType(Uri uri) {
		System.out.println("=======MyProvider=getType============");
		int t = uriMatcher.match(uri);
		
		if (t == MULTIPLE_PEOPLE) {
			return MIME_DIR_PREFIX;
		} else if (t == SINGLE_PEOPLE) {
			return MIME_ITEM_PREFIX;
		}
		return null;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {

		System.out.println("=======MyProvider=delete============");
		return 0;
	}

	//uri:content://com.sxt.provider/people
	public Uri insert(Uri arg0, ContentValues values) {
		System.out.println("=======MyProvider=insert============");
		SQLiteDatabase db = baseDB.open();
		long id = db.insert("userinfo", "uname,age,email", values);//返回值为刚插入的记录主键
		System.out.println(id); 
		
		getContext().getContentResolver().notifyChange(arg0, null);//通知调用者 发生了更新
		System.out.println("=======MyProvider=insert777============");
		return ContentUris.withAppendedId(arg0, id);//content://com.sxt.provider/people/23
	}

	@Override
	public boolean onCreate() {
		baseDB = new BaseDB(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		System.out.println("=======MyProvider=query============");
		SQLiteDatabase db = baseDB.open();
		
		Cursor cur = null;
		
		//区分查询 为数据集 还是单条记录
		int type = uriMatcher.match(uri);
		switch (type) {
		case MULTIPLE_PEOPLE:
			cur = db.query("userinfo", projection, selection, selectionArgs,null,null,sortOrder);
			break;
		case SINGLE_PEOPLE:
			cur = db.query("userinfo", projection, selection, selectionArgs,null,null,null);
			break;
		default:
			break;
		}
	
		return cur;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		System.out.println("=======MyProvider=update============");
		return 0;
	}

}
