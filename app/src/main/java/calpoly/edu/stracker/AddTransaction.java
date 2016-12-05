package calpoly.edu.stracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddTransaction extends AppCompatActivity {

    DatabaseHelper mydb;
    EditText editTask, editCategory, editDate, editAmount;
    Calendar transactionCalendar;
    Spinner spinner;
    String label;

    private DatePickerDialog fromDatePickerDialog;
    private RadioGroup radioGroup1;
    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        spinner = (Spinner) findViewById(R.id.spinner);
        mydb = new DatabaseHelper(this);
        editTask = (EditText) findViewById(R.id.edittext_task);
        getSupportActionBar().setTitle("Add Transaction");
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioExpense:
                        flag = 1;
                        loadSpinnerData(flag);
                        //Toast.makeText(getApplicationContext(), "Expense RadioButton checked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioIncome:
                        flag = 2;
                        loadSpinnerData(flag);
                        // Toast.makeText(getApplicationContext(), "Income RadioButton checked", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });
        loadSpinnerData(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                label = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editTask = (EditText) findViewById(R.id.edittext_task);

        editAmount = (EditText) findViewById(R.id.edittext_amount);

        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        editDate = (EditText) findViewById(R.id.edittext_date);
        editDate.setInputType(InputType.TYPE_NULL);
        editDate.requestFocus();
    }

    private void setDateTimeField() {
        editDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        transactionCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, R.style.MyDatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                transactionCalendar.set(year, monthOfYear, dayOfMonth);
                editDate.setText(DatabaseHelper.convertHumanDate(transactionCalendar));
            }
        }, transactionCalendar.get(Calendar.YEAR),
                transactionCalendar.get(Calendar.MONTH),
                transactionCalendar.get(Calendar.DAY_OF_MONTH));

        editDate.setText(DatabaseHelper.convertHumanDate(transactionCalendar));
    }

    public void addData() {
        if (flag == 1) {
            boolean isInserted = mydb.insertData(
                    editTask.getText().toString(),
                    DatabaseHelper.convertSqlDate(transactionCalendar),
                    editAmount.getText().toString(),
                    label);

            if (isInserted == true) {
                Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
            }
        } else if (flag == 2) {
            boolean isInsertedIncome = mydb.insertData(
                    editTask.getText().toString(),
                    DatabaseHelper.convertSqlDate(transactionCalendar),
                    editAmount.getText().toString(),
                    label);
            if (isInsertedIncome == true) {
                Toast.makeText(getApplicationContext(), "Insert Income Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Insert Income Fail", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void loadSpinnerData(int flag) {
        if (flag == 1) {
            List<String> categoryNames = mydb.getAllCategoryNames();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        } else if (flag == 2) {
            List<String> categoryNames = mydb.getAllCategoryNamesIncome();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            addData();
        }

        return super.onOptionsItemSelected(item);
    }

}

