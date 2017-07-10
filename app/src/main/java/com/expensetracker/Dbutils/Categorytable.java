package com.expensetracker.Dbutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.expensetracker.Model.Category;

import java.util.ArrayList;

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

    public void insert_expense(Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("Insert into " + TABLE + " (" + KEY_Name + "," + KEY_ExpenseID + ") VALUES  " + "(?,?)");

        stmt.bindString(1, category.getName());
        stmt.bindLong(2, category.getExpenseID());

        stmt.executeInsert();

    }

    public void delete_expense(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("Delete from" + TABLE + " where id=?");
        stmt.bindLong(1, id);
        stmt.executeUpdateDelete();

    }

    public ArrayList<Category> get_all_expenses() {

        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT  * FROM " + TABLE;
        Cursor cursor = db.rawQuery(s, null);

        while (cursor.moveToNext()) {
            categories.add(new Category(cursor.getInt(1), cursor.getString(2), cursor.getInt(3)));
        }

        return categories;
    }

    public void update_expense(Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("update " + TABLE + " set " + KEY_Name + "=?," + KEY_ExpenseID + "=? where " + KEY_ID + "=?");
        stmt.bindString(1, category.getName());
        stmt.bindLong(2, category.getExpenseID());
        stmt.executeUpdateDelete();

    }


    public Category get_single_expense(int id) {

        Category category = null;
        SQLiteDatabase db = this.getWritableDatabase();

        String s = "SELECT  * FROM " + TABLE + " where " + KEY_ID + "=?;";
        Cursor cursor = db.rawQuery(s, null);

        while (cursor.moveToNext()) {
            category = new Category(cursor.getInt(1), cursor.getString(2), cursor.getInt(3));
        }

        return category;
    }


}
