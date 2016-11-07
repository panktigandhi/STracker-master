package calpoly.edu.stracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText editimage, editcategoryname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mydb = new DatabaseHelper(this);
        editimage = (EditText) findViewById(R.id.edittext_image);
        editcategoryname = (EditText) findViewById(R.id.edittext_categoryname);
    }
    public void addCategory() {

        boolean isInserted = mydb.insertCategory(editimage.getText().toString(), editcategoryname.getText().toString());
        if (isInserted == true)
            Toast.makeText(getApplicationContext(), "Insert Success", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Insert Fail", Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_add) {
            addCategory();
        }

        return super.onOptionsItemSelected(item);
    }
}
