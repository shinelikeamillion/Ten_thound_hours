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
 * 1.��һ������1���½���̳�ContentProvider ����д���� 2.�ڹ���1�е���Ŀ�嵥������ <provider
 * android:name=".MyProvider" android:authorities="com.sxt.provider"></provider>
 * ע�⣺����android:authorities="com.sxt.provider" ������ʹ���ߵ��õ�URI
 * 
 *3.���ݷ����ߣ���������Ӧ�ù���2�У� ��Ҫ��activity�� ContentResolver resolver =
 * getContentResolver();��ý���������ʹ��uri������
 * 
 */
public class MyProvider extends ContentProvider {
	public static final String AUTHORITY = "com.sxt.provider";
	public static final String PATH_SINGLE = "people/#";
	public static final String PATH_MULTIPLE = "people";

	public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";// ���ݼ�
	public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";// ������¼

	public static final String CONTENT_URI_STRING = "content://" + AUTHORITY
			+ "/" + PATH_MULTIPLE;
	public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

	private static final int MULTIPLE_PEOPLE = 1;
	private static final int SINGLE_PEOPLE = 2;

	private static final UriMatcher uriMatcher;
	
	private BaseDB baseDB;
	
	static {
		// ��ƥ������������ݼ�����
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH_SINGLE, MULTIPLE_PEOPLE);
		uriMatcher.addURI(AUTHORITY, PATH_MULTIPLE, SINGLE_PEOPLE);
	}

	/**
	 * uri=== content://com.sxt.provider/people
	 * content://com.sxt.provider/people/1 ����uri��ʽ ��ƥ�����Ҫ��������������
	 * ��vnd.android.cursor.dir����vnd.android.cursor.item
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
		long id = db.insert("userinfo", "uname,age,email", values);//����ֵΪ�ղ���ļ�¼����
		System.out.println(id); 
		
		getContext().getContentResolver().notifyChange(arg0, null);//֪ͨ������ �����˸���
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
		
		//���ֲ�ѯ Ϊ���ݼ� ���ǵ�����¼
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
