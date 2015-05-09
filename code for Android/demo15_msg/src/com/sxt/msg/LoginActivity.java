package com.sxt.msg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * ������ReciverActivityʹ��
 * ���Ž��պ�Activity���ز���
 *
 */
public class LoginActivity extends Activity{
	private Button btnReg;
	private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		btnReg = (Button) findViewById(R.id.btn_regNow);
		tv = (TextView) findViewById(R.id.tv_welcome);
		
		btnReg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {//��תȥע��  ��֤�뼤��			
				Intent it = new Intent(LoginActivity.this, ReciverActivity.class);
				startActivityForResult(it, RESULT_FIRST_USER);//ע�⣺����activity ��ʹ��startActivity()
			}
		});
	}
	/**
	 * activtiy����ʱ�������ղ���
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data==null){//��������back��������ʱ ��null
			return;
		}
		System.out.println(requestCode+"======"+resultCode);
		if(requestCode==RESULT_FIRST_USER){
			Bundle bundle = data.getExtras();
			String phone = bundle.getString("phone");
			tv.setText(phone);
		}
	}
	
}
