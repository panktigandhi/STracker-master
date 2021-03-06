package calpoly.edu.stracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by makkabeus on 12/3/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private Context curContext;

    public static final String DB_NAME = "stracker.db";
    public static final String TABLE_TRANSACTIONS = "AppTransaction";
    public static final String ID = "ID";
    public static final String TASK = "TASK";
    public static final String DATE = "DATE";
    public static final String AMOUNT = "AMOUNT";
    public static final String CATEGORY = "CATEGORY";

    public static final String TABLE_CATEGORY = "Category";
    public static final String CATEGORYNAME = "CATEGORYNAME";
    public static final String IMAGE = "IMAGE";
    public static final String CATEGORYTYPE = "CATEGORYTYPE";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        curContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(" create table " + TABLE_TRANSACTIONS + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + DATE + " TEXT, " + AMOUNT + " DECIMAL(6,2), " + CATEGORY + " STRING ) ");
            db.execSQL(" create table " + TABLE_CATEGORY + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IMAGE + " BLOB, " + CATEGORYNAME + " TEXT, " + CATEGORYTYPE + " TEXT ) ");
        } catch (Exception e) {
            System.out.println("PLS WORK");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists" + TABLE_TRANSACTIONS);
        db.execSQL("Drop Table if exists" + TABLE_CATEGORY);
        onCreate(db);
    }

    public boolean insertData(String Task, String Date, String Amount, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, Task);
        contentValues.put(DATE, Date);
        contentValues.put(AMOUNT, Amount);
        contentValues.put(CATEGORY, Category);

        long result = db.insert(TABLE_TRANSACTIONS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(int id, String Task, String Date, String Amount, String Category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, Task);
        contentValues.put(DATE, Date);
        contentValues.put(AMOUNT, Amount);
        contentValues.put(CATEGORY, Category);
        db.update(TABLE_TRANSACTIONS, contentValues, ID + " = ?", new String[]{id + ""});
        return true;
    }

    public Integer deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRANSACTIONS, ID + " = ?", new String[]{id + ""});
    }

    public ArrayList<Transaction> getTransactionDateRange(String begin, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + DATE + " >= \"" + begin +
                "\" AND " + DATE + " <= \"" + end + "\"", null);
        return getTransactions(res);
    }

    public ArrayList<Transaction> getIncomeDateRange(String begin, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + AMOUNT + " >= 0" +
                " AND " + DATE + " >= \"" + begin + "\"" +
                " AND " + DATE + " <= \"" + end + "\"", null);

        return getTransactions(res);
    }

    public ArrayList<Transaction> getExpenseDateRange(String begin, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS +
                " WHERE " + AMOUNT + " <= 0" +
                " AND " + DATE + " >= \"" + begin + "\"" +
                " AND " + DATE + " <= \"" + end + "\"", null);

        return getTransactions(res);
    }

    public static ArrayList<Transaction> getTransactions(Cursor res) {

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Transaction temp;

        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                temp = new Transaction();
                temp.setId(res.getInt(0));
                temp.setTitle(res.getString(1));
                temp.setDate(res.getString(2));
                temp.setAmount(res.getInt(3));
                temp.setCategory(res.getString(4));
                transactions.add(temp);
                res.moveToNext();
            }
        }
        res.close();
        return transactions;
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

        String[] incomeCatNames = {
                "Salary"
        };

        Integer[] incomeImageId = {
                R.drawable.general
        };

        Drawable d;
        Bitmap bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        for (int ndx = 0; ndx < web.length; ndx++) {
            d = curContext.getResources().getDrawable(imageId[ndx], curContext.getTheme());
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream.reset();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            insertCategory(stream.toByteArray(), web[ndx], "expense");
        }

        for (int ndx = 0; ndx < incomeCatNames.length; ndx++) {
            d = curContext.getResources().getDrawable(incomeImageId[ndx], curContext.getTheme());
            bitmap = ((BitmapDrawable) d).getBitmap();
            stream.reset();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            insertCategory(stream.toByteArray(), incomeCatNames[ndx], "income");
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean insertCategory(byte[] image, String category, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE, image);
        contentValues.put(CATEGORYNAME, category);
        contentValues.put(CATEGORYTYPE, type);

        long result = db.insert(TABLE_CATEGORY, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Category> getCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORY, null);

        ArrayList<Category> rtn = new ArrayList<Category>();
        Category temp;
        byte[] byteArray;
        Bitmap bmp;

        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                temp = new Category();
                temp.id = res.getInt(0);
                byteArray = res.getBlob(1);
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                temp.icon = bmp;
                temp.title = res.getString(2);
                rtn.add(temp);
                res.moveToNext();
            }
        }

        if (rtn.size() == 0) {
            initCategories();
            return getCategories();
        }

        res.close();
        return rtn;
    }
    public ArrayList<Category> getExpenseCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORY + " where " + CATEGORYTYPE + " = \"expense\"", null);

        ArrayList<Category> rtn1 = new ArrayList<Category>();
        Category temp;
        byte[] byteArray;
        Bitmap bmp;

        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                temp = new Category();
                temp.id = res.getInt(0);
                byteArray = res.getBlob(1);
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                temp.icon = bmp;
                temp.title = res.getString(2);
                rtn1.add(temp);
                res.moveToNext();
            }
        }

        if (rtn1.size() == 0) {
            initCategories();
            return getExpenseCategories();
        }

        res.close();
        return rtn1;
    }

    public ArrayList<Category> getIncomeCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORY + " where " + CATEGORYTYPE + " = \"income\"", null);

        ArrayList<Category> rtn2 = new ArrayList<Category>();
        Category temp;
        byte[] byteArray;
        Bitmap bmp;

        if (res.moveToFirst()) {
            while (!res.isAfterLast()) {
                temp = new Category();
                temp.id = res.getInt(0);
                byteArray = res.getBlob(1);
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                temp.icon = bmp;
                temp.title = res.getString(2);
                rtn2.add(temp);
                res.moveToNext();
            }
        }

        if (rtn2.size() == 0) {
            initCategories();
            return getIncomeCategories();
        }

        res.close();
        return rtn2;
    }

    public List<String> getAllCategoryNames() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + CATEGORYNAME + " from " + TABLE_CATEGORY + " where " + CATEGORYTYPE + " = \"expense\"", null);
        if (res.moveToFirst()) {
            do {
                list.add(res.getString(0));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
    }
    public List<String> getAllCategoryNamesIncome() {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + CATEGORYNAME + " from " + TABLE_CATEGORY + " where " + CATEGORYTYPE + " = \"income\"", null);
        if (res.moveToFirst()) {
            do {
                list.add(res.getString(0));
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
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

    public static String convertDoubleToHumanDecimal(double myNumber) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(myNumber);
    }

}
