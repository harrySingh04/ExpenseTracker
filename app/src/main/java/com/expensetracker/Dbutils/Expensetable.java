package com.expensetracker.Dbutils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.expensetracker.Model.ExpenseModel;

public class Expensetable extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "Expensetracker";
    private static final String TABLE = "ExpenseModel";
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

    public void insert_expense(ExpenseModel expenseModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("Insert into " + TABLE + " (" + KEY_Amount + "," + KEY_Date + ") VALUES  " + "(?,?)");

        stmt.bindLong(1, expenseModel.getAmount());
        stmt.bindString(2, expenseModel.getDate());

        stmt.executeInsert();

    }

    public void delete_expense(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("Delete from" + TABLE + " where id=?");

        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();

    }

//    public ArrayList<ExpenseModel> get_all_expenses() {
//
//        ArrayList<ExpenseModel> expense = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String s = "SELECT  * FROM " + TABLE;
//        Cursor cursor = db.rawQuery(s, null);
//
//        while (cursor.moveToNext()) {
//            expense.add(new ExpenseModel(cursor.getInt(1), cursor.getInt(2), cursor.getString(3)));
//        }
//
//        return expense;
//    }

    public void update_expense(ExpenseModel expenseModel) {


        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("update " + TABLE + " set " + KEY_Amount + "=?," + KEY_Date + "=? where " + KEY_ID + "=?");

        stmt.bindLong(1, expenseModel.getAmount());
        stmt.bindString(2, expenseModel.getDate());
        stmt.bindString(3, expenseModel.getDate());
        stmt.executeUpdateDelete();


    }

//    public ExpenseModel get_single_expense(int id) {
//
//        ExpenseModel expense = null;
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String s = "SELECT  * FROM " + TABLE + " where "+KEY_ID+"=?;";
//        Cursor cursor = db.rawQuery(s, null);
//
//        while (cursor.moveToNext()) {
//            expense = new ExpenseModel(cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
//        }
//
//        return expense;
//
//
//    }


}
