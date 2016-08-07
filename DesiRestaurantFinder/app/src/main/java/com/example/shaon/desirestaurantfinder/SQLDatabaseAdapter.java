package com.example.shaon.desirestaurantfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class SQLDatabaseAdapter {

    SQLHelper helper;

    public SQLDatabaseAdapter(Context context){
        helper = new SQLHelper(context);
    }

    public long insertData(Results result) {

        if(getResultId(result.name)) return 0;


        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHelper.YQLID, result.name);
        contentValues.put(SQLHelper.TITLE, result.address);
        contentValues.put(SQLHelper.ADDRESS, result.city);
        contentValues.put(SQLHelper.PHONE, result.zipcode);
        contentValues.put(SQLHelper.LATITUDE, result.latitude);
        contentValues.put(SQLHelper.LONGITUDE, result.longitude);

        long id = db.insert(SQLHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public Boolean getResultId(String resultId){

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {SQLHelper.YQLID};

        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, SQLHelper.YQLID+" = '"+resultId+"'", null, null, null, null);
        if(!cursor.moveToFirst() || cursor.getCount() == 0) return false;
        Log.d("RESULTS", "FOUND DUPLICATE");
        return true;
    }

    public Cursor fetchAllResults() {
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columns = {SQLHelper.TITLE, SQLHelper.ADDRESS, SQLHelper.PHONE};

        Cursor cursor = db.query(SQLHelper.TABLE_NAME, columns, null, null, null, null, null);



//        ArrayList<Results> result = new ArrayList<>();
//
//	        /*
//	         *
//	         * Silver Diner | 123 Main St | 123456
//	         * 2 | Sam | 123 Someother st
//	         *
//	         */
//
//        while(cursor.moveToNext()) {
//            int indexTitle = cursor.getColumnIndex(SQLHelper.TITLE);
//            int indexAddress = cursor.getColumnIndex(SQLHelper.ADDRESS);
//            int indexPhone = cursor.getColumnIndex(SQLHelper.PHONE);
//
//            String title = cursor.getString(indexTitle);
//            String address = cursor.getString(indexAddress);
//            String phone = cursor.getString(indexPhone);
//
//            Log.d("Results", title +"-"+address);
//
//
//
//        }
//        return  null;
        return cursor;

    }




    static class SQLHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "wtfDatabase";
        private static final String TABLE_NAME = "RESULTS";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "rid";
        private static final String YQLID = "rYQLid";
        private static final String TITLE = "rTitle";
        private static final String ADDRESS = "rAddress";
        private static final String PHONE = "rPhone";
        private static final String LATITUDE = "rLatitude";
        private static final String LONGITUDE = "rLongitude";

        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +" ("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ YQLID +" VARCHAR(255), "+ TITLE +" VARCHAR(255), "+ ADDRESS +" VARCHAR(255), "+ PHONE +" VARCHAR(255), "+ LATITUDE +" REAL, "+ LONGITUDE +" REAL);";
        //private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        //private static final String ALTER_TABLE = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + PHONE + " int DEFAULT 0";

        Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate Called",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL(ALTER_TABLE);
            Toast.makeText(context, "onUpgrade Called",Toast.LENGTH_LONG).show();

        }
    }
}
