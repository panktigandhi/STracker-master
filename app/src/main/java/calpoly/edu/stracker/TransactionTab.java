package calpoly.edu.stracker;

/**
 * Created by panktigandhi on 10/15/16.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

public class TransactionTab extends Fragment {

    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;

    EditText beginDate, endDate;
    Calendar beginCalendar, endCalendar;
    int mode = 0;


    DatePickerDialog.OnDateSetListener beginDatePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            beginCalendar.set(Calendar.YEAR, year);
            beginCalendar.set(Calendar.MONTH, monthOfYear);
            beginCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            beginDate.setText(DatabaseHelper.convertHumanDate(beginCalendar));
            updateRange();
        }
    };

    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDate.setText(DatabaseHelper.convertHumanDate(endCalendar));
            updateRange();
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.transaction_tab,container,false);

        RadioGroup transactionType = (RadioGroup) v.findViewById(R.id.transaction_radio_group);
        transactionType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.transaction_radio_group_radioAll:
                        mode = 0;
                        break;
                    case R.id.transaction_radio_group_radioIncome:
                        mode = 1;
                        break;
                    case R.id.transaction_radio_group_radioExpense:
                        mode = -1;
                        break;
                    default:
                        mode = 0;
                        break;
                }

            }
        });

        beginDate = (EditText) v.findViewById(R.id.transaction_begin_date);
        beginDate.setInputType(InputType.TYPE_NULL);
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


        endDate = (EditText) v.findViewById(R.id.transaction_end_date);
        endDate.setInputType(InputType.TYPE_NULL);
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

        transactionRecyclerView = (RecyclerView) v.findViewById(R.id.transaction_recyclerview);
        transactionRecyclerView.setLayoutManager((new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)));

        transactionAdapter = new TransactionAdapter(TransactionManager.getTransactions(
                DatabaseHelper.convertSqlDate(beginCalendar),
                DatabaseHelper.convertSqlDate(endCalendar),
                mode) ,
            getContext());

        transactionRecyclerView.setAdapter(transactionAdapter);
        ItemTouchHelper.Callback callback = new SwipeHelper(transactionAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(transactionRecyclerView);
        return v;
    }

    public void updateRange() {
        transactionAdapter = new TransactionAdapter(TransactionManager.getTransactions(
                DatabaseHelper.convertSqlDate(beginCalendar),
                DatabaseHelper.convertSqlDate(endCalendar),
                mode) ,
                getContext());

        transactionRecyclerView.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();
    }

    public void onStart() {
        super.onStart();
        updateRange();
    }

}