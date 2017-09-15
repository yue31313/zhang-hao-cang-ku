package cn.riskycheng;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class base extends SQLiteOpenHelper {
	  static final String TABLENAME="information";
	  final static String TABLE_ID		= "ID";
	  final static String TABLE_KEYWORD  = "keyword";
	  final static String TABLE_ACCOUNT	= "account";
	  final static String TABLE_PASSWORD	= "password";
	  final static String TABLE_REMIND	= "remind";
	  ArrayList<String> arrayList; 
	  final String CREATETABLE="CREATE TABLE "+TABLENAME+" ( "+TABLE_ID +" integer primary key autoincrement, "+TABLE_KEYWORD+" char(40), "+TABLE_ACCOUNT +" char(20), "+TABLE_PASSWORD+" char(20), "+TABLE_REMIND+" vchar(50))";     
	  
	  static final String DBNAME="information.db";
	  static final int DBVERSION=1;
	   
	  
	  public base(Context context,String ver) {
		   
		super(context, DBNAME, null, DBVERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 
		
       db.execSQL(CREATETABLE);		
      Log.d("abc", "created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	public Cursor select() {
	    SQLiteDatabase db = this.getReadableDatabase();
     	Cursor cursor = db.query(TABLENAME, null, null, null, null, null, null);
		return cursor;
		}
	
	public long add(String keyword,String account,String password,String remind ) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_KEYWORD,keyword);
        contentValues.put(TABLE_ACCOUNT,account);
        contentValues.put(TABLE_PASSWORD,password);
        contentValues.put(TABLE_REMIND,remind);
        return db.insert(TABLENAME, null, contentValues);
	}
	 	
	public Cursor search(String keyword_for_search){ 
		final String SEARCH="select * from"+TABLENAME+"( where"+TABLE_KEYWORD+"like %"+keyword_for_search+"%)";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(SEARCH, null);//这边写上你的查询语句
		return cursor;
		}

	 
	 

}

