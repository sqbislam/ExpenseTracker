package com.example.android.expensetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.expensetracker.data.ExpenseContract.ExpenseEntry;


/*Created by Saqib Islam
* Student of Computer Science Engineering
* Brac University
* ID 16101084
* Completed on 9/07/2017
* Copyrighted by ©Saqib_Islam */

public class ExpenseDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expenses.db";
    public static final int DATABASE_VERSION = 1;

    public ExpenseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLCreateString = "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " ( "
                + ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpenseEntry.COLUMN_EXPENSE + " TEXT, "
                + ExpenseEntry.COLUMN_MONTH + " TEXT"
                + " );";
        db.execSQL(SQLCreateString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


}