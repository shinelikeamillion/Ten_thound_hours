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
 * <!-- ����绰��Ȩ�� -->
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
     * ����绰����
     * new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
     */
    private void call(){
    	
    	Intent it = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+numberEdit.getText()));
    	startActivity(it);
    }
    
    /**
     * �����¼�
     */
    public void inputNum(View view){
    	if(view instanceof Button){
    		Button btn = (Button)view;
    		String numberText = btn.getText().toString();
    		
    		//��ɾ�� ����� �������� 
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
    
    //��ѯ�绰��Ӫ�� 
    private void showCompany(){
    	
    	//��õ绰���������
    	TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	String deviceId = telManager.getDeviceId();//�豸���
    	
    	String operator = telManager.getSimOperator();//��ȡSIM��������
    	System.out.println(operator+"*****"+telManager.getNetworkCountryIso()+"===");//���Ҵ���
    	System.out.println("-----------"+telManager.getSimOperatorName());//����ϵͳ
		String sm = "";
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")) {
				sm=" �й��ƶ�";
			} else if (operator.equals("46001")) {
				sm=" �й���ͨ";// �й���ͨ
			} else if (operator.equals("46003")) {
				sm=" �й�����";// �й�����
			}
		}
		Toast.makeText(getApplicationContext(), sm+operator+"|"+telManager.getLine1Number(), Toast.LENGTH_LONG).show(); 
    }
}