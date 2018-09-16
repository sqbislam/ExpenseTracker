package com.example.android.expensetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.expensetracker.data.ExpenseContract;
import com.example.android.expensetracker.data.ExpenseDBHelper;


/*Created by Saqib Islam
* Student of Computer Science Engineering
* Brac University
* ID 16101084
* Completed on 9/07/2017
* Copyrighted by ©Saqib_Islam */


public class HistoryActivity extends AppCompatActivity{

    SQLiteOpenHelper dbHelper;
    boolean check=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dbHelper=new ExpenseDBHelper(this);

        final TextView view=(TextView) findViewById(R.id.view);
        final TextView totalview=(TextView) findViewById(R.id.Total);
        Button current= (Button)findViewById(R.id.currentMonth);
        final Button month=(Button) findViewById(R.id.prevMonthTotal);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int [] current=currentMonth();
                if(check){
                String curr= getFormat(current);
                    String total=getTotal(current);

                view.setText(curr);
                totalview.setText(total);
                }
                else{
                view.setText("Not Saved");}
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText(makeMonth(monthTotal()));
                totalview.setText(makeMonthTotal(monthTotal()));
            }
        });

    }

    public int[] currentMonth(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String [] projection={ExpenseContract.ExpenseEntry._ID,ExpenseContract.ExpenseEntry.COLUMN_EXPENSE,ExpenseContract.ExpenseEntry.COLUMN_MONTH};
        Cursor c=db.query(ExpenseContract.ExpenseEntry.TABLE_NAME,projection,null,null,null,null,null);
        if(check=c.moveToLast()){
            int colIndex=c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE);
            String s=c.getString(colIndex);
            String [] sArr=s.split(", ");
            int[] arr=new int[sArr.length];
            for(int z=1;z<arr.length-1;z++) {
                arr[z] = Integer.parseInt(sArr[z]);
                Log.v("MainActivity.this", "" + arr[z]);
            }
            c.close();
            db.close();
            return arr;
        }
        else return new int[33];
        }

        public String getFormat(int[] arr){
            String s="";
            for(int c=1;c<arr.length-1;c++){
                s= s+ "Day "+c+" = "+arr[c]+"\n";

            }
            return s;
        }
        public String getTotal(int [] arr){
            int Total=0;
            String s="";
            for(int c=1;c<arr.length-1;c++){
                Total=Total+arr[c];
            }
            s=s+ "Total : "+Total;
            return s;
        }

        private int[] monthTotal(){
        int [] months;
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        String [] projection={ExpenseContract.ExpenseEntry._ID,ExpenseContract.ExpenseEntry.COLUMN_EXPENSE,ExpenseContract.ExpenseEntry.COLUMN_MONTH};
        Cursor c=db.query(ExpenseContract.ExpenseEntry.TABLE_NAME,projection,null,null,null,null,null);
        int number=c.getCount();
        if(c.moveToFirst()&&number>0){
            months=new int[number];
            int col=c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE);
            String first=c.getString(col);
            String [] temp=first.split(", ");
            int[] temparr=new int[temp.length];
            for(int r=1;r<temparr.length-1;r++){
                temparr[r] = Integer.parseInt(temp[r]);
                months[0]=months[0]+temparr[r];
                Log.v("MainActivity.this", "" + months[0]);
            }


            int s=1;
            while(c.moveToNext()){
                int colIndex=c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_EXPENSE);
                String str=c.getString(colIndex);
                String [] sArr=str.split(", ");
                int[] arr=new int[sArr.length];
                for(int z=1;z<arr.length-1;z++) {
                    arr[z] = Integer.parseInt(sArr[z]);
                    months[s]=months[s]+arr[z];
                    Log.v("MainActivity.this", "" + months[s]);
                }
                s++;
            }
            c.close();
            db.close();
            return months;
        }
        else return new int[1];
    }

    private String makeMonth(int [] arr){
        String s="";
        int m=1;
        for(int c=0;c<arr.length;c++){
            s=s+"Month "+m+": "+arr[c]+"\n";
            m++;
        }
        return s;
    }

    private String makeMonthTotal(int [] arr){
        String s="Total : ";
        int total=0;
        for(int c=0;c<arr.length;c++){
            total+=arr[c];
        }
        s=s+total;
        return s;
    }

}
