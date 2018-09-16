package com.example.android.expensetracker.data;

import android.provider.BaseColumns;


/*Created by Saqib Islam
* Student of Computer Science Engineering
* Brac University
* ID 16101084
* Completed on 9/07/2017
* Copyrighted by ©Saqib_Islam */


public final class ExpenseContract {

    ExpenseContract(){}

    public static final class ExpenseEntry implements BaseColumns{
        public static final String DATABASE_NAME = "expenses.db";
        public final static String TABLE_NAME="expense";

        public final static String _ID=BaseColumns._ID;
        public final static String COLUMN_EXPENSE="expenses";
        public final static String COLUMN_MONTH="month";




    }
}
