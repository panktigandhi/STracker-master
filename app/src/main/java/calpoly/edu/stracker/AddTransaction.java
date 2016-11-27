package calpoly.edu.stracker;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransaction extends AppCompatActivity {

    DatabaseHelper mydb;
    Toolbar toolbar;
    EditText editTask, editCategory, editDate, editAmount;
    Button buttonShow,buttonupdate,buttondelete;
    Calendar transactionCalendar;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        mydb = new DatabaseHelper(this);
        editTask = (EditText) findViewById(R.id.edittext_task);

        editAmount = (EditText) findViewById(R.id.edittext_amount);
        editCategory = (EditText) findViewById(R.id.edittext_category);

        buttonShow = (Button) findViewById(R.id.buttonshow);
        //buttonupdate = (Button) findViewById(R.id.buttonupdate);
        buttondelete = (Button) findViewById(R.id.buttondelete);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
        viewAllData();
        //UpdateData();
        deleteData();
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

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                transactionCalendar.set(year, monthOfYear, dayOfMonth);
                editDate.setText(DatabaseHelper.convertHumanDate(transactionCalendar));
            }
        },transactionCalendar.get(Calendar.YEAR),
            transactionCalendar.get(Calendar.MONTH),
            transactionCalendar.get(Calendar.DAY_OF_MONTH));

        editDate.setText(DatabaseHelper.convertHumanDate(transactionCalendar));
    }

//    public void UpdateData()
//    {
//        buttonupdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isUpdate = mydb.updateData(edittask.getText().toString(), editDate.getText().toString(),editAmount.getText().toString(),editcategory.getText().toString());
//                if(isUpdate==true)
//                {
//                    Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_LONG).show();
//                }
//                else
//                    Toast.makeText(getApplicationContext(), "Update Fail", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }

    public void addData() {

                boolean isInserted = mydb.insertData(
                        editTask.getText().toString(),
                        DatabaseHelper.convertSqlDate(transactionCalendar),
                        editAmount.getText().toString(),
                        editCategory.getText().toString());

                if (isInserted == true)
                    Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
            }

    public void viewAllData()
    {
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res= mydb.getAllData();
                if(res.getCount()==0)
                {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext())
                {
                    buffer.append("Task: " + res.getString(0)+"\n");
                    buffer.append("Date: " + res.getString(1)+"\n");
                    buffer.append("Amount: " + res.getString(2)+"\n");
                    buffer.append("Category: " + res.getString(3)+"\n");
                }
                showMessage("DATA" ,buffer.toString());
            }
        });

    }
public void deleteData()
{
    buttondelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer deletedRows=mydb.deleteData(editTask.getText().toString());
            if(deletedRows>0)

                Toast.makeText(getApplicationContext(), "Delete Success", Toast.LENGTH_LONG).show();
                else
                Toast.makeText(getApplicationContext(), "Delete Fail", Toast.LENGTH_LONG).show();

        }
    });
}
    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

