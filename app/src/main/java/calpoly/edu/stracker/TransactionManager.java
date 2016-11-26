package calpoly.edu.stracker;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by makkabeus on 11/25/16.
 */

public class TransactionManager {

    static ArrayList<Transaction> transactions;
    static DatabaseHelper db;
    static Context appInfo;

    public static void transactionManagerInit(Context appContext) {
        appInfo = appContext;
        db = new DatabaseHelper(appInfo);
    }

    public static ArrayList<Transaction> getTransactions(String begin, String end) {

        transactions = new ArrayList<Transaction>();
        Cursor retrieval = db.getDataDateRange(begin, end);
        Transaction temp;

        if (retrieval.moveToFirst()) {
            while (!retrieval.isAfterLast()) {
                temp = new Transaction();
                temp.setId(retrieval.getInt(0));
                temp.setTitle(retrieval.getString(1));
                temp.setDate(retrieval.getString(2));
                temp.setAmount(retrieval.getInt(3));
                temp.setCategory(retrieval.getString(4));
                transactions.add(temp);
                retrieval.moveToNext();
            }
        }

        retrieval.close();
        return transactions;
    }


}
