package com.example.android.expensetracker;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.expensetracker.data.ExpenseContract;
import com.example.android.expensetracker.data.ExpenseDBHelper;

import java.util.Arrays;


/*Created by Saqib Islam
* Student of Computer Science Engineering
* Brac University
* ID 16101084
* Completed on 9/07/2017
* Copyrighted by ©Saqib_Islam */



public class MainActivity extends AppCompatActivity {

    static int day = 1;
    static int[] expenseDay = new int[33];
    static int month = 1;
    boolean monthCheck;
    ExpenseDBHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new ExpenseDBHelper(this);
        monthCheck = check();
        if (check()) {
            expenseDay = currentstate();
            findZero(expenseDay);

        } else {
            setNew();
        }
        show();
        showDay();

        final ToggleButton button = (ToggleButton) findViewById(R.id.Plus);
        final ToggleButton button2 = (ToggleButton) findViewById(R.id.Minus);
        Button dayUp = (Button) findViewById(R.id.dayUp);
        Button dayDown = (Button) findViewById(R.id.dayDown);

        Button ten = (Button) findViewById(R.id.ten);
        Button twenty = (Button) findViewById(R.id.twenty);
        Button fifty = (Button) findViewById(R.id.fifty);
        Button hundred = (Button) findViewById(R.id.hundred);
        Button fivehundred = (Button) findViewById(R.id.fivehundred);
        Button unitUp = (Button) findViewById(R.id.unitUp);
        Button unitDown = (Button) findViewById(R.id.unitDown);

        CompoundButton.OnCheckedChangeListener check = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (buttonView == button) {
                        button.setChecked(true);
                        button2.setChecked(false);
                    }
                    if (buttonView == button2) {
                        button2.setChecked(true);
                        button.setChecked(false);
                    }
                }
            }
        };

        button.setOnCheckedChangeListener(check);
        button2.setOnCheckedChangeListener(check);

        View.OnClickListener Increments = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ten:
                        if (button.isChecked()) {
                            add(10);
                            show();
                        }
                        if (button2.isChecked()) {
                            sub(10);
                            show();
                        }
                        if (!button.isChecked() && !button2.isChecked()) {
                            Toast.makeText(MainActivity.this, "Toggle add or sub", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.twenty:
                        if (button.isChecked()) {
                            add(20);
                            show();
                        }
                        if (button2.isChecked()) {
                            sub(20);
                            show();
                        }
                        if (!button.isChecked() && !button2.isChecked()) {
                            Toast.makeText(MainActivity.this, "Toggle add or sub", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.fifty:
                        if (button.isChecked()) {
                            add(50);
                            show();
                        }
                        if (button2.isChecked()) {
                            sub(50);
                            show();
                        }
                        if (!button.isChecked() && !button2.isChecked()) {
                            Toast.makeText(MainActivity.this, "Toggle add or sub", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.hundred:
                        if (button.isChecked()) {
                            add(100);
                            show();
                        }
                        if (button2.isChecked()) {
                            sub(100);
                            show();
                        }
                        if (!button.isChecked() && !button2.isChecked()) {
                            Toast.makeText(MainActivity.this, "Toggle add or sub", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.fivehundred:
                        if (button.isChecked()) {
                            add(500);
                            show();
                        }
                        if (button2.isChecked()) {
                            sub(500);
                            show();
                        }
                        if (!button.isChecked() && !button2.isChecked()) {
                            Toast.makeText(MainActivity.this, "Toggle add or sub", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }
        };
        ten.setOnClickListener(Increments);
        twenty.setOnClickListener(Increments);
        fifty.setOnClickListener(Increments);
        hundred.setOnClickListener(Increments);
        fivehundred.setOnClickListener(Increments);

        unitUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(1);
                show();
            }
        });

        unitDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub(1);
                show();

            }
        });


        dayUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementDay();
                showDay();
                show();
            }
        });

        dayDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementDay();
                showDay();
                show();
            }
        });


        Button history = (Button) findViewById(R.id.History);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthCheck) {
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Save Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button save = (Button) findViewById(R.id.saveChanges);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(expenseDay);
                monthCheck = true;
                Toast.makeText(MainActivity.this, "Changes Saved!", Toast.LENGTH_LONG).show();
            }
        });

        Button month = (Button) findViewById(R.id.monthadd);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

    }

    public void add(int value) {
        expenseDay[day] = expenseDay[day] + value;
    }

    public void sub(int value) {
        expenseDay[day] = expenseDay[day] - value;
        if (expenseDay[day] < 0) {
            expenseDay[day] = 0;
        }
    }

    public void show() {
        TextView disp = (TextView) findViewById(R.id.Total);
        disp.setText(Integer.toString(expenseDay[day]) + ".0");
    }

    public void showDay() {
        TextView daydisp = (TextView) findViewById(R.id.dayview);
        daydisp.setText(Integer.toString(day));
    }

    public void incrementDay() {
        if (day == 31) {
            day = 1;
        } else {
            day++;
        }
    }

    public void decrementDay() {
        if (day == 1)
            day = 1;
        else day--;
    }

    private void insertData() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to add new month? Changes to current month cannot be made later").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                expenseDay = new int[33];
                String ex = Arrays.toString(expenseDay);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE, ex);
                values.put(ExpenseContract.ExpenseEntry.COLUMN_MONTH, Integer.toString(month + 1));
                long res = db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);
                currentstate();
                show();
                findZero(expenseDay);
                showDay();
                db.close();


                Log.v("MainActivity.this", "Executed " + res);
            }
        }).setNegativeButton("No", null)
                .show();
    }

    private void updateData(int[] expense) {
        String s = Arrays.toString(expense);
        SQLiteDatabase dbW = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE, s);
        String selection = ExpenseContract.ExpenseEntry.COLUMN_MONTH + "=?";
        String[] selectionArgs = {Integer.toString(month)};
        dbW.update(ExpenseContract.ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
        dbW.close();
    }

    private void getData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {ExpenseContract.ExpenseEntry._ID, ExpenseContract.ExpenseEntry.COLUMN_EXPENSE};
        String selection = ExpenseContract.ExpenseEntry._ID + "=?";
        String[] selectionArgs = {"5"};
        Cursor c = db.query(ExpenseContract.ExpenseEntry.TABLE_NAME, projection, null, null, null, null, null);
        c.moveToLast();
        int colIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE);
        String s = c.getString(colIndex);
        c.close();
        db.close();
        Log.v("MainActivity.this", s);
    }

    private int[] currentstate() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {ExpenseContract.ExpenseEntry._ID, ExpenseContract.ExpenseEntry.COLUMN_EXPENSE, ExpenseContract.ExpenseEntry.COLUMN_MONTH};
        Cursor c = db.query(ExpenseContract.ExpenseEntry.TABLE_NAME, projection, null, null, null, null, null);
        c.moveToLast();
        int colIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE);
        String s = c.getString(colIndex);
        int col = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_MONTH);
        String m = c.getString(col);
        month = Integer.parseInt(m);
        Log.v("MainActivity.this", s);
        String[] sArr = s.split(", ");
        Log.v("MainActivity.this", sArr[2]);
        int[] arr = new int[sArr.length];
        for (int z = 1; z < arr.length - 1; z++) {
            arr[z] = Integer.parseInt(sArr[z]);
            Log.v("MainActivity.this", "" + arr[z]);
        }
        c.close();
        db.close();
        return arr;

    }

    private boolean check() {
        boolean check;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {ExpenseContract.ExpenseEntry._ID, ExpenseContract.ExpenseEntry.COLUMN_EXPENSE, ExpenseContract.ExpenseEntry.COLUMN_MONTH};
        Cursor c = db.query(ExpenseContract.ExpenseEntry.TABLE_NAME, projection, null, null, null, null, null);
        check = c.moveToFirst();
        c.close();
        db.close();
        return check;

    }

    private void findZero(int[] expenseDay) {

        for (int c = 1; c < expenseDay.length; c++) {
            if (expenseDay[c] == 0) {
                day = c;
                break;
            }
        }
    }

    private void setNew() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_MONTH, Integer.toString(month));
        long res = db.insert(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateData(expenseDay);
        monthCheck = true;
    }


    boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            MainActivity.this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}