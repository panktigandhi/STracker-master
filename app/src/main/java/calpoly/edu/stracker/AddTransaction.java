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
    EditText edittask, editcategory,editdate,editamount;
    Button buttonshow,buttonupdate,buttondelete;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        mydb = new DatabaseHelper(this);
        edittask = (EditText) findViewById(R.id.edittext_task);

        editamount = (EditText) findViewById(R.id.edittext_amount);
        editcategory = (EditText) findViewById(R.id.edittext_category);

        buttonshow = (Button) findViewById(R.id.buttonshow);
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
        editdate = (EditText) findViewById(R.id.edittext_date);
        editdate.setInputType(InputType.TYPE_NULL);
        editdate.requestFocus();
    }
    private void setDateTimeField() {
        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

//    public void UpdateData()
//    {
//        buttonupdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isUpdate = mydb.updateData(edittask.getText().toString(), editdate.getText().toString(),editamount.getText().toString(),editcategory.getText().toString());
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

                boolean isInserted = mydb.insertData(edittask.getText().toString(), editdate.getText().toString(),editamount.getText().toString(),editcategory.getText().toString());
                if (isInserted == true)
                    Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
            }

    public void viewAllData()
    {
        buttonshow.setOnClickListener(new View.OnClickListener() {
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
            Integer deletedRows=mydb.deleteData(edittask.getText().toString());
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

        int id = item.getItemId();

        if (id == R.id.menu_share) {
           addData();
        }

        return super.onOptionsItemSelected(item);
    }
}

