package com.sxt.client;

import com.sxt.client.util.ClientThread;
import com.sxt.client.util.SysConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CloudClientActivity extends Activity {
	private int winWidth;
	private LinearLayout shorcutLinearLayout;
	private LinearLayout newLinearLayout; //�½���ť����
	private Button newButton ;
	private EditText edtCode;
	private TextView ipTitle;
	private ProgressDialog progressDialog;
    private Handler myHandler = new Handler(){
        // @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
			case 0:
				progressDialog.dismiss();
				Toast.makeText(CloudClientActivity.this, "���н����"+msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 1:
				progressDialog.dismiss();
				Toast.makeText(CloudClientActivity.this, "�����쳣������ʧ�ܣ�", Toast.LENGTH_LONG).show();
				break;
			case 2:
				progressDialog.setMessage("���ڽ��б���...");
				break;
			}
        }
    };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ��ȡ��Ļ���
		winWidth = this.getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();

		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// �Զ�����⡣����Ҫ�Զ������ʱ����ָ��
		setContentView(R.layout.main);
		// ����ȫ��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title);
		
		 //������
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("���������Ʒ�����...");
		
		 //��window��ͼ�л�ȡ��ʾIP���ı�����
        ipTitle = (TextView) this.getWindow().findViewById(R.id.textIp);
        ipTitle.setText("��ǰ�Ʒ�������"+SysConfig.defaultip);
		 //��������б�
		shorcutLinearLayout = (LinearLayout) findViewById(R.id.shortcutLayout);
		shorcutLinearLayout.getLayoutParams().width = winWidth / 3;

		 //�༭��
		edtCode = (EditText) findViewById(R.id.editCode);
		edtCode.setOnTouchListener(new MyOnTouch());
		edtCode.setText(R.string.initCode);
		
		//�½���ť
        newLinearLayout = (LinearLayout) findViewById(R.id.newLayout);
        
        //����¼�
        newButton = (Button) findViewById(R.id.newButton);
        newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				onClickShow();
			}
		});
        
        //Ĭ������
        shorcutLinearLayout.setVisibility(View.GONE);
        newLinearLayout.setVisibility(View.GONE);
        
        initListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	/**
     * ��ʼ��Listview
     */
    public void initListView(){
    	//���values��Դ�е�array�ڵ���Դ
    	String[] codes = this.getResources().getStringArray(R.array.list);
    	
    	//����listView �б���ÿ����ʽ������R.layout.item���Զ���,Ҳ����Ĭ��android.R.layout.simple_list_item_1  	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, codes);
    	ListView listView = (ListView) findViewById(R.id.shortcutListView);
    	listView.setAdapter(adapter);
    	
    	//�ı���Դ��id
    	final int[] str = {R.string.for_insert,R.string.if_insert,R.string.syso_insert,R.string.int_insert,
    				 R.string.String_insert,R.string.boolean_insert};
    	
    	//ע������¼�
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				int postion = edtCode.getSelectionStart();//���ѡ���ı��Ŀ�ʼλ�� ���λ��
				edtCode.getText().insert(postion, getResources().getString(str[index]));				
			}
		});
    }
    /**
     * ����˵��¼�
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId()==R.id.itemIp){
    		final EditText edit = new EditText(this);
    		AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
    		exitAlert.setTitle("�����������IP");
    		exitAlert.setView(edit);//�ñ༭����Ϊ��ͼ
    		exitAlert.setPositiveButton("����", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					SysConfig.defaultip = edit.getText().toString(); 
					ipTitle.setText("��ǰ�Ʒ�������"+SysConfig.defaultip);
					Toast.makeText(CloudClientActivity.this, "���óɹ���", Toast.LENGTH_LONG).show();
				}
			});
    		exitAlert.setNegativeButton("ȡ��", null);
    		exitAlert.show();
    		   		
    	}else if(item.getItemId()==R.id.itempExit){ //�˳��¼�
    		AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
    		exitAlert.setTitle("��ʾ");
    		exitAlert.setMessage("ȷ���˳�ô��");
    		exitAlert.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
    		exitAlert.setNegativeButton("ȡ��", null);
    		exitAlert.show();
    	}else if(item.getItemId()==R.id.itemRun){//��ʼ�Ʊ���
//    		Toast.makeText(this, "Run", Toast.LENGTH_LONG).show();
    		progressDialog.show();//��ʾ����
    		new ClientThread(edtCode.getText().toString(),myHandler).start();
    	}
    	return super.onOptionsItemSelected(item);
    }
    /**
     * �����½���ť���������
     */
    private void onClickShow(){
    	final EditText edit = new EditText(this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("��������");
		alertDialog.setView(edit);
		alertDialog.setPositiveButton("�½�", new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				newLinearLayout.setVisibility(View.GONE);
				String claName = edit.getText().toString();
				String codeDemo = getResources().getString(R.string.CodeDemo);//��ȡ����ģ��
				codeDemo = codeDemo.replace("$claName$", claName);
				
				edtCode.setText(codeDemo);
			}
		});
		alertDialog.setNegativeButton("ȡ��", null);
		alertDialog.create();
		alertDialog.show();
    }
    

	/**
	 * ����������
	 */
	class MyOnTouch implements OnTouchListener {
		private float startX;
		private float startY;
		@Override
		public boolean onTouch(View arg0, MotionEvent e) {
			if (e.getAction() == MotionEvent.ACTION_DOWN) {
				startX = e.getX();
				startY = e.getY();
			} else if (e.getAction() == MotionEvent.ACTION_UP) {
				if (e.getX() - startX < -100) {
					shorcutLinearLayout.setVisibility(View.VISIBLE);
					newLinearLayout.setVisibility(View.GONE);
				} else if (e.getX() - startX > 100) {
					shorcutLinearLayout.setVisibility(View.GONE);
				}
				if(startY-e.getY()>100){
					newLinearLayout.setVisibility(View.GONE);
				}else if(e.getY()-startY>100){
					newLinearLayout.setVisibility(View.VISIBLE);
					shorcutLinearLayout.setVisibility(View.GONE);
				}
			}
			return false;
		}
	}
}