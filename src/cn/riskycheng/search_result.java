 
package cn.riskycheng;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
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

public class search_result extends Activity {
	
	Intent intent;
	ListView list;
	String Id,Keyword,Account,Password,Remind;//��ѯ�����ֶ�
	Cursor cursor;
	String indexID;
	HashMap<String,Object> map;
	base helper;
	private Button backButton,indexbButton;
	ArrayList<String> idList = new ArrayList<String>();
	private int back = 0;//�жϰ�����back
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		list = (ListView)findViewById(R.id.preview_listview);//�õ�ListView�ؼ�
		list.setOnItemClickListener(new ListOnItem());//ListView���������
		list.setOnItemLongClickListener(new ListOnItemLong());
		list.setOnCreateContextMenuListener(new ListOnCreate());//ListView����������
		backButton=(Button)findViewById(R.id.preview_item_back);
		backButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				 Intent intent=new Intent();
				 intent.setClass(search_result.this, search_record.class);
				 startActivity(intent);
				 search_result.this.finish();
			}
		});
	   indexbButton=(Button)findViewById(R.id.preview_item_index);
	   indexbButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent=new Intent();
			 intent.setClass(search_result.this, AndroidcaseActivity.class);
			 startActivity(intent);
			 search_result.this.finish();
			
		}
	});
		 
		helper = new base(search_result.this,"information.db");
		SQLiteDatabase db = helper.getReadableDatabase();
		
		String bundle = (String)getIntent().getExtras().get("searchkey"); 
		  
		 
		 
		 
		cursor = db.query("information", new String[]{"ID","keyword","account","password","remind"},base.TABLE_KEYWORD+" like ?",new String[] { "%"+bundle+"%"}, null, null, "ID" );//��ѯ����
		ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();//����ListView
		while(cursor.moveToNext()){  //�ж���һ���±��Ƿ�������
			Id = cursor.getString(cursor.getColumnIndex("ID"));//ID
			Keyword = cursor.getString(cursor.getColumnIndex("keyword")); 
			Account = cursor.getString(cursor.getColumnIndex("account")); 
			Password = cursor.getString(cursor.getColumnIndex("password"));
			Remind = cursor.getString(cursor.getColumnIndex("remind"));
			idList.add(Id);
			
			map = new HashMap<String,Object>(); 
			map.put("Itemkeyword", "�����ؼ��֣�"+Keyword);
			map.put("Itemaccount", "�˺ţ�"+Account);
			map.put("Itempassword","���룺"+ Password);
			map.put("Itemremind","��ע��"+ Remind);
			listItem.add(map);
		}
		SimpleAdapter listAdapter = new SimpleAdapter(search_result.this,listItem, R.layout.preview_item,new String[]{"Itemkeyword","Itemaccount","Itempassword","Itemremind"},new int[]{R.id.check_textview01,R.id.check_textview02,R.id.check_textview03,R.id.check_textview04});		list.setAdapter(listAdapter);//��ӵ�������������ʾ
	}
	
	class appendButton implements OnClickListener{//��Ӱ�ť������
		public void onClick(View v) {//�����ť����������ҳ�棨Append.java��
			intent = new Intent(search_result.this, add_record.class);
			startActivity(intent);
			search_result.this.finish();
		}
	}
	class ListOnItem implements OnItemClickListener{//ListView���������
		 
		public void onItemClick(AdapterView<?> adapterView, View v, int position,long arg3) {//index��list�б�ѡ��Ԫ�ص��±꣬��0��ʼ
			indexID = idList.get(position);
			//�����
		}
	}
	class ListOnItemLong implements OnItemLongClickListener{//ListView����������
	 
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			indexID = idList.get(position);
			return false;
		}
	}
	class ListOnCreate implements OnCreateContextMenuListener{//ListView���������������˵�
		 
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.setHeaderTitle("����");
			//menu.add(0,0,0,"��");
			menu.add(0,1,0,"ɾ��");
			//menu.add(0,2,0,"�༭");
		}
	}
	 
	public boolean onContextItemSelected(MenuItem item) {//���������˵���Ӧ����
		switch (item.getItemId()) {
		/*case 0:
			check();//�����˵���
			break;*/
		case 1://ɾ��
			SQLiteDatabase db = helper.getWritableDatabase();
			db.execSQL("delete from information where ID="+"'"+indexID+"'"+";");
			intent = new Intent();
			intent.setClass(search_result.this, AndroidcaseActivity.class);
			startActivity(intent);
		
		/*case 2://�༭
			intent = new Intent(search_result.this,update.class);
			intent.putExtra("Index", indexID);
			startActivity(intent);
			search_result.this.finish();
			break;*/
		}
		return super.onContextItemSelected(item);
	}
	
	
	 
	 public boolean onKeyDown(int keyCode, KeyEvent event) {//back�˳�
		if(keyCode == KeyEvent.KEYCODE_BACK){
			back++;
			switch (back) {
			case 1:
				Toast.makeText(search_result.this, "�ٰ�һ���˳�����", Toast.LENGTH_LONG).show();
				break;
			case 2:
				back = 0;//��ʼ��backֵ
				search_result.this.finish();
				android.os.Process.killProcess(android.os.Process.myPid());//�رս���
				break;
			}
			return true;//���ó�false��backʧЧ    ��true��ʾ ��ʧЧ
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	} 
}
