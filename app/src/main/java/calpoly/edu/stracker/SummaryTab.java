package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class SummaryTab extends Fragment {

    EditText beginDate, endDate;
    TextView incomeValue, expenseValue, totalValue;
    Calendar beginCalendar, endCalendar;
    ImageButton FAB;

    DatePickerDialog.OnDateSetListener beginDatePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            beginCalendar.set(Calendar.YEAR, year);
            beginCalendar.set(Calendar.MONTH, monthOfYear);
            beginCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            beginDate.setText(DatabaseHelper.convertHumanDate(beginCalendar));
            setTotals();
        }
    };

    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDate.setText(DatabaseHelper.convertHumanDate(endCalendar));
            setTotals();
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.summary_tab, container, false);

        beginDate = (EditText) v.findViewById(R.id.summary_begin_date);
        endDate = (EditText) v.findViewById(R.id.summary_end_date);
        incomeValue = (TextView) v.findViewById(R.id.summary_income_value);
        expenseValue = (TextView) v.findViewById(R.id.summary_expense_value);
        totalValue = (TextView) v.findViewById(R.id.summary_totals_value);

        beginCalendar = Calendar.getInstance();
        beginCalendar.add(Calendar.MONTH, -1);
        beginDate.setText(DatabaseHelper.convertHumanDate(beginCalendar));
        beginDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(getContext(), beginDatePicker,
                        beginCalendar.get(Calendar.YEAR),
                        beginCalendar.get(Calendar.MONTH),
                        beginCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        endCalendar = Calendar.getInstance();
        endDate.setText(DatabaseHelper.convertHumanDate(endCalendar));
        endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(getContext(), endDatePicker,
                        endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FAB = (ImageButton) v.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTransaction.class);
                startActivity(intent);
            }
        });

        setTotals();

        return v;
    }

    private void setTotals() {

        ArrayList<Transaction> incomeList = TransactionManager.getTransactions(
                DatabaseHelper.convertSqlDate(beginCalendar),
                DatabaseHelper.convertSqlDate(endCalendar),
                true);

        ArrayList<Transaction> expenseList = TransactionManager.getTransactions(
                DatabaseHelper.convertSqlDate(beginCalendar),
                DatabaseHelper.convertSqlDate(endCalendar),
                false);

        System.out.println("GET SUMMARY");
        System.out.println(incomeList);
        System.out.println(expenseList);

        int income = 0;
        int expenses = 0;

        for (Transaction curTrans : incomeList) {
            income += curTrans.amount;
        }
        for (Transaction curTrans : expenseList) {
            expenses += curTrans.amount;
        }

        incomeValue.setText("" + income);
        expenseValue.setText("" + expenses);
        totalValue.setText("" + (income - expenses));

    }

}