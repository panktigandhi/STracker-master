package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SummaryTab extends Fragment {

    EditText beginDate, endDate;
    TextView incomeValue, expenseValue, totalValue;
    Calendar beginCalendar, endCalendar;
    ImageButton FAB;
    DatabaseHelper mydb;

    DatePickerDialog.OnDateSetListener beginDatePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            beginCalendar.set(Calendar.YEAR, year);
            beginCalendar.set(Calendar.MONTH, monthOfYear);
            beginCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateBeginning();
        }
    };

    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEnding();
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
        updateBeginning();
        beginDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(getContext(), beginDatePicker,
                        beginCalendar.get(Calendar.YEAR),
                        beginCalendar.get(Calendar.MONTH),
                        beginCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        endCalendar = Calendar.getInstance();
        updateEnding();
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

    private void updateBeginning() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        beginDate.setText(sdf.format(beginCalendar.getTime()));
    }

    private void updateEnding() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        endDate.setText(sdf.format(endCalendar.getTime()));
    }

    private void setTotals() {
        TransactionManager.transactionManagerInit(getContext());
        ArrayList<Transaction> mList = TransactionManager.getTransactions(beginDate.getText().toString(), endDate.getText().toString());
        int income = 0;
        int expenses = 0;

        for (Transaction curTrans : mList) {
            if (curTrans.amount < 0) {
                expenses += curTrans.amount;
            } else {
                income += curTrans.amount;
            }
        }

        incomeValue.setText("" + income);
        expenseValue.setText("" + expenses);
        totalValue.setText("" + (income + expenses));

    }

}