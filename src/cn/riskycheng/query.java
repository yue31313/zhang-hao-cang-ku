package cn.riskycheng;

import java.util.ArrayList;
import java.util.HashMap;

 



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class query extends Activity {
	
	Intent intent;
	ListView list;
	String id,keyword,account,password,remind;//查询到的字段
	Cursor cursor;
	String indexID;
	private Button backButton,indexbButton;
	HashMap<String,Object> map;
	base helper;
	ArrayList<String> idList = new ArrayList<String>();
	private int back = 0;//判断按几次back
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		list = (ListView)findViewById(R.id.preview_listview);//拿到ListView控件
		list.setOnItemClickListener(new ListOnItem());//ListView点击监听器
		list.setOnItemLongClickListener(new ListOnItemLong());
		list.setOnCreateContextMenuListener(new ListOnCreate());//ListView长按监听器
		 
		 
		helper = new base(query.this,"information.db");
		
		SQLiteDatabase db = helper.getReadableDatabase();
		cursor = db.query("information", new String[]{"ID","keyword","account","password","remind"}, null, null, null, null, "ID");//查询数据
		ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();//创建ListView
		while(cursor.moveToNext()){  //判断下一个下标是否有内容
			id = cursor.getString(cursor.getColumnIndex("ID"));//ID
			keyword = cursor.getString(cursor.getColumnIndex("keyword")); 
			account = cursor.getString(cursor.getColumnIndex("account")); 
			password = cursor.getString(cursor.getColumnIndex("password"));
			remind = cursor.getString(cursor.getColumnIndex("remind"));
			idList.add(id);
			backButton=(Button)findViewById(R.id.preview_item_back);
			backButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					 Intent intent=new Intent();
					 intent.setClass(query.this, search_record.class);
					 startActivity(intent);
                     query.this.finish();
				}
			});
		   indexbButton=(Button)findViewById(R.id.preview_item_index);
		   indexbButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent();
				 intent.setClass(query.this, AndroidcaseActivity.class);
				 startActivity(intent);
				 query.this.finish();
				
			}
		});
			 
			map = new HashMap<String,Object>(); 
			map.put("Itemkeyword", "检索关键字："+keyword);
			map.put("Itemaccount", "账号："+account);
			map.put("Itempassword","密码："+ password);
			map.put("Itemremind","备注："+ remind);
			listItem.add(map);
		}
		SimpleAdapter listAdapter = new SimpleAdapter(query.this,listItem, R.layout.preview_item,new String[]{"Itemkeyword","Itemaccount","Itempassword","Itemremind"},new int[]{R.id.check_textview01,R.id.check_textview02,R.id.check_textview03,R.id.check_textview04});
		list.setAdapter(listAdapter);//添加到适配器并且显示
	}
	
	class appendButton implements OnClickListener{//添加按钮监听器
		public void onClick(View v) {//点击按钮后跳到输入页面（Append.java）
			intent = new Intent(query.this, add_record.class);
			startActivity(intent);
			query.this.finish();
		}
	}
	class ListOnItem implements OnItemClickListener{//ListView点击监听器
		 
		public void onItemClick(AdapterView<?> adapterView, View v, int position,long arg3) {//index是list中被选中元素的下标，从0开始
			indexID = idList.get(position);
		//	check();//点击打开
		}
	}
	class ListOnItemLong implements OnItemLongClickListener{//ListView长按监听器
	 
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			indexID = idList.get(position);
			return false;
		}
	}
	class ListOnCreate implements OnCreateContextMenuListener{//ListView长按监听器弹出菜单
		 
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.setHeaderTitle("操作");
			//menu.add(0,0,0,"打开");
			menu.add(0,1,0,"删除");
			//menu.add(0,2,0,"编辑");
		}
	}
	 
	public boolean onContextItemSelected(MenuItem item) {//长按弹出菜单响应函数
		switch (item.getItemId()) {
		/*case 0:
			check();//长按菜单打开
			break;*/
		
		case 1://删除
			SQLiteDatabase db = helper.getWritableDatabase();
			db.execSQL("delete from information where ID="+"'"+indexID+"'"+";");
			intent = new Intent(query.this, query.class);
			startActivity(intent);
			query.this.finish();
		 
			
		/*case 2://编辑
			intent = new Intent(query.this,update.class);
			intent.putExtra("Index", indexID);
			startActivity(intent);
			query.this.finish();
			break;*/
		}
		return super.onContextItemSelected(item);
	}
	
	/*public void check(){//查看、打开
		
		intent = new Intent(query.this,check.class);
		Bundle bundle=new Bundle();
		bundle.putString("Index", indexID);
		intent.putExtras(bundle);
		startActivity(intent);
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//back退出
		if(keyCode == KeyEvent.KEYCODE_BACK){
			back++;
			switch (back) {
			case 1:
				Toast.makeText(query.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				break;
			case 2:
				back = 0;//初始化back值
				query.this.finish();
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
