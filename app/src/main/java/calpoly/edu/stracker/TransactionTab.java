package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TransactionTab extends Fragment {

    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;

    EditText beginDate, endDate;
    Calendar beginCalendar, endCalendar;


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

        View v = inflater.inflate(R.layout.transaction_tab,container,false);

        beginDate = (EditText) v.findViewById(R.id.transaction_begin_date);
        endDate = (EditText) v.findViewById(R.id.transaction_end_date);

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

        TransactionManager.transactionManagerInit(getContext());

        transactionRecyclerView = (RecyclerView) v.findViewById(R.id.transaction_recyclerview);
        transactionRecyclerView.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));
        transactionAdapter = new TransactionAdapter(TransactionManager.getTransactions(beginDate.getText().toString(), endDate.getText().toString()), getContext());
        transactionRecyclerView.setAdapter(transactionAdapter);

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
}