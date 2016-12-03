package calpoly.edu.stracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by panktigandhi on 10/22/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "stracker.db";
    public static final String TABLE_EXPENSE = "expenses";
    public static final String ID = "ID";
    public static final String TASK = "TASK";
    public static final String DATE = "DATE";
    public static final String AMOUNT = "AMOUNT";
    public static final String CATEGORY = "CATEGORY";

    public static final String TABLE_CATEGORY = "category";
    public static final String CATEGORYNAME = "CATEGORYNAME";
    public static final String IMAGE = "IMAGE";
    public static final String CATEGORYTYPE = "CATEGORYTYPE";

    public static final String TABLE_INCOME = "income";
    public static final String INCOMETASK = "INCOMETASK";
    public static final String INCOMEDATE = "INCOMEDATE";
    public static final String INCOMEAMOUNT = "INCOMEAMOUNT";
    public static final String INCOMECATEGORY = "INCOMECATEGORY";
    String name = "Salary";
    private Context curContext;
    byte[] imageArray;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        curContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(" create table " + TABLE_EXPENSE + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, DATE TEXT, AMOUNT INTEGER, CATEGORY TEXT ) ");
            db.execSQL(" create table " + TABLE_CATEGORY + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, IMAGE BLOB, CATEGORYNAME TEXT, CATEGORYTYPE TEXT ) ");
            db.execSQL(" create table " + TABLE_INCOME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, INCOMETASK TEXT, INCOMEDATE TEXT, INCOMEAMOUNT INTEGER, INCOMECATEGORY TEXT ) ");
        } catch (Exception e) {
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists" + TABLE_EXPENSE);
        db.execSQL("Drop Table if exists" + TABLE_CATEGORY);
        db.execSQL("Drop Table if exists" + TABLE_INCOME);
        onCreate(db);
    }

    public boolean insertData(String Task, String Date, String Amount, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, Task);
        contentValues.put(DATE, Date);
        contentValues.put(AMOUNT, Amount);
        contentValues.put(CATEGORY, Category);

        long result = db.insert(TABLE_EXPENSE, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public void initCategories() {
        String[] web = {
                "Shopping",
                "Eating Out",
                "Travel",
                "Entertainment",
                "Fuel",
                "General",
                "Gifts"
        };
        Integer[] imageId = {
                R.drawable.shopping,
                R.drawable.eating_out,
                R.drawable.travel,
                R.drawable.entertainment,
                R.drawable.fuel,
                R.drawable.general,
                R.drawable.gifts
        };

        Integer expenseImage = R.drawable.general;

        Drawable d;
        Bitmap bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for (int ndx = 0; ndx < web.length; ndx++) {
            d = curContext.getResources().getDrawable(imageId[ndx], curContext.getTheme());
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream.reset();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            insertCategory(stream.toByteArray(), web[ndx], "expense");
            if (web[ndx].equals("General")) {
                imageArray = stream.toByteArray();
            }
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public String getTransactionasString()
{
    String copy="";
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor res = db.rawQuery("select " + TASK + "," + DATE + "," + AMOUNT + " from " + TABLE_EXPENSE, null);
    if (res.moveToFirst()) {
        do {
            copy=copy + res.getString(0)+ "\t\t" + res.getString(1) + "\t\t" + "$" + res.getString(2) + "\n";
        } while (res.moveToNext());
    }
    res.close();
    db.close();
    return copy;

}
    public boolean insertCategory(byte[] image, String category, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE, image);
        contentValues.put(CATEGORYNAME, category);
        contentValues.put(CATEGORYTYPE, type);

        long result = db.insert(TABLE_CATEGORY, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORY, null);

        return res;
    }

    public List<String> getAllCategoryNames() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + CATEGORYNAME + " from " + TABLE_CATEGORY + " where " + CATEGORYTYPE + " = \"expense\"" , null);
        if (res.moveToFirst()) {
            do {
                list.add(res.getString(0));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_EXPENSE, null);
        return res;
    }

    public Cursor getExpenseDateRange(String begin, String end) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE +
                " WHERE " + DATE + " >= \"" + begin +
                "\" AND " + DATE + " <= \"" + end + "\"", null);

        return res;
    }

    public Cursor getIncomeDateRange(String begin, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_INCOME +
                " WHERE " + INCOMEDATE + " >= \"" + begin +
                "\" AND " + INCOMEDATE + " <= \"" + end + "\"", null);

        return res;
    }


    public Integer deleteData(String Task) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENSE, "TASK = ?", new String[]{Task});
    }

    public static String convertIntToDecimal(int myNumber) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(myNumber / 100.0);
    }

    public static String convertSqlDate(Calendar calendar) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(calendar.getTime());
    }

    public static String convertHumanDate(Calendar calendar) {
        String format = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(calendar.getTime());
    }

    public boolean insertIncomeData(String Task, String Date, String Amount, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INCOMETASK, Task);
        contentValues.put(INCOMEDATE, Date);
        contentValues.put(INCOMEAMOUNT, Amount);
        contentValues.put(INCOMECATEGORY, Category);

        long result = db.insert(TABLE_INCOME, null, contentValues);
        if (result == -1) return false;
        else return true;
    }
}
