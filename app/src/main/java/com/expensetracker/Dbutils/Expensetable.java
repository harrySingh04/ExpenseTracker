package com.expensetracker.Dbutils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.expensetracker.Model.Expense;

public class Expensetable extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "Expensetracker";
    private static final String TABLE = "Expense";
    private static final String KEY_ID = "id";
    private static final String KEY_Amount = "word";
    private static final String KEY_Date = "word_date";


    Context c;

    public Expensetable(Context context) {
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
                + KEY_Amount + "INTEGER,"
                + KEY_Date + "TEXT"

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

    public void insert_expense(Expense expense) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt =  db.compileStatement("Insert into " + TABLE + " (" +  KEY_Amount + "," + KEY_Date + ") VALUES  " + "(?,?)");

        stmt.bindLong(1, expense.getAmount());
        stmt.bindString(2, expense.getDate());

        stmt.executeInsert();

    }

    public void delete_expense(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt =  db.compileStatement("Delete from" + TABLE + " where id=?");

        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();

    }

    public void get_all_expenses() {

        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT  * FROM " + TABLE + " LIMIT 1;";
        Cursor cursor = db.rawQuery(s, null);

        while(cursor.moveToNext()){

        }


    }

    public void edit_expense(Expensetable expense) {

    }

    public void get_single_expense(int id){

    }


}
