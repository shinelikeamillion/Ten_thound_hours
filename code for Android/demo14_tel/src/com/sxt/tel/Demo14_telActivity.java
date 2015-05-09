package com.sxt.tel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * <!-- 拨打电话的权限 -->
	<uses-permission android:name="android.permission.CALL_PHONE" />
 *
 */
public class Demo14_telActivity extends Activity {
    private Button btnCall;
    private Button btnShowCom;
    
    private EditText numberEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        numberEdit = (EditText) findViewById(R.id.editText_number);
        btnCall = (Button) findViewById(R.id.button_call);
        btnShowCom = (Button) findViewById(R.id.button_company);
        
        btnCall.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				call();
			}
		});
        
        btnShowCom.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showCompany();
			}
		});
    }
    
    /**
     * 拨打电话号码
     * new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
     */
    private void call(){
    	
    	Intent it = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+numberEdit.getText()));
    	startActivity(it);
    }
    
    /**
     * 输入事件
     */
    public void inputNum(View view){
    	if(view instanceof Button){
    		Button btn = (Button)view;
    		String numberText = btn.getText().toString();
    		
    		//对删除 和清空 单独处理 
    		if(view.getId()==R.id.buttonDel){
    			System.out.println(numberEdit.length()+"======"+numberText);
    			numberEdit.getText().delete(numberEdit.length()-1, numberEdit.length());
    			return;
    		}else if(view.getId()==R.id.buttonClear){
    			numberEdit.getText().clear();
    			return;
    		}
    				   		
    		numberEdit.append(numberText);
    	}
    }
    
    //查询电话运营商 
    private void showCompany(){
    	
    	//获得电话服务管理器
    	TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	String deviceId = telManager.getDeviceId();//设备编号
    	
    	String operator = telManager.getSimOperator();//获取SIM卡所有者
    	System.out.println(operator+"*****"+telManager.getNetworkCountryIso()+"===");//国家代号
    	System.out.println("-----------"+telManager.getSimOperatorName());//操作系统
		String sm = "";
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")) {
				sm=" 中国移动";
			} else if (operator.equals("46001")) {
				sm=" 中国联通";// 中国联通
			} else if (operator.equals("46003")) {
				sm=" 中国电信";// 中国电信
			}
		}
		Toast.makeText(getApplicationContext(), sm+operator+"|"+telManager.getLine1Number(), Toast.LENGTH_LONG).show(); 
    }
}