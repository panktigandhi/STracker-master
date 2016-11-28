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

    public static ArrayList<Transaction> getTransactions(String begin, String end, boolean isIncome) {

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Cursor retrieval = isIncome ? db.getIncomeDateRange(begin, end) : db.getExpenseDateRange(begin, end);
        Transaction temp;

        System.out.println("Attempt Retrieval");
        if (retrieval.moveToFirst()) {
            while (!retrieval.isAfterLast()) {
                temp = new Transaction();
                temp.setId(retrieval.getInt(0));
                temp.setTitle(retrieval.getString(1));
                temp.setDate(retrieval.getString(2));
                temp.setAmount(retrieval.getInt(3));
                temp.setCategory(retrieval.getString(4));
                temp.setIsIncome(isIncome);
                transactions.add(temp);
                retrieval.moveToNext();
            }
        }

        retrieval.close();

        if (isIncome) {
            income = transactions;
        } else {
            expense = transactions;
        }

        return transactions;
    }



}
