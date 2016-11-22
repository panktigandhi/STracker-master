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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SummaryTab extends Fragment {

    EditText beginDate, endDate;
    Calendar beginCalendar, endCalendar;
    ImageButton FAB;

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
        View v =inflater.inflate(R.layout.summary_tab,container,false);

        beginDate = (EditText) v.findViewById(R.id.summary_begin_date);
        endDate = (EditText) v.findViewById(R.id.summary_end_date);

        beginCalendar = Calendar.getInstance();
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
        endCalendar.add(Calendar.MONTH, -1);
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

        return v;
    }

    private void updateBeginning() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        beginDate.setText(sdf.format(beginCalendar.getTime()));
    }

    private void updateEnding() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        endDate.setText(sdf.format(endCalendar.getTime()));
    }


}