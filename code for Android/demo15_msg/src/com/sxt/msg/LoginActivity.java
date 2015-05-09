package com.sxt.msg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 本类结合ReciverActivity使用
 * 短信接收和Activity返回参数
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
			public void onClick(View arg0) {//跳转去注册  验证码激活			
				Intent it = new Intent(LoginActivity.this, ReciverActivity.class);
				startActivityForResult(it, RESULT_FIRST_USER);//注意：启动activity 不使用startActivity()
			}
		});
	}
	/**
	 * activtiy返回时用来接收参数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data==null){//如果点击的back按键返回时 是null
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
