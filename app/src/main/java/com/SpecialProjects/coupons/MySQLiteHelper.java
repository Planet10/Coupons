package com.SpecialProjects.coupons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David Smith on 7/5/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "couponLocations.db";

    public static final String TABLE_NAME = "coordinates";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_DATE = "date";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                COLUMN_LAT + TEXT_TYPE + COMMA_SEP +
                                COLUMN_LONG + TEXT_TYPE + COMMA_SEP +
                                COLUMN_DATE + TEXT_TYPE + " TIMESTAMP NOT NULL DEFAULT current_timestamp" + ")";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }

    //Database Creation statement


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create the database
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

   //Delete Location Records
    void DeleteLocationRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null){
            db.execSQL("DELETE FROM " + TABLE_NAME);
            db.close();
        }

    }

    //Add new Coordinates
    void addCoordinates(Coordinates coordinates){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LAT, coordinates.get_latitude());
        values.put(COLUMN_LONG, coordinates.get_longitude());
        values.put(COLUMN_DATE, dateFormat.format(date));

        db.insert(TABLE_NAME,null,values);
        Log.i("values"," values: " + values);
        db.close();
    }

    public List<Coordinates> getAllCoordinates() {
        List <Coordinates> coordList;
        coordList = new ArrayList<Coordinates>();
        //Select all query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //loop thru all rows and add to list
        if(cursor.moveToFirst()){
            do{
                Coordinates coord = new Coordinates();
                coord.set_id(Integer.parseInt(cursor.getString(0)));
                coord.set_latitude(Double.parseDouble(cursor.getString(1)));
                coord.set_longitude(Double.parseDouble(cursor.getString(2)));

                //add coord to list
                coordList.add(coord);
               }while (cursor.moveToNext());
        }
        //return the coord list
        return coordList;
    }
    public void openAndQueryDb(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT latitude, longitude FROM " + TABLE_NAME ;

        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String lat = cursor.getString(cursor.getColumnIndex("latitude"));
            Log.i("dbLatitude", " latitude from db " + lat);
        }
        cursor.close();
        db.close();
    }
}


