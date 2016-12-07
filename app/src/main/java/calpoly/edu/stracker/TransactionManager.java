package calpoly.edu.stracker;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by makkabeus on 11/25/16.
 */

public class TransactionManager {

    static ArrayList<Transaction> income;
    static ArrayList<Transaction> expense;
    static DatabaseHelper db;
    static Context appInfo;

    public static void transactionManagerInit(Context appContext) {
        appInfo = appContext;
        db = new DatabaseHelper(appInfo);
    }

    // mode < 0 negative , mode = 0 get all, mode = 1, get positive
    public static ArrayList<Transaction> getTransactions(String begin, String end, int mode) {
        if (mode < 0)
            return db.getExpenseDateRange(begin, end);
        else if (mode == 0)
            return db.getTransactionDateRange(begin, end);
        else
            return db.getIncomeDateRange(begin, end);

    }



}
