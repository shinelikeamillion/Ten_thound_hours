package com.sxt.sqlite.dao;

import android.content.Context;
import android.database.Cursor;

import com.sxt.sqlite.BaseDB;
import com.sxt.sqlite.entity.User;
/**
 * Statement
 * PreparedStatemet
 * @author Administrator
 *
 */
public class UserDao extends BaseDB {

	public UserDao(Context context) {
		super(context);
	}
	
	public void addUser(User user){
//		String sql = "insert into "+DB_TABLE+"(uid,uname,age,email)values("+user.getUid()+",'"+user.getUname()+"',"+user.getAge()+",'"+user.getEmail()+"')";
//		super.update(sql);
		String sql = "insert into "+DB_TABLE+"(uid,uname,age,email)values(?,?,?,?)";
		Object[] data = {user.getUid(),user.getUname(),user.getAge(),user.getEmail()};
		super.update(sql, data);
	}
	
	public void updateUser(User user){
//		String sql = "update userinfo set ";
//		super.update(sql);
	}
	/**
	 * 根据主键查详细
	 * @param uid
	 * @return
	 */
	public User getUser(String uid){
		String sql = "select uid,uname,age,email from userinfo where uid=?";
		String[] data = {uid};
		Cursor cur = super.query(sql, data);
		
		try {
			if(cur.moveToNext()){
				User user = new User();
				user.setAge(cur.getInt(cur.getColumnIndex("age")));
				user.setEmail(cur.getString(cur.getColumnIndex("email")));
				user.setUname(cur.getString(cur.getColumnIndex("uname")));
				user.setUid(cur.getInt(cur.getColumnIndex("uid")));
				
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.close();
		}
		
		return null;
	}
}
