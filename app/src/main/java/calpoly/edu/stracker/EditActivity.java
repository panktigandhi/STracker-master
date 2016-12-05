package calpoly.edu.stracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    String itemTitle, date;
    String amount,category;
    int position;
    EditText editDescription, editDate, editAmount;
    DatabaseHelper mydb;
    Calendar transactionCalendar;
    private DatePickerDialog fromDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position=extras.getInt("position");
            itemTitle = extras.getString("ItemTitle");
            date = extras.getString("date");
            amount = extras.getString("amount");
            category=extras.getString("category");
        }
        editDescription = (EditText) findViewById(R.id.editDesc);
        editDate = (EditText) findViewById(R.id.editDate);
        editAmount = (EditText) findViewById(R.id.editAmount);
        editDescription.setText(itemTitle);
        editDate.setText(date);
        editAmount.setText(amount);
        mydb = new DatabaseHelper(this);
        getSupportActionBar().setTitle("Edit Transaction");
        editDate.setInputType(InputType.TYPE_NULL);
        editDate.requestFocus();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_done) {
            updateTransaction();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateTransaction() {
        boolean isUpdate = mydb.updateData(position, editDescription.getText().toString(), DatabaseHelper.convertSqlDate(transactionCalendar), editAmount.getText().toString(),category);
        Log.d("editDescription", editDescription.getText().toString());
        Log.d("editDate", DatabaseHelper.convertSqlDate(transactionCalendar));
        Log.d("editAmount", editAmount.getText().toString());

        if (isUpdate == true) {
            Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(), "Update Fail", Toast.LENGTH_LONG).show();
        finish();
    }
}
