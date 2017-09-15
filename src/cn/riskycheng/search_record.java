package cn.riskycheng;

import java.security.PublicKey;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class search_record extends Activity {
	private Button button_search,button_back;
	private int  back=0;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query);
		button_search=(Button) findViewById(R.id.search_button01);
		button_back=(Button) findViewById(R.id.search_button02);
		final EditText searchText=(EditText) findViewById(R.id.search_edittext01);
		
		
	button_search.setOnClickListener(new OnClickListener() {
		public void onClick(View arg0) {
			 Intent intent=new Intent();
			
			 	Bundle bundle=new Bundle();
			 bundle.putString("searchkey", searchText.getText().toString());
			 intent.setClass(search_record.this, search_result.class);
			 intent.putExtras(bundle);
				startActivity(intent);
				search_record.this.finish();
			}
			
		});
		button_back.setOnClickListener(new OnClickListener() {
	     	public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(search_record.this, AndroidcaseActivity.class);
				startActivity(intent);
				search_record.this.finish();
			}
		});
	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//back退出
		if(keyCode == KeyEvent.KEYCODE_BACK){
			back++;
			switch (back) {
			case 1:
				Toast.makeText(search_record.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				break;
			case 2:
				back = 0;//初始化back值
				search_record.this.finish();
				android.os.Process.killProcess(android.os.Process.myPid());//关闭进程
				break;
			}
			return true;//设置成false让back失效    ，true表示 不失效
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}
}
