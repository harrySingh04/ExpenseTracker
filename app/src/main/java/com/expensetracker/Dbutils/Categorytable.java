package com.expensetracker.Dbutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 03-07-2017.
 */

public class Categorytable extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "Expensetracker";
    private static final String TABLE = "Category";
    private static final String KEY_ID = "id";
    private static final String KEY_Name = "name";
    private static final String KEY_ExpenseID = "expenseID";


    Context c;

    public Categorytable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
        //   Insert_data();
        //3rd argument to be passed is CursorFactory instance
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //   Log.e("I am in onCreate", "I am in onCreate");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_Name + "TEXT,"
                + KEY_ExpenseID + "INTEGER"

                + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    public void insert_expense() {

    }

    public void delete_expense(int id) {

    }

    public void get_all_expenses() {

        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT  * FROM " + TABLE + " LIMIT 1;";
        Cursor cursor = db.rawQuery(s, null);


    }

    public void edit_expense(int id) {

    }






}
