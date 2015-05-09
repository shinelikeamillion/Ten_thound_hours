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
	private LinearLayout newLinearLayout; //新建按钮布局
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
				Toast.makeText(CloudClientActivity.this, "运行结果："+msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 1:
				progressDialog.dismiss();
				Toast.makeText(CloudClientActivity.this, "出现异常！编译失败！", Toast.LENGTH_LONG).show();
				break;
			case 2:
				progressDialog.setMessage("正在进行编译...");
				break;
			}
        }
    };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 获取屏幕宽度
		winWidth = this.getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();

		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 自定义标题。当需要自定义标题时必须指定
		setContentView(R.layout.main);
		// 设置全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.title);
		
		 //进度条
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在连接云服务器...");
		
		 //从window视图中获取显示IP的文本对象
        ipTitle = (TextView) this.getWindow().findViewById(R.id.textIp);
        ipTitle.setText("当前云服务器："+SysConfig.defaultip);
		 //快捷输入列表
		shorcutLinearLayout = (LinearLayout) findViewById(R.id.shortcutLayout);
		shorcutLinearLayout.getLayoutParams().width = winWidth / 3;

		 //编辑框
		edtCode = (EditText) findViewById(R.id.editCode);
		edtCode.setOnTouchListener(new MyOnTouch());
		edtCode.setText(R.string.initCode);
		
		//新建按钮
        newLinearLayout = (LinearLayout) findViewById(R.id.newLayout);
        
        //添加事件
        newButton = (Button) findViewById(R.id.newButton);
        newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				onClickShow();
			}
		});
        
        //默认隐藏
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
     * 初始化Listview
     */
    public void initListView(){
    	//获得values资源中的array节点资源
    	String[] codes = this.getResources().getStringArray(R.array.list);
    	
    	//适配listView 列表中每项样式可以在R.layout.item中自定义,也可以默认android.R.layout.simple_list_item_1  	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, codes);
    	ListView listView = (ListView) findViewById(R.id.shortcutListView);
    	listView.setAdapter(adapter);
    	
    	//文本资源的id
    	final int[] str = {R.string.for_insert,R.string.if_insert,R.string.syso_insert,R.string.int_insert,
    				 R.string.String_insert,R.string.boolean_insert};
    	
    	//注册监听事件
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				int postion = edtCode.getSelectionStart();//获得选择文本的开始位置 光标位置
				edtCode.getText().insert(postion, getResources().getString(str[index]));				
			}
		});
    }
    /**
     * 点击菜单事件
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId()==R.id.itemIp){
    		final EditText edit = new EditText(this);
    		AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
    		exitAlert.setTitle("请输入服务器IP");
    		exitAlert.setView(edit);//用编辑框作为视图
    		exitAlert.setPositiveButton("设置", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					SysConfig.defaultip = edit.getText().toString(); 
					ipTitle.setText("当前云服务器："+SysConfig.defaultip);
					Toast.makeText(CloudClientActivity.this, "设置成功！", Toast.LENGTH_LONG).show();
				}
			});
    		exitAlert.setNegativeButton("取消", null);
    		exitAlert.show();
    		   		
    	}else if(item.getItemId()==R.id.itempExit){ //退出事件
    		AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
    		exitAlert.setTitle("提示");
    		exitAlert.setMessage("确定退出么？");
    		exitAlert.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
    		exitAlert.setNegativeButton("取消", null);
    		exitAlert.show();
    	}else if(item.getItemId()==R.id.itemRun){//开始云编译
//    		Toast.makeText(this, "Run", Toast.LENGTH_LONG).show();
    		progressDialog.show();//显示进度
    		new ClientThread(edtCode.getText().toString(),myHandler).start();
    	}
    	return super.onOptionsItemSelected(item);
    }
    /**
     * 定义新建按钮点击弹出框
     */
    private void onClickShow(){
    	final EditText edit = new EditText(this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("输入类名");
		alertDialog.setView(edit);
		alertDialog.setPositiveButton("新建", new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				newLinearLayout.setVisibility(View.GONE);
				String claName = edit.getText().toString();
				String codeDemo = getResources().getString(R.string.CodeDemo);//获取代码模版
				codeDemo = codeDemo.replace("$claName$", claName);
				
				edtCode.setText(codeDemo);
			}
		});
		alertDialog.setNegativeButton("取消", null);
		alertDialog.create();
		alertDialog.show();
    }
    

	/**
	 * 滑动监听器
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