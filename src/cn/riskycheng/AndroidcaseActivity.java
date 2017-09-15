package cn.riskycheng;

import cn.riskycheng.AndroidcaseActivity;
import cn.riskycheng.R;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DialerFilter;
import android.widget.ImageButton;
import android.widget.Toast;

public class AndroidcaseActivity<SQLiteQuery, SQLiteCursorDriver> extends Activity {
	private ImageButton button_add,button_search,button_preview,button_about;
     
    private SQLiteDatabase database;
    private int back=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button_add=(ImageButton) findViewById(R.id.button_add);
        button_add.setImageDrawable(getResources().getDrawable(R.drawable.add_record));
        button_add.setOnClickListener(new mybutton());
        button_search=(ImageButton) findViewById(R.id.button_search);
        button_search.setImageDrawable(getResources().getDrawable(R.drawable.seek_record));
        button_search.setOnClickListener(new mybutton());
        button_preview=(ImageButton) findViewById(R.id.button_preview);
        button_preview.setImageDrawable(getResources().getDrawable(R.drawable.record_preview));
        button_preview.setOnClickListener(new mybutton());
        button_about=(ImageButton) findViewById(R.id.button_about);
        button_about.setImageDrawable(getResources().getDrawable(R.drawable.about_us));
        button_about.setOnClickListener(new mybutton());
        
			  
        SQLiteDatabase DataBase=this.openOrCreateDatabase("information.db", Context.MODE_APPEND,null);  
    }  
     
    
    class mybutton implements  OnClickListener{
    	public void onClick(View v) {
    		switch(v.getId()){
    		case R.id.button_add:
    			Intent  intent=new Intent();
				intent.setClass(AndroidcaseActivity.this, add_record.class);
				startActivity(intent);
				AndroidcaseActivity.this.finish(); 
				break;
    		case R.id.button_search:
    			Intent  intent1=new Intent();
				intent1.setClass(AndroidcaseActivity.this, search_record.class);
				startActivity(intent1);
				AndroidcaseActivity.this.finish();
				break;
    		case R.id.button_preview:
    			Intent  intent2=new Intent();
				intent2.setClass(AndroidcaseActivity.this, query.class);
				startActivity(intent2);
				AndroidcaseActivity.this.finish(); 
				break;
    		case R.id.button_about:
    			new AlertDialog.Builder(AndroidcaseActivity.this).setTitle("关于我们").setMessage("作者：riskycheng"+"\n"+"email:1182849753@qq.com").setPositiveButton("确定", null).show();  
    				 
    				  
    				 }
    				 
    	}
    }  
    public boolean onKeyDown(int keyCode, KeyEvent event) {//back退出
		if(keyCode == KeyEvent.KEYCODE_BACK){
			back++;
			switch (back) {
			case 1:
				Toast.makeText(AndroidcaseActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				break;
			case 2:
				back = 0;//初始化back值
				AndroidcaseActivity.this.finish();
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