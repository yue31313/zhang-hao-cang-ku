package cn.riskycheng;

import javax.security.auth.PrivateCredentialPermission;

import android.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.riskycheng.base;

public class add_record extends Activity {
     private Button add_record_buttonadd,add_record_buttonback;
 	 private EditText add_record_edittext01,add_record_edittext02,add_record_edittext03,add_record_edittext04;
     private Context context;
     private int back=0;
 	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);
        add_record_buttonadd=(Button) findViewById(R.id.add_record_add);
        add_record_buttonback=(Button) findViewById(R.id.add_record_back);
        add_record_edittext01=(EditText) findViewById(R.id.add_record_edittext01);
        add_record_edittext02=(EditText) findViewById(R.id.add_record_edittext02);
        add_record_edittext03=(EditText) findViewById(R.id.add_record_edittext03);
        add_record_edittext04=(EditText) findViewById(R.id.add_record_edittext04);
        base helperBase=new base(add_record.this, "information.db");
        add_record_buttonadd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
				AddData();
				
			}
		});
        add_record_buttonback.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent =new Intent();
				intent.setClass(add_record.this,AndroidcaseActivity.class);
				startActivity(intent);
				add_record.this.finish();
				
			}
		});
        
     	
 	 }
 	 public void AddData(){
 		base base01=new base(this.getBaseContext(),"information.db");
 		String user_keyword=add_record_edittext01.getText().toString();
 		String user_account=add_record_edittext02.getText().toString();
 		String user_password=add_record_edittext03.getText().toString();
 		String user_remind=add_record_edittext04.getText().toString();
 		if (user_keyword.equals("")||user_account.equals("")||user_password.equals("")) {
			Toast.makeText(this, "input basic info!", Toast.LENGTH_SHORT).show();
			return;
		}
 		base01.add(user_keyword, user_account, user_password, user_remind);
 		Toast.makeText(this, "Add Successed!", Toast.LENGTH_SHORT).show();

 	 }

 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//back退出
		if(keyCode == KeyEvent.KEYCODE_BACK){
			back++;
			switch (back) {
			case 1:
				Toast.makeText(add_record.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				break;
			case 2:
				back = 0;//初始化back值
				add_record.this.finish();
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
	  
 	

	